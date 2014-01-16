/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Eventos;
import Entidades.VigenciasDeportes;
import Entidades.VigenciasEventos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplVigenciaEventoInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
public class ControlEmplVigenciaEvento implements Serializable {

    @EJB
    AdministrarEmplVigenciaEventoInterface administrarEmplVigenciaEvento;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    ////
    private List<VigenciasEventos> listVigenciasEventos;
    private List<VigenciasEventos> filtrarListVigenciasEventos;
    private List<Eventos> listEventos;
    private Eventos eventoSeleccionado;
    private List<Eventos> filtrarListEventos;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column veFechaInicial, veFechaFinal, veDescripcion, veIndividual, veCIndividual, veGrupal, veCGrupal;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasEventos> listVigenciaEventoModificar;
    private boolean guardado;
    //crear VC
    public VigenciasEventos nuevaVigenciaEvento;
    private List<VigenciasEventos> listVigenciaEventoCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasEventos> listVigenciaEventoBorrar;
    //editar celda
    private VigenciasEventos editarVigenciaEvento;
    private int cualCelda, tipoLista;
    //duplicar
    private VigenciasEventos duplicarVigenciaEvento;
    private String evento;
    private boolean permitirIndex;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Empleados empleado;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;

    public ControlEmplVigenciaEvento() {
        listVigenciasEventos = null;
        listEventos = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listVigenciaEventoBorrar = new ArrayList<VigenciasEventos>();
        //crear aficiones
        listVigenciaEventoCrear = new ArrayList<VigenciasEventos>();
        k = 0;
        //modificar aficiones
        listVigenciaEventoModificar = new ArrayList<VigenciasEventos>();
        //editar
        editarVigenciaEvento = new VigenciasEventos();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigenciaEvento = new VigenciasEventos();
        nuevaVigenciaEvento.setEvento(new Eventos());
        secRegistro = null;
        permitirIndex = true;
        backUpSecRegistro = null;
        empleado = new Empleados();

    }

    public void recibirEmpleado(BigInteger secuencia) {
        listVigenciasEventos = null;
        listEventos = null;
        empleado = administrarEmplVigenciaEvento.empleadoActual(secuencia);
    }

    public void modificarVigenciaEvento(int indice) {
        if (tipoLista == 0) {
            if (!listVigenciaEventoCrear.contains(listVigenciasEventos.get(indice))) {

                if (listVigenciaEventoModificar.isEmpty()) {
                    listVigenciaEventoModificar.add(listVigenciasEventos.get(indice));
                } else if (!listVigenciaEventoModificar.contains(listVigenciasEventos.get(indice))) {
                    listVigenciaEventoModificar.add(listVigenciasEventos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        } else {
            if (!listVigenciaEventoCrear.contains(filtrarListVigenciasEventos.get(indice))) {

                if (listVigenciaEventoModificar.isEmpty()) {
                    listVigenciaEventoModificar.add(filtrarListVigenciasEventos.get(indice));
                } else if (!listVigenciaEventoModificar.contains(filtrarListVigenciasEventos.get(indice))) {
                    listVigenciaEventoModificar.add(filtrarListVigenciasEventos.get(indice));
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
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciasReformasLaborales de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVigenciaEvento(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EVENTOS")) {
            if (tipoLista == 0) {
                listVigenciasEventos.get(indice).getEvento().setDescripcion(evento);
            } else {
                filtrarListVigenciasEventos.get(indice).getEvento().setDescripcion(evento);
            }
            for (int i = 0; i < listEventos.size(); i++) {
                if (listEventos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasEventos.get(indice).setEvento(listEventos.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasEventos.get(indice).setEvento(listEventos.get(indiceUnicoElemento));
                }
                listEventos.clear();
                getListEventos();
            } else {
                permitirIndex = false;
                context.update("form:EventosDialogo");
                context.execute("EventosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVigenciaEventoCrear.contains(listVigenciasEventos.get(indice))) {

                    if (listVigenciaEventoModificar.isEmpty()) {
                        listVigenciaEventoModificar.add(listVigenciasEventos.get(indice));
                    } else if (!listVigenciaEventoModificar.contains(listVigenciasEventos.get(indice))) {
                        listVigenciaEventoModificar.add(listVigenciasEventos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVigenciaEventoCrear.contains(filtrarListVigenciasEventos.get(indice))) {

                    if (listVigenciaEventoModificar.isEmpty()) {
                        listVigenciaEventoModificar.add(filtrarListVigenciasEventos.get(indice));
                    } else if (!listVigenciaEventoModificar.contains(filtrarListVigenciasEventos.get(indice))) {
                        listVigenciaEventoModificar.add(filtrarListVigenciasEventos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigenciaEventos");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("EVENTOS")) {
            if (tipoNuevo == 1) {
                evento = nuevaVigenciaEvento.getEvento().getDescripcion();
            } else if (tipoNuevo == 2) {
                evento = duplicarVigenciaEvento.getEvento().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EVENTOS")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaEvento.getEvento().setDescripcion(evento);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaEvento.getEvento().setDescripcion(evento);
            }
            for (int i = 0; i < listEventos.size(); i++) {
                if (listEventos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaEvento.setEvento(listEventos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaVigencias");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaEvento.setEvento(listEventos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarVigencias");
                }
                listEventos.clear();
                getListEventos();
            } else {
                context.update("form:EventosDialogo");
                context.execute("EventosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaVigencias");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarVigencias");
                }
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasReformasLaborales
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                fechaFin = listVigenciasEventos.get(index).getFechafinal();
                fechaIni = listVigenciasEventos.get(index).getFechainicial();
                secRegistro = listVigenciasEventos.get(index).getSecuencia();
                if (cualCelda == 2) {
                    evento = listVigenciasEventos.get(index).getEvento().getDescripcion();
                }
            }
            if (tipoLista == 1) {
                fechaFin = filtrarListVigenciasEventos.get(index).getFechafinal();
                fechaIni = filtrarListVigenciasEventos.get(index).getFechainicial();
                secRegistro = filtrarListVigenciasEventos.get(index).getSecuencia();
                if (cualCelda == 2) {
                    evento = filtrarListVigenciasEventos.get(index).getEvento().getDescripcion();
                }
            }

        }
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasEventos auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listVigenciasEventos.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListVigenciasEventos.get(index);
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
            if (nuevaVigenciaEvento.getFechafinal() != null) {
                if (nuevaVigenciaEvento.getFechainicial().after(fechaParametro) && nuevaVigenciaEvento.getFechainicial().before(nuevaVigenciaEvento.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                if (nuevaVigenciaEvento.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVigenciaEvento.getFechafinal() != null) {
                if (duplicarVigenciaEvento.getFechainicial().after(fechaParametro) && duplicarVigenciaEvento.getFechainicial().before(duplicarVigenciaEvento.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                if (duplicarVigenciaEvento.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        VigenciasEventos auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listVigenciasEventos.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarListVigenciasEventos.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            if (auxiliar.getFechafinal() == null) {
                retorno = true;
            }
            if (auxiliar.getFechafinal() != null) {
                index = i;
                retorno = validarFechasRegistro(0);
            }
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarVigenciaEvento(i);
            } else {
                if (tipoLista == 0) {
                    listVigenciasEventos.get(i).setFechafinal(fechaFin);
                    listVigenciasEventos.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {
                    filtrarListVigenciasEventos.get(i).setFechafinal(fechaFin);
                    filtrarListVigenciasEventos.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciaEventos");
                context.execute("form:errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listVigenciasEventos.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {
                filtrarListVigenciasEventos.get(i).setFechainicial(fechaIni);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaEventos");
            context.execute("errorRegNew.show()");
        }
    }

    //GUARDAR
    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasReformasLaborales
     */
    public void guardarCambios() {
        if (guardado == false) {
            if (!listVigenciaEventoBorrar.isEmpty()) {
                administrarEmplVigenciaEvento.borrarVigenciasEventos(listVigenciaEventoBorrar);
                listVigenciaEventoBorrar.clear();
            }
            if (!listVigenciaEventoCrear.isEmpty()) {
                administrarEmplVigenciaEvento.crearVigenciasEventos(listVigenciaEventoCrear);
                listVigenciaEventoCrear.clear();
            }
            if (!listVigenciaEventoModificar.isEmpty()) {
                administrarEmplVigenciaEvento.editarVigenciasEventos(listVigenciaEventoModificar);
                listVigenciaEventoModificar.clear();
            }
            listVigenciasEventos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaEventos");
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
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaEventos");
            bandera = 0;
            filtrarListVigenciasEventos = null;
            tipoLista = 0;
        }

        listVigenciaEventoBorrar.clear();
        listVigenciaEventoCrear.clear();
        listVigenciaEventoModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasEventos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaEventos");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciaEvento = listVigenciasEventos.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaEvento = filtrarListVigenciasEventos.get(index);
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
    public void agregarNuevaVigenciaEvento() {
        if (nuevaVigenciaEvento.getFechainicial() != null && nuevaVigenciaEvento.getEvento().getSecuencia() != null) {
            if (validarFechasRegistro(1) == true) {
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaInicial");
                    veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaFinal");
                    veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veDescripcion");
                    veDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veIndividual");
                    veIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCIndividual");
                    veCIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veGrupal");
                    veGrupal.setFilterStyle("display: none; visibility: hidden;");
                    veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCGrupal");
                    veCGrupal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaEventos");
                    bandera = 0;
                    filtrarListVigenciasEventos = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigenciaEvento.setSecuencia(l);
                nuevaVigenciaEvento.setEmpleado(empleado);
                listVigenciaEventoCrear.add(nuevaVigenciaEvento);

                listVigenciasEventos.add(nuevaVigenciaEvento);
                nuevaVigenciaEvento = new VigenciasEventos();
                nuevaVigenciaEvento.setEvento(new Eventos());
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciaEventos");
                context.execute("NuevoRegistroVigencias.hide()");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
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
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVigenciaEvento() {
        nuevaVigenciaEvento = new VigenciasEventos();
        nuevaVigenciaEvento.setEvento(new Eventos());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVigenciaEventoM() {
        if (index >= 0) {
            duplicarVigenciaEvento = new VigenciasEventos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {

                duplicarVigenciaEvento.setSecuencia(l);
                duplicarVigenciaEvento.setFechafinal(listVigenciasEventos.get(index).getFechafinal());
                duplicarVigenciaEvento.setFechainicial(listVigenciasEventos.get(index).getFechainicial());
                duplicarVigenciaEvento.setEmpleado(listVigenciasEventos.get(index).getEmpleado());
                duplicarVigenciaEvento.setValorcualitativo(listVigenciasEventos.get(index).getValorcualitativo());
                duplicarVigenciaEvento.setValorcualitativogrupo(listVigenciasEventos.get(index).getValorcualitativogrupo());
                duplicarVigenciaEvento.setValorcuantitativo(listVigenciasEventos.get(index).getValorcuantitativo());
                duplicarVigenciaEvento.setValorcuantitativogrupo(listVigenciasEventos.get(index).getValorcuantitativogrupo());
                duplicarVigenciaEvento.setEvento(listVigenciasEventos.get(index).getEvento());

            }
            if (tipoLista == 1) {

                duplicarVigenciaEvento.setSecuencia(l);
                duplicarVigenciaEvento.setFechafinal(filtrarListVigenciasEventos.get(index).getFechafinal());
                duplicarVigenciaEvento.setFechainicial(filtrarListVigenciasEventos.get(index).getFechainicial());
                duplicarVigenciaEvento.setEmpleado(filtrarListVigenciasEventos.get(index).getEmpleado());
                duplicarVigenciaEvento.setValorcualitativo(filtrarListVigenciasEventos.get(index).getValorcualitativo());
                duplicarVigenciaEvento.setValorcualitativogrupo(filtrarListVigenciasEventos.get(index).getValorcualitativogrupo());
                duplicarVigenciaEvento.setValorcuantitativo(filtrarListVigenciasEventos.get(index).getValorcuantitativo());
                duplicarVigenciaEvento.setValorcuantitativogrupo(filtrarListVigenciasEventos.get(index).getValorcuantitativogrupo());
                duplicarVigenciaEvento.setEvento(filtrarListVigenciasEventos.get(index).getEvento());

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
        if (duplicarVigenciaEvento.getFechainicial() != null && duplicarVigenciaEvento.getEvento().getSecuencia() != null) {
            if (validarFechasRegistro(2) == true) {
                duplicarVigenciaEvento.setEmpleado(empleado);
                listVigenciasEventos.add(duplicarVigenciaEvento);
                listVigenciaEventoCrear.add(duplicarVigenciaEvento);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciaEventos");
                context.execute("DuplicarRegistroVigencias.hide()");
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaInicial");
                    veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaFinal");
                    veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veDescripcion");
                    veDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veIndividual");
                    veIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCIndividual");
                    veCIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veGrupal");
                    veGrupal.setFilterStyle("display: none; visibility: hidden;");
                    veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCGrupal");
                    veCGrupal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaEventos");
                    bandera = 0;
                    filtrarListVigenciasEventos = null;
                    tipoLista = 0;
                }
                duplicarVigenciaEvento = new VigenciasEventos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarDuplicar() {
        duplicarVigenciaEvento = new VigenciasEventos();
        duplicarVigenciaEvento.setEvento(new Eventos());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVigenciaEvento() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVigenciaEventoModificar.isEmpty() && listVigenciaEventoModificar.contains(listVigenciasEventos.get(index))) {
                    int modIndex = listVigenciaEventoModificar.indexOf(listVigenciasEventos.get(index));
                    listVigenciaEventoModificar.remove(modIndex);
                    listVigenciaEventoBorrar.add(listVigenciasEventos.get(index));
                } else if (!listVigenciaEventoCrear.isEmpty() && listVigenciaEventoCrear.contains(listVigenciasEventos.get(index))) {
                    int crearIndex = listVigenciaEventoCrear.indexOf(listVigenciasEventos.get(index));
                    listVigenciaEventoCrear.remove(crearIndex);
                } else {
                    listVigenciaEventoBorrar.add(listVigenciasEventos.get(index));
                }
                listVigenciasEventos.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVigenciaEventoModificar.isEmpty() && listVigenciaEventoModificar.contains(filtrarListVigenciasEventos.get(index))) {
                    int modIndex = listVigenciaEventoModificar.indexOf(filtrarListVigenciasEventos.get(index));
                    listVigenciaEventoModificar.remove(modIndex);
                    listVigenciaEventoBorrar.add(filtrarListVigenciasEventos.get(index));
                } else if (!listVigenciaEventoCrear.isEmpty() && listVigenciaEventoCrear.contains(filtrarListVigenciasEventos.get(index))) {
                    int crearIndex = listVigenciaEventoCrear.indexOf(filtrarListVigenciasEventos.get(index));
                    listVigenciaEventoCrear.remove(crearIndex);
                } else {
                    listVigenciaEventoBorrar.add(filtrarListVigenciasEventos.get(index));
                }
                int VCIndex = listVigenciasEventos.indexOf(filtrarListVigenciasEventos.get(index));
                listVigenciasEventos.remove(VCIndex);
                filtrarListVigenciasEventos.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaEventos");
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

            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaInicial");
            veFechaInicial.setFilterStyle("width: 50px");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaFinal");
            veFechaFinal.setFilterStyle("width: 50px");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veDescripcion");
            veDescripcion.setFilterStyle("width: 100px");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veIndividual");
            veIndividual.setFilterStyle("width: 100px");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCIndividual");
            veCIndividual.setFilterStyle("width: 100px");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veGrupal");
            veGrupal.setFilterStyle("width: 100px");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCGrupal");
            veCGrupal.setFilterStyle("width: 100px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaEventos");
            bandera = 1;
        } else if (bandera == 1) {
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaEventos");
            bandera = 0;
            filtrarListVigenciasEventos = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaEventos:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaEventos");
            bandera = 0;
            filtrarListVigenciasEventos = null;
            tipoLista = 0;
        }

        listVigenciaEventoBorrar.clear();
        listVigenciaEventoCrear.clear();
        listVigenciaEventoModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasEventos = null;
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
        context.update("form:EventosDialogo");
        context.execute("EventosDialogo.show()");
    }

    //LOVS
    //CIUDAD
    /**
     * Metodo que actualiza la reforma laboral seleccionada
     */
    public void actualizarEvento() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasEventos.get(index).setEvento(eventoSeleccionado);
                if (!listVigenciaEventoCrear.contains(listVigenciasEventos.get(index))) {
                    if (listVigenciaEventoModificar.isEmpty()) {
                        listVigenciaEventoModificar.add(listVigenciasEventos.get(index));
                    } else if (!listVigenciaEventoModificar.contains(listVigenciasEventos.get(index))) {
                        listVigenciaEventoModificar.add(listVigenciasEventos.get(index));
                    }
                }
            } else {
                filtrarListVigenciasEventos.get(index).setEvento(eventoSeleccionado);
                if (!listVigenciaEventoCrear.contains(filtrarListVigenciasEventos.get(index))) {
                    if (listVigenciaEventoModificar.isEmpty()) {
                        listVigenciaEventoModificar.add(filtrarListVigenciasEventos.get(index));
                    } else if (!listVigenciaEventoModificar.contains(filtrarListVigenciasEventos.get(index))) {
                        listVigenciaEventoModificar.add(filtrarListVigenciasEventos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaEventos");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaEvento.setEvento(eventoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVigencias");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaEvento.setEvento(eventoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigencias");
        }
        filtrarListEventos = null;
        eventoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre reforma laboral
     */
    public void cancelarCambioEvento() {
        filtrarListEventos = null;
        eventoSeleccionado = null;
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
                context.update("form:EventosDialogo");
                context.execute("EventosDialogo.show()");
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
        exporter.export(context, tabla, "VigenciasEventosPDF", false, false, "UTF-8", null, null);
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
        exporter.export(context, tabla, "VigenciasEventosXLS", false, false, "UTF-8", null, null);
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
        if (listVigenciasEventos != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "IDIOMASPERSONAS");
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
            if (administrarRastros.verificarHistoricosTabla("IDIOMASPERSONAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    //GETTERS AND SETTERS

    /**
     * Metodo que obtiene las VigenciasReformasLaborales de un empleado, en caso
     * de ser null por medio del administrar hace el llamado para almacenarlo
     *
     * @return listVC Lista VigenciasReformasLaborales
     */
    public List<VigenciasEventos> getListVigenciasEventos() {
        try {
            if (listVigenciasEventos == null) {
                listVigenciasEventos = new ArrayList<VigenciasEventos>();
                listVigenciasEventos = administrarEmplVigenciaEvento.listVigenciasEventosEmpleado(empleado.getSecuencia());
                return listVigenciasEventos;
            } else {
                return listVigenciasEventos;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListVigenciasEventos : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasEventos(List<VigenciasEventos> setListVigenciasEventos) {
        this.listVigenciasEventos = setListVigenciasEventos;
    }

    public List<VigenciasEventos> getFiltrarListVigenciasEventos() {
        return filtrarListVigenciasEventos;
    }

    public void setFiltrarListVigenciasEventos(List<VigenciasEventos> setFiltrarListVigenciasEventos) {
        this.filtrarListVigenciasEventos = setFiltrarListVigenciasEventos;
    }

    public VigenciasEventos getNuevaVigenciaEvento() {
        return nuevaVigenciaEvento;
    }

    public void setNuevaVigenciaEvento(VigenciasEventos setNuevaVigenciaEvento) {
        this.nuevaVigenciaEvento = setNuevaVigenciaEvento;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<Eventos> getListEventos() {
        if (listEventos == null) {
            listEventos = new ArrayList<Eventos>();
            listEventos = administrarEmplVigenciaEvento.listEventos();
        }
        return listEventos;
    }

    public void setListEventos(List<Eventos> setListEventos) {
        this.listEventos = setListEventos;
    }

    public List<Eventos> getFiltrarListEventos() {
        return filtrarListEventos;
    }

    public void setFiltrarListEventos(List<Eventos> setFiltrarListEventos) {
        this.filtrarListEventos = setFiltrarListEventos;
    }

    public VigenciasEventos getEditarVigenciaEvento() {
        return editarVigenciaEvento;
    }

    public void setEditarVigenciaEvento(VigenciasEventos setEditarVigenciaEvento) {
        this.editarVigenciaEvento = setEditarVigenciaEvento;
    }

    public VigenciasEventos getDuplicarVigenciaEvento() {
        return duplicarVigenciaEvento;
    }

    public void setDuplicarVigenciaEvento(VigenciasEventos setDuplicarVigenciaEvento) {
        this.duplicarVigenciaEvento = setDuplicarVigenciaEvento;
    }

    public Eventos getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(Eventos setEventoSeleccionado) {
        this.eventoSeleccionado = setEventoSeleccionado;
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

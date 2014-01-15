/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Deportes;
import Entidades.Empleados;
import Entidades.VigenciasDeportes;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciaDeporteInterface;
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
public class ControlVigenciaDeporte implements Serializable {

    @EJB
    AdministrarVigenciaDeporteInterface administrarVigenciaDeporte;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    ////
    private List<VigenciasDeportes> listVigenciasDeportes;
    private List<VigenciasDeportes> filtrarListVigenciasDeportes;
    private List<Deportes> listDeportes;
    private Deportes deporteSeleccionado;
    private List<Deportes> filtrarListDeportes;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column veFechaInicial, veFechaFinal, veDescripcion, veIndividual, veCIndividual, veGrupal, veCGrupal;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasDeportes> listVigenciaDeporteModificar;
    private boolean guardado;
    //crear VC
    public VigenciasDeportes nuevaVigenciaDeporte;
    private List<VigenciasDeportes> listVigenciaDeporteCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasDeportes> listVigenciaDDeporteBorrar;
    //editar celda
    private VigenciasDeportes editarVigenciaDeporte;
    private int cualCelda, tipoLista;
    //duplicar
    private VigenciasDeportes duplicarVigenciaDeporte;
    private String deporte;
    private boolean permitirIndex;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Empleados empleado;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;

    public ControlVigenciaDeporte() {
        listVigenciasDeportes = null;
        listDeportes = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listVigenciaDDeporteBorrar = new ArrayList<VigenciasDeportes>();
        //crear aficiones
        listVigenciaDeporteCrear = new ArrayList<VigenciasDeportes>();
        k = 0;
        //modificar aficiones
        listVigenciaDeporteModificar = new ArrayList<VigenciasDeportes>();
        //editar
        editarVigenciaDeporte = new VigenciasDeportes();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigenciaDeporte = new VigenciasDeportes();
        nuevaVigenciaDeporte.setDeporte(new Deportes());
        secRegistro = null;
        permitirIndex = true;
        backUpSecRegistro = null;
        empleado = new Empleados();
    }

    public void recibirEmpleado(BigInteger secuencia) {
        listVigenciasDeportes = null;
        listDeportes = null;
        empleado = administrarVigenciaDeporte.empleadoActual(secuencia);
    }

    public void modificarVigenciaDeporte(int indice) {
        if (tipoLista == 0) {
            if (!listVigenciaDeporteCrear.contains(listVigenciasDeportes.get(indice))) {
                if (listVigenciaDeporteModificar.isEmpty()) {
                    listVigenciaDeporteModificar.add(listVigenciasDeportes.get(indice));
                } else if (!listVigenciaDeporteModificar.contains(listVigenciasDeportes.get(indice))) {
                    listVigenciaDeporteModificar.add(listVigenciasDeportes.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        } else {
            if (!listVigenciaDeporteCrear.contains(filtrarListVigenciasDeportes.get(indice))) {

                if (listVigenciaDeporteModificar.isEmpty()) {
                    listVigenciaDeporteModificar.add(filtrarListVigenciasDeportes.get(indice));
                } else if (!listVigenciaDeporteModificar.contains(filtrarListVigenciasDeportes.get(indice))) {
                    listVigenciaDeporteModificar.add(filtrarListVigenciasDeportes.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    public void modificarVigenciaDeporte(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("DEPORTES")) {
            if (tipoLista == 0) {
                listVigenciasDeportes.get(indice).getDeporte().setNombre(deporte);
            } else {
                filtrarListVigenciasDeportes.get(indice).getDeporte().setNombre(deporte);
            }
            for (int i = 0; i < listDeportes.size(); i++) {
                if (listDeportes.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasDeportes.get(indice).setDeporte(listDeportes.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasDeportes.get(indice).setDeporte(listDeportes.get(indiceUnicoElemento));
                }
                listDeportes.clear();
                getListDeportes();
            } else {
                permitirIndex = false;
                context.update("form:DeportesDialogo");
                context.execute("DeportesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVigenciaDeporteCrear.contains(listVigenciasDeportes.get(indice))) {

                    if (listVigenciaDeporteModificar.isEmpty()) {
                        listVigenciaDeporteModificar.add(listVigenciasDeportes.get(indice));
                    } else if (!listVigenciaDeporteModificar.contains(listVigenciasDeportes.get(indice))) {
                        listVigenciaDeporteModificar.add(listVigenciasDeportes.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVigenciaDeporteCrear.contains(filtrarListVigenciasDeportes.get(indice))) {

                    if (listVigenciaDeporteModificar.isEmpty()) {
                        listVigenciaDeporteModificar.add(filtrarListVigenciasDeportes.get(indice));
                    } else if (!listVigenciaDeporteModificar.contains(filtrarListVigenciasDeportes.get(indice))) {
                        listVigenciaDeporteModificar.add(filtrarListVigenciasDeportes.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigenciasDeportes");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("DEPORTES")) {
            if (tipoNuevo == 1) {
                deporte = nuevaVigenciaDeporte.getDeporte().getNombre();
            } else if (tipoNuevo == 2) {
                deporte = duplicarVigenciaDeporte.getDeporte().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("DEPORTES")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaDeporte.getDeporte().setNombre(deporte);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaDeporte.getDeporte().setNombre(deporte);
            }
            for (int i = 0; i < listDeportes.size(); i++) {
                if (listDeportes.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaDeporte.setDeporte(listDeportes.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaVigencias");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaDeporte.setDeporte(listDeportes.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarVigencias");
                }
                listDeportes.clear();
                getListDeportes();
            } else {
                context.update("form:DeportesDialogo");
                context.execute("DeportesDialogo.show()");
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
            if (tipoLista == 0) {
                fechaFin = listVigenciasDeportes.get(index).getFechafinal();
                fechaIni = listVigenciasDeportes.get(index).getFechainicial();
                secRegistro = listVigenciasDeportes.get(index).getSecuencia();
                if (cualCelda == 2) {
                    deporte = listVigenciasDeportes.get(index).getDeporte().getNombre();
                }
            }
            if (tipoLista == 1) {
                fechaFin = filtrarListVigenciasDeportes.get(index).getFechafinal();
                fechaIni = filtrarListVigenciasDeportes.get(index).getFechainicial();
                secRegistro = filtrarListVigenciasDeportes.get(index).getSecuencia();
                if (cualCelda == 2) {
                    deporte = filtrarListVigenciasDeportes.get(index).getDeporte().getNombre();
                }
            }
        }
    }

    public void guardarCambios() {
        if (guardado == false) {
            if (!listVigenciaDDeporteBorrar.isEmpty()) {
                administrarVigenciaDeporte.borrarVigenciasDeportes(listVigenciaDDeporteBorrar);
                listVigenciaDDeporteBorrar.clear();
            }
            if (!listVigenciaDeporteCrear.isEmpty()) {
                administrarVigenciaDeporte.crearVigenciasDeportes(listVigenciaDeporteCrear);
                listVigenciaDeporteCrear.clear();
            }
            if (!listVigenciaDeporteModificar.isEmpty()) {
                administrarVigenciaDeporte.editarVigenciasDeportes(listVigenciaDeporteModificar);
                listVigenciaDeporteModificar.clear();
            }
            listVigenciasDeportes = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasDeportes");
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
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasDeportes");
            bandera = 0;
            filtrarListVigenciasDeportes = null;
            tipoLista = 0;
        }

        listVigenciaDDeporteBorrar.clear();
        listVigenciaDeporteCrear.clear();
        listVigenciaDeporteModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasDeportes = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasDeportes");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciaDeporte = listVigenciasDeportes.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaDeporte = filtrarListVigenciasDeportes.get(index);
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

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasDeportes auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listVigenciasDeportes.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListVigenciasDeportes.get(index);
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
            if (nuevaVigenciaDeporte.getFechafinal() != null) {
                if (nuevaVigenciaDeporte.getFechainicial().after(fechaParametro) && nuevaVigenciaDeporte.getFechainicial().before(nuevaVigenciaDeporte.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                if (nuevaVigenciaDeporte.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVigenciaDeporte.getFechafinal() != null) {
                if (duplicarVigenciaDeporte.getFechainicial().after(fechaParametro) && duplicarVigenciaDeporte.getFechainicial().before(duplicarVigenciaDeporte.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                if (duplicarVigenciaDeporte.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        VigenciasDeportes auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listVigenciasDeportes.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarListVigenciasDeportes.get(i);
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
                modificarVigenciaDeporte(i);
            } else {
                if (tipoLista == 0) {
                    listVigenciasDeportes.get(i).setFechafinal(fechaFin);
                    listVigenciasDeportes.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {
                    filtrarListVigenciasDeportes.get(i).setFechafinal(fechaFin);
                    filtrarListVigenciasDeportes.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciasDeportes");
                context.execute("form:errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listVigenciasDeportes.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {
                filtrarListVigenciasDeportes.get(i).setFechainicial(fechaIni);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasDeportes");
            context.execute("errorRegNew.show()");
        }
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciaReformaLaboral
     */
    public void agregarNuevaVigenciaDeporte() {
        if (nuevaVigenciaDeporte.getFechainicial() != null && nuevaVigenciaDeporte.getDeporte() != null) {
            if (validarFechasRegistro(1) == true) {
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaInicial");
                    veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaFinal");
                    veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veDescripcion");
                    veDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veIndividual");
                    veIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCIndividual");
                    veCIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veGrupal");
                    veGrupal.setFilterStyle("display: none; visibility: hidden;");
                    veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCGrupal");
                    veCGrupal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciasDeportes");
                    bandera = 0;
                    filtrarListVigenciasDeportes = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigenciaDeporte.setSecuencia(l);
                nuevaVigenciaDeporte.setPersona(empleado.getPersona());
                listVigenciaDeporteCrear.add(nuevaVigenciaDeporte);

                listVigenciasDeportes.add(nuevaVigenciaDeporte);
                nuevaVigenciaDeporte = new VigenciasDeportes();
                nuevaVigenciaDeporte.setDeporte(new Deportes());
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciasDeportes");
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
    public void limpiarNuevaVigenciaDeporte() {
        nuevaVigenciaDeporte = new VigenciasDeportes();
        nuevaVigenciaDeporte.setDeporte(new Deportes());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVigenciaDeporteM() {
        if (index >= 0) {
            duplicarVigenciaDeporte = new VigenciasDeportes();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {

                duplicarVigenciaDeporte.setSecuencia(l);
                duplicarVigenciaDeporte.setDeporte(listVigenciasDeportes.get(index).getDeporte());
                duplicarVigenciaDeporte.setFechafinal(listVigenciasDeportes.get(index).getFechafinal());
                duplicarVigenciaDeporte.setFechainicial(listVigenciasDeportes.get(index).getFechainicial());
                duplicarVigenciaDeporte.setPersona(listVigenciasDeportes.get(index).getPersona());
                duplicarVigenciaDeporte.setValorcualitativo(listVigenciasDeportes.get(index).getValorcualitativo());
                duplicarVigenciaDeporte.setValorcualitativogrupo(listVigenciasDeportes.get(index).getValorcualitativogrupo());
                duplicarVigenciaDeporte.setValorcuantitativo(listVigenciasDeportes.get(index).getValorcuantitativo());
                duplicarVigenciaDeporte.setValorcuantitativogrupo(listVigenciasDeportes.get(index).getValorcuantitativogrupo());

            }
            if (tipoLista == 1) {

                duplicarVigenciaDeporte.setSecuencia(l);
                duplicarVigenciaDeporte.setDeporte(filtrarListVigenciasDeportes.get(index).getDeporte());
                duplicarVigenciaDeporte.setFechafinal(filtrarListVigenciasDeportes.get(index).getFechafinal());
                duplicarVigenciaDeporte.setFechainicial(filtrarListVigenciasDeportes.get(index).getFechainicial());
                duplicarVigenciaDeporte.setPersona(filtrarListVigenciasDeportes.get(index).getPersona());
                duplicarVigenciaDeporte.setValorcualitativo(filtrarListVigenciasDeportes.get(index).getValorcualitativo());
                duplicarVigenciaDeporte.setValorcualitativogrupo(filtrarListVigenciasDeportes.get(index).getValorcualitativogrupo());
                duplicarVigenciaDeporte.setValorcuantitativo(filtrarListVigenciasDeportes.get(index).getValorcuantitativo());
                duplicarVigenciaDeporte.setValorcuantitativogrupo(filtrarListVigenciasDeportes.get(index).getValorcuantitativogrupo());

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
        if (duplicarVigenciaDeporte.getFechainicial() != null && duplicarVigenciaDeporte.getDeporte() != null) {
            if (validarFechasRegistro(2) == true) {
                duplicarVigenciaDeporte.setPersona(empleado.getPersona());
                listVigenciasDeportes.add(duplicarVigenciaDeporte);
                listVigenciaDeporteCrear.add(duplicarVigenciaDeporte);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciasDeportes");
                context.execute("DuplicarRegistroVigencias.hide()");
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaInicial");
                    veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaFinal");
                    veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veDescripcion");
                    veDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veIndividual");
                    veIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCIndividual");
                    veCIndividual.setFilterStyle("display: none; visibility: hidden;");
                    veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veGrupal");
                    veGrupal.setFilterStyle("display: none; visibility: hidden;");
                    veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCGrupal");
                    veCGrupal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciasDeportes");
                    bandera = 0;
                    filtrarListVigenciasDeportes = null;
                    tipoLista = 0;
                }
                duplicarVigenciaDeporte = new VigenciasDeportes();
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
        duplicarVigenciaDeporte = new VigenciasDeportes();
        duplicarVigenciaDeporte.setDeporte(new Deportes());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVigenciaDeporte() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVigenciaDeporteModificar.isEmpty() && listVigenciaDeporteModificar.contains(listVigenciasDeportes.get(index))) {
                    int modIndex = listVigenciaDeporteModificar.indexOf(listVigenciasDeportes.get(index));
                    listVigenciaDeporteModificar.remove(modIndex);
                    listVigenciaDDeporteBorrar.add(listVigenciasDeportes.get(index));
                } else if (!listVigenciaDeporteCrear.isEmpty() && listVigenciaDeporteCrear.contains(listVigenciasDeportes.get(index))) {
                    int crearIndex = listVigenciaDeporteCrear.indexOf(listVigenciasDeportes.get(index));
                    listVigenciaDeporteCrear.remove(crearIndex);
                } else {
                    listVigenciaDDeporteBorrar.add(listVigenciasDeportes.get(index));
                }
                listVigenciasDeportes.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVigenciaDeporteModificar.isEmpty() && listVigenciaDeporteModificar.contains(filtrarListVigenciasDeportes.get(index))) {
                    int modIndex = listVigenciaDeporteModificar.indexOf(filtrarListVigenciasDeportes.get(index));
                    listVigenciaDeporteModificar.remove(modIndex);
                    listVigenciaDDeporteBorrar.add(filtrarListVigenciasDeportes.get(index));
                } else if (!listVigenciaDeporteCrear.isEmpty() && listVigenciaDeporteCrear.contains(filtrarListVigenciasDeportes.get(index))) {
                    int crearIndex = listVigenciaDeporteCrear.indexOf(filtrarListVigenciasDeportes.get(index));
                    listVigenciaDeporteCrear.remove(crearIndex);
                } else {
                    listVigenciaDDeporteBorrar.add(filtrarListVigenciasDeportes.get(index));
                }
                int VCIndex = listVigenciasDeportes.indexOf(filtrarListVigenciasDeportes.get(index));
                listVigenciasDeportes.remove(VCIndex);
                filtrarListVigenciasDeportes.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasDeportes");
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

            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaInicial");
            veFechaInicial.setFilterStyle("width: 50px");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaFinal");
            veFechaFinal.setFilterStyle("width: 50px");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veDescripcion");
            veDescripcion.setFilterStyle("width: 100px");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veIndividual");
            veIndividual.setFilterStyle("width: 100px");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCIndividual");
            veCIndividual.setFilterStyle("width: 100px");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veGrupal");
            veGrupal.setFilterStyle("width: 100px");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCGrupal");
            veCGrupal.setFilterStyle("width: 100px");
            RequestContext.getCurrentInstance().update("form:datosVigenciasDeportes");
            bandera = 1;
        } else if (bandera == 1) {
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasDeportes");
            bandera = 0;
            filtrarListVigenciasDeportes = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasDeportes:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasDeportes");
            bandera = 0;
            filtrarListVigenciasDeportes = null;
            tipoLista = 0;
        }

        listVigenciaDDeporteBorrar.clear();
        listVigenciaDeporteCrear.clear();
        listVigenciaDeporteModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasDeportes = null;
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
        context.update("form:DeportesDialogo");
        context.execute("DeportesDialogo.show()");
    }

    //LOVS
    //CIUDAD
    /**
     * Metodo que actualiza la reforma laboral seleccionada
     */
    public void actualizarDeporte() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasDeportes.get(index).setDeporte(deporteSeleccionado);
                if (!listVigenciaDeporteCrear.contains(listVigenciasDeportes.get(index))) {
                    if (listVigenciaDeporteModificar.isEmpty()) {
                        listVigenciaDeporteModificar.add(listVigenciasDeportes.get(index));
                    } else if (!listVigenciaDeporteModificar.contains(listVigenciasDeportes.get(index))) {
                        listVigenciaDeporteModificar.add(listVigenciasDeportes.get(index));
                    }
                }
            } else {
                filtrarListVigenciasDeportes.get(index).setDeporte(deporteSeleccionado);
                if (!listVigenciaDeporteCrear.contains(filtrarListVigenciasDeportes.get(index))) {
                    if (listVigenciaDeporteModificar.isEmpty()) {
                        listVigenciaDeporteModificar.add(filtrarListVigenciasDeportes.get(index));
                    } else if (!listVigenciaDeporteModificar.contains(filtrarListVigenciasDeportes.get(index))) {
                        listVigenciaDeporteModificar.add(filtrarListVigenciasDeportes.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasDeportes");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaDeporte.setDeporte(deporteSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVigencias");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaDeporte.setDeporte(deporteSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigencias");
        }
        filtrarListDeportes = null;
        deporteSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre reforma laboral
     */
    public void cancelarCambioDeporte() {
        filtrarListDeportes = null;
        deporteSeleccionado = null;
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
                context.update("form:DeportesDialogo");
                context.execute("DeportesDialogo.show()");
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
        exporter.export(context, tabla, "VigenciasDeportesPDF", false, false, "UTF-8", null, null);
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
        exporter.export(context, tabla, "VigenciasDeportesXLS", false, false, "UTF-8", null, null);
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
        if (listVigenciasDeportes != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASDEPORTES");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASDEPORTES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    //GETTERS AND SETTERS

    public List<VigenciasDeportes> getListVigenciasDeportes() {
        try {
            if (listVigenciasDeportes == null) {
                listVigenciasDeportes = new ArrayList<VigenciasDeportes>();
                listVigenciasDeportes = administrarVigenciaDeporte.listVigenciasDeportesPersona(empleado.getPersona().getSecuencia());
                return listVigenciasDeportes;
            } else {
                return listVigenciasDeportes;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListVigenciasDeportes : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasDeportes(List<VigenciasDeportes> setListVigenciasDeportes) {
        this.listVigenciasDeportes = setListVigenciasDeportes;
    }

    public List<VigenciasDeportes> getFiltrarListVigenciasDeportes() {
        return filtrarListVigenciasDeportes;
    }

    public void setFiltrarListVigenciasDeportes(List<VigenciasDeportes> setFiltrarListVigenciasDeportes) {
        this.filtrarListVigenciasDeportes = setFiltrarListVigenciasDeportes;
    }

    public VigenciasDeportes getNuevaVigenciaDeporte() {
        return nuevaVigenciaDeporte;
    }

    public void setNuevaVigenciaDeporte(VigenciasDeportes setNuevaVigenciaDeporte) {
        this.nuevaVigenciaDeporte = setNuevaVigenciaDeporte;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<Deportes> getListDeportes() {
        if (listDeportes == null) {
            listDeportes = new ArrayList<Deportes>();
            listDeportes = administrarVigenciaDeporte.listDeportes();
        }
        return listDeportes;
    }

    public void setListDeportes(List<Deportes> setListDeportes) {
        this.listDeportes = setListDeportes;
    }

    public List<Deportes> getFiltrarListDeportes() {
        return filtrarListDeportes;
    }

    public void setFiltrarListDeportes(List<Deportes> setFiltrarListDeportes) {
        this.filtrarListDeportes = setFiltrarListDeportes;
    }

    public VigenciasDeportes getEditarVigenciaDeporte() {
        return editarVigenciaDeporte;
    }

    public void setEditarVigenciaDeporte(VigenciasDeportes setEditarVigenciaDeporte) {
        this.editarVigenciaDeporte = setEditarVigenciaDeporte;
    }

    public VigenciasDeportes getDuplicarVigenciaDeporte() {
        return duplicarVigenciaDeporte;
    }

    public void setDuplicarVigenciaDeporte(VigenciasDeportes setDuplicarVigenciaDeporte) {
        this.duplicarVigenciaDeporte = setDuplicarVigenciaDeporte;
    }

    public Deportes getDeporteSeleccionado() {
        return deporteSeleccionado;
    }

    public void setDeporteSeleccionado(Deportes setDeporteSeleccionado) {
        this.deporteSeleccionado = setDeporteSeleccionado;
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

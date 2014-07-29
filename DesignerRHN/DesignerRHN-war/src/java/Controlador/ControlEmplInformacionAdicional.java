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
    private InformacionesAdicionales informacionTablaSeleccionada;

    private List<GruposInfAdicionales> listGruposInfAdicional;
    private GruposInfAdicionales grupoSelecionado;
    private List<GruposInfAdicionales> filtrarListGruposInfAdicional;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column infoAdFechaInicial, infoAdFechaFinal, infoAdGrupo, infoAdCaracter, infoAdNumerico, infoAdFecha, infoAdObservacion;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<InformacionesAdicionales> listInfoAdicionalModificar;
    private boolean guardado;
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
    //duplicar
    private InformacionesAdicionales duplicarInfoAdicional;
    //String Variables AutoCompletar
    private String grupo;
    //Boolean AutoCompletar
    private boolean permitirIndex;
    private BigInteger backUpSecRegistro;
    private BigInteger secRegistro;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;
    //
    private String altoTabla;
    private String infoRegistro;
    private String infoRegistroGrupo;

    public ControlEmplInformacionAdicional() {
        altoTabla = "310";
        secRegistro = null;
        grupoSelecionado = new GruposInfAdicionales();
        backUpSecRegistro = null;
        listInformacionAdicional = null;
        listGruposInfAdicional = null;
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
        cualCelda = -1;
        tipoLista = 0;
        //guardar 
        guardado = true;
        //Crear VC
        nuevaInfoAdicional = new InformacionesAdicionales();
        nuevaInfoAdicional.setGrupo(new GruposInfAdicionales());
        permitirIndex = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEmplInformacionAdicional.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger empl) {
        listInformacionAdicional = null;
        empleado = administrarEmplInformacionAdicional.empleadoActual(empl);
        getListInformacionAdicional();
        if (listInformacionAdicional != null) {
            infoRegistro = "Cantidad de registros : " + listInformacionAdicional.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
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
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    public void modificarInfoAd(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPO")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                if (tipoLista == 0) {
                    listInformacionAdicional.get(indice).setGrupo(new GruposInfAdicionales());
                } else {
                    filtrarListInformacionAdicional.get(indice).setGrupo(new GruposInfAdicionales());
                }
                coincidencias = 1;
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosInfoAdEmpleado");
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            InformacionesAdicionales auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listInformacionAdicional.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListInformacionAdicional.get(index);
            }
            if (auxiliar.getFechainicial().after(fechaParametro) && auxiliar.getFechainicial().before(auxiliar.getFechafinal())) {
                retorno = true;
            } else {
                retorno = false;
            }

        }
        if (i == 1) {
            if (nuevaInfoAdicional.getFechainicial().after(fechaParametro) && nuevaInfoAdicional.getFechainicial().before(nuevaInfoAdicional.getFechafinal())) {
                retorno = true;
            } else {
                retorno = false;
            }

        }
        if (i == 2) {
            if (duplicarInfoAdicional.getFechainicial().after(fechaParametro) && duplicarInfoAdicional.getFechainicial().before(duplicarInfoAdicional.getFechafinal())) {
                retorno = true;
            } else {
                retorno = false;
            }

        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        InformacionesAdicionales auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listInformacionAdicional.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarListInformacionAdicional.get(i);
        }
        if (auxiliar.getFechainicial() != null && auxiliar.getFechafinal() != null) {
            boolean retorno = false;
            retorno = validarFechasRegistro(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarInfoAd(i);
            } else {
                if (tipoLista == 0) {
                    listInformacionAdicional.get(i).setFechafinal(fechaFin);
                    listInformacionAdicional.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {
                    filtrarListInformacionAdicional.get(i).setFechafinal(fechaFin);
                    filtrarListInformacionAdicional.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosInfoAdEmpleado");
                context.execute("form:errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listInformacionAdicional.get(i).setFechafinal(fechaFin);
                listInformacionAdicional.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {
                filtrarListInformacionAdicional.get(i).setFechafinal(fechaFin);
                filtrarListInformacionAdicional.get(i).setFechainicial(fechaIni);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosInfoAdEmpleado");
            context.execute("errorRegNew.show()");
        }
    }

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
            if (!valorConfirmar.isEmpty()) {
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
                        context.update("formularioDialogos:nuevaGrupo");
                    } else if (tipoNuevo == 2) {
                        duplicarInfoAdicional.setGrupo(listGruposInfAdicional.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarGrupo");
                    }
                    listGruposInfAdicional.clear();
                    getListGruposInfAdicional();
                } else {
                    context.update("form:GrupoDialogo");
                    context.execute("GrupoDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaGrupo");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarGrupo");
                    }
                }
            } else {
                if (tipoNuevo == 1) {
                    nuevaInfoAdicional.setGrupo(new GruposInfAdicionales());
                    context.update("formularioDialogos:nuevaGrupo");
                } else if (tipoNuevo == 2) {
                    duplicarInfoAdicional.setGrupo(new GruposInfAdicionales());
                    context.update("formularioDialogos:duplicarGrupo");
                }
            }
        }
    }

    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listInformacionAdicional.get(index).getSecuencia();
                fechaFin = listInformacionAdicional.get(index).getFechafinal();
                fechaIni = listInformacionAdicional.get(index).getFechainicial();
                if (cualCelda == 2) {
                    grupo = listInformacionAdicional.get(index).getGrupo().getDescripcion();
                }
            }
            if (tipoLista == 1) {
                secRegistro = filtrarListInformacionAdicional.get(index).getSecuencia();
                fechaFin = filtrarListInformacionAdicional.get(index).getFechafinal();
                fechaIni = filtrarListInformacionAdicional.get(index).getFechainicial();
                if (cualCelda == 2) {
                    grupo = filtrarListInformacionAdicional.get(index).getGrupo().getDescripcion();
                }
            }
        }
    }

    public void guardarCambiosInfoAd() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
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
                getListInformacionAdicional();
                if (listInformacionAdicional != null) {
                    infoRegistro = "Cantidad de registros : " + listInformacionAdicional.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                context.update("form:informacionRegistro");
                context.update("form:datosInfoAdEmpleado");
                guardado = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                k = 0;
                index = -1;
                secRegistro = null;
                context.update("form:informacionRegistro");
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con Éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambios : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }
    //CANCELAR MODIFICACIONES

    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            altoTabla = "310";
            infoAdFechaInicial = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaInicial");
            infoAdFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            infoAdFechaFinal = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaFinal");
            infoAdFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            infoAdGrupo = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdGrupo");
            infoAdGrupo.setFilterStyle("display: none; visibility: hidden;");
            infoAdCaracter = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdCaracter");
            infoAdCaracter.setFilterStyle("display: none; visibility: hidden;");
            ////
            infoAdNumerico = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdNumerico");
            infoAdNumerico.setFilterStyle("display: none; visibility: hidden;");
            infoAdFecha = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFecha");
            infoAdFecha.setFilterStyle("display: none; visibility: hidden;");
            infoAdObservacion = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdObservacion");
            infoAdObservacion.setFilterStyle("display: none; visibility: hidden;");
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
        getListInformacionAdicional();
        if (listInformacionAdicional != null) {
            infoRegistro = "Cantidad de registros : " + listInformacionAdicional.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        context.update("form:informacionRegistro");
        context.update("form:datosInfoAdEmpleado");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarInfoAdicional = listInformacionAdicional.get(index);
            }
            if (tipoLista == 1) {
                editarInfoAdicional = filtrarListInformacionAdicional.get(index);
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
                context.update("formularioDialogos:editarGrupoD");
                context.execute("editarGrupoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarCaracterD");
                context.execute("editarCaracterD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarNumericoD");
                context.execute("editarNumericoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarFechaD");
                context.execute("editarFechaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarObservacionD");
                context.execute("editarObservacionD.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevaInfoAd() {
        if (nuevaInfoAdicional.getFechafinal() != null && nuevaInfoAdicional.getFechainicial() != null && nuevaInfoAdicional.getTipodato() != null) {
            if (validarFechasRegistro(1) == true) {
                FacesContext c = FacesContext.getCurrentInstance();
                if (bandera == 1) {
                    altoTabla = "310";
                    //CERRAR FILTRADO
                    infoAdFechaInicial = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaInicial");
                    infoAdFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    infoAdFechaFinal = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaFinal");
                    infoAdFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    infoAdGrupo = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdGrupo");
                    infoAdGrupo.setFilterStyle("display: none; visibility: hidden;");
                    infoAdCaracter = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdCaracter");
                    infoAdCaracter.setFilterStyle("display: none; visibility: hidden;");
                    ////
                    infoAdNumerico = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdNumerico");
                    infoAdNumerico.setFilterStyle("display: none; visibility: hidden;");
                    infoAdFecha = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFecha");
                    infoAdFecha.setFilterStyle("display: none; visibility: hidden;");
                    infoAdObservacion = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdObservacion");
                    infoAdObservacion.setFilterStyle("display: none; visibility: hidden;");
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

                infoRegistro = "Cantidad de registros : " + listInformacionAdicional.size();

                context.update("form:informacionRegistro");
                context.update("form:datosInfoAdEmpleado");
                context.execute("NuevoRegistroInfoAd.hide()");
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

    public void limpiarNuevaInfoAd() {
        nuevaInfoAdicional = new InformacionesAdicionales();
        nuevaInfoAdicional.setGrupo(new GruposInfAdicionales());
        index = -1;
        secRegistro = null;
    }

    public void duplicarInfoAd() {
        if (index >= 0) {
            duplicarInfoAdicional = new InformacionesAdicionales();

            if (tipoLista == 0) {
                duplicarInfoAdicional.setEmpleado(listInformacionAdicional.get(index).getEmpleado());
                duplicarInfoAdicional.setFechainicial(listInformacionAdicional.get(index).getFechainicial());
                duplicarInfoAdicional.setFechafinal(listInformacionAdicional.get(index).getFechafinal());
                duplicarInfoAdicional.setGrupo(listInformacionAdicional.get(index).getGrupo());
                duplicarInfoAdicional.setTipodato(listInformacionAdicional.get(index).getTipodato());
                duplicarInfoAdicional.setResultadocaracter(listInformacionAdicional.get(index).getResultadocaracter());
                duplicarInfoAdicional.setResultadonumerico(listInformacionAdicional.get(index).getResultadonumerico());
                duplicarInfoAdicional.setResultadofecha(listInformacionAdicional.get(index).getResultadofecha());
                duplicarInfoAdicional.setDescripcion(listInformacionAdicional.get(index).getDescripcion());
                duplicarInfoAdicional.setTipoDatoCompleto(listInformacionAdicional.get(index).getTipoDatoCompleto());
            }
            if (tipoLista == 1) {
                duplicarInfoAdicional.setEmpleado(filtrarListInformacionAdicional.get(index).getEmpleado());
                duplicarInfoAdicional.setFechainicial(filtrarListInformacionAdicional.get(index).getFechainicial());
                duplicarInfoAdicional.setFechafinal(filtrarListInformacionAdicional.get(index).getFechafinal());
                duplicarInfoAdicional.setGrupo(filtrarListInformacionAdicional.get(index).getGrupo());
                duplicarInfoAdicional.setTipodato(filtrarListInformacionAdicional.get(index).getTipodato());
                duplicarInfoAdicional.setResultadocaracter(filtrarListInformacionAdicional.get(index).getResultadocaracter());
                duplicarInfoAdicional.setResultadonumerico(filtrarListInformacionAdicional.get(index).getResultadonumerico());
                duplicarInfoAdicional.setResultadofecha(filtrarListInformacionAdicional.get(index).getResultadofecha());
                duplicarInfoAdicional.setDescripcion(filtrarListInformacionAdicional.get(index).getDescripcion());
                duplicarInfoAdicional.setTipoDatoCompleto(filtrarListInformacionAdicional.get(index).getTipoDatoCompleto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarInfoAd");
            context.execute("DuplicarRegistroInfoAd.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        if (duplicarInfoAdicional.getFechafinal() != null && duplicarInfoAdicional.getFechainicial() != null && duplicarInfoAdicional.getTipodato() != null) {
            if (validarFechasRegistro(2) == true) {
                k++;
                l = BigInteger.valueOf(k);
                duplicarInfoAdicional.setSecuencia(l);
                listInformacionAdicional.add(duplicarInfoAdicional);
                listInfoAdicionalCrear.add(duplicarInfoAdicional);
                RequestContext context = RequestContext.getCurrentInstance();

                infoRegistro = "Cantidad de registros : " + listInformacionAdicional.size();

                context.update("form:informacionRegistro");
                context.update("form:datosInfoAdEmpleado");
                context.execute("DuplicarRegistroInfoAd.hide()");
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                FacesContext c = FacesContext.getCurrentInstance();
                if (bandera == 1) {
                    altoTabla = "310";
                    //CERRAR FILTRADO
                    infoAdFechaInicial = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaInicial");
                    infoAdFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    infoAdFechaFinal = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaFinal");
                    infoAdFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    infoAdGrupo = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdGrupo");
                    infoAdGrupo.setFilterStyle("display: none; visibility: hidden;");
                    infoAdCaracter = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdCaracter");
                    infoAdCaracter.setFilterStyle("display: none; visibility: hidden;");
                    ////
                    infoAdNumerico = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdNumerico");
                    infoAdNumerico.setFilterStyle("display: none; visibility: hidden;");
                    infoAdFecha = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFecha");
                    infoAdFecha.setFilterStyle("display: none; visibility: hidden;");
                    infoAdObservacion = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdObservacion");
                    infoAdObservacion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
                    bandera = 0;
                    filtrarListInformacionAdicional = null;
                    tipoLista = 0;
                }
                duplicarInfoAdicional = new InformacionesAdicionales();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }

    public void limpiarduplicarInfoAd() {
        duplicarInfoAdicional = new InformacionesAdicionales();
        duplicarInfoAdicional.setGrupo(new GruposInfAdicionales());
    }

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

            infoRegistro = "Cantidad de registros : " + listInformacionAdicional.size();

            context.update("form:informacionRegistro");
            context.update("form:datosInfoAdEmpleado");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "288";
            infoAdFechaInicial = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaInicial");
            infoAdFechaInicial.setFilterStyle("width: 60px");
            infoAdFechaFinal = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaFinal");
            infoAdFechaFinal.setFilterStyle("width: 60px");
            infoAdGrupo = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdGrupo");
            infoAdGrupo.setFilterStyle("width: 80px");
            infoAdCaracter = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdCaracter");
            infoAdCaracter.setFilterStyle("width: 80px");
            ////
            infoAdNumerico = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdNumerico");
            infoAdNumerico.setFilterStyle("width: 60px");
            infoAdFecha = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFecha");
            infoAdFecha.setFilterStyle("width: 60px");
            infoAdObservacion = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdObservacion");
            infoAdObservacion.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "310";
            infoAdFechaInicial = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaInicial");
            infoAdFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            infoAdFechaFinal = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaFinal");
            infoAdFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            infoAdGrupo = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdGrupo");
            infoAdGrupo.setFilterStyle("display: none; visibility: hidden;");
            infoAdCaracter = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdCaracter");
            infoAdCaracter.setFilterStyle("display: none; visibility: hidden;");
            ////
            infoAdNumerico = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdNumerico");
            infoAdNumerico.setFilterStyle("display: none; visibility: hidden;");
            infoAdFecha = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFecha");
            infoAdFecha.setFilterStyle("display: none; visibility: hidden;");
            infoAdObservacion = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdObservacion");
            infoAdObservacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 0;
            filtrarListInformacionAdicional = null;
            tipoLista = 0;
        }
    }

    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "310";
            infoAdFechaInicial = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaInicial");
            infoAdFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            infoAdFechaFinal = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFechaFinal");
            infoAdFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            infoAdGrupo = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdGrupo");
            infoAdGrupo.setFilterStyle("display: none; visibility: hidden;");
            infoAdCaracter = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdCaracter");
            infoAdCaracter.setFilterStyle("display: none; visibility: hidden;");
            ////
            infoAdNumerico = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdNumerico");
            infoAdNumerico.setFilterStyle("display: none; visibility: hidden;");
            infoAdFecha = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdFecha");
            infoAdFecha.setFilterStyle("display: none; visibility: hidden;");
            infoAdObservacion = (Column) c.getViewRoot().findComponent("form:datosInfoAdEmpleado:infoAdObservacion");
            infoAdObservacion.setFilterStyle("display: none; visibility: hidden;");
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

    public void asignarIndex(Integer indice, int list, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }

        if (list == 0) {
            context.update("form:GrupoDialogo");
            context.execute("GrupoDialogo.show()");
        }
    }

    public void actualizarGrupo() {
        RequestContext context = RequestContext.getCurrentInstance();
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaInfoAdicional.setGrupo(grupoSelecionado);
            context.update("formularioDialogos:nuevaGrupo");
        } else if (tipoActualizacion == 2) {
            duplicarInfoAdicional.setGrupo(grupoSelecionado);
            context.update("formularioDialogos:duplicarGrupo");
        }
        filtrarListGruposInfAdicional = null;
        grupoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:GrupoDialogo");
        context.update("form:lovGrupo");
        context.update("form:aceptarG");
        context.reset("form:lovGrupo:globalFilter");
        context.execute("GrupoDialogo.hide()");
    }

    public void cancelarCambioGrupo() {
        filtrarListGruposInfAdicional = null;
        grupoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:GrupoDialogo");
                context.execute("GrupoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIAEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "InformacionAdicionalPDF", false, false, "UTF-8", null, null);
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIAEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "InformacionAdicionalXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros : " + filtrarListInformacionAdicional.size();
        context.update("form:informacionRegistro");
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

    public List<InformacionesAdicionales> getListInformacionAdicional() {
        try {
            if (listInformacionAdicional == null) {
                if (empleado.getSecuencia() != null) {
                    listInformacionAdicional = administrarEmplInformacionAdicional.listInformacionesAdicionalesEmpleado(empleado.getSecuencia());
                    if (listInformacionAdicional != null) {
                        for (int i = 0; i < listInformacionAdicional.size(); i++) {
                            if (listInformacionAdicional.get(i).getGrupo() == null) {
                                listInformacionAdicional.get(i).setGrupo(new GruposInfAdicionales());
                            }
                        }
                    }
                }

            }
            return listInformacionAdicional;

        } catch (Exception e) {
            System.out.println("Error...!! getListInformacionAdicional : " + e.toString());
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
        listGruposInfAdicional = administrarEmplInformacionAdicional.listGruposInfAdicionales();
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

    public void setGrupoSelecionado(GruposInfAdicionales selecionado) {
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

    public InformacionesAdicionales getInformacionTablaSeleccionada() {
        getListInformacionAdicional();
        if (listInformacionAdicional != null) {
            int tam = listInformacionAdicional.size();
            if (tam > 0) {
                informacionTablaSeleccionada = listInformacionAdicional.get(0);
            }
        }
        return informacionTablaSeleccionada;
    }

    public void setInformacionTablaSeleccionada(InformacionesAdicionales informacionTablaSeleccionada) {
        this.informacionTablaSeleccionada = informacionTablaSeleccionada;
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

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroGrupo() {
        getListGruposInfAdicional();
        if (listGruposInfAdicional != null) {
            infoRegistroGrupo = "Cantidad de registros : " + listGruposInfAdicional.size();
        } else {
            infoRegistroGrupo = "Cantidad de registros : 0";
        }
        return infoRegistroGrupo;
    }

    public void setInfoRegistroGrupo(String infoRegistroGrupo) {
        this.infoRegistroGrupo = infoRegistroGrupo;
    }

}

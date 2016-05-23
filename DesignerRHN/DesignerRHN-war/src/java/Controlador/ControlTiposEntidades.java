/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Grupostiposentidades;
import Entidades.TiposEntidades;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposEntidadesInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class ControlTiposEntidades implements Serializable {

    @EJB
    AdministrarTiposEntidadesInterface administrarTipoEntidad;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposEntidades> listTiposEntidades;
    private List<TiposEntidades> filtrarTiposEntidades;
    private List<TiposEntidades> crearTiposEntidades;
    private List<TiposEntidades> modificarTiposEntidades;
    private List<TiposEntidades> borrarTiposEntidades;
    private TiposEntidades nuevoTipoEntidad;
    private TiposEntidades duplicarTipoEntidad;
    private TiposEntidades editarTipoEntidad;
    private TiposEntidades tipoEntidadSeleccionada;
    //lov gruposTiposEntidades
    private Grupostiposentidades grupoTESeleccionada;
    private List<Grupostiposentidades> filtradoGruposTiposEntidades;
    private List<Grupostiposentidades> listaGruposTiposEntidades;
    //otros
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    String grupoAsociadoAutoCompletar;
    String nuevogrupoAsociadoAutoCompletar;
    //RASTRO
    private Column codigo, nombre, grupoAsociado;
    //borrado
    private BigInteger borrado;
    private BigInteger borradoFCE;
    private int registrosBorrados;
    private int tamano;
    private String infoRegistro;
//
    private DataTable tablaC;
    //
    private boolean activarLOV;
    //
    private Short backUpCodigo;
    private String backUpDescripcion;
    //
    private String mensajeValidacion;

    public ControlTiposEntidades() {
        listTiposEntidades = null;
        crearTiposEntidades = new ArrayList<TiposEntidades>();
        modificarTiposEntidades = new ArrayList<TiposEntidades>();
        borrarTiposEntidades = new ArrayList<TiposEntidades>();
        listaGruposTiposEntidades = null;
        permitirIndex = true;
        editarTipoEntidad = new TiposEntidades();
        nuevoTipoEntidad = new TiposEntidades();
        nuevoTipoEntidad.setGrupo(new Grupostiposentidades());
        guardado = true;
        tamano = 270;
        aceptar = true;
        tipoEntidadSeleccionada = null;
        activarLOV = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTipoEntidad.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    private String paginaAnterior;

    public void recibirPagina(String pagina) {
        paginaAnterior = pagina;
        getListTiposEntidades();
        if (listTiposEntidades != null) {
            if (!listTiposEntidades.isEmpty()) {
                tipoEntidadSeleccionada = listTiposEntidades.get(0);
                modificarInfoRegistro(listTiposEntidades.size());
            }
        } else {
            modificarInfoRegistro(0);
        }
    }

    public String redirigirPaginaAnterior() {
        return paginaAnterior;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposEntidades.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            //infoRegistro = "Cantidad de registros: " + filtrarTiposEntidades.size();
            modificarInfoRegistro(filtrarTiposEntidades.size());
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposEntidades eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void eventoFiltrarGrupo() {
        RequestContext context = RequestContext.getCurrentInstance();
        modificarInfoRegistroGrupo(filtradoGruposTiposEntidades.size());
        context.update("form:infoRegistroGruposTiposEntidades");
    }

    public void cambiarIndice(TiposEntidades te, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex == true) {
            tipoEntidadSeleccionada = te;
            cualCelda = celda;
            if (cualCelda == 0) {
                activarLOV = true;
                backUpCodigo = tipoEntidadSeleccionada.getCodigo();
            }
            if (cualCelda == 1) {
                activarLOV = true;
                backUpDescripcion = tipoEntidadSeleccionada.getNombre();
            }
            if (cualCelda == 2) {
                activarLOV = false;
                grupoAsociadoAutoCompletar = tipoEntidadSeleccionada.getGrupo().getNombre();
            }
        } else {
            context.execute("datosTipoEntidad.selectRow(" + te + ", false); datosTipoEntidad.unselectAllRows()");
        }
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    /**
     *
     * @param indice
     * @param LND
     * @param dig muestra el dialogo respectivo
     */
    public void asignarIndex(TiposEntidades te, int LND, int dig) {
        tipoEntidadSeleccionada = te;
        RequestContext context = RequestContext.getCurrentInstance();
        activarLOV = false;
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        System.out.println("dig: " + dig);

        if (dig == 2) {
            modificarInfoRegistroGrupo(listaGruposTiposEntidades.size());
            grupoTESeleccionada = null;
            context.update("form:gruposTiposEntidadesDialogo");
            context.execute("gruposTiposEntidadesDialogo.show()");
            dig = -1;
        }
        RequestContext.getCurrentInstance().update("form:listaValores");
        tipoActualizacion = 0;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
            RequestContext context = RequestContext.getCurrentInstance();
        if (tipoEntidadSeleccionada != null) {
            if (cualCelda == 2) {
                grupoTESeleccionada = null;
                modificarInfoRegistroGrupo(listaGruposTiposEntidades.size());
                context.update("form:gruposTiposEntidadesDialogo");
                context.execute("gruposTiposEntidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }else {
            context.execute("seleccionarRegistro.show()");
        }
    }
// tipo Entidad

    public void actualizarGrupoTipoEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            tipoEntidadSeleccionada.setGrupo(grupoTESeleccionada);

            if (!crearTiposEntidades.contains(tipoEntidadSeleccionada)) {
                if (modificarTiposEntidades.isEmpty()) {
                    modificarTiposEntidades.add(tipoEntidadSeleccionada);
                } else if (!modificarTiposEntidades.contains(tipoEntidadSeleccionada)) {
                    modificarTiposEntidades.add(tipoEntidadSeleccionada);
                }
            }

            if (guardado) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosTipoEntidad");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            nuevoTipoEntidad.setGrupo(grupoTESeleccionada);
            context.update("formularioDialogos:nuevoTipoEntidad");
        } else if (tipoActualizacion == 2) {
            duplicarTipoEntidad.setGrupo(grupoTESeleccionada);
            context.update("formularioDialogos:duplicarTiposEntidades");
        }
        filtradoGruposTiposEntidades = null;
        grupoTESeleccionada = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("form:lovGruposTiposEntidades:globalFilter");
        context.execute("lovGruposTiposEntidades.clearFilters()");
        context.execute("gruposTiposEntidadesDialogo.hide()");
        //context.update("form:lovGruposTiposEntidades");
    }

    public void cancelarCambioGrupoTipoEntidad() {
        filtradoGruposTiposEntidades = null;
        grupoTESeleccionada = null;
        aceptar = true;
        tipoEntidadSeleccionada = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovGruposTiposEntidades:globalFilter");
        context.execute("lovGruposTiposEntidades.clearFilters()");
        context.execute("gruposTiposEntidadesDialogo.hide()");
    }
//------------------------------------------------------------------------------

    public void modificarTipoEntidad(TiposEntidades te, String confirmarCambio, String valorConfirmar) {
        tipoEntidadSeleccionada = te;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            activarLOV = true;
            if (!crearTiposEntidades.contains(tipoEntidadSeleccionada)) {
                if (tipoEntidadSeleccionada.getCodigo() == a) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                    tipoEntidadSeleccionada.setCodigo(backUpCodigo);
                } else {
                    for (int j = 0; j < listTiposEntidades.size(); j++) {
                        if (j != listTiposEntidades.indexOf(tipoEntidadSeleccionada)) {
                            if (tipoEntidadSeleccionada.getCodigo().equals(listTiposEntidades.get(j).getCodigo())) {
                                contador++;
                            }
                        }
                    }
                    if (contador > 0) {
                        mensajeValidacion = "CODIGOS REPETIDOS";
                        banderita = false;
                        tipoEntidadSeleccionada.setCodigo(backUpCodigo);
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                }

                if (tipoEntidadSeleccionada.getNombre().isEmpty()) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                    tipoEntidadSeleccionada.setNombre(backUpDescripcion);
                }
                if (tipoEntidadSeleccionada.getNombre() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    tipoEntidadSeleccionada.setNombre(backUpDescripcion);
                    banderita = false;
                }

                if (banderita == true) {
                    if (modificarTiposEntidades.isEmpty()) {
                        modificarTiposEntidades.add(tipoEntidadSeleccionada);
                    } else if (!modificarTiposEntidades.contains(tipoEntidadSeleccionada)) {
                        modificarTiposEntidades.add(tipoEntidadSeleccionada);
                    }
                    if (guardado) {
                        guardado = false;
                    }

                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                }
            }

            context.update("form:datosTipoEntidad");
        } else if (confirmarCambio.equalsIgnoreCase("GRUPOSTIPOSENTIDADES")) {
            activarLOV = false;
            tipoEntidadSeleccionada.getGrupo().setNombre(grupoAsociadoAutoCompletar);

            for (int i = 0; i < listaGruposTiposEntidades.size(); i++) {
                if (listaGruposTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                tipoEntidadSeleccionada.setGrupo(listaGruposTiposEntidades.get(indiceUnicoElemento));
                listaGruposTiposEntidades.clear();
                getListaGruposTiposEntidades();
            } else {
                permitirIndex = false;
                context.update("form:gruposTiposEntidadesDialogo");
                context.execute("gruposTiposEntidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        context.update("form:datosTipoEntidad");
        context.update("form:ACEPTAR");

    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("GRUPOSTIPOSENTIDADES")) {
            if (tipoNuevo == 1) {
                nuevogrupoAsociadoAutoCompletar = nuevoTipoEntidad.getGrupo().getNombre();
            } else if (tipoNuevo == 2) {
                nuevogrupoAsociadoAutoCompletar = nuevoTipoEntidad.getGrupo().getNombre();
            }

        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPOSTIPOSENTIDADES")) {
            if (tipoNuevo == 1) {
                nuevoTipoEntidad.getGrupo().setNombre(nuevogrupoAsociadoAutoCompletar);
            } else if (tipoNuevo == 2) {
                duplicarTipoEntidad.getGrupo().setNombre(nuevogrupoAsociadoAutoCompletar);
            }
            for (int i = 0; i < listaGruposTiposEntidades.size(); i++) {
                if (listaGruposTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTipoEntidad.setGrupo(listaGruposTiposEntidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoGrupoAsociado");
                } else if (tipoNuevo == 2) {
                    nuevoTipoEntidad.setGrupo(listaGruposTiposEntidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarGrupoAsociado");
                }
                listaGruposTiposEntidades.clear();
                listaGruposTiposEntidades = null;
                getListaGruposTiposEntidades();
            } else {
                context.update("form:gruposTiposEntidadesDialogo");
                context.execute("gruposTiposEntidadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoGrupoAsociado");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarGrupoAsociado");
                }
            }
        }

    }

    public void asignarVariableGrupoTipoEntidadNueva(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:gruposTiposEntidadesDialogo");
        context.execute("gruposTiposEntidadesDialogo.show()");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosTipoEntidad:codigo");
            codigo.setFilterStyle("width: 200px");
            nombre = (Column) c.getViewRoot().findComponent("form:datosTipoEntidad:nombre");
            nombre.setFilterStyle("width: 270px");
            grupoAsociado = (Column) c.getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
            grupoAsociado.setFilterStyle("width: 270px");
            RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            cerrarFiltrado();
        }
    }

    public void cancelarModificacion() {
        cerrarFiltrado();
        activarLOV = true;
        borrarTiposEntidades.clear();
        crearTiposEntidades.clear();
        modificarTiposEntidades.clear();
        tipoEntidadSeleccionada = null;
        k = 0;
        listTiposEntidades = null;
        guardado = true;
        permitirIndex = true;
        getListTiposEntidades();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTiposEntidades != null) {
            modificarInfoRegistro(listTiposEntidades.size());
        } else {
            modificarInfoRegistro(0);
        }
        context.update("form:informacionRegistro");
        context.update("form:datosTipoEntidad");
        context.update("form:ACEPTAR");
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void salir() {
        cerrarFiltrado();
        activarLOV = true;
        borrarTiposEntidades.clear();
        crearTiposEntidades.clear();
        modificarTiposEntidades.clear();
        tipoEntidadSeleccionada = null;
        k = 0;
        listTiposEntidades = null;
        guardado = true;
    }

    public void cerrarFiltrado() {
        FacesContext c = FacesContext.getCurrentInstance();
        tamano = 270;
        //CERRAR FILTRADO
        codigo = (Column) c.getViewRoot().findComponent("form:datosTipoEntidad:codigo");
        codigo.setFilterStyle("display: none; visibility: hidden;");
        nombre = (Column) c.getViewRoot().findComponent("form:datosTipoEntidad:nombre");
        nombre.setFilterStyle("display: none; visibility: hidden;");
        grupoAsociado = (Column) c.getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
        grupoAsociado.setFilterStyle("display: none; visibility: hidden;");
        RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
        bandera = 0;
        filtrarTiposEntidades = null;
        tipoLista = 0;
    }

    public void agregarNuevoTipoEntidad() {
        System.out.println("Agregar nueva vigencia");
        int contador = 0;
        int duplicados = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoTipoEntidad.getCodigo() == null) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("tamaño lista de tipos de entidades en agregar  : " + listTiposEntidades.size());
            System.out.println("codigo en tipo entidad: " + nuevoTipoEntidad.getCodigo());

            for (int x = 0; x < listTiposEntidades.size(); x++) {
                if (listTiposEntidades.get(x).getCodigo().equals(nuevoTipoEntidad.getCodigo())) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoTipoEntidad.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoTipoEntidad.getGrupo().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   *Grupo Tipo Entidad \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;
            duplicados = 0;

        }
        if (contador == 3) {
            if (bandera == 1) {
                cerrarFiltrado();
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoEntidad.setSecuencia(l);

            crearTiposEntidades.add(nuevoTipoEntidad);
            listTiposEntidades.add(nuevoTipoEntidad);
            tipoEntidadSeleccionada = listTiposEntidades.get(listTiposEntidades.indexOf(nuevoTipoEntidad));
            nuevoTipoEntidad = new TiposEntidades();
            nuevoTipoEntidad.setGrupo(new Grupostiposentidades());

            context.update("form:datosTipoEntidad");
            modificarInfoRegistro(listTiposEntidades.size());
            context.update("form:informacionRegistro");

            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("nuevoRegistroTipoEntidad.hide()");
        } else {
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTipoEntidad() {
        System.out.println("LimpiarNuevoTipoEntidad");
        nuevoTipoEntidad = new TiposEntidades();
        nuevoTipoEntidad.setGrupo(new Grupostiposentidades());
        tipoEntidadSeleccionada = null;

    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoEntidadSeleccionada != null) {
            editarTipoEntidad = tipoEntidadSeleccionada;
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editNombre");
                context.execute("editNombre.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editGrupoAsociado");
                context.execute("editGrupoAsociado.show()");
                cualCelda = -1;
            }

        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void cargarGruposTiposEntidadesNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:gruposTiposEntidadesDialogo");
            context.execute("gruposTiposEntidadesDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:gruposTiposEntidadesDialogo");
            context.execute("gruposTiposEntidadesDialogo.show()");
        }
    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            borrado = administrarTipoEntidad.contarVigenciasAfiliacionesTipoEntidad(tipoEntidadSeleccionada.getSecuencia());
            borradoFCE = administrarTipoEntidad.contarFormulasContratosEntidadesTipoEntidad(tipoEntidadSeleccionada.getSecuencia());

            if (borrado.equals(new BigInteger("0")) && borradoFCE.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarTiposEntidades();
            } else {
                System.out.println("VERIFICARBORRADO borrado : " + borrado);
                System.out.println("VERIFICARBORRADO borradoFCE : " + borradoFCE);

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                //tipoEntidadSeleccionada = null;
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposEntidades verificarBorrado ERROR " + e);
        }
    }

    public void borrarTiposEntidades() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (tipoEntidadSeleccionada != null) {
            System.out.println("Entro a borrarTiposEntidades");
            if (!modificarTiposEntidades.isEmpty() && modificarTiposEntidades.contains(tipoEntidadSeleccionada)) {
                int modIndex = modificarTiposEntidades.indexOf(tipoEntidadSeleccionada);
                modificarTiposEntidades.remove(modIndex);
                borrarTiposEntidades.add(tipoEntidadSeleccionada);
            } else if (!crearTiposEntidades.isEmpty() && crearTiposEntidades.contains(tipoEntidadSeleccionada)) {
                int crearIndex = crearTiposEntidades.indexOf(tipoEntidadSeleccionada);
                crearTiposEntidades.remove(crearIndex);
            } else {
                borrarTiposEntidades.add(tipoEntidadSeleccionada);
            }
            listTiposEntidades.remove(tipoEntidadSeleccionada);
            if (tipoLista == 1) {
                filtrarTiposEntidades.remove(tipoEntidadSeleccionada);
            }
            modificarInfoRegistro(listTiposEntidades.size());
            tipoEntidadSeleccionada = null;
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");

            context.update("form:informacionRegistro");
            context.update("form:datosTipoEntidad");

            if (guardado) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        } else {
            context.execute("seleccionarRegistro.show()");
        }

    }

    public void duplicarTiposEntidades() {
        System.out.println("DuplicarVigenciasFormasPagos");
            RequestContext context = RequestContext.getCurrentInstance();
        if (tipoEntidadSeleccionada != null) {
            duplicarTipoEntidad = new TiposEntidades();
            k++;
            l = BigInteger.valueOf(k);

            duplicarTipoEntidad.setSecuencia(l);
            duplicarTipoEntidad.setCodigo(tipoEntidadSeleccionada.getCodigo());
            duplicarTipoEntidad.setGrupo(tipoEntidadSeleccionada.getGrupo());
            duplicarTipoEntidad.setNombre(tipoEntidadSeleccionada.getNombre());

            context.update("formularioDialogos:duplicarTiposEntidades");
            context.execute("duplicarRegistroTiposEntidades.show()");
        }else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;

        if (duplicarTipoEntidad.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposEntidades.size(); x++) {
                if (listTiposEntidades.get(x).getCodigo().equals(duplicarTipoEntidad.getCodigo())) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Existan Codigo Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
                duplicados = 0;
            }
        }
        if (duplicarTipoEntidad.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarTipoEntidad.getGrupo().getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   *Grupo Tipo Entidad \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            contador++;
            System.out.println("Bandera : ");

        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarTipoEntidad.getSecuencia() + "  " + duplicarTipoEntidad.getCodigo());
            if (crearTiposEntidades.contains(duplicarTipoEntidad)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposEntidades.add(duplicarTipoEntidad);
            crearTiposEntidades.add(duplicarTipoEntidad);
            context.update("form:datosTipoEntidad");
            modificarInfoRegistro(listTiposEntidades.size());
            tipoEntidadSeleccionada = listTiposEntidades.get(listTiposEntidades.indexOf(duplicarTipoEntidad));
            context.update("form:informacionRegistro");

            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                cerrarFiltrado();
            }
            duplicarTipoEntidad = new TiposEntidades();
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposEntidades.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarTiposEntidades() {
        duplicarTipoEntidad = new TiposEntidades();
        duplicarTipoEntidad.setGrupo(new Grupostiposentidades());
    }

    public void guardarCambiosTiposEntidades() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == false) {
            if (!borrarTiposEntidades.isEmpty()) {
                administrarTipoEntidad.borrarTipoEntidad(borrarTiposEntidades);
                //mostrarBorrados
                registrosBorrados = borrarTiposEntidades.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposEntidades.clear();
            }
            if (!crearTiposEntidades.isEmpty()) {
                administrarTipoEntidad.crearTipoEntidad(crearTiposEntidades);

                crearTiposEntidades.clear();
            }
            if (!modificarTiposEntidades.isEmpty()) {
                administrarTipoEntidad.modificarTipoEntidad(modificarTiposEntidades);
                modificarTiposEntidades.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposEntidades = null;
            getListTiposEntidades();
            if (tipoEntidadSeleccionada != null) {
                tipoEntidadSeleccionada = listTiposEntidades.get(0);
                modificarInfoRegistro(listTiposEntidades.size());
            } else {
                modificarInfoRegistro(0);
            }
            activarLOV = true;
            context.update("form:datosTipoEntidad");
            RequestContext.getCurrentInstance().update("form:listaValores");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            context.update("form:informacionRegistro");
            k = 0;
            permitirIndex = true;
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }

    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoEntidadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposEntidadesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoEntidadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposEntidadesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoEntidadSeleccionada != null) {
            int resultado = administrarRastros.obtenerTabla(tipoEntidadSeleccionada.getSecuencia(), "TIPOSENTIDADES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
            System.out.println("resultado: " + resultado);
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSENTIDADES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
        System.out.println("infoRegistro: " + infoRegistro);
    }

    private void modificarInfoRegistroGrupo(int valor) {
        infoRegistroGruposTiposEntidades = String.valueOf(valor);
        System.out.println("infoRegistroGruposTiposEntidades: " + infoRegistroGruposTiposEntidades);
    }

    public void recordarSeleccion() {
        if (tipoEntidadSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosTipoEntidad");
            tablaC.setSelection(tipoEntidadSeleccionada);
        }
    }

//----------------------------------------------------------------------------
    public List<TiposEntidades> getListTiposEntidades() {
        if (listTiposEntidades == null) {
            listTiposEntidades = administrarTipoEntidad.consultarTiposEntidades();
        }
        return listTiposEntidades;
    }

    public void setListTiposEntidades(List<TiposEntidades> listTiposEntidades) {
        this.listTiposEntidades = listTiposEntidades;
    }

    public List<TiposEntidades> getFiltrarTiposEntidades() {
        return filtrarTiposEntidades;
    }

    public void setFiltrarTiposEntidades(List<TiposEntidades> filtrarTiposEntidades) {
        this.filtrarTiposEntidades = filtrarTiposEntidades;
    }

    public List<Grupostiposentidades> getFiltradoGruposTiposEntidades() {
        return filtradoGruposTiposEntidades;
    }

    public void setFiltradoGruposTiposEntidades(List<Grupostiposentidades> filtradoGruposTiposEntidades) {
        this.filtradoGruposTiposEntidades = filtradoGruposTiposEntidades;
    }
    private String infoRegistroGruposTiposEntidades;

    public List<Grupostiposentidades> getListaGruposTiposEntidades() {
        if (listaGruposTiposEntidades == null) {
            listaGruposTiposEntidades = administrarTipoEntidad.consultarLOVGruposTiposEntidades();
        }
        return listaGruposTiposEntidades;
    }

    public void setListaGruposTiposEntidades(List<Grupostiposentidades> listaGruposTiposEntidades) {
        this.listaGruposTiposEntidades = listaGruposTiposEntidades;
    }

    public Grupostiposentidades getGruposTiposEntidadesSeleccionada() {
        return grupoTESeleccionada;
    }

    public void setGruposTiposEntidadesSeleccionada(Grupostiposentidades gruposTiposEntidadesSeleccionada) {
        this.grupoTESeleccionada = gruposTiposEntidadesSeleccionada;
    }

    public TiposEntidades getEditarTipoEntidad() {
        return editarTipoEntidad;
    }

    public void setEditarTipoEntidad(TiposEntidades editarTipoEntidad) {
        this.editarTipoEntidad = editarTipoEntidad;
    }

    public TiposEntidades getNuevoTipoEntidad() {
        return nuevoTipoEntidad;
    }

    public void setNuevoTipoEntidad(TiposEntidades nuevoTipoEntidad) {
        this.nuevoTipoEntidad = nuevoTipoEntidad;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public TiposEntidades getDuplicarTipoEntidad() {
        return duplicarTipoEntidad;
    }

    public void setDuplicarTipoEntidad(TiposEntidades duplicarTipoEntidad) {
        this.duplicarTipoEntidad = duplicarTipoEntidad;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getInfoRegistroGruposTiposEntidades() {
        return infoRegistroGruposTiposEntidades;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public TiposEntidades getTipoEntidadSeleccionada() {
        return tipoEntidadSeleccionada;
    }

    public void setTipoEntidadSeleccionada(TiposEntidades tipoEntidadSeleccionada) {
        this.tipoEntidadSeleccionada = tipoEntidadSeleccionada;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }
}

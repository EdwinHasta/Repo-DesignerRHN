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
import InterfaceAdministrar.AdministrarTipoEntidadInterface;
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
public class ControlTiposEntidades implements Serializable {

    @EJB
    AdministrarTipoEntidadInterface administrarTipoEntidad;
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
    //lov gruposTiposEntidades
    private Grupostiposentidades grupoTipoEntidadSeleccionada;
    private List<Grupostiposentidades> filtradoGruposTiposEntidades;
    private List<Grupostiposentidades> listaGruposTiposEntidades;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    String grupoAsociadoAutoCompletar;
    String nuevogrupoAsociadoAutoCompletar;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, nombre, grupoAsociado;
    //borrado
    private Long borrado;
    private Long borradoFCE;
    private int registrosBorrados;

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

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposEntidades.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposEntidades eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposEntidades.get(index).getSecuencia();

            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    grupoAsociadoAutoCompletar = listTiposEntidades.get(index).getGrupo().getNombre();
                } else {
                    grupoAsociadoAutoCompletar = filtrarTiposEntidades.get(index).getGrupo().getNombre();
                }
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);

    }

    /**
     *
     * @param indice
     * @param LND
     * @param dig muestra el dialogo respectivo
     */
    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposEntidades.asignarIndex \n");
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
            System.out.println("dig: " + dig);

            if (dig == 2) {
                context.update("form:gruposTiposEntidadesDialogo");
                context.execute("gruposTiposEntidadesDialogo.show()");
                dig = -1;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlVigenciasAfiliaciones3.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:gruposTiposEntidadesDialogo");
                context.execute("gruposTiposEntidadesDialogo.show()");
                tipoActualizacion = 0;
            }

        }
    }
// tipo Entidad

    public void actualizarGrupoTipoEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listTiposEntidades.get(index).setGrupo(grupoTipoEntidadSeleccionada);

                if (!crearTiposEntidades.contains(listTiposEntidades.get(index))) {
                    if (modificarTiposEntidades.isEmpty()) {
                        modificarTiposEntidades.add(listTiposEntidades.get(index));
                    } else if (!modificarTiposEntidades.contains(listTiposEntidades.get(index))) {
                        modificarTiposEntidades.add(listTiposEntidades.get(index));
                    }
                }
            } else {
                filtrarTiposEntidades.get(index).setGrupo(grupoTipoEntidadSeleccionada);

                if (!crearTiposEntidades.contains(filtrarTiposEntidades.get(index))) {
                    if (modificarTiposEntidades.isEmpty()) {
                        modificarTiposEntidades.add(filtrarTiposEntidades.get(index));
                    } else if (!modificarTiposEntidades.contains(filtrarTiposEntidades.get(index))) {
                        modificarTiposEntidades.add(filtrarTiposEntidades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosTipoEntidad");
        } else if (tipoActualizacion == 1) {
            nuevoTipoEntidad.setGrupo(grupoTipoEntidadSeleccionada);
            context.update("formularioDialogos:nuevoTipoEntidad");
        } else if (tipoActualizacion == 2) {
            duplicarTipoEntidad.setGrupo(grupoTipoEntidadSeleccionada);
            context.update("formularioDialogos:duplicarTiposEntidades");
        }
        filtradoGruposTiposEntidades = null;
        grupoTipoEntidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("gruposTiposEntidadesDialogo.hide()");
        context.reset("form:lovGruposTiposEntidades:globalFilter");
        context.update("form:lovGruposTiposEntidades");
    }

    public void cancelarCambioGrupoTipoEntidad() {
        filtradoGruposTiposEntidades = null;
        grupoTipoEntidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
//------------------------------------------------------------------------------

    public void modificarTipoEntidad(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!crearTiposEntidades.contains(listTiposEntidades.get(indice))) {
                    if (listTiposEntidades.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposEntidades.size(); j++) {
                            if (j != indice) {
                                if (listTiposEntidades.get(indice).getCodigo() == listTiposEntidades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (banderita == true) {
                        if (modificarTiposEntidades.isEmpty()) {
                            modificarTiposEntidades.add(listTiposEntidades.get(indice));
                        } else if (!modificarTiposEntidades.contains(listTiposEntidades.get(indice))) {
                            modificarTiposEntidades.add(listTiposEntidades.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacioModificar");
                        context.execute("validacioModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {


                if (!crearTiposEntidades.contains(filtrarTiposEntidades.get(indice))) {
                    if (filtrarTiposEntidades.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposEntidades.size(); j++) {
                            if (j != indice) {
                                if (listTiposEntidades.get(indice).getCodigo() == listTiposEntidades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (banderita == true) {
                        if (modificarTiposEntidades.isEmpty()) {
                            modificarTiposEntidades.add(filtrarTiposEntidades.get(indice));
                        } else if (!modificarTiposEntidades.contains(filtrarTiposEntidades.get(indice))) {
                            modificarTiposEntidades.add(filtrarTiposEntidades.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacioModificar");
                        context.execute("validacioModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }


                /*if (!crearTiposEntidades.contains(filtrarTiposEntidades.get(indice))) {

                 if (modificarTiposEntidades.isEmpty()) {
                 modificarTiposEntidades.add(filtrarTiposEntidades.get(indice));
                 } else if (!modificarTiposEntidades.contains(filtrarTiposEntidades.get(indice))) {
                 modificarTiposEntidades.add(filtrarTiposEntidades.get(indice));
                 }
                 if (guardado == true) {
                 guardado = false;
                 }
                 }
                 index = -1;
                 secRegistro = null;*/
            }
            context.update("form:datosTipoEntidad");
        } else if (confirmarCambio.equalsIgnoreCase("GRUPOSTIPOSENTIDADES")) {
            if (tipoLista == 0) {
                listTiposEntidades.get(indice).getGrupo().setNombre(grupoAsociadoAutoCompletar);
            } else {
                filtrarTiposEntidades.get(indice).getGrupo().setNombre(grupoAsociadoAutoCompletar);
            }

            for (int i = 0; i < listaGruposTiposEntidades.size(); i++) {
                if (listaGruposTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listTiposEntidades.get(indice).setGrupo(listaGruposTiposEntidades.get(indiceUnicoElemento));
                } else {
                    filtrarTiposEntidades.get(indice).setGrupo(listaGruposTiposEntidades.get(indiceUnicoElemento));
                }
                listaGruposTiposEntidades.clear();
                listaGruposTiposEntidades = null;
                getListaGruposTiposEntidades();
            } else {
                permitirIndex = false;
                context.update("form:gruposTiposEntidadesDialogo");
                context.execute("gruposTiposEntidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        context.update("form:datosTipoEntidad");

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
        if (bandera == 0) {
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:codigo");
            codigo.setFilterStyle("width: 200px");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:nombre");
            nombre.setFilterStyle("width: 270px");
            grupoAsociado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
            grupoAsociado.setFilterStyle("width: 270px");
            RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            grupoAsociado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
            grupoAsociado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
            bandera = 0;
            filtrarTiposEntidades = null;
            tipoLista = 0;
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            grupoAsociado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
            grupoAsociado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
            bandera = 0;
            filtrarTiposEntidades = null;
            tipoLista = 0;
        }

        borrarTiposEntidades.clear();
        crearTiposEntidades.clear();
        modificarTiposEntidades.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposEntidades = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoEntidad");
    }
    private String mensajeValidacion;

    public void agregarNuevoTipoEntidad() {
        System.out.println("Agregar nueva vigencia");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoEntidad.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("tamaño lista de tipos de entidades en agregar  : " + listTiposEntidades.size());
            System.out.println("codigo en tipo entidad: " + nuevoTipoEntidad.getCodigo());

            for (int x = 0; x < listTiposEntidades.size(); x++) {
                if (listTiposEntidades.get(x).getCodigo() == nuevoTipoEntidad.getCodigo()) {
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
        if (nuevoTipoEntidad.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Un  Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoTipoEntidad.getGrupo().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Grupo Tipo Entidad \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;
            duplicados = 0;


        }//TENER PRESENTE ------------------------------------------------------
        /*if (nuevaVigenciaFormasPago.getFechavigencia() == null) {
         mensajeValidacion = " * Fecha Vigencia \n";
         System.out.println("Mensaje validacion : " + mensajeValidacion);
         pasa = false;
         } else {
         for (int i = 0; i < listVigenciasFormasPagosPorEmpleado.size(); i++) {
         if (listVigenciasFormasPagosPorEmpleado.get(i).getFechavigencia().equals(nuevaVigenciaFormasPago.getFechavigencia())) {
         System.out.println("Comparacion for !! if");
         contador++;
         }
         }
         if (contador == 0) {
         pasa = true;
         } else {
         System.out.println("Comparacion FechasRepetidad");
         mensajeValidacion = mensajeValidacion + " * Fechas repetidas \n";
         pasa = false;
         }
         }*/
        //----------------------------------------------------------------------
        if (contador == 3) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                grupoAsociado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
                grupoAsociado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
                bandera = 0;
                filtrarTiposEntidades = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoEntidad.setSecuencia(l);

            crearTiposEntidades.add(nuevoTipoEntidad);

            listTiposEntidades.add(nuevoTipoEntidad);
            nuevoTipoEntidad = new TiposEntidades();
            nuevoTipoEntidad.setGrupo(new Grupostiposentidades());

            context.update("form:datosTipoEntidad");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("nuevoRegistroTipoEntidad.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTipoEntidad() {
        System.out.println("LimpiarNuevoTipoEntidad");
        nuevoTipoEntidad = new TiposEntidades();
        nuevoTipoEntidad.setGrupo(new Grupostiposentidades());
        secRegistro = null;
        index = -1;

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoEntidad = listTiposEntidades.get(index);
            }
            if (tipoLista == 1) {
                editarTipoEntidad = filtrarTiposEntidades.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
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

        }
        index = -1;
        secRegistro = null;
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
            borrado = administrarTipoEntidad.verificarBorrado(listTiposEntidades.get(index).getSecuencia());
            borradoFCE = administrarTipoEntidad.verificarBorradoFCE(listTiposEntidades.get(index).getSecuencia());
            if (borrado.intValue() == 0 && borradoFCE.intValue() == 0) {
                System.out.println("Borrado==0");
                borrarTiposEntidades();
            }
            if (0 < borrado.intValue() && borradoFCE.intValue() > 0) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
            }
            if (0 == borrado.intValue() && borradoFCE.intValue() > 0) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
            }
            if (0 < borrado.intValue() && borradoFCE.intValue() == 0) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
            }
            if (borrado.intValue() == -1 && borradoFCE.intValue() == -1) {
                System.out.println("Borrado==-1");
                System.err.println("ERROR ERROR ERROR");
                index = - 1;
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposEntidades verificarBorrado ERROR " + e);
        }
    }

    public void borrarTiposEntidades() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarTiposEntidades");
                if (!modificarTiposEntidades.isEmpty() && modificarTiposEntidades.contains(listTiposEntidades.get(index))) {
                    int modIndex = modificarTiposEntidades.indexOf(listTiposEntidades.get(index));
                    modificarTiposEntidades.remove(modIndex);
                    borrarTiposEntidades.add(listTiposEntidades.get(index));
                } else if (!crearTiposEntidades.isEmpty() && crearTiposEntidades.contains(listTiposEntidades.get(index))) {
                    int crearIndex = crearTiposEntidades.indexOf(listTiposEntidades.get(index));
                    crearTiposEntidades.remove(crearIndex);
                } else {
                    borrarTiposEntidades.add(listTiposEntidades.get(index));
                }
                listTiposEntidades.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("BorrarTiposEntidades ");
                if (!modificarTiposEntidades.isEmpty() && modificarTiposEntidades.contains(filtrarTiposEntidades.get(index))) {
                    int modIndex = modificarTiposEntidades.indexOf(filtrarTiposEntidades.get(index));
                    modificarTiposEntidades.remove(modIndex);
                    borrarTiposEntidades.add(filtrarTiposEntidades.get(index));
                } else if (!crearTiposEntidades.isEmpty() && crearTiposEntidades.contains(filtrarTiposEntidades.get(index))) {
                    int crearIndex = crearTiposEntidades.indexOf(filtrarTiposEntidades.get(index));
                    crearTiposEntidades.remove(crearIndex);
                } else {
                    borrarTiposEntidades.add(filtrarTiposEntidades.get(index));
                }
                int VCIndex = listTiposEntidades.indexOf(filtrarTiposEntidades.get(index));
                listTiposEntidades.remove(VCIndex);
                filtrarTiposEntidades.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoEntidad");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    public void duplicarTiposEntidades() {
        System.out.println("DuplicarVigenciasFormasPagos");
        if (index >= 0) {
            duplicarTipoEntidad = new TiposEntidades();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoEntidad.setSecuencia(l);
                duplicarTipoEntidad.setCodigo(listTiposEntidades.get(index).getCodigo());
                duplicarTipoEntidad.setGrupo(listTiposEntidades.get(index).getGrupo());
                duplicarTipoEntidad.setNombre(listTiposEntidades.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarTipoEntidad.setCodigo(filtrarTiposEntidades.get(index).getCodigo());
                duplicarTipoEntidad.setGrupo(filtrarTiposEntidades.get(index).getGrupo());
                duplicarTipoEntidad.setNombre(filtrarTiposEntidades.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTiposEntidades");
            context.execute("duplicarRegistroTiposEntidades.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoEntidad.getCodigo());
        System.err.println("ConfirmarDuplicar grupo " + duplicarTipoEntidad.getGrupo().getNombre());
        System.err.println("ConfirmarDuplicar nombre " + duplicarTipoEntidad.getNombre());

        if (duplicarTipoEntidad.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposEntidades.size(); x++) {
                if (listTiposEntidades.get(x).getCodigo() == duplicarTipoEntidad.getCodigo()) {
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
        if (duplicarTipoEntidad.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarTipoEntidad.getGrupo().getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Grupo Tipo Entidad \n";
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
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                grupoAsociado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoEntidad:grupoAsociado");
                grupoAsociado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoEntidad");
                bandera = 0;
                filtrarTiposEntidades = null;
                tipoLista = 0;
            }
            duplicarTipoEntidad = new TiposEntidades();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposEntidades.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarTiposEntidades() {
        duplicarTipoEntidad = new TiposEntidades();
    }

    public void guardarCambiosTiposEntidades() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarTiposEntidades.isEmpty()) {
                for (int i = 0; i < borrarTiposEntidades.size(); i++) {
                    System.out.println("Borrando...");
                    administrarTipoEntidad.borrarTipoEntidad(borrarTiposEntidades.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarTiposEntidades.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposEntidades.clear();
            }
            if (!crearTiposEntidades.isEmpty()) {
                for (int i = 0; i < crearTiposEntidades.size(); i++) {

                    System.out.println("Creando...");
                    administrarTipoEntidad.crearTipoEntidad(crearTiposEntidades.get(i));

                }
                crearTiposEntidades.clear();
            }
            if (!modificarTiposEntidades.isEmpty()) {
                administrarTipoEntidad.modificarTipoEntidad(modificarTiposEntidades);
                modificarTiposEntidades.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposEntidades = null;
            context.update("form:datosTipoEntidad");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:aceptar");

    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoEntidadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposEntidadesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoEntidadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposEntidadesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposEntidades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSENTIDADES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("TIPOSENTIDADES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
//----------------------------------------------------------------------------

    public List<TiposEntidades> getListTiposEntidades() {
        if (listTiposEntidades == null) {
            listTiposEntidades = administrarTipoEntidad.mostrarTiposEntidades();
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

    public List<Grupostiposentidades> getListaGruposTiposEntidades() {
        if (listaGruposTiposEntidades == null) {
            listaGruposTiposEntidades = administrarTipoEntidad.mostrarGruposTiposEntidades();
        }
        return listaGruposTiposEntidades;
    }

    public void setListaGruposTiposEntidades(List<Grupostiposentidades> listaGruposTiposEntidades) {
        this.listaGruposTiposEntidades = listaGruposTiposEntidades;
    }

    public Grupostiposentidades getGruposTiposEntidadesSeleccionada() {
        return grupoTipoEntidadSeleccionada;
    }

    public void setGruposTiposEntidadesSeleccionada(Grupostiposentidades gruposTiposEntidadesSeleccionada) {
        this.grupoTipoEntidadSeleccionada = gruposTiposEntidadesSeleccionada;
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

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }
    
}
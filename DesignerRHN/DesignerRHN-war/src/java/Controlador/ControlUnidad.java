/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposUnidades;
import Entidades.Unidades;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarUnidadesInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
public class ControlUnidad implements Serializable {

    @EJB
    AdministrarUnidadesInterface administrarUnidades;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<Unidades> listaUnidades;
    private List<Unidades> filtradoListaUnidades;
    private Unidades unidadSeleccionada;
    //LISTA DE VALORES DE TIPOS UNIDADES FALTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private List<TiposUnidades> lovTiposUnidades;
    private List<TiposUnidades> lovFiltradoTiposUnidades;
    private TiposUnidades tiposUnidadSeleccionado;
    //Otros
    private boolean aceptar;
    private int index;
    private int tipoActualizacion;
    private boolean permitirIndex;
    private int tipoLista;
    private int cualCelda;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Modificar Ciudades
    private List<Unidades> listaUnidadesModificar;
    private boolean guardado, guardarOk;
    //Crear Ciudades
    public Unidades nuevaUnidad;
    private List<Unidades> listaUnidadesCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //Borrar Ciudad
    private List<Unidades> listaUnidadesBorrar;
    //AUTOCOMPLETAR
    private String tipoUnidad;
    //editar celda
    private Unidades editarUnidad;
    private boolean cambioEditor, aceptarEditar;
    //DUPLICAR
    private Unidades duplicarUnidad;
    //RASTRO
    private BigInteger secRegistro;
    public String altoTabla;
    public String infoRegistroTiposUnidades;
    //
    private Column unidadesCodigos, unidadesNombres, unidadesTipos;
    public String infoRegistro;

    public ControlUnidad() {
        permitirIndex = true;
        aceptar = true;
        tipoLista = 0;
        listaUnidadesBorrar = new ArrayList<Unidades>();
        listaUnidadesCrear = new ArrayList<Unidades>();
        listaUnidadesModificar = new ArrayList<Unidades>();
        lovTiposUnidades = new ArrayList<TiposUnidades>();
        //editar
        editarUnidad = new Unidades();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        nuevaUnidad = new Unidades();
        nuevaUnidad.setTipounidad(new TiposUnidades());
        nuevaUnidad.getTipounidad().setNombre(" ");
        secRegistro = null;
        k = 0;
        altoTabla = "270";
        guardado = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarUnidades.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            getListaUnidades();
            if (listaUnidades != null) {
                infoRegistro = "Cantidad de registros : " + listaUnidades.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tipoLista = 1;
            System.out.println("Activar");
            unidadesCodigos = (Column) c.getViewRoot().findComponent("form:datosUnidades:unidadesCodigos");
            unidadesCodigos.setFilterStyle("width: 60px");
            unidadesNombres = (Column) c.getViewRoot().findComponent("form:datosUnidades:unidadesNombres");
            unidadesNombres.setFilterStyle("");
            unidadesTipos = (Column) c.getViewRoot().findComponent("form:datosUnidades:unidadesTipos");
            unidadesTipos.setFilterStyle("");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosUnidades");
            bandera = 1;

            System.out.println("TipoLista= " + tipoLista);
        } else if (bandera == 1) {
            tipoLista = 0;
            System.out.println("Desactivar");
            unidadesCodigos = (Column) c.getViewRoot().findComponent("form:datosUnidades:unidadesCodigos");
            unidadesCodigos.setFilterStyle("display: none; visibility: hidden;");
            unidadesNombres = (Column) c.getViewRoot().findComponent("form:datosUnidades:unidadesNombres");
            unidadesNombres.setFilterStyle("display: none; visibility: hidden;");
            unidadesTipos = (Column) c.getViewRoot().findComponent("form:datosUnidades:unidadesTipos");
            unidadesTipos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosUnidades");
            altoTabla = "270";
            bandera = 0;
            filtradoListaUnidades = null;
            System.out.println("TipoLista= " + tipoLista);

        }
    }

    //EVENTO FILTRAR
    public void eventofiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros: " + filtradoListaUnidades.size();
        context.update("form:informacionRegistro");
    }

    //UBICACION CELDA
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;

            if (tipoLista == 0) {
                secRegistro = listaUnidades.get(index).getSecuencia();
                if (cualCelda == 2) {
                    tipoUnidad = listaUnidades.get(index).getTipounidad().getNombre();
                }
            } else {
                secRegistro = filtradoListaUnidades.get(index).getSecuencia();
                if (cualCelda == 2) {
                    tipoUnidad = filtradoListaUnidades.get(index).getTipounidad().getNombre();
                }
            }
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarUnidad = listaUnidades.get(index);
            }
            if (tipoLista == 1) {
                editarUnidad = filtradoListaUnidades.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigos");
                context.execute("editarCodigos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombres");
                context.execute("editarNombres.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipos");
                context.execute("editarTipos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //AUTOCOMPLETAR
    public void modificarUnidades(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;

        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaUnidadesCrear.contains(listaUnidades.get(indice))) {

                    if (listaUnidadesModificar.isEmpty()) {
                        listaUnidadesModificar.add(listaUnidades.get(indice));
                    } else if (!listaUnidadesModificar.contains(listaUnidades.get(indice))) {
                        listaUnidadesModificar.add(listaUnidades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaUnidadesCrear.contains(filtradoListaUnidades.get(indice))) {

                    if (listaUnidadesModificar.isEmpty()) {
                        listaUnidadesModificar.add(filtradoListaUnidades.get(indice));
                    } else if (!listaUnidadesModificar.contains(filtradoListaUnidades.get(indice))) {
                        listaUnidadesModificar.add(filtradoListaUnidades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosUnidades");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOSUNIDADES")) {
            if (tipoLista == 0) {
                listaUnidades.get(indice).getTipounidad().setNombre(tipoUnidad);
            } else {
                filtradoListaUnidades.get(indice).getTipounidad().setNombre(tipoUnidad);
            }

            for (int i = 0; i < lovTiposUnidades.size(); i++) {
                if (lovTiposUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaUnidades.get(indice).setTipounidad(lovTiposUnidades.get(indiceUnicoElemento));
                } else {
                    filtradoListaUnidades.get(indice).setTipounidad(lovTiposUnidades.get(indiceUnicoElemento));
                }
                lovTiposUnidades.clear();
                getLovTiposUnidades();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposUnidadesDialogo");
                context.execute("tiposUnidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaUnidadesCrear.contains(listaUnidades.get(indice))) {
                    if (listaUnidadesModificar.isEmpty()) {
                        listaUnidadesModificar.add(listaUnidades.get(indice));
                    } else if (!listaUnidadesModificar.contains(listaUnidades.get(indice))) {
                        listaUnidadesModificar.add(listaUnidades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaUnidadesCrear.contains(filtradoListaUnidades.get(indice))) {

                    if (listaUnidadesModificar.isEmpty()) {
                        listaUnidadesModificar.add(filtradoListaUnidades.get(indice));
                    } else if (!listaUnidadesModificar.contains(filtradoListaUnidades.get(indice))) {
                        listaUnidadesModificar.add(filtradoListaUnidades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosUnidades");
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:tiposUnidadesDialogo");
            context.execute("tiposUnidadesDialogo.show()");
        }
    }

    //Actualizar desde una lista de valores
    //METODOS L.O.V Tipos Unidades
    public void actualizarTiposUnidades() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaUnidades.get(index).setTipounidad(tiposUnidadSeleccionado);
                if (!listaUnidadesCrear.contains(listaUnidades.get(index))) {
                    if (listaUnidadesModificar.isEmpty()) {
                        listaUnidadesModificar.add(listaUnidades.get(index));
                    } else if (!listaUnidadesModificar.contains(listaUnidades.get(index))) {
                        listaUnidadesModificar.add(listaUnidades.get(index));
                    }
                }
            } else {
                filtradoListaUnidades.get(index).setTipounidad(tiposUnidadSeleccionado);
                if (!listaUnidadesCrear.contains(filtradoListaUnidades.get(index))) {
                    if (listaUnidadesModificar.isEmpty()) {
                        listaUnidadesModificar.add(filtradoListaUnidades.get(index));
                    } else if (!listaUnidadesModificar.contains(filtradoListaUnidades.get(index))) {
                        listaUnidadesModificar.add(filtradoListaUnidades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosCiudades");
        } else if (tipoActualizacion == 1) {
            nuevaUnidad.setTipounidad(tiposUnidadSeleccionado);
            context.update("formularioDialogos:nuevoTipoUnidad");
        } else if (tipoActualizacion == 2) {
            duplicarUnidad.setTipounidad(tiposUnidadSeleccionado);
            context.update("formularioDialogos:duplicarTipoUnidad");
        }
        lovFiltradoTiposUnidades = null;
        tiposUnidadSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("tiposUnidadesDialogo.hide()");
        context.reset("formularioDialogos:LOVTiposUnidades:globalFilter");
        context.update("formularioDialogos:LOVTiposUnidades");
    }

    public void cancelarCambioTiposUnidades() {
        lovFiltradoTiposUnidades = null;
        tiposUnidadSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:tiposUnidadesDialogo");
                context.execute("tiposUnidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }
    
    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosUnidadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "UnidadesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosUnidadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "UnidadesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    
    //LIMPIAR NUEVO REGISTRO CIUDAD

    public void limpiarNuevaUnidad() {
        nuevaUnidad = new Unidades();
        nuevaUnidad.setTipounidad(new TiposUnidades());
        nuevaUnidad.getTipounidad().setNombre(" ");
        index = -1;
        secRegistro = null;
    }
    
    public void valoresBackupAutocompletar(int tipoNuevo) {

        if (tipoNuevo == 1) {
            tipoUnidad = nuevaUnidad.getTipounidad().getNombre();
        } else if (tipoNuevo == 2) {
            tipoUnidad = duplicarUnidad.getTipounidad().getNombre();
        }
    }
    
    public void autocompletarNuevoyDuplicado(String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoNuevo == 1) {
            nuevaUnidad.getTipounidad().setNombre(tipoUnidad);
        } else if (tipoNuevo == 2) {
            duplicarUnidad.getTipounidad().setNombre(tipoUnidad);
        }
        for (int i = 0; i < lovTiposUnidades.size(); i++) {
            if (lovTiposUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }
        if (coincidencias == 1) {
            if (tipoNuevo == 1) {
                nuevaUnidad.setTipounidad(lovTiposUnidades.get(indiceUnicoElemento));
                context.update("formularioDialogos:nuevoTipoUnidad");
            } else if (tipoNuevo == 2) {
                duplicarUnidad.setTipounidad(lovTiposUnidades.get(indiceUnicoElemento));
                context.update("formularioDialogos:duplicarTipoUnidad");
            }
            lovTiposUnidades.clear();
            getLovTiposUnidades();
        } else {
            context.update("form:tiposUnidadesDialogo");
            context.execute("tiposUnidadesDialogo.show()");
            tipoActualizacion = tipoNuevo;
            if (tipoNuevo == 1) {
                context.update("formularioDialogos:nuevoTipoUnidad");
            } else if (tipoNuevo == 2) {
                context.update("formularioDialogos:duplicarTipoUnidad");
            }
        }
    }
    
    public void llamarLovTiposUnidades(int tipoN) {
        if (tipoN == 1) {
            tipoActualizacion = 1;
        } else if (tipoN == 2) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:tiposUnidadesDialogo");
        context.execute("tiposUnidadesDialogo.show()");
    }
    
    /*//CREAR CIUDAD
    public void agregarNuevaUnidad() {
        
        int pasa = 0;
        int pasaA = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevaCiudad.getNombre() == null && (nuevaCiudad.getNombre().equals(" ")) || (nuevaCiudad.getNombre().equals(""))) {
            System.out.println("Entra");
            mensajeValidacion = mensajeValidacion + " * Nombre de la Ciudad \n";
            pasa++;
        }
        if (nuevaCiudad.getDepartamento().getSecuencia() == null) {
            System.out.println("Entra 2");
            mensajeValidacion = mensajeValidacion + "   * Departamento \n";
            pasa++;
        }

        for (int i = 0; i < listaCiudades.size(); i++) {
            if (nuevaCiudad.getNombre().equals(listaCiudades.get(i).getNombre())) {
                System.out.println("Entro al IF");
                context.update("formularioDialogos:existe");
                context.execute("existe.show()");
                pasaA++;

            }
        }

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaCiudad");
            context.execute("validacionNuevaCiudad.show()");
        }

        if (pasa == 0 && pasaA == 0) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                ciudadesCodigos = (Column) c.getViewRoot().findComponent("form:datosCiudades:ciudadesCodigos");
                ciudadesCodigos.setFilterStyle("display: none; visibility: hidden;");
                ciudadesNombres = (Column) c.getViewRoot().findComponent("form:datosCiudades:ciudadesNombres");
                ciudadesNombres.setFilterStyle("display: none; visibility: hidden;");
                nombresDepartamentos = (Column) c.getViewRoot().findComponent("form:datosCiudades:nombresDepartamentos");
                nombresDepartamentos.setFilterStyle("display: none; visibility: hidden;");
                ciudadesCodigosAlternativos = (Column) c.getViewRoot().findComponent("form:datosCiudades:ciudadesCodigosAlternativos");
                ciudadesCodigosAlternativos.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "270";
                RequestContext.getCurrentInstance().update("form:datosCiudades");
                bandera = 0;
                filtradoListaCiudades = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA CIUDADES.
            k++;
            l = BigInteger.valueOf(k);
            nuevaCiudad.setSecuencia(l);
            listaCiudadesCrear.add(nuevaCiudad);
            listaCiudades.add(nuevaCiudad);
            infoRegistro = "Cantidad de registros: " + listaCiudades.size();
            context.update("form:informacionRegistro");
            nuevaCiudad = new Ciudades();
            //  nuevaCiudad.setNombre(Departamento);
            nuevaCiudad.setDepartamento(new Departamentos());
            nuevaCiudad.getDepartamento().setNombre(" ");
            context.update("form:datosCiudades");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            context.execute("NuevoRegistroCiudad.hide()");
            index = -1;
            secRegistro = null;
        }
    }
*/
    //Getter & Setters
    public List<Unidades> getListaUnidades() {
        if (listaUnidades == null) {
            listaUnidades = administrarUnidades.consultarUnidades();
        }
        return listaUnidades;
    }

    public void setListaUnidades(List<Unidades> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Unidades> getFiltradoListaUnidades() {
        return filtradoListaUnidades;
    }

    public void setFiltradoListaUnidades(List<Unidades> filtradoListaUnidades) {
        this.filtradoListaUnidades = filtradoListaUnidades;
    }

    public Unidades getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(Unidades unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public List<TiposUnidades> getLovTiposUnidades() {
        lovTiposUnidades = administrarUnidades.consultarTiposUnidades();
        RequestContext context = RequestContext.getCurrentInstance();

        if (lovTiposUnidades == null || lovTiposUnidades.isEmpty()) {
            infoRegistroTiposUnidades = "Cantidad de registros: 0 ";
        } else {
            infoRegistroTiposUnidades = "Cantidad de registros: " + lovTiposUnidades.size();
        }
        context.update("formularioDialogos:infoRegistroTiposUnidades");
        return lovTiposUnidades;
    }

    public void setLovTiposUnidades(List<TiposUnidades> lovTiposUnidades) {
        this.lovTiposUnidades = lovTiposUnidades;
    }

    public List<TiposUnidades> getLovFiltradoTiposUnidades() {
        return lovFiltradoTiposUnidades;
    }

    public void setLovFiltradoTiposUnidades(List<TiposUnidades> lovFiltradoTiposUnidades) {
        this.lovFiltradoTiposUnidades = lovFiltradoTiposUnidades;
    }

    public TiposUnidades getTiposUnidadSeleccionado() {
        return tiposUnidadSeleccionado;
    }

    public void setTiposUnidadSeleccionado(TiposUnidades tiposUnidadSeleccionado) {
        this.tiposUnidadSeleccionado = tiposUnidadSeleccionado;
    }

    public String getInfoRegistroTiposUnidades() {
        return infoRegistroTiposUnidades;
    }

    public void setInfoRegistroTiposUnidades(String infoRegistroTiposUnidades) {
        this.infoRegistroTiposUnidades = infoRegistroTiposUnidades;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public Unidades getEditarUnidad() {
        return editarUnidad;
    }

    public void setEditarUnidad(Unidades editarUnidad) {
        this.editarUnidad = editarUnidad;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Entidades.SoCondicionesTrabajos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSoCondicionesTrabajosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class ControlSoCondicionesTrabajos implements Serializable {

    @EJB
    AdministrarSoCondicionesTrabajosInterface administrarSoCondicionesTrabajos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<SoCondicionesTrabajos> listSoCondicionesTrabajos;
    private List<SoCondicionesTrabajos> filtrarSoCondicionesTrabajos;
    private List<SoCondicionesTrabajos> crearSoCondicionesTrabajos;
    private List<SoCondicionesTrabajos> modificarSoCondicionesTrabajos;
    private List<SoCondicionesTrabajos> borrarSoCondicionesTrabajos;
    private SoCondicionesTrabajos nuevaSoCondicionTrabajo;
    private SoCondicionesTrabajos duplicarSoCondicionTrabajo;
    private SoCondicionesTrabajos editarSoCondicionTrabajo;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, factorriesgo;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigDecimal verificarSoAccidentesMedicos;
    private BigDecimal verificarSoExposicionesFr;
    private BigDecimal verificarSoDetallesPanoramas;
    private BigDecimal verificarInspecciones;

    public ControlSoCondicionesTrabajos() {
        listSoCondicionesTrabajos = null;
        crearSoCondicionesTrabajos = new ArrayList<SoCondicionesTrabajos>();
        modificarSoCondicionesTrabajos = new ArrayList<SoCondicionesTrabajos>();
        borrarSoCondicionesTrabajos = new ArrayList<SoCondicionesTrabajos>();
        permitirIndex = true;
        editarSoCondicionTrabajo = new SoCondicionesTrabajos();
        nuevaSoCondicionTrabajo = new SoCondicionesTrabajos();
        duplicarSoCondicionTrabajo = new SoCondicionesTrabajos();
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n EVENTO FILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR EVENTO FILTRAR ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listSoCondicionesTrabajos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLSOCONDICIONESTRABAJOS  AsignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLSOCONDICIONESTRABAJOS asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            factorriesgo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:factorriesgo");
            factorriesgo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSoCondicionesTrabajos");
            bandera = 0;
            filtrarSoCondicionesTrabajos = null;
            tipoLista = 0;
        }

        borrarSoCondicionesTrabajos.clear();
        crearSoCondicionesTrabajos.clear();
        modificarSoCondicionesTrabajos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listSoCondicionesTrabajos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:datosSoCondicionesTrabajos");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:codigo");
            codigo.setFilterStyle("width: 120px");
            factorriesgo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:factorriesgo");
            factorriesgo.setFilterStyle("width: 600px");
            RequestContext.getCurrentInstance().update("form:datosSoCondicionesTrabajos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            factorriesgo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:factorriesgo");
            factorriesgo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSoCondicionesTrabajos");
            bandera = 0;
            filtrarSoCondicionesTrabajos = null;
            tipoLista = 0;
        }
    }

    public void modificandoSoCondicionesTrabajos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("MODIFICAR  SO ACTOS INSEGUROS");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("MODIFICANDO SO ACTO INSEGURO CONFIRMAR CAMBIO = N");
            if (tipoLista == 0) {
                if (!crearSoCondicionesTrabajos.contains(listSoCondicionesTrabajos.get(indice))) {
                    if (listSoCondicionesTrabajos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listSoCondicionesTrabajos.size(); j++) {
                            if (j != indice) {
                                if (listSoCondicionesTrabajos.get(indice).getCodigo().equals(listSoCondicionesTrabajos.get(j).getCodigo())) {
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
                    if (listSoCondicionesTrabajos.get(indice).getFactorriesgo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listSoCondicionesTrabajos.get(indice).getFactorriesgo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarSoCondicionesTrabajos.isEmpty()) {
                            modificarSoCondicionesTrabajos.add(listSoCondicionesTrabajos.get(indice));
                        } else if (!modificarSoCondicionesTrabajos.contains(listSoCondicionesTrabajos.get(indice))) {
                            modificarSoCondicionesTrabajos.add(listSoCondicionesTrabajos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearSoCondicionesTrabajos.contains(filtrarSoCondicionesTrabajos.get(indice))) {
                    if (filtrarSoCondicionesTrabajos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarSoCondicionesTrabajos.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listSoCondicionesTrabajos.size(); j++) {
                            if (filtrarSoCondicionesTrabajos.get(indice).getCodigo().equals(listSoCondicionesTrabajos.get(j).getCodigo())) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarSoCondicionesTrabajos.size(); j++) {
                            if (j == indice) {
                                if (filtrarSoCondicionesTrabajos.get(indice).getCodigo().equals(filtrarSoCondicionesTrabajos.get(j).getCodigo())) {
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

                    if (filtrarSoCondicionesTrabajos.get(indice).getFactorriesgo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarSoCondicionesTrabajos.get(indice).getFactorriesgo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarSoCondicionesTrabajos.isEmpty()) {
                            modificarSoCondicionesTrabajos.add(filtrarSoCondicionesTrabajos.get(indice));
                        } else if (!modificarSoCondicionesTrabajos.contains(filtrarSoCondicionesTrabajos.get(indice))) {
                            modificarSoCondicionesTrabajos.add(filtrarSoCondicionesTrabajos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosSoCondicionesTrabajos");
        }

    }

    public void borrandoSoCondicionesTrabajos() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("borrandoSoCondicionesTrabajos");
                if (!modificarSoCondicionesTrabajos.isEmpty() && modificarSoCondicionesTrabajos.contains(listSoCondicionesTrabajos.get(index))) {
                    int modIndex = modificarSoCondicionesTrabajos.indexOf(listSoCondicionesTrabajos.get(index));
                    modificarSoCondicionesTrabajos.remove(modIndex);
                    borrarSoCondicionesTrabajos.add(listSoCondicionesTrabajos.get(index));
                } else if (!crearSoCondicionesTrabajos.isEmpty() && crearSoCondicionesTrabajos.contains(listSoCondicionesTrabajos.get(index))) {
                    int crearIndex = crearSoCondicionesTrabajos.indexOf(listSoCondicionesTrabajos.get(index));
                    crearSoCondicionesTrabajos.remove(crearIndex);
                } else {
                    borrarSoCondicionesTrabajos.add(listSoCondicionesTrabajos.get(index));
                }
                listSoCondicionesTrabajos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoSoCondicionesTrabajos");
                if (!modificarSoCondicionesTrabajos.isEmpty() && modificarSoCondicionesTrabajos.contains(filtrarSoCondicionesTrabajos.get(index))) {
                    int modIndex = modificarSoCondicionesTrabajos.indexOf(filtrarSoCondicionesTrabajos.get(index));
                    modificarSoCondicionesTrabajos.remove(modIndex);
                    borrarSoCondicionesTrabajos.add(filtrarSoCondicionesTrabajos.get(index));
                } else if (!crearSoCondicionesTrabajos.isEmpty() && crearSoCondicionesTrabajos.contains(filtrarSoCondicionesTrabajos.get(index))) {
                    int crearIndex = crearSoCondicionesTrabajos.indexOf(filtrarSoCondicionesTrabajos.get(index));
                    crearSoCondicionesTrabajos.remove(crearIndex);
                } else {
                    borrarSoCondicionesTrabajos.add(filtrarSoCondicionesTrabajos.get(index));
                }
                int VCIndex = listSoCondicionesTrabajos.indexOf(filtrarSoCondicionesTrabajos.get(index));
                listSoCondicionesTrabajos.remove(VCIndex);
                filtrarSoCondicionesTrabajos.remove(index);

            }
            context.update("form:datosSoCondicionesTrabajos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("verificarBorrado");
        try {
            verificarInspecciones = administrarSoCondicionesTrabajos.verificarInspecciones(listSoCondicionesTrabajos.get(index).getSecuencia());
            verificarSoDetallesPanoramas = administrarSoCondicionesTrabajos.verificarSoDetallesPanoramas(listSoCondicionesTrabajos.get(index).getSecuencia());
            verificarSoExposicionesFr = administrarSoCondicionesTrabajos.verificarSoExposicionesFr(listSoCondicionesTrabajos.get(index).getSecuencia());
            verificarSoAccidentesMedicos = administrarSoCondicionesTrabajos.verificarSoAccidentesMedicos(listSoCondicionesTrabajos.get(index).getSecuencia());
            if (verificarSoAccidentesMedicos.intValueExact() == 0 && verificarSoExposicionesFr.intValueExact() == 0
                    && verificarSoDetallesPanoramas.intValueExact() == 0 && verificarInspecciones.intValueExact() == 0) {
                System.out.println("Borrado==0");
                borrandoSoCondicionesTrabajos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarSoAccidentesMedicos = new BigDecimal(-1);
                verificarSoExposicionesFr = new BigDecimal(-1);
                verificarSoDetallesPanoramas = new BigDecimal(-1);
                verificarInspecciones = new BigDecimal(-1);
            }
        } catch (Exception e) {
            System.err.println("ERROR CLASES ACCIDENTES verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarSoCondicionesTrabajos.isEmpty() || !crearSoCondicionesTrabajos.isEmpty() || !modificarSoCondicionesTrabajos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoSoCondicionesTrabajos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (!borrarSoCondicionesTrabajos.isEmpty()) {
                for (int i = 0; i < borrarSoCondicionesTrabajos.size(); i++) {
                    System.out.println("Borrando...");
                    administrarSoCondicionesTrabajos.borrarSoCondicionesTrabajos(borrarSoCondicionesTrabajos.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarSoCondicionesTrabajos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarSoCondicionesTrabajos.clear();
            }
            if (!crearSoCondicionesTrabajos.isEmpty()) {
                for (int i = 0; i < crearSoCondicionesTrabajos.size(); i++) {

                    System.out.println("Creando...");
                    administrarSoCondicionesTrabajos.crearSoCondicionesTrabajos(crearSoCondicionesTrabajos.get(i));

                }
                crearSoCondicionesTrabajos.clear();
            }
            if (!modificarSoCondicionesTrabajos.isEmpty()) {
                administrarSoCondicionesTrabajos.modificarSoCondicionesTrabajos(modificarSoCondicionesTrabajos);
                modificarSoCondicionesTrabajos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listSoCondicionesTrabajos = null;
            context.update("form:datosSoCondicionesTrabajos");
            k = 0;
            if (guardado == false) {
                guardado = true;
            }
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarSoCondicionTrabajo = listSoCondicionesTrabajos.get(index);
            }
            if (tipoLista == 1) {
                editarSoCondicionTrabajo = filtrarSoCondicionesTrabajos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editFactorRiesgo");
                context.execute("editFactorRiesgo.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoSoCondicionesTrabajos() {
        System.out.println("agregarNuevoSoCondicionesTrabajos");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaSoCondicionTrabajo.getCodigo() == null) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevaSoCondicionTrabajo.getCodigo());

            for (int x = 0; x < listSoCondicionesTrabajos.size(); x++) {
                if (listSoCondicionesTrabajos.get(x).getCodigo().equals(nuevaSoCondicionTrabajo.getCodigo())) {
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
        if (nuevaSoCondicionTrabajo.getFactorriesgo() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener un factor de riesgo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                factorriesgo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:factorriesgo");
                factorriesgo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSoCondicionesTrabajos");
                bandera = 0;
                filtrarSoCondicionesTrabajos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            /*      nuevaSoCondicionTrabajo.setSecuencia(l);
             nuevaSoCondicionTrabajo.setEfectocronico(" ");
             nuevaSoCondicionTrabajo.setEfectoagudo(" ");
             nuevaSoCondicionTrabajo.setFuente(" ");
             nuevaSoCondicionTrabajo.setObservacion(" ");
             nuevaSoCondicionTrabajo.setReportar(" ");
             nuevaSoCondicionTrabajo.setRecomendacion(" ");
             */ crearSoCondicionesTrabajos.add(nuevaSoCondicionTrabajo);

            listSoCondicionesTrabajos.add(nuevaSoCondicionTrabajo);
            nuevaSoCondicionTrabajo = new SoCondicionesTrabajos();
            context.update("form:datosSoCondicionesTrabajos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroSoCondicionesTrabajos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoSoCondicionesTrabajos() {
        System.out.println("limpiarNuevoSoCondicionesTrabajos");
        nuevaSoCondicionTrabajo = new SoCondicionesTrabajos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoSoActoInseguro() {
        System.out.println("duplicandoSoActoInseguro");
        if (index >= 0) {
            duplicarSoCondicionTrabajo = new SoCondicionesTrabajos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarSoCondicionTrabajo.setSecuencia(l);
                duplicarSoCondicionTrabajo.setCodigo(listSoCondicionesTrabajos.get(index).getCodigo());
                duplicarSoCondicionTrabajo.setFactorriesgo(listSoCondicionesTrabajos.get(index).getFactorriesgo());
                /*/ duplicarSoCondicionTrabajo.setEfectocronico(listSoCondicionesTrabajos.get(index).getEfectocronico());
                 duplicarSoCondicionTrabajo.setEfectoagudo(listSoCondicionesTrabajos.get(index).getEfectoagudo());
                 duplicarSoCondicionTrabajo.setFuente(listSoCondicionesTrabajos.get(index).getFuente());
                 duplicarSoCondicionTrabajo.setObservacion(listSoCondicionesTrabajos.get(index).getObservacion());
                 duplicarSoCondicionTrabajo.setReportar(listSoCondicionesTrabajos.get(index).getReportar());
                 duplicarSoCondicionTrabajo.setRecomendacion(listSoCondicionesTrabajos.get(index).getRecomendacion());
                 */
            }
            if (tipoLista == 1) {
                duplicarSoCondicionTrabajo.setSecuencia(l);
                duplicarSoCondicionTrabajo.setCodigo(filtrarSoCondicionesTrabajos.get(index).getCodigo());
                duplicarSoCondicionTrabajo.setFactorriesgo(filtrarSoCondicionesTrabajos.get(index).getFactorriesgo());
                /*  duplicarSoCondicionTrabajo.setEfectocronico(filtrarSoCondicionesTrabajos.get(index).getEfectocronico());
                 duplicarSoCondicionTrabajo.setEfectoagudo(filtrarSoCondicionesTrabajos.get(index).getEfectoagudo());
                 duplicarSoCondicionTrabajo.setFuente(filtrarSoCondicionesTrabajos.get(index).getFuente());
                 duplicarSoCondicionTrabajo.setObservacion(filtrarSoCondicionesTrabajos.get(index).getObservacion());
                 duplicarSoCondicionTrabajo.setReportar(filtrarSoCondicionesTrabajos.get(index).getReportar());
                 duplicarSoCondicionTrabajo.setRecomendacion(filtrarSoCondicionesTrabajos.get(index).getRecomendacion());
                 */
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarRCT");
            context.execute("duplicarRegistroSoCondicionesTrabajo.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("CONFIRMAR DUPLICAR SO ACTOS INSEGUROS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarSoCondicionTrabajo.getCodigo());
        System.err.println("ConfirmarDuplicar factorriesgo " + duplicarSoCondicionTrabajo.getFactorriesgo());

        if (duplicarSoCondicionTrabajo.getCodigo() == null || duplicarSoCondicionTrabajo.getCodigo().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            for (int x = 0; x < listSoCondicionesTrabajos.size(); x++) {
                if (listSoCondicionesTrabajos.get(x).getCodigo().equals(duplicarSoCondicionTrabajo.getCodigo())) {
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
        if (duplicarSoCondicionTrabajo.getFactorriesgo() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un factor de riesgo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarSoCondicionTrabajo.getSecuencia() + "  " + duplicarSoCondicionTrabajo.getCodigo());
            if (crearSoCondicionesTrabajos.contains(duplicarSoCondicionTrabajo)) {
                System.out.println("Ya lo contengo.");
            }
            listSoCondicionesTrabajos.add(duplicarSoCondicionTrabajo);
            crearSoCondicionesTrabajos.add(duplicarSoCondicionTrabajo);
            context.update("form:datosSoCondicionesTrabajos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                factorriesgo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesTrabajos:factorriesgo");
                factorriesgo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSoCondicionesTrabajos");
                bandera = 0;
                filtrarSoCondicionesTrabajos = null;
                tipoLista = 0;
            }
            duplicarSoCondicionTrabajo = new SoCondicionesTrabajos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroSoCondicionesTrabajo.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarSoCondicionesTrabajos() {
        duplicarSoCondicionTrabajo = new SoCondicionesTrabajos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSoCondicionesTrabajosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CONDICIONESTRABAJOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSoCondicionesTrabajosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CONDICIONESTRABAJOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listSoCondicionesTrabajos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "SOCONDICIONESTRABAJOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("SOCONDICIONESTRABAJOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

//*****
    public List<SoCondicionesTrabajos> getListSoCondicionesTrabajos() {
        if (listSoCondicionesTrabajos == null) {
            listSoCondicionesTrabajos = administrarSoCondicionesTrabajos.mostrarSoCondicionesTrabajos();
        }
        return listSoCondicionesTrabajos;
    }

    public void setListSoCondicionesTrabajos(List<SoCondicionesTrabajos> listSoCondicionesTrabajos) {
        this.listSoCondicionesTrabajos = listSoCondicionesTrabajos;
    }

    public List<SoCondicionesTrabajos> getFiltrarSoCondicionesTrabajos() {
        return filtrarSoCondicionesTrabajos;
    }

    public void setFiltrarSoCondicionesTrabajos(List<SoCondicionesTrabajos> filtrarSoCondicionesTrabajos) {
        this.filtrarSoCondicionesTrabajos = filtrarSoCondicionesTrabajos;
    }

    public SoCondicionesTrabajos getNuevaSoCondicionTrabajo() {
        return nuevaSoCondicionTrabajo;
    }

    public void setNuevaSoCondicionTrabajo(SoCondicionesTrabajos nuevaSoCondicionTrabajo) {
        this.nuevaSoCondicionTrabajo = nuevaSoCondicionTrabajo;
    }

    public SoCondicionesTrabajos getDuplicarSoCondicionTrabajo() {
        return duplicarSoCondicionTrabajo;
    }

    public void setDuplicarSoCondicionTrabajo(SoCondicionesTrabajos duplicarSoCondicionTrabajo) {
        this.duplicarSoCondicionTrabajo = duplicarSoCondicionTrabajo;
    }

    public SoCondicionesTrabajos getEditarSoCondicionTrabajo() {
        return editarSoCondicionTrabajo;
    }

    public void setEditarSoCondicionTrabajo(SoCondicionesTrabajos editarSoCondicionTrabajo) {
        this.editarSoCondicionTrabajo = editarSoCondicionTrabajo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

}

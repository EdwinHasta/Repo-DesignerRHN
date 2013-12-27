/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.SoCondicionesAmbientalesP;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSoCondicionesAmbientalesPInterface;
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
public class ControlSoCondicionesAmbientalesP implements Serializable {

    @EJB
    AdministrarSoCondicionesAmbientalesPInterface administrarSoCondicionesAmbientalesP;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP;
    private List<SoCondicionesAmbientalesP> filtrarSoCondicionesAmbientalesP;
    private List<SoCondicionesAmbientalesP> crearSoCondicionesAmbientalesP;
    private List<SoCondicionesAmbientalesP> modificarSoCondicionesAmbientalesP;
    private List<SoCondicionesAmbientalesP> borrarSoCondicionesAmbientalesP;
    private SoCondicionesAmbientalesP nuevaSoCondicionAmbientalP;
    private SoCondicionesAmbientalesP duplicarSoCondicionAmbientalP;
    private SoCondicionesAmbientalesP editarSoCondicionAmbientalP;
    private BigDecimal verificarBorradoAccidentes;

    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;

    public ControlSoCondicionesAmbientalesP() {
        listSoCondicionesAmbientalesP = null;
        crearSoCondicionesAmbientalesP = new ArrayList<SoCondicionesAmbientalesP>();
        modificarSoCondicionesAmbientalesP = new ArrayList<SoCondicionesAmbientalesP>();
        borrarSoCondicionesAmbientalesP = new ArrayList<SoCondicionesAmbientalesP>();
        permitirIndex = true;
        editarSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
        nuevaSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
        duplicarSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
        guardado = true;
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
            secRegistro = listSoCondicionesAmbientalesP.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLSOCONDICIONESAMBIENTALESP  AsignarIndex \n");
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
            System.out.println("ERROR CONTROLSOCONDICIONESAMBIENTALESP asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSoCondicionesAmbientalesP");
            bandera = 0;
            filtrarSoCondicionesAmbientalesP = null;
            tipoLista = 0;
        }

        borrarSoCondicionesAmbientalesP.clear();
        crearSoCondicionesAmbientalesP.clear();
        modificarSoCondicionesAmbientalesP.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listSoCondicionesAmbientalesP = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:datosSoCondicionesAmbientalesP");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:codigo");
            codigo.setFilterStyle("width: 120px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:descripcion");
            descripcion.setFilterStyle("width: 600px");
            RequestContext.getCurrentInstance().update("form:datosSoCondicionesAmbientalesP");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSoCondicionesAmbientalesP");
            bandera = 0;
            filtrarSoCondicionesAmbientalesP = null;
            tipoLista = 0;
        }
    }

    public void modificandoSoCondicionAmbientalP(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("MODIFICAR  SO CONDICIONES AMBIENTALES P");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("MODIFICANDO CLASE ACCIDENTE CONFIRMAR CAMBIO = N");
            if (tipoLista == 0) {
                if (!crearSoCondicionesAmbientalesP.contains(listSoCondicionesAmbientalesP.get(indice))) {
                    if (listSoCondicionesAmbientalesP.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listSoCondicionesAmbientalesP.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listSoCondicionesAmbientalesP.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listSoCondicionesAmbientalesP.size(); j++) {
                            if (j != indice) {
                                if (listSoCondicionesAmbientalesP.get(indice).getCodigo().equals(listSoCondicionesAmbientalesP.get(j).getCodigo())) {
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
                    if (listSoCondicionesAmbientalesP.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listSoCondicionesAmbientalesP.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarSoCondicionesAmbientalesP.isEmpty()) {
                            modificarSoCondicionesAmbientalesP.add(listSoCondicionesAmbientalesP.get(indice));
                        } else if (!modificarSoCondicionesAmbientalesP.contains(listSoCondicionesAmbientalesP.get(indice))) {
                            modificarSoCondicionesAmbientalesP.add(listSoCondicionesAmbientalesP.get(indice));
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

                if (!crearSoCondicionesAmbientalesP.contains(filtrarSoCondicionesAmbientalesP.get(indice))) {
                    if (filtrarSoCondicionesAmbientalesP.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarSoCondicionesAmbientalesP.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listSoCondicionesAmbientalesP.size(); j++) {
                            System.err.println("indice lista  indice : " + listSoCondicionesAmbientalesP.get(j).getCodigo());
                            if (filtrarSoCondicionesAmbientalesP.get(indice).getCodigo().equals(listSoCondicionesAmbientalesP.get(j).getCodigo())) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarSoCondicionesAmbientalesP.size(); j++) {
                            System.err.println("indice filtrar indice : " + filtrarSoCondicionesAmbientalesP.get(j).getCodigo());
                            if (j == indice) {
                                if (filtrarSoCondicionesAmbientalesP.get(indice).getCodigo().equals(filtrarSoCondicionesAmbientalesP.get(j).getCodigo())) {
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

                    if (filtrarSoCondicionesAmbientalesP.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarSoCondicionesAmbientalesP.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarSoCondicionesAmbientalesP.isEmpty()) {
                            modificarSoCondicionesAmbientalesP.add(filtrarSoCondicionesAmbientalesP.get(indice));
                        } else if (!modificarSoCondicionesAmbientalesP.contains(filtrarSoCondicionesAmbientalesP.get(indice))) {
                            modificarSoCondicionesAmbientalesP.add(filtrarSoCondicionesAmbientalesP.get(indice));
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
            context.update("form:datosSoCondicionesAmbientalesP");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoSoCondicionesAmbientalesP() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("borrandoSoCondicionesAmbientalesP");
                if (!modificarSoCondicionesAmbientalesP.isEmpty() && modificarSoCondicionesAmbientalesP.contains(listSoCondicionesAmbientalesP.get(index))) {
                    int modIndex = modificarSoCondicionesAmbientalesP.indexOf(listSoCondicionesAmbientalesP.get(index));
                    modificarSoCondicionesAmbientalesP.remove(modIndex);
                    borrarSoCondicionesAmbientalesP.add(listSoCondicionesAmbientalesP.get(index));
                } else if (!crearSoCondicionesAmbientalesP.isEmpty() && crearSoCondicionesAmbientalesP.contains(listSoCondicionesAmbientalesP.get(index))) {
                    int crearIndex = crearSoCondicionesAmbientalesP.indexOf(listSoCondicionesAmbientalesP.get(index));
                    crearSoCondicionesAmbientalesP.remove(crearIndex);
                } else {
                    borrarSoCondicionesAmbientalesP.add(listSoCondicionesAmbientalesP.get(index));
                }
                listSoCondicionesAmbientalesP.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoSoCondicionesAmbientalesP");
                if (!modificarSoCondicionesAmbientalesP.isEmpty() && modificarSoCondicionesAmbientalesP.contains(filtrarSoCondicionesAmbientalesP.get(index))) {
                    int modIndex = modificarSoCondicionesAmbientalesP.indexOf(filtrarSoCondicionesAmbientalesP.get(index));
                    modificarSoCondicionesAmbientalesP.remove(modIndex);
                    borrarSoCondicionesAmbientalesP.add(filtrarSoCondicionesAmbientalesP.get(index));
                } else if (!crearSoCondicionesAmbientalesP.isEmpty() && crearSoCondicionesAmbientalesP.contains(filtrarSoCondicionesAmbientalesP.get(index))) {
                    int crearIndex = crearSoCondicionesAmbientalesP.indexOf(filtrarSoCondicionesAmbientalesP.get(index));
                    crearSoCondicionesAmbientalesP.remove(crearIndex);
                } else {
                    borrarSoCondicionesAmbientalesP.add(filtrarSoCondicionesAmbientalesP.get(index));
                }
                int VCIndex = listSoCondicionesAmbientalesP.indexOf(filtrarSoCondicionesAmbientalesP.get(index));
                listSoCondicionesAmbientalesP.remove(VCIndex);
                filtrarSoCondicionesAmbientalesP.remove(index);

            }
            context.update("form:datosSoCondicionesAmbientalesP");
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
            if (tipoLista == 0) {
                verificarBorradoAccidentes = administrarSoCondicionesAmbientalesP.verificarSoAccidentesMedicos(listSoCondicionesAmbientalesP.get(index).getSecuencia());
            } else {
                verificarBorradoAccidentes = administrarSoCondicionesAmbientalesP.verificarSoAccidentesMedicos(filtrarSoCondicionesAmbientalesP.get(index).getSecuencia());
            }
            if (verificarBorradoAccidentes.intValueExact() == 0) {
                System.out.println("Borrado==0");
                borrandoSoCondicionesAmbientalesP();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarBorradoAccidentes = new BigDecimal(-1);
            }
        } catch (Exception e) {
            System.err.println("ERROR CLASES ACCIDENTES verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarSoCondicionesAmbientalesP.isEmpty() || !crearSoCondicionesAmbientalesP.isEmpty() || !modificarSoCondicionesAmbientalesP.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoSoCondicionesAmbientalesP() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("REALIZANDO SO CONDICIONES AMBIENTALES P");
            if (!borrarSoCondicionesAmbientalesP.isEmpty()) {
                for (int i = 0; i < borrarSoCondicionesAmbientalesP.size(); i++) {
                    System.out.println("Borrando...");
                    administrarSoCondicionesAmbientalesP.borrarSoCondicionesAmbientalesP(borrarSoCondicionesAmbientalesP.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarSoCondicionesAmbientalesP.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarSoCondicionesAmbientalesP.clear();
            }
            if (!crearSoCondicionesAmbientalesP.isEmpty()) {
                for (int i = 0; i < crearSoCondicionesAmbientalesP.size(); i++) {

                    System.out.println("Creando...");
                    administrarSoCondicionesAmbientalesP.crearSoCondicionesAmbientalesP(crearSoCondicionesAmbientalesP.get(i));

                }
                crearSoCondicionesAmbientalesP.clear();
            }
            if (!modificarSoCondicionesAmbientalesP.isEmpty()) {
                administrarSoCondicionesAmbientalesP.modificarSoCondicionesAmbientalesP(modificarSoCondicionesAmbientalesP);
                modificarSoCondicionesAmbientalesP.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listSoCondicionesAmbientalesP = null;
            context.update("form:datosSoCondicionesAmbientalesP");
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
                editarSoCondicionAmbientalP = listSoCondicionesAmbientalesP.get(index);
            }
            if (tipoLista == 1) {
                editarSoCondicionAmbientalP = filtrarSoCondicionesAmbientalesP.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editDescripcion");
                context.execute("editDescripcion.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevaSoCondicionAmbiental() {
        System.out.println("agregarNuevaSoCondicionAmbiental");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaSoCondicionAmbientalP.getCodigo() == null) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevaSoCondicionAmbientalP.getCodigo());

            for (int x = 0; x < listSoCondicionesAmbientalesP.size(); x++) {
                if (listSoCondicionesAmbientalesP.get(x).getCodigo().equals(nuevaSoCondicionAmbientalP.getCodigo())) {
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
        if (nuevaSoCondicionAmbientalP.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener Un Descripción \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSoCondicionesAmbientalesP");
                bandera = 0;
                filtrarSoCondicionesAmbientalesP = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevaSoCondicionAmbientalP.setSecuencia(l);

            crearSoCondicionesAmbientalesP.add(nuevaSoCondicionAmbientalP);

            listSoCondicionesAmbientalesP.add(nuevaSoCondicionAmbientalP);
            nuevaSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
            context.update("form:datosSoCondicionesAmbientalesP");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroSoCondicionesAmbientalesPeligrosaP.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoSoCondicionesAmbientalesP() {
        System.out.println("limpiarSoCondicionesAmbientalesP");
        nuevaSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoSoCondicionAmbientalP() {
        System.out.println("duplicandoSoCondicionAmbientalP");
        if (index >= 0) {
            duplicarSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarSoCondicionAmbientalP.setSecuencia(l);
                duplicarSoCondicionAmbientalP.setCodigo(listSoCondicionesAmbientalesP.get(index).getCodigo());
                duplicarSoCondicionAmbientalP.setDescripcion(listSoCondicionesAmbientalesP.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarSoCondicionAmbientalP.setSecuencia(l);
                duplicarSoCondicionAmbientalP.setCodigo(filtrarSoCondicionesAmbientalesP.get(index).getCodigo());
                duplicarSoCondicionAmbientalP.setDescripcion(filtrarSoCondicionesAmbientalesP.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarRCAP");
            context.execute("duplicarRegistroSoCondicionesAmbientalesP.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("CONFIRMAR DUPLICAR SO CONDICIONES AMBIENTALES P");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarSoCondicionAmbientalP.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarSoCondicionAmbientalP.getDescripcion());

        if (duplicarSoCondicionAmbientalP.getCodigo() == null || duplicarSoCondicionAmbientalP.getCodigo().equals(" ") || duplicarSoCondicionAmbientalP.getCodigo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            for (int x = 0; x < listSoCondicionesAmbientalesP.size(); x++) {
                if (listSoCondicionesAmbientalesP.get(x).getCodigo().equals(duplicarSoCondicionAmbientalP.getCodigo())) {
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
        if (duplicarSoCondicionAmbientalP.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarSoCondicionAmbientalP.getSecuencia() + "  " + duplicarSoCondicionAmbientalP.getCodigo());
            if (crearSoCondicionesAmbientalesP.contains(duplicarSoCondicionAmbientalP)) {
                System.out.println("Ya lo contengo.");
            }
            listSoCondicionesAmbientalesP.add(duplicarSoCondicionAmbientalP);
            crearSoCondicionesAmbientalesP.add(duplicarSoCondicionAmbientalP);
            context.update("form:datosSoCondicionesAmbientalesP");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSoCondicionesAmbientalesP:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSoCondicionesAmbientalesP");
                bandera = 0;
                filtrarSoCondicionesAmbientalesP = null;
                tipoLista = 0;
            }
            duplicarSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
            RequestContext.getCurrentInstance().execute("duplicarRegistroSoCondicionesAmbientalesP.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarSoCondicionesAmbientalesP() {
        duplicarSoCondicionAmbientalP = new SoCondicionesAmbientalesP();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSoCondicionesAmbientalesPExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CONDICIONESAMBIENTALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSoCondicionesAmbientalesPExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CONDICIONESAMBIENTALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listSoCondicionesAmbientalesP.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "SOCONDICIONESAMBIENTALESP"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("SOCONDICIONESAMBIENTALESP")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<SoCondicionesAmbientalesP> getListSoCondicionesAmbientalesP() {
        if (listSoCondicionesAmbientalesP == null) {
            listSoCondicionesAmbientalesP = administrarSoCondicionesAmbientalesP.mostrarSoCondicionesAmbientalesP();
        }
        return listSoCondicionesAmbientalesP;
    }

    public void setListSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP) {
        this.listSoCondicionesAmbientalesP = listSoCondicionesAmbientalesP;
    }

    public List<SoCondicionesAmbientalesP> getFiltrarSoCondicionesAmbientalesP() {
        return filtrarSoCondicionesAmbientalesP;
    }

    public void setFiltrarSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> filtrarSoCondicionesAmbientalesP) {
        this.filtrarSoCondicionesAmbientalesP = filtrarSoCondicionesAmbientalesP;
    }

    public SoCondicionesAmbientalesP getNuevaSoCondicionAmbientalP() {
        return nuevaSoCondicionAmbientalP;
    }

    public void setNuevaSoCondicionAmbientalP(SoCondicionesAmbientalesP nuevaSoCondicionAmbientalP) {
        this.nuevaSoCondicionAmbientalP = nuevaSoCondicionAmbientalP;
    }

    public SoCondicionesAmbientalesP getDuplicarSoCondicionAmbientalP() {
        return duplicarSoCondicionAmbientalP;
    }

    public void setDuplicarSoCondicionAmbientalP(SoCondicionesAmbientalesP duplicarSoCondicionAmbientalP) {
        this.duplicarSoCondicionAmbientalP = duplicarSoCondicionAmbientalP;
    }

    public SoCondicionesAmbientalesP getEditarSoCondicionAmbientalP() {
        return editarSoCondicionAmbientalP;
    }

    public void setEditarSoCondicionAmbientalP(SoCondicionesAmbientalesP editarSoCondicionAmbientalP) {
        this.editarSoCondicionAmbientalP = editarSoCondicionAmbientalP;
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

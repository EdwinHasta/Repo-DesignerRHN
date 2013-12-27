/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.SoActosInseguros;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSoActosInsegurosInterface;
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
 * @author John Pineda
 */
@ManagedBean
@SessionScoped
public class ControlSoActosInseguros implements Serializable {

    @EJB
    AdministrarSoActosInsegurosInterface administrarSoActosInseguros;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<SoActosInseguros> listSoActosInseguros;
    private List<SoActosInseguros> filtrarSoActosInseguros;
    private List<SoActosInseguros> crearSoActosInseguros;
    private List<SoActosInseguros> modificarSoActosInseguros;
    private List<SoActosInseguros> borrarSoActosInseguros;
    private SoActosInseguros nuevaSoActoInseguro;
    private SoActosInseguros duplicarSoActoInseguro;
    private SoActosInseguros editarSoActoInseguro;
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
    private BigDecimal verificarSoAccidentesMedicos;

    public ControlSoActosInseguros() {
        listSoActosInseguros = null;
        crearSoActosInseguros = new ArrayList<SoActosInseguros>();
        modificarSoActosInseguros = new ArrayList<SoActosInseguros>();
        borrarSoActosInseguros = new ArrayList<SoActosInseguros>();
        permitirIndex = true;
        editarSoActoInseguro = new SoActosInseguros();
        nuevaSoActoInseguro = new SoActosInseguros();
        duplicarSoActoInseguro = new SoActosInseguros();
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
            secRegistro = listSoActosInseguros.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLSOACTOSINSEGUROS  AsignarIndex \n");
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
            System.out.println("ERROR CONTROLSOACTOSINSEGUROS asignarIndex ERROR =" + e);
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
            filtrarSoActosInseguros = null;
            tipoLista = 0;
        }

        borrarSoActosInseguros.clear();
        crearSoActosInseguros.clear();
        modificarSoActosInseguros.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listSoActosInseguros = null;
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
            filtrarSoActosInseguros = null;
            tipoLista = 0;
        }
    }

    public void modificandoSoActosInseguros(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearSoActosInseguros.contains(listSoActosInseguros.get(indice))) {
                    if (listSoActosInseguros.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listSoActosInseguros.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listSoActosInseguros.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listSoActosInseguros.size(); j++) {
                            if (j != indice) {
                                if (listSoActosInseguros.get(indice).getCodigo().equals(listSoActosInseguros.get(j).getCodigo())) {
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
                    if (listSoActosInseguros.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listSoActosInseguros.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarSoActosInseguros.isEmpty()) {
                            modificarSoActosInseguros.add(listSoActosInseguros.get(indice));
                        } else if (!modificarSoActosInseguros.contains(listSoActosInseguros.get(indice))) {
                            modificarSoActosInseguros.add(listSoActosInseguros.get(indice));
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

                if (!crearSoActosInseguros.contains(filtrarSoActosInseguros.get(indice))) {
                    if (filtrarSoActosInseguros.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarSoActosInseguros.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listSoActosInseguros.size(); j++) {
                            if (filtrarSoActosInseguros.get(indice).getCodigo().equals(listSoActosInseguros.get(j).getCodigo())) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarSoActosInseguros.size(); j++) {
                            if (j != indice) {
                                if (filtrarSoActosInseguros.get(indice).getCodigo().equals(filtrarSoActosInseguros.get(j).getCodigo())) {
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

                    if (filtrarSoActosInseguros.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarSoActosInseguros.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarSoActosInseguros.isEmpty()) {
                            modificarSoActosInseguros.add(filtrarSoActosInseguros.get(indice));
                        } else if (!modificarSoActosInseguros.contains(filtrarSoActosInseguros.get(indice))) {
                            modificarSoActosInseguros.add(filtrarSoActosInseguros.get(indice));
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

    public void borrandoSoActosInseguros() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("borrandoSoActosInseguros");
                if (!modificarSoActosInseguros.isEmpty() && modificarSoActosInseguros.contains(listSoActosInseguros.get(index))) {
                    int modIndex = modificarSoActosInseguros.indexOf(listSoActosInseguros.get(index));
                    modificarSoActosInseguros.remove(modIndex);
                    borrarSoActosInseguros.add(listSoActosInseguros.get(index));
                } else if (!crearSoActosInseguros.isEmpty() && crearSoActosInseguros.contains(listSoActosInseguros.get(index))) {
                    int crearIndex = crearSoActosInseguros.indexOf(listSoActosInseguros.get(index));
                    crearSoActosInseguros.remove(crearIndex);
                } else {
                    borrarSoActosInseguros.add(listSoActosInseguros.get(index));
                }
                listSoActosInseguros.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoSoActosInseguros");
                if (!modificarSoActosInseguros.isEmpty() && modificarSoActosInseguros.contains(filtrarSoActosInseguros.get(index))) {
                    int modIndex = modificarSoActosInseguros.indexOf(filtrarSoActosInseguros.get(index));
                    modificarSoActosInseguros.remove(modIndex);
                    borrarSoActosInseguros.add(filtrarSoActosInseguros.get(index));
                } else if (!crearSoActosInseguros.isEmpty() && crearSoActosInseguros.contains(filtrarSoActosInseguros.get(index))) {
                    int crearIndex = crearSoActosInseguros.indexOf(filtrarSoActosInseguros.get(index));
                    crearSoActosInseguros.remove(crearIndex);
                } else {
                    borrarSoActosInseguros.add(filtrarSoActosInseguros.get(index));
                }
                int VCIndex = listSoActosInseguros.indexOf(filtrarSoActosInseguros.get(index));
                listSoActosInseguros.remove(VCIndex);
                filtrarSoActosInseguros.remove(index);

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
                verificarSoAccidentesMedicos = administrarSoActosInseguros.verificarSoAccidentesMedicos(listSoActosInseguros.get(index).getSecuencia());
            } else {
                verificarSoAccidentesMedicos = administrarSoActosInseguros.verificarSoAccidentesMedicos(filtrarSoActosInseguros.get(index).getSecuencia());
            }
            if (verificarSoAccidentesMedicos.intValueExact() == 0) {
                System.out.println("Borrado==0");
                borrandoSoActosInseguros();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarSoAccidentesMedicos = new BigDecimal(-1);
            }
        } catch (Exception e) {
            System.err.println("ERROR CLASES ACCIDENTES verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarSoActosInseguros.isEmpty() || !crearSoActosInseguros.isEmpty() || !modificarSoActosInseguros.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoSoActosInseguros() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("REALIZANDO SO ACTPS INSEGUROS");
            if (!borrarSoActosInseguros.isEmpty()) {
                for (int i = 0; i < borrarSoActosInseguros.size(); i++) {
                    System.out.println("Borrando...");
                    administrarSoActosInseguros.borrarSoActosInseguros(borrarSoActosInseguros.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarSoActosInseguros.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarSoActosInseguros.clear();
            }
            if (!crearSoActosInseguros.isEmpty()) {
                for (int i = 0; i < crearSoActosInseguros.size(); i++) {

                    System.out.println("Creando...");
                    administrarSoActosInseguros.crearSoActosInseguros(crearSoActosInseguros.get(i));

                }
                crearSoActosInseguros.clear();
            }
            if (!modificarSoActosInseguros.isEmpty()) {
                administrarSoActosInseguros.modificarSoActosInseguros(modificarSoActosInseguros);
                modificarSoActosInseguros.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listSoActosInseguros = null;
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
                editarSoActoInseguro = listSoActosInseguros.get(index);
            }
            if (tipoLista == 1) {
                editarSoActoInseguro = filtrarSoActosInseguros.get(index);
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

    public void agregarNuevoSoActoInseguro() {
        System.out.println("agregarNuevoSoActoInseguro");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaSoActoInseguro.getCodigo() == null) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevaSoActoInseguro.getCodigo());

            for (int x = 0; x < listSoActosInseguros.size(); x++) {
                if (listSoActosInseguros.get(x).getCodigo().equals(nuevaSoActoInseguro.getCodigo())) {
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
        if (nuevaSoActoInseguro.getDescripcion() == (null)) {
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
                filtrarSoActosInseguros = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevaSoActoInseguro.setSecuencia(l);

            crearSoActosInseguros.add(nuevaSoActoInseguro);

            listSoActosInseguros.add(nuevaSoActoInseguro);
            nuevaSoActoInseguro = new SoActosInseguros();
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

    public void limpiarNuevoaSoActoInseguro() {
        System.out.println("limpiarNuevoaSoActoInseguro");
        nuevaSoActoInseguro = new SoActosInseguros();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoSoActoInseguro() {
        System.out.println("duplicandoSoActoInseguro");
        if (index >= 0) {
            duplicarSoActoInseguro = new SoActosInseguros();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarSoActoInseguro.setSecuencia(l);
                duplicarSoActoInseguro.setCodigo(listSoActosInseguros.get(index).getCodigo());
                duplicarSoActoInseguro.setDescripcion(listSoActosInseguros.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarSoActoInseguro.setSecuencia(l);
                duplicarSoActoInseguro.setCodigo(filtrarSoActosInseguros.get(index).getCodigo());
                duplicarSoActoInseguro.setDescripcion(filtrarSoActosInseguros.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarRCAP");
            context.execute("duplicarRegistroSoCondicionesAmbientalesP.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarSoActoInseguro.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarSoActoInseguro.getDescripcion());

        if (duplicarSoActoInseguro.getCodigo() == null || duplicarSoActoInseguro.getCodigo().equals(" ") || duplicarSoActoInseguro.getCodigo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            for (int x = 0; x < listSoActosInseguros.size(); x++) {
                if (listSoActosInseguros.get(x).getCodigo().equals(duplicarSoActoInseguro.getCodigo())) {
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
        if (duplicarSoActoInseguro.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarSoActoInseguro.getSecuencia() + "  " + duplicarSoActoInseguro.getCodigo());
            if (crearSoActosInseguros.contains(duplicarSoActoInseguro)) {
                System.out.println("Ya lo contengo.");
            }
            listSoActosInseguros.add(duplicarSoActoInseguro);
            crearSoActosInseguros.add(duplicarSoActoInseguro);
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
                filtrarSoActosInseguros = null;
                tipoLista = 0;
            }
            duplicarSoActoInseguro = new SoActosInseguros();
            RequestContext.getCurrentInstance().execute("duplicarRegistroSoCondicionesAmbientalesP.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarSoActosInseguros() {
        duplicarSoActoInseguro = new SoActosInseguros();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSoCondicionesAmbientalesPExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ACTOSINSEGUROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSoCondicionesAmbientalesPExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ACTOSINSEGUROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listSoActosInseguros.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "SOACTOSINSEGUROS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("SOACTOSINSEGUROS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<SoActosInseguros> getListSoActosInseguros() {
        if (listSoActosInseguros == null) {
            listSoActosInseguros = administrarSoActosInseguros.mostrarSoActosInseguros();
        }
        return listSoActosInseguros;
    }

    public void setListSoActosInseguros(List<SoActosInseguros> listSoActosInseguros) {
        this.listSoActosInseguros = listSoActosInseguros;
    }

    public List<SoActosInseguros> getFiltrarSoActosInseguros() {
        return filtrarSoActosInseguros;
    }

    public void setFiltrarSoActosInseguros(List<SoActosInseguros> filtrarSoActosInseguros) {
        this.filtrarSoActosInseguros = filtrarSoActosInseguros;
    }

    public List<SoActosInseguros> getModificarSoActosInseguros() {
        return modificarSoActosInseguros;
    }

    public void setModificarSoActosInseguros(List<SoActosInseguros> modificarSoActosInseguros) {
        this.modificarSoActosInseguros = modificarSoActosInseguros;
    }

    public SoActosInseguros getNuevaSoActoInseguro() {
        return nuevaSoActoInseguro;
    }

    public void setNuevaSoActoInseguro(SoActosInseguros nuevaSoActoInseguro) {
        this.nuevaSoActoInseguro = nuevaSoActoInseguro;
    }

    public SoActosInseguros getDuplicarSoActoInseguro() {
        return duplicarSoActoInseguro;
    }

    public void setDuplicarSoActoInseguro(SoActosInseguros duplicarSoActoInseguro) {
        this.duplicarSoActoInseguro = duplicarSoActoInseguro;
    }

    public SoActosInseguros getEditarSoActoInseguro() {
        return editarSoActoInseguro;
    }

    public void setEditarSoActoInseguro(SoActosInseguros editarSoActoInseguro) {
        this.editarSoActoInseguro = editarSoActoInseguro;
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

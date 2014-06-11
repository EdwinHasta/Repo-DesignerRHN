/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposFamiliares;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposFamiliaresInterface;
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
public class ControlTiposFamiliares implements Serializable {

    @EJB
    AdministrarTiposFamiliaresInterface administrarTiposFamiliares;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposFamiliares> listTiposFamiliares;
    private List<TiposFamiliares> filtrarTiposFamiliares;
    private List<TiposFamiliares> crearTiposFamiliares;
    private List<TiposFamiliares> modificarTiposFamiliares;
    private List<TiposFamiliares> borrarTiposFamiliares;
    private TiposFamiliares nuevoTipoFamiliar;
    private TiposFamiliares duplicarTipoFamiliar;
    private TiposFamiliares editarTipoFamiliar;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, parentesco;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;

    public ControlTiposFamiliares() {
        listTiposFamiliares = null;
        crearTiposFamiliares = new ArrayList<TiposFamiliares>();
        modificarTiposFamiliares = new ArrayList<TiposFamiliares>();
        borrarTiposFamiliares = new ArrayList<TiposFamiliares>();
        permitirIndex = true;
        editarTipoFamiliar = new TiposFamiliares();
        nuevoTipoFamiliar = new TiposFamiliares();
        duplicarTipoFamiliar = new TiposFamiliares();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposFamiliares.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposFamiliares.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposFamiliares eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposFamiliares.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposFamiliares.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposFamiliares.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosTipoFamiliar");
            bandera = 0;
            filtrarTiposFamiliares = null;
            tipoLista = 0;
        }

        borrarTiposFamiliares.clear();
        crearTiposFamiliares.clear();
        modificarTiposFamiliares.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposFamiliares = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoFamiliar");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:codigo");
            codigo.setFilterStyle("width: 270px");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:parentesco");
            parentesco.setFilterStyle("width: 450px");
            RequestContext.getCurrentInstance().update("form:datosTipoFamiliar");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoFamiliar");
            bandera = 0;
            filtrarTiposFamiliares = null;
            tipoLista = 0;
        }
    }

    public void modificarTipoFamiliar(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO FAMILIARES");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EXAMEN, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposFamiliares.contains(listTiposFamiliares.get(indice))) {
                    if (listTiposFamiliares.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (listTiposFamiliares.get(indice).getCodigo() == listTiposFamiliares.get(j).getCodigo()) {
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
                    if (listTiposFamiliares.get(indice).getTipo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposFamiliares.get(indice).getTipo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposFamiliares.isEmpty()) {
                            modificarTiposFamiliares.add(listTiposFamiliares.get(indice));
                        } else if (!modificarTiposFamiliares.contains(listTiposFamiliares.get(indice))) {
                            modificarTiposFamiliares.add(listTiposFamiliares.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearTiposFamiliares.contains(filtrarTiposFamiliares.get(indice))) {
                    if (filtrarTiposFamiliares.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposFamiliares.get(indice).getCodigo() == listTiposFamiliares.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposFamiliares.get(indice).getCodigo() == filtrarTiposFamiliares.get(j).getCodigo()) {
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

                    if (filtrarTiposFamiliares.get(indice).getTipo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposFamiliares.get(indice).getTipo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposFamiliares.isEmpty()) {
                            modificarTiposFamiliares.add(filtrarTiposFamiliares.get(indice));
                        } else if (!modificarTiposFamiliares.contains(filtrarTiposFamiliares.get(indice))) {
                            modificarTiposFamiliares.add(filtrarTiposFamiliares.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosTipoFamiliar");
        }

    }

    public void borrandoTiposFamiliares() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposFamiliares");
                if (!modificarTiposFamiliares.isEmpty() && modificarTiposFamiliares.contains(listTiposFamiliares.get(index))) {
                    int modIndex = modificarTiposFamiliares.indexOf(listTiposFamiliares.get(index));
                    modificarTiposFamiliares.remove(modIndex);
                    borrarTiposFamiliares.add(listTiposFamiliares.get(index));
                } else if (!crearTiposFamiliares.isEmpty() && crearTiposFamiliares.contains(listTiposFamiliares.get(index))) {
                    int crearIndex = crearTiposFamiliares.indexOf(listTiposFamiliares.get(index));
                    crearTiposFamiliares.remove(crearIndex);
                } else {
                    borrarTiposFamiliares.add(listTiposFamiliares.get(index));
                }
                listTiposFamiliares.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposFamiliares");
                if (!modificarTiposFamiliares.isEmpty() && modificarTiposFamiliares.contains(filtrarTiposFamiliares.get(index))) {
                    int modIndex = modificarTiposFamiliares.indexOf(filtrarTiposFamiliares.get(index));
                    modificarTiposFamiliares.remove(modIndex);
                    borrarTiposFamiliares.add(filtrarTiposFamiliares.get(index));
                } else if (!crearTiposFamiliares.isEmpty() && crearTiposFamiliares.contains(filtrarTiposFamiliares.get(index))) {
                    int crearIndex = crearTiposFamiliares.indexOf(filtrarTiposFamiliares.get(index));
                    crearTiposFamiliares.remove(crearIndex);
                } else {
                    borrarTiposFamiliares.add(filtrarTiposFamiliares.get(index));
                }
                int VCIndex = listTiposFamiliares.indexOf(filtrarTiposFamiliares.get(index));
                listTiposFamiliares.remove(VCIndex);
                filtrarTiposFamiliares.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoFamiliar");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");

            }
        }

    }

    public void verificarBorrado() {
        BigInteger hvReferencias;

        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de ControlTiposFamiliares ");
            if (tipoLista == 0) {
                hvReferencias = administrarTiposFamiliares.contarHvReferenciasTipoFamiliar(listTiposFamiliares.get(index).getSecuencia());
            } else {
                hvReferencias = administrarTiposFamiliares.contarHvReferenciasTipoFamiliar(listTiposFamiliares.get(index).getSecuencia());
            }
            if (hvReferencias.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposFamiliares();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                hvReferencias = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposFamiliares verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposFamiliares.isEmpty() || !crearTiposFamiliares.isEmpty() || !modificarTiposFamiliares.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTipoFamiliar() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando ControlTiposFamiliares");
            if (!borrarTiposFamiliares.isEmpty()) {
              administrarTiposFamiliares.borrarTiposFamiliares(borrarTiposFamiliares);
                
                //mostrarBorrados
                registrosBorrados = borrarTiposFamiliares.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposFamiliares.clear();
            }
            if (!crearTiposFamiliares.isEmpty()) {
                administrarTiposFamiliares.crearTiposFamiliares(crearTiposFamiliares);

                crearTiposFamiliares.clear();
            }
            if (!modificarTiposFamiliares.isEmpty()) {
                administrarTiposFamiliares.modificarTiposFamiliares(modificarTiposFamiliares);
                modificarTiposFamiliares.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposFamiliares = null;
            context.update("form:datosTipoFamiliar");
            k = 0;
        }
        index = -1;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoFamiliar = listTiposFamiliares.get(index);
            }
            if (tipoLista == 1) {
                editarTipoFamiliar = filtrarTiposFamiliares.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editParentesco");
                context.execute("editParentesco.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void mostrarDialogoNuevoTiposFamiliares() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:nuevaTipoFamiliar");
        context.execute("nuevoRegistroTiposFamiliar.show()");
    }

    public void agregarNuevoTiposFamiliares() {
        System.out.println("agregarNuevoTiposFamiliares");
        RequestContext context = RequestContext.getCurrentInstance();

        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        if (nuevoTipoFamiliar.getCodigo() == a) {
            mensajeValidacion = " *debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoFamiliar.getCodigo());

            for (int x = 0; x < listTiposFamiliares.size(); x++) {
                if (listTiposFamiliares.get(x).getCodigo() == nuevoTipoFamiliar.getCodigo()) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoTipoFamiliar.getTipo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Debe tener un parentesco \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoTipoFamiliar.getTipo().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe tener un parentesco  \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoFamiliar");
                bandera = 0;
                filtrarTiposFamiliares = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoFamiliar.setSecuencia(l);

            crearTiposFamiliares.add(nuevoTipoFamiliar);

            listTiposFamiliares.add(nuevoTipoFamiliar);
            nuevoTipoFamiliar = new TiposFamiliares();
            context.update("form:datosTipoFamiliar");
            if (guardado == true) {
                guardado = false;
            }
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

            context.execute("nuevoRegistroTiposFamiliar.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposFamiliares() {
        System.out.println("limpiarNuevoTiposFamiliares");
        nuevoTipoFamiliar = new TiposFamiliares();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposFamiliares() {
        System.out.println("duplicandoTiposExamenes");
        if (index >= 0) {
            duplicarTipoFamiliar = new TiposFamiliares();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoFamiliar.setSecuencia(l);
                duplicarTipoFamiliar.setCodigo(listTiposFamiliares.get(index).getCodigo());
                duplicarTipoFamiliar.setTipo(listTiposFamiliares.get(index).getTipo());
            }
            if (tipoLista == 1) {
                duplicarTipoFamiliar.setSecuencia(l);
                duplicarTipoFamiliar.setCodigo(filtrarTiposFamiliares.get(index).getCodigo());
                duplicarTipoFamiliar.setTipo(filtrarTiposFamiliares.get(index).getTipo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTF");
            context.execute("duplicarRegistroTiposFamiliares.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS EXAMENES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoFamiliar.getCodigo());
        System.err.println("ConfirmarDuplicar parentesco " + duplicarTipoFamiliar.getTipo());

        if (duplicarTipoFamiliar.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposFamiliares.size(); x++) {
                if (listTiposFamiliares.get(x).getCodigo() == duplicarTipoFamiliar.getCodigo()) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *que NO existan codigo repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
                duplicados = 0;
            }
        }
        if (duplicarTipoFamiliar.getTipo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un parentesco \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoFamiliar.getSecuencia() + "  " + duplicarTipoFamiliar.getCodigo());
            if (crearTiposFamiliares.contains(duplicarTipoFamiliar)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposFamiliares.add(duplicarTipoFamiliar);
            crearTiposFamiliares.add(duplicarTipoFamiliar);
            context.update("form:datosTipoFamiliar");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoFamiliar:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoFamiliar");
                bandera = 0;
                filtrarTiposFamiliares = null;
                tipoLista = 0;
            }
            duplicarTipoFamiliar = new TiposFamiliares();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposFamiliares.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposFamiliares() {
        duplicarTipoFamiliar = new TiposFamiliares();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoFamiliarExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSFAMILIARES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoFamiliarExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSFAMILIARES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposFamiliares.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSFAMILIARES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSFAMILIARES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposFamiliares> getListTiposFamiliares() {
        if (listTiposFamiliares == null) {
            listTiposFamiliares = administrarTiposFamiliares.consultarTiposFamiliares();

        }
        return listTiposFamiliares;
    }

    public void setListTiposFamiliares(List<TiposFamiliares> listTiposFamiliares) {
        this.listTiposFamiliares = listTiposFamiliares;
    }

    public List<TiposFamiliares> getFiltrarTiposFamiliares() {
        return filtrarTiposFamiliares;
    }

    public void setFiltrarTiposFamiliares(List<TiposFamiliares> filtrarTiposFamiliares) {
        this.filtrarTiposFamiliares = filtrarTiposFamiliares;
    }

    public TiposFamiliares getNuevoTipoFamiliar() {
        return nuevoTipoFamiliar;
    }

    public void setNuevoTipoFamiliar(TiposFamiliares nuevoTipoFamiliar) {
        this.nuevoTipoFamiliar = nuevoTipoFamiliar;
    }

    public TiposFamiliares getDuplicarTipoFamiliar() {
        return duplicarTipoFamiliar;
    }

    public void setDuplicarTipoFamiliar(TiposFamiliares duplicarTipoFamiliar) {
        this.duplicarTipoFamiliar = duplicarTipoFamiliar;
    }

    public TiposFamiliares getEditarTipoFamiliar() {
        return editarTipoFamiliar;
    }

    public void setEditarTipoFamiliar(TiposFamiliares editarTipoFamiliar) {
        this.editarTipoFamiliar = editarTipoFamiliar;
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

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

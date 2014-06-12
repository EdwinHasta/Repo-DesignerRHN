/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Tiposausentismos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposAusentismosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlTiposAusentismos implements Serializable {

    @EJB
    AdministrarTiposAusentismosInterface administrarTiposAusentismos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Tiposausentismos> listTiposAusentismos;
    private List<Tiposausentismos> filtrarTiposAusentismos;
    private List<Tiposausentismos> crearTiposAusentismos;
    private List<Tiposausentismos> modificarTiposAusentismos;
    private List<Tiposausentismos> borrarTiposAusentismos;
    private Tiposausentismos nuevoTiposAusentismos;
    private Tiposausentismos duplicarTiposAusentismos;
    private Tiposausentismos editarTiposAusentismos;
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
    //filtrado table
    private int tamano;

    public ControlTiposAusentismos() {
        listTiposAusentismos = null;
        crearTiposAusentismos = new ArrayList<Tiposausentismos>();
        modificarTiposAusentismos = new ArrayList<Tiposausentismos>();
        borrarTiposAusentismos = new ArrayList<Tiposausentismos>();
        permitirIndex = true;
        editarTiposAusentismos = new Tiposausentismos();
        nuevoTiposAusentismos = new Tiposausentismos();
        duplicarTiposAusentismos = new Tiposausentismos();
        guardado = true;
        tamano = 300;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposAusentismos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposAusentismos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposAusentismos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposAusentismos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposAusentismos.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposAusentismos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposAusentismos");
            bandera = 0;
            filtrarTiposAusentismos = null;
            tipoLista = 0;
        }

        borrarTiposAusentismos.clear();
        crearTiposAusentismos.clear();
        modificarTiposAusentismos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposAusentismos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposAusentismos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposAusentismos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 300;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposAusentismos");
            bandera = 0;
            filtrarTiposAusentismos = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposAusentismos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposAusentismos.contains(listTiposAusentismos.get(indice))) {
                    if (listTiposAusentismos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposAusentismos.size(); j++) {
                            if (j != indice) {
                                if (listTiposAusentismos.get(indice).getCodigo() == listTiposAusentismos.get(j).getCodigo()) {
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
                    if (listTiposAusentismos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposAusentismos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposAusentismos.isEmpty()) {
                            modificarTiposAusentismos.add(listTiposAusentismos.get(indice));
                        } else if (!modificarTiposAusentismos.contains(listTiposAusentismos.get(indice))) {
                            modificarTiposAusentismos.add(listTiposAusentismos.get(indice));
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

                if (!crearTiposAusentismos.contains(filtrarTiposAusentismos.get(indice))) {
                    if (filtrarTiposAusentismos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < filtrarTiposAusentismos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposAusentismos.get(indice).getCodigo() == listTiposAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposAusentismos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposAusentismos.get(indice).getCodigo() == listTiposAusentismos.get(j).getCodigo()) {
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

                    if (filtrarTiposAusentismos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposAusentismos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposAusentismos.isEmpty()) {
                            modificarTiposAusentismos.add(filtrarTiposAusentismos.get(indice));
                        } else if (!modificarTiposAusentismos.contains(filtrarTiposAusentismos.get(indice))) {
                            modificarTiposAusentismos.add(filtrarTiposAusentismos.get(indice));
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
            context.update("form:datosTiposAusentismos");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposAusentismos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposAusentismos");
                if (!modificarTiposAusentismos.isEmpty() && modificarTiposAusentismos.contains(listTiposAusentismos.get(index))) {
                    int modIndex = modificarTiposAusentismos.indexOf(listTiposAusentismos.get(index));
                    modificarTiposAusentismos.remove(modIndex);
                    borrarTiposAusentismos.add(listTiposAusentismos.get(index));
                } else if (!crearTiposAusentismos.isEmpty() && crearTiposAusentismos.contains(listTiposAusentismos.get(index))) {
                    int crearIndex = crearTiposAusentismos.indexOf(listTiposAusentismos.get(index));
                    crearTiposAusentismos.remove(crearIndex);
                } else {
                    borrarTiposAusentismos.add(listTiposAusentismos.get(index));
                }
                listTiposAusentismos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposAusentismos ");
                if (!modificarTiposAusentismos.isEmpty() && modificarTiposAusentismos.contains(filtrarTiposAusentismos.get(index))) {
                    int modIndex = modificarTiposAusentismos.indexOf(filtrarTiposAusentismos.get(index));
                    modificarTiposAusentismos.remove(modIndex);
                    borrarTiposAusentismos.add(filtrarTiposAusentismos.get(index));
                } else if (!crearTiposAusentismos.isEmpty() && crearTiposAusentismos.contains(filtrarTiposAusentismos.get(index))) {
                    int crearIndex = crearTiposAusentismos.indexOf(filtrarTiposAusentismos.get(index));
                    crearTiposAusentismos.remove(crearIndex);
                } else {
                    borrarTiposAusentismos.add(filtrarTiposAusentismos.get(index));
                }
                int VCIndex = listTiposAusentismos.indexOf(filtrarTiposAusentismos.get(index));
                listTiposAusentismos.remove(VCIndex);
                filtrarTiposAusentismos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposAusentismos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        BigInteger contarClasesAusentimosTipoAusentismo;
        BigInteger contarSOAusentimosTipoAusentismo;

        try {
            System.err.println("Control Secuencia de ControlTiposAusentismos ");
            if (tipoLista == 0) {
                contarClasesAusentimosTipoAusentismo = administrarTiposAusentismos.contarClasesAusentimosTipoAusentismo(listTiposAusentismos.get(index).getSecuencia());
                contarSOAusentimosTipoAusentismo = administrarTiposAusentismos.contarSOAusentimosTipoAusentismo(listTiposAusentismos.get(index).getSecuencia());
            } else {
                contarClasesAusentimosTipoAusentismo = administrarTiposAusentismos.contarClasesAusentimosTipoAusentismo(filtrarTiposAusentismos.get(index).getSecuencia());
                contarSOAusentimosTipoAusentismo = administrarTiposAusentismos.contarSOAusentimosTipoAusentismo(filtrarTiposAusentismos.get(index).getSecuencia());
            }
            if (contarClasesAusentimosTipoAusentismo.equals(new BigInteger("0")) && contarSOAusentimosTipoAusentismo.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposAusentismos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarClasesAusentimosTipoAusentismo = new BigInteger("-1");
                contarSOAusentimosTipoAusentismo = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposAusentismos verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposAusentismos.isEmpty() || !crearTiposAusentismos.isEmpty() || !modificarTiposAusentismos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposAusentismos");
            if (!borrarTiposAusentismos.isEmpty()) {
                administrarTiposAusentismos.borrarTiposAusentismos(borrarTiposAusentismos);
                //mostrarBorrados
                registrosBorrados = borrarTiposAusentismos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposAusentismos.clear();
            }
            if (!modificarTiposAusentismos.isEmpty()) {
                administrarTiposAusentismos.modificarTiposAusentismos(modificarTiposAusentismos);
                modificarTiposAusentismos.clear();
            }
            if (!crearTiposAusentismos.isEmpty()) {
                administrarTiposAusentismos.crearTiposAusentismos(crearTiposAusentismos);
                crearTiposAusentismos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposAusentismos = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosTiposAusentismos");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTiposAusentismos = listTiposAusentismos.get(index);
            }
            if (tipoLista == 1) {
                editarTiposAusentismos = filtrarTiposAusentismos.get(index);
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

    public void agregarNuevoTiposAusentismos() {
        System.out.println("agregarNuevoTiposAusentismos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposAusentismos.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposAusentismos.getCodigo());

            for (int x = 0; x < listTiposAusentismos.size(); x++) {
                if (listTiposAusentismos.get(x).getCodigo() == nuevoTiposAusentismos.getCodigo()) {
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
        if (nuevoTiposAusentismos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposAusentismos");
                bandera = 0;
                filtrarTiposAusentismos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposAusentismos.setSecuencia(l);

            crearTiposAusentismos.add(nuevoTiposAusentismos);

            listTiposAusentismos.add(nuevoTiposAusentismos);
            nuevoTiposAusentismos = new Tiposausentismos();
            context.update("form:datosTiposAusentismos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposAusentismos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposAusentismos() {
        System.out.println("limpiarNuevoTiposAusentismos");
        nuevoTiposAusentismos = new Tiposausentismos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposAusentismos() {
        System.out.println("duplicandoTiposAusentismos");
        if (index >= 0) {
            duplicarTiposAusentismos = new Tiposausentismos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposAusentismos.setSecuencia(l);
                duplicarTiposAusentismos.setCodigo(listTiposAusentismos.get(index).getCodigo());
                duplicarTiposAusentismos.setDescripcion(listTiposAusentismos.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTiposAusentismos.setSecuencia(l);
                duplicarTiposAusentismos.setCodigo(filtrarTiposAusentismos.get(index).getCodigo());
                duplicarTiposAusentismos.setDescripcion(filtrarTiposAusentismos.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposAusentismos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS EMPRESAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposAusentismos.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposAusentismos.getDescripcion());

        if (duplicarTiposAusentismos.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposAusentismos.size(); x++) {
                if (listTiposAusentismos.get(x).getCodigo() == duplicarTiposAusentismos.getCodigo()) {
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
        if (duplicarTiposAusentismos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposAusentismos.getSecuencia() + "  " + duplicarTiposAusentismos.getCodigo());
            if (crearTiposAusentismos.contains(duplicarTiposAusentismos)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposAusentismos.add(duplicarTiposAusentismos);
            crearTiposAusentismos.add(duplicarTiposAusentismos);
            context.update("form:datosTiposAusentismos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAusentismos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposAusentismos");
                bandera = 0;
                filtrarTiposAusentismos = null;
                tipoLista = 0;
            }
            duplicarTiposAusentismos = new Tiposausentismos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposAusentismos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposAusentismos() {
        duplicarTiposAusentismos = new Tiposausentismos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposAusentismosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSAUSENTISMOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposAusentismosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSAUSENTISMOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposAusentismos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSAUSENTISMOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSAUSENTISMOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Tiposausentismos> getListTiposAusentismos() {
        if (listTiposAusentismos == null) {
            listTiposAusentismos = administrarTiposAusentismos.consultarTiposAusentismos();
        }
        return listTiposAusentismos;
    }

    public void setListTiposAusentismos(List<Tiposausentismos> listTiposAusentismos) {
        this.listTiposAusentismos = listTiposAusentismos;
    }

    public List<Tiposausentismos> getFiltrarTiposAusentismos() {
        return filtrarTiposAusentismos;
    }

    public void setFiltrarTiposAusentismos(List<Tiposausentismos> filtrarTiposAusentismos) {
        this.filtrarTiposAusentismos = filtrarTiposAusentismos;
    }

    public Tiposausentismos getNuevoTiposAusentismos() {
        return nuevoTiposAusentismos;
    }

    public void setNuevoTiposAusentismos(Tiposausentismos nuevoTiposAusentismos) {
        this.nuevoTiposAusentismos = nuevoTiposAusentismos;
    }

    public Tiposausentismos getDuplicarTiposAusentismos() {
        return duplicarTiposAusentismos;
    }

    public void setDuplicarTiposAusentismos(Tiposausentismos duplicarTiposAusentismos) {
        this.duplicarTiposAusentismos = duplicarTiposAusentismos;
    }

    public Tiposausentismos getEditarTiposAusentismos() {
        return editarTiposAusentismos;
    }

    public void setEditarTiposAusentismos(Tiposausentismos editarTiposAusentismos) {
        this.editarTiposAusentismos = editarTiposAusentismos;
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

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

}

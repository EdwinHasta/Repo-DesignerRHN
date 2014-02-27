/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Jornadas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarJornadasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlJornadas implements Serializable {

    @EJB
    AdministrarJornadasInterface administrarJornadas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Jornadas> listJornadas;
    private List<Jornadas> filtrarJornadas;
    private List<Jornadas> crearJornadas;
    private List<Jornadas> modificarJornadas;
    private List<Jornadas> borrarJornadas;
    private Jornadas nuevoJornadas;
    private Jornadas duplicarJornadas;
    private Jornadas editarJornadas;
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
    private Integer backupCodigo;
    private String backupDescripcion;

    public ControlJornadas() {
        listJornadas = null;
        crearJornadas = new ArrayList<Jornadas>();
        modificarJornadas = new ArrayList<Jornadas>();
        borrarJornadas = new ArrayList<Jornadas>();
        permitirIndex = true;
        editarJornadas = new Jornadas();
        nuevoJornadas = new Jornadas();
        duplicarJornadas = new Jornadas();
        guardado = true;
        tamano = 302;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlJornadas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlJornadas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listJornadas.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listJornadas.get(index).getCodigo();
                backupDescripcion = listJornadas.get(index).getDescripcion();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarJornadas.get(index).getCodigo();
                backupDescripcion = filtrarJornadas.get(index).getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlJornadas.asignarIndex \n");
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
            System.out.println("ERROR ControlJornadas.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosJornadas");
            bandera = 0;
            filtrarJornadas = null;
            tipoLista = 0;
        }

        borrarJornadas.clear();
        crearJornadas.clear();
        modificarJornadas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listJornadas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosJornadas");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosJornadas");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosJornadas");
            bandera = 0;
            filtrarJornadas = null;
            tipoLista = 0;
        }
    }

    public void modificarJornadas(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        boolean banderita1 = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearJornadas.contains(listJornadas.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listJornadas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listJornadas.size(); j++) {
                            if (j != indice) {
                                if (listJornadas.get(indice).getCodigo() == listJornadas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listJornadas.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listJornadas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listJornadas.get(indice).setDescripcion(backupDescripcion);
                    } else if (listJornadas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listJornadas.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarJornadas.isEmpty()) {
                            modificarJornadas.add(listJornadas.get(indice));
                        } else if (!modificarJornadas.contains(listJornadas.get(indice))) {
                            modificarJornadas.add(listJornadas.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");

                    }
                    index = -1;
                    secRegistro = null;
                    context.update("form:datosJornadas");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listJornadas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listJornadas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listJornadas.size(); j++) {
                            if (j != indice) {
                                if (listJornadas.get(indice).getCodigo() == listJornadas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listJornadas.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listJornadas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listJornadas.get(indice).setDescripcion(backupDescripcion);
                    } else if (listJornadas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listJornadas.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (guardado == true) {
                            guardado = false;
                        }
                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");

                    }
                    index = -1;
                    secRegistro = null;
                    context.update("form:datosJornadas");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearJornadas.contains(filtrarJornadas.get(indice))) {
                    if (filtrarJornadas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarJornadas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarJornadas.size(); j++) {
                            if (j != indice) {
                                if (filtrarJornadas.get(indice).getCodigo() == listJornadas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listJornadas.size(); j++) {
                            if (j != indice) {
                                if (filtrarJornadas.get(indice).getCodigo() == listJornadas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarJornadas.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarJornadas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarJornadas.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarJornadas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarJornadas.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarJornadas.isEmpty()) {
                            modificarJornadas.add(filtrarJornadas.get(indice));
                        } else if (!modificarJornadas.contains(filtrarJornadas.get(indice))) {
                            modificarJornadas.add(filtrarJornadas.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                } else {if (filtrarJornadas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarJornadas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarJornadas.size(); j++) {
                            if (j != indice) {
                                if (filtrarJornadas.get(indice).getCodigo() == listJornadas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listJornadas.size(); j++) {
                            if (j != indice) {
                                if (filtrarJornadas.get(indice).getCodigo() == listJornadas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarJornadas.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarJornadas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarJornadas.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarJornadas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarJornadas.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosJornadas");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoJornadas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoJornadas");
                if (!modificarJornadas.isEmpty() && modificarJornadas.contains(listJornadas.get(index))) {
                    int modIndex = modificarJornadas.indexOf(listJornadas.get(index));
                    modificarJornadas.remove(modIndex);
                    borrarJornadas.add(listJornadas.get(index));
                } else if (!crearJornadas.isEmpty() && crearJornadas.contains(listJornadas.get(index))) {
                    int crearIndex = crearJornadas.indexOf(listJornadas.get(index));
                    crearJornadas.remove(crearIndex);
                } else {
                    borrarJornadas.add(listJornadas.get(index));
                }
                listJornadas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoJornadas ");
                if (!modificarJornadas.isEmpty() && modificarJornadas.contains(filtrarJornadas.get(index))) {
                    int modIndex = modificarJornadas.indexOf(filtrarJornadas.get(index));
                    modificarJornadas.remove(modIndex);
                    borrarJornadas.add(filtrarJornadas.get(index));
                } else if (!crearJornadas.isEmpty() && crearJornadas.contains(filtrarJornadas.get(index))) {
                    int crearIndex = crearJornadas.indexOf(filtrarJornadas.get(index));
                    crearJornadas.remove(crearIndex);
                } else {
                    borrarJornadas.add(filtrarJornadas.get(index));
                }
                int VCIndex = listJornadas.indexOf(filtrarJornadas.get(index));
                listJornadas.remove(VCIndex);
                filtrarJornadas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosJornadas");
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
        BigInteger contarJornadasLaboralesJornada;
        BigInteger contarTarifasEscalafonesJornada;

        try {
            System.err.println("Control Secuencia de ControlJornadas ");
            if (tipoLista == 0) {
                contarJornadasLaboralesJornada = administrarJornadas.contarJornadasLaboralesJornada(listJornadas.get(index).getSecuencia());
                contarTarifasEscalafonesJornada = administrarJornadas.contarTarifasEscalafonesJornada(listJornadas.get(index).getSecuencia());
            } else {
                contarJornadasLaboralesJornada = administrarJornadas.contarJornadasLaboralesJornada(filtrarJornadas.get(index).getSecuencia());
                contarTarifasEscalafonesJornada = administrarJornadas.contarTarifasEscalafonesJornada(filtrarJornadas.get(index).getSecuencia());
            }
            if (contarJornadasLaboralesJornada.equals(new BigInteger("0")) && contarTarifasEscalafonesJornada.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoJornadas();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarJornadasLaboralesJornada = new BigInteger("-1");
                contarTarifasEscalafonesJornada = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlJornadas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarJornadas.isEmpty() || !crearJornadas.isEmpty() || !modificarJornadas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarJornadas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarJornadas");
            if (!borrarJornadas.isEmpty()) {
                administrarJornadas.borrarJornadas(borrarJornadas);
                //mostrarBorrados
                registrosBorrados = borrarJornadas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarJornadas.clear();
            }
            if (!modificarJornadas.isEmpty()) {
                administrarJornadas.modificarJornadas(modificarJornadas);
                modificarJornadas.clear();
            }
            if (!crearJornadas.isEmpty()) {
                administrarJornadas.crearJornadas(crearJornadas);
                crearJornadas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listJornadas = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosJornadas");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarJornadas = listJornadas.get(index);
            }
            if (tipoLista == 1) {
                editarJornadas = filtrarJornadas.get(index);
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

    public void agregarNuevoJornadas() {
        System.out.println("agregarNuevoJornadas");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoJornadas.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoJornadas.getCodigo());

            for (int x = 0; x < listJornadas.size(); x++) {
                if (listJornadas.get(x).getCodigo() == nuevoJornadas.getCodigo()) {
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
        if (nuevoJornadas.getDescripcion().equals(" ")) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosJornadas");
                bandera = 0;
                filtrarJornadas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoJornadas.setSecuencia(l);

            crearJornadas.add(nuevoJornadas);

            listJornadas.add(nuevoJornadas);
            nuevoJornadas = new Jornadas();
            context.update("form:datosJornadas");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroJornadas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoJornadas() {
        System.out.println("limpiarNuevoJornadas");
        nuevoJornadas = new Jornadas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoJornadas() {
        System.out.println("duplicandoJornadas");
        if (index >= 0) {
            duplicarJornadas = new Jornadas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarJornadas.setSecuencia(l);
                duplicarJornadas.setCodigo(listJornadas.get(index).getCodigo());
                duplicarJornadas.setDescripcion(listJornadas.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarJornadas.setSecuencia(l);
                duplicarJornadas.setCodigo(filtrarJornadas.get(index).getCodigo());
                duplicarJornadas.setDescripcion(filtrarJornadas.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroJornadas.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarJornadas.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarJornadas.getDescripcion());

        if (duplicarJornadas.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listJornadas.size(); x++) {
                if (listJornadas.get(x).getCodigo() == duplicarJornadas.getCodigo()) {
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
        if (duplicarJornadas.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarJornadas.getSecuencia() + "  " + duplicarJornadas.getCodigo());
            if (crearJornadas.contains(duplicarJornadas)) {
                System.out.println("Ya lo contengo.");
            }
            listJornadas.add(duplicarJornadas);
            crearJornadas.add(duplicarJornadas);
            context.update("form:datosJornadas");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosJornadas:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosJornadas");
                bandera = 0;
                filtrarJornadas = null;
                tipoLista = 0;
            }
            duplicarJornadas = new Jornadas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroJornadas.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarJornadas() {
        duplicarJornadas = new Jornadas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosJornadasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "JORNADAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosJornadasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "JORNADAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listJornadas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "JORNADAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("JORNADAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Jornadas> getListJornadas() {
        if (listJornadas == null) {
            listJornadas = administrarJornadas.consultarJornadas();
        }
        return listJornadas;
    }

    public void setListJornadas(List<Jornadas> listJornadas) {
        this.listJornadas = listJornadas;
    }

    public List<Jornadas> getFiltrarJornadas() {
        return filtrarJornadas;
    }

    public void setFiltrarJornadas(List<Jornadas> filtrarJornadas) {
        this.filtrarJornadas = filtrarJornadas;
    }

    public Jornadas getNuevoJornadas() {
        return nuevoJornadas;
    }

    public void setNuevoJornadas(Jornadas nuevoJornadas) {
        this.nuevoJornadas = nuevoJornadas;
    }

    public Jornadas getDuplicarJornadas() {
        return duplicarJornadas;
    }

    public void setDuplicarJornadas(Jornadas duplicarJornadas) {
        this.duplicarJornadas = duplicarJornadas;
    }

    public Jornadas getEditarJornadas() {
        return editarJornadas;
    }

    public void setEditarJornadas(Jornadas editarJornadas) {
        this.editarJornadas = editarJornadas;
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

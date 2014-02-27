/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Paises;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarPaisesInterface;
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
public class ControlPaises implements Serializable {

    @EJB
    AdministrarPaisesInterface administrarPaises;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Paises> listPaises;
    private List<Paises> filtrarPaises;
    private List<Paises> crearPaises;
    private List<Paises> modificarPaises;
    private List<Paises> borrarPaises;
    private Paises nuevoPaises;
    private Paises duplicarPaises;
    private Paises editarPaises;
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

    public ControlPaises() {
        listPaises = null;
        crearPaises = new ArrayList<Paises>();
        modificarPaises = new ArrayList<Paises>();
        borrarPaises = new ArrayList<Paises>();
        permitirIndex = true;
        editarPaises = new Paises();
        nuevoPaises = new Paises();
        duplicarPaises = new Paises();
        guardado = true;
        tamano = 302;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlPaises.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlPaises eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listPaises.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listPaises.get(index).getCodigo();
                backupDescripcion = listPaises.get(index).getNombre();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarPaises.get(index).getCodigo();
                backupDescripcion = filtrarPaises.get(index).getNombre();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlPaises.asignarIndex \n");
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
            System.out.println("ERROR ControlPaises.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPaises");
            bandera = 0;
            filtrarPaises = null;
            tipoLista = 0;
        }

        borrarPaises.clear();
        crearPaises.clear();
        modificarPaises.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listPaises = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosPaises");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosPaises");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPaises");
            bandera = 0;
            filtrarPaises = null;
            tipoLista = 0;
        }
    }

    public void modificarPaises(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearPaises.contains(listPaises.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listPaises.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPaises.size(); j++) {
                            if (j != indice) {
                                if (listPaises.get(indice).getCodigo() == listPaises.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listPaises.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listPaises.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listPaises.get(indice).setNombre(backupDescripcion);
                    } else if (listPaises.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listPaises.get(indice).setNombre(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarPaises.isEmpty()) {
                            modificarPaises.add(listPaises.get(indice));
                        } else if (!modificarPaises.contains(listPaises.get(indice))) {
                            modificarPaises.add(listPaises.get(indice));
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
                    context.update("form:datosPaises");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listPaises.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listPaises.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listPaises.size(); j++) {
                            if (j != indice) {
                                if (listPaises.get(indice).getCodigo() == listPaises.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listPaises.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listPaises.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listPaises.get(indice).setNombre(backupDescripcion);
                    } else if (listPaises.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listPaises.get(indice).setNombre(backupDescripcion);

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
                    context.update("form:datosPaises");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearPaises.contains(filtrarPaises.get(indice))) {
                    if (filtrarPaises.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarPaises.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarPaises.size(); j++) {
                            if (j != indice) {
                                if (filtrarPaises.get(indice).getCodigo() == listPaises.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listPaises.size(); j++) {
                            if (j != indice) {
                                if (filtrarPaises.get(indice).getCodigo() == listPaises.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarPaises.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarPaises.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarPaises.get(indice).setNombre(backupDescripcion);
                    }
                    if (filtrarPaises.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarPaises.get(indice).setNombre(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarPaises.isEmpty()) {
                            modificarPaises.add(filtrarPaises.get(indice));
                        } else if (!modificarPaises.contains(filtrarPaises.get(indice))) {
                            modificarPaises.add(filtrarPaises.get(indice));
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
                } else {if (filtrarPaises.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarPaises.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarPaises.size(); j++) {
                            if (j != indice) {
                                if (filtrarPaises.get(indice).getCodigo() == listPaises.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listPaises.size(); j++) {
                            if (j != indice) {
                                if (filtrarPaises.get(indice).getCodigo() == listPaises.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarPaises.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarPaises.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarPaises.get(indice).setNombre(backupDescripcion);
                    }
                    if (filtrarPaises.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarPaises.get(indice).setNombre(backupDescripcion);
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
            context.update("form:datosPaises");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoPaises() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoPaises");
                if (!modificarPaises.isEmpty() && modificarPaises.contains(listPaises.get(index))) {
                    int modIndex = modificarPaises.indexOf(listPaises.get(index));
                    modificarPaises.remove(modIndex);
                    borrarPaises.add(listPaises.get(index));
                } else if (!crearPaises.isEmpty() && crearPaises.contains(listPaises.get(index))) {
                    int crearIndex = crearPaises.indexOf(listPaises.get(index));
                    crearPaises.remove(crearIndex);
                } else {
                    borrarPaises.add(listPaises.get(index));
                }
                listPaises.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoPaises ");
                if (!modificarPaises.isEmpty() && modificarPaises.contains(filtrarPaises.get(index))) {
                    int modIndex = modificarPaises.indexOf(filtrarPaises.get(index));
                    modificarPaises.remove(modIndex);
                    borrarPaises.add(filtrarPaises.get(index));
                } else if (!crearPaises.isEmpty() && crearPaises.contains(filtrarPaises.get(index))) {
                    int crearIndex = crearPaises.indexOf(filtrarPaises.get(index));
                    crearPaises.remove(crearIndex);
                } else {
                    borrarPaises.add(filtrarPaises.get(index));
                }
                int VCIndex = listPaises.indexOf(filtrarPaises.get(index));
                listPaises.remove(VCIndex);
                filtrarPaises.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosPaises");
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
        BigInteger contarDepartamentosPais;
        BigInteger contarFestivosPais;

        try {
            System.err.println("Control Secuencia de ControlPaises ");
            if (tipoLista == 0) {
                contarDepartamentosPais = administrarPaises.contarDepartamentosPais(listPaises.get(index).getSecuencia());
                contarFestivosPais = administrarPaises.contarFestivosPais(listPaises.get(index).getSecuencia());
            } else {
                contarDepartamentosPais = administrarPaises.contarDepartamentosPais(filtrarPaises.get(index).getSecuencia());
                contarFestivosPais = administrarPaises.contarFestivosPais(filtrarPaises.get(index).getSecuencia());
            }
            if (contarDepartamentosPais.equals(new BigInteger("0")) && contarFestivosPais.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoPaises();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarDepartamentosPais = new BigInteger("-1");
                contarFestivosPais = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlPaises verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarPaises.isEmpty() || !crearPaises.isEmpty() || !modificarPaises.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarPaises() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarPaises");
            if (!borrarPaises.isEmpty()) {
                administrarPaises.borrarPaises(borrarPaises);
                //mostrarBorrados
                registrosBorrados = borrarPaises.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarPaises.clear();
            }
            if (!modificarPaises.isEmpty()) {
                administrarPaises.modificarPaises(modificarPaises);
                modificarPaises.clear();
            }
            if (!crearPaises.isEmpty()) {
                administrarPaises.crearPaises(crearPaises);
                crearPaises.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listPaises = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosPaises");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarPaises = listPaises.get(index);
            }
            if (tipoLista == 1) {
                editarPaises = filtrarPaises.get(index);
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

    public void agregarNuevoPaises() {
        System.out.println("agregarNuevoPaises");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoPaises.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoPaises.getCodigo());

            for (int x = 0; x < listPaises.size(); x++) {
                if (listPaises.get(x).getCodigo() == nuevoPaises.getCodigo()) {
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
        if (nuevoPaises.getNombre().equals(" ")) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPaises");
                bandera = 0;
                filtrarPaises = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoPaises.setSecuencia(l);

            crearPaises.add(nuevoPaises);

            listPaises.add(nuevoPaises);
            nuevoPaises = new Paises();
            context.update("form:datosPaises");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroPaises.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoPaises() {
        System.out.println("limpiarNuevoPaises");
        nuevoPaises = new Paises();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoPaises() {
        System.out.println("duplicandoPaises");
        if (index >= 0) {
            duplicarPaises = new Paises();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarPaises.setSecuencia(l);
                duplicarPaises.setCodigo(listPaises.get(index).getCodigo());
                duplicarPaises.setNombre(listPaises.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarPaises.setSecuencia(l);
                duplicarPaises.setCodigo(filtrarPaises.get(index).getCodigo());
                duplicarPaises.setNombre(filtrarPaises.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroPaises.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarPaises.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarPaises.getNombre());

        if (duplicarPaises.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listPaises.size(); x++) {
                if (listPaises.get(x).getCodigo() == duplicarPaises.getCodigo()) {
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
        if (duplicarPaises.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarPaises.getSecuencia() + "  " + duplicarPaises.getCodigo());
            if (crearPaises.contains(duplicarPaises)) {
                System.out.println("Ya lo contengo.");
            }
            listPaises.add(duplicarPaises);
            crearPaises.add(duplicarPaises);
            context.update("form:datosPaises");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPaises:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPaises");
                bandera = 0;
                filtrarPaises = null;
                tipoLista = 0;
            }
            duplicarPaises = new Paises();
            RequestContext.getCurrentInstance().execute("duplicarRegistroPaises.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarPaises() {
        duplicarPaises = new Paises();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPaisesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "PAISES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPaisesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "PAISES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listPaises.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PAISES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("PAISES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Paises> getListPaises() {
        if (listPaises == null) {
            listPaises = administrarPaises.consultarPaises();
        }
        return listPaises;
    }

    public void setListPaises(List<Paises> listPaises) {
        this.listPaises = listPaises;
    }

    public List<Paises> getFiltrarPaises() {
        return filtrarPaises;
    }

    public void setFiltrarPaises(List<Paises> filtrarPaises) {
        this.filtrarPaises = filtrarPaises;
    }

    public Paises getNuevoPaises() {
        return nuevoPaises;
    }

    public void setNuevoPaises(Paises nuevoPaises) {
        this.nuevoPaises = nuevoPaises;
    }

    public Paises getDuplicarPaises() {
        return duplicarPaises;
    }

    public void setDuplicarPaises(Paises duplicarPaises) {
        this.duplicarPaises = duplicarPaises;
    }

    public Paises getEditarPaises() {
        return editarPaises;
    }

    public void setEditarPaises(Paises editarPaises) {
        this.editarPaises = editarPaises;
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

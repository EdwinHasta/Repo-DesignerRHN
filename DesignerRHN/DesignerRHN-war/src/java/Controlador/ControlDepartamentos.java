/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Departamentos;
import Entidades.Paises;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarDepartamentosInterface;
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
public class ControlDepartamentos implements Serializable {

    @EJB
    AdministrarDepartamentosInterface administrarDepartamentos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Departamentos> listDepartamentos;
    private List<Departamentos> filtrarDepartamentos;
    private List<Departamentos> crearDepartamentos;
    private List<Departamentos> modificarDepartamentos;
    private List<Departamentos> borrarDepartamentos;
    private Departamentos nuevoDepartamentos;
    private Departamentos duplicarDepartamentos;
    private Departamentos editarDepartamentos;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, pais;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;
    private Integer backupCodigo;
    private String backupDescripcion;
    private String backupPais;
    //-------------------------------
    private String paises;
    private List<Paises> listaPaises;
    private List<Paises> filtradoPaises;
    private Paises paisSeleccionado;
    private String nuevoYduplicarCompletarPais;

    public ControlDepartamentos() {
        listDepartamentos = null;
        crearDepartamentos = new ArrayList<Departamentos>();
        modificarDepartamentos = new ArrayList<Departamentos>();
        borrarDepartamentos = new ArrayList<Departamentos>();
        permitirIndex = true;
        editarDepartamentos = new Departamentos();
        nuevoDepartamentos = new Departamentos();
        nuevoDepartamentos.setPais(new Paises());
        duplicarDepartamentos = new Departamentos();
        duplicarDepartamentos.setPais(new Paises());
        listaPaises = null;
        filtradoPaises = null;
        guardado = true;
        tamano = 302;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlDepartamentos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlDepartamentos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listDepartamentos.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listDepartamentos.get(index).getCodigo();
                backupDescripcion = listDepartamentos.get(index).getNombre();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarDepartamentos.get(index).getCodigo();
                backupDescripcion = filtrarDepartamentos.get(index).getNombre();
            }
            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    paises = listDepartamentos.get(index).getPais().getNombre();
                } else {
                    paises = filtrarDepartamentos.get(index).getPais().getNombre();
                }
            }
            System.out.println("PAIS : " + paises);
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A ControlDepartamentos.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 2) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                dig = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlDepartamentos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            if (cualCelda == 2) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            pais = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:pais");
            pais.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDepartamentos");
            bandera = 0;
            filtrarDepartamentos = null;
            tipoLista = 0;
        }

        borrarDepartamentos.clear();
        crearDepartamentos.clear();
        modificarDepartamentos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listDepartamentos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDepartamentos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:codigo");
            codigo.setFilterStyle("width: 70px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:descripcion");
            descripcion.setFilterStyle("width: 280px");
            pais = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:pais");
            pais.setFilterStyle("width: 230px");
            RequestContext.getCurrentInstance().update("form:datosDepartamentos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            pais = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:pais");
            pais.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDepartamentos");
            bandera = 0;
            filtrarDepartamentos = null;
            tipoLista = 0;
        }
    }

    public void actualizarPais() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("pais seleccionado : " + paisSeleccionado.getNombre());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listDepartamentos.get(index).setPais(paisSeleccionado);

                if (!crearDepartamentos.contains(listDepartamentos.get(index))) {
                    if (modificarDepartamentos.isEmpty()) {
                        modificarDepartamentos.add(listDepartamentos.get(index));
                    } else if (!modificarDepartamentos.contains(listDepartamentos.get(index))) {
                        modificarDepartamentos.add(listDepartamentos.get(index));
                    }
                }
            } else {
                filtrarDepartamentos.get(index).setPais(paisSeleccionado);

                if (!crearDepartamentos.contains(filtrarDepartamentos.get(index))) {
                    if (modificarDepartamentos.isEmpty()) {
                        modificarDepartamentos.add(filtrarDepartamentos.get(index));
                    } else if (!modificarDepartamentos.contains(filtrarDepartamentos.get(index))) {
                        modificarDepartamentos.add(filtrarDepartamentos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR PAIS PAIS SELECCIONADO : " + paisSeleccionado.getNombre());
            context.update("form:datosDepartamentos");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR PAIS NUEVO DEPARTAMENTO: " + paisSeleccionado.getNombre());
            nuevoDepartamentos.setPais(paisSeleccionado);
            context.update("formularioDialogos:nuevoPais");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR PAIS DUPLICAR DEPARTAMENO: " + paisSeleccionado.getNombre());
            duplicarDepartamentos.setPais(paisSeleccionado);
            context.update("formularioDialogos:duplicarPais");
        }
        filtradoPaises = null;
        paisSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("sucursalesDialogo.hide()");
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.update("form:lovTiposFamiliares");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioPais() {
        filtradoPaises = null;
        paisSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void modificarDepartamentos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;
        int coincidencias = 0;
        int contador = 0;
        boolean banderita = false;
        boolean banderita1 = false;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearDepartamentos.contains(listDepartamentos.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);
                    System.out.println("backupPais : " + backupPais);

                    if (listDepartamentos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listDepartamentos.get(indice).setCodigo(backupCodigo);

                    } else {
                        for (int j = 0; j < listDepartamentos.size(); j++) {
                            if (j != indice) {
                                if (listDepartamentos.get(indice).getCodigo() == listDepartamentos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listDepartamentos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listDepartamentos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listDepartamentos.get(indice).setNombre(backupDescripcion);
                    } else if (listDepartamentos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listDepartamentos.get(indice).setNombre(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarDepartamentos.isEmpty()) {
                            modificarDepartamentos.add(listDepartamentos.get(indice));
                        } else if (!modificarDepartamentos.contains(listDepartamentos.get(indice))) {
                            modificarDepartamentos.add(listDepartamentos.get(indice));
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
                    context.update("form:datosDepartamentos");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listDepartamentos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listDepartamentos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listDepartamentos.size(); j++) {
                            if (j != indice) {
                                if (listDepartamentos.get(indice).getCodigo() == listDepartamentos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listDepartamentos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listDepartamentos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listDepartamentos.get(indice).setNombre(backupDescripcion);
                    } else if (listDepartamentos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listDepartamentos.get(indice).setNombre(backupDescripcion);

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
                    context.update("form:datosDepartamentos");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearDepartamentos.contains(filtrarDepartamentos.get(indice))) {
                    if (filtrarDepartamentos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDepartamentos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarDepartamentos.size(); j++) {
                            if (j != indice) {
                                if (filtrarDepartamentos.get(indice).getCodigo() == listDepartamentos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listDepartamentos.size(); j++) {
                            if (j != indice) {
                                if (filtrarDepartamentos.get(indice).getCodigo() == listDepartamentos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarDepartamentos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarDepartamentos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarDepartamentos.get(indice).setNombre(backupDescripcion);
                    }
                    if (filtrarDepartamentos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarDepartamentos.get(indice).setNombre(backupDescripcion);
                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {

                        if (modificarDepartamentos.isEmpty()) {
                            modificarDepartamentos.add(filtrarDepartamentos.get(indice));
                        } else if (!modificarDepartamentos.contains(filtrarDepartamentos.get(indice))) {
                            modificarDepartamentos.add(filtrarDepartamentos.get(indice));
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
                } else {
                    if (filtrarDepartamentos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDepartamentos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarDepartamentos.size(); j++) {
                            if (j != indice) {
                                if (filtrarDepartamentos.get(indice).getCodigo() == listDepartamentos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listDepartamentos.size(); j++) {
                            if (j != indice) {
                                if (filtrarDepartamentos.get(indice).getCodigo() == listDepartamentos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarDepartamentos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarDepartamentos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarDepartamentos.get(indice).setNombre(backupDescripcion);
                    } else if (filtrarDepartamentos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarDepartamentos.get(indice).setNombre(backupDescripcion);
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
                }

            }
            context.update("form:datosDepartamentos");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("PAISES")) {
            System.out.println("MODIFICANDO NORMA LABORAL : " + listDepartamentos.get(indice).getPais().getNombre());
            if (!listDepartamentos.get(indice).getPais().getNombre().equals("")) {
                if (tipoLista == 0) {
                    listDepartamentos.get(indice).getPais().setNombre(paises);
                } else {
                    listDepartamentos.get(indice).getPais().setNombre(paises);
                }

                for (int i = 0; i < listaPaises.size(); i++) {
                    if (listaPaises.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listDepartamentos.get(indice).setPais(listaPaises.get(indiceUnicoElemento));
                    } else {
                        filtrarDepartamentos.get(indice).setPais(listaPaises.get(indiceUnicoElemento));
                    }
                    listaPaises.clear();
                    listaPaises = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (paises != null) {
                    if (tipoLista == 0) {
                        listDepartamentos.get(index).getPais().setNombre(paises);
                    } else {
                        filtrarDepartamentos.get(index).getPais().setNombre(paises);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO : " + paises);
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearDepartamentos.contains(listDepartamentos.get(indice))) {

                        if (modificarDepartamentos.isEmpty()) {
                            modificarDepartamentos.add(listDepartamentos.get(indice));
                        } else if (!modificarDepartamentos.contains(listDepartamentos.get(indice))) {
                            modificarDepartamentos.add(listDepartamentos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearDepartamentos.contains(filtrarDepartamentos.get(indice))) {

                        if (modificarDepartamentos.isEmpty()) {
                            modificarDepartamentos.add(filtrarDepartamentos.get(indice));
                        } else if (!modificarDepartamentos.contains(filtrarDepartamentos.get(indice))) {
                            modificarDepartamentos.add(filtrarDepartamentos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosDepartamentos");
            context.update("form:ACEPTAR");

        }

    }

    public void borrandoDepartamentos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoDepartamentos");
                if (!modificarDepartamentos.isEmpty() && modificarDepartamentos.contains(listDepartamentos.get(index))) {
                    int modIndex = modificarDepartamentos.indexOf(listDepartamentos.get(index));
                    modificarDepartamentos.remove(modIndex);
                    borrarDepartamentos.add(listDepartamentos.get(index));
                } else if (!crearDepartamentos.isEmpty() && crearDepartamentos.contains(listDepartamentos.get(index))) {
                    int crearIndex = crearDepartamentos.indexOf(listDepartamentos.get(index));
                    crearDepartamentos.remove(crearIndex);
                } else {
                    borrarDepartamentos.add(listDepartamentos.get(index));
                }
                listDepartamentos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoDepartamentos ");
                if (!modificarDepartamentos.isEmpty() && modificarDepartamentos.contains(filtrarDepartamentos.get(index))) {
                    int modIndex = modificarDepartamentos.indexOf(filtrarDepartamentos.get(index));
                    modificarDepartamentos.remove(modIndex);
                    borrarDepartamentos.add(filtrarDepartamentos.get(index));
                } else if (!crearDepartamentos.isEmpty() && crearDepartamentos.contains(filtrarDepartamentos.get(index))) {
                    int crearIndex = crearDepartamentos.indexOf(filtrarDepartamentos.get(index));
                    crearDepartamentos.remove(crearIndex);
                } else {
                    borrarDepartamentos.add(filtrarDepartamentos.get(index));
                }
                int VCIndex = listDepartamentos.indexOf(filtrarDepartamentos.get(index));
                listDepartamentos.remove(VCIndex);
                filtrarDepartamentos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDepartamentos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void valoresBackupAutocompletar(int tipoNuevo) {
        System.out.println("1...");
        if (tipoNuevo == 1) {
            nuevoYduplicarCompletarPais = nuevoDepartamentos.getPais().getNombre();
        } else if (tipoNuevo == 2) {
            nuevoYduplicarCompletarPais = duplicarDepartamentos.getPais().getNombre();
        }

    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PAISES")) {
            System.out.println(" nueva Ciudad    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoDepartamentos.getPais().getNombre());

            if (!nuevoDepartamentos.getPais().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCiudadCompletar: " + nuevoYduplicarCompletarPais);
                nuevoDepartamentos.getPais().setNombre(nuevoYduplicarCompletarPais);
                getListaPaises();
                for (int i = 0; i < listaPaises.size(); i++) {
                    if (listaPaises.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoDepartamentos.setPais(listaPaises.get(indiceUnicoElemento));
                    listaPaises = null;
                    getListaPaises();
                    System.err.println("NORMA LABORAL GUARDADA " + nuevoDepartamentos.getPais().getNombre());
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoDepartamentos.getPais().setNombre(nuevoYduplicarCompletarPais);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoDepartamentos.setPais(new Paises());
                nuevoDepartamentos.getPais().setNombre(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoDepartamentos.getPais().getNombre());
            }
            context.update("formularioDialogos:nuevoPais");
        }

    }

    public void asignarVariablePaises(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:sucursalesDialogo");
        context.execute("sucursalesDialogo.show()");
    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PAISES")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarPais);

            if (!duplicarDepartamentos.getPais().getNombre().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarPais);
                duplicarDepartamentos.getPais().setNombre(nuevoYduplicarCompletarPais);
                for (int i = 0; i < listaPaises.size(); i++) {
                    if (listaPaises.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarDepartamentos.setPais(listaPaises.get(indiceUnicoElemento));
                    listaPaises = null;
                    getListaPaises();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarDepartamentos.getPais().setNombre(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarDepartamentos.setPais(new Paises());
                    duplicarDepartamentos.getPais().setNombre(" ");

                    System.out.println("DUPLICAR Valor NORMA LABORAL : " + duplicarDepartamentos.getPais().getNombre());
                    System.out.println("nuevoYduplicarCompletarPais : " + nuevoYduplicarCompletarPais);
                    if (tipoLista == 0) {
                        listDepartamentos.get(index).getPais().setNombre(nuevoYduplicarCompletarPais);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listDepartamentos.get(index).getPais().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarDepartamentos.get(index).getPais().setNombre(nuevoYduplicarCompletarPais);
                    }

                }

            }
            context.update("formularioDialogos:duplicarPais");
        }
    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        BigInteger contarBienProgramacionesDepartamento;
        BigInteger contarCapModulosDepartamento;
        BigInteger contarCiudadesDepartamento;
        BigInteger contarSoAccidentesMedicosDepartamento;

        try {
            System.err.println("Control Secuencia de ControlDepartamentos ");
            if (tipoLista == 0) {
                contarBienProgramacionesDepartamento = administrarDepartamentos.contarBienProgramacionesDepartamento(listDepartamentos.get(index).getSecuencia());
                contarCapModulosDepartamento = administrarDepartamentos.contarCapModulosDepartamento(listDepartamentos.get(index).getSecuencia());
                contarCiudadesDepartamento = administrarDepartamentos.contarCiudadesDepartamento(listDepartamentos.get(index).getSecuencia());
                contarSoAccidentesMedicosDepartamento = administrarDepartamentos.contarSoAccidentesMedicosDepartamento(listDepartamentos.get(index).getSecuencia());
            } else {
                contarBienProgramacionesDepartamento = administrarDepartamentos.contarBienProgramacionesDepartamento(filtrarDepartamentos.get(index).getSecuencia());
                contarCapModulosDepartamento = administrarDepartamentos.contarCapModulosDepartamento(filtrarDepartamentos.get(index).getSecuencia());
                contarCiudadesDepartamento = administrarDepartamentos.contarCiudadesDepartamento(filtrarDepartamentos.get(index).getSecuencia());
                contarSoAccidentesMedicosDepartamento = administrarDepartamentos.contarSoAccidentesMedicosDepartamento(filtrarDepartamentos.get(index).getSecuencia());
            }
            if (contarBienProgramacionesDepartamento.equals(new BigInteger("0"))
                    && contarCapModulosDepartamento.equals(new BigInteger("0"))
                    && contarCiudadesDepartamento.equals(new BigInteger("0"))
                    && contarSoAccidentesMedicosDepartamento.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoDepartamentos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarBienProgramacionesDepartamento = new BigInteger("-1");
                contarCapModulosDepartamento = new BigInteger("-1");
                contarCiudadesDepartamento = new BigInteger("-1");
                contarSoAccidentesMedicosDepartamento = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlDepartamentos verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarDepartamentos.isEmpty() || !crearDepartamentos.isEmpty() || !modificarDepartamentos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarDepartamentos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarDepartamentos");
            if (!borrarDepartamentos.isEmpty()) {
                administrarDepartamentos.borrarDepartamentos(borrarDepartamentos);
                //mostrarBorrados
                registrosBorrados = borrarDepartamentos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarDepartamentos.clear();
            }
            if (!modificarDepartamentos.isEmpty()) {
                administrarDepartamentos.modificarDepartamentos(modificarDepartamentos);
                modificarDepartamentos.clear();
            }
            if (!crearDepartamentos.isEmpty()) {
                administrarDepartamentos.crearDepartamentos(crearDepartamentos);
                crearDepartamentos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listDepartamentos = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosDepartamentos");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarDepartamentos = listDepartamentos.get(index);
            }
            if (tipoLista == 1) {
                editarDepartamentos = filtrarDepartamentos.get(index);
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

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editPais");
                context.execute("editPais.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoDepartamentos() {
        System.out.println("agregarNuevoDepartamentos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoDepartamentos.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoDepartamentos.getCodigo());

            for (int x = 0; x < listDepartamentos.size(); x++) {
                if (listDepartamentos.get(x).getCodigo() == nuevoDepartamentos.getCodigo()) {
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
        if (nuevoDepartamentos.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoDepartamentos.getPais().getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Pais \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 3) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                pais = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:pais");
                pais.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDepartamentos");
                bandera = 0;
                filtrarDepartamentos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoDepartamentos.setSecuencia(l);

            crearDepartamentos.add(nuevoDepartamentos);

            listDepartamentos.add(nuevoDepartamentos);
            nuevoDepartamentos = new Departamentos();
            nuevoDepartamentos.setPais(new Paises());
            context.update("form:datosDepartamentos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroDepartamentos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoDepartamentos() {
        System.out.println("limpiarNuevoDepartamentos");
        nuevoDepartamentos = new Departamentos();
        nuevoDepartamentos.setPais(new Paises());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoDepartamentos() {
        System.out.println("duplicandoDepartamentos");
        if (index >= 0) {
            duplicarDepartamentos = new Departamentos();
            duplicarDepartamentos.setPais(new Paises());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarDepartamentos.setSecuencia(l);
                duplicarDepartamentos.setCodigo(listDepartamentos.get(index).getCodigo());
                duplicarDepartamentos.setNombre(listDepartamentos.get(index).getNombre());
                duplicarDepartamentos.getPais().setNombre(listDepartamentos.get(index).getPais().getNombre());
            }
            if (tipoLista == 1) {
                duplicarDepartamentos.setSecuencia(l);
                duplicarDepartamentos.setCodigo(filtrarDepartamentos.get(index).getCodigo());
                duplicarDepartamentos.setNombre(filtrarDepartamentos.get(index).getNombre());
                duplicarDepartamentos.getPais().setNombre(filtrarDepartamentos.get(index).getPais().getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroDepartamentos.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarDepartamentos.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarDepartamentos.getNombre());

        if (duplicarDepartamentos.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listDepartamentos.size(); x++) {
                if (listDepartamentos.get(x).getCodigo() == duplicarDepartamentos.getCodigo()) {
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
        if (duplicarDepartamentos.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarDepartamentos.getPais().getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * un Pais \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarDepartamentos.getSecuencia() + "  " + duplicarDepartamentos.getCodigo());
            if (crearDepartamentos.contains(duplicarDepartamentos)) {
                System.out.println("Ya lo contengo.");
            }
            listDepartamentos.add(duplicarDepartamentos);
            crearDepartamentos.add(duplicarDepartamentos);
            context.update("form:datosDepartamentos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                pais = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDepartamentos:pais");
                pais.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDepartamentos");
                bandera = 0;
                filtrarDepartamentos = null;
                tipoLista = 0;
            }
            duplicarDepartamentos = new Departamentos();
            duplicarDepartamentos.setPais(new Paises());

            RequestContext.getCurrentInstance().execute("duplicarRegistroDepartamentos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarDepartamentos() {
        duplicarDepartamentos = new Departamentos();
        duplicarDepartamentos.setPais(new Paises());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosDepartamentosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "DEPARTAMENTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosDepartamentosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "DEPARTAMENTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listDepartamentos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "DEPARTAMENTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("DEPARTAMENTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Departamentos> getListDepartamentos() {
        if (listDepartamentos == null) {
            listDepartamentos = administrarDepartamentos.consultarDepartamentos();
        }
        return listDepartamentos;
    }

    public void setListDepartamentos(List<Departamentos> listDepartamentos) {
        this.listDepartamentos = listDepartamentos;
    }

    public List<Departamentos> getFiltrarDepartamentos() {
        return filtrarDepartamentos;
    }

    public void setFiltrarDepartamentos(List<Departamentos> filtrarDepartamentos) {
        this.filtrarDepartamentos = filtrarDepartamentos;
    }

    public Departamentos getNuevoDepartamentos() {
        return nuevoDepartamentos;
    }

    public void setNuevoDepartamentos(Departamentos nuevoDepartamentos) {
        this.nuevoDepartamentos = nuevoDepartamentos;
    }

    public Departamentos getDuplicarDepartamentos() {
        return duplicarDepartamentos;
    }

    public void setDuplicarDepartamentos(Departamentos duplicarDepartamentos) {
        this.duplicarDepartamentos = duplicarDepartamentos;
    }

    public Departamentos getEditarDepartamentos() {
        return editarDepartamentos;
    }

    public void setEditarDepartamentos(Departamentos editarDepartamentos) {
        this.editarDepartamentos = editarDepartamentos;
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

    public List<Paises> getListaPaises() {
        if (listaPaises == null) {
            listaPaises = administrarDepartamentos.consultarLOVPaises();
        }
        return listaPaises;
    }

    public void setListaPaises(List<Paises> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public List<Paises> getFiltradoPaises() {
        return filtradoPaises;
    }

    public void setFiltradoPaises(List<Paises> filtradoPaises) {
        this.filtradoPaises = filtradoPaises;
    }

    public Paises getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(Paises paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

}

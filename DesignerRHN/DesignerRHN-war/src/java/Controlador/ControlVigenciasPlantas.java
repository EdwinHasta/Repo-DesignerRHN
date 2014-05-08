/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.VigenciasPlantas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVigenciasPlantasInterface;
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
public class ControlVigenciasPlantas implements Serializable {

    @EJB
    AdministrarVigenciasPlantasInterface administrarVigenciasPlantas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<VigenciasPlantas> listVigenciasPlantas;
    private List<VigenciasPlantas> filtrarVigenciasPlantas;
    private List<VigenciasPlantas> crearVigenciasPlantas;
    private List<VigenciasPlantas> modificarVigenciasPlantas;
    private List<VigenciasPlantas> borrarVigenciasPlantas;
    private VigenciasPlantas nuevoVigenciaPlanta;
    private VigenciasPlantas duplicarVigenciaPlanta;
    private VigenciasPlantas editarVigenciaPlanta;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, fecha;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;

    public ControlVigenciasPlantas() {
        listVigenciasPlantas = null;
        crearVigenciasPlantas = new ArrayList<VigenciasPlantas>();
        modificarVigenciasPlantas = new ArrayList<VigenciasPlantas>();
        borrarVigenciasPlantas = new ArrayList<VigenciasPlantas>();
        permitirIndex = true;
        editarVigenciaPlanta = new VigenciasPlantas();
        nuevoVigenciaPlanta = new VigenciasPlantas();
        duplicarVigenciaPlanta = new VigenciasPlantas();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasPlantas.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlVigenciasPlantas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlVigenciasPlantas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listVigenciasPlantas.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlVigenciasPlantas.asignarIndex \n");
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
            System.out.println("ERROR ControlVigenciasPlantas.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaPlanta");
            bandera = 0;
            filtrarVigenciasPlantas = null;
            tipoLista = 0;
        }

        borrarVigenciasPlantas.clear();
        crearVigenciasPlantas.clear();
        modificarVigenciasPlantas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasPlantas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaPlanta");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:codigo");
            codigo.setFilterStyle("width: 370px");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:fecha");
            fecha.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaPlanta");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaPlanta");
            bandera = 0;
            filtrarVigenciasPlantas = null;
            tipoLista = 0;
        }
    }

    public void mostrarInfo(int indice, int celda) {
        int contador = 0;
        int fechas = 0;
        if (permitirIndex == true) {
            RequestContext context = RequestContext.getCurrentInstance();

            mensajeValidacion = " ";
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listVigenciasPlantas.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listVigenciasPlantas.get(indice).getFechavigencia());
                if (listVigenciasPlantas.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < listVigenciasPlantas.size(); j++) {
                        if (j != indice) {
                            if (listVigenciasPlantas.get(indice).getFechavigencia().equals(listVigenciasPlantas.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "FECHAS REPETIDAS";
                    contador++;
                }
                if (contador == 0) {
                    if (!crearVigenciasPlantas.contains(listVigenciasPlantas.get(indice))) {
                        if (modificarVigenciasPlantas.isEmpty()) {
                            modificarVigenciasPlantas.add(listVigenciasPlantas.get(indice));
                        } else if (!modificarVigenciasPlantas.contains(listVigenciasPlantas.get(indice))) {
                            modificarVigenciasPlantas.add(listVigenciasPlantas.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            RequestContext.getCurrentInstance().update("form:ACEPTAR");
                        }
                        context.update("form:datosHvEntrevista");

                    }
                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();
                }
            } else {
                secRegistro = filtrarVigenciasPlantas.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + filtrarVigenciasPlantas.get(indice).getFechavigencia());
                if (filtrarVigenciasPlantas.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < filtrarVigenciasPlantas.size(); j++) {
                        if (j != indice) {
                            if (filtrarVigenciasPlantas.get(indice).getFechavigencia().equals(filtrarVigenciasPlantas.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                    for (int j = 0; j < listVigenciasPlantas.size(); j++) {
                        if (j != indice) {
                            if (filtrarVigenciasPlantas.get(indice).getFechavigencia().equals(listVigenciasPlantas.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "FECHAS REPETIDAS";
                    contador++;
                }
                if (contador == 0) {
                    if (!crearVigenciasPlantas.contains(filtrarVigenciasPlantas.get(indice))) {
                        if (modificarVigenciasPlantas.isEmpty()) {
                            modificarVigenciasPlantas.add(filtrarVigenciasPlantas.get(indice));
                        } else if (!modificarVigenciasPlantas.contains(filtrarVigenciasPlantas.get(indice))) {
                            modificarVigenciasPlantas.add(filtrarVigenciasPlantas.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            RequestContext.getCurrentInstance().update("form:ACEPTAR");
                        }
                        context.update("form:datosHvEntrevista");

                    }
                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();
                }
            }

            index = -1;
            secRegistro = null;
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);

    }

    public void modificarVigenciaPlanta(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO EMPRESA");
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
                if (!crearVigenciasPlantas.contains(listVigenciasPlantas.get(indice))) {
                    if (listVigenciasPlantas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listVigenciasPlantas.size(); j++) {
                            if (j != indice) {
                                if (listVigenciasPlantas.get(indice).getCodigo() == listVigenciasPlantas.get(j).getCodigo()) {
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
                        if (modificarVigenciasPlantas.isEmpty()) {
                            modificarVigenciasPlantas.add(listVigenciasPlantas.get(indice));
                        } else if (!modificarVigenciasPlantas.contains(listVigenciasPlantas.get(indice))) {
                            modificarVigenciasPlantas.add(listVigenciasPlantas.get(indice));
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

                if (!crearVigenciasPlantas.contains(filtrarVigenciasPlantas.get(indice))) {
                    if (filtrarVigenciasPlantas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < filtrarVigenciasPlantas.size(); j++) {
                            if (j != indice) {
                                if (filtrarVigenciasPlantas.get(indice).getCodigo() == listVigenciasPlantas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listVigenciasPlantas.size(); j++) {
                            if (j != indice) {
                                if (filtrarVigenciasPlantas.get(indice).getCodigo() == listVigenciasPlantas.get(j).getCodigo()) {
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
                        if (modificarVigenciasPlantas.isEmpty()) {
                            modificarVigenciasPlantas.add(filtrarVigenciasPlantas.get(indice));
                        } else if (!modificarVigenciasPlantas.contains(filtrarVigenciasPlantas.get(indice))) {
                            modificarVigenciasPlantas.add(filtrarVigenciasPlantas.get(indice));
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
            context.update("form:datosVigenciaPlanta");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoVigenciasPlantas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoVigenciasPlantas");
                if (!modificarVigenciasPlantas.isEmpty() && modificarVigenciasPlantas.contains(listVigenciasPlantas.get(index))) {
                    int modIndex = modificarVigenciasPlantas.indexOf(listVigenciasPlantas.get(index));
                    modificarVigenciasPlantas.remove(modIndex);
                    borrarVigenciasPlantas.add(listVigenciasPlantas.get(index));
                } else if (!crearVigenciasPlantas.isEmpty() && crearVigenciasPlantas.contains(listVigenciasPlantas.get(index))) {
                    int crearIndex = crearVigenciasPlantas.indexOf(listVigenciasPlantas.get(index));
                    crearVigenciasPlantas.remove(crearIndex);
                } else {
                    borrarVigenciasPlantas.add(listVigenciasPlantas.get(index));
                }
                listVigenciasPlantas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoVigenciasPlantas ");
                if (!modificarVigenciasPlantas.isEmpty() && modificarVigenciasPlantas.contains(filtrarVigenciasPlantas.get(index))) {
                    int modIndex = modificarVigenciasPlantas.indexOf(filtrarVigenciasPlantas.get(index));
                    modificarVigenciasPlantas.remove(modIndex);
                    borrarVigenciasPlantas.add(filtrarVigenciasPlantas.get(index));
                } else if (!crearVigenciasPlantas.isEmpty() && crearVigenciasPlantas.contains(filtrarVigenciasPlantas.get(index))) {
                    int crearIndex = crearVigenciasPlantas.indexOf(filtrarVigenciasPlantas.get(index));
                    crearVigenciasPlantas.remove(crearIndex);
                } else {
                    borrarVigenciasPlantas.add(filtrarVigenciasPlantas.get(index));
                }
                int VCIndex = listVigenciasPlantas.indexOf(filtrarVigenciasPlantas.get(index));
                listVigenciasPlantas.remove(VCIndex);
                filtrarVigenciasPlantas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaPlanta");
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
        BigInteger contarPlantasVigenciaPlanta;

        try {
            System.err.println("Control Secuencia de ControlVigenciasPlantas ");
            if (tipoLista == 0) {
                contarPlantasVigenciaPlanta = administrarVigenciasPlantas.contarPlantasVigenciaPlanta(listVigenciasPlantas.get(index).getSecuencia());
            } else {
                contarPlantasVigenciaPlanta = administrarVigenciasPlantas.contarPlantasVigenciaPlanta(filtrarVigenciasPlantas.get(index).getSecuencia());
            }
            if (contarPlantasVigenciaPlanta.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoVigenciasPlantas();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarPlantasVigenciaPlanta = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlVigenciasPlantas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarVigenciasPlantas.isEmpty() || !crearVigenciasPlantas.isEmpty() || !modificarVigenciasPlantas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarVigenciasPlantas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarVigenciasPlantas");
            if (!borrarVigenciasPlantas.isEmpty()) {
                administrarVigenciasPlantas.borrarVigenciasPlantas(borrarVigenciasPlantas);

                //mostrarBorrados
                registrosBorrados = borrarVigenciasPlantas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarVigenciasPlantas.clear();
            }
            if (!crearVigenciasPlantas.isEmpty()) {
                administrarVigenciasPlantas.crearVigenciasPlantas(crearVigenciasPlantas);
                crearVigenciasPlantas.clear();
            }
            if (!modificarVigenciasPlantas.isEmpty()) {
                administrarVigenciasPlantas.modificarVigenciasPlantas(modificarVigenciasPlantas);
                modificarVigenciasPlantas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listVigenciasPlantas = null;
            context.update("form:datosVigenciaPlanta");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciaPlanta = listVigenciasPlantas.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaPlanta = filtrarVigenciasPlantas.get(index);
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

    public void agregarNuevoVigenciasPlantas() {
        System.out.println("agregarNuevoVigenciasPlantas");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoVigenciaPlanta.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoVigenciaPlanta.getCodigo());

            for (int x = 0; x < listVigenciasPlantas.size(); x++) {
                if (listVigenciasPlantas.get(x).getCodigo() == nuevoVigenciaPlanta.getCodigo()) {
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
        if (nuevoVigenciaPlanta.getFechavigencia() == null || nuevoVigenciaPlanta.getFechavigencia().equals("")) {
            mensajeValidacion = " *Debe tener una fecha \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaPlanta");
                bandera = 0;
                filtrarVigenciasPlantas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoVigenciaPlanta.setSecuencia(l);

            crearVigenciasPlantas.add(nuevoVigenciaPlanta);

            listVigenciasPlantas.add(nuevoVigenciaPlanta);
            nuevoVigenciaPlanta = new VigenciasPlantas();
            context.update("form:datosVigenciaPlanta");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroVigenciasPlantas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoVigenciasPlantas() {
        System.out.println("limpiarNuevoVigenciasPlantas");
        nuevoVigenciaPlanta = new VigenciasPlantas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoVigenciasPlantas() {
        System.out.println("duplicandoVigenciasPlantas");
        if (index >= 0) {
            duplicarVigenciaPlanta = new VigenciasPlantas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVigenciaPlanta.setSecuencia(l);
                duplicarVigenciaPlanta.setCodigo(listVigenciasPlantas.get(index).getCodigo());
                duplicarVigenciaPlanta.setFechavigencia(listVigenciasPlantas.get(index).getFechavigencia());
            }
            if (tipoLista == 1) {
                duplicarVigenciaPlanta.setSecuencia(l);
                duplicarVigenciaPlanta.setCodigo(filtrarVigenciasPlantas.get(index).getCodigo());
                duplicarVigenciaPlanta.setFechavigencia(filtrarVigenciasPlantas.get(index).getFechavigencia());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroVigenciasPlantas.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarVigenciaPlanta.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarVigenciaPlanta.getFechavigencia());

        if (duplicarVigenciaPlanta.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listVigenciasPlantas.size(); x++) {
                if (listVigenciasPlantas.get(x).getCodigo() == duplicarVigenciaPlanta.getCodigo()) {
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
        if (duplicarVigenciaPlanta.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * una Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarVigenciaPlanta.getSecuencia() + "  " + duplicarVigenciaPlanta.getCodigo());
            if (crearVigenciasPlantas.contains(duplicarVigenciaPlanta)) {
                System.out.println("Ya lo contengo.");
            }
            listVigenciasPlantas.add(duplicarVigenciaPlanta);
            crearVigenciasPlantas.add(duplicarVigenciaPlanta);
            context.update("form:datosVigenciaPlanta");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaPlanta:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaPlanta");
                bandera = 0;
                filtrarVigenciasPlantas = null;
                tipoLista = 0;
            }
            duplicarVigenciaPlanta = new VigenciasPlantas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroVigenciasPlantas.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarVigenciasPlantas() {
        duplicarVigenciaPlanta = new VigenciasPlantas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciaPlantaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VIGENCIASPLANTAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciaPlantaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VIGENCIASPLANTAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listVigenciasPlantas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASPLANTAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASPLANTAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<VigenciasPlantas> getListVigenciasPlantas() {
        if (listVigenciasPlantas == null) {
            listVigenciasPlantas = administrarVigenciasPlantas.consultarVigenciasPlantas();
        }
        return listVigenciasPlantas;
    }

    public void setListVigenciasPlantas(List<VigenciasPlantas> listVigenciasPlantas) {
        this.listVigenciasPlantas = listVigenciasPlantas;
    }

    public List<VigenciasPlantas> getFiltrarVigenciasPlantas() {
        return filtrarVigenciasPlantas;
    }

    public void setFiltrarVigenciasPlantas(List<VigenciasPlantas> filtrarVigenciasPlantas) {
        this.filtrarVigenciasPlantas = filtrarVigenciasPlantas;
    }

    public VigenciasPlantas getNuevoVigenciaPlanta() {
        return nuevoVigenciaPlanta;
    }

    public void setNuevoVigenciaPlanta(VigenciasPlantas nuevoVigenciaPlanta) {
        this.nuevoVigenciaPlanta = nuevoVigenciaPlanta;
    }

    public VigenciasPlantas getDuplicarVigenciaPlanta() {
        return duplicarVigenciaPlanta;
    }

    public void setDuplicarVigenciaPlanta(VigenciasPlantas duplicarVigenciaPlanta) {
        this.duplicarVigenciaPlanta = duplicarVigenciaPlanta;
    }

    public VigenciasPlantas getEditarVigenciaPlanta() {
        return editarVigenciaPlanta;
    }

    public void setEditarVigenciaPlanta(VigenciasPlantas editarVigenciaPlanta) {
        this.editarVigenciaPlanta = editarVigenciaPlanta;
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

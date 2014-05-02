/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposReemplazos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposReemplazosInterface;
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
public class ControlTiposReemplazos implements Serializable {

    @EJB
    AdministrarTiposReemplazosInterface administrarTiposReemplazos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposReemplazos> listTiposReemplazos;
    private List<TiposReemplazos> filtrarTiposReemplazos;
    private List<TiposReemplazos> crearTiposReemplazos;
    private List<TiposReemplazos> modificarTiposReemplazos;
    private List<TiposReemplazos> borrarTiposReemplazos;
    private TiposReemplazos nuevoTipoReemplazo;
    private TiposReemplazos duplicarTipoReemplazo;
    private TiposReemplazos editarTipoReemplazo;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, factorReemplazado;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    BigDecimal verificarBorrandoEncargaturas;
    BigDecimal verificarBorradoProgramacionesTiempos;
    BigDecimal verificarBorradoReemplazos;
    //Redireccionamiento de pantallas
    private String paginaAnterior;

    public ControlTiposReemplazos() {
        listTiposReemplazos = null;
        crearTiposReemplazos = new ArrayList<TiposReemplazos>();
        modificarTiposReemplazos = new ArrayList<TiposReemplazos>();
        borrarTiposReemplazos = new ArrayList<TiposReemplazos>();
        permitirIndex = true;
        editarTipoReemplazo = new TiposReemplazos();
        nuevoTipoReemplazo = new TiposReemplazos();
        duplicarTipoReemplazo = new TiposReemplazos();
        guardado = true;
    }
    
    public void recibirPaginaEntrante(String pagina){
        paginaAnterior = pagina;  
        }
    
    public String redirigir(){
        return paginaAnterior;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLTIPOSREEMPLAZOS.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLTIPOSREEMPLAZOS eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposReemplazos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLTIPOSREEMPLAZOS ASIGNAR INDEX \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("TIPO ACTUALIZACION : " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLTIPOSREEMPLAZOS ASIGNAR INDEX ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            factorReemplazado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:factorReemplazado");
            factorReemplazado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposReemplazos = null;
            tipoLista = 0;
        }

        borrarTiposReemplazos.clear();
        crearTiposReemplazos.clear();
        modificarTiposReemplazos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposReemplazos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoReemplazo");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("width: 195px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("width: 265px");
            factorReemplazado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:factorReemplazado");
            factorReemplazado.setFilterStyle("width: 265px");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            factorReemplazado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:factorReemplazado");
            factorReemplazado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposReemplazos = null;
            tipoLista = 0;
        }
    }

    public void modificandoTipoReemplazo(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO REEMPLAZO");
        index = indice;

        int contador = 0;
        int contadorGuardar = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR TIPO REEMPLAZO, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposReemplazos.contains(listTiposReemplazos.get(indice))) {
                    if (listTiposReemplazos.get(indice).getCodigo() == a || listTiposReemplazos.get(indice).getCodigo().equals(null)) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposReemplazos.size(); j++) {
                            if (j != indice) {
                                if (listTiposReemplazos.get(indice).getCodigo().equals(listTiposReemplazos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            contadorGuardar++;
                        }

                    }
                    if (listTiposReemplazos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposReemplazos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (listTiposReemplazos.get(indice).getFactorreemplazado() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposReemplazos.get(indice).getFactorreemplazado().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarTiposReemplazos.isEmpty()) {
                            modificarTiposReemplazos.add(listTiposReemplazos.get(indice));
                        } else if (!modificarTiposReemplazos.contains(listTiposReemplazos.get(indice))) {
                            modificarTiposReemplazos.add(listTiposReemplazos.get(indice));
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

                if (!crearTiposReemplazos.contains(filtrarTiposReemplazos.get(indice))) {
                    if (filtrarTiposReemplazos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposReemplazos.size(); j++) {
                            System.err.println("indice lista  indice : " + listTiposReemplazos.get(j).getCodigo());
                            if (j != indice) {
                                if (filtrarTiposReemplazos.get(indice).getCodigo().equals(listTiposReemplazos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }

                        for (int j = 0; j < filtrarTiposReemplazos.size(); j++) {
                            System.err.println("indice filtrar indice : " + filtrarTiposReemplazos.get(j).getCodigo());
                            if (j != indice) {
                                if (filtrarTiposReemplazos.get(indice).getCodigo().equals(filtrarTiposReemplazos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            contadorGuardar++;
                        }

                    }

                    if (filtrarTiposReemplazos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarTiposReemplazos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (filtrarTiposReemplazos.get(indice).getFactorreemplazado() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarTiposReemplazos.get(indice).getFactorreemplazado().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarTiposReemplazos.isEmpty()) {
                            modificarTiposReemplazos.add(filtrarTiposReemplazos.get(indice));
                        } else if (!modificarTiposReemplazos.contains(filtrarTiposReemplazos.get(indice))) {
                            modificarTiposReemplazos.add(filtrarTiposReemplazos.get(indice));
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
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposReemplazos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarandoTiposReemplazos");
                if (!modificarTiposReemplazos.isEmpty() && modificarTiposReemplazos.contains(listTiposReemplazos.get(index))) {
                    int modIndex = modificarTiposReemplazos.indexOf(listTiposReemplazos.get(index));
                    modificarTiposReemplazos.remove(modIndex);
                    borrarTiposReemplazos.add(listTiposReemplazos.get(index));
                } else if (!crearTiposReemplazos.isEmpty() && crearTiposReemplazos.contains(listTiposReemplazos.get(index))) {
                    int crearIndex = crearTiposReemplazos.indexOf(listTiposReemplazos.get(index));
                    crearTiposReemplazos.remove(crearIndex);
                } else {
                    borrarTiposReemplazos.add(listTiposReemplazos.get(index));
                }
                listTiposReemplazos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarandoTiposReemplazos");
                if (!modificarTiposReemplazos.isEmpty() && modificarTiposReemplazos.contains(filtrarTiposReemplazos.get(index))) {
                    int modIndex = modificarTiposReemplazos.indexOf(filtrarTiposReemplazos.get(index));
                    modificarTiposReemplazos.remove(modIndex);
                    borrarTiposReemplazos.add(filtrarTiposReemplazos.get(index));
                } else if (!crearTiposReemplazos.isEmpty() && crearTiposReemplazos.contains(filtrarTiposReemplazos.get(index))) {
                    int crearIndex = crearTiposReemplazos.indexOf(filtrarTiposReemplazos.get(index));
                    crearTiposReemplazos.remove(crearIndex);
                } else {
                    borrarTiposReemplazos.add(filtrarTiposReemplazos.get(index));
                }
                int VCIndex = listTiposReemplazos.indexOf(filtrarTiposReemplazos.get(index));
                listTiposReemplazos.remove(VCIndex);
                filtrarTiposReemplazos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoReemplazo");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("ESTOY EN VERIFICAR BORRADO");
        BigInteger verificarBorrandoEncargaturas;
        BigInteger verificarBorradoProgramacionesTiempos;
        BigInteger verificarBorradoReemplazos;
        try {
            if (tipoLista == 0) {
                verificarBorrandoEncargaturas = administrarTiposReemplazos.contarEncargaturasTipoReemplazo(listTiposReemplazos.get(index).getSecuencia());
                verificarBorradoProgramacionesTiempos = administrarTiposReemplazos.contarProgramacionesTiemposTipoReemplazo(listTiposReemplazos.get(index).getSecuencia());
                verificarBorradoReemplazos = administrarTiposReemplazos.contarReemplazosTipoReemplazo(listTiposReemplazos.get(index).getSecuencia());
            } else {
                verificarBorrandoEncargaturas = administrarTiposReemplazos.contarEncargaturasTipoReemplazo(filtrarTiposReemplazos.get(index).getSecuencia());
                verificarBorradoProgramacionesTiempos = administrarTiposReemplazos.contarProgramacionesTiemposTipoReemplazo(filtrarTiposReemplazos.get(index).getSecuencia());
                verificarBorradoReemplazos = administrarTiposReemplazos.contarReemplazosTipoReemplazo(filtrarTiposReemplazos.get(index).getSecuencia());

            }

            if (verificarBorrandoEncargaturas.equals(new BigInteger("0")) && verificarBorradoProgramacionesTiempos.equals(new BigInteger("0")) && verificarBorradoReemplazos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposReemplazos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarBorrandoEncargaturas = new BigInteger("-1");
                verificarBorradoProgramacionesTiempos = new BigInteger("-1");
                verificarBorradoReemplazos = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposReemplazos.isEmpty() || !crearTiposReemplazos.isEmpty() || !modificarTiposReemplazos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTipoReemplazo() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando TipoReemplazo");
            if (!borrarTiposReemplazos.isEmpty()) {
                administrarTiposReemplazos.borrarTiposReemplazos(borrarTiposReemplazos);
                //mostrarBorrados
                registrosBorrados = borrarTiposReemplazos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposReemplazos.clear();
            }
            if (!crearTiposReemplazos.isEmpty()) {
                administrarTiposReemplazos.crearTiposReemplazos(crearTiposReemplazos);
                crearTiposReemplazos.clear();
            }
            if (!modificarTiposReemplazos.isEmpty()) {
                administrarTiposReemplazos.modificarTiposReemplazos(modificarTiposReemplazos);
                modificarTiposReemplazos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposReemplazos = null;
            guardado = true;
            context.update("form:datosTipoReemplazo");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoReemplazo = listTiposReemplazos.get(index);
            }
            if (tipoLista == 1) {
                editarTipoReemplazo = filtrarTiposReemplazos.get(index);
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
                context.update("formularioDialogos:editarFactorRiesgo");
                context.execute("editarFactorRiesgo.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoTiposReemplazos() {
        System.out.println("agregarNuevoTiposReemplazos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoReemplazo.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoReemplazo.getCodigo());

            for (int x = 0; x < listTiposReemplazos.size(); x++) {
                if (listTiposReemplazos.get(x).getCodigo() == nuevoTipoReemplazo.getCodigo()) {
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
        if (nuevoTipoReemplazo.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe tener un nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoTipoReemplazo.getFactorreemplazado() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener un factor \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                factorReemplazado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:factorReemplazado");
                factorReemplazado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposReemplazos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoReemplazo.setSecuencia(l);

            crearTiposReemplazos.add(nuevoTipoReemplazo);

            listTiposReemplazos.add(nuevoTipoReemplazo);
            nuevoTipoReemplazo = new TiposReemplazos();
            context.update("form:datosTipoReemplazo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposReemplazos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposReemplazos() {
        System.out.println("limpiarNuevoTiposReemplazos");
        nuevoTipoReemplazo = new TiposReemplazos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposReemplazos() {
        System.out.println("duplicandoTiposReemplazos");
        if (index >= 0) {
            duplicarTipoReemplazo = new TiposReemplazos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoReemplazo.setSecuencia(l);
                duplicarTipoReemplazo.setCodigo(listTiposReemplazos.get(index).getCodigo());
                duplicarTipoReemplazo.setNombre(listTiposReemplazos.get(index).getNombre());
                duplicarTipoReemplazo.setFactorreemplazado(listTiposReemplazos.get(index).getFactorreemplazado());
            }
            if (tipoLista == 1) {
                duplicarTipoReemplazo.setSecuencia(l);
                duplicarTipoReemplazo.setCodigo(filtrarTiposReemplazos.get(index).getCodigo());
                duplicarTipoReemplazo.setNombre(filtrarTiposReemplazos.get(index).getNombre());
                duplicarTipoReemplazo.setFactorreemplazado(filtrarTiposReemplazos.get(index).getFactorreemplazado());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTR");
            context.execute("duplicarRegistroTiposReemplazos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOSREEMPLAZOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;

        if (duplicarTipoReemplazo.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposReemplazos.size(); x++) {
                if (listTiposReemplazos.get(x).getCodigo() == duplicarTipoReemplazo.getCodigo()) {
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
        if (duplicarTipoReemplazo.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarTipoReemplazo.getFactorreemplazado() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un factor \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarTipoReemplazo.getSecuencia() + "  " + duplicarTipoReemplazo.getCodigo());
            if (crearTiposReemplazos.contains(duplicarTipoReemplazo)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposReemplazos.add(duplicarTipoReemplazo);
            crearTiposReemplazos.add(duplicarTipoReemplazo);
            context.update("form:datosTipoReemplazo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                factorReemplazado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:factorReemplazado");
                factorReemplazado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposReemplazos = null;
                tipoLista = 0;
            }
            duplicarTipoReemplazo = new TiposReemplazos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposReemplazos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposReemplazos() {
        duplicarTipoReemplazo = new TiposReemplazos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSREEMPLAZOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSREEMPLAZOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposReemplazos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSREEMPLAZOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSREEMPLAZOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<TiposReemplazos> getListTiposReemplazos() {
        if (listTiposReemplazos == null) {
            listTiposReemplazos = administrarTiposReemplazos.consultarTiposReemplazos();
        }
        return listTiposReemplazos;
    }

    public void setListTiposReemplazos(List<TiposReemplazos> listTiposReemplazos) {
        this.listTiposReemplazos = listTiposReemplazos;
    }

    public List<TiposReemplazos> getFiltrarTiposReemplazos() {
        return filtrarTiposReemplazos;
    }

    public void setFiltrarTiposReemplazos(List<TiposReemplazos> filtrarTiposReemplazos) {
        this.filtrarTiposReemplazos = filtrarTiposReemplazos;
    }

    public TiposReemplazos getNuevoTipoReemplazo() {
        return nuevoTipoReemplazo;
    }

    public void setNuevoTipoReemplazo(TiposReemplazos nuevoTipoReemplazo) {
        this.nuevoTipoReemplazo = nuevoTipoReemplazo;
    }

    public TiposReemplazos getDuplicarTipoReemplazo() {
        return duplicarTipoReemplazo;
    }

    public void setDuplicarTipoReemplazo(TiposReemplazos duplicarTipoReemplazo) {
        this.duplicarTipoReemplazo = duplicarTipoReemplazo;
    }

    public TiposReemplazos getEditarTipoReemplazo() {
        return editarTipoReemplazo;
    }

    public void setEditarTipoReemplazo(TiposReemplazos editarTipoReemplazo) {
        this.editarTipoReemplazo = editarTipoReemplazo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
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

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

}

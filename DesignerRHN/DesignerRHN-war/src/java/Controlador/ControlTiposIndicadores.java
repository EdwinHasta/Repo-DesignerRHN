/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposIndicadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposIndicadoresInterface;
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
public class ControlTiposIndicadores implements Serializable {

    @EJB
    AdministrarTiposIndicadoresInterface administrarTiposIndicadores;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposIndicadores> listTiposIndicadores;
    private List<TiposIndicadores> filtrarTiposIndicadores;
    private List<TiposIndicadores> crearTiposIndicadores;
    private List<TiposIndicadores> modificarTiposIndicadores;
    private List<TiposIndicadores> borrarTiposIndicadores;
    private TiposIndicadores nuevoTipoIndicador;
    private TiposIndicadores duplicarTipoIndicador;
    private TiposIndicadores editarTipoIndicador;
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

    public ControlTiposIndicadores() {
        listTiposIndicadores = null;
        crearTiposIndicadores = new ArrayList<TiposIndicadores>();
        modificarTiposIndicadores = new ArrayList<TiposIndicadores>();
        borrarTiposIndicadores = new ArrayList<TiposIndicadores>();
        permitirIndex = true;
        editarTipoIndicador = new TiposIndicadores();
        nuevoTipoIndicador = new TiposIndicadores();
        duplicarTipoIndicador = new TiposIndicadores();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposCertificados.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposCertificados eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposIndicadores.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposCertificados.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposCertificados.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoIndicador");
            bandera = 0;
            filtrarTiposIndicadores = null;
            tipoLista = 0;
        }

        borrarTiposIndicadores.clear();
        crearTiposIndicadores.clear();
        modificarTiposIndicadores.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposIndicadores = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoIndicador");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTipoIndicador");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoIndicador");
            bandera = 0;
            filtrarTiposIndicadores = null;
            tipoLista = 0;
        }
    }

    public void modificarTipoCertificado(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPOSCERTIFICADOS");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVODEMANDA, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposIndicadores.contains(listTiposIndicadores.get(indice))) {
                    if (listTiposIndicadores.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposIndicadores.size(); j++) {
                            if (j != indice) {
                                if (listTiposIndicadores.get(indice).getCodigo() == listTiposIndicadores.get(j).getCodigo()) {
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
                    if (listTiposIndicadores.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposIndicadores.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposIndicadores.isEmpty()) {
                            modificarTiposIndicadores.add(listTiposIndicadores.get(indice));
                        } else if (!modificarTiposIndicadores.contains(listTiposIndicadores.get(indice))) {
                            modificarTiposIndicadores.add(listTiposIndicadores.get(indice));
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

                if (!crearTiposIndicadores.contains(filtrarTiposIndicadores.get(indice))) {
                    if (filtrarTiposIndicadores.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposIndicadores.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposIndicadores.get(indice).getCodigo() == listTiposIndicadores.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarTiposIndicadores.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposIndicadores.get(indice).getCodigo() == filtrarTiposIndicadores.get(j).getCodigo()) {
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

                    if (filtrarTiposIndicadores.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposIndicadores.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposIndicadores.isEmpty()) {
                            modificarTiposIndicadores.add(filtrarTiposIndicadores.get(indice));
                        } else if (!modificarTiposIndicadores.contains(filtrarTiposIndicadores.get(indice))) {
                            modificarTiposIndicadores.add(filtrarTiposIndicadores.get(indice));
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
            context.update("form:datosTipoIndicador");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposCertificados() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarandoTiposCertificados");
                if (!modificarTiposIndicadores.isEmpty() && modificarTiposIndicadores.contains(listTiposIndicadores.get(index))) {
                    int modIndex = modificarTiposIndicadores.indexOf(listTiposIndicadores.get(index));
                    modificarTiposIndicadores.remove(modIndex);
                    borrarTiposIndicadores.add(listTiposIndicadores.get(index));
                } else if (!crearTiposIndicadores.isEmpty() && crearTiposIndicadores.contains(listTiposIndicadores.get(index))) {
                    int crearIndex = crearTiposIndicadores.indexOf(listTiposIndicadores.get(index));
                    crearTiposIndicadores.remove(crearIndex);
                } else {
                    borrarTiposIndicadores.add(listTiposIndicadores.get(index));
                }
                listTiposIndicadores.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarandoTiposCertificados ");
                if (!modificarTiposIndicadores.isEmpty() && modificarTiposIndicadores.contains(filtrarTiposIndicadores.get(index))) {
                    int modIndex = modificarTiposIndicadores.indexOf(filtrarTiposIndicadores.get(index));
                    modificarTiposIndicadores.remove(modIndex);
                    borrarTiposIndicadores.add(filtrarTiposIndicadores.get(index));
                } else if (!crearTiposIndicadores.isEmpty() && crearTiposIndicadores.contains(filtrarTiposIndicadores.get(index))) {
                    int crearIndex = crearTiposIndicadores.indexOf(filtrarTiposIndicadores.get(index));
                    crearTiposIndicadores.remove(crearIndex);
                } else {
                    borrarTiposIndicadores.add(filtrarTiposIndicadores.get(index));
                }
                int VCIndex = listTiposIndicadores.indexOf(filtrarTiposIndicadores.get(index));
                listTiposIndicadores.remove(VCIndex);
                filtrarTiposIndicadores.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoIndicador");
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
        BigInteger vigenciasIndicadores;

        try {
            System.err.println("Control Secuencia de MotivosDemandas a borrar");
            if (tipoLista == 0) {
                vigenciasIndicadores = administrarTiposIndicadores.contarVigenciasIndicadoresTipoIndicador(listTiposIndicadores.get(index).getSecuencia());
            } else {
                vigenciasIndicadores = administrarTiposIndicadores.contarVigenciasIndicadoresTipoIndicador(filtrarTiposIndicadores.get(index).getSecuencia());
            }
            if (vigenciasIndicadores.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposCertificados();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                vigenciasIndicadores = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposIndicadores.isEmpty() || !crearTiposIndicadores.isEmpty() || !modificarTiposIndicadores.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTipoCertificado() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando TipoCertificados");
            if (!borrarTiposIndicadores.isEmpty()) {
                administrarTiposIndicadores.borrarTiposIndicadores(borrarTiposIndicadores);
                //mostrarBorrados
                registrosBorrados = borrarTiposIndicadores.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposIndicadores.clear();
            }
            if (!crearTiposIndicadores.isEmpty()) {
                administrarTiposIndicadores.crearTiposIndicadores(crearTiposIndicadores);
                crearTiposIndicadores.clear();
            }
            if (!modificarTiposIndicadores.isEmpty()) {
                administrarTiposIndicadores.modificarTiposIndicadores(modificarTiposIndicadores);
                modificarTiposIndicadores.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposIndicadores = null;
            context.update("form:datosTipoIndicador");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoIndicador = listTiposIndicadores.get(index);
            }
            if (tipoLista == 1) {
                editarTipoIndicador = filtrarTiposIndicadores.get(index);
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

    public void agregarNuevoTiposCertificados() {
        System.out.println("agregarNuevoTiposCertificados");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoIndicador.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoIndicador.getCodigo());

            for (int x = 0; x < listTiposIndicadores.size(); x++) {
                if (listTiposIndicadores.get(x).getCodigo() == nuevoTipoIndicador.getCodigo()) {
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
        if (nuevoTipoIndicador.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoIndicador");
                bandera = 0;
                filtrarTiposIndicadores = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoIndicador.setSecuencia(l);

            crearTiposIndicadores.add(nuevoTipoIndicador);

            listTiposIndicadores.add(nuevoTipoIndicador);
            nuevoTipoIndicador = new TiposIndicadores();
            context.update("form:datosTipoIndicador");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposIndicadores.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposCertificados() {
        System.out.println("limpiarNuevoTiposCertificados");
        nuevoTipoIndicador = new TiposIndicadores();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposCertificados() {
        System.out.println("duplicandoTiposCertificados");
        if (index >= 0) {
            duplicarTipoIndicador = new TiposIndicadores();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoIndicador.setSecuencia(l);
                duplicarTipoIndicador.setCodigo(listTiposIndicadores.get(index).getCodigo());
                duplicarTipoIndicador.setDescripcion(listTiposIndicadores.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTipoIndicador.setSecuencia(l);
                duplicarTipoIndicador.setCodigo(filtrarTiposIndicadores.get(index).getCodigo());
                duplicarTipoIndicador.setDescripcion(filtrarTiposIndicadores.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTI");
            context.execute("duplicarRegistroTiposIndicadores.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOSINDICADORES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoIndicador.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTipoIndicador.getDescripcion());

        if (duplicarTipoIndicador.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposIndicadores.size(); x++) {
                if (listTiposIndicadores.get(x).getCodigo() == duplicarTipoIndicador.getCodigo()) {
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
        if (duplicarTipoIndicador.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoIndicador.getSecuencia() + "  " + duplicarTipoIndicador.getCodigo());
            if (crearTiposIndicadores.contains(duplicarTipoIndicador)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposIndicadores.add(duplicarTipoIndicador);
            crearTiposIndicadores.add(duplicarTipoIndicador);
            context.update("form:datosTipoIndicador");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoIndicador:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoIndicador");
                bandera = 0;
                filtrarTiposIndicadores = null;
                tipoLista = 0;
            }
            duplicarTipoIndicador = new TiposIndicadores();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposIndicadores.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposCertificados() {
        duplicarTipoIndicador = new TiposIndicadores();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoIndicadorExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSINDICADORES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoIndicadorExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSINDICADORES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposIndicadores.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSINDICADORES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSINDICADORES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<TiposIndicadores> getListTiposIndicadores() {
        if (listTiposIndicadores == null) {
            listTiposIndicadores = administrarTiposIndicadores.consultarTiposIndicadores();
        }
        return listTiposIndicadores;
    }

    public void setListTiposIndicadores(List<TiposIndicadores> listTiposIndicadores) {
        this.listTiposIndicadores = listTiposIndicadores;
    }

    public List<TiposIndicadores> getFiltrarTiposIndicadores() {
        return filtrarTiposIndicadores;
    }

    public void setFiltrarTiposIndicadores(List<TiposIndicadores> filtrarTiposIndicadores) {
        this.filtrarTiposIndicadores = filtrarTiposIndicadores;
    }

    public TiposIndicadores getNuevoTipoIndicador() {
        return nuevoTipoIndicador;
    }

    public void setNuevoTipoIndicador(TiposIndicadores nuevoTipoIndicador) {
        this.nuevoTipoIndicador = nuevoTipoIndicador;
    }

    public TiposIndicadores getDuplicarTipoIndicador() {
        return duplicarTipoIndicador;
    }

    public void setDuplicarTipoIndicador(TiposIndicadores duplicarTipoIndicador) {
        this.duplicarTipoIndicador = duplicarTipoIndicador;
    }

    public TiposIndicadores getEditarTipoIndicador() {
        return editarTipoIndicador;
    }

    public void setEditarTipoIndicador(TiposIndicadores editarTipoIndicador) {
        this.editarTipoIndicador = editarTipoIndicador;
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

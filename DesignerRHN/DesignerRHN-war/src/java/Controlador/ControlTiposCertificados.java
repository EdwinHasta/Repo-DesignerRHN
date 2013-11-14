/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposCertificados;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposCertificadosInterface;
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
public class ControlTiposCertificados implements Serializable {

    @EJB
    AdministrarTiposCertificadosInterface administrarTiposCertificados;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposCertificados> listTiposCertificados;
    private List<TiposCertificados> filtrarTiposCertificados;
    private List<TiposCertificados> crearTiposCertificados;
    private List<TiposCertificados> modificarTiposCertificados;
    private List<TiposCertificados> borrarTiposCertificados;
    private TiposCertificados nuevoTipoCertificado;
    private TiposCertificados duplicarTipoCertificado;
    private TiposCertificados editarTipoCertificado;
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
    private Long borradoVC;

    public ControlTiposCertificados() {
        listTiposCertificados = null;
        crearTiposCertificados = new ArrayList<TiposCertificados>();
        modificarTiposCertificados = new ArrayList<TiposCertificados>();
        borrarTiposCertificados = new ArrayList<TiposCertificados>();
        permitirIndex = true;
        editarTipoCertificado = new TiposCertificados();
        nuevoTipoCertificado = new TiposCertificados();
        duplicarTipoCertificado = new TiposCertificados();
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlNormasLaborales.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlNormasLaborales eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposCertificados.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlNormasLaborales.asignarIndex \n");
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
            System.out.println("ERROR ControlNormasLaborales.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoCertificado");
            bandera = 0;
            filtrarTiposCertificados = null;
            tipoLista = 0;
        }

        borrarTiposCertificados.clear();
        crearTiposCertificados.clear();
        modificarTiposCertificados.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposCertificados = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoCertificado");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTipoCertificado");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoCertificado");
            bandera = 0;
            filtrarTiposCertificados = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposCertificados(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR Motivos Mvrs");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR Motivos Mvrs, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposCertificados.contains(listTiposCertificados.get(indice))) {
                    if (listTiposCertificados.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposCertificados.size(); j++) {
                            if (j != indice) {
                                if (listTiposCertificados.get(indice).getCodigo() == listTiposCertificados.get(j).getCodigo()) {
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
                    if (listTiposCertificados.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposCertificados.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposCertificados.isEmpty()) {
                            modificarTiposCertificados.add(listTiposCertificados.get(indice));
                        } else if (!modificarTiposCertificados.contains(listTiposCertificados.get(indice))) {
                            modificarTiposCertificados.add(listTiposCertificados.get(indice));
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


                if (!crearTiposCertificados.contains(filtrarTiposCertificados.get(indice))) {
                    if (filtrarTiposCertificados.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposCertificados.size(); j++) {
                            if (j != indice) {
                                if (listTiposCertificados.get(indice).getCodigo() == listTiposCertificados.get(j).getCodigo()) {
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


                    if (filtrarTiposCertificados.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposCertificados.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }


                    if (banderita == true) {
                        if (modificarTiposCertificados.isEmpty()) {
                            modificarTiposCertificados.add(filtrarTiposCertificados.get(indice));
                        } else if (!modificarTiposCertificados.contains(filtrarTiposCertificados.get(indice))) {
                            modificarTiposCertificados.add(filtrarTiposCertificados.get(indice));
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
            context.update("form:datosTipoCertificado");
        }

    }

    public void borrarTiposCertificados() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarNormasLaborales");
                if (!modificarTiposCertificados.isEmpty() && modificarTiposCertificados.contains(listTiposCertificados.get(index))) {
                    int modIndex = modificarTiposCertificados.indexOf(listTiposCertificados.get(index));
                    modificarTiposCertificados.remove(modIndex);
                    borrarTiposCertificados.add(listTiposCertificados.get(index));
                } else if (!crearTiposCertificados.isEmpty() && crearTiposCertificados.contains(listTiposCertificados.get(index))) {
                    int crearIndex = crearTiposCertificados.indexOf(listTiposCertificados.get(index));
                    crearTiposCertificados.remove(crearIndex);
                } else {
                    borrarTiposCertificados.add(listTiposCertificados.get(index));
                }
                listTiposCertificados.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarMotivosMvrs ");
                if (!modificarTiposCertificados.isEmpty() && modificarTiposCertificados.contains(filtrarTiposCertificados.get(index))) {
                    int modIndex = modificarTiposCertificados.indexOf(filtrarTiposCertificados.get(index));
                    modificarTiposCertificados.remove(modIndex);
                    borrarTiposCertificados.add(filtrarTiposCertificados.get(index));
                } else if (!crearTiposCertificados.isEmpty() && crearTiposCertificados.contains(filtrarTiposCertificados.get(index))) {
                    int crearIndex = crearTiposCertificados.indexOf(filtrarTiposCertificados.get(index));
                    crearTiposCertificados.remove(crearIndex);
                } else {
                    borrarTiposCertificados.add(filtrarTiposCertificados.get(index));
                }
                int VCIndex = listTiposCertificados.indexOf(filtrarTiposCertificados.get(index));
                listTiposCertificados.remove(VCIndex);
                filtrarTiposCertificados.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoCertificado");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    /* public void verificarBorrado() {
     System.out.println("Estoy en verificarBorrado");
     try {
     borradoVC = administrarNormasLaborales.verificarBorradoVNE(listNormasLaborales.get(index).getSecuencia());
     if (borradoVC.intValue() == 0) {
     System.out.println("Borrado==0");
     borrarNormasLaborales();
     }
     if (borradoVC.intValue() != 0) {
     System.out.println("Borrado>0");

     RequestContext context = RequestContext.getCurrentInstance();
     context.update("form:validacionBorrar");
     context.execute("validacionBorrar.show()");
     index = -1;
     borradoVC = new Long(-1);
     }

     } catch (Exception e) {
     System.err.println("ERROR ControlNormasLaborales verificarBorrado ERROR " + e);
     }
     }
     */
    public void guardarTiposCertificados() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Motivos Mvrs");
            if (!borrarTiposCertificados.isEmpty()) {
                for (int i = 0; i < borrarTiposCertificados.size(); i++) {
                    System.out.println("Borrando...");
                    administrarTiposCertificados.borrarTiposCertificados(borrarTiposCertificados.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarTiposCertificados.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposCertificados.clear();
            }
            if (!crearTiposCertificados.isEmpty()) {
                for (int i = 0; i < crearTiposCertificados.size(); i++) {

                    System.out.println("Creando...");
                    administrarTiposCertificados.crearTiposCertificados(crearTiposCertificados.get(i));

                }
                crearTiposCertificados.clear();
            }
            if (!modificarTiposCertificados.isEmpty()) {
                administrarTiposCertificados.modificarTiposCertificados(modificarTiposCertificados);
                modificarTiposCertificados.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposCertificados = null;
            context.update("form:datosTipoCertificado");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:aceptar");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoCertificado = listTiposCertificados.get(index);
            }
            if (tipoLista == 1) {
                editarTipoCertificado = filtrarTiposCertificados.get(index);
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
        System.out.println("Agregar Norma Laboral");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoCertificado.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoCertificado.getCodigo());

            for (int x = 0; x < listTiposCertificados.size(); x++) {
                if (listTiposCertificados.get(x).getCodigo() == nuevoTipoCertificado.getCodigo()) {
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
        if (nuevoTipoCertificado.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Nombre \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoCertificado");
                bandera = 0;
                filtrarTiposCertificados = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoCertificado.setSecuencia(l);

            crearTiposCertificados.add(nuevoTipoCertificado);

            listTiposCertificados.add(nuevoTipoCertificado);
            nuevoTipoCertificado = new TiposCertificados();

            context.update("form:datosTipoCertificado");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }

            context.execute("nuevoRegistroTipoCertificado.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposCertificados() {
        System.out.println("limpiarNuevoMotivoMvrs");
        nuevoTipoCertificado = new TiposCertificados();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarTiposCertificados() {
        System.out.println("duplicarTiposCertificados");
        if (index >= 0) {
            duplicarTipoCertificado = new TiposCertificados();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoCertificado.setSecuencia(l);
                duplicarTipoCertificado.setCodigo(listTiposCertificados.get(index).getCodigo());
                duplicarTipoCertificado.setDescripcion(listTiposCertificados.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTipoCertificado.setSecuencia(l);
                duplicarTipoCertificado.setCodigo(filtrarTiposCertificados.get(index).getCodigo());
                duplicarTipoCertificado.setDescripcion(filtrarTiposCertificados.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTiposCertificados");
            context.execute("duplicarRegistroTiposCertificados.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLMOTIVOMVRS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoCertificado.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTipoCertificado.getDescripcion());

        if (duplicarTipoCertificado.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposCertificados.size(); x++) {
                if (listTiposCertificados.get(x).getCodigo() == duplicarTipoCertificado.getCodigo()) {
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
        if (duplicarTipoCertificado.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }


        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoCertificado.getSecuencia() + "  " + duplicarTipoCertificado.getCodigo());
            if (crearTiposCertificados.contains(duplicarTipoCertificado)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposCertificados.add(duplicarTipoCertificado);
            crearTiposCertificados.add(duplicarTipoCertificado);
            context.update("form:datosTipoCertificado");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCertificado:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoCertificado");
                bandera = 0;
                filtrarTiposCertificados = null;
                tipoLista = 0;
            }
            duplicarTipoCertificado = new TiposCertificados();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposCertificados.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposCertificados() {
        duplicarTipoCertificado = new TiposCertificados();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoMvrExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposCertificados", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoMvrExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposCertificados", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposCertificados.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCERTIFICADOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCERTIFICADOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

//------------------------------------------------------------------------------
    public List<TiposCertificados> getListTiposCertificados() {
        if (listTiposCertificados == null) {
            listTiposCertificados = administrarTiposCertificados.mostrarTiposCertificados();
        }
        return listTiposCertificados;
    }

    public void setListTiposCertificados(List<TiposCertificados> listTiposCertificados) {
        this.listTiposCertificados = listTiposCertificados;
    }

    public List<TiposCertificados> getFiltrarTiposCertificados() {
        return filtrarTiposCertificados;
    }

    public void setFiltrarTiposCertificados(List<TiposCertificados> filtrarTiposCertificados) {
        this.filtrarTiposCertificados = filtrarTiposCertificados;
    }

    public TiposCertificados getNuevoTipoCertificado() {
        return nuevoTipoCertificado;
    }

    public void setNuevoTipoCertificado(TiposCertificados nuevoTipoCertificado) {
        this.nuevoTipoCertificado = nuevoTipoCertificado;
    }

    public TiposCertificados getDuplicarTipoCertificado() {
        return duplicarTipoCertificado;
    }

    public void setDuplicarTipoCertificado(TiposCertificados duplicarTipoCertificado) {
        this.duplicarTipoCertificado = duplicarTipoCertificado;
    }

    public TiposCertificados getEditarTipoCertificado() {
        return editarTipoCertificado;
    }

    public void setEditarTipoCertificado(TiposCertificados editarTipoCertificado) {
        this.editarTipoCertificado = editarTipoCertificado;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Motivosmvrs;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMotivosMvrsInterface;
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
public class ControlMotivosMvrs implements Serializable {

    @EJB
    AdministrarMotivosMvrsInterface administrarMotivosMvrs;
     @EJB
    AdministrarRastrosInterface administrarRastros;
   
    private List<Motivosmvrs> listMotivosMvrs;
    private List<Motivosmvrs> filtrarMotivosMvrs;
    private List<Motivosmvrs> crearMotivoMvrs;
    private List<Motivosmvrs> modificarMotivoMvrs;
    private List<Motivosmvrs> borrarMotivoMvrs;
    private Motivosmvrs nuevoMotivoMvr;
    private Motivosmvrs duplicarMotivosMvrs;
    private Motivosmvrs editarMotivosMvrs;
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

    public ControlMotivosMvrs() {
        listMotivosMvrs = null;
        crearMotivoMvrs = new ArrayList<Motivosmvrs>();
        modificarMotivoMvrs = new ArrayList<Motivosmvrs>();
        borrarMotivoMvrs = new ArrayList<Motivosmvrs>();
        permitirIndex = true;
        editarMotivosMvrs = new Motivosmvrs();
        nuevoMotivoMvr = new Motivosmvrs();
        duplicarMotivosMvrs = new Motivosmvrs();
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
            secRegistro = listMotivosMvrs.get(index).getSecuencia();

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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoMvr");
            bandera = 0;
            filtrarMotivosMvrs = null;
            tipoLista = 0;
        }

        borrarMotivoMvrs.clear();
        crearMotivoMvrs.clear();
        modificarMotivoMvrs.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMotivosMvrs = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMotivoMvr");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosMotivoMvr");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoMvr");
            bandera = 0;
            filtrarMotivosMvrs = null;
            tipoLista = 0;
        }
    }

    public void modificarMotivosMvrs(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearMotivoMvrs.contains(listMotivosMvrs.get(indice))) {
                    if (listMotivosMvrs.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosMvrs.size(); j++) {
                            if (j != indice) {
                                if (listMotivosMvrs.get(indice).getCodigo() == listMotivosMvrs.get(j).getCodigo()) {
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
                    if (listMotivosMvrs.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMotivosMvrs.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMotivoMvrs.isEmpty()) {
                            modificarMotivoMvrs.add(listMotivosMvrs.get(indice));
                        } else if (!modificarMotivoMvrs.contains(listMotivosMvrs.get(indice))) {
                            modificarMotivoMvrs.add(listMotivosMvrs.get(indice));
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


                if (!crearMotivoMvrs.contains(filtrarMotivosMvrs.get(indice))) {
                    if (filtrarMotivosMvrs.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosMvrs.size(); j++) {
                            if (j != indice) {
                                if (listMotivosMvrs.get(indice).getCodigo() == listMotivosMvrs.get(j).getCodigo()) {
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


                    if (filtrarMotivosMvrs.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMotivosMvrs.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }


                    if (banderita == true) {
                        if (modificarMotivoMvrs.isEmpty()) {
                            modificarMotivoMvrs.add(filtrarMotivosMvrs.get(indice));
                        } else if (!modificarMotivoMvrs.contains(filtrarMotivosMvrs.get(indice))) {
                            modificarMotivoMvrs.add(filtrarMotivosMvrs.get(indice));
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
            context.update("form:datosMotivoMvr");
        }

    }

    public void borrarMotivosMvrs() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarNormasLaborales");
                if (!modificarMotivoMvrs.isEmpty() && modificarMotivoMvrs.contains(listMotivosMvrs.get(index))) {
                    int modIndex = modificarMotivoMvrs.indexOf(listMotivosMvrs.get(index));
                    modificarMotivoMvrs.remove(modIndex);
                    borrarMotivoMvrs.add(listMotivosMvrs.get(index));
                } else if (!crearMotivoMvrs.isEmpty() && crearMotivoMvrs.contains(listMotivosMvrs.get(index))) {
                    int crearIndex = crearMotivoMvrs.indexOf(listMotivosMvrs.get(index));
                    crearMotivoMvrs.remove(crearIndex);
                } else {
                    borrarMotivoMvrs.add(listMotivosMvrs.get(index));
                }
                listMotivosMvrs.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarMotivosMvrs ");
                if (!modificarMotivoMvrs.isEmpty() && modificarMotivoMvrs.contains(filtrarMotivosMvrs.get(index))) {
                    int modIndex = modificarMotivoMvrs.indexOf(filtrarMotivosMvrs.get(index));
                    modificarMotivoMvrs.remove(modIndex);
                    borrarMotivoMvrs.add(filtrarMotivosMvrs.get(index));
                } else if (!crearMotivoMvrs.isEmpty() && crearMotivoMvrs.contains(filtrarMotivosMvrs.get(index))) {
                    int crearIndex = crearMotivoMvrs.indexOf(filtrarMotivosMvrs.get(index));
                    crearMotivoMvrs.remove(crearIndex);
                } else {
                    borrarMotivoMvrs.add(filtrarMotivosMvrs.get(index));
                }
                int VCIndex = listMotivosMvrs.indexOf(filtrarMotivosMvrs.get(index));
                listMotivosMvrs.remove(VCIndex);
                filtrarMotivosMvrs.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMotivoMvr");
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
    public void guardarMotivosMvrs() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Motivos Mvrs");
            if (!borrarMotivoMvrs.isEmpty()) {
                for (int i = 0; i < borrarMotivoMvrs.size(); i++) {
                    System.out.println("Borrando...");
                    administrarMotivosMvrs.borrarMotivosMvrs(borrarMotivoMvrs.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarMotivoMvrs.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivoMvrs.clear();
            }
            if (!crearMotivoMvrs.isEmpty()) {
                for (int i = 0; i < crearMotivoMvrs.size(); i++) {

                    System.out.println("Creando...");
                    administrarMotivosMvrs.crearMotivosMvrs(crearMotivoMvrs.get(i));

                }
                crearMotivoMvrs.clear();
            }
            if (!modificarMotivoMvrs.isEmpty()) {
                administrarMotivosMvrs.modificarMotivosMvrs(modificarMotivoMvrs);
                modificarMotivoMvrs.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosMvrs = null;
            context.update("form:datosMotivoMvr");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:aceptar");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMotivosMvrs = listMotivosMvrs.get(index);
            }
            if (tipoLista == 1) {
                editarMotivosMvrs = filtrarMotivosMvrs.get(index);
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

    public void agregarNuevoMotivoMvrs() {
        System.out.println("Agregar Norma Laboral");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoMvr.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoMvr.getCodigo());

            for (int x = 0; x < listMotivosMvrs.size(); x++) {
                if (listMotivosMvrs.get(x).getCodigo() == nuevoMotivoMvr.getCodigo()) {
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
        if (nuevoMotivoMvr.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Una  Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoMvr");
                bandera = 0;
                filtrarMotivosMvrs = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoMvr.setSecuencia(l);

            crearMotivoMvrs.add(nuevoMotivoMvr);

            listMotivosMvrs.add(nuevoMotivoMvr);
            nuevoMotivoMvr = new Motivosmvrs();

            context.update("form:datosMotivoMvr");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }

            context.execute("nuevoRegistroMotivoMvrs.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMotivoMvrs() {
        System.out.println("limpiarNuevoMotivoMvrs");
        nuevoMotivoMvr = new Motivosmvrs();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarMotivoMvrs() {
        System.out.println("duplicarMotivoMvr");
        if (index >= 0) {
            duplicarMotivosMvrs = new Motivosmvrs();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivosMvrs.setSecuencia(l);
                duplicarMotivosMvrs.setCodigo(listMotivosMvrs.get(index).getCodigo());
                duplicarMotivosMvrs.setNombre(listMotivosMvrs.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarMotivosMvrs.setSecuencia(l);
                duplicarMotivosMvrs.setCodigo(filtrarMotivosMvrs.get(index).getCodigo());
                duplicarMotivosMvrs.setNombre(filtrarMotivosMvrs.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivoMvr");
            context.execute("duplicarRegistroMotivosMvr.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarMotivosMvrs.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarMotivosMvrs.getNombre());

        if (duplicarMotivosMvrs.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosMvrs.size(); x++) {
                if (listMotivosMvrs.get(x).getCodigo() == duplicarMotivosMvrs.getCodigo()) {
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
        if (duplicarMotivosMvrs.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }


        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivosMvrs.getSecuencia() + "  " + duplicarMotivosMvrs.getCodigo());
            if (crearMotivoMvrs.contains(duplicarMotivosMvrs)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosMvrs.add(duplicarMotivosMvrs);
            crearMotivoMvrs.add(duplicarMotivosMvrs);
            context.update("form:datosMotivoMvr");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoMvr:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoMvr");
                bandera = 0;
                filtrarMotivosMvrs = null;
                tipoLista = 0;
            }
            duplicarMotivosMvrs = new Motivosmvrs();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMotivosMvr.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarMotivosMvrs() {
        duplicarMotivosMvrs = new Motivosmvrs();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoMvrExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MotivoMvr", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoMvrExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MotivoMvr", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    
    
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMotivosMvrs.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSMVRS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSMVRS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
 //-----------------------------------------------------------------------------   
    public List<Motivosmvrs> getListMotivosMvrs() {
        if (listMotivosMvrs == null) {
            listMotivosMvrs = administrarMotivosMvrs.mostrarMotivosMvrs();
        }
        return listMotivosMvrs;
    }

    public void setListMotivosMvrs(List<Motivosmvrs> listMotivosMvrs) {
        this.listMotivosMvrs = listMotivosMvrs;
    }

    public List<Motivosmvrs> getFiltrarMotivosMvrs() {
        return filtrarMotivosMvrs;
    }

    public void setFiltrarMotivosMvrs(List<Motivosmvrs> filtrarMotivosMvrs) {
        this.filtrarMotivosMvrs = filtrarMotivosMvrs;
    }

    public Motivosmvrs getDuplicarMotivosMvrs() {
        return duplicarMotivosMvrs;
    }

    public void setDuplicarMotivosMvrs(Motivosmvrs duplicarMotivosMvrs) {
        this.duplicarMotivosMvrs = duplicarMotivosMvrs;
    }

    public Motivosmvrs getEditarMotivosMvrs() {
        return editarMotivosMvrs;
    }

    public void setEditarMotivosMvrs(Motivosmvrs editarMotivosMvrs) {
        this.editarMotivosMvrs = editarMotivosMvrs;
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

    public Motivosmvrs getNuevoMotivoMvr() {
        return nuevoMotivoMvr;
    }

    public void setNuevoMotivoMvr(Motivosmvrs nuevoMotivoMvr) {
        this.nuevoMotivoMvr = nuevoMotivoMvr;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }
    
    
}

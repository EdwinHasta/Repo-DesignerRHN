/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.MotivosCambiosCargos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMotivosCambiosCargosInterface;
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
public class ControlMotivosCambiosCargos implements Serializable {

    @EJB
    AdministrarMotivosCambiosCargosInterface administrarMotivosCambiosCargos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<MotivosCambiosCargos> listMotivosCambiosCargos;
    private List<MotivosCambiosCargos> filtrarMotivosCambiosCargos;
    private List<MotivosCambiosCargos> crearMotivoCambioCargo;
    private List<MotivosCambiosCargos> modificarMotivoCambioCargo;
    private List<MotivosCambiosCargos> borrarMotivoCambioCargo;
    private MotivosCambiosCargos nuevoMotivoCambioCargo;
    private MotivosCambiosCargos duplicarMotivoCambioCargo;
    private MotivosCambiosCargos editarMotivoCambioCargo;
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

    /**
     * Creates a new instance of ControlMotivosCambiosCargos
     */
    public ControlMotivosCambiosCargos() {

        listMotivosCambiosCargos = null;
        crearMotivoCambioCargo = new ArrayList<MotivosCambiosCargos>();
        modificarMotivoCambioCargo = new ArrayList<MotivosCambiosCargos>();
        borrarMotivoCambioCargo = new ArrayList<MotivosCambiosCargos>();
        permitirIndex = true;
        editarMotivoCambioCargo = new MotivosCambiosCargos();
        nuevoMotivoCambioCargo = new MotivosCambiosCargos();
        duplicarMotivoCambioCargo = new MotivosCambiosCargos();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlMotiviosCambiosCargos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlMotiviosCambiosCargos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listMotivosCambiosCargos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlMotiviosCambiosCargos.asignarIndex \n");
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlMotiviosCambiosCargos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoCambioCargo");
            bandera = 0;
            filtrarMotivosCambiosCargos = null;
            tipoLista = 0;
        }

        borrarMotivoCambioCargo.clear();
        crearMotivoCambioCargo.clear();
        modificarMotivoCambioCargo.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMotivosCambiosCargos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMotivoCambioCargo");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosMotivoCambioCargo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoCambioCargo");
            bandera = 0;
            filtrarMotivosCambiosCargos = null;
            tipoLista = 0;
        }
    }

    public void modificarMotivosCambiosCargos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MOTIVOS CAMBIOS CARGOS");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVOS CAMBIOS CARGOS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearMotivoCambioCargo.contains(listMotivosCambiosCargos.get(indice))) {
                    if (listMotivosCambiosCargos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosCambiosCargos.size(); j++) {
                            if (j != indice) {
                                if (listMotivosCambiosCargos.get(indice).getCodigo() == listMotivosCambiosCargos.get(j).getCodigo()) {
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
                    if (listMotivosCambiosCargos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMotivosCambiosCargos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMotivoCambioCargo.isEmpty()) {
                            modificarMotivoCambioCargo.add(listMotivosCambiosCargos.get(indice));
                        } else if (!modificarMotivoCambioCargo.contains(listMotivosCambiosCargos.get(indice))) {
                            modificarMotivoCambioCargo.add(listMotivosCambiosCargos.get(indice));
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

                if (!crearMotivoCambioCargo.contains(filtrarMotivosCambiosCargos.get(indice))) {
                    if (filtrarMotivosCambiosCargos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosCambiosCargos.size(); j++) {
                            if (j != indice) {
                                if (filtrarMotivosCambiosCargos.get(indice).getCodigo() == listMotivosCambiosCargos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarMotivosCambiosCargos.size(); j++) {
                            if (j != indice) {
                                if (filtrarMotivosCambiosCargos.get(indice).getCodigo() == filtrarMotivosCambiosCargos.get(j).getCodigo()) {
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

                    if (filtrarMotivosCambiosCargos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMotivosCambiosCargos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMotivoCambioCargo.isEmpty()) {
                            modificarMotivoCambioCargo.add(filtrarMotivosCambiosCargos.get(indice));
                        } else if (!modificarMotivoCambioCargo.contains(filtrarMotivosCambiosCargos.get(indice))) {
                            modificarMotivoCambioCargo.add(filtrarMotivosCambiosCargos.get(indice));
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
            context.update("form:datosMotivoCambioCargo");
            context.update("form:ACEPTAR");
        }

    }

    public void borrarMotivosCambiosCargos() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarMotivosCambiosCargos");
                if (!modificarMotivoCambioCargo.isEmpty() && modificarMotivoCambioCargo.contains(listMotivosCambiosCargos.get(index))) {
                    int modIndex = modificarMotivoCambioCargo.indexOf(listMotivosCambiosCargos.get(index));
                    modificarMotivoCambioCargo.remove(modIndex);
                    borrarMotivoCambioCargo.add(listMotivosCambiosCargos.get(index));
                } else if (!crearMotivoCambioCargo.isEmpty() && crearMotivoCambioCargo.contains(listMotivosCambiosCargos.get(index))) {
                    int crearIndex = crearMotivoCambioCargo.indexOf(listMotivosCambiosCargos.get(index));
                    crearMotivoCambioCargo.remove(crearIndex);
                } else {
                    borrarMotivoCambioCargo.add(listMotivosCambiosCargos.get(index));
                }
                listMotivosCambiosCargos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarMotivosCambiosCargos ");
                if (!modificarMotivoCambioCargo.isEmpty() && modificarMotivoCambioCargo.contains(filtrarMotivosCambiosCargos.get(index))) {
                    int modIndex = modificarMotivoCambioCargo.indexOf(filtrarMotivosCambiosCargos.get(index));
                    modificarMotivoCambioCargo.remove(modIndex);
                    borrarMotivoCambioCargo.add(filtrarMotivosCambiosCargos.get(index));
                } else if (!crearMotivoCambioCargo.isEmpty() && crearMotivoCambioCargo.contains(filtrarMotivosCambiosCargos.get(index))) {
                    int crearIndex = crearMotivoCambioCargo.indexOf(filtrarMotivosCambiosCargos.get(index));
                    crearMotivoCambioCargo.remove(crearIndex);
                } else {
                    borrarMotivoCambioCargo.add(filtrarMotivosCambiosCargos.get(index));
                }
                int VCIndex = listMotivosCambiosCargos.indexOf(filtrarMotivosCambiosCargos.get(index));
                listMotivosCambiosCargos.remove(VCIndex);
                filtrarMotivosCambiosCargos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMotivoCambioCargo");
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
        BigInteger borradoVC;

        try {
            if (tipoLista == 0) {
                borradoVC = administrarMotivosCambiosCargos.contarVigenciasCargosMotivoCambioCargo(listMotivosCambiosCargos.get(index).getSecuencia());
            } else {
                borradoVC = administrarMotivosCambiosCargos.contarVigenciasCargosMotivoCambioCargo(filtrarMotivosCambiosCargos.get(index).getSecuencia());
            }
            if (borradoVC.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarMotivosCambiosCargos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                borradoVC = new BigInteger("-1");
            }

        } catch (Exception e) {
            System.err.println("ERROR ControlTiposEntidades verificarBorrado ERROR " + e);
        }
    }

    public void guardarMotivosCambiosCargos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarMotivoCambioCargo.isEmpty()) {
                administrarMotivosCambiosCargos.borrarMotivosCambiosCargos(borrarMotivoCambioCargo);

                //mostrarBorrados
                registrosBorrados = borrarMotivoCambioCargo.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivoCambioCargo.clear();
            }
            if (!crearMotivoCambioCargo.isEmpty()) {

                administrarMotivosCambiosCargos.crearMotivosCambiosCargos(crearMotivoCambioCargo);
                crearMotivoCambioCargo.clear();
            }
            if (!modificarMotivoCambioCargo.isEmpty()) {
                administrarMotivosCambiosCargos.modificarMotivosCambiosCargos(modificarMotivoCambioCargo);
                modificarMotivoCambioCargo.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosCambiosCargos = null;
            context.update("form:datosMotivoCambioCargo");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMotivoCambioCargo = listMotivosCambiosCargos.get(index);
            }
            if (tipoLista == 1) {
                editarMotivoCambioCargo = filtrarMotivosCambiosCargos.get(index);
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

    public void agregarNuevoMotivoCambioCargo() {
        System.out.println("Agregar Motivo Cambio Cargo");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoCambioCargo.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoCambioCargo.getCodigo());

            for (int x = 0; x < listMotivosCambiosCargos.size(); x++) {
                if (listMotivosCambiosCargos.get(x).getCodigo() == nuevoMotivoCambioCargo.getCodigo()) {
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
        if (nuevoMotivoCambioCargo.getNombre() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoCambioCargo");
                bandera = 0;
                filtrarMotivosCambiosCargos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoCambioCargo.setSecuencia(l);

            crearMotivoCambioCargo.add(nuevoMotivoCambioCargo);

            listMotivosCambiosCargos.add(nuevoMotivoCambioCargo);
            nuevoMotivoCambioCargo = new MotivosCambiosCargos();

            context.update("form:datosMotivoCambioCargo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            System.out.println("Despues de la bandera guardado");

            context.execute("nuevoRegistroMotivoCambiosCargos.hide()");
            index = -1;
            secRegistro = null;
            System.out.println("Despues de nuevoRegistroMotivoCambiosCargos");

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMotivoCambioCargo() {
        System.out.println("limpiarNuevoMotivoCambioCargo");
        nuevoMotivoCambioCargo = new MotivosCambiosCargos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarMotivosCambiosCargos() {
        System.out.println("duplicarMotivosCambiosCargos");
        if (index >= 0) {
            duplicarMotivoCambioCargo = new MotivosCambiosCargos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoCambioCargo.setSecuencia(l);
                duplicarMotivoCambioCargo.setCodigo(listMotivosCambiosCargos.get(index).getCodigo());
                duplicarMotivoCambioCargo.setNombre(listMotivosCambiosCargos.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarMotivoCambioCargo.setSecuencia(l);
                duplicarMotivoCambioCargo.setCodigo(filtrarMotivosCambiosCargos.get(index).getCodigo());
                duplicarMotivoCambioCargo.setNombre(filtrarMotivosCambiosCargos.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivosCambiosCargos");
            context.execute("duplicarRegistroMotivosCambiosCargos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLMOTIVOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarMotivoCambioCargo.getCodigo());
        System.err.println("ConfirmarDuplicar nombre " + duplicarMotivoCambioCargo.getNombre());

        if (duplicarMotivoCambioCargo.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosCambiosCargos.size(); x++) {
                if (listMotivosCambiosCargos.get(x).getCodigo() == duplicarMotivoCambioCargo.getCodigo()) {
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
        if (duplicarMotivoCambioCargo.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoCambioCargo.getSecuencia() + "  " + duplicarMotivoCambioCargo.getCodigo());
            if (crearMotivoCambioCargo.contains(duplicarMotivoCambioCargo)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosCambiosCargos.add(duplicarMotivoCambioCargo);
            crearMotivoCambioCargo.add(duplicarMotivoCambioCargo);
            context.update("form:datosMotivoCambioCargo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioCargo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoCambioCargo");
                bandera = 0;
                filtrarMotivosCambiosCargos = null;
                tipoLista = 0;
            }
            duplicarMotivoCambioCargo = new MotivosCambiosCargos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMotivosCambiosCargos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarMotivosContratos() {
        duplicarMotivoCambioCargo = new MotivosCambiosCargos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoCambioCargoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MotivosCambiosCargosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoCambioCargoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MotivosCambiosCargosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMotivosCambiosCargos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSCAMBIOSCARGOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSCAMBIOSCARGOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------------------------------------------------------------------------
    public List<MotivosCambiosCargos> getListMotivosCambiosCargos() {
        if (listMotivosCambiosCargos == null) {
            listMotivosCambiosCargos = administrarMotivosCambiosCargos.consultarMotivosCambiosCargos();
        }
        return listMotivosCambiosCargos;
    }

    public void setListMotivosCambiosCargos(List<MotivosCambiosCargos> listMotivosCambiosCargos) {
        this.listMotivosCambiosCargos = listMotivosCambiosCargos;
    }

    public List<MotivosCambiosCargos> getFiltrarMotivosCambiosCargos() {
        return filtrarMotivosCambiosCargos;
    }

    public void setFiltrarMotivosCambiosCargos(List<MotivosCambiosCargos> filtrarMotivosCambiosCargos) {
        this.filtrarMotivosCambiosCargos = filtrarMotivosCambiosCargos;
    }

    public MotivosCambiosCargos getNuevoMotivoCambioCargo() {
        return nuevoMotivoCambioCargo;
    }

    public void setNuevoMotivoCambioCargo(MotivosCambiosCargos nuevoMotivoCambioCargo) {
        this.nuevoMotivoCambioCargo = nuevoMotivoCambioCargo;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public MotivosCambiosCargos getEditarMotivoCambioCargo() {
        return editarMotivoCambioCargo;
    }

    public void setEditarMotivoCambioCargo(MotivosCambiosCargos editarMotivoCambioCargo) {
        this.editarMotivoCambioCargo = editarMotivoCambioCargo;
    }

    public MotivosCambiosCargos getDuplicarMotivoCambioCargo() {
        return duplicarMotivoCambioCargo;
    }

    public void setDuplicarMotivoCambioCargo(MotivosCambiosCargos duplicarMotivoCambioCargo) {
        this.duplicarMotivoCambioCargo = duplicarMotivoCambioCargo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

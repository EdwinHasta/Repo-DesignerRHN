/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import InterfaceAdministrar.AdministrarMotivosCambiosSueldosInterface;
import Entidades.MotivosCambiosSueldos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
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
public class ControlMotivosCambiosSueldos implements Serializable {

    @EJB
    AdministrarMotivosCambiosSueldosInterface administrarMotivosCambiosSueldos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
   
    private List<MotivosCambiosSueldos> listMotivosCambiosSueldos;
    private List<MotivosCambiosSueldos> filtrarMotivosCambiosSueldos;
    private List<MotivosCambiosSueldos> borrarMotivosCambiosSueldos;
    private List<MotivosCambiosSueldos> modificarrMotivosCambiosSueldos;
    private List<MotivosCambiosSueldos> crearMotivosCambiosSueldos;
    private MotivosCambiosSueldos editarMotivoCambioSueldo;
    private MotivosCambiosSueldos nuevoMotivoCambioSueldo;
    private MotivosCambiosSueldos duplicarMotivoCambioSueldo;
    private String mensajeValidacion;
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, estadoSueldoPromedio;
    //borrado
    private Long borradoVS;
    private int registrosBorrados;
    /*
     prueba
     */
    private boolean prueba;

    public boolean isPrueba() {
        return prueba;
    }

    public void setPrueba(Boolean prueba) {
        this.prueba = prueba;
    }

    //----------------------
    public ControlMotivosCambiosSueldos() {
        listMotivosCambiosSueldos = null;
        crearMotivosCambiosSueldos = new ArrayList<MotivosCambiosSueldos>();
        modificarrMotivosCambiosSueldos = new ArrayList<MotivosCambiosSueldos>();
        borrarMotivosCambiosSueldos = new ArrayList<MotivosCambiosSueldos>();
        nuevoMotivoCambioSueldo = new MotivosCambiosSueldos();
        nuevoMotivoCambioSueldo.getEstadoSueldoPromedio();
        duplicarMotivoCambioSueldo = new MotivosCambiosSueldos();
        permitirIndex = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlMotivosCambiosSueldos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlMotivosCambiosSueldos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listMotivosCambiosSueldos.get(index).getSecuencia();
        }
        System.err.println("Indice: " + index + " Celda: " + cualCelda);
    }

    /**
     *
     * @param indice
     * @param LND
     * @param dig muestra el dialogo respectivo
     */
    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlMotivosCambiosSueldos.asignarIndex \n");
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
            System.out.println("ERROR ControlMotivosCambiosSueldos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        System.err.println("cancelarModificacion");
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estadoSueldoPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:estadoSueldoPromedio");
            estadoSueldoPromedio.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoCambioSueldo");
            bandera = 0;
            filtrarMotivosCambiosSueldos = null;
            tipoLista = 0;
        }

        borrarMotivosCambiosSueldos.clear();
        crearMotivosCambiosSueldos.clear();
        modificarrMotivosCambiosSueldos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMotivosCambiosSueldos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMotivoCambioSueldo");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:codigo");
            codigo.setFilterStyle("width: 360px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:descripcion");
            descripcion.setFilterStyle("width: 390px");
            estadoSueldoPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:estadoSueldoPromedio");
            estadoSueldoPromedio.setFilterStyle("width: 200px");
            RequestContext.getCurrentInstance().update("form:datosMotivoCambioSueldo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estadoSueldoPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:estadoSueldoPromedio");
            estadoSueldoPromedio.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoCambioSueldo");
            bandera = 0;
            filtrarMotivosCambiosSueldos = null;
            tipoLista = 0;
        }
    }

    public void modificarPromedioSuelos(int indice, boolean cambio) {
        System.out.println("modificarPromediosSuelos cambio = " + cambio);
        System.out.println("modificarPromediosSuelos indice = " + indice);
        System.err.println("cambio = " + cambio);

        listMotivosCambiosSueldos.get(indice).setEstadoSueldoPromedio(cambio);
        if (listMotivosCambiosSueldos.get(indice).getEstadoSueldoPromedio() == true) {
            listMotivosCambiosSueldos.get(indice).setSueldopromedio("S");
        } else {
            listMotivosCambiosSueldos.get(indice).setSueldopromedio("N");
        }


        modificarMotivosCambiosSueldos(indice, "N", "N");
    }

    public void modificarMotivosCambiosSueldos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MOTIVOS CAMBIOS SUELDOS");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVOS CAMBIOS SUELDOS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearMotivosCambiosSueldos.contains(listMotivosCambiosSueldos.get(indice))) {
                    if (listMotivosCambiosSueldos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosCambiosSueldos.size(); j++) {
                            if (j != indice) {
                                if (listMotivosCambiosSueldos.get(indice).getCodigo() == listMotivosCambiosSueldos.get(j).getCodigo()) {
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
                    if (listMotivosCambiosSueldos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMotivosCambiosSueldos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }


                    if (banderita == true) {
                        if (modificarrMotivosCambiosSueldos.isEmpty()) {
                            modificarrMotivosCambiosSueldos.add(listMotivosCambiosSueldos.get(indice));
                        } else if (!modificarrMotivosCambiosSueldos.contains(listMotivosCambiosSueldos.get(indice))) {
                            modificarrMotivosCambiosSueldos.add(listMotivosCambiosSueldos.get(indice));
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


                if (!crearMotivosCambiosSueldos.contains(filtrarMotivosCambiosSueldos.get(indice))) {
                    if (filtrarMotivosCambiosSueldos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosCambiosSueldos.size(); j++) {
                            if (j != indice) {
                                if (listMotivosCambiosSueldos.get(indice).getCodigo() == listMotivosCambiosSueldos.get(j).getCodigo()) {
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


                    if (filtrarMotivosCambiosSueldos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMotivosCambiosSueldos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (filtrarMotivosCambiosSueldos.get(indice).getEstadoSueldoPromedio() == true) {
                        listMotivosCambiosSueldos.get(indice).setSueldopromedio("S");
                    }
                    if (filtrarMotivosCambiosSueldos.get(indice).getEstadoSueldoPromedio() == false) {
                        listMotivosCambiosSueldos.get(indice).setSueldopromedio("N");
                    }

                    if (banderita == true) {
                        if (modificarrMotivosCambiosSueldos.isEmpty()) {
                            modificarrMotivosCambiosSueldos.add(filtrarMotivosCambiosSueldos.get(indice));
                        } else if (!modificarrMotivosCambiosSueldos.contains(filtrarMotivosCambiosSueldos.get(indice))) {
                            modificarrMotivosCambiosSueldos.add(filtrarMotivosCambiosSueldos.get(indice));
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
            context.update("form:datosMotivoCambioSueldo");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        //try {
        borradoVS = administrarMotivosCambiosSueldos.verificarBorradoVS(listMotivosCambiosSueldos.get(index).getSecuencia());
        if (borradoVS.intValue() == 0) {
            System.out.println("Borrado==0");
            borrarMotivosCambiosSueldos();
        }
        if (borradoVS.intValue() != 0) {
            System.out.println("Borrado>0");

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:validacionBorrar");
            context.execute("validacionBorrar.show()");
            index = -1;
            borradoVS = new Long(-1);
        }

        //  } catch (Exception e) {
        //    System.err.println("ERROR CONTROLMOTIVOOSCAMBIOSSUELDOS verificarBorrado ERROR " + e);
        // }
    }

    public void borrarMotivosCambiosSueldos() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarMotivosCambiosSueldos");
                if (!modificarrMotivosCambiosSueldos.isEmpty() && modificarrMotivosCambiosSueldos.contains(listMotivosCambiosSueldos.get(index))) {
                    int modIndex = modificarrMotivosCambiosSueldos.indexOf(listMotivosCambiosSueldos.get(index));
                    modificarrMotivosCambiosSueldos.remove(modIndex);
                    borrarMotivosCambiosSueldos.add(listMotivosCambiosSueldos.get(index));
                } else if (!crearMotivosCambiosSueldos.isEmpty() && crearMotivosCambiosSueldos.contains(listMotivosCambiosSueldos.get(index))) {
                    int crearIndex = crearMotivosCambiosSueldos.indexOf(listMotivosCambiosSueldos.get(index));
                    crearMotivosCambiosSueldos.remove(crearIndex);
                } else {
                    borrarMotivosCambiosSueldos.add(listMotivosCambiosSueldos.get(index));
                }
                listMotivosCambiosSueldos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarMotivosCambiosCargos ");
                if (!modificarrMotivosCambiosSueldos.isEmpty() && modificarrMotivosCambiosSueldos.contains(filtrarMotivosCambiosSueldos.get(index))) {
                    int modIndex = modificarrMotivosCambiosSueldos.indexOf(filtrarMotivosCambiosSueldos.get(index));
                    modificarrMotivosCambiosSueldos.remove(modIndex);
                    borrarMotivosCambiosSueldos.add(filtrarMotivosCambiosSueldos.get(index));
                } else if (!crearMotivosCambiosSueldos.isEmpty() && crearMotivosCambiosSueldos.contains(filtrarMotivosCambiosSueldos.get(index))) {
                    int crearIndex = crearMotivosCambiosSueldos.indexOf(filtrarMotivosCambiosSueldos.get(index));
                    crearMotivosCambiosSueldos.remove(crearIndex);
                } else {
                    borrarMotivosCambiosSueldos.add(filtrarMotivosCambiosSueldos.get(index));
                }
                int VCIndex = listMotivosCambiosSueldos.indexOf(filtrarMotivosCambiosSueldos.get(index));
                listMotivosCambiosSueldos.remove(VCIndex);
                filtrarMotivosCambiosSueldos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMotivoCambioSueldo");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMotivoCambioSueldo = listMotivosCambiosSueldos.get(index);
            }
            if (tipoLista == 1) {
                editarMotivoCambioSueldo = filtrarMotivosCambiosSueldos.get(index);
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

    public void agregarNuevoMotivoCambioSueldo() {
        System.out.println("Agregar Motivo Cambio Cargo");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoCambioSueldo.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoCambioSueldo.getCodigo());

            for (int x = 0; x < listMotivosCambiosSueldos.size(); x++) {
                if (listMotivosCambiosSueldos.get(x).getCodigo() == nuevoMotivoCambioSueldo.getCodigo()) {
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
        if (nuevoMotivoCambioSueldo.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Una  Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoMotivoCambioSueldo.getEstadoSueldoPromedio() == null) {
            nuevoMotivoCambioSueldo.setEstadoSueldoPromedio(false);
            nuevoMotivoCambioSueldo.setSueldopromedio("N");
        }
        if (nuevoMotivoCambioSueldo.getEstadoSueldoPromedio() == true) {
            System.err.println("Sueldo Promedio S");
            nuevoMotivoCambioSueldo.setSueldopromedio("S");
        }
        if (nuevoMotivoCambioSueldo.getEstadoSueldoPromedio() == false) {
            System.err.println("Sueldo Promedio N");
            nuevoMotivoCambioSueldo.setSueldopromedio("N");
        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");


                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                estadoSueldoPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:estadoSueldoPromedio");
                estadoSueldoPromedio.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoCambioSueldo");
                bandera = 0;
                filtrarMotivosCambiosSueldos = null;

                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            //AGREGAR REGISTRO A LA LISTA  SUELDOS .
            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoCambioSueldo.setSecuencia(l);

            crearMotivosCambiosSueldos.add(nuevoMotivoCambioSueldo);

            listMotivosCambiosSueldos.add(nuevoMotivoCambioSueldo);
            nuevoMotivoCambioSueldo = new MotivosCambiosSueldos();
            nuevoMotivoCambioSueldo.getEstadoSueldoPromedio();


            context.update("form:datosMotivoCambioSueldo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            System.out.println("Despues de la bandera guardado");

            context.execute("nuevoRegistroMotivoCambiosSueldos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMotivoCambioSueldo() {
        System.out.println("limpiarnuevoMotivoCambioSueldo");
        nuevoMotivoCambioSueldo = new MotivosCambiosSueldos();
        nuevoMotivoCambioSueldo.getEstadoSueldoPromedio();

        secRegistro = null;
        index = -1;

    }

    public void duplicarMotivosCambiosSueldos() {
        System.out.println("duplicarMotivosCambiosSueldos");
        if (index >= 0) {
            duplicarMotivoCambioSueldo = new MotivosCambiosSueldos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoCambioSueldo.setSecuencia(l);
                duplicarMotivoCambioSueldo.setCodigo(listMotivosCambiosSueldos.get(index).getCodigo());
                duplicarMotivoCambioSueldo.setNombre(listMotivosCambiosSueldos.get(index).getNombre());
                duplicarMotivoCambioSueldo.setSueldopromedio(listMotivosCambiosSueldos.get(index).getSueldopromedio());
                duplicarMotivoCambioSueldo.setEstadoSueldoPromedio(listMotivosCambiosSueldos.get(index).getEstadoSueldoPromedio());
            }
            if (tipoLista == 1) {
                duplicarMotivoCambioSueldo.setSecuencia(l);
                duplicarMotivoCambioSueldo.setCodigo(filtrarMotivosCambiosSueldos.get(index).getCodigo());
                duplicarMotivoCambioSueldo.setNombre(filtrarMotivosCambiosSueldos.get(index).getNombre());
                duplicarMotivoCambioSueldo.setSueldopromedio(filtrarMotivosCambiosSueldos.get(index).getSueldopromedio());
                duplicarMotivoCambioSueldo.setEstadoSueldoPromedio(filtrarMotivosCambiosSueldos.get(index).getEstadoSueldoPromedio());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivosCambiosSueldos");
            context.execute("duplicarRegistroMotivosCambiosSueldos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLMOTIVOSCAMBIOSSUELDOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarMotivoCambioSueldo.getCodigo());
        System.err.println("ConfirmarDuplicar nombre " + duplicarMotivoCambioSueldo.getNombre());
        System.err.println("ConfirmarDuplicar Sueldo Promedio " + duplicarMotivoCambioSueldo.getSueldopromedio());

        if (duplicarMotivoCambioSueldo.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosCambiosSueldos.size(); x++) {
                if (listMotivosCambiosSueldos.get(x).getCodigo() == duplicarMotivoCambioSueldo.getCodigo()) {
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
        if (duplicarMotivoCambioSueldo.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarMotivoCambioSueldo.getEstadoSueldoPromedio() == true) {
            System.err.println("Sueldo Promedio S");
            duplicarMotivoCambioSueldo.setSueldopromedio("S");
        }
        if (duplicarMotivoCambioSueldo.getEstadoSueldoPromedio() == false) {
            System.err.println("Sueldo Promedio N");
            duplicarMotivoCambioSueldo.setSueldopromedio("N");
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoCambioSueldo.getSecuencia() + "  " + duplicarMotivoCambioSueldo.getCodigo());
            if (crearMotivosCambiosSueldos.contains(duplicarMotivoCambioSueldo)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosCambiosSueldos.add(duplicarMotivoCambioSueldo);
            crearMotivosCambiosSueldos.add(duplicarMotivoCambioSueldo);
            context.update("form:datosMotivoCambioSueldo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                estadoSueldoPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoCambioSueldo:estadoSueldoPromedio");
                estadoSueldoPromedio.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoCambioSueldo");
                bandera = 0;
                filtrarMotivosCambiosSueldos = null;
                tipoLista = 0;
            }
            duplicarMotivoCambioSueldo = new MotivosCambiosSueldos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMotivosCambiosSueldos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarMotivosCambiosSueldos() {
        duplicarMotivoCambioSueldo = new MotivosCambiosSueldos();
    }

    public void guardarMotivosCambiosSueldos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarMotivosCambiosSueldos.isEmpty()) {
                for (int i = 0; i < borrarMotivosCambiosSueldos.size(); i++) {
                    System.out.println("Borrando...");
                    administrarMotivosCambiosSueldos.borrarMotivosCambiosSueldos(borrarMotivosCambiosSueldos.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarMotivosCambiosSueldos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivosCambiosSueldos.clear();
            }
            if (!crearMotivosCambiosSueldos.isEmpty()) {
                for (int i = 0; i < crearMotivosCambiosSueldos.size(); i++) {

                    System.out.println("Creando...");
                    administrarMotivosCambiosSueldos.crearMotivosCambiosSueldos(crearMotivosCambiosSueldos.get(i));

                }
                crearMotivosCambiosSueldos.clear();
            }
            if (!modificarrMotivosCambiosSueldos.isEmpty()) {
                administrarMotivosCambiosSueldos.modificarMotivosCambiosSueldos(modificarrMotivosCambiosSueldos);
                modificarrMotivosCambiosSueldos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosCambiosSueldos = null;
            context.update("form:datosMotivoCambioSueldo");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:aceptar");

    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoSueldoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MotivosSueldoPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoSueldoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MotivosSueldoXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    
    

            
              public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMotivosCambiosSueldos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSCAMBIOSSUELDOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSCAMBIOSSUELDOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
//---------//----------------//------------//----------------//-----------//--------------------------//-----
    public List<MotivosCambiosSueldos> getListMotivosCambiosSueldos() {
        if (listMotivosCambiosSueldos == null) {
            listMotivosCambiosSueldos = administrarMotivosCambiosSueldos.mostrarMotivosCambiosSueldos();
        }
        return listMotivosCambiosSueldos;
    }

    public void setListMotivosCambiosSueldos(List<MotivosCambiosSueldos> listMotivosCambiosSueldos) {
        this.listMotivosCambiosSueldos = listMotivosCambiosSueldos;
    }

    public List<MotivosCambiosSueldos> getFiltrarMotivosCambiosSueldos() {
        return filtrarMotivosCambiosSueldos;
    }

    public void setFiltrarMotivosCambiosSueldos(List<MotivosCambiosSueldos> filtrarMotivosCambiosSueldos) {
        this.filtrarMotivosCambiosSueldos = filtrarMotivosCambiosSueldos;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public MotivosCambiosSueldos getEditarMotivoCambioSueldo() {
        return editarMotivoCambioSueldo;
    }

    public void setEditarMotivoCambioSueldo(MotivosCambiosSueldos editarMotivoCambioSueldo) {
        this.editarMotivoCambioSueldo = editarMotivoCambioSueldo;
    }

    public MotivosCambiosSueldos getNuevoMotivoCambioSueldo() {
        return nuevoMotivoCambioSueldo;
    }

    public void setNuevoMotivoCambioSueldo(MotivosCambiosSueldos nuevoMotivoCambioSueldo) {
        this.nuevoMotivoCambioSueldo = nuevoMotivoCambioSueldo;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public MotivosCambiosSueldos getDuplicarMotivoCambioSueldo() {
        return duplicarMotivoCambioSueldo;
    }

    public void setDuplicarMotivoCambioSueldo(MotivosCambiosSueldos duplicarMotivoCambioSueldo) {
        this.duplicarMotivoCambioSueldo = duplicarMotivoCambioSueldo;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }
    
}

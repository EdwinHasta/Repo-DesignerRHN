/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import Entidades.TiposFamiliares;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarHvReferenciasInterface;
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
public class ControlHvReferencias1 implements Serializable {

    @EJB
    AdministrarHvReferenciasInterface administrarHvReferencias1;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<HvReferencias> listHvReferencias1;
    private List<HvReferencias> filtrarHvReferencias1;
    private List<HvReferencias> crearHvReferencias1;
    private List<HvReferencias> modificarHvReferencias1;
    private List<HvReferencias> borrarHvReferencias1;
    private HvReferencias nuevoHvReferencia1;
    private HvReferencias duplicarHvReferencia1;
    private HvReferencias editarHvReferencia1;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column cargo, nombre, numTelefono, numCelular, parentesco;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger secuenciaEmpleado;
//Empleado
    private Empleados empleadoSeleccionado;
//otros
    private HVHojasDeVida hvHojasDeVida;
    private List<HVHojasDeVida> listHvHojasDeVida;
    //autocompletar
    private String tiposFamiliares;
    private List<TiposFamiliares> listaTiposFamiliares;
    private List<TiposFamiliares> filtradoTiposFamiliares;
    private TiposFamiliares tipoFamiliarSeleccionado;
    private String nuevoParentesco;

    public ControlHvReferencias1() {
        listHvReferencias1 = null;
        crearHvReferencias1 = new ArrayList<HvReferencias>();
        modificarHvReferencias1 = new ArrayList<HvReferencias>();
        borrarHvReferencias1 = new ArrayList<HvReferencias>();
        permitirIndex = true;
        guardado = true;
        editarHvReferencia1 = new HvReferencias();
        nuevoHvReferencia1 = new HvReferencias();
        duplicarHvReferencia1 = new HvReferencias();
        empleadoSeleccionado = null;
        secuenciaEmpleado = null;
        listHvHojasDeVida = new ArrayList<HVHojasDeVida>();
        listaTiposFamiliares = null;
        filtradoTiposFamiliares = null;
        tipoLista = 0;

    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarHvReferencias1.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        System.out.println("ENTRE A RECIBIR EMPLEADO EN CONTROL HVREFERENCIAS1");
        if (sec == null) {
            System.out.println("ERROR EN RECIVIR LA SECUENCIA DEL EMPLEADO EN ControlHvReferencias");
            //  sec = BigInteger.valueOf(10661039);
        }
        secuenciaEmpleado = sec;
        listHvReferencias1 = null;
        empleadoSeleccionado = null;
        getEmpleadoSeleccionado();
        getListHvReferencias1();
    }

    public void mostrarNuevo() {
        System.err.println("nuevo Tipo Entrevista " + nuevoHvReferencia1.getTipo());
    }

    /* public void mostrarInfo(int indice, int celda) {
     if (permitirIndex == true) {
     index = indice;
     cualCelda = celda;
     secRegistro = listHvReferencias.get(index).getSecuencia();
     System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listHvReferencias.get(indice).getNombrepersona());
     System.out.println("Tipo Entrevista " + listHvReferencias.get(indice).getTipo());
     if (!crearHvReferencias.contains(listHvReferencias.get(indice))) {

     if (modificarHvReferencias.isEmpty()) {
     modificarHvReferencias.add(listHvReferencias.get(indice));
     } else if (!modificarHvReferencias.contains(listHvReferencias.get(indice))) {
     modificarHvReferencias.add(listHvReferencias.get(indice));
     }
     if (guardado == true) {
     guardado = false;
     //RequestContext.getCurrentInstance().update("form:aceptar");
     }
     }
     }
     System.out.println("Indice: " + index + " Celda: " + cualCelda);

     }
     */
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlHvReferencias.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlHvReferencias eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listHvReferencias1.get(index).getSecuencia();

            if (cualCelda == 4) {
                if (tipoLista == 0) {
                    tiposFamiliares = listHvReferencias1.get(index).getParentesco().getTipo();
                } else {
                    tiposFamiliares = filtrarHvReferencias1.get(index).getParentesco().getTipo();
                }
                System.out.println("Cambiar Indice Tipo de Familia : " + tiposFamiliares);
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlHvReferencias.asignarIndex \n");
            RequestContext context = RequestContext.getCurrentInstance();

            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 4) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                dig = -1;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlHvReferencias.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();

            if (cualCelda == 4) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                tipoActualizacion = 0;
            }

        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("display: none; visibility: hidden;");
            numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias1 = null;
            tipoLista = 0;
        }

        borrarHvReferencias1.clear();
        crearHvReferencias1.clear();
        modificarHvReferencias1.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listHvReferencias1 = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHvReferencia");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("width: 200px");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("width: 145px");
            numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("width: 130px");
            numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("width: 130px");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:parentesco");
            parentesco.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("display: none; visibility: hidden;");
            numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias1 = null;
            tipoLista = 0;
        }
    }

    /*   public void modificandoHvReferencia(int indice, String confirmarCambio, String valorConfirmar) {
     System.err.println("ENTRE A MODIFICAR HV Referencia");
     index = indice;

     int contador = 0;
     boolean banderita = false;
     Short a;
     a = null;
     RequestContext context = RequestContext.getCurrentInstance();
     System.err.println("TIPO LISTA = " + tipoLista);
     if (confirmarCambio.equalsIgnoreCase("N")) {
     System.err.println("ENTRE A MODIFICAR HvReferencia, CONFIRMAR CAMBIO ES N");
     if (tipoLista == 0) {
     if (!crearHvReferenciasFamiliares.contains(listHvReferencias1.get(indice))) {

     if (listHvReferencias1.get(indice).getNombrepersona().isEmpty()) {
     mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
     banderita = false;
     } else if (listHvReferencias1.get(indice).getNombrepersona().equals(" ")) {
     mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
     banderita = false;
     } else {
     banderita = true;
     }

     if (banderita == true) {
     if (modificarHvReferenciasFamiliares.isEmpty()) {
     modificarHvReferenciasFamiliares.add(listHvReferencias1.get(indice));
     } else if (!modificarHvReferenciasFamiliares.contains(listHvReferencias1.get(indice))) {
     modificarHvReferenciasFamiliares.add(listHvReferencias1.get(indice));
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

     if (!crearHvReferenciasFamiliares.contains(filtrarHvReferencias1.get(indice))) {
     if (filtrarHvReferencias1.get(indice).getNombrepersona().isEmpty()) {
     mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
     banderita = false;
     }
     if (filtrarHvReferencias1.get(indice).getNombrepersona().equals(" ")) {
     mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
     banderita = false;
     }

     if (banderita == true) {
     if (modificarHvReferenciasFamiliares.isEmpty()) {
     modificarHvReferenciasFamiliares.add(filtrarHvReferencias1.get(indice));
     } else if (!modificarHvReferenciasFamiliares.contains(filtrarHvReferencias1.get(indice))) {
     modificarHvReferenciasFamiliares.add(filtrarHvReferencias1.get(indice));
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
     context.update("form:datosHvReferencia");
     }

     }
     */
    /**
     *
     * @param indice donde se encuentra posicionado
     * @param confirmarCambio nombre de la columna
     * @param valorConfirmar valor ingresado
     */
    public void modificarHvrReferencia1(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        boolean banderita = false;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR HvReferencia, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearHvReferencias1.contains(listHvReferencias1.get(indice))) {

                    if (listHvReferencias1.get(indice).getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listHvReferencias1.get(indice).getNombrepersona().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarHvReferencias1.isEmpty()) {
                            modificarHvReferencias1.add(listHvReferencias1.get(indice));
                        } else if (!modificarHvReferencias1.contains(listHvReferencias1.get(indice))) {
                            modificarHvReferencias1.add(listHvReferencias1.get(indice));
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

                if (!crearHvReferencias1.contains(filtrarHvReferencias1.get(indice))) {
                    if (filtrarHvReferencias1.get(indice).getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarHvReferencias1.get(indice).getNombrepersona().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarHvReferencias1.isEmpty()) {
                            modificarHvReferencias1.add(filtrarHvReferencias1.get(indice));
                        } else if (!modificarHvReferencias1.contains(filtrarHvReferencias1.get(indice))) {
                            modificarHvReferencias1.add(filtrarHvReferencias1.get(indice));
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
            context.update("form:datosHvReferencia");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOSFAMILIARES")) {
            if (!listHvReferencias1.get(indice).getParentesco().getTipo().equals("")) {
                if (tipoLista == 0) {
                    listHvReferencias1.get(indice).getParentesco().setTipo(tiposFamiliares);

                } else {
                    filtrarHvReferencias1.get(indice).getParentesco().setTipo(tiposFamiliares);
                }

                for (int i = 0; i < listaTiposFamiliares.size(); i++) {
                    if (listaTiposFamiliares.get(i).getTipo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listHvReferencias1.get(indice).setParentesco(listaTiposFamiliares.get(indiceUnicoElemento));
                    } else {
                        filtrarHvReferencias1.get(indice).setParentesco(listaTiposFamiliares.get(indiceUnicoElemento));
                    }
                    listaTiposFamiliares.clear();
                    listaTiposFamiliares = null;
                    getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                System.out.println("PUSE UN VACIO");
                listHvReferencias1.get(indice).getParentesco().setTipo(tiposFamiliares);
                listHvReferencias1.get(indice).setParentesco(new TiposFamiliares());
                coincidencias = 1;
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearHvReferencias1.contains(listHvReferencias1.get(indice))) {

                        if (modificarHvReferencias1.isEmpty()) {
                            modificarHvReferencias1.add(listHvReferencias1.get(indice));
                        } else if (!modificarHvReferencias1.contains(listHvReferencias1.get(indice))) {
                            modificarHvReferencias1.add(listHvReferencias1.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:datosHvReferencia");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearHvReferencias1.contains(filtrarHvReferencias1.get(indice))) {

                        if (modificarHvReferencias1.isEmpty()) {
                            modificarHvReferencias1.add(filtrarHvReferencias1.get(indice));
                        } else if (!modificarHvReferencias1.contains(filtrarHvReferencias1.get(indice))) {
                            modificarHvReferencias1.add(filtrarHvReferencias1.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosHvReferencia");

        }
        context.update("form:datosHvReferencia");
        context.update("form:ACEPTAR");
    }

    public void actualizarTipoFamiliar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listHvReferencias1.get(index).setParentesco(tipoFamiliarSeleccionado);

                if (!crearHvReferencias1.contains(listHvReferencias1.get(index))) {
                    if (modificarHvReferencias1.isEmpty()) {
                        modificarHvReferencias1.add(listHvReferencias1.get(index));
                    } else if (!modificarHvReferencias1.contains(listHvReferencias1.get(index))) {
                        modificarHvReferencias1.add(listHvReferencias1.get(index));
                    }
                }
            } else {
                filtrarHvReferencias1.get(index).setParentesco(tipoFamiliarSeleccionado);

                if (!crearHvReferencias1.contains(filtrarHvReferencias1.get(index))) {
                    if (modificarHvReferencias1.isEmpty()) {
                        modificarHvReferencias1.add(filtrarHvReferencias1.get(index));
                    } else if (!modificarHvReferencias1.contains(filtrarHvReferencias1.get(index))) {
                        modificarHvReferencias1.add(filtrarHvReferencias1.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            // context.update("form:datosHvReferencia");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            nuevoHvReferencia1.setParentesco(tipoFamiliarSeleccionado);
            context.update("formularioDialogos:nuevooHvReferenciaLab");
        } else if (tipoActualizacion == 2) {
            duplicarHvReferencia1.setParentesco(tipoFamiliarSeleccionado);
            context.update("formularioDialogos:duplicarRRL");
        }
        filtradoTiposFamiliares = null;
        tipoFamiliarSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("sucursalesDialogo.hide()");
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.update("form:lovTiposFamiliares");
        context.update("form:datosHvReferencia");
    }

    public void cancelarCambioTiposFamiliares() {
        listHvReferencias1.get(index).setParentesco(tipoFamiliarSeleccionado);
        filtradoTiposFamiliares = null;
        tipoFamiliarSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void borrandoHvReferencias() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoHvReferencias");
                if (!modificarHvReferencias1.isEmpty() && modificarHvReferencias1.contains(listHvReferencias1.get(index))) {
                    int modIndex = modificarHvReferencias1.indexOf(listHvReferencias1.get(index));
                    modificarHvReferencias1.remove(modIndex);
                    borrarHvReferencias1.add(listHvReferencias1.get(index));
                } else if (!crearHvReferencias1.isEmpty() && crearHvReferencias1.contains(listHvReferencias1.get(index))) {
                    int crearIndex = crearHvReferencias1.indexOf(listHvReferencias1.get(index));
                    crearHvReferencias1.remove(crearIndex);
                } else {
                    borrarHvReferencias1.add(listHvReferencias1.get(index));
                }
                listHvReferencias1.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoHvReferencias ");
                if (!modificarHvReferencias1.isEmpty() && modificarHvReferencias1.contains(filtrarHvReferencias1.get(index))) {
                    int modIndex = modificarHvReferencias1.indexOf(filtrarHvReferencias1.get(index));
                    modificarHvReferencias1.remove(modIndex);
                    borrarHvReferencias1.add(filtrarHvReferencias1.get(index));
                } else if (!crearHvReferencias1.isEmpty() && crearHvReferencias1.contains(filtrarHvReferencias1.get(index))) {
                    int crearIndex = crearHvReferencias1.indexOf(filtrarHvReferencias1.get(index));
                    crearHvReferencias1.remove(crearIndex);
                } else {
                    borrarHvReferencias1.add(filtrarHvReferencias1.get(index));
                }
                int VCIndex = listHvReferencias1.indexOf(filtrarHvReferencias1.get(index));
                listHvReferencias1.remove(VCIndex);
                filtrarHvReferencias1.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosHvReferencia");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    /* public void verificarBorrado() {
     System.out.println("Estoy en verificarBorrado");
     try {
     System.err.println("Control Secuencia de ControlHvReferencias ");
     competenciasCargos = administrarHvEntrevistas.verificarBorradoCompetenciasCargos(listHvEntrevistas.get(index).getSecuencia());

     if (competenciasCargos.intValueExact() == 0) {
     System.out.println("Borrado==0");
     borrandoHvEntrevistas();
     } else {
     System.out.println("Borrado>0");

     RequestContext context = RequestContext.getCurrentInstance();
     context.update("form:validacionBorrar");
     context.execute("validacionBorrar.show()");
     index = -1;

     competenciasCargos = new BigDecimal(-1);

     }
     } catch (Exception e) {
     System.err.println("ERROR ControlHvReferencias verificarBorrado ERROR " + e);
     }
     }*/
    public void revisarDialogoGuardar() {

        if (!borrarHvReferencias1.isEmpty() || !crearHvReferencias1.isEmpty() || !modificarHvReferencias1.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarHvReferencia() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarHvReferencia");
            if (!borrarHvReferencias1.isEmpty()) {
                for (int i = 0; i < borrarHvReferencias1.size(); i++) {
                    System.out.println("Borrando...");
                    if (borrarHvReferencias1.get(i).getParentesco().getSecuencia() == null) {
                        borrarHvReferencias1.get(i).setParentesco(null);
                    }
                }
                administrarHvReferencias1.borrarHvReferencias(borrarHvReferencias1);
                //mostrarBorrados
                registrosBorrados = borrarHvReferencias1.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarHvReferencias1.clear();
            }
            if (!crearHvReferencias1.isEmpty()) {
                for (int i = 0; i < crearHvReferencias1.size(); i++) {

                    if (crearHvReferencias1.get(i).getParentesco().getSecuencia() == null) {
                        crearHvReferencias1.get(i).setParentesco(null);
                    }
                }
                System.out.println("Creando...");
                administrarHvReferencias1.crearHvReferencias(crearHvReferencias1);
                crearHvReferencias1.clear();
            }
            if (!modificarHvReferencias1.isEmpty()) {
                for (int i = 0; i < modificarHvReferencias1.size(); i++) {
                    if (modificarHvReferencias1.get(i).getParentesco().getSecuencia() == null) {
                        modificarHvReferencias1.get(i).setParentesco(null);
                    }
                }
                administrarHvReferencias1.modificarHvReferencias(modificarHvReferencias1);
                modificarHvReferencias1.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listHvReferencias1 = null;
            context.update("form:datosHvReferencia");
            k = 0;
        }
        index = -1;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarHvReferencia1 = listHvReferencias1.get(index);
            }
            if (tipoLista == 1) {
                editarHvReferencia1 = filtrarHvReferencias1.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editNombre");
                context.execute("editNombre.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editCargo");
                context.execute("editCargo.show()");
                cualCelda = -1;

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editTelefono");
                context.execute("editTelefono.show()");
                cualCelda = -1;

            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editCelular");
                context.execute("editCelular.show()");
                cualCelda = -1;

            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editParentesco");
                context.execute("editParentesco.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {

        if (Campo.equalsIgnoreCase("TIPOSFAMILIARES")) {
            if (tipoNuevo == 1) {
                nuevoParentesco = nuevoHvReferencia1.getParentesco().getTipo();
            } else if (tipoNuevo == 2) {
                nuevoParentesco = duplicarHvReferencia1.getParentesco().getTipo();
            }
        }
        System.err.println("NUEVO PARENTESCO " + nuevoParentesco);
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSFAMILIARES")) {
            if (!nuevoHvReferencia1.getParentesco().getTipo().equals("")) {
                if (tipoNuevo == 1) {
                    nuevoHvReferencia1.getParentesco().setTipo(nuevoParentesco);
                }
                for (int i = 0; i < listaTiposFamiliares.size(); i++) {
                    if (listaTiposFamiliares.get(i).getTipo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            } else {

                if (tipoNuevo == 1) {
                    nuevoHvReferencia1.setParentesco(new TiposFamiliares());
                    coincidencias = 1;
                }
                coincidencias = 1;
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoHvReferencia1.setParentesco(listaTiposFamiliares.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoNombreSucursal");
                }
                listaTiposFamiliares.clear();
                listaTiposFamiliares = null;
                getListaTiposFamiliares();
            } else {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoNombreSucursal");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarNombreSucursal");
                }
            }
        }
    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSFAMILIARES")) {
            if (!duplicarHvReferencia1.getParentesco().getTipo().equals("")) {
                if (tipoNuevo == 2) {
                    duplicarHvReferencia1.getParentesco().setTipo(nuevoParentesco);
                }
                for (int i = 0; i < listaTiposFamiliares.size(); i++) {
                    if (listaTiposFamiliares.get(i).getTipo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 2) {
                        duplicarHvReferencia1.setParentesco(listaTiposFamiliares.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarNombreSucursal");

                    }
                    listaTiposFamiliares.clear();
                    listaTiposFamiliares = null;
                    context.update("formularioDialogos:duplicarNombreSucursal");
                    getListaTiposFamiliares();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarNombreSucursal");
                    }
                }
            } else {
                if (tipoNuevo == 2) {
                    duplicarHvReferencia1.setParentesco(new TiposFamiliares());
                    System.out.println("NUEVO PARENTESCO " + nuevoParentesco);
                    if (tipoLista == 0) {
                        listHvReferencias1.get(index).getParentesco().setTipo(nuevoParentesco);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listHvReferencias1.get(index).getParentesco().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarHvReferencias1.get(index).getParentesco().setTipo(nuevoParentesco);
                    }

                }
            }

            context.update("formularioDialogos:duplicarNombreSucursal");

        }
    }

    public void asignarVariableTiposFamiliaresNuevo(int tipoNuevo) {
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

    public void agregarNuevoHvRefencias() {
        System.out.println("agregarNuevoHvRefencias");
        int contador = 0;
        nuevoHvReferencia1.setHojadevida(new HVHojasDeVida());
        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoHvReferencia1.getNombrepersona() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener una nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("bandera");
            contador++;
        }
        /*if (nuevoHvEntrevista.getTipo() == (null)) {
         mensajeValidacion = mensajeValidacion + " *Debe tener un tipo entrevista \n";
         System.out.println("Mensaje validacion : " + mensajeValidacion);
         } else {
         System.out.println("bandera");
         contador++;
         }*/

        listHvHojasDeVida = administrarHvReferencias1.consultarHvHojasDeVida(secuenciaEmpleado);
        if (listHvHojasDeVida == null) {
            System.err.println("ERROR NULO HVHOJASDEVIDA");
        } else {
            System.err.println("tamaño listHojasDeVida " + listHvHojasDeVida.size());
            hvHojasDeVida = listHvHojasDeVida.get(0);
            System.err.println("Agregar nuevo HvHojasDeVida " + hvHojasDeVida.getSecuencia());
            nuevoHvReferencia1.setHojadevida(hvHojasDeVida);
        }

        nuevoHvReferencia1.setTipo("FAMILIARES");
        System.err.println("agregar tipo entrevista " + nuevoHvReferencia1.getTipo());
        System.out.println("contador " + contador);

        if (contador == 1) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
                numTelefono.setFilterStyle("display: none; visibility: hidden;");
                numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
                numCelular.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvReferencia");
                bandera = 0;
                filtrarHvReferencias1 = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoHvReferencia1.setSecuencia(l);
            System.err.println("---------------AGREGAR REGISTRO----------------");
            System.err.println("nombre " + nuevoHvReferencia1.getNombrepersona());
            System.err.println("cargo " + nuevoHvReferencia1.getCargo());
            System.err.println("numero de telefono " + nuevoHvReferencia1.getTelefono());
            System.err.println("numero de celular" + nuevoHvReferencia1.getCelular());
            System.err.println("-----------------------------------------------");

            crearHvReferencias1.add(nuevoHvReferencia1);
            listHvReferencias1.add(nuevoHvReferencia1);
            nuevoHvReferencia1 = new HvReferencias();
            nuevoHvReferencia1.setParentesco(new TiposFamiliares());
            context.update("form:datosHvReferencia");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroHvReferencias.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoHvReferencias() {
        System.out.println("limpiarNuevoHvEntrevistas");
        nuevoHvReferencia1 = new HvReferencias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoHvEntrevistas() {
        System.out.println("duplicandoHvEntrevistas");
        if (index >= 0) {
            duplicarHvReferencia1 = new HvReferencias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarHvReferencia1.setSecuencia(l);
                duplicarHvReferencia1.setNombrepersona(listHvReferencias1.get(index).getNombrepersona());
                duplicarHvReferencia1.setCargo(listHvReferencias1.get(index).getCargo());
                duplicarHvReferencia1.setTelefono(listHvReferencias1.get(index).getTelefono());
                duplicarHvReferencia1.setCelular(listHvReferencias1.get(index).getCelular());
                duplicarHvReferencia1.setHojadevida(listHvReferencias1.get(index).getHojadevida());
                duplicarHvReferencia1.setTipo(listHvReferencias1.get(index).getTipo());
                TiposFamiliares t = new TiposFamiliares();
                t = listHvReferencias1.get(index).getParentesco();
                duplicarHvReferencia1.setParentesco(t);
            }
            if (tipoLista == 1) {
                duplicarHvReferencia1.setSecuencia(l);
                duplicarHvReferencia1.setNombrepersona(filtrarHvReferencias1.get(index).getNombrepersona());
                duplicarHvReferencia1.setCargo(filtrarHvReferencias1.get(index).getCargo());
                duplicarHvReferencia1.setTelefono(filtrarHvReferencias1.get(index).getTelefono());
                duplicarHvReferencia1.setCelular(filtrarHvReferencias1.get(index).getCelular());
                duplicarHvReferencia1.setHojadevida(filtrarHvReferencias1.get(index).getHojadevida());
                duplicarHvReferencia1.setTipo(filtrarHvReferencias1.get(index).getTipo());
                duplicarHvReferencia1.setParentesco(filtrarHvReferencias1.get(index).getParentesco());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarRRL");
            context.execute("duplicarRegistroHvReferencias.show()");
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR HVENTREVISTAS");
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarHvReferencia1.getNombrepersona().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 1) {

            if (crearHvReferencias1.contains(duplicarHvReferencia1)) {
                System.out.println("Ya lo contengo.");
            } else {
                crearHvReferencias1.add(duplicarHvReferencia1);
            }
            listHvReferencias1.add(duplicarHvReferencia1);
            context.update("form:datosHvReferencia");
            index = -1;
            secRegistro = null;

            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("nombre  " + duplicarHvReferencia1.getNombrepersona());
            System.err.println("cargo  " + duplicarHvReferencia1.getCargo());
            System.err.println("numero de telefono  " + duplicarHvReferencia1.getTelefono());
            System.err.println("numero de celular  " + duplicarHvReferencia1.getCelular());
            System.err.println("numero de Parentesco  " + duplicarHvReferencia1.getParentesco().getTipo());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");

            if (bandera == 1) {
                //CERRAR FILTRADO
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
                numTelefono.setFilterStyle("display: none; visibility: hidden;");
                numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
                numCelular.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");

                context.update("form:datosHvReferencia");
                bandera = 0;
                filtrarHvReferencias1 = null;
                tipoLista = 0;
            }
            context.execute("duplicarRegistroHvReferencias.hide()");
            duplicarHvReferencia1 = new HvReferencias();

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarHvReferencias() {
        duplicarHvReferencia1 = new HvReferencias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvReferenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "REFERENCIASFAMILIARES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvReferenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "REFERENCIASFAMILIARES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listHvReferencias1.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "HVREFERENCIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("HVREFERENCIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<HvReferencias> getListHvReferencias1() {
        if (listHvReferencias1 == null) {
            listHvReferencias1 = administrarHvReferencias1.consultarHvReferenciasFamiliaresPorEmpleado(secuenciaEmpleado);
        }
        return listHvReferencias1;
    }

    public void setListHvReferencias1(List<HvReferencias> listHvReferencias1) {
        this.listHvReferencias1 = listHvReferencias1;
    }

    public List<HvReferencias> getFiltrarHvReferencias1() {
        return filtrarHvReferencias1;
    }

    public void setFiltrarHvReferencias1(List<HvReferencias> filtrarHvReferencias1) {
        this.filtrarHvReferencias1 = filtrarHvReferencias1;
    }

    public List<HvReferencias> getCrearHvReferencias1() {
        return crearHvReferencias1;
    }

    public void setCrearHvReferencias1(List<HvReferencias> crearHvReferencias1) {
        this.crearHvReferencias1 = crearHvReferencias1;
    }

    public HvReferencias getNuevoHvReferencia1() {
        return nuevoHvReferencia1;
    }

    public void setNuevoHvReferencia1(HvReferencias nuevoHvReferencia1) {
        this.nuevoHvReferencia1 = nuevoHvReferencia1;
    }

    public HvReferencias getDuplicarHvReferencia1() {
        return duplicarHvReferencia1;
    }

    public void setDuplicarHvReferencia1(HvReferencias duplicarHvReferencia1) {
        this.duplicarHvReferencia1 = duplicarHvReferencia1;
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

    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarHvReferencias1.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<TiposFamiliares> getListaTiposFamiliares() {
        if (listaTiposFamiliares == null) {
            listaTiposFamiliares = administrarHvReferencias1.consultarLOVTiposFamiliares();
        }
        return listaTiposFamiliares;
    }

    public void setListaTiposFamiliares(List<TiposFamiliares> listaTiposFamiliares) {
        this.listaTiposFamiliares = listaTiposFamiliares;
    }

    public List<TiposFamiliares> getFiltradoTiposFamiliares() {
        return filtradoTiposFamiliares;
    }

    public void setFiltradoTiposFamiliares(List<TiposFamiliares> filtradoTiposFamiliares) {
        this.filtradoTiposFamiliares = filtradoTiposFamiliares;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public HvReferencias getEditarHvReferencia1() {
        return editarHvReferencia1;
    }

    public void setEditarHvReferencia1(HvReferencias editarHvReferencia1) {
        this.editarHvReferencia1 = editarHvReferencia1;
    }

    public TiposFamiliares getTipoFamiliarSeleccionado() {
        return tipoFamiliarSeleccionado;
    }

    public void setTipoFamiliarSeleccionado(TiposFamiliares tipoFamiliarSeleccionado) {
        this.tipoFamiliarSeleccionado = tipoFamiliarSeleccionado;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

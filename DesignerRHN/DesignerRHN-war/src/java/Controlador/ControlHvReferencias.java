/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.HvReferencias;
import Entidades.HVHojasDeVida;
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
public class ControlHvReferencias implements Serializable {

    @EJB
    AdministrarHvReferenciasInterface administrarHvReferencias;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<HvReferencias> listHvReferencias;
    private List<HvReferencias> filtrarHvReferencias;
    private List<HvReferencias> crearHvReferencias;
    private List<HvReferencias> modificarHvReferencias;
    private List<HvReferencias> borrarHvReferencias;
    private HvReferencias nuevoHvReferencia;
    private HvReferencias duplicarHvReferencia;
    private HvReferencias editarHvReferencia;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column cargo, nombre, numTelefono, numCelular;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger secuenciaEmpleado;
//Empleado
    private Empleados empleadoSeleccionado;
//otros
    private HVHojasDeVida hvHojasDeVida;
    private List<HVHojasDeVida> listHVHojasDeVida;

    public ControlHvReferencias() {
        listHvReferencias = null;
        crearHvReferencias = new ArrayList<HvReferencias>();
        modificarHvReferencias = new ArrayList<HvReferencias>();
        borrarHvReferencias = new ArrayList<HvReferencias>();
        permitirIndex = true;
        editarHvReferencia = new HvReferencias();
        nuevoHvReferencia = new HvReferencias();
        duplicarHvReferencia = new HvReferencias();
        empleadoSeleccionado = null;
        secuenciaEmpleado = null;
        listHVHojasDeVida = new ArrayList<HVHojasDeVida>();
        guardado = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarHvReferencias.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void recibirEmpleado(BigInteger sec) {
        System.out.println("ENTRE A RECIBIR EMPLEADO EN CONTROLHVREFERENCIAS");
        if (sec == null) {
            System.out.println("ERROR EN RECIVIR LA SECUENCIA DEL EMPLEADO EN ControlHvReferencias");
            // sec = BigInteger.valueOf(10661039);
        }
        secuenciaEmpleado = sec;
        listHvReferencias = null;
        empleadoSeleccionado = null;
        getEmpleadoSeleccionado();
        getListHvReferencias();
    }

    public void mostrarNuevo() {
        System.err.println("nuevo Tipo Entrevista " + nuevoHvReferencia.getTipo());
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
            secRegistro = listHvReferencias.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlHvReferencias.asignarIndex \n");
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
            System.out.println("ERROR ControlHvReferencias.asignarIndex ERROR======" + e.getMessage());
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
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("display: none; visibility: hidden;");
            numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias = null;
            tipoLista = 0;
        }

        borrarHvReferencias.clear();
        crearHvReferencias.clear();
        modificarHvReferencias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listHvReferencias = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHvReferencia");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("width: 270px");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("width: 145px");
            numTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("width: 135px");
            numCelular = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("width: 130px");
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
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias = null;
            tipoLista = 0;
        }
    }

    public void modificarHvReferencia(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearHvReferencias.contains(listHvReferencias.get(indice))) {

                    if (listHvReferencias.get(indice).getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listHvReferencias.get(indice).getNombrepersona().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarHvReferencias.isEmpty()) {
                            modificarHvReferencias.add(listHvReferencias.get(indice));
                        } else if (!modificarHvReferencias.contains(listHvReferencias.get(indice))) {
                            modificarHvReferencias.add(listHvReferencias.get(indice));
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

                if (!crearHvReferencias.contains(filtrarHvReferencias.get(indice))) {
                    if (filtrarHvReferencias.get(indice).getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarHvReferencias.get(indice).getNombrepersona().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarHvReferencias.isEmpty()) {
                            modificarHvReferencias.add(filtrarHvReferencias.get(indice));
                        } else if (!modificarHvReferencias.contains(filtrarHvReferencias.get(indice))) {
                            modificarHvReferencias.add(filtrarHvReferencias.get(indice));
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
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoHvReferencias() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoHvReferencias");
                if (!modificarHvReferencias.isEmpty() && modificarHvReferencias.contains(listHvReferencias.get(index))) {
                    int modIndex = modificarHvReferencias.indexOf(listHvReferencias.get(index));
                    modificarHvReferencias.remove(modIndex);
                    borrarHvReferencias.add(listHvReferencias.get(index));
                } else if (!crearHvReferencias.isEmpty() && crearHvReferencias.contains(listHvReferencias.get(index))) {
                    int crearIndex = crearHvReferencias.indexOf(listHvReferencias.get(index));
                    crearHvReferencias.remove(crearIndex);
                } else {
                    borrarHvReferencias.add(listHvReferencias.get(index));
                }
                listHvReferencias.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoHvReferencias ");
                if (!modificarHvReferencias.isEmpty() && modificarHvReferencias.contains(filtrarHvReferencias.get(index))) {
                    int modIndex = modificarHvReferencias.indexOf(filtrarHvReferencias.get(index));
                    modificarHvReferencias.remove(modIndex);
                    borrarHvReferencias.add(filtrarHvReferencias.get(index));
                } else if (!crearHvReferencias.isEmpty() && crearHvReferencias.contains(filtrarHvReferencias.get(index))) {
                    int crearIndex = crearHvReferencias.indexOf(filtrarHvReferencias.get(index));
                    crearHvReferencias.remove(crearIndex);
                } else {
                    borrarHvReferencias.add(filtrarHvReferencias.get(index));
                }
                int VCIndex = listHvReferencias.indexOf(filtrarHvReferencias.get(index));
                listHvReferencias.remove(VCIndex);
                filtrarHvReferencias.remove(index);

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

        if (!borrarHvReferencias.isEmpty() || !crearHvReferencias.isEmpty() || !modificarHvReferencias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarHvReferencia() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarHvReferencia");
            if (!borrarHvReferencias.isEmpty()) {
                administrarHvReferencias.borrarHvReferencias(borrarHvReferencias);

                //mostrarBorrados
                registrosBorrados = borrarHvReferencias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarHvReferencias.clear();
            }
            if (!crearHvReferencias.isEmpty()) {
                administrarHvReferencias.crearHvReferencias(crearHvReferencias);

                crearHvReferencias.clear();
            }
            if (!modificarHvReferencias.isEmpty()) {
                administrarHvReferencias.modificarHvReferencias(
                        modificarHvReferencias);
                modificarHvReferencias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listHvReferencias = null;
            context.update("form:datosHvReferencia");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarHvReferencia = listHvReferencias.get(index);
            }
            if (tipoLista == 1) {
                editarHvReferencia = filtrarHvReferencias.get(index);
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
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoHvRefencias() {
        System.out.println("agregarNuevoHvRefencias");
        int contador = 0;
        nuevoHvReferencia.setHojadevida(new HVHojasDeVida());
        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoHvReferencia.getNombrepersona() == (null)) {
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

        listHVHojasDeVida = administrarHvReferencias.consultarHvHojasDeVida(secuenciaEmpleado);
        if (listHVHojasDeVida == null) {
            System.err.println("ERROR NULO HVHOJASDEVIDA");
        } else {
            System.err.println("tamaño listHojasDeVida " + listHVHojasDeVida.size());
            hvHojasDeVida = listHVHojasDeVida.get(0);
            System.err.println("Agregar nuevo HVHojasDeVida " + hvHojasDeVida.getSecuencia());
            nuevoHvReferencia.setHojadevida(hvHojasDeVida);
        }

        nuevoHvReferencia.setTipo("PERSONALES");
        System.err.println("agregar tipo entrevista " + nuevoHvReferencia.getTipo());
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

                RequestContext.getCurrentInstance().update("form:datosHvReferencia");
                bandera = 0;
                filtrarHvReferencias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoHvReferencia.setSecuencia(l);
            System.err.println("---------------AGREGAR REGISTRO----------------");
            System.err.println("nombre " + nuevoHvReferencia.getNombrepersona());
            System.err.println("cargo " + nuevoHvReferencia.getCargo());
            System.err.println("numero de telefono" + nuevoHvReferencia.getTelefono());
            System.err.println("numero de celular" + nuevoHvReferencia.getCelular());
            System.err.println("-----------------------------------------------");

            crearHvReferencias.add(nuevoHvReferencia);
            listHvReferencias.add(nuevoHvReferencia);
            nuevoHvReferencia = new HvReferencias();
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
        nuevoHvReferencia = new HvReferencias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoHvEntrevistas() {
        System.out.println("duplicandoHvEntrevistas");
        if (index >= 0) {
            duplicarHvReferencia = new HvReferencias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarHvReferencia.setSecuencia(l);
                duplicarHvReferencia.setNombrepersona(listHvReferencias.get(index).getNombrepersona());
                duplicarHvReferencia.setCargo(listHvReferencias.get(index).getCargo());
                duplicarHvReferencia.setTelefono(listHvReferencias.get(index).getTelefono());
                duplicarHvReferencia.setCelular(listHvReferencias.get(index).getCelular());
                duplicarHvReferencia.setHojadevida(listHvReferencias.get(index).getHojadevida());
                duplicarHvReferencia.setTipo(listHvReferencias.get(index).getTipo());
            }
            if (tipoLista == 1) {
                duplicarHvReferencia.setSecuencia(l);
                duplicarHvReferencia.setNombrepersona(filtrarHvReferencias.get(index).getNombrepersona());
                duplicarHvReferencia.setCargo(filtrarHvReferencias.get(index).getCargo());
                duplicarHvReferencia.setTelefono(filtrarHvReferencias.get(index).getTelefono());
                duplicarHvReferencia.setCelular(filtrarHvReferencias.get(index).getCelular());
                duplicarHvReferencia.setHojadevida(filtrarHvReferencias.get(index).getHojadevida());
                duplicarHvReferencia.setTipo(filtrarHvReferencias.get(index).getTipo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarRRL");
            context.execute("duplicarRegistroHvReferencias.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR HVENTREVISTAS");
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarHvReferencia.getNombrepersona());

        if (duplicarHvReferencia.getNombrepersona().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 1) {

            if (crearHvReferencias.contains(duplicarHvReferencia)) {
                System.out.println("Ya lo contengo.");
            }
            listHvReferencias.add(duplicarHvReferencia);
            crearHvReferencias.add(duplicarHvReferencia);
            context.update("form:datosHvReferencia");
            index = -1;
            secRegistro = null;

            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("nombre " + duplicarHvReferencia.getNombrepersona());
            System.err.println("cargo " + duplicarHvReferencia.getCargo());
            System.err.println("numero de telefono" + duplicarHvReferencia.getTelefono());
            System.err.println("numero de celular" + duplicarHvReferencia.getCelular());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
            }
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
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

                RequestContext.getCurrentInstance().update("form:datosHvReferencia");
                bandera = 0;
                filtrarHvReferencias = null;
                tipoLista = 0;
            }
            duplicarHvReferencia = new HvReferencias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroHvReferencias.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarHvReferencias() {
        duplicarHvReferencia = new HvReferencias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvReferenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "REFERENCIASPERSONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvReferenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "REFERENCIASPERSONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listHvReferencias.isEmpty()) {
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
    public List<HvReferencias> getListHvReferencias() {
        if (listHvReferencias == null) {
            listHvReferencias = administrarHvReferencias.consultarHvReferenciasPersonalesPorEmpleado(secuenciaEmpleado);
        }
        return listHvReferencias;
    }

    public void setListHvReferencias(List<HvReferencias> listHvReferencias) {
        this.listHvReferencias = listHvReferencias;
    }

    public List<HvReferencias> getFiltrarHvReferencias() {
        return filtrarHvReferencias;
    }

    public void setFiltrarHvReferencias(List<HvReferencias> filtrarHvReferencias) {
        this.filtrarHvReferencias = filtrarHvReferencias;
    }

    public List<HvReferencias> getCrearHvReferencias() {
        return crearHvReferencias;
    }

    public void setCrearHvReferencias(List<HvReferencias> crearHvReferencias) {
        this.crearHvReferencias = crearHvReferencias;
    }

    public HvReferencias getNuevoHvReferencia() {
        return nuevoHvReferencia;
    }

    public void setNuevoHvReferencia(HvReferencias nuevoHvReferencia) {
        this.nuevoHvReferencia = nuevoHvReferencia;
    }

    public HvReferencias getDuplicarHvReferencia() {
        return duplicarHvReferencia;
    }

    public void setDuplicarHvReferencia(HvReferencias duplicarHvReferencia) {
        this.duplicarHvReferencia = duplicarHvReferencia;
    }

    public HvReferencias getEditarHvReferencia() {
        return editarHvReferencia;
    }

    public void setEditarHvReferencia(HvReferencias editarHvReferencia) {
        this.editarHvReferencia = editarHvReferencia;
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

    public BigInteger getSecuenciaEmpleado() {
        return secuenciaEmpleado;
    }

    public void setSecuenciaEmpleado(BigInteger secuenciaEmpleado) {
        this.secuenciaEmpleado = secuenciaEmpleado;
    }

    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarHvReferencias.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

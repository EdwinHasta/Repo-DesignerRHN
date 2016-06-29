/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.HvReferencias;
import Entidades.HVHojasDeVida;
import Entidades.Personas;
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
import javax.faces.application.FacesMessage;
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
    private HvReferencias hvReferenciaSeleccionada;
    //otros
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex, activarLov;
    //RASTRO
    private Column cargo, nombre, numTelefono, numCelular;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger secuenciaPersona;
//Empleado
    private Personas personaSeleccionada;
    private Empleados empleado;
//otros
    private int tamano;
    private String infoRegistro;
    private HVHojasDeVida hvHojasDeVida;
    private List<HVHojasDeVida> listHVHojasDeVida;
    private DataTable tablaC;
    private String backUpDescripcion;
    private Long backUpTelefono;
   
    public ControlHvReferencias() {
        listHvReferencias = null;
        crearHvReferencias = new ArrayList<HvReferencias>();
        modificarHvReferencias = new ArrayList<HvReferencias>();
        borrarHvReferencias = new ArrayList<HvReferencias>();
        permitirIndex = true;
        editarHvReferencia = new HvReferencias();
        nuevoHvReferencia = new HvReferencias();
        duplicarHvReferencia = new HvReferencias();
        empleado = new Empleados();
        secuenciaPersona = null;
        listHVHojasDeVida = new ArrayList<HVHojasDeVida>();
        guardado = true;
        tamano = 270;
        activarLov = true;
        hvHojasDeVida = new HVHojasDeVida();
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

    public void recibirEmpleado(BigInteger secuencia) {
            
            secuenciaPersona = secuencia;
            listHvReferencias = null;
            empleado = administrarHvReferencias.empleadoActual(secuencia);
            getListHvReferencias();
            contarRegistros();
            deshabilitarBotonLov();
            if(!listHvReferencias.isEmpty()){
                hvReferenciaSeleccionada = listHvReferencias.get(0);
            }
    }

    public void mostrarNuevo() {
        System.err.println("nuevo Tipo Entrevista " + nuevoHvReferencia.getTipo());
    }

    public void cambiarIndice(HvReferencias hvReferencia, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);
        hvReferenciaSeleccionada = hvReferencia;
        if (permitirIndex == true) {
            cualCelda = celda;
            hvReferenciaSeleccionada.getSecuencia();
            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    backUpDescripcion = hvReferenciaSeleccionada.getNombrepersona();
                } else {
                    backUpDescripcion = hvReferenciaSeleccionada.getNombrepersona();
                }
            }
            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    backUpTelefono = hvReferenciaSeleccionada.getTelefono();
                } else {
                    backUpTelefono = hvReferenciaSeleccionada.getTelefono();
                }
            }
        }
    }

    public void asignarIndex(HvReferencias hvReferencia, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlHvReferencias.asignarIndex \n");
            hvReferenciaSeleccionada = hvReferencia;
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
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            nombre = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            numTelefono = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("display: none; visibility: hidden;");
            numCelular = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias = null;
            tipoLista = 0;
        }

        borrarHvReferencias.clear();
        crearHvReferencias.clear();
        modificarHvReferencias.clear();
        hvReferenciaSeleccionada = null;
        k = 0;
        listHvReferencias = null;
        guardado = true;
        permitirIndex = true;
        getListHvReferencias();
        RequestContext context = RequestContext.getCurrentInstance();
        contarRegistros();
        context.update("form:informacionRegistro");
        context.update("form:datosHvReferencia");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            nombre = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            numTelefono = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("display: none; visibility: hidden;");
            numCelular = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias = null;
            tipoLista = 0;
        }

        borrarHvReferencias.clear();
        crearHvReferencias.clear();
        modificarHvReferencias.clear();
        hvReferenciaSeleccionada = null;
        k = 0;
        listHvReferencias = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        contarRegistros();
        context.update("form:informacionRegistro");
        context.update("form:datosHvReferencia");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            nombre = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("width: 85%");
            cargo = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("width: 85%");
            numTelefono = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("width: 85%");
            numCelular = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("width: 85%");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            nombre = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            numTelefono = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
            numTelefono.setFilterStyle("display: none; visibility: hidden;");
            numCelular = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numCelular");
            numCelular.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvReferencia");
            bandera = 0;
            filtrarHvReferencias = null;
            tipoLista = 0;
        }
    }

    public void modificarHvReferencia(HvReferencias hvReferencia, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR HV Referencia");
        hvReferenciaSeleccionada = hvReferencia;

        int contador = 0, pass = 0;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR HvReferencia, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearHvReferencias.contains(hvReferenciaSeleccionada)) {

                    if (hvReferenciaSeleccionada.getNombrepersona() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else if (hvReferenciaSeleccionada.getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else {
                        pass++;
                    }
                    if (hvReferenciaSeleccionada.getTelefono() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setTelefono(backUpTelefono);
                    } else {
                        pass++;
                    }

                    if (pass == 2) {
                        if (modificarHvReferencias.isEmpty()) {
                            modificarHvReferencias.add(hvReferenciaSeleccionada);
                        } else if (!modificarHvReferencias.contains(hvReferenciaSeleccionada)) {
                            modificarHvReferencias.add(hvReferenciaSeleccionada);
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                } else {
                    if (hvReferenciaSeleccionada.getNombrepersona() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else if (hvReferenciaSeleccionada.getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else {
                        pass++;
                    }
                    if (hvReferenciaSeleccionada.getTelefono() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setTelefono(backUpTelefono);
                    } else {
                        pass++;
                    }
                    if (pass == 2) {

                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                }
            } else {

                if (!crearHvReferencias.contains(hvReferenciaSeleccionada)) {
                    if (hvReferenciaSeleccionada.getNombrepersona() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else if (hvReferenciaSeleccionada.getNombrepersona().isEmpty()) {
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        pass++;
                    }
                    if (hvReferenciaSeleccionada.getTelefono() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setTelefono(backUpTelefono);
                    } else {
                        pass++;
                    }
                    if (pass == 2) {
                        if (modificarHvReferencias.isEmpty()) {
                            modificarHvReferencias.add(hvReferenciaSeleccionada);
                        } else if (!modificarHvReferencias.contains(hvReferenciaSeleccionada)) {
                            modificarHvReferencias.add(hvReferenciaSeleccionada);
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                } else {
                    if (hvReferenciaSeleccionada.getNombrepersona() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else if (hvReferenciaSeleccionada.getNombrepersona().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setNombrepersona(backUpDescripcion);
                    } else {
                        pass++;
                    }
                    if (hvReferenciaSeleccionada.getTelefono() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        hvReferenciaSeleccionada.setTelefono(backUpTelefono);
                    } else {
                        pass++;
                    }
                    if (pass == 2) {

                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                }

            }
            context.update("form:datosHvReferencia");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoHvReferencias() {

        if (hvReferenciaSeleccionada != null) {
            System.out.println("Entro a borrandoHvReferencias");
            if (!modificarHvReferencias.isEmpty() && modificarHvReferencias.contains(hvReferenciaSeleccionada)) {
                modificarHvReferencias.remove(modificarHvReferencias.indexOf(hvReferenciaSeleccionada));
                borrarHvReferencias.add(hvReferenciaSeleccionada);
            } else if (!crearHvReferencias.isEmpty() && crearHvReferencias.contains(hvReferenciaSeleccionada)) {
                crearHvReferencias.remove(crearHvReferencias.indexOf(hvReferenciaSeleccionada));
            } else {
                borrarHvReferencias.add(hvReferenciaSeleccionada);
            }
            listHvReferencias.remove(hvReferenciaSeleccionada);
            if (tipoLista == 1) {
                filtrarHvReferencias.remove(hvReferenciaSeleccionada);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            modificarInfoRegistro(listHvReferencias.size());
            context.update("form:informacionRegistro");
            context.update("form:datosHvReferencia");
            hvReferenciaSeleccionada = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        } else {
            RequestContext.getCurrentInstance().execute("formularioDialogos:seleccionarRegistro.show()");
        }
    }

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
                administrarHvReferencias.modificarHvReferencias(modificarHvReferencias);
                modificarHvReferencias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listHvReferencias = null;
            getListHvReferencias();
            contarRegistros();
            context.update("form:datosHvReferencia");
            FacesMessage msg = new FacesMessage("InformaciÃ³n", "Se gurdarÃ³n los datos con Ã©xito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            k = 0;
            guardado = true;
        }
        hvReferenciaSeleccionada = null;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (hvReferenciaSeleccionada != null) {
            if (tipoLista == 0) {
                editarHvReferencia = hvReferenciaSeleccionada;
            }
            if (tipoLista == 1) {
                editarHvReferencia = hvReferenciaSeleccionada;
            }
            deshabilitarBotonLov();
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

        } else {
            RequestContext.getCurrentInstance().execute("formularioDialogos:seleccionarRegistro.show()");
        }
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
            mensajeValidacion = mensajeValidacion + "El campo Nombre no puede estar vacÃ­o \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("bandera");
            contador++;
        }

        System.out.println("secuencia persona" + empleado.getPersona().getSecuencia());
        listHVHojasDeVida = administrarHvReferencias.consultarHvHojasDeVida(empleado.getPersona().getSecuencia());
        if (listHVHojasDeVida == null) {
            System.err.println("ERROR NULO HVHOJASDEVIDA PARA LA SECUENCIA DE PERSONA :" + empleado.getPersona().getSecuencia());
        } else {
//            if (!listHvReferencias.isEmpty()) {
//                System.out.println("La lista estÃ¡ vacÃ­a ");
//            } else {
                System.err.println("tamaÃ±o listHojasDeVida " + listHVHojasDeVida.size());
                hvHojasDeVida = listHVHojasDeVida.get(0);
                System.err.println("Agregar nuevo HVHojasDeVida " + hvHojasDeVida.getSecuencia());
                nuevoHvReferencia.setHojadevida(hvHojasDeVida);
            //}

        }

        nuevoHvReferencia.setTipo("PERSONALES");
        System.err.println("agregar tipo referencia" + nuevoHvReferencia.getTipo());
        System.out.println("contador " + contador);
        System.out.println("hvHojasDevida:" + hvHojasDeVida);

        if (contador == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                nombre = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                numTelefono = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
                numTelefono.setFilterStyle("display: none; visibility: hidden;");
                numCelular = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numCelular");
                numCelular.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvReferencia");
                bandera = 0;
                filtrarHvReferencias = null;
                tipoLista = 0;
                tamano = 270;
            }
            System.out.println("Despues de la bandera");
            System.out.println("nueva referencia " + nuevoHvReferencia);
            k++;
            l = BigInteger.valueOf(k);
            nuevoHvReferencia.setSecuencia(l);
            crearHvReferencias.add(nuevoHvReferencia);
            if (listHvReferencias == null) {
                listHvReferencias = new ArrayList<HvReferencias>();
            }
            listHvReferencias.add(nuevoHvReferencia);
            hvReferenciaSeleccionada = nuevoHvReferencia;
            nuevoHvReferencia = new HvReferencias();
            context.update("form:datosHvReferencia");
            modificarInfoRegistro(listHvReferencias.size());
            context.update("form:informacionRegistro");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroHvReferencias.hide()");

        } else {
            context.update("form:validacionNuevaReferencia");
            context.execute("validacionNuevaReferencia.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoHvReferencias() {
        System.out.println("limpiarNuevoHvEntrevistas");
        nuevoHvReferencia = new HvReferencias();

    }

    //------------------------------------------------------------------------------
    public void duplicandoHvEntrevistas() {
        System.out.println("duplicandoHvEntrevistas");
        if (hvReferenciaSeleccionada != null) {
            duplicarHvReferencia = new HvReferencias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarHvReferencia.setSecuencia(l);
                duplicarHvReferencia.setNombrepersona(hvReferenciaSeleccionada.getNombrepersona());
                duplicarHvReferencia.setCargo(hvReferenciaSeleccionada.getCargo());
                duplicarHvReferencia.setTelefono(hvReferenciaSeleccionada.getTelefono());
                duplicarHvReferencia.setCelular(hvReferenciaSeleccionada.getCelular());
                duplicarHvReferencia.setHojadevida(hvReferenciaSeleccionada.getHojadevida());
                duplicarHvReferencia.setTipo(hvReferenciaSeleccionada.getTipo());
            }
            if (tipoLista == 1) {
                duplicarHvReferencia.setSecuencia(l);
                duplicarHvReferencia.setNombrepersona(hvReferenciaSeleccionada.getNombrepersona());
                duplicarHvReferencia.setCargo(hvReferenciaSeleccionada.getCargo());
                duplicarHvReferencia.setTelefono(hvReferenciaSeleccionada.getTelefono());
                duplicarHvReferencia.setCelular(hvReferenciaSeleccionada.getCelular());
                duplicarHvReferencia.setHojadevida(hvReferenciaSeleccionada.getHojadevida());
                duplicarHvReferencia.setTipo(hvReferenciaSeleccionada.getTipo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarRRL");
            context.execute("duplicarRegistroHvReferencias.show()");
        } else {
            RequestContext.getCurrentInstance().execute("formularioDialogos:seleccionarRegistro.show()");
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
            mensajeValidacion = mensajeValidacion + "   *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 1) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarHvReferencia.setSecuencia(l);
            if (crearHvReferencias.contains(duplicarHvReferencia)) {
                System.out.println("Ya lo contengo.");
            }
            listHvReferencias.add(duplicarHvReferencia);
            crearHvReferencias.add(duplicarHvReferencia);
            hvReferenciaSeleccionada = duplicarHvReferencia;
            context.update("form:datosHvReferencia");
            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("nombre " + duplicarHvReferencia.getNombrepersona());
            System.err.println("cargo " + duplicarHvReferencia.getCargo());
            System.err.println("numero de telefono" + duplicarHvReferencia.getTelefono());
            System.err.println("numero de celular" + duplicarHvReferencia.getCelular());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
            }
            modificarInfoRegistro(listHvReferencias.size());
            context.update("form:informacionRegistro");
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                nombre = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                numTelefono = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numTelefono");
                numTelefono.setFilterStyle("display: none; visibility: hidden;");
                numCelular = (Column) c.getViewRoot().findComponent("form:datosHvReferencia:numCelular");
                numCelular.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvReferencia");
                bandera = 0;
                filtrarHvReferencias = null;
                tipoLista = 0;
                tamano = 270;
            }
            duplicarHvReferencia = new HvReferencias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroHvReferencias.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarReferencia");
            context.execute("validacionDuplicarReferencia.show()");
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
        hvReferenciaSeleccionada = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvReferenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "REFERENCIASPERSONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        hvReferenciaSeleccionada = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (hvReferenciaSeleccionada != null) {
            System.out.println("lol 2");
            int resultado = administrarRastros.obtenerTabla(hvReferenciaSeleccionada.getSecuencia(), "HVREFERENCIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("HVREFERENCIAS")) { // igual acÃ¡
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlHvReferencias.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            modificarInfoRegistro(filtrarHvReferencias.size());
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlHvReferencias eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }

    public void contarRegistros() {
        if (listHvReferencias != null) {
            modificarInfoRegistro(listHvReferencias.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    public void deshabilitarBotonLov() {
        activarLov = true;
    }

    public void recordarSeleccion() {
        if (hvReferenciaSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosHvReferencia");
            tablaC.setSelection(hvReferenciaSeleccionada);
        }
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<HvReferencias> getListHvReferencias() {
       if(listHvReferencias == null){
           listHvReferencias=administrarHvReferencias.consultarHvReferenciasPersonalesPorPersona(empleado.getPersona().getSecuencia());
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

    public BigInteger getSecuenciaPersona() {
        return secuenciaPersona;
    }

    public void setSecuenciaPersona(BigInteger secuenciaPersona) {
        this.secuenciaPersona = secuenciaPersona;
    }

    public Personas getPersonaSeleccionada() {
//        if (personaSeleccionada == null) {
//            personaSeleccionada = administrarHvReferencias.consultarPersonas(secuenciaPersona);
//        }
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Personas personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public HvReferencias getHvReferenciaSeleccionada() {
        return hvReferenciaSeleccionada;
    }

    public void setHvReferenciaSeleccionada(HvReferencias hvReferenciaSeleccionada) {
        this.hvReferenciaSeleccionada = hvReferenciaSeleccionada;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public boolean isActivarLov() {
        return activarLov;
    }

    public void setActivarLov(boolean activarLov) {
        this.activarLov = activarLov;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public HVHojasDeVida getHvHojasDeVida() {
        return hvHojasDeVida;
    }

    public void setHvHojasDeVida(HVHojasDeVida hvHojasDeVida) {
        this.hvHojasDeVida = hvHojasDeVida;
    }

    public List<HVHojasDeVida> getListHVHojasDeVida() {
        return listHVHojasDeVida;
    }

    public void setListHVHojasDeVida(List<HVHojasDeVida> listHVHojasDeVida) {
        this.listHVHojasDeVida = listHVHojasDeVida;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.HvEntrevistas;
import Entidades.HVHojasDeVida;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarHvEntrevistasInterface;
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
public class ControlHvEntrevistas implements Serializable {

    @EJB
    AdministrarHvEntrevistasInterface administrarHvEntrevistas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<HvEntrevistas> listHvEntrevistas;
    private List<HvEntrevistas> filtrarHvEntrevistas;
    private List<HvEntrevistas> crearHvEntrevistas;
    private List<HvEntrevistas> modificarHvEntrevistas;
    private List<HvEntrevistas> borrarHvEntrevistas;
    private HvEntrevistas nuevoHvEntrevista;
    private HvEntrevistas duplicarHvEntrevista;
    private HvEntrevistas editarHvEntrevista;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column fecha, nombre, tipoPuntaje, puntaje;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger secuenciaEmpleado;
//Empleado
    private Empleados empleadoSeleccionado;
//otros
    private HVHojasDeVida hvHojasDeVida;
    private List<HVHojasDeVida> listHVHojasDeVida;

    public ControlHvEntrevistas() {
        listHvEntrevistas = null;
        crearHvEntrevistas = new ArrayList<HvEntrevistas>();
        modificarHvEntrevistas = new ArrayList<HvEntrevistas>();
        borrarHvEntrevistas = new ArrayList<HvEntrevistas>();
        permitirIndex = true;
        editarHvEntrevista = new HvEntrevistas();
        nuevoHvEntrevista = new HvEntrevistas();
        duplicarHvEntrevista = new HvEntrevistas();
        empleadoSeleccionado = null;
        secuenciaEmpleado = BigInteger.valueOf(10661474);
        listHVHojasDeVida = new ArrayList<HVHojasDeVida>();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarHvEntrevistas.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        if (sec == null) {
            System.out.println("ERROR EN RECIVIR LA SECUENCIA DEL EMPLEADO EN CONTROLHVENTREVISTAS");
            sec = BigInteger.valueOf(10661474);
        }
        secuenciaEmpleado = sec;
        listHvEntrevistas = null;
        empleadoSeleccionado = null;
        getEmpleadoSeleccionado();
        getListHvEntrevistas();
    }

    public void mostrarNuevo() {
        System.err.println("nuevo Tipo Entrevista " + nuevoHvEntrevista.getTipo());
    }

    public void mostrarInfo(int indice, int celda) {
        int contador = 0;
        int fechas = 0;
        if (permitirIndex == true) {
            RequestContext context = RequestContext.getCurrentInstance();

            mensajeValidacion = " ";
            index = indice;
            cualCelda = celda;
            secRegistro = listHvEntrevistas.get(index).getSecuencia();
            System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listHvEntrevistas.get(indice).getFecha());
            if (listHvEntrevistas.get(indice).getFecha() == null) {
                mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                contador++;
            } else {
                for (int j = 0; j < listHvEntrevistas.size(); j++) {
                    if (j != indice) {
                        if (listHvEntrevistas.get(indice).getFecha().equals(listHvEntrevistas.get(j).getFecha())) {
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
                if (!crearHvEntrevistas.contains(listHvEntrevistas.get(indice))) {
                    if (modificarHvEntrevistas.isEmpty()) {
                        modificarHvEntrevistas.add(listHvEntrevistas.get(indice));
                    } else if (!modificarHvEntrevistas.contains(listHvEntrevistas.get(indice))) {
                        modificarHvEntrevistas.add(listHvEntrevistas.get(indice));
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
            index = -1;
            secRegistro = null;
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlHvEntrevistas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlHvEntrevistas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listHvEntrevistas.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlHvEntrevistas.asignarIndex \n");
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
            System.out.println("ERROR ControlHvEntrevistas.asignarIndex ERROR======" + e.getMessage());
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
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            tipoPuntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:tipoPuntaje");
            tipoPuntaje.setFilterStyle("display: none; visibility: hidden;");
            puntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:puntaje");
            puntaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarHvEntrevistas = null;
            tipoLista = 0;
        }

        borrarHvEntrevistas.clear();
        crearHvEntrevistas.clear();
        modificarHvEntrevistas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listHvEntrevistas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHvEntrevista");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("width: 90px");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:nombre");
            nombre.setFilterStyle("width: 270px");
            tipoPuntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:tipoPuntaje");
            tipoPuntaje.setFilterStyle("width: 180px");
            puntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:puntaje");
            puntaje.setFilterStyle("width: 130px");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            tipoPuntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:tipoPuntaje");
            tipoPuntaje.setFilterStyle("display: none; visibility: hidden;");
            puntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:puntaje");
            puntaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarHvEntrevistas = null;
            tipoLista = 0;
        }
    }

    public void modificarHvEntrevista(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR HV ENTREVISTA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EVALCOMPETENCIAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearHvEntrevistas.contains(listHvEntrevistas.get(indice))) {
                    if (listHvEntrevistas.get(indice).getFecha() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }
                    if (listHvEntrevistas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listHvEntrevistas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarHvEntrevistas.isEmpty()) {
                            modificarHvEntrevistas.add(listHvEntrevistas.get(indice));
                        } else if (!modificarHvEntrevistas.contains(listHvEntrevistas.get(indice))) {
                            modificarHvEntrevistas.add(listHvEntrevistas.get(indice));
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

                if (!crearHvEntrevistas.contains(filtrarHvEntrevistas.get(indice))) {
                    if (filtrarHvEntrevistas.get(indice).getFecha() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (filtrarHvEntrevistas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarHvEntrevistas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarHvEntrevistas.isEmpty()) {
                            modificarHvEntrevistas.add(filtrarHvEntrevistas.get(indice));
                        } else if (!modificarHvEntrevistas.contains(filtrarHvEntrevistas.get(indice))) {
                            modificarHvEntrevistas.add(filtrarHvEntrevistas.get(indice));
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
            context.update("form:datosHvEntrevista");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoHvEntrevistas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoEvalCompetencias");
                if (!modificarHvEntrevistas.isEmpty() && modificarHvEntrevistas.contains(listHvEntrevistas.get(index))) {
                    int modIndex = modificarHvEntrevistas.indexOf(listHvEntrevistas.get(index));
                    modificarHvEntrevistas.remove(modIndex);
                    borrarHvEntrevistas.add(listHvEntrevistas.get(index));
                } else if (!crearHvEntrevistas.isEmpty() && crearHvEntrevistas.contains(listHvEntrevistas.get(index))) {
                    int crearIndex = crearHvEntrevistas.indexOf(listHvEntrevistas.get(index));
                    crearHvEntrevistas.remove(crearIndex);
                } else {
                    borrarHvEntrevistas.add(listHvEntrevistas.get(index));
                }
                listHvEntrevistas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalCompetencias ");
                if (!modificarHvEntrevistas.isEmpty() && modificarHvEntrevistas.contains(filtrarHvEntrevistas.get(index))) {
                    int modIndex = modificarHvEntrevistas.indexOf(filtrarHvEntrevistas.get(index));
                    modificarHvEntrevistas.remove(modIndex);
                    borrarHvEntrevistas.add(filtrarHvEntrevistas.get(index));
                } else if (!crearHvEntrevistas.isEmpty() && crearHvEntrevistas.contains(filtrarHvEntrevistas.get(index))) {
                    int crearIndex = crearHvEntrevistas.indexOf(filtrarHvEntrevistas.get(index));
                    crearHvEntrevistas.remove(crearIndex);
                } else {
                    borrarHvEntrevistas.add(filtrarHvEntrevistas.get(index));
                }
                int VCIndex = listHvEntrevistas.indexOf(filtrarHvEntrevistas.get(index));
                listHvEntrevistas.remove(VCIndex);
                filtrarHvEntrevistas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosHvEntrevista");
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
     System.err.println("Control Secuencia de ControlHvEntrevistas ");
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
     System.err.println("ERROR ControlHvEntrevistas verificarBorrado ERROR " + e);
     }
     }*/
    public void revisarDialogoGuardar() {

        if (!borrarHvEntrevistas.isEmpty() || !crearHvEntrevistas.isEmpty() || !modificarHvEntrevistas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarHvEntrevistas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalCompetencias");
            if (!borrarHvEntrevistas.isEmpty()) {
                administrarHvEntrevistas.borrarHvEntrevistas(borrarHvEntrevistas);
                 //mostrarBorrados
                registrosBorrados = borrarHvEntrevistas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarHvEntrevistas.clear();
            }
            if (!crearHvEntrevistas.isEmpty()) {
                administrarHvEntrevistas.crearHvEntrevistas(crearHvEntrevistas);
                crearHvEntrevistas.clear();
            }
            if (!modificarHvEntrevistas.isEmpty()) {
                administrarHvEntrevistas.modificarHvEntrevistas(modificarHvEntrevistas);
                modificarHvEntrevistas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listHvEntrevistas = null;
            context.update("form:datosHvEntrevista");
            k = 0;
        }
        guardado = true;
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarHvEntrevista = listHvEntrevistas.get(index);
            }
            if (tipoLista == 1) {
                editarHvEntrevista = filtrarHvEntrevistas.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editNombre");
                context.execute("editNombre.show()");
                cualCelda = -1;

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editDescripcionCompetencia");
                context.execute("editDescripcionCompetencia.show()");
                cualCelda = -1;

            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editPuntaje");
                context.execute("editPuntaje.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoHvEntrevistas() {
        System.out.println("agregarNuevoHvEntrevistas");
        int contador = 0;
        nuevoHvEntrevista.setHojadevida(new HVHojasDeVida());
        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoHvEntrevista.getFecha() == null) {
            mensajeValidacion = " *Debe tener una fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("bandera");
            contador++;
        }
        if (nuevoHvEntrevista.getNombre() == (null)) {
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

        listHVHojasDeVida = administrarHvEntrevistas.buscarHVHojasDeVida(secuenciaEmpleado);
        if (listHVHojasDeVida == null) {
            System.err.println("ERROR NULO HVHOJASDEVIDA");
        } else {
            System.err.println("tamaño listHojasDeVida " + listHVHojasDeVida.size());
            hvHojasDeVida = listHVHojasDeVida.get(0);
            System.err.println("Agregar nuevo HVHojasDeVida " + hvHojasDeVida.getSecuencia());
            nuevoHvEntrevista.setHojadevida(hvHojasDeVida);
        }
        if (nuevoHvEntrevista.getTipo() == null) {
            System.err.println("SOY NULL DEFAULT INDIVUDUAL");
            nuevoHvEntrevista.setTipo("INDIVIDUAL");
        } else {
            nuevoHvEntrevista.setTipo("GRUPAL");
        }

        System.err.println("agregar tipo entrevista " + nuevoHvEntrevista.getTipo());
        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                tipoPuntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:tipoPuntaje");
                tipoPuntaje.setFilterStyle("display: none; visibility: hidden;");
                puntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:puntaje");
                puntaje.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarHvEntrevistas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoHvEntrevista.setSecuencia(l);
            System.err.println("---------------AGREGAR REGISTRO----------------");
            System.err.println("fecha " + nuevoHvEntrevista.getFecha());
            System.err.println("nombre " + nuevoHvEntrevista.getNombre());
            System.err.println("tipo " + nuevoHvEntrevista.getTipo());
            System.err.println("puntaje " + nuevoHvEntrevista.getPuntaje());
            System.err.println("-----------------------------------------------");

            crearHvEntrevistas.add(nuevoHvEntrevista);
            listHvEntrevistas.add(nuevoHvEntrevista);
            nuevoHvEntrevista = new HvEntrevistas();
            context.update("form:datosHvEntrevista");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalEmpresas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoHvEntrevistas() {
        System.out.println("limpiarNuevoHvEntrevistas");
        nuevoHvEntrevista = new HvEntrevistas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoHvEntrevistas() {
        System.out.println("duplicandoHvEntrevistas");
        if (index >= 0) {
            duplicarHvEntrevista = new HvEntrevistas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarHvEntrevista.setSecuencia(l);
                duplicarHvEntrevista.setFecha(listHvEntrevistas.get(index).getFecha());
                duplicarHvEntrevista.setNombre(listHvEntrevistas.get(index).getNombre());
                duplicarHvEntrevista.setTipo(listHvEntrevistas.get(index).getTipo());
                duplicarHvEntrevista.setPuntaje(listHvEntrevistas.get(index).getPuntaje());
                duplicarHvEntrevista.setHojadevida(listHvEntrevistas.get(index).getHojadevida());
            }
            if (tipoLista == 1) {
                duplicarHvEntrevista.setSecuencia(l);
                duplicarHvEntrevista.setFecha(filtrarHvEntrevistas.get(index).getFecha());
                duplicarHvEntrevista.setNombre(filtrarHvEntrevistas.get(index).getNombre());
                duplicarHvEntrevista.setTipo(filtrarHvEntrevistas.get(index).getTipo());
                duplicarHvEntrevista.setPuntaje(filtrarHvEntrevistas.get(index).getPuntaje());
                duplicarHvEntrevista.setHojadevida(filtrarHvEntrevistas.get(index).getHojadevida());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEvC");
            context.execute("duplicarRegistroEvalCompetencias.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarHvEntrevista.getFecha());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarHvEntrevista.getNombre());

        if (duplicarHvEntrevista.getFecha() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("bandera");
            contador++;

        }
        if (duplicarHvEntrevista.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                tipoPuntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:tipoPuntaje");
                tipoPuntaje.setFilterStyle("display: none; visibility: hidden;");
                puntaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:puntaje");
                puntaje.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarHvEntrevistas = null;
                tipoLista = 0;
            }
            System.out.println("Datos Duplicando: " + duplicarHvEntrevista.getSecuencia() + "  " + duplicarHvEntrevista.getFecha());
            if (crearHvEntrevistas.contains(duplicarHvEntrevista)) {
                System.out.println("Ya lo contengo.");
            }
            listHvEntrevistas.add(duplicarHvEntrevista);
            crearHvEntrevistas.add(duplicarHvEntrevista);
            context.update("form:datosHvEntrevista");
            index = -1;
            secRegistro = null;

            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("fecha " + duplicarHvEntrevista.getFecha());
            System.err.println("nombre " + duplicarHvEntrevista.getNombre());
            System.err.println("tipo " + duplicarHvEntrevista.getTipo());
            System.err.println("puntaje " + duplicarHvEntrevista.getPuntaje());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            duplicarHvEntrevista = new HvEntrevistas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarHvEntrevistas() {
        duplicarHvEntrevista = new HvEntrevistas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "HVENTREVISTAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "HVENTREVISTAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listHvEntrevistas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "HVENTREVISTAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("HVENTREVISTAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarHvEntrevistas.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<HvEntrevistas> getListHvEntrevistas() {
        if (listHvEntrevistas == null) {
            listHvEntrevistas = administrarHvEntrevistas.consultarHvEntrevistasPorEmpleado(secuenciaEmpleado);
        }
        return listHvEntrevistas;
    }

    public void setListHvEntrevistas(List<HvEntrevistas> listHvEntrevistas) {
        this.listHvEntrevistas = listHvEntrevistas;
    }

    public List<HvEntrevistas> getFiltrarHvEntrevistas() {
        return filtrarHvEntrevistas;
    }

    public void setFiltrarHvEntrevistas(List<HvEntrevistas> filtrarHvEntrevistas) {
        this.filtrarHvEntrevistas = filtrarHvEntrevistas;
    }

    public HvEntrevistas getNuevoHvEntrevista() {
        return nuevoHvEntrevista;
    }

    public void setNuevoHvEntrevista(HvEntrevistas nuevoHvEntrevista) {
        this.nuevoHvEntrevista = nuevoHvEntrevista;
    }

    public HvEntrevistas getDuplicarHvEntrevista() {
        return duplicarHvEntrevista;
    }

    public void setDuplicarHvEntrevista(HvEntrevistas duplicarHvEntrevista) {
        this.duplicarHvEntrevista = duplicarHvEntrevista;
    }

    public HvEntrevistas getEditarHvEntrevista() {
        return editarHvEntrevista;
    }

    public void setEditarHvEntrevista(HvEntrevistas editarHvEntrevista) {
        this.editarHvEntrevista = editarHvEntrevista;
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

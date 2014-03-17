/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Paises;
import Entidades.Festivos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarFestivosInterface;
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
public class ControlFestivos implements Serializable {

    @EJB
    AdministrarFestivosInterface administrarFestivos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Festivos> listFestivosPorPais;
    private List<Festivos> filtrarFestivosPorPais;
    private List<Festivos> crearFestivosPorPais;
    private List<Festivos> modificarFestivosPorPais;
    private List<Festivos> borrarFestivosPorPais;
    private Festivos nuevoFestivos;
    private Festivos duplicarFestivos;
    private Festivos editarFestivos;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column fecha, parentesco;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
//eado

    private List<Paises> listPaises;
    private Paises paisSeleccionado;
    private List<Paises> listPaisesFiltrar;

    public ControlFestivos() {
        listFestivosPorPais = null;
        crearFestivosPorPais = new ArrayList<Festivos>();
        modificarFestivosPorPais = new ArrayList<Festivos>();
        borrarFestivosPorPais = new ArrayList<Festivos>();
        permitirIndex = true;
        editarFestivos = new Festivos();
        nuevoFestivos = new Festivos();
        nuevoFestivos.setPais(new Paises());
        duplicarFestivos = new Festivos();
        guardado = true;
    }

    public void recibirPais() {
        RequestContext context = RequestContext.getCurrentInstance();

        System.out.println("PAIS ESCOGIDO : " + paisSeleccionado.getNombre());
        System.out.println("PAIS ESCOGIDO : " + crearFestivosPorPais.size());
        System.out.println("PAIS ESCOGIDO : " + modificarFestivosPorPais.size());
        System.out.println("PAIS ESCOGIDO : " + borrarFestivosPorPais.size());

        if (crearFestivosPorPais.size() == 0 && modificarFestivosPorPais.size() == 0 && borrarFestivosPorPais.size() == 0) {
            listFestivosPorPais = null;
            getListFestivosPorPais();
        } else {
            context.update("form:confirmarPais");
            context.execute("confirmarPais.show()");
        }
        context.update("form:datosHvEntrevista");
    }

    public void anularCambios() {
        modificarFestivosPorPais = null;
        crearFestivosPorPais = null;
        borrarFestivosPorPais = null;
        listFestivosPorPais = null;
        recibirPais();
    }

    public void mostrarNuevo() {
        System.err.println("NUEVA FECHA : " + nuevoFestivos.getDia());
    }

    public void mostrarInfo(int indice, int celda) {
        int contador = 0;
        int fechas = 0;
        mensajeValidacion = " ";
        index = indice;
        cualCelda = celda;
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex == true) {
            if (tipoLista == 0) {
                secRegistro = listFestivosPorPais.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listFestivosPorPais.get(indice).getDia());
                if (listFestivosPorPais.get(indice).getDia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < listFestivosPorPais.size(); j++) {
                        if (j != indice) {
                            if (listFestivosPorPais.get(indice).getDia().equals(listFestivosPorPais.get(j).getDia())) {
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
                    if (!crearFestivosPorPais.contains(listFestivosPorPais.get(indice))) {
                        if (modificarFestivosPorPais.isEmpty()) {
                            modificarFestivosPorPais.add(listFestivosPorPais.get(indice));
                        } else if (!modificarFestivosPorPais.contains(listFestivosPorPais.get(indice))) {
                            modificarFestivosPorPais.add(listFestivosPorPais.get(indice));
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
            } else {

                secRegistro = filtrarFestivosPorPais.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + filtrarFestivosPorPais.get(indice).getDia());
                if (filtrarFestivosPorPais.get(indice).getDia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < filtrarFestivosPorPais.size(); j++) {
                        if (j != indice) {
                            if (filtrarFestivosPorPais.get(indice).getDia().equals(filtrarFestivosPorPais.get(j).getDia())) {
                                fechas++;
                            }
                        }
                    }
                    for (int j = 0; j < listFestivosPorPais.size(); j++) {
                        if (j != indice) {
                            if (filtrarFestivosPorPais.get(indice).getDia().equals(listFestivosPorPais.get(j).getDia())) {
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
                    if (!crearFestivosPorPais.contains(listFestivosPorPais.get(indice))) {
                        if (modificarFestivosPorPais.isEmpty()) {
                            modificarFestivosPorPais.add(listFestivosPorPais.get(indice));
                        } else if (!modificarFestivosPorPais.contains(listFestivosPorPais.get(indice))) {
                            modificarFestivosPorPais.add(listFestivosPorPais.get(indice));
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

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLBETAEMPLVIGENCIANORMALABORAL.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETAEMPLVIGENCIANORMALABORAL eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listFestivosPorPais.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A CONTROLBETAEMPLVIGENCIANORMALABORAL.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 1) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                dig = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETAEMPLVIGENCIANORMALABORAL.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            if (cualCelda == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarFestivosPorPais = null;
            tipoLista = 0;
        }

        borrarFestivosPorPais.clear();
        crearFestivosPorPais.clear();
        modificarFestivosPorPais.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listFestivosPorPais = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHvEntrevista");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("width: 60px");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("width: 600px");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarFestivosPorPais = null;
            tipoLista = 0;
        }
    }

    public void modificandoFestivos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR EMPL VIGENCIA NORMA LABORAL");
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPLVIGENCIANORMALABORAL, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearFestivosPorPais.contains(listFestivosPorPais.get(indice))) {
                    if (listFestivosPorPais.get(indice).getDia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listFestivosPorPais.size(); j++) {
                            if (j != indice) {
                                if (listFestivosPorPais.get(indice).getDia().equals(listFestivosPorPais.get(j).getDia())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "FECHAS REPETIDAS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }
                    }

                    if (banderita == true) {
                        if (modificarFestivosPorPais.isEmpty()) {
                            modificarFestivosPorPais.add(listFestivosPorPais.get(indice));
                        } else if (!modificarFestivosPorPais.contains(listFestivosPorPais.get(indice))) {
                            modificarFestivosPorPais.add(listFestivosPorPais.get(indice));
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

                if (!crearFestivosPorPais.contains(filtrarFestivosPorPais.get(indice))) {
                    if (filtrarFestivosPorPais.get(indice).getDia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarFestivosPorPais.isEmpty()) {
                            modificarFestivosPorPais.add(filtrarFestivosPorPais.get(indice));
                        } else if (!modificarFestivosPorPais.contains(filtrarFestivosPorPais.get(indice))) {
                            modificarFestivosPorPais.add(filtrarFestivosPorPais.get(indice));
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

    public void limpiarNuevaNormaLaboral() {
        try {
            System.out.println("\n ENTRE A LIMPIAR NUEVO NORMA LABORAL \n");
            nuevoFestivos = new Festivos();
            nuevoFestivos.setPais(new Paises());
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETAEMPLVIGENCIANORMALABORAL LIMPIAR NUEVO NORMA LABORAL ERROR :" + e.getMessage());
        }
    }

    public void borrandoHvEntrevistas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoEvalCompetencias");
                if (!modificarFestivosPorPais.isEmpty() && modificarFestivosPorPais.contains(listFestivosPorPais.get(index))) {
                    int modIndex = modificarFestivosPorPais.indexOf(listFestivosPorPais.get(index));
                    modificarFestivosPorPais.remove(modIndex);
                    borrarFestivosPorPais.add(listFestivosPorPais.get(index));
                } else if (!crearFestivosPorPais.isEmpty() && crearFestivosPorPais.contains(listFestivosPorPais.get(index))) {
                    int crearIndex = crearFestivosPorPais.indexOf(listFestivosPorPais.get(index));
                    crearFestivosPorPais.remove(crearIndex);
                } else {
                    borrarFestivosPorPais.add(listFestivosPorPais.get(index));
                }
                listFestivosPorPais.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalCompetencias ");
                if (!modificarFestivosPorPais.isEmpty() && modificarFestivosPorPais.contains(filtrarFestivosPorPais.get(index))) {
                    int modIndex = modificarFestivosPorPais.indexOf(filtrarFestivosPorPais.get(index));
                    modificarFestivosPorPais.remove(modIndex);
                    borrarFestivosPorPais.add(filtrarFestivosPorPais.get(index));
                } else if (!crearFestivosPorPais.isEmpty() && crearFestivosPorPais.contains(filtrarFestivosPorPais.get(index))) {
                    int crearIndex = crearFestivosPorPais.indexOf(filtrarFestivosPorPais.get(index));
                    crearFestivosPorPais.remove(crearIndex);
                } else {
                    borrarFestivosPorPais.add(filtrarFestivosPorPais.get(index));
                }
                int VCIndex = listFestivosPorPais.indexOf(filtrarFestivosPorPais.get(index));
                listFestivosPorPais.remove(VCIndex);
                filtrarFestivosPorPais.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosHvEntrevista");
            context.update("form:ACEPTAR");
            index = -1;
            secRegistro = null;

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

        if (!borrarFestivosPorPais.isEmpty() || !crearFestivosPorPais.isEmpty() || !modificarFestivosPorPais.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarFestivos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalCompetencias");
            if (!borrarFestivosPorPais.isEmpty()) {
                for (int i = 0; i < borrarFestivosPorPais.size(); i++) {
                    System.out.println("Borrando...");
                    administrarFestivos.borrarFestivos(borrarFestivosPorPais);
                }
                //mostrarBorrados
                registrosBorrados = borrarFestivosPorPais.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarFestivosPorPais.clear();
            }
            if (!crearFestivosPorPais.isEmpty()) {
                for (int i = 0; i < crearFestivosPorPais.size(); i++) {
                    System.out.println("Creando...");
                    System.out.println("-----------------------------------------------");
                    System.out.println("Fecha :" + crearFestivosPorPais.get(i).getDia());
                    System.out.println("Pais : " + crearFestivosPorPais.get(i).getPais().getNombre());
                    System.out.println("-----------------------------------------------");
                    administrarFestivos.crearFestivos(crearFestivosPorPais);

                }
                crearFestivosPorPais.clear();
            }
            if (!modificarFestivosPorPais.isEmpty()) {
                System.out.println("Modificando...");
                administrarFestivos.modificarFestivos(modificarFestivosPorPais);
                modificarFestivosPorPais.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listFestivosPorPais = null;
             context.execute("mostrarGuardar.show()");
            context.update("form:datosHvEntrevista");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarFestivos = listFestivosPorPais.get(index);
            }
            if (tipoLista == 1) {
                editarFestivos = filtrarFestivosPorPais.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editPuntaje");
                context.execute("editPuntaje.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoFestivos() {
        System.out.println("agregarNuevoFestivos");
        int contador = 0;
        //nuevoFestivos.setPais(new Paises());
        Short a = 0;
        a = null;
        int fechas = 0;
        mensajeValidacion = " ";
        nuevoFestivos.setPais(paisSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Nueva Fecha : " + nuevoFestivos.getDia());
        if (nuevoFestivos.getDia() == null || nuevoFestivos.getDia().equals("")) {
            mensajeValidacion = " *Debe tener una fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int i = 0; i < listFestivosPorPais.size(); i++) {
                if (nuevoFestivos.getDia().equals(listFestivosPorPais.get(i).getDia())) {
                    fechas++;
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "Fechas repetidas ";
            } else {
                contador++;
            }
        }

        /*if (nuevoHvEntrevista.getTipo() == (null)) {
         mensajeValidacion = mensajeValidacion + " *Debe tener un tipo entrevista \n";
         System.out.println("Mensaje validacion : " + mensajeValidacion);
         } else {
         System.out.println("bandera");
         contador++;
         }*/
        System.out.println("contador " + contador);

        if (contador == 1) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarFestivosPorPais = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoFestivos.setSecuencia(l);
            System.err.println("---------------AGREGAR REGISTRO----------------");
            System.err.println("fecha " + nuevoFestivos.getDia());
            System.err.println("Pais " + nuevoFestivos.getPais().getNombre());
            System.err.println("-----------------------------------------------");

            crearFestivosPorPais.add(nuevoFestivos);
            listFestivosPorPais.add(nuevoFestivos);
            nuevoFestivos = new Festivos();
            nuevoFestivos.setPais(new Paises());
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

    public void limpiarNuevoFestivos() {
        System.out.println("limpiarNuevoFestivos");
        nuevoFestivos = new Festivos();
        nuevoFestivos.setPais(new Paises());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoFestivos() {
        if (index >= 0) {
            System.out.println("duplicandoFestivos");
            duplicarFestivos = new Festivos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarFestivos.setSecuencia(l);
                duplicarFestivos.setPais(listFestivosPorPais.get(index).getPais());
                duplicarFestivos.setDia(listFestivosPorPais.get(index).getDia());
            }
            if (tipoLista == 1) {
                duplicarFestivos.setSecuencia(l);
                duplicarFestivos.setPais(filtrarFestivosPorPais.get(index).getPais());
                duplicarFestivos.setDia(filtrarFestivosPorPais.get(index).getDia());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEvC");
            context.execute("duplicarRegistroEvalCompetencias.show()");
            //index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR HVENTREVISTAS");
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        int fechas = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarFestivos.getDia());

        if (duplicarFestivos.getDia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {

            for (int j = 0; j < listFestivosPorPais.size(); j++) {
                if (duplicarFestivos.getDia().equals(listFestivosPorPais.get(j).getDia())) {
                    System.out.println("Se repiten");
                    fechas++;
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "FECHAS REPETIDAS";
            } else {
                System.out.println("bandera");
                contador++;
            }

        }

        if (contador == 1) {

            System.out.println("Datos Duplicando: " + duplicarFestivos.getSecuencia() + "  " + duplicarFestivos.getDia());
            if (crearFestivosPorPais.contains(duplicarFestivos)) {
                System.out.println("Ya lo contengo.");
            }
            listFestivosPorPais.add(duplicarFestivos);
            crearFestivosPorPais.add(duplicarFestivos);
            context.update("form:datosHvEntrevista");
            index = -1;
            secRegistro = null;

            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("fecha " + duplicarFestivos.getDia());
            System.err.println("Pais " + duplicarFestivos.getPais().getNombre());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarFestivosPorPais = null;
                tipoLista = 0;
            }
            duplicarFestivos = new Festivos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else {
            contador = 0;
            fechas = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void limpiarDuplicarFestivos() {
        duplicarFestivos = new Festivos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VIGENCIASNORMASLABORALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VIGENCIASNORMASLABORALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listFestivosPorPais.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "FESTIVOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("FESTIVOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Festivos> getListFestivosPorPais() {
        if (listFestivosPorPais == null) {
            listFestivosPorPais = administrarFestivos.consultarFestivosPais(paisSeleccionado.getSecuencia());
        }
        return listFestivosPorPais;
    }

    public void setListFestivosPorPais(List<Festivos> listFestivosPorPais) {
        this.listFestivosPorPais = listFestivosPorPais;
    }

    public List<Festivos> getFiltrarFestivosPorPais() {
        return filtrarFestivosPorPais;
    }

    public void setFiltrarFestivosPorPais(List<Festivos> filtrarFestivosPorPais) {
        this.filtrarFestivosPorPais = filtrarFestivosPorPais;
    }

    public Festivos getNuevoFestivos() {
        return nuevoFestivos;
    }

    public void setNuevoFestivos(Festivos nuevoFestivos) {
        this.nuevoFestivos = nuevoFestivos;
    }

    public Festivos getDuplicarFestivos() {
        return duplicarFestivos;
    }

    public void setDuplicarFestivos(Festivos duplicarFestivos) {
        this.duplicarFestivos = duplicarFestivos;
    }

    public Festivos getEditarFestivos() {
        return editarFestivos;
    }

    public void setEditarFestivos(Festivos editarFestivos) {
        this.editarFestivos = editarFestivos;
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

    public List<Paises> getListPaises() {
        if (listPaises == null) {
            listPaises = administrarFestivos.consultarLOVPaises();
            paisSeleccionado = listPaises.get(0);
        }
        return listPaises;
    }

    public void setListPaises(List<Paises> listPaises) {
        this.listPaises = listPaises;
    }

    public Paises getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(Paises paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

    public List<Paises> getListPaisesFiltrar() {
        return listPaisesFiltrar;
    }

    public void setListPaisesFiltrar(List<Paises> listPaisesFiltrar) {
        this.listPaisesFiltrar = listPaisesFiltrar;
    }

}

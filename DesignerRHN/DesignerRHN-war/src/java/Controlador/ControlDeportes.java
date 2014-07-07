/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Deportes;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarDeportesInterface;
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
public class ControlDeportes implements Serializable {

    @EJB
    AdministrarDeportesInterface administrarDeportes;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<Deportes> listDeportes;
    private List<Deportes> filtrarDeportes;
    private List<Deportes> crearDeportes;
    private List<Deportes> modificarDeportes;
    private List<Deportes> borrarDeportes;
    private Deportes nuevoDeporte;
    private Deportes duplicarDeporte;
    private Deportes editarDeportes;
    private Deportes deporteSeleccionado;
    private int tamano;
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
    private Integer a;
    private Integer backUpCodigo;
    private String backUpDescripcion;
    private String infoRegistro;

    public ControlDeportes() {
        listDeportes = null;
        crearDeportes = new ArrayList<Deportes>();
        modificarDeportes = new ArrayList<Deportes>();
        borrarDeportes = new ArrayList<Deportes>();
        permitirIndex = true;
        editarDeportes = new Deportes();
        nuevoDeporte = new Deportes();
        duplicarDeporte = new Deportes();
        a = null;
        guardado = true;
        tamano = 270;

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarDeportes.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLDEPORTES.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarDeportes.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLDEPORTES eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    backUpCodigo = listDeportes.get(index).getCodigo();
                }
                if (cualCelda == 1) {
                    backUpDescripcion = listDeportes.get(index).getNombre();
                }
            } else {
                if (cualCelda == 0) {
                    backUpCodigo = filtrarDeportes.get(index).getCodigo();
                }
                if (cualCelda == 1) {
                    backUpDescripcion = filtrarDeportes.get(index).getNombre();
                }

                secRegistro = listDeportes.get(index).getSecuencia();

            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLDEPORTES.asignarIndex \n");
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
            System.out.println("ERROR CONTROLDEPORTES.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosDeporte:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosDeporte:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeporte");
            bandera = 0;
            filtrarDeportes = null;
            tipoLista = 0;
        }

        borrarDeportes.clear();
        crearDeportes.clear();
        modificarDeportes.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listDeportes = null;
        guardado = true;
        permitirIndex = true;
        getListDeportes();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listDeportes == null || listDeportes.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listDeportes.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosDeporte");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosDeporte:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosDeporte:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeporte");
            bandera = 0;
            filtrarDeportes = null;
            tipoLista = 0;
        }

        borrarDeportes.clear();
        crearDeportes.clear();
        modificarDeportes.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listDeportes = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDeporte");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosDeporte:codigo");
            codigo.setFilterStyle("width: 350px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosDeporte:descripcion");
            descripcion.setFilterStyle("width: 350px");
            RequestContext.getCurrentInstance().update("form:datosDeporte");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosDeporte:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosDeporte:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeporte");
            bandera = 0;
            filtrarDeportes = null;
            tipoLista = 0;
        }
    }

    public void modificarDeporte(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR DEPORTES");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR Deportes, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearDeportes.contains(listDeportes.get(indice))) {
                    if (listDeportes.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listDeportes.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listDeportes.size(); j++) {
                            if (j != indice) {
                                if (listDeportes.get(indice).getCodigo() == listDeportes.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            listDeportes.get(indice).setCodigo(backUpCodigo);
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listDeportes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listDeportes.get(indice).setNombre(backUpDescripcion);
                    }
                    if (listDeportes.get(indice).getNombre() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listDeportes.get(indice).setNombre(backUpDescripcion);
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarDeportes.isEmpty()) {
                            modificarDeportes.add(listDeportes.get(indice));
                        } else if (!modificarDeportes.contains(listDeportes.get(indice))) {
                            modificarDeportes.add(listDeportes.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (listDeportes.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listDeportes.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listDeportes.size(); j++) {
                            if (j != indice) {
                                if (listDeportes.get(indice).getCodigo() == listDeportes.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            listDeportes.get(indice).setCodigo(backUpCodigo);
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listDeportes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listDeportes.get(indice).setNombre(backUpDescripcion);
                    }
                    if (listDeportes.get(indice).getNombre() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listDeportes.get(indice).setNombre(backUpDescripcion);
                        banderita = false;
                    }

                    if (banderita == true) {

                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearDeportes.contains(filtrarDeportes.get(indice))) {
                    if (filtrarDeportes.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDeportes.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listDeportes.size(); j++) {
                            if (j != indice) {
                                if (filtrarDeportes.get(indice).getCodigo() == listDeportes.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarDeportes.size(); j++) {
                            if (j != indice) {
                                if (filtrarDeportes.get(indice).getCodigo() == filtrarDeportes.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarDeportes.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarDeportes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDeportes.get(indice).setNombre(backUpDescripcion);
                    }
                    if (filtrarDeportes.get(indice).getNombre() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDeportes.get(indice).setNombre(backUpDescripcion);
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarDeportes.isEmpty()) {
                            modificarDeportes.add(filtrarDeportes.get(indice));
                        } else if (!modificarDeportes.contains(filtrarDeportes.get(indice))) {
                            modificarDeportes.add(filtrarDeportes.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (filtrarDeportes.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDeportes.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listDeportes.size(); j++) {
                            if (j != indice) {
                                if (filtrarDeportes.get(indice).getCodigo() == listDeportes.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarDeportes.size(); j++) {
                            if (j != indice) {
                                if (filtrarDeportes.get(indice).getCodigo() == filtrarDeportes.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarDeportes.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarDeportes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDeportes.get(indice).setNombre(backUpDescripcion);
                    }
                    if (filtrarDeportes.get(indice).getNombre() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarDeportes.get(indice).setNombre(backUpDescripcion);
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosDeporte");
            context.update("form:ACEPTAR");
        }

    }

    public void borrarDeporte() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a BorrarDeporte");
                if (!modificarDeportes.isEmpty() && modificarDeportes.contains(listDeportes.get(index))) {
                    int modIndex = modificarDeportes.indexOf(listDeportes.get(index));
                    modificarDeportes.remove(modIndex);
                    borrarDeportes.add(listDeportes.get(index));
                } else if (!crearDeportes.isEmpty() && crearDeportes.contains(listDeportes.get(index))) {
                    int crearIndex = crearDeportes.indexOf(listDeportes.get(index));
                    crearDeportes.remove(crearIndex);
                } else {
                    borrarDeportes.add(listDeportes.get(index));
                }
                listDeportes.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("BorrarDeporte");
                if (!modificarDeportes.isEmpty() && modificarDeportes.contains(filtrarDeportes.get(index))) {
                    int modIndex = modificarDeportes.indexOf(filtrarDeportes.get(index));
                    modificarDeportes.remove(modIndex);
                    borrarDeportes.add(filtrarDeportes.get(index));
                } else if (!crearDeportes.isEmpty() && crearDeportes.contains(filtrarDeportes.get(index))) {
                    int crearIndex = crearDeportes.indexOf(filtrarDeportes.get(index));
                    crearDeportes.remove(crearIndex);
                } else {
                    borrarDeportes.add(filtrarDeportes.get(index));
                }
                int VCIndex = listDeportes.indexOf(filtrarDeportes.get(index));
                listDeportes.remove(VCIndex);
                filtrarDeportes.remove(index);

            }
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            infoRegistro = "Cantidad de registros: " + listDeportes.size();
            context.update("form:informacionRegistro");
        }
        context.update("form:datosDeporte");
        context.update("form:ACEPTAR");

    }

    private BigInteger verificarBorradoVigenciasDeportes;
    private BigInteger contadorDeportesPersonas;
    private BigInteger contadorParametrosInformes;

    public void verificarBorrado() {
        try {
            System.out.println("ESTOY EN VERIFICAR BORRADO tipoLista " + tipoLista);
            System.out.println("secuencia borrado : " + listDeportes.get(index).getSecuencia());
            if (tipoLista == 0) {
                System.out.println("secuencia borrado : " + listDeportes.get(index).getSecuencia());
                verificarBorradoVigenciasDeportes = administrarDeportes.contarVigenciasDeportesDeporte(listDeportes.get(index).getSecuencia());
                contadorDeportesPersonas = administrarDeportes.contarPersonasDeporte(listDeportes.get(index).getSecuencia());
                contadorParametrosInformes = administrarDeportes.contarParametrosInformesDeportes(listDeportes.get(index).getSecuencia());
            } else {
                System.out.println("secuencia borrado : " + filtrarDeportes.get(index).getSecuencia());
                verificarBorradoVigenciasDeportes = administrarDeportes.contarVigenciasDeportesDeporte(filtrarDeportes.get(index).getSecuencia());
                contadorDeportesPersonas = administrarDeportes.contarPersonasDeporte(filtrarDeportes.get(index).getSecuencia());
                contadorParametrosInformes = administrarDeportes.contarParametrosInformesDeportes(filtrarDeportes.get(index).getSecuencia());
            }
            if (!verificarBorradoVigenciasDeportes.equals(new BigInteger("0")) && !contadorDeportesPersonas.equals(new BigInteger("0")) && !contadorParametrosInformes.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarBorradoVigenciasDeportes = new BigInteger("-1");
                contadorDeportesPersonas = new BigInteger("-1");
                contadorParametrosInformes = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrarDeporte();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlDepotes verificarBorrado ERROR " + e);
        }
    }

    public void guardarDeportes() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Deportes");
            if (!borrarDeportes.isEmpty()) {
                administrarDeportes.borrarDeportes(borrarDeportes);
                //mostrarBorrados
                registrosBorrados = borrarDeportes.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarDeportes.clear();
            }
            if (!crearDeportes.isEmpty()) {
                administrarDeportes.crearDeportes(crearDeportes);
                crearDeportes.clear();
            }
            if (!modificarDeportes.isEmpty()) {
                administrarDeportes.modificarDeportes(modificarDeportes);
                modificarDeportes.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listDeportes = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosDeporte");
            k = 0;
        }
        index = -1;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarDeportes = listDeportes.get(index);
            }
            if (tipoLista == 1) {
                editarDeportes = filtrarDeportes.get(index);
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

    public void agregarNuevoDeportes() {
        System.out.println("agregarNuevoDeportes");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoDeporte.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoDeporte.getCodigo());

            for (int x = 0; x < listDeportes.size(); x++) {
                if (listDeportes.get(x).getCodigo() == nuevoDeporte.getCodigo()) {
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
        if (nuevoDeporte.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosDeporte:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosDeporte:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDeporte");
                bandera = 0;
                filtrarDeportes = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoDeporte.setSecuencia(l);

            crearDeportes.add(nuevoDeporte);

            listDeportes.add(nuevoDeporte);
            nuevoDeporte = new Deportes();
            infoRegistro = "Cantidad de registros: " + listDeportes.size();
            context.update("form:informacionRegistro");
            context.update("form:datosDeporte");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroDeporte.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoDeportes() {
        System.out.println("limpiarNuevoDeportes");
        nuevoDeporte = new Deportes();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarDeportes() {
        System.out.println("duplicarDeportes");
        if (index >= 0) {
            duplicarDeporte = new Deportes();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarDeporte.setSecuencia(l);
                duplicarDeporte.setCodigo(listDeportes.get(index).getCodigo());
                duplicarDeporte.setNombre(listDeportes.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarDeporte.setSecuencia(l);
                duplicarDeporte.setCodigo(filtrarDeportes.get(index).getCodigo());
                duplicarDeporte.setNombre(filtrarDeportes.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarDepor");
            context.execute("duplicarRegistroDeporte.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLDEPORTES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        System.err.println("ConfirmarDuplicar codigo " + duplicarDeporte.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarDeporte.getNombre());

        if (duplicarDeporte.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listDeportes.size(); x++) {
                if (listDeportes.get(x).getCodigo() == duplicarDeporte.getCodigo()) {
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
        if (duplicarDeporte.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarDeporte.getSecuencia() + "  " + duplicarDeporte.getCodigo());
            if (crearDeportes.contains(duplicarDeporte)) {
                System.out.println("Ya lo contengo.");
            }
            listDeportes.add(duplicarDeporte);
            crearDeportes.add(duplicarDeporte);
            context.update("form:datosDeporte");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosDeporte:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosDeporte:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDeporte");
                bandera = 0;
                filtrarDeportes = null;
                tipoLista = 0;
            }
            duplicarDeporte = new Deportes();
            infoRegistro = "Cantidad de registros: " + listDeportes.size();
            context.update("form:informacionRegistro");
            RequestContext.getCurrentInstance().execute("duplicarRegistroDeporte.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarDeportes() {
        duplicarDeporte = new Deportes();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosDeportesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "DEPORTES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosDeportesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "DEPORTES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listDeportes.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "DEPORTES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("DEPORTES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //------------------------------------------------------------------------------
    public List<Deportes> getListDeportes() {
        if (listDeportes == null) {
            listDeportes = administrarDeportes.consultarDeportes();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listDeportes == null || listDeportes.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listDeportes.size();
        }
        context.update("form:informacionRegistro");
        return listDeportes;
    }

    public void setListDeportes(List<Deportes> listDeportes) {
        this.listDeportes = listDeportes;
    }

    public List<Deportes> getFiltrarDeportes() {
        return filtrarDeportes;
    }

    public void setFiltrarDeportes(List<Deportes> filtrarDeportes) {
        this.filtrarDeportes = filtrarDeportes;
    }

    public Deportes getNuevoDeporte() {
        return nuevoDeporte;
    }

    public void setNuevoDeporte(Deportes nuevoDeporte) {
        this.nuevoDeporte = nuevoDeporte;
    }

    public Deportes getDuplicarDeporte() {
        return duplicarDeporte;
    }

    public void setDuplicarDeporte(Deportes duplicarDeporte) {
        this.duplicarDeporte = duplicarDeporte;
    }

    public Deportes getEditarDeportes() {
        return editarDeportes;
    }

    public void setEditarDeportes(Deportes editarDeportes) {
        this.editarDeportes = editarDeportes;
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

    public Deportes getDeporteSeleccionado() {
        return deporteSeleccionado;
    }

    public void setDeporteSeleccionado(Deportes deporteSeleccionado) {
        this.deporteSeleccionado = deporteSeleccionado;
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

}

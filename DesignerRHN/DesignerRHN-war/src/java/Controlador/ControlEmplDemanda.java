/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Demandas;
import Entidades.Empleados;
import Entidades.MotivosDemandas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplDemandaInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ControlEmplDemanda implements Serializable {

    
    @EJB
    AdministrarEmplDemandaInterface administrarEmplDemanda;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //VigenciasIndicadores
    private List<Demandas> listDemandasEmpleado;
    private List<Demandas> filtrarListDemandasEmpleado;
    //TiposIndicadores
    private List<MotivosDemandas> listMotivosDemandas;
    private MotivosDemandas motivoDemandaSeleccionado;
    private List<MotivosDemandas> filtrarListMotivosDemandas;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo VP Crtl + F11
    private int banderaD;
    //Columnas Tabla VP
    private Column dMotivo, dSeguimiento, dFecha;
    //Otros
    private boolean aceptar;
    //modificar
    private List<Demandas> listDemandaEmpleadoModificar;
    private boolean guardado;
    //crear VP
    public Demandas nuevaDemandaEmpleado;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<Demandas> listDemandaEmpleadoBorrar;
    //editar celda
    private Demandas editarDemandaEmpleado;
    //duplicar
    //Autocompletar
    private boolean permitirIndexD;
    //Variables Autompletar
    private String motivo;
    private int index;
    private int cualCelda, tipoLista;
    private Demandas duplicarDemandaEmpleado;
    private List<Demandas> listDemandaEmpleadoCrear;
    private boolean cambioDemanda;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaInic;
    private Empleados empleado;
    private Date fechaParametro;

    public ControlEmplDemanda() {
        empleado = new Empleados();
        backUpSecRegistro = null;
        tipoLista = 0;
        //Otros
        aceptar = true;
        listDemandaEmpleadoBorrar = new ArrayList<Demandas>();
        k = 0;
        listDemandaEmpleadoModificar = new ArrayList<Demandas>();
        editarDemandaEmpleado = new Demandas();
        tipoLista = 0;
        guardado = true;

        banderaD = 0;
        permitirIndexD = true;
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        listMotivosDemandas = null;
        nuevaDemandaEmpleado = new Demandas();
        nuevaDemandaEmpleado.setMotivo(new MotivosDemandas());
        listDemandaEmpleadoCrear = new ArrayList<Demandas>();
        cambioDemanda = false;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEmplDemanda.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger secuencia) {
        empleado = new Empleados();
        empleado = administrarEmplDemanda.actualEmpleado(secuencia);
        listDemandasEmpleado = null;
    }

    public boolean validarFechasRegistro(int i) {
        boolean retorno = true;
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        if (i == 0) {
            Demandas auxiliar = new Demandas();
            if (tipoLista == 0) {
                auxiliar = listDemandasEmpleado.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListDemandasEmpleado.get(index);
            }
            if (auxiliar.getFecha() != null) {
                if (auxiliar.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }

        }
        if (i == 1) {
            if (nuevaDemandaEmpleado.getFecha() != null) {
                if (nuevaDemandaEmpleado.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarDemandaEmpleado.getFecha() != null) {
                if (duplicarDemandaEmpleado.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        }

        return retorno;
    }

    public void modificacionesFechas(int i, int c) {
        if (validarFechasRegistro(0) == true) {
            cambiarIndiceD(i, c);
            modificarDemanda(i);
        } else {
            if (tipoLista == 0) {
                listDemandasEmpleado.get(index).setFecha(fechaInic);
            }
            if (tipoLista == 1) {
                filtrarListDemandasEmpleado.get(index).setFecha(fechaInic);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDemanda");
            context.execute("errorFechas.show()");
        }
    }

    public void modificarDemanda(int indice) {

        if (tipoLista == 0) {
            index = indice;
            if (!listDemandaEmpleadoCrear.contains(listDemandasEmpleado.get(indice))) {
                if (listDemandaEmpleadoModificar.isEmpty()) {
                    listDemandaEmpleadoModificar.add(listDemandasEmpleado.get(indice));
                } else if (!listDemandaEmpleadoModificar.contains(listDemandasEmpleado.get(indice))) {
                    listDemandaEmpleadoModificar.add(listDemandasEmpleado.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioDemanda = true;
            index = -1;
            secRegistro = null;
        } else {
            int ind = listDemandasEmpleado.indexOf(filtrarListDemandasEmpleado.get(indice));
            index = ind;

            if (!listDemandaEmpleadoCrear.contains(filtrarListDemandasEmpleado.get(indice))) {
                if (listDemandaEmpleadoModificar.isEmpty()) {
                    listDemandaEmpleadoModificar.add(filtrarListDemandasEmpleado.get(indice));
                } else if (!listDemandaEmpleadoModificar.contains(filtrarListDemandasEmpleado.get(indice))) {
                    listDemandaEmpleadoModificar.add(filtrarListDemandasEmpleado.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioDemanda = true;
            index = -1;
            secRegistro = null;

        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDemanda");

    }

    public void modificarDemanda(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVOS")) {
            if (tipoLista == 0) {
                listDemandasEmpleado.get(indice).getMotivo().setDescripcion(motivo);
            } else {
                filtrarListDemandasEmpleado.get(indice).getMotivo().setDescripcion(motivo);
            }
            for (int i = 0; i < listMotivosDemandas.size(); i++) {
                if (listMotivosDemandas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listDemandasEmpleado.get(indice).setMotivo(listMotivosDemandas.get(indiceUnicoElemento));
                } else {
                    filtrarListDemandasEmpleado.get(indice).setMotivo(listMotivosDemandas.get(indiceUnicoElemento));
                }
                listMotivosDemandas = null;
                getListMotivosDemandas();
                cambioDemanda = true;
            } else {
                permitirIndexD = false;
                context.update("form:DemandaDialogo");
                context.execute("DemandaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listDemandaEmpleadoCrear.contains(listDemandasEmpleado.get(indice))) {

                    if (listDemandaEmpleadoModificar.isEmpty()) {
                        listDemandaEmpleadoModificar.add(listDemandasEmpleado.get(indice));
                    } else if (!listDemandaEmpleadoModificar.contains(listDemandasEmpleado.get(indice))) {
                        listDemandaEmpleadoModificar.add(listDemandasEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioDemanda = true;
                index = -1;
                secRegistro = null;
            } else {
                if (!listDemandaEmpleadoCrear.contains(filtrarListDemandasEmpleado.get(indice))) {

                    if (listDemandaEmpleadoModificar.isEmpty()) {
                        listDemandaEmpleadoModificar.add(filtrarListDemandasEmpleado.get(indice));
                    } else if (!listDemandaEmpleadoModificar.contains(filtrarListDemandasEmpleado.get(indice))) {
                        listDemandaEmpleadoModificar.add(filtrarListDemandasEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioDemanda = true;
                index = -1;
                secRegistro = null;
            }
            cambioDemanda = true;
        }
        context.update("form:datosDemanda");
    }

    public void valoresBackupAutocompletarDemanda(int tipoNuevo, String Campo) {

        if (Campo.equals("MOTIVOS")) {
            if (tipoNuevo == 1) {
                motivo = nuevaDemandaEmpleado.getMotivo().getDescripcion();
            } else if (tipoNuevo == 2) {
                motivo = duplicarDemandaEmpleado.getMotivo().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoDemanda(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVOS")) {
            if (tipoNuevo == 1) {
                nuevaDemandaEmpleado.getMotivo().setDescripcion(motivo);
            } else if (tipoNuevo == 2) {
                duplicarDemandaEmpleado.getMotivo().setDescripcion(motivo);
            }
            for (int i = 0; i < listMotivosDemandas.size(); i++) {
                if (listMotivosDemandas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDemandaEmpleado.setMotivo(listMotivosDemandas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMotivo");
                } else if (tipoNuevo == 2) {
                    duplicarDemandaEmpleado.setMotivo(listMotivosDemandas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivo");
                }
                listMotivosDemandas = null;
                getListMotivosDemandas();
            } else {
                context.update("form:DemandaDialogo");
                context.execute("DemandaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaMotivo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivo");
                }
            }
        }
    }

    public void cambiarIndiceD(int indice, int celda) {
        if (permitirIndexD == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                fechaInic = listDemandasEmpleado.get(index).getFecha();
                secRegistro = listDemandasEmpleado.get(index).getSecuencia();
                if (cualCelda == 2) {
                    motivo = listDemandasEmpleado.get(index).getMotivo().getDescripcion();
                }
            }
            if (tipoLista == 1) {
                fechaInic = filtrarListDemandasEmpleado.get(index).getFecha();
                secRegistro = filtrarListDemandasEmpleado.get(index).getSecuencia();
                if (cualCelda == 2) {
                    motivo = filtrarListDemandasEmpleado.get(index).getMotivo().getDescripcion();
                }
            }
        }

    }

    public void guardadoGeneral() {
        guardarCambiosD();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    public void guardarCambiosD() {
        if (guardado == false) {
            if (!listDemandaEmpleadoBorrar.isEmpty()) {
                administrarEmplDemanda.borrarDemandas(listDemandaEmpleadoBorrar);
                listDemandaEmpleadoBorrar.clear();
            }
            if (!listDemandaEmpleadoCrear.isEmpty()) {
                administrarEmplDemanda.crearDemandas(listDemandaEmpleadoCrear);
                listDemandaEmpleadoCrear.clear();
            }
            if (!listDemandaEmpleadoModificar.isEmpty()) {
                administrarEmplDemanda.editarDemandas(listDemandaEmpleadoModificar);
                listDemandaEmpleadoModificar.clear();
            }
            listDemandasEmpleado = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDemanda");
            k = 0;
        }
        cambioDemanda = false;
        index = -1;
        secRegistro = null;
    }

    public void cancelarModificacionD() {
        if (banderaD == 1) {
            dMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dMotivo");
            dMotivo.setFilterStyle("display: none; visibility: hidden;");

            dSeguimiento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dSeguimiento");
            dSeguimiento.setFilterStyle("display: none; visibility: hidden;");

            dFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dFecha");
            dFecha.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosDemanda");
            banderaD = 0;
            filtrarListDemandasEmpleado = null;
            tipoLista = 0;
        }
        listMotivosDemandas = null;
        listDemandaEmpleadoBorrar.clear();
        listDemandaEmpleadoCrear.clear();
        listDemandaEmpleadoModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listDemandasEmpleado = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDemanda");
        cambioDemanda = false;
        nuevaDemandaEmpleado = new Demandas();
        nuevaDemandaEmpleado.setMotivo(new MotivosDemandas());
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarDemandaEmpleado = listDemandasEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarDemandaEmpleado = filtrarListDemandasEmpleado.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaDD");
                context.execute("editarFechaDD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarSeguimientoD");
                context.execute("editarSeguimientoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarMotivoD");
                context.execute("editarMotivoD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaD() {
        if (validarFechasRegistro(1)) {
            cambioDemanda = true;
            //CERRAR FILTRADO
            if (banderaD == 1) {
                dMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dMotivo");
                dMotivo.setFilterStyle("display: none; visibility: hidden;");

                dSeguimiento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dSeguimiento");
                dSeguimiento.setFilterStyle("display: none; visibility: hidden;");

                dFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dFecha");
                dFecha.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosDemanda");
                banderaD = 0;
                filtrarListDemandasEmpleado = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS
            k++;
            l = BigInteger.valueOf(k);
            nuevaDemandaEmpleado.setSecuencia(l);
            String aux = nuevaDemandaEmpleado.getSeguimiento().toUpperCase();
            nuevaDemandaEmpleado.setSeguimiento(aux);
            nuevaDemandaEmpleado.setEmpleado(empleado);
            if (listDemandasEmpleado == null) {
                listDemandasEmpleado = new ArrayList<Demandas>();
            }
            listDemandasEmpleado.add(nuevaDemandaEmpleado);
            listDemandaEmpleadoCrear.add(nuevaDemandaEmpleado);
            //
            nuevaDemandaEmpleado = new Demandas();
            nuevaDemandaEmpleado.setMotivo(new MotivosDemandas());
            limpiarNuevaD();
            //
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDemanda");
            context.execute("NuevoRegistroD.hide()");

        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("form:errorFechas.show()");
        }
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaD() {
        nuevaDemandaEmpleado = new Demandas();
        nuevaDemandaEmpleado.setMotivo(new MotivosDemandas());
        index = -1;
        secRegistro = null;

    }

    public void cancelarNuevaD() {
        nuevaDemandaEmpleado = new Demandas();
        nuevaDemandaEmpleado.setMotivo(new MotivosDemandas());
        index = -1;
        secRegistro = null;
        cambioDemanda = false;
    }

    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarDemanda() {
        if (index >= 0) {
            duplicarDemandaM();
        }
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarDemandaM() {
        if (index >= 0) {
            duplicarDemandaEmpleado = new Demandas();
            k++;
            BigDecimal var = BigDecimal.valueOf(k);
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {

                duplicarDemandaEmpleado.setSecuencia(l);
                duplicarDemandaEmpleado.setEmpleado(empleado);
                duplicarDemandaEmpleado.setMotivo(listDemandasEmpleado.get(index).getMotivo());
                duplicarDemandaEmpleado.setFecha(listDemandasEmpleado.get(index).getFecha());
                duplicarDemandaEmpleado.setSeguimiento(listDemandasEmpleado.get(index).getSeguimiento());
            }
            if (tipoLista == 1) {

                duplicarDemandaEmpleado.setSecuencia(l);
                duplicarDemandaEmpleado.setEmpleado(empleado);
                duplicarDemandaEmpleado.setMotivo(filtrarListDemandasEmpleado.get(index).getMotivo());
                duplicarDemandaEmpleado.setFecha(filtrarListDemandasEmpleado.get(index).getFecha());
                duplicarDemandaEmpleado.setSeguimiento(filtrarListDemandasEmpleado.get(index).getSeguimiento());

            }
            cambioDemanda = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarD");
            context.execute("DuplicarRegistroD.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicarD() {
        if (validarFechasRegistro(2)) {
            cambioDemanda = true;
            k++;
            l = BigInteger.valueOf(k);
            String aux = duplicarDemandaEmpleado.getSeguimiento().toUpperCase();
            duplicarDemandaEmpleado.setSeguimiento(aux);
            duplicarDemandaEmpleado.setEmpleado(empleado);
            duplicarDemandaEmpleado.setSecuencia(l);
            if (listDemandasEmpleado == null) {
                listDemandasEmpleado = new ArrayList<Demandas>();
            }
            listDemandasEmpleado.add(duplicarDemandaEmpleado);
            listDemandaEmpleadoCrear.add(duplicarDemandaEmpleado);
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (banderaD == 1) {
                //CERRAR FILTRADO
                dMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dMotivo");
                dMotivo.setFilterStyle("display: none; visibility: hidden;");

                dSeguimiento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dSeguimiento");
                dSeguimiento.setFilterStyle("display: none; visibility: hidden;");

                dFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dFecha");
                dFecha.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosDemanda");
                banderaD = 0;
                filtrarListDemandasEmpleado = null;
                tipoLista = 0;
            }
            duplicarDemandaEmpleado = new Demandas();
            limpiarduplicarD();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDemanda");
            context.execute("DuplicarRegistroD.hide()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("form:errorFechas.show()");
            System.out.println("Error fechas");
        }
    }

    public void limpiarduplicarD() {
        duplicarDemandaEmpleado = new Demandas();
        duplicarDemandaEmpleado.setMotivo(new MotivosDemandas());
    }

    public void cancelarDuplicadoD() {
        cambioDemanda = false;
        duplicarDemandaEmpleado = new Demandas();
        duplicarDemandaEmpleado.setMotivo(new MotivosDemandas());
        index = -1;
        secRegistro = null;
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoDemanda() {
        if (index >= 0) {
            borrarD();
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarD() {
        cambioDemanda = true;
        if (tipoLista == 0) {
            if (!listDemandaEmpleadoModificar.isEmpty() && listDemandaEmpleadoModificar.contains(listDemandasEmpleado.get(index))) {
                int modIndex = listDemandaEmpleadoModificar.indexOf(listDemandasEmpleado.get(index));
                listDemandaEmpleadoModificar.remove(modIndex);
                listDemandaEmpleadoBorrar.add(listDemandasEmpleado.get(index));
            } else if (!listDemandaEmpleadoCrear.isEmpty() && listDemandaEmpleadoCrear.contains(listDemandasEmpleado.get(index))) {
                int crearIndex = listDemandaEmpleadoCrear.indexOf(listDemandasEmpleado.get(index));
                listDemandaEmpleadoCrear.remove(crearIndex);
            } else {
                listDemandaEmpleadoBorrar.add(listDemandasEmpleado.get(index));
            }
            listDemandasEmpleado.remove(index);
        }
        if (tipoLista == 1) {
            if (!listDemandaEmpleadoModificar.isEmpty() && listDemandaEmpleadoModificar.contains(filtrarListDemandasEmpleado.get(index))) {
                int modIndex = listDemandaEmpleadoModificar.indexOf(filtrarListDemandasEmpleado.get(index));
                listDemandaEmpleadoModificar.remove(modIndex);
                listDemandaEmpleadoBorrar.add(filtrarListDemandasEmpleado.get(index));
            } else if (!listDemandaEmpleadoCrear.isEmpty() && listDemandaEmpleadoCrear.contains(filtrarListDemandasEmpleado.get(index))) {
                int crearIndex = listDemandaEmpleadoCrear.indexOf(filtrarListDemandasEmpleado.get(index));
                listDemandaEmpleadoCrear.remove(crearIndex);
            } else {
                listDemandaEmpleadoBorrar.add(filtrarListDemandasEmpleado.get(index));
            }
            int VPIndex = listDemandasEmpleado.indexOf(filtrarListDemandasEmpleado.get(index));
            listDemandasEmpleado.remove(VPIndex);
            filtrarListDemandasEmpleado.remove(index);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDemanda");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
        }

    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        filtradoDemanda();
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoDemanda() {
        if (banderaD == 0) {
            dMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dMotivo");
            dMotivo.setFilterStyle("width: 40px");
            dSeguimiento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dSeguimiento");
            dSeguimiento.setFilterStyle("width: 90px");
            dFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dFecha");
            dFecha.setFilterStyle("width: 90px");
            ///
            RequestContext.getCurrentInstance().update("form:datosDemanda");
            tipoLista = 1;
            banderaD = 1;
        } else if (banderaD == 1) {
            dMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dMotivo");
            dMotivo.setFilterStyle("display: none; visibility: hidden;");

            dSeguimiento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dSeguimiento");
            dSeguimiento.setFilterStyle("display: none; visibility: hidden;");

            dFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dFecha");
            dFecha.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosDemanda");
            banderaD = 0;
            filtrarListDemandasEmpleado = null;
            tipoLista = 0;
        }

    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaD == 1) {
            dMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dMotivo");
            dMotivo.setFilterStyle("display: none; visibility: hidden;");

            dSeguimiento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dSeguimiento");
            dSeguimiento.setFilterStyle("display: none; visibility: hidden;");

            dFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDemanda:dFecha");
            dFecha.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosDemanda");
            banderaD = 0;
            filtrarListDemandasEmpleado = null;
            tipoLista = 0;
        }
        listDemandaEmpleadoBorrar.clear();
        listDemandaEmpleadoCrear.clear();
        listDemandaEmpleadoModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listDemandasEmpleado = null;
        guardado = true;
        cambioDemanda = false;
        tipoActualizacion = -1;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            index = indice;
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("form:DemandaDialogo");
            context.execute("DemandaDialogo.show()");
        }
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarMotivo() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listDemandasEmpleado.get(index).setMotivo(motivoDemandaSeleccionado);
                if (!listDemandaEmpleadoCrear.contains(listDemandasEmpleado.get(index))) {
                    if (listDemandaEmpleadoModificar.isEmpty()) {
                        listDemandaEmpleadoModificar.add(listDemandasEmpleado.get(index));
                    } else if (!listDemandaEmpleadoModificar.contains(listDemandasEmpleado.get(index))) {
                        listDemandaEmpleadoModificar.add(listDemandasEmpleado.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambioDemanda = true;
                permitirIndexD = true;

            } else {
                filtrarListDemandasEmpleado.get(index).setMotivo(motivoDemandaSeleccionado);
                if (!listDemandaEmpleadoCrear.contains(filtrarListDemandasEmpleado.get(index))) {
                    if (listDemandaEmpleadoModificar.isEmpty()) {
                        listDemandaEmpleadoModificar.add(filtrarListDemandasEmpleado.get(index));
                    } else if (!listDemandaEmpleadoModificar.contains(filtrarListDemandasEmpleado.get(index))) {
                        listDemandaEmpleadoModificar.add(filtrarListDemandasEmpleado.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambioDemanda = true;
                permitirIndexD = true;

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:datosDemanda");
        } else if (tipoActualizacion == 1) {
            nuevaDemandaEmpleado.setMotivo(motivoDemandaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaMotivo");
        } else if (tipoActualizacion == 2) {
            duplicarDemandaEmpleado.setMotivo(motivoDemandaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivo");
        }
        filtrarListMotivosDemandas = null;
        motivoDemandaSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioMotivo() {
        filtrarListMotivosDemandas = null;
        motivoDemandaSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexD = true;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 2) {
                context.update("form:DemandaDialogo");
                context.execute("DemandaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDF_D();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_D() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarD:datosDemandaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "DemandasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        exportXLS_D();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_D() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarD:datosDemandaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "DemandasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listDemandasEmpleado != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "DEMANDAS");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
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
            if (administrarRastros.verificarHistoricosTabla("DEMANDAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GET - SET 
    public List<Demandas> getListDemandasEmpleado() {
        try {
            if (listDemandasEmpleado == null) {
                listDemandasEmpleado = new ArrayList<Demandas>();
                listDemandasEmpleado = administrarEmplDemanda.listDemandasEmpleadoSecuencia(empleado.getSecuencia());
                for (int i = 0; i < listDemandasEmpleado.size(); i++) {
                    if (listDemandasEmpleado.get(i).getMotivo() == null) {
                        listDemandasEmpleado.get(i).setMotivo(new MotivosDemandas());
                    }
                    String aux = listDemandasEmpleado.get(i).getSeguimiento().toUpperCase();
                    listDemandasEmpleado.get(i).setSeguimiento(aux);
                }
            }
            return listDemandasEmpleado;
        } catch (Exception e) {
            System.out.println("Error en getListDemandasEmpleado : " + e.toString());
            return null;
        }
    }

    public void setListDemandasEmpleado(List<Demandas> setListDemandasEmpleado) {
        this.listDemandasEmpleado = setListDemandasEmpleado;
    }

    public List<Demandas> getFiltrarListDemandasEmpleado() {
        return filtrarListDemandasEmpleado;
    }

    public void setFiltrarListDemandasEmpleado(List<Demandas> setFiltrarListDemandasEmpleado) {
        this.filtrarListDemandasEmpleado = setFiltrarListDemandasEmpleado;
    }

    public List<MotivosDemandas> getListMotivosDemandas() {
        try {
            if (listMotivosDemandas == null) {
                listMotivosDemandas = new ArrayList<MotivosDemandas>();
                listMotivosDemandas = administrarEmplDemanda.listMotivosDemandas();
            }
            return listMotivosDemandas;
        } catch (Exception e) {
            System.out.println("Error getListEmpresas " + e.toString());
            return null;
        }
    }

    public void setListMotivosDemandas(List<MotivosDemandas> setListMotivosDemandas) {
        this.listMotivosDemandas = setListMotivosDemandas;
    }

    public MotivosDemandas getMotivoDemandaSeleccionado() {
        return motivoDemandaSeleccionado;
    }

    public void setMotivoDemandaSeleccionado(MotivosDemandas setMotivoDemandaSeleccionado) {
        this.motivoDemandaSeleccionado = setMotivoDemandaSeleccionado;
    }

    public List<MotivosDemandas> getFiltrarListMotivosDemandas() {
        return filtrarListMotivosDemandas;
    }

    public void setFiltrarListMotivosDemandas(List<MotivosDemandas> setFiltrarListMotivosDemandas) {
        this.filtrarListMotivosDemandas = setFiltrarListMotivosDemandas;
    }

    public List<Demandas> getListDemandaEmpleadoModificar() {
        return listDemandaEmpleadoModificar;
    }

    public void setListDemandaEmpleadoModificar(List<Demandas> setListDemandaEmpleadoModificar) {
        this.listDemandaEmpleadoModificar = setListDemandaEmpleadoModificar;
    }

    public Demandas getNuevaDemandaEmpleado() {
        return nuevaDemandaEmpleado;
    }

    public void setNuevaDemandaEmpleado(Demandas setNuevaDemandaEmpleado) {
        this.nuevaDemandaEmpleado = setNuevaDemandaEmpleado;
    }

    public List<Demandas> getListDemandaEmpleadoBorrar() {
        return listDemandaEmpleadoBorrar;
    }

    public void getListDemandaEmpleadoBorrar(List<Demandas> getListDemandaEmpleadoBorrar) {
        this.listDemandaEmpleadoBorrar = getListDemandaEmpleadoBorrar;
    }

    public Demandas getEditarDemandaEmpleado() {
        return editarDemandaEmpleado;
    }

    public void setEditarDemandaEmpleado(Demandas setEditarDemandaEmpleado) {
        this.editarDemandaEmpleado = setEditarDemandaEmpleado;
    }

    public Demandas getDuplicarDemandaEmpleado() {
        return duplicarDemandaEmpleado;
    }

    public void setDuplicarDemandaEmpleado(Demandas setDuplicarDemandaEmpleado) {
        this.duplicarDemandaEmpleado = setDuplicarDemandaEmpleado;
    }

    public List<Demandas> getListDemandaEmpleadoCrear() {
        return listDemandaEmpleadoCrear;
    }

    public void setListDemandaEmpleadoCrear(List<Demandas> setListDemandaEmpleadoCrear) {
        this.listDemandaEmpleadoCrear = setListDemandaEmpleadoCrear;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public Empleados getEmpleado() {
        return empleado;
    }
}

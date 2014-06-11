/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.MotivosDefinitivas;
import Entidades.NovedadesSistema;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNovedadesSistemaInterface;
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
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlNovedadesDefinitivas implements Serializable {

    @EJB
    AdministrarNovedadesSistemaInterface administrarNovedadesSistema;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //SECUENCIA DEL Empleado
    private BigInteger secuenciaEmpleado;
    //LISTA NOVEDADES
    private List<NovedadesSistema> listaNovedades;
    private NovedadesSistema mostrar;
    private NovedadesSistema mostrarBorrar;
    private NovedadesSistema mostrarCrear;
    private List<NovedadesSistema> filtradosListaNovedades;
    //Consultas sobre novedad
    private NovedadesSistema novedadParametro;
    //LISTA DE ARRIBA EMPLEADOS
    private List<Empleados> listaEmpleadosNovedad;
    private List<Empleados> filtradosListaEmpleadosNovedad;
    private Empleados seleccionMostrar; //Seleccion Mostrar
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //editar celda
    private NovedadesSistema editarNovedades;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //Crear Novedades
    private List<NovedadesSistema> listaNovedadesCrear;
    public NovedadesSistema nuevaNovedad;
    public NovedadesSistema duplicarNovedad;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Novedades
    private List<NovedadesSistema> listaNovedadesModificar;
    //Borrar Novedades
    private List<NovedadesSistema> listaNovedadesBorrar;
    //L.O.V Empleados
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradoslistaEmpleados;
    private Empleados seleccionEmpleados;
    //L.O.V MOTIVOS
    private List<MotivosDefinitivas> listaMotivos;
    private List<MotivosDefinitivas> filtradosListaMotivos;
    private MotivosDefinitivas seleccionMotivos;
    //L.O.V RETIROS
    private List<MotivosRetiros> listaRetiros;
    private List<MotivosRetiros> filtradosListaRetiros;
    private MotivosRetiros seleccionRetiros;
    //AUTOCOMPLETAR
    private String motivoDefinitiva, motivoRetiro;
    private String celda;
    //Desactivar Campos
    private Boolean activate;

    public ControlNovedadesDefinitivas() {
        permitirIndex = true;
        listaEmpleados = null;
        listaEmpleadosNovedad = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaNovedades = null;
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setMotivodefinitiva(new MotivosDefinitivas());
        nuevaNovedad.setMotivoretiro(new MotivosRetiros());
        nuevaNovedad.setDias(BigInteger.valueOf(0));
        nuevaNovedad.setTipo(" ");
        nuevaNovedad.setSubtipo(" ");
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarNovedadesSistema.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void asignarIndex(int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:motivosDialogo");
            context.execute("motivosDialogo.show()");
        } else if (dlg == 2) {
            context.update("formularioDialogos:retirosDialogo");
            context.execute("retirosDialogo.show()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        listaEmpleadosNovedad = null;
        listaNovedades = null;
        getListaEmpleadosNovedad();
        //ACTUALIZAR CADA COMPONENTE
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");
        System.out.println("valor mostrar: " + mostrar);
        filtradosListaEmpleadosNovedad = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
    }

//Ubicacion Celda Arriba 
    public void cambiarEmpleado() {
        secuenciaEmpleado = seleccionMostrar.getSecuencia();
        listaNovedades = null;
        getListaNovedades();
        if (listaNovedades.isEmpty()) {
            mostrar = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();//ACTUALIZAR CADA 
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");
        getMostrar();
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");
    }

    public void valoresBackupAutocompletar(String Campo) {
        if (Campo.equals("MOTIVO")) {
            motivoDefinitiva = mostrar.getMotivodefinitiva().getNombre();
        } else if (Campo.equals("RETIRO")) {
            motivoRetiro = mostrar.getMotivoretiro().getNombre();
        }
    }

    public void autocompletar(String confirmarCambio, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;

        System.out.println("Valor: " + valorConfirmar);
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            mostrar.getMotivodefinitiva().setNombre(motivoDefinitiva);

            for (int i = 0; i < listaMotivos.size(); i++) {
                if (listaMotivos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                mostrar.setMotivodefinitiva(listaMotivos.get(indiceUnicoElemento));
                context.update("form:formularioNovedades:motivoLiquidacion");
                listaMotivos.clear();
                getListaMotivos();
            } else {
                context.update("formularioDialogos:motivosDialogo");
                context.execute("motivosDialogo.show()");
                context.update("form:formularioNovedades:motivoLiquidacion");
            }
        } else if (confirmarCambio.equalsIgnoreCase("RETIRO")) {
            mostrar.getMotivoretiro().setNombre(motivoRetiro);
            for (int i = 0; i < listaRetiros.size(); i++) {
                if (listaRetiros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                mostrar.setMotivoretiro(listaRetiros.get(indiceUnicoElemento));
                context.update("form:formularioNovedades:motivoRetiro");
                listaRetiros.clear();
                getListaRetiros();
            } else {
                context.update("formularioDialogos:retirosDialogo");
                context.execute("retirosDialogo.show()");
                context.update("form:formularioNovedades:motivoRetiro");
            }
        }
    }

    public void cambiosCampos() {
        guardado = false;
    }

    public void celda(String Campo) {
        secRegistro = mostrar.getSecuencia();
        if (Campo.equals("MOTIVO")) {
            cualCelda = 0;
        } else if (Campo.equals("RETIRO")) {
            cualCelda = 1;
        } else if (Campo.equals("FECHA")) {
            cualCelda = 2;
        } else if (Campo.equals("OBSERVACION")) {
            cualCelda = 3;
        }
        System.out.println("Cual celda : " + cualCelda);
    }

    public void editarCelda() {
        System.out.println("CualCelda =" + cualCelda);
        RequestContext context = RequestContext.getCurrentInstance();

        if (cualCelda == 0) {
            System.out.println("Motivos");
            context.update("formularioDialogos:editarMotivos");
            context.execute("editarMotivos.show()");
            cualCelda = -1;
        } else if (cualCelda == 1) {
            System.out.println("Retiros");
            context.update("formularioDialogos:editarRetiros");
            context.execute("editarRetiros.show()");
            cualCelda = -1;
        } else if (cualCelda == 2) {
            System.out.println("Fechas");
            context.update("formularioDialogos:editarFechas");
            context.execute("editarFechas.show()");
            cualCelda = -1;
        } else if (cualCelda == 3) {
            System.out.println("Observaciones");
            context.update("formularioDialogos:editarObservaciones");
            context.execute("editarObservaciones.show()");
            cualCelda = -1;
        }
        secRegistro = null;
    }

    public void actualizarEmpleadosNovedad() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad.add(seleccionEmpleados);
            seleccionMostrar = listaEmpleadosNovedad.get(0);
            context.execute("empleadosDialogo.hide()");
            context.reset("formularioDialogos:LOVEmpleados:globalFilter");
            context.update("formularioDialogos:LOVEmpleados");
            context.update("form:datosEmpleados");
            context.update("form:formularioNovedades");
        } else {
            listaEmpleadosNovedad.add(seleccionEmpleados);
            seleccionMostrar = listaEmpleadosNovedad.get(0);
            context.execute("empleadosDialogo.hide()");
            context.reset("formularioDialogos:LOVEmpleados:globalFilter");
            context.update("formularioDialogos:LOVEmpleados");
            context.update("form:datosEmpleados");
            context.update("form:formularioNovedades");
        }

        secuenciaEmpleado = listaEmpleadosNovedad.get(0).getSecuencia();
        listaNovedades = null;
        getListaNovedades();
        getMostrar();

        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");

        listaEmpleados = null;
        filtradosListaEmpleadosNovedad = null;
        seleccionEmpleados = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    public void listaValoresBoton() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (cualCelda == 0) {
            context.update("formularioDialogos:motivosDialogo");
            context.execute("motivosDialogo.show()");
            tipoActualizacion = 0;
        } else if (cualCelda == 1) {
            context.update("formularioDialogos:retirosDialogo");
            context.execute("retirosDialogo.show()");
            tipoActualizacion = 0;
        }
    }

    public void limpiarNuevaNovedad() {
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setMotivodefinitiva(new MotivosDefinitivas());
        nuevaNovedad.setMotivoretiro(new MotivosRetiros());
        index = -1;
        secRegistro = null;
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:novedadesEmpleadosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NovedadesDefinitivasEmpleadosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:novedadesEmpleadosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NovedadesDefinitivasEmpleadosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void actualizarMotivo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {

            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            mostrar.setMotivodefinitiva(seleccionMotivos);
            context.update("form:motivoLiquidacion");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setMotivodefinitiva(seleccionMotivos);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setMotivodefinitiva(seleccionMotivos);
            context.update("formularioDialogos:duplicarNovedad");
        }

        seleccionMotivos = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
        context.execute("motivosDialogo.hide()");
        context.reset("formularioDialogos:LOVMotivos:globalFilter");
        context.update("formularioDialogos:LOVMotivos");
    }

    public void Bool() {
        mostrar.getIndemnizaBool();
    }

    //CREAR NOVEDADES
    public void agregarNuevaNovedad() {
        getListaNovedades();
        int pasa = 0;
        int pasa2 = 0;
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();


        if (!listaNovedades.isEmpty()) {
            for (int i = 0; i < listaNovedades.size(); i++) {
                if (nuevaNovedad.getFechainicialdisfrute() != null && (nuevaNovedad.getFechainicialdisfrute().equals(listaNovedades.get(i).getFechainicialdisfrute()))) {
                    context.update("formularioDialogos:fechaRepetida");
                    context.execute("fechaRepetida.show()");
                    pasa2++;
                }
            }
        }

        if (nuevaNovedad.getFechainicialdisfrute() == null) {
            System.out.println("Entro a Fecha ");
            mensajeValidacion = mensajeValidacion + " * Fecha Liquidacion Definitiva\n";
            pasa++;
        }

        if (nuevaNovedad.getMotivodefinitiva().getNombre().equals(" ")) {
            System.out.println("Entro a Motivo");
            mensajeValidacion = mensajeValidacion + " * Motivo Liquidacion Definitiva\n";
            pasa++;
        }
        if (nuevaNovedad.getMotivoretiro().getNombre().equals(" ")) {
            System.out.println("Entro a Retiro");
            mensajeValidacion = mensajeValidacion + " * Motivo Retiro\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);

        if (pasa != 0) {
            System.out.println("HEY");
            context.update("formularioDialogos:validacionNuevaNovedad");
            context.execute("validacionNuevaNovedad.show()");
        }

        if (pasa == 0 && !listaNovedades.isEmpty()) {
            context.update("formularioDialogos:existeRegistro");
            context.execute("existeRegistro.show()");
        }

        if (pasa == 0 && pasa2 == 0) {
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .

            k++;
            l = BigInteger.valueOf(k);
            nuevaNovedad.setSecuencia(l);
            nuevaNovedad.setEmpleado(seleccionMostrar);
            nuevaNovedad.setTipo("DEFINITIVA");
            nuevaNovedad.setSubtipo("DINERO");
            nuevaNovedad.setDias(BigInteger.valueOf(0));
            listaNovedades.add(nuevaNovedad);
            mostrarCrear = nuevaNovedad;
            mostrarCrear.setTipo("DEFINITIVA");
            mostrarCrear.setSubtipo("DINERO");
            mostrarCrear.setDias(BigInteger.valueOf(0));
            mostrar.setFechainicialdisfrute(nuevaNovedad.getFechainicialdisfrute());
            mostrar.setMotivoretiro(nuevaNovedad.getMotivoretiro());
            mostrar.setMotivodefinitiva(nuevaNovedad.getMotivodefinitiva());
            mostrar.setObservaciones(nuevaNovedad.getObservaciones());
            if (mostrar != null) {
                activate = false;
            }
            context.update("form:formularioNovedades");


            nuevaNovedad = new NovedadesSistema();
            nuevaNovedad.setMotivodefinitiva(new MotivosDefinitivas());
            nuevaNovedad.setMotivoretiro(new MotivosRetiros());
            nuevaNovedad.setDias(BigInteger.valueOf(0));
            nuevaNovedad.setTipo("DEFINITIVA");
            nuevaNovedad.setSubtipo("DINERO");

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevaNovedadEmpleado.hide()");

            secRegistro = null;
        } else {
        }
    }
    
    public void save(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();  
        context.addMessage(null, new FacesMessage("Guardado Exitoso","Los cambios han sido guardados con éxito"));
    }  

    //GUARDAR
    public void guardarCambiosNovedades() {
        FacesContext context2 = FacesContext.getCurrentInstance();
        if (guardado == false) {
            System.out.println("Realizando Operaciones Ausentismos" + mostrarBorrar);

            if (mostrarBorrar != null) {
                System.out.println("Entra 0");
                administrarNovedadesSistema.borrarNovedades(mostrarBorrar);
                System.out.println("Borrar");
                mostrarBorrar = null;
            }
            //CREAR
            if (mostrarCrear != null) {
                administrarNovedadesSistema.crearNovedades(mostrarCrear);
                System.out.println("Crear");
                mostrarCrear = null;
            }
            //MODIFICAR
            if (mostrar != null && mostrarCrear == null && mostrarBorrar == null) {
                mostrar.setEmpleado(seleccionMostrar);
                mostrar.setTipo("DEFINITIVA");
                mostrar.setSubtipo("DINERO");
                administrarNovedadesSistema.modificarNovedades(mostrar);
                System.out.println("Modificar");
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context2.addMessage(null, new FacesMessage("Guardado Exitoso", "Los cambios han sido guardados exitosamente"));
            context.update("form:growl");
            
            System.out.println("Se guardaron los datos con exito");
            listaNovedades = null;
            
            context.update("form:datosEmpleados");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        secRegistro = null;
    }

    //BORRAR Novedades
    public void borrarNovedades() {
        getListaNovedades();
        if (listaNovedades != null && !listaNovedades.isEmpty()) {
            mostrarBorrar = listaNovedades.get(0);
            System.out.println("Iguales");
            listaNovedades.remove(listaNovedades.get(0));
        }
        System.out.println("Realizado");
        getMostrar();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:formularioNovedades");
        secRegistro = null;

        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }

    }

    //DUPLICAR NOVEDAD
    public void duplicarN() {

        duplicarNovedad = new NovedadesSistema();
        getMostrar();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoLista == 0) {
            duplicarNovedad.setSecuencia(l);
            duplicarNovedad.setEmpleado(seleccionMostrar);
            duplicarNovedad.setTipo(mostrar.getTipo());
            duplicarNovedad.setSubtipo(mostrar.getSubtipo());
            duplicarNovedad.setDias(mostrar.getDias());
            duplicarNovedad.setFechainicialdisfrute(mostrar.getFechainicialdisfrute());
            duplicarNovedad.setMotivodefinitiva(mostrar.getMotivodefinitiva());
            duplicarNovedad.setMotivoretiro(mostrar.getMotivoretiro());
            duplicarNovedad.setObservaciones(mostrar.getObservaciones());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:duplicarNovedad");
        context.execute("DuplicarNovedadEmpleado.show()");

        secRegistro = null;
    }

    public void confirmarDuplicar() {
        int pasa = 0;
        int pasa2 = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        
        if (!listaNovedades.isEmpty()) {
            for (int i = 0; i < listaNovedades.size(); i++) {
                if (nuevaNovedad.getFechainicialdisfrute() != null && (nuevaNovedad.getFechainicialdisfrute().equals(listaNovedades.get(i).getFechainicialdisfrute()))) {
                    context.update("formularioDialogos:fechaRepetida");
                    context.execute("fechaRepetida.show()");
                    pasa2++;
                }
            }
        }

        if (duplicarNovedad.getFechainicialdisfrute() == null) {
            System.out.println("Entro a Fecha ");
            mensajeValidacion = mensajeValidacion + " * Fecha Liquidacion Definitiva\n";
            pasa++;
        }

        if (duplicarNovedad.getMotivodefinitiva().getNombre().equals(" ")) {
            System.out.println("Entro a Motivo");
            mensajeValidacion = mensajeValidacion + " * Motivo Liquidacion Definitiva\n";
            pasa++;
        }
        if (duplicarNovedad.getMotivoretiro().getNombre().equals(" ")) {
            System.out.println("Entro a Retiro");
            mensajeValidacion = mensajeValidacion + " * Motivo Retiro\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedad");
            context.execute("validacionNuevaNovedad.show()");
        }



        if (pasa == 0 && pasa2 == 0) {
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            duplicarNovedad.setSecuencia(l);
            duplicarNovedad.setEmpleado(seleccionMostrar);
            duplicarNovedad.setTipo("DEFINITIVA");
            duplicarNovedad.setSubtipo("DINERO");
            listaNovedades.add(duplicarNovedad);

            mostrar.setFechainicialdisfrute(duplicarNovedad.getFechainicialdisfrute());
            mostrar.setMotivoretiro(duplicarNovedad.getMotivoretiro());
            mostrar.setMotivodefinitiva(duplicarNovedad.getMotivodefinitiva());
            mostrar.setObservaciones(duplicarNovedad.getObservaciones());
            mostrar.setIndemnizaBool(duplicarNovedad.getIndemnizaBool());

            duplicarNovedad = new NovedadesSistema();
            duplicarNovedad.setMotivodefinitiva(new MotivosDefinitivas());
            duplicarNovedad.setMotivoretiro(new MotivosRetiros());
            duplicarNovedad.setDias(BigInteger.valueOf(0));
            duplicarNovedad.setTipo("DEFINITIVA");
            duplicarNovedad.setSubtipo("DINERO");
            context.update("form:formularioNovedades");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("DuplicarNovedadEmpleado.hide()");
            secRegistro = null;
        } else {
        }
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Novedad
     */
    public void limpiarduplicarNovedad() {
        duplicarNovedad = new NovedadesSistema();
        duplicarNovedad.setTipo("DEFINITIVA");
        duplicarNovedad.setSubtipo("DINERO");
        duplicarNovedad.setMotivodefinitiva(new MotivosDefinitivas());
        duplicarNovedad.setMotivoretiro(new MotivosRetiros());
        secRegistro = null;
    }

    public void actualizarRetiros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {

            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            mostrar.setMotivoretiro(seleccionRetiros);
            context.update("form:motivoRetiro");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setMotivoretiro(seleccionRetiros);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setMotivoretiro(seleccionRetiros);
            context.update("formularioDialogos:duplicarNovedad");

        }

        seleccionRetiros = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("retirosDialogo.hide()");
        context.reset("formularioDialogos:LOVRetiros:globalFilter");
        context.update("formularioDialogos:LOVRetiros");
    }

    public void cancelarCambioMotivos() {
        seleccionMotivos = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void cancelarCambioRetiros() {
        seleccionRetiros = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void cancelarCambioEmpleados() {
        filtradoslistaEmpleados = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaNovedades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int result = administrarRastros.obtenerTabla(secRegistro, "NOVEDADESSISTEMA");
                System.out.println("resultado: " + result);
                if (result == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (result == 2) {
                    context.execute("confirmarRastro.show()");
                } else if (result == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (result == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (result == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("NOVEDADESSISTEMA")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //SALIR
    public void salir() {
        secRegistro = null;
        listaNovedades = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:formularioNovedades");
    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        cualCelda = -1;
        secRegistro = null;
        listaNovedades = null;
        getListaNovedades();
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:formularioNovedades");
    }

    //GETTERS & SETTERS
    public List<NovedadesSistema> getListaNovedades() {
        if (listaNovedades == null) {
            listaNovedades = administrarNovedadesSistema.novedadesEmpleado(secuenciaEmpleado);
        }
        if (listaNovedades.isEmpty()) {
            activate = true;
            mostrar = new NovedadesSistema();
        } else {
            activate = false;
            getMostrar();
        }
        return listaNovedades;
    }

    public void setListaNovedades(List<NovedadesSistema> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public List<NovedadesSistema> getFiltradosListaNovedades() {
        return filtradosListaNovedades;
    }

    public void setFiltradosListaNovedades(List<NovedadesSistema> filtradosListaNovedades) {
        this.filtradosListaNovedades = filtradosListaNovedades;
    }

    public List<Empleados> getListaEmpleadosNovedad() {
        if (listaEmpleadosNovedad == null) {
            listaEmpleadosNovedad = administrarNovedadesSistema.buscarEmpleados();
            if (listaEmpleadosNovedad != null && !listaEmpleadosNovedad.isEmpty()) {
                seleccionMostrar = listaEmpleadosNovedad.get(0);
                secuenciaEmpleado = seleccionMostrar.getSecuencia();
                getListaNovedades();
                getMostrar();
            }
        }
        return listaEmpleadosNovedad;
    }

    public void setListaEmpleadosNovedad(List<Empleados> listaEmpleadosNovedad) {
        this.listaEmpleadosNovedad = listaEmpleadosNovedad;
    }

    public List<Empleados> getFiltradosListaEmpleadosNovedad() {
        return filtradosListaEmpleadosNovedad;
    }

    public void setFiltradosListaEmpleadosNovedad(List<Empleados> filtradosListaEmpleadosNovedad) {
        this.filtradosListaEmpleadosNovedad = filtradosListaEmpleadosNovedad;
    }

    public Empleados getSeleccionMostrar() {
        if (seleccionMostrar == null) {
            getListaEmpleadosNovedad();
        }
        return seleccionMostrar;
    }

    public void setSeleccionMostrar(Empleados seleccionMostrar) {
        this.seleccionMostrar = seleccionMostrar;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }
    //LOV empleados

    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesSistema.lovEmpleados();
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Empleados> getFiltradoslistaEmpleados() {
        return filtradoslistaEmpleados;
    }

    public void setFiltradoslistaEmpleados(List<Empleados> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleados = filtradoslistaEmpleados;
    }

    public Empleados getSeleccionEmpleados() {
        return seleccionEmpleados;
    }

    public void setSeleccionEmpleados(Empleados seleccionEmpleados) {
        this.seleccionEmpleados = seleccionEmpleados;
    }

    public NovedadesSistema getMostrar() {
        if (listaNovedades != null && !listaNovedades.isEmpty()) {
            mostrar = listaNovedades.get(0);
        } else {
            mostrar = new NovedadesSistema();
        }
        return mostrar;
    }

    public void setMostrar(NovedadesSistema mostrar) {
        this.mostrar = mostrar;
    }
//LOV MOTIVOS LIQUIDACIONES

    public List<MotivosDefinitivas> getListaMotivos() {
        if (listaMotivos == null) {
            listaMotivos = administrarNovedadesSistema.lovMotivos();
        }
        return listaMotivos;
    }

    public void setListaMotivos(List<MotivosDefinitivas> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public List<MotivosDefinitivas> getFiltradosListaMotivos() {
        return filtradosListaMotivos;
    }

    public void setFiltradosListaMotivos(List<MotivosDefinitivas> filtradosListaMotivos) {
        this.filtradosListaMotivos = filtradosListaMotivos;
    }

    public MotivosDefinitivas getSeleccionMotivos() {
        return seleccionMotivos;
    }

    public void setSeleccionMotivos(MotivosDefinitivas seleccionMotivos) {
        this.seleccionMotivos = seleccionMotivos;
    }
//LOV RETIROS

    public List<MotivosRetiros> getListaRetiros() {
        if (listaRetiros == null) {
            listaRetiros = administrarNovedadesSistema.lovRetiros();
        }
        return listaRetiros;
    }

    public void setListaRetiros(List<MotivosRetiros> listaRetiros) {
        this.listaRetiros = listaRetiros;
    }

    public List<MotivosRetiros> getFiltradosListaRetiros() {
        return filtradosListaRetiros;
    }

    public void setFiltradosListaRetiros(List<MotivosRetiros> filtradosListaRetiros) {
        this.filtradosListaRetiros = filtradosListaRetiros;
    }

    public MotivosRetiros getSeleccionRetiros() {
        return seleccionRetiros;
    }

    public void setSeleccionRetiros(MotivosRetiros seleccionRetiros) {
        this.seleccionRetiros = seleccionRetiros;
    }

    public NovedadesSistema getDuplicarNovedad() {
        return duplicarNovedad;
    }

    public void setDuplicarNovedad(NovedadesSistema duplicarNovedad) {
        this.duplicarNovedad = duplicarNovedad;
    }

    public NovedadesSistema getNuevaNovedad() {
        return nuevaNovedad;
    }

    public void setNuevaNovedad(NovedadesSistema nuevaNovedad) {
        this.nuevaNovedad = nuevaNovedad;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }
}

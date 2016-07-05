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
    //LISTA NOVEDADES
    private List<NovedadesSistema> listaNovedades;
    private NovedadesSistema novedadMostrar;
    private List<NovedadesSistema> listaBorrar;
    private List<NovedadesSistema> listaCrear;
    private List<NovedadesSistema> listaModificar;
    private List<NovedadesSistema> filtradosListaNovedades;
    //Consultas sobre novedad
    private NovedadesSistema novedadParametro;
    //LISTA DE ARRIBA EMPLEADOS
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradosListaEmpleadosNovedad;
    private Empleados empleadoSeleccionado; //Seleccion Mostrar
    private Empleados empleadoBack; //Seleccion Mostrar
    //OTROS
    private boolean aceptar;
    //private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private boolean guardado, guardarOk;
    //editar celda
    private NovedadesSistema editarNovedades;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //Crear Novedades
    //private List<NovedadesSistema> listaNovedadesCrear;
    public NovedadesSistema nuevaNovedad;
    public NovedadesSistema duplicarNovedad;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //L.O.V Empleados
    private List<Empleados> listaEmpleadosLOV;
    private List<Empleados> filtradoslistaEmpleadosLOV;
    private Empleados empleadoSeleccionadoLOV;
    //L.O.V MOTIVOS
    private List<MotivosDefinitivas> listaMotivos;
    private List<MotivosDefinitivas> filtradosListaMotivos;
    private MotivosDefinitivas seleccionMotivos;
    //L.O.V RETIROS
    private List<MotivosRetiros> listaMotiRetiros;
    private List<MotivosRetiros> filtradosListaRetiros;
    private MotivosRetiros seleccionRetiros;
    //AUTOCOMPLETAR
    private String motivoDefinitiva, motivoRetiro;
    private String celda;
    //Desactivar Campos
    private Boolean activate;
    //Pagina Anterior
    private String paginaAnterior;
    //Activar boton mostrar todos
    private boolean activarMostrarTodos;
    private String infoRegistro;
    private String infoRegistroEmpl;
    private String infoRegistroMLiqDef;
    private String infoRegistroMRetiro;
    private int numNovedad;
    private DataTable tablaC;
    private boolean activarNoRango;
    private boolean activarLOV;

    public ControlNovedadesDefinitivas() {
        paginaAnterior = "";
        permitirIndex = true;
        listaEmpleadosLOV = null;
        listaEmpleados = null;
        permitirIndex = true;
        aceptar = true;
        guardado = true;
        tipoLista = 0;
        listaNovedades = null;
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setMotivodefinitiva(new MotivosDefinitivas());
        nuevaNovedad.setMotivoretiro(new MotivosRetiros());
        nuevaNovedad.setDias(BigInteger.valueOf(0));
        nuevaNovedad.setTipo(" ");
        nuevaNovedad.setSubtipo(" ");
        activarMostrarTodos = true;
        numNovedad = -1;
        activarNoRango = false;
        listaCrear = new ArrayList<NovedadesSistema>();
        listaBorrar = new ArrayList<NovedadesSistema>();
        listaModificar = new ArrayList<NovedadesSistema>();
        empleadoBack = new Empleados();
        activarLOV = true;
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

    public void recibirPagina(String pagina) {
        paginaAnterior = pagina;
        getListaEmpleados();
        if(listaEmpleados != null){
            empleadoSeleccionado = listaEmpleados.get(0);
        }
    }

    public String volverPagAnterior() {
        return paginaAnterior;
    }

    public void asignarIndex(int cualLOV, int tipoAct) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = tipoAct;

        if (cualLOV == 0) {
            contarRegistrosLovEmpl(0);
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (cualLOV == 1) {
            activarBotonLov();
            contarRegistrosLovMLiqDef(0);
            context.update("formularioDialogos:motivosDialogo");
            context.execute("motivosDialogo.show()");
        } else if (cualLOV == 2) {
            activarBotonLov();
            contarRegistrosLovMRetiro(0);
            context.update("formularioDialogos:retirosDialogo");
            context.execute("retirosDialogo.show()");
        } else {
            deshabilitarBotonLov();
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        listaNovedades = null;
        listaEmpleados.clear();
        for (int i = 0; i < listaEmpleadosLOV.size(); i++) {
            listaEmpleados.add(listaEmpleadosLOV.get(i));
        }
        empleadoSeleccionado = listaEmpleados.get(0);
        //ACTUALIZAR CADA COMPONENTE
        filtradosListaEmpleadosNovedad = null;
        aceptar = true;
        tipoActualizacion = -1;
        activarMostrarTodos = true;
        contarRegistros();
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");
    }

//Ubicacion Celda Arriba 
    public void cambiarEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();//ACTUALIZAR DATOS POR CADA EMPLEADO
        if (guardado) {
            empleadoBack = empleadoSeleccionado;
            listaNovedades = null;
            novedadMostrar = null;
            numNovedad = -1;
            getListaNovedades();
            activarNoRango = false;
            contarRegistros();
            context.update("form:formularioNovedades");
            context.update("form:btnMostrarTodos");
            context.update("form:informacionRegistro");
        } else {
            context.execute("confirmarGuardar.show()");
        }
        deshabilitarBotonLov();
    }

    public void valoresBackupAutocompletar(String Campo) {
        if (novedadMostrar != null) {
            if (Campo.equals("MOTIVO")) {
                motivoDefinitiva = novedadMostrar.getMotivodefinitiva().getNombre();
            } else if (Campo.equals("RETIRO")) {
                motivoRetiro = novedadMostrar.getMotivoretiro().getNombre();
            }
            activarBotonLov();
        } else {
            RequestContext.getCurrentInstance().execute("seleccionarRegistro.show()");
        }
    }

    public void autocompletar(String campoaCambiar, String valor) {
        RequestContext context = RequestContext.getCurrentInstance();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;

        if (campoaCambiar.equalsIgnoreCase("MOTIVO")) {
            novedadMostrar.getMotivodefinitiva().setNombre(motivoDefinitiva);

            for (int i = 0; i < listaMotivos.size(); i++) {
                if (listaMotivos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                novedadMostrar.setMotivodefinitiva(listaMotivos.get(indiceUnicoElemento));
                context.update("form:formularioNovedades:motivoLiquidacion");
                listaModificar.add(novedadMostrar);
            } else {
                contarRegistrosLovMLiqDef(0);
                context.update("formularioDialogos:motivosDialogo");
                context.execute("motivosDialogo.show()");
                context.update("form:formularioNovedades:motivoLiquidacion");
            }
        } else if (campoaCambiar.equalsIgnoreCase("RETIRO")) {
            novedadMostrar.getMotivoretiro().setNombre(motivoRetiro);
            for (int i = 0; i < listaMotiRetiros.size(); i++) {
                if (listaMotiRetiros.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                novedadMostrar.setMotivoretiro(listaMotiRetiros.get(indiceUnicoElemento));
                context.update("form:formularioNovedades:motivoRetiro");
                listaModificar.add(novedadMostrar);
            } else {
                contarRegistrosLovMRetiro(0);
                context.update("formularioDialogos:retirosDialogo");
                context.execute("retirosDialogo.show()");
                context.update("form:formularioNovedades:motivoRetiro");
            }
        }
    }

    public void cambiosCampos() {
        RequestContext context = RequestContext.getCurrentInstance();
        int error = 0;
        for (int i = 0; i < listaNovedades.size(); i++) {
            if (novedadMostrar.getFechainicialdisfrute() != null && (nuevaNovedad.getFechainicialdisfrute().equals(listaNovedades.get(i).getFechainicialdisfrute()))) {
                error++;
            }
        }
        if (error > 0) {
            context.update("formularioDialogos:fechaRepetida");
            context.execute("fechaRepetida.show()");
        } else {
            listaModificar.add(novedadMostrar);
            guardado = false;
        }
    }

    public void celda(String Campo) {
        if (Campo.equals("MOTIVO")) {
            activarBotonLov();
            cualCelda = 0;
        } else if (Campo.equals("RETIRO")) {
            activarBotonLov();
            cualCelda = 1;
        } else if (Campo.equals("FECHA")) {
            deshabilitarBotonLov();
            cualCelda = 2;
        } else if (Campo.equals("OBSERVACION")) {
            deshabilitarBotonLov();
            cualCelda = 3;
        } else {
            deshabilitarBotonLov();
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (cualCelda == 0) {
            context.update("formularioDialogos:editarMotivos");
            context.execute("editarMotivos.show()");
            cualCelda = -1;
        } else if (cualCelda == 1) {
            context.update("formularioDialogos:editarRetiros");
            context.execute("editarRetiros.show()");
            cualCelda = -1;
        } else if (cualCelda == 2) {
            context.update("formularioDialogos:editarFechas");
            context.execute("editarFechas.show()");
            cualCelda = -1;
        } else if (cualCelda == 3) {
            context.update("formularioDialogos:editarObservaciones");
            context.execute("editarObservaciones.show()");
            cualCelda = -1;
        }
    }

    public void actualizarEmpleadosNovedad() {
        RequestContext context = RequestContext.getCurrentInstance();

        listaEmpleados.clear();
        listaEmpleados.add(empleadoSeleccionadoLOV);
        empleadoSeleccionado = empleadoSeleccionadoLOV;
        contarRegistros();
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.reset("formularioDialogos:LOVEmpleados");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
        //context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");

        listaNovedades = null;
        getListaNovedades();
        //getNovedadMostrar();

        listaEmpleadosLOV = null;
        filtradosListaEmpleadosNovedad = null;
        empleadoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        activarMostrarTodos = false;

        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
        context.update("form:btnMostrarTodos");
        context.update("form:informacionRegistro");
    }

    public void listaValoresBoton() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (cualCelda == 0) {
            contarRegistrosLovMLiqDef(0);
            context.update("formularioDialogos:motivosDialogo");
            context.execute("motivosDialogo.show()");
            tipoActualizacion = 0;
        } else if (cualCelda == 1) {
            contarRegistrosLovMRetiro(0);
            context.update("formularioDialogos:retirosDialogo");
            context.execute("retirosDialogo.show()");
            tipoActualizacion = 0;
        }
    }

    public void limpiarNuevaNovedad() {
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setMotivodefinitiva(new MotivosDefinitivas());
        nuevaNovedad.setMotivoretiro(new MotivosRetiros());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:novedadesEmpleadosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NovedadesDefinitivasEmpleadosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        deshabilitarBotonLov();
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:novedadesEmpleadosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NovedadesDefinitivasEmpleadosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        deshabilitarBotonLov();
    }

    public void actualizarMotivo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            novedadMostrar.setMotivodefinitiva(seleccionMotivos);
            context.update("form:motivoLiquidacion");
            listaModificar.add(novedadMostrar);
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setMotivodefinitiva(seleccionMotivos);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setMotivodefinitiva(seleccionMotivos);
            context.update("formularioDialogos:duplicarNovedad");
        }
        seleccionMotivos = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.reset("formularioDialogos:LOVMotivos:globalFilter");
        context.execute("LOVMotivos.clearFilters()");
        context.execute("motivosDialogo.hide()");
        context.update("formularioDialogos:motivosDialogo");
    }

    public void Bool() {
        novedadMostrar.getIndemnizaBool();
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

        if (nuevaNovedad.getMotivodefinitiva().getNombre() == null) {
            System.out.println("Entro a Motivo");
            mensajeValidacion = mensajeValidacion + " * Motivo Liquidacion Definitiva\n";
            pasa++;
        }
        if (nuevaNovedad.getMotivoretiro().getNombre() == null) {
            System.out.println("Entro a Retiro");
            mensajeValidacion = mensajeValidacion + " * Motivo Retiro\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);

        if (pasa != 0) {
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
            nuevaNovedad.setEmpleado(empleadoSeleccionado);
            nuevaNovedad.setTipo("DEFINITIVA");
            nuevaNovedad.setSubtipo("DINERO");
            nuevaNovedad.setDias(BigInteger.valueOf(0));
            listaNovedades.add(nuevaNovedad);
            novedadMostrar = listaNovedades.get(listaNovedades.indexOf(nuevaNovedad));
            listaCrear.add(nuevaNovedad);

            if (novedadMostrar != null) {
                activate = false;
            }

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
            primeraNovedad();
            context.execute("NuevaNovedadEmpleado.hide()");
        } else {
            context.update("form:formularioNovedades");
        }
        contarRegistros();
    }

    public void save(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Guardado Exitoso", "Los cambios han sido guardados con éxito"));
    }

    //GUARDAR
    public void guardarCambiosNovedades() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (guardado == false) {

            //MODIFICAR
            if (listaModificar != null) {
                if (!listaModificar.isEmpty()) {
                    for (int n = 0; n < listaModificar.size(); n++) {
                        listaModificar.get(n).setEmpleado(empleadoSeleccionado);
                        listaModificar.get(n).setTipo("DEFINITIVA");
                        listaModificar.get(n).setSubtipo("DINERO");
                        administrarNovedadesSistema.modificarNovedades(listaModificar.get(n));
                    }
                    listaModificar.clear();
                }
            }
            //CREAR
            if (listaCrear != null) {
                if (!listaCrear.isEmpty()) {
                    for (int n = 0; n < listaCrear.size(); n++) {
                        administrarNovedadesSistema.crearNovedades(listaCrear.get(n));
                    }
                    listaCrear.clear();
                }
            }
            if (listaBorrar != null) {
                if (!listaBorrar.isEmpty()) {
                    for (int n = 0; n < listaBorrar.size(); n++) {
                        administrarNovedadesSistema.borrarNovedades(listaBorrar.get(n));
                    }
                    listaBorrar.clear();
                }
            }
            RequestContext context = RequestContext.getCurrentInstance();
            fcontext.addMessage(null, new FacesMessage("Guardado Exitoso", "Los cambios han sido guardados exitosamente"));
            context.update("form:growl");

            listaNovedades = null;
            context.update("form:datosEmpleados");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        deshabilitarBotonLov();
    }

    public void noGuardarCambiosNovedades() {
        listaBorrar.clear();
        listaCrear.clear();
        listaModificar.clear();
        guardado = true;
    }

    public void seleccionAntes() {
        empleadoSeleccionado = empleadoBack;
        FacesContext c = FacesContext.getCurrentInstance();
        tablaC = (DataTable) c.getViewRoot().findComponent("form:datosEmpleados");
        tablaC.setSelection(empleadoSeleccionado);
        RequestContext.getCurrentInstance().update("form:datosEmpleados");
    }

    public void activarBotonLov() {
        activarLOV = false;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void deshabilitarBotonLov() {
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    //BORRAR Novedades
    public void borrarNovedades() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (novedadMostrar != null) {
            listaBorrar.add(novedadMostrar);
            if (listaCrear.contains(novedadMostrar)) {
                listaCrear.remove(novedadMostrar);
            }
            if (listaModificar.contains(novedadMostrar)) {
                listaModificar.remove(novedadMostrar);
            }
            listaNovedades.remove(novedadMostrar);
        }
        if (guardado == true) {
            guardado = false;
            context.update("form:aceptar");
        }
        primeraNovedad();
        deshabilitarBotonLov();
        context.update("form:formularioNovedades");
    }

    //DUPLICAR NOVEDAD
    public void duplicarN() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (novedadMostrar != null) {
            if (novedadMostrar.getEmpleado() != null) {
                duplicarNovedad = new NovedadesSistema();
                k++;
                l = BigInteger.valueOf(k);
                if (tipoLista == 0) {
                    duplicarNovedad.setSecuencia(l);
                    duplicarNovedad.setEmpleado(empleadoSeleccionado);
                    duplicarNovedad.setTipo(novedadMostrar.getTipo());
                    duplicarNovedad.setSubtipo(novedadMostrar.getSubtipo());
                    duplicarNovedad.setDias(novedadMostrar.getDias());
                    duplicarNovedad.setFechainicialdisfrute(novedadMostrar.getFechainicialdisfrute());
                    duplicarNovedad.setMotivodefinitiva(novedadMostrar.getMotivodefinitiva());
                    duplicarNovedad.setMotivoretiro(novedadMostrar.getMotivoretiro());
                    duplicarNovedad.setObservaciones(novedadMostrar.getObservaciones());
                }
                context.update("formularioDialogos:duplicarNovedad");
                context.execute("DuplicarNovedadEmpleado.show()");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        int pasa = 0;
        int pasa2 = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        if (!listaNovedades.isEmpty()) {
            for (int i = 0; i < listaNovedades.size(); i++) {
                if (duplicarNovedad.getFechainicialdisfrute() != null && (duplicarNovedad.getFechainicialdisfrute().equals(listaNovedades.get(i).getFechainicialdisfrute()))) {
                    context.update("formularioDialogos:fechaRepetida");
                    context.execute("fechaRepetida.show()");
                    pasa2++;
                }
            }
        }

        if (duplicarNovedad.getFechainicialdisfrute() == null) {
            mensajeValidacion = mensajeValidacion + " * Fecha Liquidacion Definitiva\n";
            pasa++;
        }

        if (duplicarNovedad.getMotivodefinitiva().getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " * Motivo Liquidacion Definitiva\n";
            pasa++;
        }
        if (duplicarNovedad.getMotivoretiro().getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " * Motivo Retiro\n";
            pasa++;
        }

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedad");
            context.execute("validacionNuevaNovedad.show()");
        }

        if (pasa == 0 && pasa2 == 0) {
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            duplicarNovedad.setSecuencia(l);
            duplicarNovedad.setEmpleado(empleadoSeleccionado);
            duplicarNovedad.setTipo("DEFINITIVA");
            duplicarNovedad.setSubtipo("DINERO");
            listaNovedades.add(duplicarNovedad);
            novedadMostrar = listaNovedades.get(listaNovedades.indexOf(duplicarNovedad));
            listaCrear.add(duplicarNovedad);

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
        } else {
        }
        contarRegistros();
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
    }

    public void actualizarRetiros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            novedadMostrar.setMotivoretiro(seleccionRetiros);
            context.update("form:motivoRetiro");
            listaModificar.add(novedadMostrar);
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setMotivoretiro(seleccionRetiros);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setMotivoretiro(seleccionRetiros);
            context.update("formularioDialogos:duplicarNovedad");
        }
        seleccionRetiros = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVRetiros:globalFilter");
        context.execute("LOVRetiros.clearFilters()");
        context.execute("retirosDialogo.hide()");
        context.update("formularioDialogos:LOVRetiros");
    }

    public void cancelarCambioMotivos() {
        seleccionMotivos = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVMotivos:globalFilter");
        context.execute("LOVMotivos.clearFilters()");
        context.execute("motivosDialogo.hide()");
        context.update("formularioDialogos:motivosDialogo");
    }

    public void cancelarCambioRetiros() {
        seleccionRetiros = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVRetiros:globalFilter");
        context.execute("LOVRetiros.clearFilters()");
        context.execute("retirosDialogo.hide()");
        context.update("formularioDialogos:LOVRetiros");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void cancelarCambioEmpleados() {
        filtradoslistaEmpleadosLOV = null;
        empleadoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        deshabilitarBotonLov();
        if (novedadMostrar != null) {
            int result = administrarRastros.obtenerTabla(novedadMostrar.getSecuencia(), "NOVEDADESSISTEMA");
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
            if (administrarRastros.verificarHistoricosTabla("NOVEDADESSISTEMA")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
    }

    //CANCELAR MODIFICACIONES
    public void refrescar() {
        RequestContext context = RequestContext.getCurrentInstance();
        permitirIndex = true;
        cualCelda = -1;
        listaNovedades = null;
        guardado = true;
        permitirIndex = true;
        aceptar = true;
        activarMostrarTodos = true;
        k = 0;
        tipoLista = 0;
        numNovedad = -1;
        activarNoRango = false;

        nuevaNovedad = new NovedadesSistema();
        duplicarNovedad = new NovedadesSistema();
        permitirIndex = true;

        listaBorrar.clear();
        listaCrear.clear();
        listaModificar.clear();

        listaEmpleados = null;
        getListaEmpleados();
        contarRegistros();
        contarRegistrosLovEmpl(0);
        deshabilitarBotonLov();
        context.update("form:btnMostrarTodos");
        context.update("form:datosEmpleados");
        context.update("form:formularioNovedades");
    }

    //GETTERS & SETTERS
    public List<NovedadesSistema> getListaNovedades() {
        if (listaNovedades == null) {
            if (empleadoSeleccionado == null) {
                empleadoSeleccionado = listaEmpleados.get(0);
            }
            listaNovedades = administrarNovedadesSistema.novedadesEmpleado(empleadoSeleccionado.getSecuencia());
            if (listaNovedades == null) {
                listaNovedades = new ArrayList<NovedadesSistema>();
            }
        }
        novedadMostrar = null;
        if (listaNovedades == null || listaNovedades.isEmpty()) {
            activate = true;
        } else {
            activate = false;
            getNovedadMostrar();
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

    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesSistema.buscarEmpleados();
            contarRegistros();
//            if (listaEmpleadosLOV == null) {
            listaEmpleadosLOV = new ArrayList<Empleados>();
            for (int i = 0; i < listaEmpleados.size(); i++) {
                listaEmpleadosLOV.add(listaEmpleados.get(i));
            }
//            }
            getListaNovedades();
            contarRegistrosLovEmpl(0);
        }
        return listaEmpleados;
    }

    public void siguienteNovedad() {
        int poss = listaNovedades.indexOf(novedadMostrar);
        if (poss < (listaNovedades.size() - 1)) {
            novedadMostrar = listaNovedades.get(poss + 1);
            activarNoRango = false;
            numNovedad++;
        } else {
            activarNoRango = true;
        }
        RequestContext.getCurrentInstance().update("form:formularioNovedades");
    }

    public void anteriorNovedad() {
        int poss = listaNovedades.indexOf(novedadMostrar);
        if (poss > 0) {
            novedadMostrar = listaNovedades.get(poss - 1);
            activarNoRango = false;
            numNovedad--;
        } else {
            activarNoRango = true;
        }
        RequestContext.getCurrentInstance().update("form:formularioNovedades");
    }

    public void primeraNovedad() {
        if (listaNovedades.size() >= 1) {
            novedadMostrar = listaNovedades.get(0);
            activarNoRango = false;
            numNovedad = 0;
        } else {
            activarNoRango = true;
            numNovedad = -1;
        }
        RequestContext.getCurrentInstance().update("form:formularioNovedades");
    }

    public void ultimaNovedad() {
        if (listaNovedades.size() > 1) {
            novedadMostrar = listaNovedades.get(listaNovedades.size() - 1);
            activarNoRango = false;
            numNovedad = listaNovedades.size() - 1;
        } else {
            activarNoRango = true;
        }
        RequestContext.getCurrentInstance().update("form:formularioNovedades");
    }

    public void eventoFiltrarLovEmpl() {
        contarRegistrosLovEmpl(1);
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        deshabilitarBotonLov();
        contarRegistros();
    }

    public void contarRegistros() {
        if (tipoLista == 1) {
            infoRegistro = String.valueOf(filtradosListaEmpleadosNovedad.size());
        } else if (listaEmpleados != null) {
            infoRegistro = String.valueOf(listaEmpleados.size());
        } else {
            infoRegistro = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
    }

    public void contarRegistrosLovEmpl(int tipoListaLOV) {
        if (tipoListaLOV == 1) {
            infoRegistroEmpl = String.valueOf(filtradoslistaEmpleadosLOV.size());
        } else if (listaEmpleadosLOV != null) {
            infoRegistroEmpl = String.valueOf(listaEmpleadosLOV.size());
        } else {
            infoRegistroEmpl = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("formularioDialogos:informacionRegistroEmplLov");
    }

    public void contarRegistrosLovMRetiro(int tipoListaLOV) {
        if (tipoListaLOV == 1) {
            infoRegistroMRetiro = String.valueOf(filtradosListaRetiros.size());
        } else if (listaEmpleadosLOV != null) {
            infoRegistroMRetiro = String.valueOf(listaMotiRetiros.size());
        } else {
            infoRegistroMRetiro = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("formularioDialogos:informacionRegistroRet");
    }

    public void contarRegistrosLovMLiqDef(int tipoListaLOV) {
        if (tipoListaLOV == 1) {
            infoRegistroMLiqDef = String.valueOf(filtradosListaMotivos.size());
        } else if (listaMotiRetiros != null) {
            infoRegistroMLiqDef = String.valueOf(listaMotivos.size());
        } else {
            infoRegistroMLiqDef = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("formularioDialogos:informacionRegistroMot");
    }

    public void setListaEmpleados(List<Empleados> listaEmpleadosNovedad) {
        this.listaEmpleados = listaEmpleadosNovedad;
    }

    public List<Empleados> getFiltradosListaEmpleadosNovedad() {
        return filtradosListaEmpleadosNovedad;
    }

    public void setFiltradosListaEmpleadosNovedad(List<Empleados> filtradosListaEmpleadosNovedad) {
        this.filtradosListaEmpleadosNovedad = filtradosListaEmpleadosNovedad;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados seleccion) {
        this.empleadoSeleccionado = seleccion;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }
    //LOV empleados

    public List<Empleados> getListaEmpleadosLOV() {
        if (listaEmpleadosLOV == null) {
            listaEmpleadosLOV = administrarNovedadesSistema.lovEmpleados();
        }
        return listaEmpleadosLOV;
    }

    public void setListaEmpleadosLOV(List<Empleados> listaEmpleados) {
        this.listaEmpleadosLOV = listaEmpleados;
    }

    public List<Empleados> getFiltradoslistaEmpleadosLOV() {
        return filtradoslistaEmpleadosLOV;
    }

    public void setFiltradoslistaEmpleadosLOV(List<Empleados> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleadosLOV = filtradoslistaEmpleados;
    }

    public Empleados getEmpleadoSeleccionadoLOV() {
        return empleadoSeleccionadoLOV;
    }

    public void setEmpleadoSeleccionadoLOV(Empleados seleccionEmpleados) {
        this.empleadoSeleccionadoLOV = seleccionEmpleados;
    }

    public NovedadesSistema getNovedadMostrar() {
        if (listaNovedades != null) {
            if (!listaNovedades.isEmpty()) {
                if (novedadMostrar == null) {
                    novedadMostrar = listaNovedades.get(0);
                }
                numNovedad = listaNovedades.indexOf(novedadMostrar);
            }
        }
        if (novedadMostrar == null) {
            novedadMostrar = new NovedadesSistema();
            numNovedad = -1;
        }
        return novedadMostrar;
    }

    public void setNovedadMostrar(NovedadesSistema mostrar) {
        this.novedadMostrar = mostrar;
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

    public List<MotivosRetiros> getListaMotiRetiros() {
        if (listaMotiRetiros == null) {
            listaMotiRetiros = administrarNovedadesSistema.lovRetiros();
        }
        return listaMotiRetiros;
    }

    public void setListaMotiRetiros(List<MotivosRetiros> listaRetiros) {
        this.listaMotiRetiros = listaRetiros;
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

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public boolean isActivarMostrarTodos() {
        return activarMostrarTodos;
    }

    public void setActivarMostrarTodos(boolean activarMostrarTodos) {
        this.activarMostrarTodos = activarMostrarTodos;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public int getNumNovedad() {
        return numNovedad;
    }

    public void setNumNovedad(int numNovedad) {
        this.numNovedad = numNovedad;
    }

    public boolean isActivarNoRango() {
        return activarNoRango;
    }

    public void setActivarNoRango(boolean activarNoRango) {
        this.activarNoRango = activarNoRango;
    }

    public String getInfoRegistroEmpl() {
        return infoRegistroEmpl;
    }

    public void setInfoRegistroEmpl(String infoRegistroEmpl) {
        this.infoRegistroEmpl = infoRegistroEmpl;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }

    public String getInfoRegistroMLiqDef() {
        return infoRegistroMLiqDef;
    }

    public void setInfoRegistroMLiqDef(String infoRegistroMLiqDef) {
        this.infoRegistroMLiqDef = infoRegistroMLiqDef;
    }

    public String getInfoRegistroMRetiro() {
        return infoRegistroMRetiro;
    }

    public void setInfoRegistroMRetiro(String infoRegistroMRetiro) {
        this.infoRegistroMRetiro = infoRegistroMRetiro;
    }
    
}

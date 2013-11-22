/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.Motivosdefinitivas;
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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    private List<Motivosdefinitivas> listaMotivos;
    private List<Motivosdefinitivas> filtradosListaMotivos;
    private Motivosdefinitivas seleccionMotivos;
    //L.O.V RETIROS
    private List<MotivosRetiros> listaRetiros;
    private List<MotivosRetiros> filtradosListaRetiros;
    private MotivosRetiros seleccionRetiros;
    //AUTOCOMPLETAR
    private String motivoDefinitiva, motivoRetiro;
    private String celda;

    public ControlNovedadesDefinitivas() {
        permitirIndex = true;
        listaEmpleados = null;
        listaEmpleadosNovedad = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaNovedadesBorrar = new ArrayList<NovedadesSistema>();
        listaNovedadesCrear = new ArrayList<NovedadesSistema>();
        listaNovedadesModificar = new ArrayList<NovedadesSistema>();
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setMotivodefinitiva(new Motivosdefinitivas());
        nuevaNovedad.setMotivoretiro(new MotivosRetiros());
        nuevaNovedad.setDias(BigInteger.valueOf(0));
        nuevaNovedad.setTipo(" ");
        nuevaNovedad.setSubtipo(" ");
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
        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad = administrarNovedadesSistema.buscarEmpleados();
        } else {
            listaEmpleadosNovedad = administrarNovedadesSistema.buscarEmpleados();
        }
        if (!listaEmpleadosNovedad.isEmpty()) {
            seleccionMostrar = listaEmpleadosNovedad.get(0);
            listaEmpleadosNovedad = null;
            getListaEmpleadosNovedad();
        }

        context.update("form:datosEmpleados");
        //ACTUALIZAR CADA COMPONENTE
        context.update("form:datosEmpleados");
        context.update("formularioNovedades:fechaLiquidacion");
        context.update("formularioNovedades:motivoLiquidacion");
        context.update("formularioNovedades:motivoRetiro");
        context.update("formularioNovedades:observaciones");
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
        getMostrar();


        RequestContext context = RequestContext.getCurrentInstance();//ACTUALIZAR CADA 
        context.update("form:datosEmpleados");
        context.update("formularioNovedades:fechaLiquidacion");
        context.update("formularioNovedades:motivoLiquidacion");
        context.update("formularioNovedades:motivoRetiro");
        context.update("formularioNovedades:observaciones");
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
                context.update("formularioNovedades:motivoLiquidacion");
                listaMotivos.clear();
                getListaMotivos();
            } else {
                context.update("formularioDialogos:motivosDialogo");
                context.execute("motivosDialogo.show()");
                context.update("formularioNovedades:motivoLiquidacion");
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
                context.update("formularioNovedades:motivoRetiro");
                listaRetiros.clear();
                getListaRetiros();
            } else {
                context.update("formularioDialogos:retirosDialogo");
                context.execute("retirosDialogo.show()");
                context.update("formularioNovedades:motivoRetiro");
            }
        }
    }

    public void cambiosCampos() {
        guardado = false;
    }

    public void celda(String Campo) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (Campo.equals("MOTIVO")) {
            cualCelda = 0;
        } else if (Campo.equals("RETIRO")) {
            cualCelda = 1;
        } else if (Campo.equals("FECHA")) {
            cualCelda = 2;
        } else if (Campo.equals("OBSERVACION")) {
            cualCelda = 3;
        }
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
        } else {
            listaEmpleadosNovedad.add(seleccionEmpleados);
        }

        secuenciaEmpleado = listaEmpleadosNovedad.get(0).getSecuencia();
        listaNovedades = null;
        getListaNovedades();
        mostrar = listaNovedades.get(0);

        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("formularioDetalles:fechaLiquidacion");
        context.update("formularioDetalles:motivoLiquidacion");
        context.update("formularioDetalles:motivoRetiro");
        context.update("formularioDetalles:observaciones");

        listaEmpleados = null;
        filtradosListaEmpleadosNovedad = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
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
        nuevaNovedad.setMotivodefinitiva(new Motivosdefinitivas());
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
            context.update("formularioNovedades:motivoLiquidacion");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setMotivodefinitiva(seleccionMotivos);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setMotivodefinitiva(seleccionMotivos);
            context.update("formularioDialogos:duplicarNovedad");
        }

        seleccionMotivos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("motivosDialogo.hide()");
        context.reset("formularioDialogos:LOVMotivos:globalFilter");
        context.update("formularioDialogos:LOVMotivos");
    }

    public void Bool() {
        mostrar.getIndemnizaBool();
    }

    //CREAR NOVEDADES
    public void agregarNuevaNovedad() {

        int pasa = 0;
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();


        if (!listaNovedades.isEmpty()) {
            context.update("formularioDialogos:existeRegistro");
            context.execute("existeRegistro.show()");
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

        if (pasa == 0) {
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevaNovedad.setSecuencia(l);
            nuevaNovedad.setEmpleado(seleccionMostrar);
            listaNovedades.add(nuevaNovedad);
            nuevaNovedad = new NovedadesSistema();
            nuevaNovedad.setMotivodefinitiva(new Motivosdefinitivas());
            nuevaNovedad.setMotivoretiro(new MotivosRetiros());
            nuevaNovedad.setDias(BigInteger.valueOf(0));
            nuevaNovedad.setTipo(" ");
            nuevaNovedad.setSubtipo(" ");

            context.update("form:datosNovedadesEmpleado");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevaNovedadEmpleado.hide()");

            secRegistro = null;
        } else {
        }
    }

    public void actualizarRetiros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {

            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            mostrar.setMotivoretiro(seleccionRetiros);
            context.update("formularioNovedades:motivoRetiro");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setMotivoretiro(seleccionRetiros);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setMotivoretiro(seleccionRetiros);
            context.update("formularioDialogos:duplicarNovedad");

        }

        seleccionRetiros = null;
        aceptar = true;
        index = -1;
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
    }

    public void cancelarCambioRetiros() {
        seleccionRetiros = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
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

//GETTERS & SETTERS
    public List<NovedadesSistema> getListaNovedades() {
        if (listaNovedades == null) {
            listaNovedades = administrarNovedadesSistema.novedadesEmpleado(secuenciaEmpleado);
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
            seleccionMostrar = listaEmpleados.get(0);
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
            RequestContext context = RequestContext.getCurrentInstance();//ACTUALIZAR CADA 
            context.update("formularioNovedades:fechaLiquidacion");
            context.update("formularioNovedades:motivoLiquidacion");
            context.update("formularioNovedades:motivoRetiro");
            context.update("formularioNovedades:observaciones");
        } else {
            mostrar = new NovedadesSistema();
        }
        return mostrar;
    }

    public void setMostrar(NovedadesSistema mostrar) {
        this.mostrar = mostrar;
    }
//LOV MOTIVOS LIQUIDACIONES

    public List<Motivosdefinitivas> getListaMotivos() {
        if (listaMotivos == null) {
            listaMotivos = administrarNovedadesSistema.lovMotivos();
        }
        return listaMotivos;
    }

    public void setListaMotivos(List<Motivosdefinitivas> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public List<Motivosdefinitivas> getFiltradosListaMotivos() {
        return filtradosListaMotivos;
    }

    public void setFiltradosListaMotivos(List<Motivosdefinitivas> filtradosListaMotivos) {
        this.filtradosListaMotivos = filtradosListaMotivos;
    }

    public Motivosdefinitivas getSeleccionMotivos() {
        return seleccionMotivos;
    }

    public void setSeleccionMotivos(Motivosdefinitivas seleccionMotivos) {
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
}

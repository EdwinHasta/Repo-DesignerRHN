/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Causasausentismos;
import Entidades.Clasesausentismos;
import Entidades.Diagnosticoscategorias;
import Entidades.Empleados;
import Entidades.EnfermeadadesProfesionales;
import Entidades.Ibcs;
import Entidades.Soaccidentes;
import Entidades.Soausentismos;
import Entidades.Terceros;
import Entidades.Tiposausentismos;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSoausentismosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.column.Column;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlAusentismos implements Serializable {

    @EJB
    AdministrarSoausentismosInterface administrarAusentismos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //LISTA FICTI PORCENTAJES
    private List<String> listaPorcentaje;
    private List<String> filtradosListaPorcentajes;
    private String seleccionPorcentajes;
    //LISTA FICTI IBCS
    private List<Ibcs> listaIBCS;
    private List<Ibcs> filtradosListaIBCS;
    private Ibcs seleccionIBCS;
    //LISTA FICTI FORMA LIQUIDACION
    private List<String> listaForma;
    private List<String> filtradosListaForma;
    private String seleccionForma;
    //SECUENCIA DEL EMPLEADO
    private BigInteger secuenciaEmpleado;
    //Secuencia de la Causa
    private BigInteger secuenciaCausa;
    //Secuencia del ausentismo
    private BigInteger secuenciaAusentismo;
    //LISTA AUSENTISMOS
    private List<Soausentismos> listaAusentismos;
    private List<Soausentismos> filtradosListaAusentismos;
    private Soausentismos ausentismoSeleccionado;
    //LISTA DE ARRIBA
    private List<Empleados> listaEmpleadosAusentismo;
    private List<Empleados> filtradosListaEmpleadosAusentismo;
    private Empleados seleccionMostrar; //Seleccion Mostrar
    //editar celda
    private Soausentismos editarAusentismos;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private int banderaBotones;
    private int banderaBotonesD;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //Crear Novedades
    private List<Soausentismos> listaAusentismosCrear;
    public Soausentismos nuevoAusentismo;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Novedades
    private List<Soausentismos> listaAusentismosModificar;
    //Borrar Novedades
    private List<Soausentismos> listaAusentismosBorrar;
    //L.O.V EMPLEADOS
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradoslistaEmpleados;
    private Empleados seleccionEmpleados;
    //Autocompletar
    private String TipoAusentismo, Tercero, ClaseAusentismo, CausaAusentismo, Porcentaje, Forma, AD, Enfermedad, Diagnostico;
    private String BaseLiquidacion, Dias, Horas, Fechafinaus, Fechaexpedicion, InicioPago, FinPago, NumeroCertificado, Prorrogas; // faltan más campos
    private String Relacionn, Observacion; // faltan más campos
    // faltan más campos
    //L.O.V TIPO AUSENTISMO
    private List<Tiposausentismos> listaTiposAusentismos;
    private List<Tiposausentismos> filtradoslistaTiposAusentismos;
    private Tiposausentismos seleccionTiposAusentismos;
    //L.O.V CLASE AUSENTISMO
    private List<Clasesausentismos> listaClasesAusentismos;
    private List<Clasesausentismos> filtradoslistaClasesAusentismos;
    private Clasesausentismos seleccionClasesAusentismos;
    //L.O.V CAUSA AUSENTISMO
    private List<Causasausentismos> listaCausasAusentismos;
    private List<Causasausentismos> filtradoslistaCausasAusentismos;
    private Causasausentismos seleccionCausasAusentismos;
    //L.O.V Descripcion Accidente
    private List<Soaccidentes> listaAccidentes;
    private List<Soaccidentes> filtradoslistaAccidentes;
    private Soaccidentes seleccionAccidentes;
    //L.O.V Enfermedades Profesionales
    private List<EnfermeadadesProfesionales> listaEnfermeadadesProfesionales;
    private List<EnfermeadadesProfesionales> filtradoslistaEnfermeadadesProfesionales;
    private EnfermeadadesProfesionales seleccionEnfermeadadesProfesionales;
    //L.O.V Terceros
    private List<Terceros> listaTerceros;
    private List<Terceros> filtradoslistaTerceros;
    private Terceros seleccionTerceros;
    //L.O.V Diagnostivos
    private List<Diagnosticoscategorias> listaDiagnosticos;
    private List<Diagnosticoscategorias> filtradoslistaDiagnosticos;
    private Diagnosticoscategorias seleccionDiagnosticos;
    //L.O.V Prorrogas
    private List<Soausentismos> listaProrrogas;
    private List<Soausentismos> filtradoslistaProrrogas;
    private Soausentismos seleccionProrrogas;
    //Duplicar
    public Soausentismos duplicarAusentismo;
    //PRORROGA MOSTRAR
    private String Prorroga, Relacion;
    //Columnas Tabla NOVEDADES
    private Column ATipo, AClase, ACausa, ADias, AHoras, AFecha, AFechaFinaus, AFechaExpedicion, AFechaInipago,
            AFechaFinpago, APorcentaje, ABase, AForma, ADescripcionCaso, AEnfermedad, ANumero, ADiagnostico,
            AProrroga, ARelacion, ARelacionada, ATercero, AObservaciones;
    //
    private CommandButton botonAgregar, botonCancelar, botonLimpiar;
    private CommandButton botonAgregarD, botonCancelarD, botonLimpiarD;
    //
    private boolean cambiosPagina;
    //
    private String altoTabla;
    private String altoDialogoNuevo;
    private String altoDialogoDuplicar;
    private boolean colapsado;
    private String infoRegistroTipo;
    private String infoRegistroClase;
    private String infoRegistroCausa;
    private String infoRegistroPorcentaje;
    private String infoRegistroBase;
    private String infoRegistroForma;
    private String infoRegistroAccidente;
    private String infoRegistroEnfermedad;
    private String infoRegistroDiagnostico;
    private String infoRegistroProrroga;
    private String infoRegistroTercero;
    private String infoRegistroEmpleado;

    public ControlAusentismos() {
        colapsado = true;
        altoDialogoNuevo = "430";
        altoDialogoDuplicar = "430";
        altoTabla = "145";
        cambiosPagina = true;
        Relacion = null;
        Prorroga = null;
        listaProrrogas = null;
        listaIBCS = null;
        listaAccidentes = null;
        listaDiagnosticos = null;
        listaPorcentaje = new ArrayList<String>();
        listaPorcentaje.add("50");
        listaPorcentaje.add("66.6666");
        listaPorcentaje.add("80");
        listaPorcentaje.add("100");
        listaForma = new ArrayList<String>();
        listaForma.add("BASICO");
        listaForma.add("IBC MES ANTERIOR");
        listaForma.add("IBC MES ENERO");
        listaForma.add("IBC MES INCAPACIDAD");
        listaForma.add("PROMEDIO ACUMULADOS 12 MESES");
        listaForma.add("PROMEDIO IBC 12 MESES");
        listaForma.add("PROMEDIO IBC 6 MESES");
        permitirIndex = true;
        listaAusentismos = null;
        listaEmpleados = null;
        listaEmpleadosAusentismo = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaAusentismosBorrar = new ArrayList<Soausentismos>();
        listaAusentismosCrear = new ArrayList<Soausentismos>();
        listaAusentismosModificar = new ArrayList<Soausentismos>();
        //Crear VC
        nuevoAusentismo = new Soausentismos();
        nuevoAusentismo.setTipo(new Tiposausentismos());
        nuevoAusentismo.setClase(new Clasesausentismos());
        nuevoAusentismo.setCausa(new Causasausentismos());
        nuevoAusentismo.setPorcentajeindividual(BigInteger.valueOf(0));
        nuevoAusentismo.setBaseliquidacion(BigInteger.valueOf(0));
        nuevoAusentismo.setRelacionadaBool(false);
        nuevoAusentismo.setAccidente(new Soaccidentes());
        nuevoAusentismo.setEnfermedad(new EnfermeadadesProfesionales());
        nuevoAusentismo.setDiagnosticocategoria(new Diagnosticoscategorias());
        nuevoAusentismo.setProrroga(new Soausentismos());
        nuevoAusentismo.setTercero(new Terceros());
        bandera = 0;
        banderaBotones = 0;
        banderaBotonesD = 0;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarAusentismos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    //Ubicacion Celda Arriba 
    public void cambiarEmpleado() {
        //Si ninguna de las 3 listas (crear,modificar,borrar) tiene algo, hace esto
        //{
        if (listaAusentismosCrear.isEmpty() && listaAusentismosBorrar.isEmpty() && listaAusentismosModificar.isEmpty()) {
            secuenciaEmpleado = seleccionMostrar.getSecuencia();
            listaAusentismos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosAusentismosEmpleado");
            //}
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:cambiar");
            context.execute("cambiar.show()");
        }
    }

    public void limpiarListas() {
        listaAusentismosCrear.clear();
        listaAusentismosBorrar.clear();
        listaAusentismosModificar.clear();
        secuenciaEmpleado = seleccionMostrar.getSecuencia();
        listaAusentismos = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosAusentismosEmpleado");
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:tiposAusentismosDialogo");
            context.execute("tiposAusentismosDialogo.show()");
        } else if (dlg == 2) {
            context.update("formularioDialogos:clasesAusentismosDialogo");
            context.execute("clasesAusentismosDialogo.show()");
        } else if (dlg == 3) {
            context.update("formularioDialogos:causasAusentismosDialogo");
            context.execute("causasAusentismosDialogo.show()");
        } else if (dlg == 4) {
            context.update("formularioDialogos:porcentajesDialogo");
            context.execute("porcentajesDialogo.show()");
        } else if (dlg == 5) {
            context.update("formularioDialogos:ibcsDialogo");
            context.execute("ibcsDialogo.show()");
        } else if (dlg == 6) {
            context.update("formularioDialogos:formasDialogo");
            context.execute("formasDialogo.show()");
        } else if (dlg == 7) {
            context.update("formularioDialogos:accidentesDialogo");
            context.execute("accidentesDialogo.show()");
        } else if (dlg == 8) {
            context.update("formularioDialogos:tercerosDialogo");
            context.execute("tercerosDialogo.show()");
        } else if (dlg == 9) {
            context.update("formularioDialogos:enfermedadesDialogo");
            context.execute("enfermedadesDialogo.show()");
        } else if (dlg == 10) {
            listaProrrogas = null;
            context.update("formularioDialogos:prorrogasDialogo");
            context.execute("prorrogasDialogo.show()");
        } else if (dlg == 11) {
            context.update("formularioDialogos:diagnosticosDialogo");
            context.execute("diagnosticosDialogo.show()");
        }
    }

    public void mostrarTodos() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosAusentismo.isEmpty()) {
            listaEmpleadosAusentismo.clear();
        }
        //listaEmpleadosAusentismo = administrarAusentismos.lovEmpleados();
        if (listaEmpleadosAusentismo != null) {
            for (int i = 0; i < listaEmpleados.size(); i++) {
                listaEmpleadosAusentismo.add(listaEmpleados.get(i));
            }
        }
        seleccionEmpleados = listaEmpleadosAusentismo.get(0);
        listaAusentismos = administrarAusentismos.ausentismosEmpleado(seleccionEmpleados.getSecuencia());

        listaEmpleadosAusentismo = null;
        context.update("form:datosEmpleados");
        context.update("form:datosAusentismosEmpleado");
        filtradosListaAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarEmpleados() {
        RequestContext context = RequestContext.getCurrentInstance();
        Empleados e = seleccionEmpleados;

        if (!listaEmpleadosAusentismo.isEmpty()) {
            listaEmpleadosAusentismo.clear();
            listaEmpleadosAusentismo.add(e);
            seleccionMostrar = listaEmpleadosAusentismo.get(0);
        } else {
            listaEmpleadosAusentismo.add(e);
        }
        cambiosPagina = false;
        secuenciaEmpleado = seleccionEmpleados.getSecuencia();
        listaAusentismos = null;
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:datosAusentismosEmpleado");
        filtradosListaAusentismos = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarProrrogas() {
        RequestContext context = RequestContext.getCurrentInstance();
        listaAusentismos.get(index).setProrroga(seleccionProrrogas);
        context.reset("formularioDialogos:LOVProrrogas:globalFilter");
        context.execute("LOVProrrogas.clearFilters()");
        context.execute("prorrogasDialogo.hide()");
        ///context.update("formularioDialogos:LOVProrrogas");
        //context.update("form:datosAusentismosEmpleado");
        cambiosPagina = false;
        filtradosListaAusentismos = null;
        seleccionProrrogas = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarTiposAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setTipo(seleccionTiposAusentismos);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setTipo(seleccionTiposAusentismos);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setTipo(seleccionTiposAusentismos);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setTipo(seleccionTiposAusentismos);
            context.update("formularioDialogos:duplicarAusentismo");

        }

        filtradoslistaTiposAusentismos = null;
        seleccionTiposAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
//        context.update("formularioDialogos:LOVTiposAusentismos");
        context.reset("formularioDialogos:LOVTiposAusentismos:globalFilter");
        context.execute("LOVTiposAusentismos.clearFilters()");
        context.execute("tiposAusentismosDialogo.hide()");
    }

    public void actualizarClasesAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setClase(seleccionClasesAusentismos);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setClase(seleccionClasesAusentismos);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setClase(seleccionClasesAusentismos);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setClase(seleccionClasesAusentismos);
            context.update("formularioDialogos:duplicarAusentismo");
        }

        filtradoslistaClasesAusentismos = null;
        seleccionClasesAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVClasesAusentismos:globalFilter");
        context.execute("LOVClasesAusentismos.clearFilters()");
        context.execute("clasesAusentismosDialogo.hide()");
        //context.update("formularioDialogos:LOVClasesAusentismos");
    }

    public void actualizarCausasAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setCausa(seleccionCausasAusentismos);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setCausa(seleccionCausasAusentismos);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setCausa(seleccionCausasAusentismos);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setCausa(seleccionCausasAusentismos);
            context.update("formularioDialogos:duplicarAusentismo");
        }
        filtradoslistaCausasAusentismos = null;
        seleccionCausasAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVCausasAusentismos:globalFilter");
        context.execute("LOVCausasAusentismos.clearFilters()");
        context.execute("causasAusentismosDialogo.hide()");
        //context.update("formularioDialogos:LOVCausasAusentismos");
    }

    public void actualizarPorcentajes() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setPorcentajeindividual(new BigInteger(seleccionPorcentajes));
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setPorcentajeindividual(new BigInteger(seleccionPorcentajes));
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setPorcentajeindividual(new BigInteger(seleccionPorcentajes));
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setPorcentajeindividual(new BigInteger(seleccionPorcentajes));
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradosListaPorcentajes = null;
        seleccionPorcentajes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVPorcentajes:globalFilter");
        context.execute("LOVPorcentajes.clearFilters()");
        context.execute("porcentajesDialogo.hide()");
        //context.update("formularioDialogos:LOVPorcentajes");
    }

    public void actualizarIBCS() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setBaseliquidacion(seleccionIBCS.getValor().toBigInteger());
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setBaseliquidacion(seleccionIBCS.getValor().toBigInteger());
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setBaseliquidacion((seleccionIBCS.getValor().toBigInteger()));
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setBaseliquidacion((seleccionIBCS.getValor().toBigInteger()));
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradosListaIBCS = null;
        seleccionIBCS = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVIbcs:globalFilter");
        context.execute("LOVIbcs.clearFilters()");
        context.execute("ibcsDialogo.hide()");
        //context.update("formularioDialogos:LOVIbcs");
    }

    public void actualizarEnfermedades() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setEnfermedad(seleccionEnfermeadadesProfesionales);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setEnfermedad(seleccionEnfermeadadesProfesionales);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setEnfermedad(seleccionEnfermeadadesProfesionales);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setEnfermedad(seleccionEnfermeadadesProfesionales);
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradoslistaEnfermeadadesProfesionales = null;
        seleccionEnfermeadadesProfesionales = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVEnfermedades:globalFilter");
        context.execute("LOVEnfermedades.clearFilters()");
        context.execute("enfermedadesDialogo.hide()");
        //context.update("formularioDialogos:LOVEnfermedades");
    }

    public void actualizarFormas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setFormaliquidacion(seleccionForma);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setFormaliquidacion(seleccionForma);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setFormaliquidacion(seleccionForma);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setFormaliquidacion(seleccionForma);
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradosListaForma = null;
        seleccionForma = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVFormas:globalFilter");
        context.execute("LOVFormas.clearFilters()");
        context.execute("formasDialogo.hide()");
        //context.update("formularioDialogos:LOVFormas");
    }

    public void actualizarAD() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setAccidente(seleccionAccidentes);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setAccidente(seleccionAccidentes);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setAccidente((seleccionAccidentes));
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setAccidente((seleccionAccidentes));
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradoslistaAccidentes = null;
        seleccionAccidentes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVAccidentes:globalFilter");
        context.execute("LOVAccidentes.clearFilters()");
        context.execute("accidentesDialogo.hide()");
        //context.update("formularioDialogos:LOVAccidentes");
    }

    public void actualizarTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setTercero(seleccionTerceros);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setTercero(seleccionTerceros);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setTercero(seleccionTerceros);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setTercero(seleccionTerceros);
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradoslistaTerceros = null;
        seleccionTerceros = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVTerceros:globalFilter");
        context.execute("LOVTerceros.clearFilters()");
        context.execute("tercerosDialogo.hide()");
        //context.update("formularioDialogos:LOVTerceros");
    }

    public void actualizarDiagnosticos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setDiagnosticocategoria(seleccionDiagnosticos);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setDiagnosticocategoria(seleccionDiagnosticos);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setDiagnosticocategoria(seleccionDiagnosticos);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setDiagnosticocategoria(seleccionDiagnosticos);
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradoslistaDiagnosticos = null;
        seleccionDiagnosticos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVDiagnosticos:globalFilter");
        context.execute("LOVDiagnosticos.clearFilters()");
        context.execute("diagnosticosDialogo.hide()");
        //context.update("formularioDialogos:LOVDiagnosticos");
    }

    public void cancelarCambioDiagnosticos() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaDiagnosticos = null;
        seleccionDiagnosticos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVDiagnosticos:globalFilter");
        context.execute("LOVDiagnosticos.clearFilters()");
        context.execute("diagnosticosDialogo.hide()");
    }

    public void cancelarCambioEmpleados() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaEmpleados = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
    }

    public void cancelarCambioTiposAusentismos() {
        filtradoslistaTiposAusentismos = null;
        seleccionTiposAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVTiposAusentismos:globalFilter");
        context.execute("LOVTiposAusentismos.clearFilters()");
        context.execute("tiposAusentismosDialogo.hide()");
    }

    public void cancelarCambioClasesAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaClasesAusentismos = null;
        seleccionClasesAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVClasesAusentismos:globalFilter");
        context.execute("LOVClasesAusentismos.clearFilters()");
        context.execute("clasesAusentismosDialogo.hide()");
    }

    public void cancelarCambioCausasAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaCausasAusentismos = null;
        seleccionCausasAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVCausasAusentismos:globalFilter");
        context.execute("LOVCausasAusentismos.clearFilters()");
        context.execute("causasAusentismosDialogo.hide()");
    }

    public void cancelarCambioPorcentajes() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradosListaPorcentajes = null;
        seleccionPorcentajes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVPorcentajes:globalFilter");
        context.execute("LOVPorcentajes.clearFilters()");
        context.execute("porcentajesDialogo.hide()");
    }

    public void cancelarCambioFormas() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradosListaForma = null;
        seleccionForma = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVFormas:globalFilter");
        context.execute("LOVFormas.clearFilters()");
        context.execute("formasDialogo.hide()");
    }

    public void cancelarCambioIBCS() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradosListaIBCS = null;
        seleccionIBCS = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVIbcs:globalFilter");
        context.execute("LOVIbcs.clearFilters()");
        context.execute("ibcsDialogo.hide()");
    }

    public void cancelarCambioEnfermedades() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaEnfermeadadesProfesionales = null;
        seleccionEnfermeadadesProfesionales = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVEnfermedades:globalFilter");
        context.execute("LOVEnfermedades.clearFilters()");
        context.execute("enfermedadesDialogo.hide()");
    }

    public void cancelarCambioProrrogas() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaProrrogas = null;
        seleccionProrrogas = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVProrrogas:globalFilter");
        context.execute("LOVProrrogas.clearFilters()");
        context.execute("prorrogasDialogo.hide()");
    }

    public void cancelarCambioAD() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaAccidentes = null;
        seleccionAccidentes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVAccidentes:globalFilter");
        context.execute("LOVAccidentes.clearFilters()");
        context.execute("accidentesDialogo.hide()");
    }

    public void cancelarCambioTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoslistaTerceros = null;
        seleccionTerceros = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        context.reset("formularioDialogos:LOVTerceros:globalFilter");
        context.execute("LOVTerceros.clearFilters()");
        context.execute("tercerosDialogo.hide()");
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaAusentismos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int result = administrarRastros.obtenerTabla(secRegistro, "SOAUSENTISMOS");
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
            if (administrarRastros.verificarHistoricosTabla("SOAUSENTISMOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //AUTOCOMPLETAR
    public void modificarAusentismos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        System.out.println("modificarAusentismos");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaAusentismosCrear.contains(listaAusentismos.get(indice))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(indice))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");

                    }
                }
                index = -1;
            }

            context.update("form:datosAusentismosEmpleado");
        } else if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getTipo().setDescripcion(TipoAusentismo);
            } else {
                filtradosListaAusentismos.get(indice).getTipo().setDescripcion(TipoAusentismo);
            }

            for (int i = 0; i < listaTiposAusentismos.size(); i++) {
                if (listaTiposAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                }
                listaTiposAusentismos.clear();
                getListaTiposAusentismos();
                cambiosPagina = false;
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposAusentismosDialogo");
                context.execute("tiposAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getTercero().setNombre(Tercero);
            } else {
                filtradosListaAusentismos.get(indice).getTercero().setNombre(Tercero);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getTercero().getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                }
                listaTerceros.clear();
                getListaTerceros();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tercerosDialogo");
                context.execute("tercerosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CLASE")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getClase().setDescripcion(ClaseAusentismo);
            } else {
                filtradosListaAusentismos.get(indice).getClase().setDescripcion(ClaseAusentismo);
            }

            for (int i = 0; i < listaClasesAusentismos.size(); i++) {
                if (listaClasesAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setClase(listaClasesAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setClase(listaClasesAusentismos.get(indiceUnicoElemento));
                }
                listaClasesAusentismos.clear();
                getListaClasesAusentismos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:clasesAusentismosDialogo");
                context.execute("clasesAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CAUSA")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getCausa().setDescripcion(CausaAusentismo);
            } else {
                filtradosListaAusentismos.get(indice).getCausa().setDescripcion(CausaAusentismo);
            }

            for (int i = 0; i < listaCausasAusentismos.size(); i++) {
                if (listaCausasAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setCausa(listaCausasAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setCausa(listaCausasAusentismos.get(indiceUnicoElemento));
                }
                listaCausasAusentismos.clear();
                getListaCausasAusentismos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:causasAusentismosDialogo");
                context.execute("causasAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PORCENTAJE")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).setPorcentajeindividual(new BigInteger(Porcentaje));
            } else {
                filtradosListaAusentismos.get(indice).setPorcentajeindividual(new BigInteger(Porcentaje));
            }

            for (int i = 0; i < listaPorcentaje.size(); i++) {
                if ((listaPorcentaje.get(i)).toString().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setPorcentajeindividual(new BigInteger(listaPorcentaje.get(indiceUnicoElemento)));
                } else {
                    filtradosListaAusentismos.get(indice).setPorcentajeindividual(new BigInteger(listaPorcentaje.get(indiceUnicoElemento)));
                }
                listaPorcentaje.clear();
                getListaPorcentaje();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:porcentajesDialogo");
                context.execute("porcentajesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("BASE")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).setBaseliquidacion(new BigInteger(BaseLiquidacion));
            } else {
                filtradosListaAusentismos.get(indice).setBaseliquidacion(new BigInteger(BaseLiquidacion));
            }

            for (int i = 0; i < listaIBCS.size(); i++) {
                if ((listaIBCS.get(i)).toString().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setBaseliquidacion(listaIBCS.get(indiceUnicoElemento).getSecuencia());
                } else {
                    filtradosListaAusentismos.get(indice).setBaseliquidacion(listaIBCS.get(indiceUnicoElemento).getSecuencia());
                }
                listaIBCS.clear();
                getListaIBCS();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:ibcsDialogo");
                context.execute("ibcsDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("FORMA")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).setFormaliquidacion(Forma);
            } else {
                filtradosListaAusentismos.get(indice).setFormaliquidacion(Forma);
            }

            for (int i = 0; i < listaForma.size(); i++) {
                if ((listaForma.get(i)).startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setFormaliquidacion(listaForma.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setFormaliquidacion(listaForma.get(indiceUnicoElemento));
                }
                listaForma.clear();
                getListaForma();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:formasDialogo");
                context.execute("formasDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("AD")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getAccidente().setDescripcioncaso(AD);
            } else {
                filtradosListaAusentismos.get(indice).getAccidente().setDescripcioncaso(AD);
            }

            for (int i = 0; i < listaAccidentes.size(); i++) {
                if (listaAccidentes.get(i).getDescripcioncaso().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setAccidente(listaAccidentes.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setAccidente(listaAccidentes.get(indiceUnicoElemento));
                }
                listaAccidentes.clear();
                getListaAccidentes();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:accidentesDialogo");
                context.execute("accidentesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("ENFERMEDADES")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getEnfermedad().getCategoria().setDescripcion(Enfermedad);
            } else {
                filtradosListaAusentismos.get(indice).getEnfermedad().getCategoria().setDescripcion(Enfermedad);
            }

            for (int i = 0; i < listaEnfermeadadesProfesionales.size(); i++) {
                if (listaEnfermeadadesProfesionales.get(i).getCategoria().getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setEnfermedad(listaEnfermeadadesProfesionales.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setEnfermedad(listaEnfermeadadesProfesionales.get(indiceUnicoElemento));
                }
                listaEnfermeadadesProfesionales.clear();
                getListaEnfermeadadesProfesionales();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:enfermedadesDialogo");
                context.execute("enfermedadesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("DIAGNOSTICO")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getDiagnosticocategoria().setCodigo(Diagnostico);
            } else {
                filtradosListaAusentismos.get(indice).getDiagnosticocategoria().setCodigo(Diagnostico);
            }

            for (int i = 0; i < listaDiagnosticos.size(); i++) {
                if (listaDiagnosticos.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setDiagnosticocategoria(listaDiagnosticos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setDiagnosticocategoria(listaDiagnosticos.get(indiceUnicoElemento));
                }
                listaDiagnosticos.clear();
                getListaDiagnosticos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:diagnosticosDialogo");
                context.execute("diagnosticosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PRORROGA")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getProrroga().setProrrogaAusentismo(Prorroga);
            } else {
                filtradosListaAusentismos.get(indice).getProrroga().setProrrogaAusentismo(Prorroga);
            }

            for (int i = 0; i < listaProrrogas.size(); i++) {
                if (listaProrrogas.get(i).getProrrogaAusentismo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setProrroga(listaProrrogas.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setProrroga(listaProrrogas.get(indiceUnicoElemento));
                }
                listaProrrogas.clear();
                getListaProrrogas();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:prorrogasDialogo");
                context.execute("prorrogasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaAusentismosCrear.contains(listaAusentismos.get(indice))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(indice))) {

                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosAusentismosEmpleado");
    }

    //BORRAR Novedades
    public void borrarAusentismos() {
        if (index >= 0) {
            cambiosPagina = false;
            if (tipoLista == 0) {
                if (!listaAusentismosModificar.isEmpty() && listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                    int modIndex = listaAusentismosModificar.indexOf(listaAusentismos.get(index));
                    listaAusentismosModificar.remove(modIndex);
                    listaAusentismosBorrar.add(listaAusentismos.get(index));
                } else if (!listaAusentismosCrear.isEmpty() && listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    int crearIndex = listaAusentismosCrear.indexOf(listaAusentismos.get(index));
                    listaAusentismosCrear.remove(crearIndex);
                } else {
                    listaAusentismosBorrar.add(listaAusentismos.get(index));
                }
                listaAusentismos.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaAusentismosModificar.isEmpty() && listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                    int modIndex = listaAusentismosModificar.indexOf(filtradosListaAusentismos.get(index));
                    listaAusentismosModificar.remove(modIndex);
                    listaAusentismosBorrar.add(filtradosListaAusentismos.get(index));
                } else if (!listaAusentismosCrear.isEmpty() && listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    int crearIndex = listaAusentismosCrear.indexOf(filtradosListaAusentismos.get(index));
                    listaAusentismosCrear.remove(crearIndex);
                } else {
                    listaAusentismosBorrar.add(filtradosListaAusentismos.get(index));
                }
                int CIndex = listaAusentismos.indexOf(filtradosListaAusentismos.get(index));
                listaAusentismos.remove(CIndex);
                filtradosListaAusentismos.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosAusentismosEmpleado");
            context.update("form:ACEPTAR");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
        }
    }

    public void periodoAusentismo() {
        System.out.println("Fuckin Funciona");
    }
    //Ubicacion Celda Indice Abajo. //Van los que no son NOT NULL.

    public void cambiarIndice(int indice, int celda) {
        System.out.println("Cambiar Indice");
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secuenciaCausa = listaAusentismos.get(index).getCausa().getSecuencia();
                secuenciaAusentismo = listaAusentismos.get(index).getSecuencia();
                secRegistro = listaAusentismos.get(index).getSecuencia();
                Relacion = administrarAusentismos.mostrarRelacion(listaAusentismos.get(index).getSecuencia());
                if (cualCelda == 0) {
                    TipoAusentismo = listaAusentismos.get(index).getTipo().getDescripcion();
                } else if (cualCelda == 3) {
                    Dias = listaAusentismos.get(index).getDias().toString();
                } else if (cualCelda == 4) {
                    Horas = listaAusentismos.get(index).getHoras().toString();
                } else if (cualCelda == 6) {
                    Fechafinaus = listaAusentismos.get(index).getFechafinaus().toString();
                } else if (cualCelda == 7) {
                    Fechaexpedicion = listaAusentismos.get(index).getFechaexpedicion().toString();
                } else if (cualCelda == 8) {
                    InicioPago = listaAusentismos.get(index).getFechainipago().toString();
                } else if (cualCelda == 9) {
                    FinPago = listaAusentismos.get(index).getFechafinpago().toString();
                } else if (cualCelda == 10) {
                    Porcentaje = listaAusentismos.get(index).getPorcentajeindividual().toString();
                } else if (cualCelda == 11) {
                    BaseLiquidacion = listaAusentismos.get(index).getBaseliquidacion().toString();
                } else if (cualCelda == 12) {
                    Forma = listaAusentismos.get(index).getFormaliquidacion();
                } else if (cualCelda == 13) {
                    AD = listaAusentismos.get(index).getAccidente().getDescripcioncaso();
                } else if (cualCelda == 14) {
                    Enfermedad = listaAusentismos.get(index).getEnfermedad().getCategoria().getDescripcion();
                } else if (cualCelda == 15) {
                    NumeroCertificado = listaAusentismos.get(index).getNumerocertificado();
                } else if (cualCelda == 16) {
                    Diagnostico = listaAusentismos.get(index).getDiagnosticocategoria().getCodigo();
                } else if (cualCelda == 17) {
                    Prorrogas = listaAusentismos.get(index).getProrroga().getProrrogaAusentismo();
                } else if (cualCelda == 18) {
                    Relacionn = listaAusentismos.get(index).getRelacion();
                } else if (cualCelda == 20) {
                    Tercero = listaAusentismos.get(index).getTercero().getNombre();
                } else if (cualCelda == 21) {
                    Observacion = listaAusentismos.get(index).getObservaciones();
                }
            } else {
                secuenciaCausa = listaAusentismos.get(index).getCausa().getSecuencia();
                secuenciaAusentismo = listaAusentismos.get(index).getSecuencia();
                secRegistro = filtradosListaAusentismos.get(index).getSecuencia();
                Relacion = administrarAusentismos.mostrarRelacion(listaAusentismos.get(index).getSecuencia());
                if (cualCelda == 3) {
                    Dias = filtradosListaAusentismos.get(index).getDias().toString();
                } else if (cualCelda == 4) {
                    Horas = filtradosListaAusentismos.get(index).getHoras().toString();
                } else if (cualCelda == 6) {
                    Fechafinaus = filtradosListaAusentismos.get(index).getFechafinaus().toString();
                } else if (cualCelda == 7) {
                    Fechaexpedicion = filtradosListaAusentismos.get(index).getFechaexpedicion().toString();
                } else if (cualCelda == 8) {
                    InicioPago = filtradosListaAusentismos.get(index).getFechainipago().toString();
                } else if (cualCelda == 9) {
                    FinPago = filtradosListaAusentismos.get(index).getFechafinpago().toString();
                } else if (cualCelda == 10) {
                    Porcentaje = filtradosListaAusentismos.get(index).getPorcentajeindividual().toString();
                } else if (cualCelda == 11) {
                    BaseLiquidacion = filtradosListaAusentismos.get(index).getBaseliquidacion().toString();
                } else if (cualCelda == 12) {
                    Forma = filtradosListaAusentismos.get(index).getFormaliquidacion();
                } else if (cualCelda == 13) {
                    AD = filtradosListaAusentismos.get(index).getAccidente().getDescripcioncaso();
                } else if (cualCelda == 14) {
                    Enfermedad = filtradosListaAusentismos.get(index).getEnfermedad().getCategoria().getDescripcion();
                } else if (cualCelda == 15) {
                    NumeroCertificado = filtradosListaAusentismos.get(index).getNumerocertificado();
                } else if (cualCelda == 16) {
                    Diagnostico = filtradosListaAusentismos.get(index).getDiagnosticocategoria().getCodigo();
                } else if (cualCelda == 17) {
                    Prorrogas = filtradosListaAusentismos.get(index).getProrroga().getProrrogaAusentismo();
                } else if (cualCelda == 18) {
                    Relacionn = filtradosListaAusentismos.get(index).getRelacion();
                } else if (cualCelda == 20) {
                    Tercero = filtradosListaAusentismos.get(index).getTercero().getNombre();
                } else if (cualCelda == 21) {
                    Observacion = filtradosListaAusentismos.get(index).getObservaciones();
                }
            }
        }
        System.out.println("Index: " + index + " Celda: " + celda);
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosAusentismosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "AusentismosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosAusentismosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "AusentismosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

//MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarAusentismos = listaAusentismos.get(index);
            }
            if (tipoLista == 1) {
                editarAusentismos = filtradosListaAusentismos.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarTiposAusentismos");
                context.execute("editarTiposAusentismos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarClasesAusentismos");
                context.execute("editarClasesAusentismos.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarCausasAusentismos");
                context.execute("editarCausasAusentismos.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarDias");
                context.execute("editarDias.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarHoras");
                context.execute("editarHoras.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarFIausencias");
                context.execute("editarFIausencias.show()");
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarFFausencias");
                context.execute("editarFFausencias.show()");
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarFexpediciones");
                context.execute("editarFexpediciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarFIpagos");
                context.execute("editarFIpagos.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarFFpagos");
                context.execute("editarFFpagos.show()");
                cualCelda = -1;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editarPorcentajes");
                context.execute("editarPorcentajes.show()");
                cualCelda = -1;
            } else if (cualCelda == 11) {
                context.update("formularioDialogos:editarBliquidaciones");
                context.execute("editarBliquidaciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:editarFliquidaciones");
                context.execute("editarFliquidaciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 13) {
                context.update("formularioDialogos:editarAccidentes");
                context.execute("editarAccidentes.show()");
                cualCelda = -1;
            } else if (cualCelda == 14) {
                context.update("formularioDialogos:editarEnfermedades");
                context.execute("editarEnfermedades.show()");
                cualCelda = -1;
            } else if (cualCelda == 15) {
                context.update("formularioDialogos:editarNcertificados");
                context.execute("editarNcertificados.show()");
                cualCelda = -1;
            } else if (cualCelda == 16) {
                context.update("formularioDialogos:editarDiagnosticos");
                context.execute("editarDiagnosticos.show()");
                cualCelda = -1;
            } else if (cualCelda == 17) {
                context.update("formularioDialogos:editarProrrogas");
                context.execute("editarProrrogas.show()");
                cualCelda = -1;
            } else if (cualCelda == 18) {
                context.update("formularioDialogos:editarRelacion");
                context.execute("editarRelacion.show()");
                cualCelda = -1;
            } else if (cualCelda == 20) {
                context.update("formularioDialogos:editarTerceros");
                context.execute("editarTerceros.show()");
                cualCelda = -1;
            } else if (cualCelda == 21) {
                context.update("formularioDialogos:editarObservaciones");
                context.execute("editarObservaciones.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("TIPO")) {
            if (tipoNuevo == 1) {
                TipoAusentismo = nuevoAusentismo.getTipo().getDescripcion();
            } else if (tipoNuevo == 2) {
                TipoAusentismo = duplicarAusentismo.getTipo().getDescripcion();
            }
        } else if (Campo.equals("CLASE")) {
            if (tipoNuevo == 1) {
                ClaseAusentismo = nuevoAusentismo.getClase().getDescripcion();
            } else if (tipoNuevo == 2) {
                ClaseAusentismo = duplicarAusentismo.getClase().getDescripcion();
            }
        } else if (Campo.equals("CAUSA")) {
            if (tipoNuevo == 1) {
                CausaAusentismo = nuevoAusentismo.getCausa().getDescripcion();
            } else if (tipoNuevo == 2) {
                CausaAusentismo = duplicarAusentismo.getCausa().getDescripcion();
            }
        } else if (Campo.equals("PORCENTAJE")) {
            if (tipoNuevo == 1) {
                Porcentaje = nuevoAusentismo.getPorcentajeindividual().toString();
            } else if (tipoNuevo == 2) {
                Porcentaje = duplicarAusentismo.getPorcentajeindividual().toString();
            }
        } else if (Campo.equals("BASE")) {
            if (tipoNuevo == 1) {
                BaseLiquidacion = nuevoAusentismo.getBaseliquidacion().toString();
            } else if (tipoNuevo == 2) {
                BaseLiquidacion = duplicarAusentismo.getBaseliquidacion().toString();
            }
        } else if (Campo.equals("FORMA")) {
            if (tipoNuevo == 1) {
                Forma = nuevoAusentismo.getFormaliquidacion();
            } else if (tipoNuevo == 2) {
                Forma = duplicarAusentismo.getFormaliquidacion();
            }
        } else if (Campo.equals("AD")) {
            if (tipoNuevo == 1) {
                AD = nuevoAusentismo.getAccidente().getDescripcioncaso();
            } else if (tipoNuevo == 2) {
                AD = duplicarAusentismo.getAccidente().getDescripcioncaso();
            }
        } else if (Campo.equals("ENFERMEDADES")) {
            if (tipoNuevo == 1) {
                Enfermedad = nuevoAusentismo.getEnfermedad().getCategoria().getDescripcion();
            } else if (tipoNuevo == 2) {
                Enfermedad = duplicarAusentismo.getEnfermedad().getCategoria().getDescripcion();
            }
        } else if (Campo.equals("DIAGNOSTICO")) {
            if (tipoNuevo == 1) {
                Diagnostico = nuevoAusentismo.getDiagnosticocategoria().getCodigo();
            } else if (tipoNuevo == 2) {
                Diagnostico = duplicarAusentismo.getDiagnosticocategoria().getCodigo();
            }
        } else if (Campo.equals("PRORROGA")) {
            if (tipoNuevo == 1) {
                Prorroga = nuevoAusentismo.getProrroga().getProrrogaAusentismo();
            } else if (tipoNuevo == 2) {
                Prorroga = duplicarAusentismo.getProrroga().getProrrogaAusentismo();
            }
        } else if (Campo.equals("TERCERO")) {
            if (tipoNuevo == 1) {
                Tercero = nuevoAusentismo.getTercero().getNombre();
            } else if (tipoNuevo == 2) {
                Tercero = duplicarAusentismo.getTercero().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getTipo().setDescripcion(TipoAusentismo);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getTipo().setDescripcion(TipoAusentismo);
            }
            for (int i = 0; i < listaTiposAusentismos.size(); i++) {
                if (listaTiposAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTipo");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipo");
                }
                listaTiposAusentismos.clear();
                getListaTiposAusentismos();
            } else {
                context.update("form:tiposAusentismosDialogo");
                context.execute("tiposAusentismosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTipo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipo");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CLASE")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getClase().setDescripcion(ClaseAusentismo);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getClase().setDescripcion(ClaseAusentismo);
            }

            for (int i = 0; i < listaClasesAusentismos.size(); i++) {
                if (listaClasesAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setClase(listaClasesAusentismos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaClase");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setClase(listaClasesAusentismos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarClase");
                }
                listaClasesAusentismos.clear();
                getListaClasesAusentismos();
            } else {
                context.update("form:clasesAusentismosDialogo");
                context.execute("clasesAusentismosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaClase");

                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarClase");

                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CAUSA")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getCausa().setDescripcion(CausaAusentismo);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getCausa().setDescripcion(CausaAusentismo);
            }

            for (int i = 0; i < listaCausasAusentismos.size(); i++) {
                if (listaCausasAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setCausa(listaCausasAusentismos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCausa");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setCausa(listaCausasAusentismos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCausa");
                }
                listaCausasAusentismos.clear();
                getListaCausasAusentismos();
            } else {
                context.update("form:causasAusentismosDialogo");
                context.execute("causasAusentismosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCausa");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCausa");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PORCENTAJE")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.setPorcentajeindividual(new BigInteger(Porcentaje));
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.setPorcentajeindividual(new BigInteger(Porcentaje));
            }

            for (int i = 0; i < listaPorcentaje.size(); i++) {
                if (listaPorcentaje.get(i).startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setPorcentajeindividual(new BigInteger(listaPorcentaje.get(indiceUnicoElemento)));
                    context.update("formularioDialogos:duplicarPorcentaje");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setPorcentajeindividual(new BigInteger(listaPorcentaje.get(indiceUnicoElemento)));
                    context.update("formularioDialogos:duplicarPorcentaje");
                }
                listaPorcentaje.clear();
                getListaPorcentaje();
            } else {
                context.update("form:porcentajesDialogo");
                context.execute("porcentajesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:duplicarPorcentaje");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarPorcentaje");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("BASE")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.setBaseliquidacion(new BigInteger(BaseLiquidacion));
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.setBaseliquidacion(new BigInteger(BaseLiquidacion));
            }

            for (int i = 0; i < listaIBCS.size(); i++) {
                if (listaIBCS.get(i).getValor().toString().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setBaseliquidacion(listaIBCS.get(indiceUnicoElemento).getValor().toBigInteger());
                    context.update("formularioDialogos:duplicarIBCS");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setBaseliquidacion(listaIBCS.get(indiceUnicoElemento).getValor().toBigInteger());
                    context.update("formularioDialogos:duplicarIBCS");
                }
                listaIBCS.clear();
                getListaIBCS();
            } else {
                context.update("form:ibcsDialogo");
                context.execute("ibcsDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:duplicarIBCS");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarIBCS");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("FORMA")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.setFormaliquidacion(Forma);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.setFormaliquidacion(Forma);
            }

            for (int i = 0; i < listaForma.size(); i++) {
                if (listaForma.get(i).startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setFormaliquidacion(listaForma.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaForma");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setFormaliquidacion(listaForma.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarForma");
                }
                listaForma.clear();
                getListaForma();
            } else {
                context.update("form:formasDialogo");
                context.execute("formasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaForma");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarForma");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("AD")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getAccidente().setDescripcioncaso(AD);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getAccidente().setDescripcioncaso(AD);
            }

            for (int i = 0; i < listaAccidentes.size(); i++) {
                if (listaAccidentes.get(i).getDescripcioncaso().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setAccidente(listaAccidentes.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarAccidente");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setAccidente(listaAccidentes.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarAccidente");
                }
                listaAccidentes.clear();
                getListaAccidentes();
            } else {
                context.update("form:accidentesDialogo");
                context.execute("accidentesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:duplicarAccidente");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarAccidente");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("ENFERMEDADES")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getEnfermedad().getCategoria().setDescripcion(Enfermedad);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getEnfermedad().getCategoria().setDescripcion(Enfermedad);
            }

            for (int i = 0; i < listaEnfermeadadesProfesionales.size(); i++) {
                if (listaEnfermeadadesProfesionales.get(i).getCategoria().getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setEnfermedad(listaEnfermeadadesProfesionales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaEnfermedad");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setEnfermedad(listaEnfermeadadesProfesionales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEnfermedad");
                }
                listaAccidentes.clear();
                getListaAccidentes();
            } else {
                context.update("form:enfermedadesDialogo");
                context.execute("enfermedadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaEnfermedad");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEnfermedad");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("DIAGNOSTICO")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getDiagnosticocategoria().setDescripcion(Diagnostico);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getDiagnosticocategoria().setDescripcion(Diagnostico);
            }

            for (int i = 0; i < listaDiagnosticos.size(); i++) {
                if (listaDiagnosticos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setDiagnosticocategoria(listaDiagnosticos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDiagnostico");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setDiagnosticocategoria(listaDiagnosticos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDiagnostico");
                }
                listaDiagnosticos.clear();
                getListaDiagnosticos();
            } else {
                context.update("form:diagnosticosDialogo");
                context.execute("diagnosticosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:duplicarDiagnostico");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDiagnostico");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PRORROGA")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getProrroga().setProrrogaAusentismo(Prorroga);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getProrroga().setProrrogaAusentismo(Prorroga);
            }

            for (int i = 0; i < listaProrrogas.size(); i++) {
                if (listaProrrogas.get(i).getProrrogaAusentismo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setProrroga(listaProrrogas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProrroga");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setProrroga(listaProrrogas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarProrroga");
                }
                listaProrrogas.clear();
                getListaProrrogas();
            } else {
                context.update("form:prorrogasDialogo");
                context.execute("prorrogasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaProrroga");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarProrroga");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoNuevo == 1) {
                nuevoAusentismo.getTercero().setNombre(Tercero);
            } else if (tipoNuevo == 2) {
                duplicarAusentismo.getTercero().setNombre(Tercero);
            }

            for (int i = 0; i < listaTerceros.size(); i++) {
                if (listaTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoAusentismo.setTercero(listaTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTercero");
                } else if (tipoNuevo == 2) {
                    duplicarAusentismo.setTercero(listaTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTercero");
                }
                listaTerceros.clear();
                getListaTerceros();
            } else {
                context.update("form:tercerosDialogo");
                context.execute("tercerosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTercero");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTercero");
                }
            }
        }
    }

    //DUPLICAR ENCARGATURA
    public void duplicarA() {
        if (index >= 0) {
            duplicarAusentismo = new Soausentismos();

            if (tipoLista == 0) {
                duplicarAusentismo.setEmpleado(seleccionMostrar);
                duplicarAusentismo.setTipo(listaAusentismos.get(index).getTipo());
                duplicarAusentismo.setClase(listaAusentismos.get(index).getClase());
                duplicarAusentismo.setCausa(listaAusentismos.get(index).getCausa());
                duplicarAusentismo.setDias(listaAusentismos.get(index).getDias());
                duplicarAusentismo.setHoras(listaAusentismos.get(index).getHoras());
                duplicarAusentismo.setFecha(listaAusentismos.get(index).getFecha());
                duplicarAusentismo.setFechafinaus(listaAusentismos.get(index).getFechafinaus());
                duplicarAusentismo.setFechaexpedicion(listaAusentismos.get(index).getFechaexpedicion());
                duplicarAusentismo.setFechainipago(listaAusentismos.get(index).getFechainipago());
                duplicarAusentismo.setFechafinpago(listaAusentismos.get(index).getFechafinpago());
                duplicarAusentismo.setPorcentajeindividual(listaAusentismos.get(index).getPorcentajeindividual());
                duplicarAusentismo.setBaseliquidacion(listaAusentismos.get(index).getBaseliquidacion());
                duplicarAusentismo.setFormaliquidacion(listaAusentismos.get(index).getFormaliquidacion());
                duplicarAusentismo.setAccidente(listaAusentismos.get(index).getAccidente());
                duplicarAusentismo.setEnfermedad(listaAusentismos.get(index).getEnfermedad());
                duplicarAusentismo.setNumerocertificado(listaAusentismos.get(index).getNumerocertificado());
                duplicarAusentismo.setDiagnosticocategoria(listaAusentismos.get(index).getDiagnosticocategoria());
                duplicarAusentismo.setProrroga(listaAusentismos.get(index).getProrroga());
                duplicarAusentismo.setRelacion(listaAusentismos.get(index).getRelacion());
                duplicarAusentismo.setRelacionada(listaAusentismos.get(index).getRelacionada());
                duplicarAusentismo.setTercero(listaAusentismos.get(index).getTercero());
                duplicarAusentismo.setObservaciones(listaAusentismos.get(index).getObservaciones());
            }
            if (tipoLista == 1) {
                duplicarAusentismo.setEmpleado(seleccionMostrar);
                duplicarAusentismo.setTipo(filtradosListaAusentismos.get(index).getTipo());
                duplicarAusentismo.setClase(filtradosListaAusentismos.get(index).getClase());
                duplicarAusentismo.setCausa(filtradosListaAusentismos.get(index).getCausa());
                duplicarAusentismo.setDias(filtradosListaAusentismos.get(index).getDias());
                duplicarAusentismo.setHoras(filtradosListaAusentismos.get(index).getHoras());
                duplicarAusentismo.setFecha(filtradosListaAusentismos.get(index).getFecha());
                duplicarAusentismo.setFechafinaus(filtradosListaAusentismos.get(index).getFechafinaus());
                duplicarAusentismo.setFechaexpedicion(filtradosListaAusentismos.get(index).getFechaexpedicion());
                duplicarAusentismo.setFechainipago(filtradosListaAusentismos.get(index).getFechainipago());
                duplicarAusentismo.setFechafinpago(filtradosListaAusentismos.get(index).getFechafinpago());
                duplicarAusentismo.setPorcentajeindividual(filtradosListaAusentismos.get(index).getPorcentajeindividual());
                duplicarAusentismo.setBaseliquidacion(filtradosListaAusentismos.get(index).getBaseliquidacion());
                duplicarAusentismo.setFormaliquidacion(filtradosListaAusentismos.get(index).getFormaliquidacion());
                duplicarAusentismo.setAccidente(filtradosListaAusentismos.get(index).getAccidente());
                duplicarAusentismo.setEnfermedad(filtradosListaAusentismos.get(index).getEnfermedad());
                duplicarAusentismo.setNumerocertificado(filtradosListaAusentismos.get(index).getNumerocertificado());
                duplicarAusentismo.setDiagnosticocategoria(filtradosListaAusentismos.get(index).getDiagnosticocategoria());
                duplicarAusentismo.setProrroga(filtradosListaAusentismos.get(index).getProrroga());
                duplicarAusentismo.setRelacion(filtradosListaAusentismos.get(index).getRelacion());
                duplicarAusentismo.setRelacionada(filtradosListaAusentismos.get(index).getRelacionada());
                duplicarAusentismo.setTercero(filtradosListaAusentismos.get(index).getTercero());
                duplicarAusentismo.setObservaciones(filtradosListaAusentismos.get(index).getObservaciones());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarAusentismo");
            context.execute("DuplicarAusentismoEmpleado.show()");
            index = -1;
            secRegistro = null;
        }
    }

    //LIMPIAR NUEVO AUSENTISMO
    public void limpiarNuevoAusentismo() {
        FacesContext c = FacesContext.getCurrentInstance();
        nuevoAusentismo = new Soausentismos();
        nuevoAusentismo.setTipo(new Tiposausentismos());
        nuevoAusentismo.setCausa(new Causasausentismos());
        nuevoAusentismo.setClase(new Clasesausentismos());
        index = -1;
        secRegistro = null;
        System.out.println("Entro a Bandera B. 1");
        RequestContext context = RequestContext.getCurrentInstance();
        //context.update("formularioDialogos:limpiar");
    }

//Salir NUEVO AUSENTISMO
    public void salirNuevoAusentismo() {
        FacesContext c = FacesContext.getCurrentInstance();
        nuevoAusentismo = new Soausentismos();
        nuevoAusentismo.setTipo(new Tiposausentismos());
        nuevoAusentismo.setCausa(new Causasausentismos());
        nuevoAusentismo.setClase(new Clasesausentismos());
        index = -1;
        secRegistro = null;
        System.out.println("Entro a Bandera B. 1");
        botonLimpiar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:limpiar");
        botonLimpiar.setStyle("position: absolute; left: 50px; top: 400px;");
        botonAgregar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:agregarNA");
        botonAgregar.setStyle("position: absolute; left: 350px; top: 400px;");
        botonCancelar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:cancelarNA");
        botonCancelar.setStyle("position: absolute; left: 450px; top: 400px;");
        altoDialogoNuevo = "430";
        banderaBotones = 0;
        colapsado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:NuevoAusentismoEmpleado");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();

        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0) {
            altoTabla = "121";
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            ATipo = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ATipo");
            ATipo.setFilterStyle("width: 60px");
            AClase = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AClase");
            AClase.setFilterStyle("width: 60px");
            ACausa = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ACausa");
            ACausa.setFilterStyle("width: 60px");
            ADias = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ADias");
            ADias.setFilterStyle("width: 60px");
            AHoras = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AHoras");
            AHoras.setFilterStyle("width: 60px");
            AFecha = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFecha");
            AFecha.setFilterStyle("width: 60px");
            AFechaFinaus = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaFinaus");
            AFechaFinaus.setFilterStyle("width: 60px");
            AFechaExpedicion = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaExpedicion");
            AFechaExpedicion.setFilterStyle("width: 60px");
            AFechaInipago = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaInipago");
            AFechaInipago.setFilterStyle("width: 60px");
            AFechaFinpago = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaFinpago");
            AFechaFinpago.setFilterStyle("width: 60px");
            APorcentaje = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:APorcentaje");
            APorcentaje.setFilterStyle("width: 60px");
            ABase = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ABase");
            ABase.setFilterStyle("width: 60px");
            AForma = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AForma");
            AForma.setFilterStyle("width: 60px");
            ADescripcionCaso = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ADescripcionCaso");
            ADescripcionCaso.setFilterStyle("width: 60px");
            AEnfermedad = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AEnfermedad");
            AEnfermedad.setFilterStyle("width: 60px");
            ANumero = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ANumero");
            ANumero.setFilterStyle("width: 60px");
            ADiagnostico = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ADiagnostico");
            ADiagnostico.setFilterStyle("width: 60px");
            AProrroga = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AProrroga");
            AProrroga.setFilterStyle("width: 60px");
            ANumero = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ANumero");
            ANumero.setFilterStyle("width: 60px");
            ARelacion = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ARelacion");
            ARelacion.setFilterStyle("width: 60px");
            ARelacionada = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ARelacionada");
            ARelacionada.setFilterStyle("width: 60px");
            ATercero = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ATercero");
            ATercero.setFilterStyle("width: 60px");
            AObservaciones = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AObservaciones");
            AObservaciones.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosAusentismosEmpleado");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            altoTabla = "145";
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            cerrarFiltrado();
        }
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:tiposAusentismosDialogo");
                context.execute("tiposAusentismosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:clasesAusentismosDialogo");
                context.execute("clasesAusentismosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:causasAusentismosDialogo");
                context.execute("causasAusentismosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:porcentajesDialogo");
                context.execute("porcentajesDialogo.show()");
            } else if (cualCelda == 11) {
                context.update("formularioDialogos:ibcsDialogo");
                context.execute("ibcsDialogo.show()");
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:formasDialogo");
                context.execute("formasDialogo.show()");
            } else if (cualCelda == 13) {
                context.update("formularioDialogos:accidentesDialogo");
                context.execute("accidentesDialogo.show()");
            } else if (cualCelda == 14) {
                context.update("formularioDialogos:enfermedadesDialogo");
                context.execute("enfermedadesDialogo.show()");
            } else if (cualCelda == 16) {
                context.update("formularioDialogos:diagnosticosDialogo");
                context.execute("diagnosticosDialogo.show()");
            } else if (cualCelda == 17) {
                context.update("formularioDialogos:prorrogasDialogo");
                context.execute("prorrogasDialogo.show()");
            } else if (cualCelda == 19) {
                context.update("formularioDialogos:tercerosDialogo");
                context.execute("tercerosDialogo.show()");
            }
        }
    }

    public void experimento(Date aux) {
        System.out.println("experimento Valor experimento : " + aux);
        System.out.println("experimento Nuevo Ausentismo Fecha Inicial: " + nuevoAusentismo.getFecha());
    }

    //CREAR NOVEDADES
    public void agregarNuevoAusentismo() {
        int pasa = 0;
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Nuevo Ausentismo Fecha Inicial: " + nuevoAusentismo.getFecha());
        System.out.println("Nuevo Ausentismo Fecha Inicial: " + nuevoAusentismo.getFecha());
        if (nuevoAusentismo.getFecha() == null) {
            System.out.println("Entro a Fecha ");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicio Ausentismo\n";
            pasa++;
        }

        if (nuevoAusentismo.getTipo().getDescripcion().equals(" ") || nuevoAusentismo.getTipo().getDescripcion().equals("")) {
            System.out.println("Entro a Tipo");
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }

        if (nuevoAusentismo.getCausa().getDescripcion().equals("")) {
            System.out.println("Entro a Causa");
            mensajeValidacion = mensajeValidacion + " * Causa\n";
            pasa++;
        }
        if (nuevoAusentismo.getClase().getDescripcion().equals("")) {
            System.out.println("Entro a Clase");
            mensajeValidacion = mensajeValidacion + " * Clase\n";
            pasa++;
        }

        if (nuevoAusentismo.getFormaliquidacion() == null) {
            System.out.println("Entro a Forma");
            mensajeValidacion = mensajeValidacion + " * Forma Liquidación\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevoAusentismo");
            context.execute("validacionNuevoAusentismo.show()");
        }

        if (pasa == 0) {
            if (bandera == 1) {
                altoTabla = "135";
                System.out.println("Activar");
                System.out.println("TipoLista= " + tipoLista);
                cerrarFiltrado();
            }
            cambiosPagina = false;
            context.update("form:ACEPTAR");

            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevoAusentismo.setSecuencia(l);
            nuevoAusentismo.setEmpleado(seleccionMostrar);
            listaAusentismosCrear.add(nuevoAusentismo);
            listaAusentismos.add(nuevoAusentismo);
            nuevoAusentismo = new Soausentismos();
            nuevoAusentismo.setTipo(new Tiposausentismos());
            nuevoAusentismo.setClase(new Clasesausentismos());
            nuevoAusentismo.setCausa(new Causasausentismos());
            nuevoAusentismo.setPorcentajeindividual(BigInteger.valueOf(0));
            nuevoAusentismo.setBaseliquidacion(BigInteger.valueOf(0));
            nuevoAusentismo.setFormaliquidacion(" ");
            nuevoAusentismo.setRelacionadaBool(false);
            nuevoAusentismo.setAccidente(new Soaccidentes());
            nuevoAusentismo.setEnfermedad(new EnfermeadadesProfesionales());
            nuevoAusentismo.setDiagnosticocategoria(new Diagnosticoscategorias());
            nuevoAusentismo.setProrroga(new Soausentismos());
            nuevoAusentismo.setTercero(new Terceros());

            context.update("form:datosAusentismosEmpleado");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.execute("NuevoAusentismoEmpleado.hide()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {

        int pasa = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = false;
        context.update("form:ACEPTAR");

        if (duplicarAusentismo.getFecha() == null) {
            System.out.println("Entro a Fecha ");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicio Ausentismo\n";
            pasa++;
        }

        if (duplicarAusentismo.getTipo().getDescripcion().equals(" ") || duplicarAusentismo.getTipo().getDescripcion().equals("")) {
            System.out.println("Entro a Tipo");
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }

        if (duplicarAusentismo.getCausa().getDescripcion().equals(" ")) {
            System.out.println("Entro a Causa");
            mensajeValidacion = mensajeValidacion + " * Causa\n";
            pasa++;
        }
        if (duplicarAusentismo.getClase().getDescripcion().equals(" ")) {
            System.out.println("Entro a Clase");
            mensajeValidacion = mensajeValidacion + " * Clase\n";
            pasa++;
        }

        if (duplicarAusentismo.getFormaliquidacion().equals(" ")) {
            System.out.println("Entro a Forma");
            mensajeValidacion = mensajeValidacion + " * Forma Liquidación\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevoAusentismo");
            context.execute("validacionNuevoAusentismo.show()");
        }
        k++;
        l = BigInteger.valueOf(k);
        duplicarAusentismo.setSecuencia(l);
        listaAusentismos.add(duplicarAusentismo);
        listaAusentismosCrear.add(duplicarAusentismo);

        context.update("form:datosAusentismosEmpleado");
        index = -1;
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }

        if (bandera == 1) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            altoTabla = "135";
            cerrarFiltrado();
        }
        duplicarAusentismo.setEmpleado(seleccionMostrar);
        duplicarAusentismo = new Soausentismos();
        context.update("formularioDialogos:DuplicarAusentismoEmpleado");
        context.execute("DuplicarAusentismoEmpleado.hide()");
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Ausentismos
     */
    public void limpiarduplicarAusentismos() {
        duplicarAusentismo = new Soausentismos();
        duplicarAusentismo.setTipo(new Tiposausentismos());
        duplicarAusentismo.setCausa(new Causasausentismos());
        duplicarAusentismo.setClase(new Clasesausentismos());
        index = -1;
        secRegistro = null;
    }

    public void salirduplicarAusentismos() {
        duplicarAusentismo = new Soausentismos();
        duplicarAusentismo.setTipo(new Tiposausentismos());
        duplicarAusentismo.setCausa(new Causasausentismos());
        duplicarAusentismo.setClase(new Clasesausentismos());
        index = -1;
        secRegistro = null;
        FacesContext c = FacesContext.getCurrentInstance();
        System.out.println("Entro a Bandera B. 1");
        botonLimpiarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:limpiarDuplicado");
        botonLimpiarD.setStyle("position: absolute; left: 50px; top: 400px;");
        botonAgregarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:agregarNADuplicado");
        botonAgregarD.setStyle("position: absolute; left: 350px; top: 400px;");
        botonCancelarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:cancelarNADuplicado");
        botonCancelarD.setStyle("position: absolute; left: 450px; top: 400px;");
        altoDialogoDuplicar = "430";
        banderaBotonesD = 0;
        colapsado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarAusentismoEmpleado");
    }

    //GUARDAR
    public void guardarCambiosAusentismos() {

        if (guardado == false) {
            System.out.println("Realizando Operaciones Ausentismos");

            if (!listaAusentismosBorrar.isEmpty()) {
                for (int i = 0; i < listaAusentismosBorrar.size(); i++) {
                    System.out.println("Borrando..." + listaAusentismosBorrar.size());

                    if (listaAusentismosBorrar.get(i).getDias() == null) {
                        listaAusentismosBorrar.get(i).setDias(null);
                    }
                    if (listaAusentismosBorrar.get(i).getHoras() == null) {
                        listaAusentismosBorrar.get(i).setHoras(null);
                    }
                    if (listaAusentismosBorrar.get(i).getFechafinaus() == null) {
                        listaAusentismosBorrar.get(i).setFechafinaus(null);
                    }
                    if (listaAusentismosBorrar.get(i).getFechaexpedicion() == null) {
                        listaAusentismosBorrar.get(i).setFechaexpedicion(null);
                    }
                    if (listaAusentismosBorrar.get(i).getFechainipago() == null) {
                        listaAusentismosBorrar.get(i).setFechainipago(null);
                    }
                    if (listaAusentismosBorrar.get(i).getFechafinpago() == null) {
                        listaAusentismosBorrar.get(i).setFechafinpago(null);
                    }
                    if (listaAusentismosBorrar.get(i).getPorcentajeindividual() == null) {
                        listaAusentismosBorrar.get(i).setPorcentajeindividual(null);
                    }
                    if (listaAusentismosBorrar.get(i).getBaseliquidacion() == null) {
                        listaAusentismosBorrar.get(i).setBaseliquidacion(null);
                    }
                    if (listaAusentismosBorrar.get(i).getFormaliquidacion() == null) {
                        listaAusentismosBorrar.get(i).setFormaliquidacion(null);
                    }
                    if (listaAusentismosBorrar.get(i).getAccidente().getSecuencia() == null) {
                        listaAusentismosBorrar.get(i).setAccidente(null);
                    }
                    if (listaAusentismosBorrar.get(i).getEnfermedad().getSecuencia() == null) {
                        listaAusentismosBorrar.get(i).setEnfermedad(null);
                    }
                    if (listaAusentismosBorrar.get(i).getNumerocertificado() == null) {
                        listaAusentismosBorrar.get(i).setNumerocertificado(null);
                    }
                    if (listaAusentismosBorrar.get(i).getDiagnosticocategoria().getSecuencia() == null) {
                        listaAusentismosBorrar.get(i).setDiagnosticocategoria(null);
                    }
                    if (listaAusentismosBorrar.get(i).getProrroga().getSecuencia() == null) {
                        listaAusentismosBorrar.get(i).setProrroga(null);
                    }
                    if (listaAusentismosBorrar.get(i).getRelacion() == null) {
                        listaAusentismosBorrar.get(i).setRelacion(null);
                    }
                    /*
                     * if (listaAusentismosBorrar.get(i).getRelacionadaBool()==
                     * false) {
                     * listaAusentismosBorrar.get(i).setRelacionada("N"); }
                     */
                    if (listaAusentismosBorrar.get(i).getTercero().getSecuencia() == null) {
                        listaAusentismosBorrar.get(i).setTercero(null);
                    }
                    if (listaAusentismosBorrar.get(i).getObservaciones() == null) {
                        listaAusentismosBorrar.get(i).setObservaciones(null);
                    }
                    administrarAusentismos.borrarAusentismos(listaAusentismosBorrar.get(i));
                }
                System.out.println("Entra");
                listaAusentismosBorrar.clear();
            }

            if (!listaAusentismosCrear.isEmpty()) {
                for (int i = 0; i < listaAusentismosCrear.size(); i++) {
                    System.out.println("Creando...");

                    if (listaAusentismosCrear.get(i).getDias() == null) {
                        listaAusentismosCrear.get(i).setDias(null);
                    }
                    if (listaAusentismosCrear.get(i).getHoras() == null) {
                        listaAusentismosCrear.get(i).setHoras(null);
                    }
                    if (listaAusentismosCrear.get(i).getFechafinaus() == null) {
                        listaAusentismosCrear.get(i).setFechafinaus(null);
                    }
                    if (listaAusentismosCrear.get(i).getFechaexpedicion() == null) {
                        listaAusentismosCrear.get(i).setFechaexpedicion(null);
                    }
                    if (listaAusentismosCrear.get(i).getFechainipago() == null) {
                        listaAusentismosCrear.get(i).setFechainipago(null);
                    }
                    if (listaAusentismosCrear.get(i).getFechafinpago() == null) {
                        listaAusentismosCrear.get(i).setFechafinpago(null);
                    }
                    if (listaAusentismosCrear.get(i).getPorcentajeindividual() == null) {
                        listaAusentismosCrear.get(i).setPorcentajeindividual(null);
                    }
                    if (listaAusentismosCrear.get(i).getBaseliquidacion() == null) {
                        listaAusentismosCrear.get(i).setBaseliquidacion(null);
                    }
                    if (listaAusentismosCrear.get(i).getFormaliquidacion() == null) {
                        listaAusentismosCrear.get(i).setFormaliquidacion(null);
                    }
                    if (listaAusentismosCrear.get(i).getAccidente().getSecuencia() == null) {
                        listaAusentismosCrear.get(i).setAccidente(null);
                    }
                    if (listaAusentismosCrear.get(i).getEnfermedad().getSecuencia() == null) {
                        listaAusentismosCrear.get(i).setEnfermedad(null);
                    }
                    if (listaAusentismosCrear.get(i).getNumerocertificado() == null) {
                        listaAusentismosCrear.get(i).setNumerocertificado(null);
                    }
                    if (listaAusentismosCrear.get(i).getDiagnosticocategoria().getSecuencia() == null) {
                        listaAusentismosCrear.get(i).setDiagnosticocategoria(null);
                    }
                    if (listaAusentismosCrear.get(i).getProrroga().getSecuencia() == null) {
                        listaAusentismosCrear.get(i).setProrroga(null);
                    }
                    if (listaAusentismosCrear.get(i).getRelacion() == null) {
                        listaAusentismosCrear.get(i).setRelacion(null);
                    }
                    if (listaAusentismosCrear.get(i).getRelacionadaBool() == false) {
                        listaAusentismosCrear.get(i).setRelacionada("N");
                    }
                    if (listaAusentismosCrear.get(i).getTercero().getSecuencia() == null) {
                        listaAusentismosCrear.get(i).setTercero(null);
                    }
                    if (listaAusentismosCrear.get(i).getObservaciones() == null) {
                        listaAusentismosCrear.get(i).setObservaciones(null);
                    }
                    administrarAusentismos.crearAusentismos(listaAusentismosCrear.get(i));
                }
                System.out.println("LimpiaLista");
                listaAusentismosCrear.clear();
            }
            if (!listaAusentismosModificar.isEmpty()) {
                administrarAusentismos.modificarAusentismos(listaAusentismosModificar);
                listaAusentismosModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaAusentismos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = true;
            context.update("form:ACEPTAR");
            context.update("form:datosAusentismosEmpleado");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        System.out.println("Tamaño lista: " + listaAusentismosCrear.size());
        System.out.println("Valor k: " + k);
        index = -1;
        secRegistro = null;
    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            altoTabla = "135";
            cerrarFiltrado();
        }
        FacesContext d = FacesContext.getCurrentInstance();

        botonLimpiar = (CommandButton) d.getViewRoot().findComponent("formularioDialogos:limpiar");
        botonLimpiar.setStyle("position: absolute; left: 50px; top: 400px;");
        botonAgregar = (CommandButton) d.getViewRoot().findComponent("formularioDialogos:agregarNA");
        botonAgregar.setStyle("position: absolute; left: 350px; top: 400px;");
        botonCancelar = (CommandButton) d.getViewRoot().findComponent("formularioDialogos:cancelarNA");
        botonCancelar.setStyle("position: absolute; left: 450px; top: 400px;");
        altoDialogoNuevo = "430";
        botonLimpiarD = (CommandButton) d.getViewRoot().findComponent("formularioDialogos:limpiarDuplicado");
        botonLimpiarD.setStyle("position: absolute; left: 50px; top: 400px;");
        botonAgregarD = (CommandButton) d.getViewRoot().findComponent("formularioDialogos:agregarNADuplicado");
        botonAgregarD.setStyle("position: absolute; left: 350px; top: 400px;");
        botonCancelarD = (CommandButton) d.getViewRoot().findComponent("formularioDialogos:cancelarNADuplicado");
        botonCancelarD.setStyle("position: absolute; left: 450px; top: 400px;");
        altoDialogoDuplicar = "430";
        banderaBotonesD = 0;
        banderaBotones = 0;
        listaAusentismosBorrar.clear();
        listaAusentismosCrear.clear();
        listaAusentismosModificar.clear();
        colapsado = true;
        index = -1;
        secRegistro = null;
        listaAusentismos = null;
        guardado = true;
        permitirIndex = true;
        cambiosPagina = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosAusentismosEmpleado");
        context.update("formularioDialogos:nuevoAusentismo");
        context.update("formularioDialogos:duplicarAusentismo");
        context.update("formularioDialogos:NuevoAusentismoEmpleado");
        context.update("formularioDialogos:DuplicarAusentismoEmpleado");

    }

    public void cambiosToggle() {
        System.out.println("cambiosToggle");
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaBotones == 0) {
            System.out.println("Entro a Bandera B. 0");
            botonLimpiar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:limpiar");
            botonLimpiar.setStyle("position: absolute; left: 50px; top: 570px;");
            botonAgregar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:agregarNA");
            botonAgregar.setStyle("position: absolute; left: 350px; top: 570px;");
            botonCancelar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:cancelarNA");
            botonCancelar.setStyle("position: absolute; left: 450px; top: 570px;");
            altoDialogoNuevo = "530";
            banderaBotones = 1;
        } else if (banderaBotones == 1) {
            System.out.println("Entro a Bandera B. 1");
            botonLimpiar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:limpiar");
            botonLimpiar.setStyle("position: absolute; left: 50px; top: 400px;");
            botonAgregar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:agregarNA");
            botonAgregar.setStyle("position: absolute; left: 350px; top: 400px;");
            botonCancelar = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:cancelarNA");
            botonCancelar.setStyle("position: absolute; left: 450px; top: 400px;");
            altoDialogoNuevo = "430";
            banderaBotones = 0;
        }

        RequestContext context = RequestContext.getCurrentInstance();
        //context.update("formularioDialogos:limpiar");
        context.update("formularioDialogos:nuevoAusentismo");
        context.update("formularioDialogos:NuevoAusentismoEmpleado");
        context.execute("NuevoAusentismoEmpleado.show()");
    }

    public void cambiosToggleD() {
        System.out.println("cambiosToggle");
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaBotonesD == 0) {
            System.out.println("Entro a Bandera B. 0");
            botonLimpiarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:limpiarDuplicado");
            botonLimpiarD.setStyle("position: absolute; left: 50px; top: 570px;");
            botonAgregarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:agregarNADuplicado");
            botonAgregarD.setStyle("position: absolute; left: 350px; top: 570px;");
            botonCancelarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:cancelarNADuplicado");
            botonCancelarD.setStyle("position: absolute; left: 450px; top: 570px;");
            altoDialogoDuplicar = "530";
            banderaBotonesD = 1;
        } else if (banderaBotonesD == 1) {
            System.out.println("Entro a Bandera B. 1");
            botonLimpiarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:limpiarDuplicado");
            botonLimpiarD.setStyle("position: absolute; left: 50px; top: 400px;");
            botonAgregarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:agregarNADuplicado");
            botonAgregarD.setStyle("position: absolute; left: 350px; top: 400px;");
            botonCancelarD = (CommandButton) c.getViewRoot().findComponent("formularioDialogos:cancelarNADuplicado");
            botonCancelarD.setStyle("position: absolute; left: 450px; top: 400px;");
            altoDialogoDuplicar = "430";
            banderaBotonesD = 0;
        }

        RequestContext context = RequestContext.getCurrentInstance();
        //context.update("formularioDialogos:limpiar");
        context.update("formularioDialogos:duplicarAusentismo");
        context.update("formularioDialogos:DuplicarAusentismoEmpleado");
        context.execute("DuplicarAusentismoEmpleado.show()");
    }

    //SALIR
    public void salir() {
        if (bandera == 1) {
            altoTabla = "135";
            cerrarFiltrado();
        }
        listaAusentismosBorrar.clear();
        listaAusentismosCrear.clear();
        listaAusentismosModificar.clear();
        index = -1;
        secRegistro = null;
        listaAusentismos = null;
        guardado = true;
        cambiosPagina = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosAusentismosEmpleado");
        context.update("form:ACEPTAR");
    }

    public void cerrarFiltrado() {
        FacesContext c = FacesContext.getCurrentInstance();
        ATipo = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ATipo");
        ATipo.setFilterStyle("display: none; visibility: hidden;");
        AClase = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AClase");
        AClase.setFilterStyle("display: none; visibility: hidden;");
        ACausa = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ACausa");
        ACausa.setFilterStyle("display: none; visibility: hidden;");
        ADias = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ADias");
        ADias.setFilterStyle("display: none; visibility: hidden;");
        AHoras = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AHoras");
        AHoras.setFilterStyle("display: none; visibility: hidden;");
        AFecha = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFecha");
        AFecha.setFilterStyle("display: none; visibility: hidden;");
        AFechaFinaus = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaFinaus");
        AFechaFinaus.setFilterStyle("display: none; visibility: hidden;");
        AFechaExpedicion = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaExpedicion");
        AFechaExpedicion.setFilterStyle("display: none; visibility: hidden;");
        AFechaInipago = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaInipago");
        AFechaInipago.setFilterStyle("display: none; visibility: hidden;");
        AFechaFinpago = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AFechaFinpago");
        AFechaFinpago.setFilterStyle("display: none; visibility: hidden;");
        APorcentaje = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:APorcentaje");
        APorcentaje.setFilterStyle("display: none; visibility: hidden;");
        ABase = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ABase");
        ABase.setFilterStyle("display: none; visibility: hidden;");
        AForma = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AForma");
        AForma.setFilterStyle("display: none; visibility: hidden;");
        ADescripcionCaso = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ADescripcionCaso");
        ADescripcionCaso.setFilterStyle("display: none; visibility: hidden;");
        AEnfermedad = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AEnfermedad");
        AEnfermedad.setFilterStyle("display: none; visibility: hidden;");
        ANumero = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ANumero");
        ANumero.setFilterStyle("display: none; visibility: hidden;");
        ADiagnostico = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ADiagnostico");
        ADiagnostico.setFilterStyle("display: none; visibility: hidden;");
        AProrroga = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AProrroga");
        AProrroga.setFilterStyle("display: none; visibility: hidden;");
        ANumero = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ANumero");
        ANumero.setFilterStyle("display: none; visibility: hidden;");
        ARelacion = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ARelacion");
        ARelacion.setFilterStyle("display: none; visibility: hidden;");
        ARelacionada = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ARelacionada");
        ARelacionada.setFilterStyle("display: none; visibility: hidden;");
        ATercero = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:ATercero");
        ATercero.setFilterStyle("display: none; visibility: hidden;");
        AObservaciones = (Column) c.getViewRoot().findComponent("form:datosAusentismosEmpleado:AObservaciones");
        AObservaciones.setFilterStyle("display: none; visibility: hidden;");
        RequestContext.getCurrentInstance().update("form:datosAusentismosEmpleado");
        bandera = 0;
        filtradosListaAusentismos = null;
        tipoLista = 0;
    }

    //GETTER & SETTER
    public List<Soausentismos> getListaAusentismos() {
        if (listaAusentismos == null && secuenciaEmpleado != null) {
            listaAusentismos = administrarAusentismos.ausentismosEmpleado(secuenciaEmpleado);
            if (listaAusentismos != null && !listaAusentismos.isEmpty()) {
                for (int i = 0; i < listaAusentismos.size(); i++) {
                    listaAusentismos.get(i).setRelacion(administrarAusentismos.mostrarRelacion(listaAusentismos.get(i).getSecuencia()));
                }
            }
        }
        return listaAusentismos;
    }

    public void setListaAusentismos(List<Soausentismos> listaAusentismos) {
        this.listaAusentismos = listaAusentismos;
    }

    public List<Soausentismos> getFiltradosListaAusentismos() {
        return filtradosListaAusentismos;
    }

    public void setFiltradosListaAusentismos(List<Soausentismos> filtradosListaAusentismos) {
        this.filtradosListaAusentismos = filtradosListaAusentismos;
    }

    public List<Empleados> getListaEmpleadosAusentismo() {
        if (listaEmpleadosAusentismo == null) {
            listaEmpleadosAusentismo = administrarAusentismos.lovEmpleados();
            if (!listaEmpleadosAusentismo.isEmpty()) {
                seleccionMostrar = listaEmpleadosAusentismo.get(0);
                secuenciaEmpleado = seleccionMostrar.getSecuencia();
                System.out.println(seleccionMostrar.getSecuencia());
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosEmpleados");
                System.out.println("Seleccionado: " + seleccionMostrar.getPersona().getNombreCompleto());
            }
        }
        return listaEmpleadosAusentismo;
    }

    public void setListaEmpleadosAusentismo(List<Empleados> listaEmpleadosAusentismo) {
        this.listaEmpleadosAusentismo = listaEmpleadosAusentismo;
    }

    public List<Empleados> getFiltradosListaEmpleadosAusentismo() {
        return filtradosListaEmpleadosAusentismo;
    }

    public void setFiltradosListaEmpleadosAusentismo(List<Empleados> filtradosListaEmpleadosAusentismo) {
        this.filtradosListaEmpleadosAusentismo = filtradosListaEmpleadosAusentismo;
    }

    public Empleados getSeleccionMostrar() {
        return seleccionMostrar;
    }

    public void setSeleccionMostrar(Empleados seleccionMostrar) {
        this.seleccionMostrar = seleccionMostrar;
    }

    public List<Empleados> getListaEmpleados() {
//        if (listaEmpleados == null) {
//            listaEmpleados = administrarAusentismos.lovEmpleados();
//            if (!listaEmpleados.isEmpty()) {
//                seleccionMostrar = listaEmpleados.get(0);
//            }
//        }
//        return listaEmpleados;
        if (listaEmpleados == null) {
            listaEmpleados = administrarAusentismos.lovEmpleados();
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

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Tiposausentismos> getListaTiposAusentismos() {
        if (listaTiposAusentismos == null) {
            listaTiposAusentismos = administrarAusentismos.lovTiposAusentismos();
        }
        return listaTiposAusentismos;
    }

    public void setListaTiposAusentismos(List<Tiposausentismos> listaTiposAusentismos) {
        this.listaTiposAusentismos = listaTiposAusentismos;
    }

    public List<Tiposausentismos> getFiltradoslistaTiposAusentismos() {
        return filtradoslistaTiposAusentismos;
    }

    public void setFiltradoslistaTiposAusentismos(List<Tiposausentismos> filtradoslistaTiposAusentismos) {
        this.filtradoslistaTiposAusentismos = filtradoslistaTiposAusentismos;
    }

    public Tiposausentismos getSeleccionTiposAusentismos() {
        return seleccionTiposAusentismos;
    }

    public void setSeleccionTiposAusentismos(Tiposausentismos seleccionTiposAusentismos) {
        this.seleccionTiposAusentismos = seleccionTiposAusentismos;
    }

    public Clasesausentismos getSeleccionClasesAusentismos() {
        return seleccionClasesAusentismos;
    }

    public void setSeleccionClasesAusentismos(Clasesausentismos seleccionClasesAusentismos) {
        this.seleccionClasesAusentismos = seleccionClasesAusentismos;
    }

    public Causasausentismos getSeleccionCausasAusentismos() {
        return seleccionCausasAusentismos;
    }

    public void setSeleccionCausasAusentismos(Causasausentismos seleccionCausasAusentismos) {
        this.seleccionCausasAusentismos = seleccionCausasAusentismos;
    }

    public List<Clasesausentismos> getListaClasesAusentismos() {
        if (listaClasesAusentismos == null) {
            listaClasesAusentismos = administrarAusentismos.lovClasesAusentismos();
        }
        return listaClasesAusentismos;
    }

    public void setListaClasesAusentismos(List<Clasesausentismos> listaClasesAusentismos) {
        this.listaClasesAusentismos = listaClasesAusentismos;
    }

    public List<Clasesausentismos> getFiltradoslistaClasesAusentismos() {
        return filtradoslistaClasesAusentismos;
    }

    public void setFiltradoslistaClasesAusentismos(List<Clasesausentismos> filtradoslistaClasesAusentismos) {
        this.filtradoslistaClasesAusentismos = filtradoslistaClasesAusentismos;
    }

    public List<Causasausentismos> getListaCausasAusentismos() {

        if (listaCausasAusentismos == null) {
            listaCausasAusentismos = administrarAusentismos.lovCausasAusentismos();
        }
        return listaCausasAusentismos;
    }

    public void setListaCausasAusentismos(List<Causasausentismos> listaCausasAusentismos) {
        this.listaCausasAusentismos = listaCausasAusentismos;
    }

    public List<Causasausentismos> getFiltradoslistaCausasAusentismos() {
        return filtradoslistaCausasAusentismos;
    }

    public void setFiltradoslistaCausasAusentismos(List<Causasausentismos> filtradoslistaCausasAusentismos) {
        this.filtradoslistaCausasAusentismos = filtradoslistaCausasAusentismos;
    }

    public List<String> getListaPorcentaje() {
        return listaPorcentaje;
    }

    public void setListaPorcentaje(List<String> listaPorcentaje) {
        this.listaPorcentaje = listaPorcentaje;
    }

    public List<String> getFiltradosListaPorcentajes() {
        return filtradosListaPorcentajes;
    }

    public void setFiltradosListaPorcentajes(List<String> filtradosListaPorcentajes) {
        this.filtradosListaPorcentajes = filtradosListaPorcentajes;
    }

    public String getSeleccionPorcentajes() {
        return seleccionPorcentajes;
    }

    public void setSeleccionPorcentajes(String seleccionPorcentajes) {
        this.seleccionPorcentajes = seleccionPorcentajes;
    }

    public BigInteger getSecuenciaEmpleado() {
        return secuenciaEmpleado;
    }

    public void setSecuenciaEmpleado(BigInteger secuenciaEmpleado) {
        this.secuenciaEmpleado = secuenciaEmpleado;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public List<Ibcs> getListaIBCS() {
        if (listaIBCS == null) {
            listaIBCS = administrarAusentismos.empleadosIBCS(secuenciaEmpleado);
        }
        return listaIBCS;
    }

    public void setListaIBCS(List<Ibcs> listaIBCS) {
        this.listaIBCS = listaIBCS;
    }

    public List<Ibcs> getFiltradosListaIBCS() {
        return filtradosListaIBCS;
    }

    public void setFiltradosListaIBCS(List<Ibcs> filtradosListaIBCS) {
        this.filtradosListaIBCS = filtradosListaIBCS;
    }

    public Ibcs getSeleccionIBCS() {
        return seleccionIBCS;
    }

    public void setSeleccionIBCS(Ibcs seleccionIBCS) {
        this.seleccionIBCS = seleccionIBCS;
    }

    public List<String> getListaForma() {
        return listaForma;
    }

    public void setListaForma(List<String> listaForma) {
        this.listaForma = listaForma;
    }

    public List<String> getFiltradosListaForma() {
        return filtradosListaForma;
    }

    public void setFiltradosListaForma(List<String> filtradosListaForma) {
        this.filtradosListaForma = filtradosListaForma;
    }

    public String getSeleccionForma() {
        return seleccionForma;
    }

    public void setSeleccionForma(String seleccionForma) {
        this.seleccionForma = seleccionForma;
    }

    public List<Soaccidentes> getListaAccidentes() {
        if (listaAccidentes == null) {
            listaAccidentes = administrarAusentismos.lovAccidentes(secuenciaEmpleado);
        }
        return listaAccidentes;
    }

    public void setListaAccidentes(List<Soaccidentes> listaAccidentes) {
        this.listaAccidentes = listaAccidentes;
    }

    public List<Soaccidentes> getFiltradoslistaAccidentes() {
        return filtradoslistaAccidentes;
    }

    public void setFiltradoslistaAccidentes(List<Soaccidentes> filtradoslistaAccidentes) {
        this.filtradoslistaAccidentes = filtradoslistaAccidentes;
    }

    public Soaccidentes getSeleccionAccidentes() {
        return seleccionAccidentes;
    }

    public void setSeleccionAccidentes(Soaccidentes seleccionAccidentes) {
        this.seleccionAccidentes = seleccionAccidentes;
    }

    public List<EnfermeadadesProfesionales> getListaEnfermeadadesProfesionales() {


        listaEnfermeadadesProfesionales = administrarAusentismos.empleadosEP(secuenciaEmpleado);
        return listaEnfermeadadesProfesionales;
    }

    public void setListaEnfermeadadesProfesionales(List<EnfermeadadesProfesionales> listaEnfermeadadesProfesionales) {
        this.listaEnfermeadadesProfesionales = listaEnfermeadadesProfesionales;
    }

    public List<EnfermeadadesProfesionales> getFiltradoslistaEnfermeadadesProfesionales() {
        return filtradoslistaEnfermeadadesProfesionales;
    }

    public void setFiltradoslistaEnfermeadadesProfesionales(List<EnfermeadadesProfesionales> filtradoslistaEnfermeadadesProfesionales) {
        this.filtradoslistaEnfermeadadesProfesionales = filtradoslistaEnfermeadadesProfesionales;
    }

    public EnfermeadadesProfesionales getSeleccionEnfermeadadesProfesionales() {
        return seleccionEnfermeadadesProfesionales;
    }

    public void setSeleccionEnfermeadadesProfesionales(EnfermeadadesProfesionales seleccionEnfermeadadesProfesionales) {
        this.seleccionEnfermeadadesProfesionales = seleccionEnfermeadadesProfesionales;
    }

    public List<Diagnosticoscategorias> getListaDiagnosticos() {

        if (listaDiagnosticos == null) {
            listaDiagnosticos = administrarAusentismos.lovDiagnosticos();
        }
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnosticoscategorias> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<Diagnosticoscategorias> getFiltradoslistaDiagnosticos() {
        return filtradoslistaDiagnosticos;
    }

    public void setFiltradoslistaDiagnosticos(List<Diagnosticoscategorias> filtradoslistaDiagnosticos) {
        this.filtradoslistaDiagnosticos = filtradoslistaDiagnosticos;
    }

    public Diagnosticoscategorias getSeleccionDiagnosticos() {
        return seleccionDiagnosticos;
    }

    public void setSeleccionDiagnosticos(Diagnosticoscategorias seleccionDiagnosticos) {
        this.seleccionDiagnosticos = seleccionDiagnosticos;
    }

    public List<Terceros> getListaTerceros() {
        if (listaTerceros == null) {
            listaTerceros = administrarAusentismos.lovTerceros();
        }
        return listaTerceros;
    }

    public void setListaTerceros(List<Terceros> listaTerceros) {
        this.listaTerceros = listaTerceros;
    }

    public List<Terceros> getFiltradoslistaTerceros() {
        return filtradoslistaTerceros;
    }

    public void setFiltradoslistaTerceros(List<Terceros> filtradoslistaTerceros) {
        this.filtradoslistaTerceros = filtradoslistaTerceros;
    }

    public Terceros getSeleccionTerceros() {
        return seleccionTerceros;
    }

    public void setSeleccionTerceros(Terceros seleccionTerceros) {
        this.seleccionTerceros = seleccionTerceros;
    }

    public List<Soausentismos> getListaProrrogas() {
        if (listaProrrogas == null) {
            listaProrrogas = administrarAusentismos.lovProrrogas(secuenciaEmpleado, secuenciaCausa, secuenciaAusentismo);
        }
        return listaProrrogas;
    }

    public void setListaProrrogas(List<Soausentismos> listaProrrogas) {
        this.listaProrrogas = listaProrrogas;
    }

    public List<Soausentismos> getFiltradoslistaProrrogas() {
        return filtradoslistaProrrogas;
    }

    public void setFiltradoslistaProrrogas(List<Soausentismos> filtradoslistaProrrogas) {
        this.filtradoslistaProrrogas = filtradoslistaProrrogas;
    }

    public Soausentismos getSeleccionProrrogas() {
        return seleccionProrrogas;
    }

    public void setSeleccionProrrogas(Soausentismos seleccionProrrogas) {
        this.seleccionProrrogas = seleccionProrrogas;
    }

    public String getProrroga() {
        if (seleccionProrrogas != null) {
            if (Prorroga == null) {
                Prorroga = administrarAusentismos.mostrarProrroga(seleccionProrrogas.getSecuencia());
            }
        }
        return Prorroga;

    }

    public String getRelacion() {
        if (index >= 0) {
            if (Relacion == null) {
                Relacion = administrarAusentismos.mostrarRelacion(listaAusentismos.get(index).getSecuencia());
            }
        }
        return Relacion;
    }

    public Soausentismos getEditarAusentismos() {
        return editarAusentismos;
    }

    public void setEditarAusentismos(Soausentismos editarAusentismos) {
        this.editarAusentismos = editarAusentismos;
    }

    public Soausentismos getNuevoAusentismo() {
        return nuevoAusentismo;
    }

    public void setNuevoAusentismo(Soausentismos nuevoAusentismo) {
        this.nuevoAusentismo = nuevoAusentismo;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public Soausentismos getDuplicarAusentismo() {
        return duplicarAusentismo;
    }

    public void setDuplicarAusentismo(Soausentismos duplicarAusentismo) {
        this.duplicarAusentismo = duplicarAusentismo;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

    public Soausentismos getAusentismoSeleccionado() {
        return ausentismoSeleccionado;
    }

    public void setAusentismoSeleccionado(Soausentismos ausentismoSeleccionado) {
        this.ausentismoSeleccionado = ausentismoSeleccionado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getAltoDialogoNuevo() {
        return altoDialogoNuevo;
    }

    public void setAltoDialogoNuevo(String altoDialogoNuevo) {
        this.altoDialogoNuevo = altoDialogoNuevo;
    }

    public String getAltoDialogoDuplicar() {
        return altoDialogoDuplicar;
    }

    public void setAltoDialogoDuplicar(String altoDialogoDuplicar) {
        this.altoDialogoDuplicar = altoDialogoDuplicar;
    }

    public String getInfoRegistroTipo() {
//        getListaTiposAusentismos();
//        if (listaTiposAusentismos != null) {
//            infoRegistroTipo = "Cantidad de registros : " + listaTiposAusentismos.size();
//        } else {
//            infoRegistroTipo = "Cantidad de registros : 0";
//        }

        return infoRegistroTipo;
    }

    public void setInfoRegistroTipo(String infoRegistroTipo) {
        this.infoRegistroTipo = infoRegistroTipo;
    }

    public String getInfoRegistroClase() {
//        getListaClasesAusentismos();
//        if (listaClasesAusentismos != null) {
//            infoRegistroClase = "Cantidad de registros : " + listaClasesAusentismos.size();
//        } else {
//            infoRegistroClase = "Cantidad de registros : 0";
//        }
        return infoRegistroClase;
    }

    public void setInfoRegistroClase(String infoRegistroClase) {
        this.infoRegistroClase = infoRegistroClase;
    }

    public String getInfoRegistroCausa() {
//        getListaCausasAusentismos();
//        if (listaCausasAusentismos != null) {
//            infoRegistroCausa = "Cantidad de registros : " + listaCausasAusentismos.size();
//        } else {
//            infoRegistroCausa = "Cantidad de registros : 0";
//        }
        return infoRegistroCausa;
    }

    public void setInfoRegistroCausa(String infoRegistroCausa) {
        this.infoRegistroCausa = infoRegistroCausa;
    }

    public String getInfoRegistroPorcentaje() {
//        getListaPorcentaje();
//        if (listaPorcentaje != null) {
//            infoRegistroPorcentaje = "Cantidad de registros : " + listaPorcentaje.size();
//        } else {
//            infoRegistroPorcentaje = "Cantidad de registros : 0";
//        }
        return infoRegistroPorcentaje;
    }

    public void setInfoRegistroPorcentaje(String infoRegistroPorcentaje) {
        this.infoRegistroPorcentaje = infoRegistroPorcentaje;
    }

    public String getInfoRegistroBase() {
//        getListaIBCS();
//        if (listaIBCS != null) {
//            infoRegistroBase = "Cantidad de registros : " + listaIBCS.size();
//        } else {
//            infoRegistroBase = "Cantidad de registros : 0";
//        }
        return infoRegistroBase;
    }

    public void setInfoRegistroBase(String infoRegistroBase) {
        this.infoRegistroBase = infoRegistroBase;
    }

    public String getInfoRegistroAccidente() {
//        getListaAccidentes();
//        if (listaAccidentes != null) {
//            infoRegistroAccidente = "Cantidad de registros : " + listaAccidentes.size();
//        } else {
//            infoRegistroAccidente = "Cantidad de registros : 0";
//        }
        return infoRegistroAccidente;
    }

    public void setInfoRegistroAccidente(String infoRegistroAccidente) {
        this.infoRegistroAccidente = infoRegistroAccidente;
    }

    public String getInfoRegistroEnfermedad() {
//        getListaEnfermeadadesProfesionales();
//        if (listaEnfermeadadesProfesionales != null) {
//            infoRegistroEnfermedad = "Cantidad de registros : " + listaEnfermeadadesProfesionales.size();
//        } else {
//            infoRegistroEnfermedad = "Cantidad de registros : 0";
//        }
        return infoRegistroEnfermedad;
    }

    public void setInfoRegistroEnfermedad(String infoRegistroEnfermedad) {
        this.infoRegistroEnfermedad = infoRegistroEnfermedad;
    }

    public String getInfoRegistroDiagnostico() {
//        getListaDiagnosticos();
//        if (listaDiagnosticos != null) {
//            infoRegistroDiagnostico = "Cantidad de registros : " + listaDiagnosticos.size();
//        } else {
//            infoRegistroDiagnostico = "Cantidad de registros : 0";
//        }
        return infoRegistroDiagnostico;
    }

    public void setInfoRegistroDiagnostico(String infoRegistroDiagnostico) {
        this.infoRegistroDiagnostico = infoRegistroDiagnostico;
    }

    public String getInfoRegistroProrroga() {
//        getListaProrrogas();
//        if (listaProrrogas != null) {
//            infoRegistroProrroga = "Cantidad de registros : " + listaProrrogas.size();
//        } else {
//            infoRegistroProrroga = "Cantidad de registros : 0";
//        }
        return infoRegistroProrroga;
    }

    public void setInfoRegistroProrroga(String infoRegistroProrroga) {
        this.infoRegistroProrroga = infoRegistroProrroga;
    }

    public String getInfoRegistroTercero() {
//        getListaTerceros();
//        if (listaTerceros != null) {
//            infoRegistroTercero = "Cantidad de registros : " + listaTerceros.size();
//        } else {
//            infoRegistroTercero = "Cantidad de registros : 0";
//        }
        return infoRegistroTercero;
    }

    public void setInfoRegistroTercero(String infoRegistroTercero) {
        this.infoRegistroTercero = infoRegistroTercero;
    }

    public String getInfoRegistroEmpleado() {
//        getListaEmpleadosAusentismo();
//        if (listaEmpleadosAusentismo != null) {
//            infoRegistroEmpleado = "Cantidad de registros : " + listaEmpleadosAusentismo.size();
//        } else {
//            infoRegistroEmpleado = "Cantidad de registros : 0";
//        }
        return infoRegistroEmpleado;
    }

    public void setInfoRegistroEmpleado(String infoRegistroEmpleado) {
        this.infoRegistroEmpleado = infoRegistroEmpleado;
    }

    public String getInfoRegistroForma() {
//        getListaForma();
//        if (listaForma != null) {
//            infoRegistroForma = "Cantidad de registros : " + listaForma.size();
//        } else {
//            infoRegistroForma = "Cantidad de registros : 0";
//        }
        return infoRegistroForma;
    }

    public void setInfoRegistroForma(String infoRegistroForma) {
        this.infoRegistroForma = infoRegistroForma;
    }

    public boolean isColapsado() {
        return colapsado;
    }

    public void setColapsado(boolean colapsado) {
        this.colapsado = colapsado;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }
}

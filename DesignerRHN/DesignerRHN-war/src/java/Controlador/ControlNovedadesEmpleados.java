/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Formulas;
import Entidades.Novedades;
import Entidades.Periodicidades;
import Entidades.PruebaEmpleados;
import Entidades.Terceros;
import Entidades.Usuarios;
import Entidades.VWActualesTiposTrabajadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNovedadesEmpleadosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Viktor
 */
@ManagedBean
@SessionScoped
public class ControlNovedadesEmpleados implements Serializable {

    @EJB
    AdministrarNovedadesEmpleadosInterface administrarNovedadesEmpleados;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaEmpleado;
    //LISTA NOVEDADES
    private List<Novedades> listaNovedades;
    private List<Novedades> filtradosListaNovedades;
    //LISTA QUE NO ES LISTA - 1 SOLO ELEMENTO
    private List<PruebaEmpleados> listaEmpleadosNovedad;
    private List<PruebaEmpleados> filtradosListaEmpleadosNovedad;
    private PruebaEmpleados seleccionMostrar; //Seleccion Mostrar
    //editar celda
    private Novedades editarNovedades;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //LOV EMPLEADOS
    private List<VWActualesTiposTrabajadores> listaEmpleados;
    private List<VWActualesTiposTrabajadores> filtradoslistaEmpleados;
    private VWActualesTiposTrabajadores seleccionEmpleados;
    //Crear Novedades
    private List<Novedades> listaNovedadesCrear;
    public Novedades nuevaNovedad;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Novedades
    private List<Novedades> listaNovedadesModificar;
    //Borrar Novedades
    private List<Novedades> listaNovedadesBorrar;
    //Autocompletar
    private BigInteger CodigoConcepto;
    private String NitTercero, Formula, DescripcionConcepto, DescripcionPeriodicidad, NombreTercero;
    private Date FechaFinal;
    private Short CodigoPeriodicidad;
    private BigDecimal Saldo;
    private Integer HoraDia, MinutoHora;
    //L.O.V CONCEPTOS
    private List<Conceptos> listaConceptos;
    private List<Conceptos> filtradoslistaConceptos;
    private Conceptos seleccionConceptos;
    //L.O.V CONCEPTOS
    private List<Periodicidades> listaPeriodicidades;
    private List<Periodicidades> filtradoslistaPeriodicidades;
    private Periodicidades seleccionPeriodicidades;
    //L.O.V TERCEROS
    private List<Terceros> listaTerceros;
    private List<Terceros> filtradoslistaTerceros;
    private Terceros seleccionTerceros;
    //L.O.V FORMULAS
    private List<Formulas> listaFormulas;
    private List<Formulas> filtradoslistaFormulas;
    private Formulas seleccionFormulas;
    //Columnas Tabla NOVEDADES
    private Column nEConceptoCodigo, nEConceptoDescripcion, nEFechasInicial, nEFechasFinal,
            nEValor, nESaldo, nEPeriodicidadCodigo, nEDescripcionPeriodicidad, nETercerosNit,
            nETercerosNombre, nEFormulas, nEHorasDias, nEMinutosHoras, nETipo;
    //Duplicar
    private Novedades duplicarNovedad;
    //USUARIO
    private String alias;
    private Usuarios usuarioBD;
    //VALIDAR SI EL QUE SE VA A BORRAR ESTÁ EN SOLUCIONES FORMULAS
    private int resultado;

    public ControlNovedadesEmpleados() {
        permitirIndex = true;
        listaNovedades = null;
        listaEmpleados = null;
        listaFormulas = null;
        listaConceptos = null;
        listaPeriodicidades = null;
        listaEmpleadosNovedad = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaNovedadesBorrar = new ArrayList<Novedades>();
        listaNovedadesCrear = new ArrayList<Novedades>();
        listaNovedadesModificar = new ArrayList<Novedades>();
        //Crear VC
        nuevaNovedad = new Novedades();
        //nuevaNovedad.setFechafinal(new Date());
        nuevaNovedad.setFormula(new Formulas());
        nuevaNovedad.setTercero(new Terceros());
        nuevaNovedad.setPeriodicidad(new Periodicidades());
        nuevaNovedad.setFechareporte(new Date());
        nuevaNovedad.setTipo("FIJA");
        
    }
    
    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            nEConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoCodigo");
            nEConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoDescripcion");
            nEConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nEFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasInicial");
            nEFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nEFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasFinal");
            nEFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nEValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEValor");
            nEValor.setFilterStyle("display: none; visibility: hidden;");
            nESaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nESaldo");
            nESaldo.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodicidadCodigo");
            nEPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDescripcionPeriodicidad");
            nEDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNit");
            nETercerosNit.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNombre");
            nETercerosNombre.setFilterStyle("display: none; visibility: hidden;");
            nEFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFormulas");
            nEFormulas.setFilterStyle("display: none; visibility: hidden;");
            nEHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEHorasDias");
            nEHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nEMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEMinutosHoras");
            nEMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nETipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETipo");
            nETipo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }

        listaNovedadesBorrar.clear();
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
//        k = 0;
        listaNovedades = null;
        guardado = true;
        permitirIndex = true;
        resultado = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesEmpleado");
        
    }
    
    public void salir() {

        if (bandera == 1) {
            nEConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoCodigo");
            nEConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoDescripcion");
            nEConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nEFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasInicial");
            nEFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nEFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasFinal");
            nEFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nEValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEValor");
            nEValor.setFilterStyle("display: none; visibility: hidden;");
            nESaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nESaldo");
            nESaldo.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodicidadCodigo");
            nEPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDescripcionPeriodicidad");
            nEDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNit");
            nETercerosNit.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNombre");
            nETercerosNombre.setFilterStyle("display: none; visibility: hidden;");
            nEFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFormulas");
            nEFormulas.setFilterStyle("display: none; visibility: hidden;");
            nEHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEHorasDias");
            nEHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nEMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEMinutosHoras");
            nEMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nETipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETipo");
            nETipo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }
        listaNovedadesBorrar.clear();
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
//        k = 0;
        listaNovedades = null;
        resultado = 0;
        guardado = true;
        permitirIndex = true;
    }

    //Ubicacion Celda Arriba 
    public void cambiarEmpleado() {
        //Si ninguna de las 3 listas (crear,modificar,borrar) tiene algo, hace esto
        //{
        secuenciaEmpleado = seleccionMostrar.getId();
        listaNovedades = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesEmpleado");
        //}
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        listaNovedadesBorrar.clear();
    }

    public void seleccionarTipoNuevaNovedad(String tipo, int tipoNuevo) {
        if (tipoNuevo == 1) {
            if (tipo.equals("FIJA")) {
                nuevaNovedad.setTipo("FIJA");
            } else if (tipo.equals("OCASIONAL")) {
                nuevaNovedad.setTipo("OCASIONAL");
            } else if (tipo.equals("PAGO POR FUERA")) {
                nuevaNovedad.setTipo("PAGO POR FUERA");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoTipo");
        } else {
            if (tipo.equals("FIJA")) {
                duplicarNovedad.setTipo("FIJA");
            } else if (tipo.equals("OCASIONAL")) {
                duplicarNovedad.setTipo("OCASIONAL");
            } else if (tipo.equals("PAGO POR FUERA")) {
                duplicarNovedad.setTipo("PAGO POR FUERA");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarTipo");
        }

    }

    public void seleccionarTipo(String estadoTipo, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoTipo.equals("FIJA")) {
                listaNovedades.get(indice).setTipo("FIJA");
            } else if (estadoTipo.equals("OCASIONAL")) {
                listaNovedades.get(indice).setTipo("OCASIONAL");
            } else if (estadoTipo.equals("PAGO POR FUERA")) {
                listaNovedades.get(indice).setTipo("PAGO POR FUERA");
            }

            if (!listaNovedadesCrear.contains(listaNovedades.get(indice))) {
                if (listaNovedadesModificar.isEmpty()) {
                    listaNovedadesModificar.add(listaNovedades.get(indice));
                } else if (!listaNovedadesModificar.contains(listaNovedades.get(indice))) {
                    listaNovedadesModificar.add(listaNovedades.get(indice));
                }
            }
        } else {
            if (estadoTipo.equals("FIJA")) {
                filtradosListaNovedades.get(indice).setTipo("FIJA");
            } else if (estadoTipo.equals("OCASIONAL")) {
                filtradosListaNovedades.get(indice).setTipo("OCASIONAL");
            } else if (estadoTipo.equals("PAGO POR FUERA")) {
                filtradosListaNovedades.get(indice).setTipo("PAGO POR FUERA");
            }

            if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(indice))) {
                if (listaNovedadesModificar.isEmpty()) {
                    listaNovedadesModificar.add(filtradosListaNovedades.get(indice));
                } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(indice))) {
                    listaNovedadesModificar.add(filtradosListaNovedades.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
        }
        RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
    }

    //GUARDAR
    public void guardarCambiosNovedades() {
        Empleados emp = new Empleados();
        int pasas = 0;
        if (guardado == false) {
            System.out.println("Realizando Operaciones Novedades");

            getResultado();
            System.out.println("Resultado: " + resultado);
            if (resultado > 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:solucionesFormulas");
                context.execute("solucionesFormulas.show()");
                listaNovedadesBorrar.clear();
            }
            

            if (!listaNovedadesBorrar.isEmpty() && pasas == 0) {
                for (int i = 0; i < listaNovedadesBorrar.size(); i++) {
                    System.out.println("Borrando..." + listaNovedadesBorrar.size());

                    if (listaNovedadesBorrar.get(i).getTercero().getSecuencia() == null) {
                        listaNovedadesBorrar.get(i).setTercero(null);
                    }
                    if (listaNovedadesBorrar.get(i).getSaldo() == null) {
                        listaNovedadesBorrar.get(i).setSaldo(null);
                    }
                    if (listaNovedadesBorrar.get(i).getUnidadesparteentera() == null) {
                        listaNovedadesBorrar.get(i).setUnidadesparteentera(null);
                    }
                    if (listaNovedadesBorrar.get(i).getUnidadespartefraccion() == null) {
                        listaNovedadesBorrar.get(i).setUnidadespartefraccion(null);
                    }
                    administrarNovedadesEmpleados.borrarNovedades(listaNovedadesBorrar.get(i));
                }
                System.out.println("Entra");
                listaNovedadesBorrar.clear();
            }

            if (!listaNovedadesCrear.isEmpty()) {
                for (int i = 0; i < listaNovedadesCrear.size(); i++) {
                    System.out.println("Creando...");

                    if (listaNovedadesCrear.get(i).getTercero().getSecuencia() == null) {
                        listaNovedadesCrear.get(i).setTercero(null);
                    }
                    if (listaNovedadesCrear.get(i).getPeriodicidad().getSecuencia() == null) {
                        listaNovedadesCrear.get(i).setPeriodicidad(null);
                    }

                    if (listaNovedadesCrear.get(i).getSaldo() == null) {
                        listaNovedadesCrear.get(i).setSaldo(null);
                    }
                    if (listaNovedadesCrear.get(i).getUnidadesparteentera() == null) {
                        listaNovedadesCrear.get(i).setUnidadesparteentera(null);
                    }
                    if (listaNovedadesCrear.get(i).getUnidadespartefraccion() == null) {
                        listaNovedadesCrear.get(i).setUnidadespartefraccion(null);
                    }
                    System.out.println(listaNovedadesCrear.get(i).getTipo());
                    administrarNovedadesEmpleados.crearNovedades(listaNovedadesCrear.get(i));




                }
                System.out.println("LimpiaLista");
                listaNovedadesCrear.clear();
            }
            if (!listaNovedadesModificar.isEmpty()) {
                administrarNovedadesEmpleados.modificarNovedades(listaNovedadesModificar);
                listaNovedadesModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaNovedades = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesEmpleado");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            //  k = 0;
        }
        System.out.println("Tamaño lista: " + listaNovedadesCrear.size());
        System.out.println("Valor k: " + k);
        index = -1;
        secRegistro = null;
    }

    //CREAR NOVEDADES
    public void agregarNuevaNovedadEmpleado() throws UnknownHostException {
        int pasa = 0;
        int pasa2 = 0;
        Empleados emp = new Empleados();
        Empleados emp2 = new Empleados();
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();

        /* if ((!nuevaEncargatura.getReemplazado().getPersona().getNombreCompleto().equals("  ") && !nuevaEncargatura.getReemplazado().getPersona().getNombreCompleto().equals(" ")) && (!nuevaEncargatura.getCargo().getNombre().equals(" ") && !nuevaEncargatura.getCargo().getNombre().equals(""))) {
         System.out.println("Entro a Inconsistencia");
         context.update("formularioDialogos:inconsistencia");
         context.execute("inconsistencia.show()");
         pasa2++;
         } */
        if (nuevaNovedad.getFechainicial() == null) {
            System.out.println("Entro a Fecha Inicial");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicial\n";
            pasa++;
        }
        
        for (int i = 0; i < listaEmpleados.size(); i++) {
                if (seleccionMostrar.getId().compareTo(listaEmpleados.get(i).getEmpleado().getSecuencia()) == 0) {
                    emp2 = listaEmpleados.get(i).getEmpleado();
                    if(nuevaNovedad.getFechainicial().compareTo(emp2.getFechacreacion()) < 0){
                       context.update("formularioDialogos:inconsistencia");
                       context.execute("inconsistencia.show()");
                       pasa2++;
                    }
                }
                
            }
        
        if (nuevaNovedad.getFechainicial().compareTo(nuevaNovedad.getFechafinal())> 0){
            context.update("formularioDialogos:fechas");
            context.execute("fechas.show()");
            pasa2++;
        }
        
        if (nuevaNovedad.getConcepto() == null) {
            System.out.println("Entro a Concepto");
            mensajeValidacion = mensajeValidacion + " * Concepto\n";
            pasa++;
        }
        if (nuevaNovedad.getFormula().getNombrelargo() == null) {
            System.out.println("Entro a Formula");
            mensajeValidacion = mensajeValidacion + " * Formula\n";
            pasa++;
        }
        if (nuevaNovedad.getValortotal() == null) {
            System.out.println("Entro a Valor");
            mensajeValidacion = mensajeValidacion + " * Valor\n";
            pasa++;
        }
        
        if (nuevaNovedad.getTipo()== null) {
            System.out.println("Entro a Tipo");
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }
        
        for (int i = 0; i < listaEmpleados.size(); i++) {
                if (seleccionMostrar.getId().compareTo(listaEmpleados.get(i).getEmpleado().getSecuencia()) == 0) {
                    emp = listaEmpleados.get(i).getEmpleado();
                }
            }

        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedadEmpleado");
            context.execute("validacionNuevaNovedadEmpleado.show()");
        }

        if (pasa == 0 && pasa2 == 0) {
            if (bandera == 1) {
                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoLista);
                nEConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoCodigo");
                nEConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
                nEConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoDescripcion");
                nEConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                nEFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasInicial");
                nEFechasInicial.setFilterStyle("display: none; visibility: hidden;");
                nEFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasFinal");
                nEFechasFinal.setFilterStyle("display: none; visibility: hidden;");
                nEValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEValor");
                nEValor.setFilterStyle("display: none; visibility: hidden;");
                nESaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nESaldo");
                nESaldo.setFilterStyle("display: none; visibility: hidden;");
                nEPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodicidadCodigo");
                nEPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
                nEDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDescripcionPeriodicidad");
                nEDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
                nETercerosNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNit");
                nETercerosNit.setFilterStyle("display: none; visibility: hidden;");
                nETercerosNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNombre");
                nETercerosNombre.setFilterStyle("display: none; visibility: hidden;");
                nEFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFormulas");
                nEFormulas.setFilterStyle("display: none; visibility: hidden;");
                nEHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEHorasDias");
                nEHorasDias.setFilterStyle("display: none; visibility: hidden;");
                nEMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEMinutosHoras");
                nEMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
                nETipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETipo");
                nETipo.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
                bandera = 0;
                filtradosListaNovedades = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevaNovedad.setSecuencia(l);
            for (int i = 0; i < listaEmpleados.size(); i++) {
                if (seleccionMostrar.getId().compareTo(listaEmpleados.get(i).getEmpleado().getSecuencia()) == 0) {
                    emp = listaEmpleados.get(i).getEmpleado();
                }
            }
            // OBTENER EL TERMINAL 
            HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
            String equipo = null;
            java.net.InetAddress localMachine = null;
            if (request.getRemoteAddr().startsWith("127.0.0.1")) {
                localMachine = java.net.InetAddress.getLocalHost();
                equipo = localMachine.getHostAddress();
            } else {
                equipo = request.getRemoteAddr();
            }
            localMachine = java.net.InetAddress.getByName(equipo);

            getAlias();
            System.out.println("Alias: " + alias);
            getUsuarioBD();
            System.out.println("UsuarioBD: " + usuarioBD);
            nuevaNovedad.setTerminal(localMachine.getHostName());
            nuevaNovedad.setUsuarioreporta(usuarioBD);
            nuevaNovedad.setEmpleado(emp); //Envia empleado
            System.out.println("Empleado enviado: " + emp.getPersona().getNombreCompleto());
            listaNovedadesCrear.add(nuevaNovedad);
            System.out.println(listaNovedadesCrear.size());
            System.out.println(listaNovedadesCrear.get(0).getTipo());
            System.out.println(nuevaNovedad.getTipo());
            listaNovedades.add(nuevaNovedad);
            nuevaNovedad = new Novedades();
            nuevaNovedad.setFormula(new Formulas());
            nuevaNovedad.setTercero(new Terceros());
            nuevaNovedad.setFechareporte(new Date());
            nuevaNovedad.setPeriodicidad(new Periodicidades());
            nuevaNovedad.setTipo("FIJA");

            System.out.println("nuevaNovedad : " + nuevaNovedad.getFechareporte());

            context.update("form:datosNovedadesEmpleado");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevaNovedadEmpleado.hide()");
            index = -1;
            secRegistro = null;
        } else {
        }
    }
    
    
    public void confirmarDuplicar() throws UnknownHostException {

        int pasa2 = 0;
        int pasa = 0;
        Empleados emp = new Empleados();
        Empleados emp2 = new Empleados();
        RequestContext context = RequestContext.getCurrentInstance();

        if (duplicarNovedad.getFechainicial() == null) {
            System.out.println("Entro a Fecha Inicial");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicial\n";
            pasa++;
        }
        
        for (int i = 0; i < listaEmpleados.size(); i++) {
                if (seleccionMostrar.getId().compareTo(listaEmpleados.get(i).getEmpleado().getSecuencia()) == 0) {
                    emp2 = listaEmpleados.get(i).getEmpleado();
                    if(duplicarNovedad.getFechainicial().compareTo(emp2.getFechacreacion()) < 0){
                       context.update("formularioDialogos:inconsistencia");
                       context.execute("inconsistencia.show()");
                       pasa2++;
                    }
                }
                
            }
        
        
        
        if (duplicarNovedad.getConcepto() == null) {
            System.out.println("Entro a Concepto");
            mensajeValidacion = mensajeValidacion + " * Concepto\n";
            pasa++;
        }
        if (duplicarNovedad.getFormula().getNombrelargo() == null) {
            System.out.println("Entro a Formula");
            mensajeValidacion = mensajeValidacion + " * Formula\n";
            pasa++;
        }
        if (duplicarNovedad.getValortotal() == null) {
            System.out.println("Entro a Valor");
            mensajeValidacion = mensajeValidacion + " * Valor\n";
            pasa++;
        }
        
        if (duplicarNovedad.getTipo()== null) {
            System.out.println("Entro a Tipo");
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }
        
        for (int i = 0; i < listaEmpleados.size(); i++) {
                if (seleccionMostrar.getId().compareTo(listaEmpleados.get(i).getEmpleado().getSecuencia()) == 0) {
                    emp = listaEmpleados.get(i).getEmpleado();
                }
            }

        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedadEmpleado");
            context.execute("validacionNuevaNovedadEmpleado.show()");
        }
        

        if (pasa2 == 0) {
            listaNovedades.add(duplicarNovedad);
            listaNovedadesCrear.add(duplicarNovedad);

            context.update("form:datosNovedadesEmpleado");
            index = -1;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                nEConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoCodigo");
            nEConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoDescripcion");
            nEConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nEFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasInicial");
            nEFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nEFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasFinal");
            nEFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nEValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEValor");
            nEValor.setFilterStyle("display: none; visibility: hidden;");
            nESaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nESaldo");
            nESaldo.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodicidadCodigo");
            nEPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDescripcionPeriodicidad");
            nEDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNit");
            nETercerosNit.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNombre");
            nETercerosNombre.setFilterStyle("display: none; visibility: hidden;");
            nEFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFormulas");
            nEFormulas.setFilterStyle("display: none; visibility: hidden;");
            nEHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEHorasDias");
            nEHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nEMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEMinutosHoras");
            nEMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nETipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETipo");
            nETipo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
            }
            
            // OBTENER EL TERMINAL 
            HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
            String equipo = null;
            java.net.InetAddress localMachine = null;
            if (request.getRemoteAddr().startsWith("127.0.0.1")) {
                localMachine = java.net.InetAddress.getLocalHost();
                equipo = localMachine.getHostAddress();
            } else {
                equipo = request.getRemoteAddr();
            }
            localMachine = java.net.InetAddress.getByName(equipo);

            getAlias();
            System.out.println("Alias: " + alias);
            getUsuarioBD();
            System.out.println("UsuarioBD: " + usuarioBD);
            duplicarNovedad.setTerminal(localMachine.getHostName());
            
            duplicarNovedad = new Novedades();
            context.update("formularioDialogos:DuplicarRegistroNovedad");
            context.execute("DuplicarRegistroNovedad.hide()");
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
            context.update("formularioDialogos:conceptosDialogo");
            context.execute("conceptosDialogo.show()");
        } else if (dlg == 2) {
            context.update("formularioDialogos:formulasDialogo");
            context.execute("formulasDialogo.show()");
        } else if (dlg == 3) {
            context.update("formularioDialogos:periodicidadesDialogo");
            context.execute("periodicidadesDialogo.show()");
        } else if (dlg == 4) {
            context.update("formularioDialogos:tercerosDialogo");
            context.execute("tercerosDialogo.show()");
        }
//FALTAN MAS LOVS, OBVIAMENTE
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    //AUTOCOMPLETAR
    public void modificarNovedades(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaNovedadesCrear.contains(listaNovedades.get(indice))) {

                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(indice));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(indice))) {
                        listaNovedadesModificar.add(listaNovedades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(indice))) {

                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(indice));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(indice))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            }

            context.update("form:datosNovedadesEmpleado");
        } else if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoLista == 0) {
                listaNovedades.get(indice).getFormula().setNombresFormula(Formula);
            } else {
                filtradosListaNovedades.get(indice).getFormula().setNombresFormula(Formula);
            }

            for (int i = 0; i < listaNovedades.size(); i++) {
                if (listaNovedades.get(i).getFormula().getNombresFormula().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaNovedades.get(indice).setFormula(listaFormulas.get(indiceUnicoElemento));
                } else {
                    filtradosListaNovedades.get(indice).setFormula(listaFormulas.get(indiceUnicoElemento));
                }
                listaFormulas.clear();
                getListaFormulas();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:formulasDialogo");
                context.execute("formulasDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("NIT")) {
            if (tipoLista == 0) {
                listaNovedades.get(indice).getTercero().setNitalternativo(NitTercero);
            } else {
                filtradosListaNovedades.get(indice).getTercero().setNitalternativo(NitTercero);
            }

            for (int i = 0; i < listaNovedades.size(); i++) {
                if (listaNovedades.get(i).getTercero().getNitalternativo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaNovedades.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                } else {
                    filtradosListaNovedades.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                }
                listaTerceros.clear();
                getListaTerceros();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tercerosDialogo");
                context.execute("tercerosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTO")) {

            if (tipoLista == 0) {
                //String ayuda = (listaNovedades.get(index).getConcepto().getCodigo()).toString();

                listaNovedades.get(indice).getConcepto().setCodigo(CodigoConcepto);
            } else {
                filtradosListaNovedades.get(indice).getConcepto().setCodigo(CodigoConcepto);
            }

            for (int i = 0; i < listaNovedades.size(); i++) {
                if ((listaNovedades.get(i).getConcepto().getCodigo()).toString().startsWith(valorConfirmar.toString().toUpperCase())) {

                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaNovedades.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                } else {
                    filtradosListaNovedades.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                }
                listaConceptos.clear();
                getListaConceptos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaNovedadesCrear.contains(listaNovedades.get(indice))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(indice));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(indice))) {
                        listaNovedadesModificar.add(listaNovedades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(indice))) {

                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(indice));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(indice))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosNovedadesEmpleado");
    }

    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            nEConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoCodigo");
            nEConceptoCodigo.setFilterStyle("width: 60px");
            nEConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoDescripcion");
            nEConceptoDescripcion.setFilterStyle("");
            nEFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasInicial");
            nEFechasInicial.setFilterStyle("width: 60px");
            nEFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasFinal");
            nEFechasFinal.setFilterStyle("width: 60px");
            nEValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEValor");
            nEValor.setFilterStyle("width: 60px");
            nESaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nESaldo");
            nESaldo.setFilterStyle("width: 60px");
            nEPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodicidadCodigo");
            nEPeriodicidadCodigo.setFilterStyle("width: 60px");
            nEDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDescripcionPeriodicidad");
            nEDescripcionPeriodicidad.setFilterStyle("width: 60px");
            nETercerosNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNit");
            nETercerosNit.setFilterStyle("width: 60px");
            nETercerosNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNombre");
            nETercerosNombre.setFilterStyle("width: 60px");
            nEFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFormulas");
            nEFormulas.setFilterStyle("width: 60px");
            nEHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEHorasDias");
            nEHorasDias.setFilterStyle("width: 60px");
            nEMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEMinutosHoras");
            nEMinutosHoras.setFilterStyle("width: 60px");
            nETipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETipo");
            nETipo.setFilterStyle("width: 60px");


            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            nEConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoCodigo");
            nEConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEConceptoDescripcion");
            nEConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nEFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasInicial");
            nEFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nEFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechasFinal");
            nEFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nEValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEValor");
            nEValor.setFilterStyle("display: none; visibility: hidden;");
            nESaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nESaldo");
            nESaldo.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodicidadCodigo");
            nEPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nEDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDescripcionPeriodicidad");
            nEDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNit");
            nETercerosNit.setFilterStyle("display: none; visibility: hidden;");
            nETercerosNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETercerosNombre");
            nETercerosNombre.setFilterStyle("display: none; visibility: hidden;");
            nEFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFormulas");
            nEFormulas.setFilterStyle("display: none; visibility: hidden;");
            nEHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEHorasDias");
            nEHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nEMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nEMinutosHoras");
            nEMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nETipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:nETipo");
            nETipo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:tercerosDialogo");
                context.execute("tercerosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:formulasDialogo");
                context.execute("formulasDialogo.show()");
            }
        }
    }

    //Ubicacion Celda Indice Abajo. //Van los que no son NOT NULL.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {

            index = indice;
            cualCelda = celda;

            if (tipoLista == 0) {
                secRegistro = listaNovedades.get(index).getSecuencia();
                if (cualCelda == 0) {
                    CodigoConcepto = listaNovedades.get(index).getConcepto().getCodigo();
                } else if (cualCelda == 1) {
                    DescripcionConcepto = listaNovedades.get(index).getConcepto().getDescripcion();
                } else if (cualCelda == 3) {
                    FechaFinal = listaNovedades.get(index).getFechafinal();
                } else if (cualCelda == 5) {
                    Saldo = listaNovedades.get(index).getSaldo();
                } else if (cualCelda == 6) {
                    CodigoPeriodicidad = listaNovedades.get(index).getPeriodicidad().getCodigo();
                } else if (cualCelda == 7) {
                    DescripcionPeriodicidad = listaNovedades.get(index).getPeriodicidad().getNombre();
                } else if (cualCelda == 8) {
                    NitTercero = listaNovedades.get(index).getTercero().getNitalternativo();
                } else if (cualCelda == 9) {
                    NombreTercero = listaNovedades.get(index).getTercero().getNombre();
                } else if (cualCelda == 10) {
                    HoraDia = listaNovedades.get(index).getUnidadesparteentera();
                } else if (cualCelda == 11) {
                    MinutoHora = listaNovedades.get(index).getUnidadespartefraccion();
                }
            } else {
                secRegistro = filtradosListaNovedades.get(index).getSecuencia();
                if (cualCelda == 0) {
                    CodigoConcepto = filtradosListaNovedades.get(index).getConcepto().getCodigo();
                } else if (cualCelda == 1) {
                    DescripcionConcepto = filtradosListaNovedades.get(index).getConcepto().getDescripcion();
                } else if (cualCelda == 3) {
                    FechaFinal = filtradosListaNovedades.get(index).getFechafinal();
                } else if (cualCelda == 5) {
                    Saldo = filtradosListaNovedades.get(index).getSaldo();
                } else if (cualCelda == 6) {
                    CodigoPeriodicidad = filtradosListaNovedades.get(index).getPeriodicidad().getCodigo();
                } else if (cualCelda == 7) {
                    DescripcionPeriodicidad = filtradosListaNovedades.get(index).getPeriodicidad().getNombre();
                } else if (cualCelda == 8) {
                    NitTercero = filtradosListaNovedades.get(index).getTercero().getNitalternativo();
                } else if (cualCelda == 9) {
                    NombreTercero = filtradosListaNovedades.get(index).getTercero().getNombre();
                } else if (cualCelda == 10) {
                    HoraDia = filtradosListaNovedades.get(index).getUnidadesparteentera();
                } else if (cualCelda == 11) {
                    MinutoHora = filtradosListaNovedades.get(index).getUnidadespartefraccion();
                }
            }
        }
        System.out.println("Index: " + index + " Celda: " + celda);
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarNovedades = listaNovedades.get(index);
            }
            if (tipoLista == 1) {
                editarNovedades = filtradosListaNovedades.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarConceptosCodigos");
                context.execute("editarConceptosCodigos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarConceptosDescripciones");
                context.execute("editarConceptosDescripciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editFechaInicial");
                context.execute("editFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editFechasFinales");
                context.execute("editFechasIniciales.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarValores");
                context.execute("editarSaldos.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarSaldos");
                context.execute("editarSaldos.show()");
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarPeriodicidadesCodigos");
                context.execute("editarPeriodicidadesCodigos.show()");
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarPeriodicidadesDescripciones");
                context.execute("editarPeriodicidadesDescripciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarTercerosNit");
                context.execute("editarTercerosNit.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarTercerosNombres");
                context.execute("editarTercerosNombres.show()");
                cualCelda = -1;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editarFormulas");
                context.execute("editarFormulas.show()");
                cualCelda = -1;
            } else if (cualCelda == 11) {
                context.update("formularioDialogos:editarHorasDias");
                context.execute("editarHorasDias.show()");
                cualCelda = -1;
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:editarMinutosHoras");
                context.execute("editarMinutosHoras.show()");
                cualCelda = -1;
            } else if (cualCelda == 13) {
                context.update("formularioDialogos:editarTipos");
                context.execute("editarTipos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("CONCEPTO")) {
            if (tipoNuevo == 1) {
                CodigoConcepto = nuevaNovedad.getConcepto().getCodigo();
            } else if (tipoNuevo == 2) {
                CodigoConcepto = duplicarNovedad.getConcepto().getCodigo();
            }
        } else if (Campo.equals("CODIGO")) {
            if (tipoNuevo == 1) {
                CodigoPeriodicidad = nuevaNovedad.getPeriodicidad().getCodigo();
            } else if (tipoNuevo == 2) {
                CodigoPeriodicidad = duplicarNovedad.getPeriodicidad().getCodigo();
            }
        } else if (Campo.equals("NIT")) {
            if (tipoNuevo == 1) {
                NitTercero = nuevaNovedad.getTercero().getNitalternativo();
            } else if (tipoNuevo == 2) {
                NitTercero = duplicarNovedad.getTercero().getNitalternativo();
            }
        } else if (Campo.equals("FORMULAS")) {
            if (tipoNuevo == 1) {
                Formula = nuevaNovedad.getFormula().getNombrelargo();
            } else if (tipoNuevo == 2) {
                Formula = duplicarNovedad.getFormula().getNombrelargo();
            }
        }

    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoNuevo == 1) {
                nuevaNovedad.getFormula().setNombrelargo(Formula);
            } else if (tipoNuevo == 2) {
                duplicarNovedad.getFormula().setNombrelargo(Formula);
            }
            for (int i = 0; i < listaFormulas.size(); i++) {
                if (listaFormulas.get(i).getNombrelargo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaNovedad.setFormula(listaFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaFormula");
                } else if (tipoNuevo == 2) {
                    duplicarNovedad.setFormula(listaFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarFormula");
                }
                listaFormulas.clear();
                getListaFormulas();
            } else {
                context.update("form:formulasDialogo");
                context.execute("formulasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaFormula");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarFormula");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("NIT")) {
            if (tipoNuevo == 1) {
                nuevaNovedad.getTercero().setNitalternativo(NitTercero);
            } else if (tipoNuevo == 2) {
                duplicarNovedad.getTercero().setNitalternativo(NitTercero);
            }

            for (int i = 0; i < listaTerceros.size(); i++) {
                if (listaTerceros.get(i).getNitalternativo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaNovedad.setTercero(listaTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTercero");
                } else if (tipoNuevo == 2) {
                    duplicarNovedad.setTercero(listaTerceros.get(indiceUnicoElemento));
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
        } /*else if (confirmarCambio.equalsIgnoreCase("MOTIVOREEMPLAZO")) {
         if (tipoNuevo == 1) {
         nuevaEncargatura.getMotivoreemplazo().setNombre(MotivoReemplazo);
         } else if (tipoNuevo == 2) {
         duplicarEncargatura.getMotivoreemplazo().setNombre(MotivoReemplazo);
         }

         for (int i = 0; i < listaMotivosReemplazos.size(); i++) {
         if (listaMotivosReemplazos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
         indiceUnicoElemento = i;
         coincidencias++;
         }
         }
         if (coincidencias == 1) {
         if (tipoNuevo == 1) {
         nuevaEncargatura.setMotivoreemplazo(listaMotivosReemplazos.get(indiceUnicoElemento));
         context.update("formularioDialogos:nuevoMotivoReemplazo");
         } else if (tipoNuevo == 2) {
         duplicarEncargatura.setMotivoreemplazo(listaMotivosReemplazos.get(indiceUnicoElemento));
         context.update("formularioDialogos:duplicarMotivoReemplazo");
         }
         listaMotivosReemplazos.clear();
         getListaMotivosReemplazos();
         } else {
         context.update("form:motivosReemplazosDialogo");
         context.execute("motivosReemplazosDialogo.show()");
         tipoActualizacion = tipoNuevo;
         if (tipoNuevo == 1) {
         context.update("formularioDialogos:nuevoMotivoReemplazo");
         } else if (tipoNuevo == 2) {
         context.update("formularioDialogos:duplicarMotivoReemplazo");
         }
         }

         } else if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
         if (tipoNuevo == 1) {
         nuevaEncargatura.getEstructura().setNombre(Estructura);
         } else if (tipoNuevo == 2) {
         duplicarEncargatura.getEstructura().setNombre(Estructura);
         }

         for (int i = 0; i < listaEstructuras.size(); i++) {
         if (listaEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
         indiceUnicoElemento = i;
         coincidencias++;
         }
         }
         if (coincidencias == 1) {
         if (tipoNuevo == 1) {
         nuevaEncargatura.setEstructura(listaEstructuras.get(indiceUnicoElemento));
         context.update("formularioDialogos:nuevaEstructura");
         } else if (tipoNuevo == 2) {
         duplicarEncargatura.setEstructura(listaEstructuras.get(indiceUnicoElemento));
         context.update("formularioDialogos:duplicarEstructura");
         }
         listaEstructuras.clear();
         getListaEstructuras();
         } else {
         context.update("form:estructurasDialogo");
         context.execute("estructurasDialogo.show()");
         tipoActualizacion = tipoNuevo;
         if (tipoNuevo == 1) {
         context.update("formularioDialogos:nuevaEstructura");
         } else if (tipoNuevo == 2) {
         context.update("formularioDialogos:duplicarEstructura");
         }
         }

         }*/
    }

    public void actualizarEmpleadosNovedad() {
        RequestContext context = RequestContext.getCurrentInstance();
        PruebaEmpleados pe = administrarNovedadesEmpleados.novedadEmpleado(seleccionEmpleados.getSecuencia());

        if (pe != null) {
            listaEmpleadosNovedad.add(pe);
        } else {
            pe = new PruebaEmpleados();
            pe.setId(seleccionEmpleados.getEmpleado().getSecuencia());
            pe.setCodigo(seleccionEmpleados.getEmpleado().getCodigoempleado());
            pe.setNombre(seleccionEmpleados.getEmpleado().getPersona().getNombreCompleto());
            pe.setValor(null);
            pe.setTipo(seleccionEmpleados.getTipoTrabajador().getTipo());
            listaEmpleadosNovedad.add(pe);
        }


        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad.add(pe);
            seleccionMostrar = listaEmpleadosNovedad.get(0);
        } else {
            listaEmpleadosNovedad.add(pe);
        }
        secuenciaEmpleado = seleccionEmpleados.getSecuencia();
        listaNovedades = null;

        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:datosNovedadesEmpleado");

        filtradosListaEmpleadosNovedad = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarFormulas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaNovedades.get(index).setFormula(seleccionFormulas);
                if (!listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(index))) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    }
                }
            } else {
                filtradosListaNovedades.get(index).setFormula(seleccionFormulas);
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosNovedadesEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setFormula(seleccionFormulas);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
         duplicarNovedad.setFormula(seleccionFormulas);
         context.update("formularioDialogos:duplicarNovedad");

         }
        filtradoslistaFormulas = null;
        seleccionFormulas = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("formulasDialogo.hide()");
        context.reset("formularioDialogos:LOVFormulas:globalFilter");
        context.update("formularioDialogos:LOVFormulas");
    }

    public void actualizarConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaNovedades.get(index).setConcepto(seleccionConceptos);
                if (!listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(index))) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    }
                }
            } else {
                filtradosListaNovedades.get(index).setConcepto(seleccionConceptos);
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosNovedadesEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setConcepto(seleccionConceptos);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
         duplicarNovedad.setConcepto(seleccionConceptos);
         context.update("formularioDialogos:duplicarNovedad");

         }
        filtradoslistaConceptos = null;
        seleccionConceptos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("conceptosDialogo.hide()");
        context.reset("formularioDialogos:LOVConceptos:globalFilter");
        context.update("formularioDialogos:LOVConceptos");
    }

    public void actualizarPeriodicidades() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaNovedades.get(index).setPeriodicidad(seleccionPeriodicidades);
                if (!listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(index))) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    }
                }
            } else {
                filtradosListaNovedades.get(index).setPeriodicidad(seleccionPeriodicidades);
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosNovedadesEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setPeriodicidad(seleccionPeriodicidades);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
         duplicarNovedad.setPeriodicidad(seleccionPeriodicidades);
         context.update("formularioDialogos:duplicarNovedad");

         }
        filtradoslistaPeriodicidades = null;
        seleccionPeriodicidades = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("periodicidadesDialogo.hide()");
        context.reset("formularioDialogos:LOVPeriodicidades:globalFilter");
        context.update("formularioDialogos:LOVPeriodicidades");
    }

    public void actualizarTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaNovedades.get(index).setTercero(seleccionTerceros);
                if (!listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(index))) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    }
                }
            } else {
                filtradosListaNovedades.get(index).setTercero(seleccionTerceros);
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosNovedadesEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setTercero(seleccionTerceros);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
         duplicarNovedad.setTercero(seleccionTerceros);
         context.update("formularioDialogos:duplicarNovedad");

         }
        filtradoslistaTerceros = null;
        seleccionTerceros = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("tercerosDialogo.hide()");
        context.reset("formularioDialogos:LOVTerceros:globalFilter");
        context.update("formularioDialogos:LOVTerceros");
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosNovedad();
        } else {
            listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosNovedad();
        }
        if (!listaEmpleadosNovedad.isEmpty()) {
            seleccionMostrar = listaEmpleadosNovedad.get(0);
            listaEmpleadosNovedad = null;
            getListaEmpleadosNovedad();
        }
        listaNovedades = null;
        context.update("form:datosEmpleados");
        context.update("form:datosNovedadesEmpleado");
        filtradosListaEmpleadosNovedad = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }
    
    //DUPLICAR ENCARGATURA
    public void duplicarEN() {
        if (index >= 0) {
            duplicarNovedad = new Novedades();
            k++;
            l = BigInteger.valueOf(k);
            Empleados emple = new Empleados();
            
            for (int i = 0; i < listaEmpleados.size(); i++) {
                if (seleccionMostrar.getId().compareTo(listaEmpleados.get(i).getEmpleado().getSecuencia()) == 0) {
                    emple = listaEmpleados.get(i).getEmpleado();
                }
            }

            if (tipoLista == 0) {
                duplicarNovedad.setSecuencia(l);
                duplicarNovedad.setEmpleado(emple);
                duplicarNovedad.setConcepto(listaNovedades.get(index).getConcepto());
                duplicarNovedad.setFechainicial(listaNovedades.get(index).getFechainicial());
                duplicarNovedad.setFechafinal(listaNovedades.get(index).getFechafinal());
                duplicarNovedad.setFechareporte(listaNovedades.get(index).getFechareporte());
                duplicarNovedad.setValortotal(listaNovedades.get(index).getValortotal());
                duplicarNovedad.setSaldo(listaNovedades.get(index).getSaldo());
                duplicarNovedad.setPeriodicidad(listaNovedades.get(index).getPeriodicidad());
                duplicarNovedad.setTercero(listaNovedades.get(index).getTercero());
                duplicarNovedad.setFormula(listaNovedades.get(index).getFormula());
                duplicarNovedad.setUnidadesparteentera(listaNovedades.get(index).getUnidadesparteentera());
                duplicarNovedad.setUnidadespartefraccion(listaNovedades.get(index).getUnidadespartefraccion());
                duplicarNovedad.setTipo(listaNovedades.get(index).getTipo());
                duplicarNovedad.setTerminal(listaNovedades.get(index).getTerminal());
                duplicarNovedad.setUsuarioreporta(listaNovedades.get(index).getUsuarioreporta());
            }
            if (tipoLista == 1) {
                duplicarNovedad.setSecuencia(l);
                duplicarNovedad.setEmpleado(emple);
                duplicarNovedad.setConcepto(filtradosListaNovedades.get(index).getConcepto());
                duplicarNovedad.setFechainicial(filtradosListaNovedades.get(index).getFechainicial());
                duplicarNovedad.setFechafinal(filtradosListaNovedades.get(index).getFechafinal());
                duplicarNovedad.setFechareporte(filtradosListaNovedades.get(index).getFechareporte());
                duplicarNovedad.setValortotal(filtradosListaNovedades.get(index).getValortotal());
                duplicarNovedad.setSaldo(filtradosListaNovedades.get(index).getSaldo());
                duplicarNovedad.setPeriodicidad(filtradosListaNovedades.get(index).getPeriodicidad());
                duplicarNovedad.setTercero(filtradosListaNovedades.get(index).getTercero());
                duplicarNovedad.setFormula(filtradosListaNovedades.get(index).getFormula());
                duplicarNovedad.setUnidadesparteentera(filtradosListaNovedades.get(index).getUnidadesparteentera());
                duplicarNovedad.setUnidadespartefraccion(filtradosListaNovedades.get(index).getUnidadespartefraccion());
                duplicarNovedad.setTipo(filtradosListaNovedades.get(index).getTipo());
                duplicarNovedad.setTerminal(filtradosListaNovedades.get(index).getTerminal());
                duplicarNovedad.setUsuarioreporta(filtradosListaNovedades.get(index).getUsuarioreporta());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarNovedad");
            context.execute("DuplicarRegistroNovedad.show()");
            index = -1;
            secRegistro = null;
        }
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

    public void cancelarCambioFormulas() {
        filtradoslistaFormulas = null;
        seleccionFormulas = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioConceptos() {
        filtradoslistaConceptos = null;
        seleccionConceptos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioPeriodicidades() {
        filtradoslistaPeriodicidades = null;
        seleccionPeriodicidades = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioTerceros() {
        filtradoslistaTerceros = null;
        seleccionTerceros = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NovedadesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NovedadesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    //LIMPIAR NUEVO REGISTRO NOVEDAD
    public void limpiarNuevaNovedad() {
        nuevaNovedad = new Novedades();
        nuevaNovedad.setPeriodicidad(new Periodicidades());
        nuevaNovedad.getPeriodicidad().setNombre(" ");
        nuevaNovedad.setTercero(new Terceros());
        nuevaNovedad.getTercero().setNombre(" ");
        nuevaNovedad.setConcepto(new Conceptos());
        nuevaNovedad.getConcepto().setDescripcion(" ");
        nuevaNovedad.setTipo("FIJA");
        nuevaNovedad.setUsuarioreporta(new Usuarios());
        nuevaNovedad.setTerminal(" ");
        nuevaNovedad.setFechareporte(new Date());
        index = -1;
        secRegistro = null;
        resultado = 0;
    }
    
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Encargaturas
     */
    public void limpiarduplicarNovedades() {
        duplicarNovedad = new Novedades();
        duplicarNovedad.setPeriodicidad(new Periodicidades());
        duplicarNovedad.getPeriodicidad().setNombre(" ");
        duplicarNovedad.setTercero(new Terceros());
        duplicarNovedad.getTercero().setNombre(" ");
        duplicarNovedad.setConcepto(new Conceptos());
        duplicarNovedad.getConcepto().setDescripcion(" ");
        duplicarNovedad.setTipo("FIJA");
        duplicarNovedad.setUsuarioreporta(new Usuarios());
        duplicarNovedad.setTerminal(" ");
        duplicarNovedad.setFechareporte(new Date());
        
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaNovedades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "NOVEDADES");
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
            if (administrarRastros.verificarHistoricosTabla("NOVEDADES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //BORRAR Novedades
    public void borrarNovedades() {

        if (index >= 0) {

            if (tipoLista == 0) {
                if (!listaNovedadesModificar.isEmpty() && listaNovedadesModificar.contains(listaNovedades.get(index))) {
                    int modIndex = listaNovedadesModificar.indexOf(listaNovedades.get(index));
                    listaNovedadesModificar.remove(modIndex);
                    listaNovedadesBorrar.add(listaNovedades.get(index));
                } else if (!listaNovedadesCrear.isEmpty() && listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    int crearIndex = listaNovedadesCrear.indexOf(listaNovedades.get(index));
                    listaNovedadesCrear.remove(crearIndex);
                } else {
                    listaNovedadesBorrar.add(listaNovedades.get(index));
                }
                listaNovedades.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaNovedadesModificar.isEmpty() && listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                    int modIndex = listaNovedadesModificar.indexOf(filtradosListaNovedades.get(index));
                    listaNovedadesModificar.remove(modIndex);
                    listaNovedadesBorrar.add(filtradosListaNovedades.get(index));
                } else if (!listaNovedadesCrear.isEmpty() && listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    int crearIndex = listaNovedadesCrear.indexOf(filtradosListaNovedades.get(index));
                    listaNovedadesCrear.remove(crearIndex);
                } else {
                    listaNovedadesBorrar.add(filtradosListaNovedades.get(index));
                }
                int CIndex = listaNovedades.indexOf(filtradosListaNovedades.get(index));
                listaNovedades.remove(CIndex);
                filtradosListaNovedades.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesEmpleado");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void setListaEmpleadosNovedad(List<PruebaEmpleados> listaEmpleados) {
        this.listaEmpleadosNovedad = listaEmpleados;
    }

    public List<PruebaEmpleados> getFiltradosListaEmpleadosNovedad() {
        return filtradosListaEmpleadosNovedad;
    }

    public void setFiltradosListaEmpleadosNovedad(List<PruebaEmpleados> filtradosListaEmpleadosNovedad) {
        this.filtradosListaEmpleadosNovedad = filtradosListaEmpleadosNovedad;
    }

    public List<VWActualesTiposTrabajadores> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesEmpleados.tiposTrabajadores();
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<VWActualesTiposTrabajadores> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<VWActualesTiposTrabajadores> getFiltradoslistaEmpleados() {
        return filtradoslistaEmpleados;
    }

    public void setFiltradoslistaEmpleados(List<VWActualesTiposTrabajadores> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleados = filtradoslistaEmpleados;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public BigInteger getSecuenciaEmpleado() {
        return secuenciaEmpleado;
    }

    public void setSecuenciaEmpleado(BigInteger secuenciaEmpleado) {
        this.secuenciaEmpleado = secuenciaEmpleado;
    }

    public PruebaEmpleados getSeleccionMostrar() {
        return seleccionMostrar;
    }

    public void setSeleccionMostrar(PruebaEmpleados seleccionMostrar) {
        this.seleccionMostrar = seleccionMostrar;
    }

    public VWActualesTiposTrabajadores getSeleccionEmpleados() {
        return seleccionEmpleados;
    }

    public void setSeleccionEmpleados(VWActualesTiposTrabajadores seleccionEmpleados) {
        this.seleccionEmpleados = seleccionEmpleados;
    }

    //LISTA NOVEDADES
    public List<Novedades> getListaNovedades() {
        if (listaNovedades == null) {
            listaNovedades = administrarNovedadesEmpleados.novedadesEmpleado(seleccionMostrar.getId());

        }
        return listaNovedades;
    }

    public void setListaNovedades(List<Novedades> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public List<Novedades> getFiltradosListaNovedades() {
        return filtradosListaNovedades;
    }

    public void setFiltradosListaNovedades(List<Novedades> filtradosListaNovedades) {
        this.filtradosListaNovedades = filtradosListaNovedades;
    }

    //L.O.V PERIODICIDADES
    public List<Periodicidades> getListaPeriodicidades() {
        if (listaPeriodicidades == null) {
            listaPeriodicidades = administrarNovedadesEmpleados.lovPeriodicidades();
        }
        return listaPeriodicidades;
    }

    public void setListaPeriodicidades(List<Periodicidades> listaPeriodicidades) {
        this.listaPeriodicidades = listaPeriodicidades;
    }

    public List<Periodicidades> getFiltradoslistaPeriodicidades() {
        return filtradoslistaPeriodicidades;
    }

    public void setFiltradoslistaPeriodicidades(List<Periodicidades> filtradoslistaPeriodicidades) {
        this.filtradoslistaPeriodicidades = filtradoslistaPeriodicidades;
    }

    public Periodicidades getSeleccionPeriodicidades() {
        return seleccionPeriodicidades;
    }

    public void setSeleccionPeriodicidades(Periodicidades seleccionPeriodicidades) {
        this.seleccionPeriodicidades = seleccionPeriodicidades;
    }

    //L.O.V CONCEPTOS
    public List<Conceptos> getListaConceptos() {
        if (listaConceptos == null) {
            listaConceptos = administrarNovedadesEmpleados.lovConceptos();
        }
        return listaConceptos;
    }

    public void setListaConceptos(List<Conceptos> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }
    //L.O.V FORMULAS

    public List<Formulas> getListaFormulas() {
        if (listaFormulas == null) {
            listaFormulas = administrarNovedadesEmpleados.lovFormulas();
        }
        return listaFormulas;
    }

    public void setListaFormulas(List<Formulas> listaFormulas) {
        this.listaFormulas = listaFormulas;
    }

    public List<Conceptos> getFiltradoslistaConceptos() {
        return filtradoslistaConceptos;
    }

    public void setFiltradoslistaConceptos(List<Conceptos> filtradoslistaConceptos) {
        this.filtradoslistaConceptos = filtradoslistaConceptos;
    }

    public Conceptos getSeleccionConceptos() {
        return seleccionConceptos;
    }

    public void setSeleccionConceptos(Conceptos seleccionConceptos) {
        this.seleccionConceptos = seleccionConceptos;
    }

    public List<Formulas> getFiltradoslistaFormulas() {
        return filtradoslistaFormulas;
    }

    public void setFiltradoslistaFormulas(List<Formulas> filtradoslistaFormulas) {
        this.filtradoslistaFormulas = filtradoslistaFormulas;
    }

    public Formulas getSeleccionFormulas() {
        return seleccionFormulas;
    }

    public void setSeleccionFormulas(Formulas seleccionFormulas) {
        this.seleccionFormulas = seleccionFormulas;
    }
    //L.O.V TERCEROS

    public List<Terceros> getListaTerceros() {
        if (listaTerceros == null) {
            listaTerceros = administrarNovedadesEmpleados.lovTerceros();
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

    public Novedades getNuevaNovedad() {
        return nuevaNovedad;
    }

    public void setNuevaNovedad(Novedades nuevaNovedad) {
        this.nuevaNovedad = nuevaNovedad;
    }

    public Novedades getEditarNovedades() {
        return editarNovedades;
    }

    public void setEditarNovedades(Novedades editarNovedades) {
        this.editarNovedades = editarNovedades;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public Novedades getDuplicarNovedad() {
        return duplicarNovedad;
    }

    public void setDuplicarNovedad(Novedades duplicarNovedad) {
        this.duplicarNovedad = duplicarNovedad;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public List<PruebaEmpleados> getListaEmpleadosNovedad() {
        if (listaEmpleadosNovedad == null) {
            listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosNovedad();
            seleccionMostrar = listaEmpleadosNovedad.get(1);
            System.out.println(seleccionMostrar.getId());
        }
        return listaEmpleadosNovedad;
    }

    public String getAlias() {
        alias = administrarNovedadesEmpleados.alias();
        System.out.println("Alias: " + alias);
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Usuarios getUsuarioBD() {
        getAlias();
        usuarioBD = administrarNovedadesEmpleados.usuarioBD(alias);
        return usuarioBD;
    }

    public void setUsuarioBD(Usuarios usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public int getResultado() {
        if (!listaNovedadesBorrar.isEmpty()) {
            for (int i = 0; i < listaNovedadesBorrar.size(); i++) {
                resultado = administrarNovedadesEmpleados.solucionesFormulas(listaNovedadesBorrar.get(i).getSecuencia());
                
            }
        }
        return resultado;
    }
}

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
import Entidades.Terceros;
import Entidades.Usuarios;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNovedadesTercerosInterface;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlNovedadesTerceros implements Serializable {

    @EJB
    AdministrarNovedadesTercerosInterface administrarNovedadesTerceros;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //SECUENCIA DEL CONCEPTO
    private BigInteger secuenciaTercero;
    //LISTA NOVEDADES
    private List<Novedades> listaNovedades;
    private List<Novedades> filtradosListaNovedades;
    //LISTA DE ARRIBA
    private List<Terceros> listaTercerosNovedad;
    private List<Terceros> filtradosListaTercerosNovedad;
    private Terceros seleccionMostrar; //Seleccion Mostrar
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
    private String CodigoEmpleado, CodigoConcepto, NitTercero, Formula, DescripcionConcepto, DescripcionPeriodicidad, NombreTercero;
    private Date FechaFinal;
    private String CodigoPeriodicidad;
    private BigDecimal Saldo;
    private Integer HoraDia, MinutoHora;
    //L.O.V Conceptos
    private List<Conceptos> listaConceptos;
    private List<Conceptos> filtradoslistaConceptos;
    private Conceptos seleccionConceptos;
    //L.O.V Empleados
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradoslistaEmpleados;
    private Empleados seleccionEmpleados;
    //L.O.V PERIODICIDADES
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
    private Column nTEmpleadoCodigo, nTEmpleadoNombre, nTConceptoCodigo, nTConceptoDescripcion, nTFechasInicial, nTFechasFinal,
            nTValor, nTSaldo, nTPeriodicidadCodigo, nTDescripcionPeriodicidad, nTFormulas, nTHorasDias, nTMinutosHoras, nTTipo;
    //Duplicar
    private Novedades duplicarNovedad;
    //USUARIO
    private String alias;
    private Usuarios usuarioBD;
    //VALIDAR SI EL QUE SE VA A BORRAR ESTÁ EN SOLUCIONES FORMULAS
    private int resultado;
    private boolean todas;
    private boolean actuales;

    public ControlNovedadesTerceros() {
        permitirIndex = true;
        listaNovedades = null;
        listaEmpleados = null;
        listaFormulas = null;
        listaEmpleados = null;
        todas = false;
        actuales = true;
        listaPeriodicidades = null;
        listaTercerosNovedad = null;
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
        nuevaNovedad.setFormula(new Formulas());
        nuevaNovedad.setTercero(new Terceros());
        nuevaNovedad.setPeriodicidad(new Periodicidades());
        nuevaNovedad.setFechareporte(new Date());
        nuevaNovedad.setTipo("FIJA");
    }

    public void limpiarListas() {
        listaNovedadesCrear.clear();
        listaNovedadesBorrar.clear();
        listaNovedadesModificar.clear();
        secuenciaTercero = seleccionMostrar.getSecuencia();
        listaNovedades = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesTercero");
    }

    //Ubicacion Celda Arriba 
    public void cambiarTercero() {
        //Si ninguna de las 3 listas (crear,modificar,borrar) tiene algo, hace esto
        //{
        if (listaNovedadesCrear.isEmpty() && listaNovedadesBorrar.isEmpty() && listaNovedadesModificar.isEmpty()) {
            secuenciaTercero = seleccionMostrar.getSecuencia();
            listaNovedades = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesTercero");
            //}
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:cambiar");
            context.execute("cambiar.show()");
        }
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaNovedades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int result = administrarRastros.obtenerTabla(secRegistro, "NOVEDADES");
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
            if (administrarRastros.verificarHistoricosTabla("NOVEDADES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //Ubicacion Celda Indice Abajo. //Van los que no son NOT NULL.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaNovedades.get(index).getSecuencia();
                if (cualCelda == 0) {
                    CodigoEmpleado = listaNovedades.get(index).getEmpleado().getCodigoempleadoSTR();
                } else if (cualCelda == 2) {
                    CodigoConcepto = listaNovedades.get(index).getConcepto().getCodigoSTR();
                } else if (cualCelda == 5) {
                    FechaFinal = listaNovedades.get(index).getFechafinal();
                } else if (cualCelda == 7) {
                    Saldo = listaNovedades.get(index).getSaldo();
                } else if (cualCelda == 8) {
                    CodigoPeriodicidad = listaNovedades.get(index).getPeriodicidad().getCodigoStr();
                } else if (cualCelda == 9) {
                    DescripcionPeriodicidad = listaNovedades.get(index).getPeriodicidad().getNombre();
                } else if (cualCelda == 10) {
                    NitTercero = listaNovedades.get(index).getTercero().getNitalternativo();
                } else if (cualCelda == 11) {
                    NombreTercero = listaNovedades.get(index).getTercero().getNombre();
                } else if (cualCelda == 13) {
                    HoraDia = listaNovedades.get(index).getUnidadesparteentera();
                } else if (cualCelda == 14) {
                    MinutoHora = listaNovedades.get(index).getUnidadespartefraccion();
                }
            } else {
                secRegistro = filtradosListaNovedades.get(index).getSecuencia();
                if (cualCelda == 0) {
                    CodigoEmpleado = filtradosListaNovedades.get(index).getEmpleado().getCodigoempleadoSTR();
                } else if (cualCelda == 2) {
                    CodigoConcepto = listaNovedades.get(index).getConcepto().getCodigoSTR();
                } else if (cualCelda == 3) {
                    FechaFinal = filtradosListaNovedades.get(index).getFechafinal();
                } else if (cualCelda == 5) {
                    Saldo = filtradosListaNovedades.get(index).getSaldo();
                } else if (cualCelda == 6) {
                    CodigoPeriodicidad = filtradosListaNovedades.get(index).getPeriodicidad().getCodigoStr();
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
    }

    //GUARDAR
    public void guardarCambiosNovedades() {

        int pasas = 0;
        if (guardado == false) {
            System.out.println("Realizando Operaciones Novedades Terceros");

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
                    if (listaNovedadesBorrar.get(i).getPeriodicidad().getSecuencia() == null) {
                        listaNovedadesBorrar.get(i).setPeriodicidad(null);
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
                    administrarNovedadesTerceros.borrarNovedades(listaNovedadesBorrar.get(i));
                }
                System.out.println("Entra");
                listaNovedadesBorrar.clear();
            }

            if (!listaNovedadesCrear.isEmpty()) {
                for (int i = 0; i < listaNovedadesCrear.size(); i++) {
                    System.out.println("Creando...");

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
                    administrarNovedadesTerceros.crearNovedades(listaNovedadesCrear.get(i));
                }
                System.out.println("LimpiaLista");
                listaNovedadesCrear.clear();
            }
            if (!listaNovedadesModificar.isEmpty()) {
                administrarNovedadesTerceros.modificarNovedades(listaNovedadesModificar);
                listaNovedadesModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaNovedades = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesTercero");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        System.out.println("Tamaño lista: " + listaNovedadesCrear.size());
        System.out.println("Valor k: " + k);
        index = -1;
        secRegistro = null;
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
            context.update("form:datosNovedadesTercero");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //CREAR NOVEDADES
    public void agregarNuevaNovedadTercero() throws UnknownHostException {
        int pasa = 0;
        int pasa2 = 0;
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevaNovedad.getFechainicial() == null) {
            System.out.println("Entro a Fecha Inicial");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicial\n";
            pasa++;
        }

        if (nuevaNovedad.getConcepto().getCodigoSTR().equals("0") || nuevaNovedad.getConcepto().getCodigoSTR().equals("")) {
            System.out.println("Entro a Concepto");
            mensajeValidacion = mensajeValidacion + " * Concepto\n";
            pasa++;
        }

        if (nuevaNovedad.getEmpleado().getCodigoempleadoSTR().equals("0")) {
            System.out.println("Entro a Empleado");
            mensajeValidacion = mensajeValidacion + " * Empleado\n";
            pasa++;
        }

        if (nuevaNovedad.getFormula().getNombrelargo().equals("")) {
            System.out.println("Entro a Formula");
            mensajeValidacion = mensajeValidacion + " * Formula\n";
            pasa++;
        }
        if (nuevaNovedad.getValortotal() == null) {
            System.out.println("Entro a Valor");
            mensajeValidacion = mensajeValidacion + " * Valor\n";
            pasa++;
        }

        if (nuevaNovedad.getTipo() == null) {
            System.out.println("Entro a Tipo");
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }
        
        if (nuevaNovedad.getEmpleado() != null && pasa==0){
        for (int i = 0; i < listaEmpleados.size(); i++) {
            if (nuevaNovedad.getEmpleado().getSecuencia().compareTo(listaEmpleados.get(i).getSecuencia()) == 0) {

                if (nuevaNovedad.getFechainicial().compareTo(nuevaNovedad.getEmpleado().getFechacreacion()) < 0) {
                    context.update("formularioDialogos:inconsistencia");
                    context.execute("inconsistencia.show()");
                    pasa2++;
                }
            }
        }
        }
        if (nuevaNovedad.getFechafinal() != null) {
            if (nuevaNovedad.getFechainicial().compareTo(nuevaNovedad.getFechafinal()) > 0) {
                context.update("formularioDialogos:fechas");
                context.execute("fechas.show()");
                pasa2++;
            }
        }
        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedadTercero");
            context.execute("validacionNuevaNovedadTercero.show()");
        }

        if (pasa == 0 && pasa2 == 0) {
            if (bandera == 1) {
                nTEmpleadoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoCodigo");
                nTEmpleadoCodigo.setFilterStyle("display: none; visibility: hidden;");
                nTEmpleadoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoNombre");
                nTEmpleadoNombre.setFilterStyle("display: none; visibility: hidden;");
                nTConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoCodigo");
                nTConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
                nTConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoDescripcion");
                nTConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                nTFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasInicial");
                nTFechasInicial.setFilterStyle("display: none; visibility: hidden;");
                nTFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasFinal");
                nTFechasFinal.setFilterStyle("display: none; visibility: hidden;");
                nTValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTValor");
                nTValor.setFilterStyle("display: none; visibility: hidden;");
                nTSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTSaldo");
                nTSaldo.setFilterStyle("display: none; visibility: hidden;");
                nTPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTPeriodicidadCodigo");
                nTPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
                nTDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTDescripcionPeriodicidad");
                nTDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
                nTFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFormulas");
                nTFormulas.setFilterStyle("display: none; visibility: hidden;");
                nTHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTHorasDias");
                nTHorasDias.setFilterStyle("display: none; visibility: hidden;");
                nTMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTMinutosHoras");
                nTMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
                nTTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTTipo");
                nTTipo.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
                bandera = 0;
                filtradosListaNovedades = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevaNovedad.setSecuencia(l);
            // OBTENER EL TERMINAL {
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
            //}
            getAlias();
            System.out.println("Alias: " + alias);
            getUsuarioBD();
            System.out.println("UsuarioBD: " + usuarioBD);
            nuevaNovedad.setTercero(seleccionMostrar);
            nuevaNovedad.setTerminal(localMachine.getHostName());
            nuevaNovedad.setUsuarioreporta(usuarioBD);
            listaNovedadesCrear.add(nuevaNovedad);
            System.out.println(listaNovedadesCrear.size());
            System.out.println(listaNovedadesCrear.get(0).getTipo());
            System.out.println(nuevaNovedad.getTipo());
            listaNovedades.add(nuevaNovedad);
            nuevaNovedad = new Novedades();
            nuevaNovedad.setFormula(new Formulas());
            nuevaNovedad.setConcepto(new Conceptos());
            nuevaNovedad.setFechareporte(new Date());
            nuevaNovedad.setPeriodicidad(new Periodicidades());
            nuevaNovedad.setTipo("FIJA");

            context.update("form:datosNovedadesTercero");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevaNovedadTercero.hide()");
            index = -1;
            secRegistro = null;
        } else {
        }
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
        RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
    }

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

            context.update("form:datosNovedadesTercero");
        } else if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoLista == 0) {
                listaNovedades.get(indice).getFormula().setNombresFormula(Formula);
            } else {
                filtradosListaNovedades.get(indice).getFormula().setNombresFormula(Formula);
            }

            for (int i = 0; i < listaFormulas.size(); i++) {
                if (listaFormulas.get(i).getNombresFormula().startsWith(valorConfirmar.toUpperCase())) {
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

            for (int i = 0; i < listaTerceros.size(); i++) {
                if (listaTerceros.get(i).getNitalternativo().startsWith(valorConfirmar.toUpperCase())) {
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
        } else if (confirmarCambio.equalsIgnoreCase("EMPLEADO")) {

            if (tipoLista == 0) {
                listaNovedades.get(indice).getEmpleado().setCodigoempleadoSTR(CodigoEmpleado);
            } else {
                filtradosListaNovedades.get(indice).getEmpleado().setCodigoempleadoSTR(CodigoEmpleado);
            }

            for (int i = 0; i < listaEmpleados.size(); i++) {
                if (listaEmpleados.get(i).getCodigoempleadoSTR().startsWith(valorConfirmar.toString().toUpperCase())) {

                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaNovedades.get(indice).setEmpleado(listaEmpleados.get(indiceUnicoElemento));
                } else {
                    filtradosListaNovedades.get(indice).setEmpleado(listaEmpleados.get(indiceUnicoElemento));
                }
                listaEmpleados.clear();
                getListaEmpleados();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:empleadosDialogo");
                context.execute("empleadosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CODIGOPERIODICIDAD")) {

            if (tipoLista == 0) {
                listaNovedades.get(indice).getPeriodicidad().setCodigoStr(CodigoPeriodicidad);
            } else {
                filtradosListaNovedades.get(indice).getPeriodicidad().setCodigoStr(CodigoPeriodicidad);
            }

            for (int i = 0; i < listaPeriodicidades.size(); i++) {
                if ((listaPeriodicidades.get(i).getCodigoStr()).startsWith(valorConfirmar.toString().toUpperCase())) {

                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaNovedades.get(indice).setPeriodicidad(listaPeriodicidades.get(indiceUnicoElemento));
                } else {
                    filtradosListaNovedades.get(indice).setPeriodicidad(listaPeriodicidades.get(indiceUnicoElemento));
                }
                listaPeriodicidades.clear();
                getListaPeriodicidades();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTO")) {

            if (tipoLista == 0) {
                listaNovedades.get(indice).getConcepto().setCodigoSTR(CodigoConcepto);
            } else {
                filtradosListaNovedades.get(indice).getConcepto().setCodigoSTR(CodigoConcepto);
            }

            for (int i = 0; i < listaConceptos.size(); i++) {
                if (listaConceptos.get(i).getCodigoSTR().startsWith(valorConfirmar.toString().toUpperCase())) {
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
        context.update("form:datosNovedadesTercero");
    }

    //DUPLICAR TERCERO NOVEDAD
    public void duplicarTN() {
        if (index >= 0) {
            duplicarNovedad = new Novedades();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarNovedad.setSecuencia(l);
                duplicarNovedad.setEmpleado(listaNovedades.get(index).getEmpleado());
                duplicarNovedad.setConcepto(listaNovedades.get(index).getConcepto());
                duplicarNovedad.setFechainicial(listaNovedades.get(index).getFechainicial());
                duplicarNovedad.setFechafinal(listaNovedades.get(index).getFechafinal());
                duplicarNovedad.setFechareporte(listaNovedades.get(index).getFechareporte());
                duplicarNovedad.setValortotal(listaNovedades.get(index).getValortotal());
                duplicarNovedad.setSaldo(listaNovedades.get(index).getSaldo());
                duplicarNovedad.setPeriodicidad(listaNovedades.get(index).getPeriodicidad());
                duplicarNovedad.setTercero(seleccionMostrar);
                duplicarNovedad.setFormula(listaNovedades.get(index).getFormula());
                duplicarNovedad.setUnidadesparteentera(listaNovedades.get(index).getUnidadesparteentera());
                duplicarNovedad.setUnidadespartefraccion(listaNovedades.get(index).getUnidadespartefraccion());
                duplicarNovedad.setTipo(listaNovedades.get(index).getTipo());
                duplicarNovedad.setTerminal(listaNovedades.get(index).getTerminal());
                duplicarNovedad.setUsuarioreporta(listaNovedades.get(index).getUsuarioreporta());
            }
            if (tipoLista == 1) {
                duplicarNovedad.setSecuencia(l);
                duplicarNovedad.setEmpleado(filtradosListaNovedades.get(index).getEmpleado());
                duplicarNovedad.setConcepto(filtradosListaNovedades.get(index).getConcepto());
                duplicarNovedad.setFechainicial(filtradosListaNovedades.get(index).getFechainicial());
                duplicarNovedad.setFechafinal(filtradosListaNovedades.get(index).getFechafinal());
                duplicarNovedad.setFechareporte(filtradosListaNovedades.get(index).getFechareporte());
                duplicarNovedad.setValortotal(filtradosListaNovedades.get(index).getValortotal());
                duplicarNovedad.setSaldo(filtradosListaNovedades.get(index).getSaldo());
                duplicarNovedad.setPeriodicidad(filtradosListaNovedades.get(index).getPeriodicidad());
                duplicarNovedad.setTercero(seleccionMostrar);
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

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Novedades
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
        if (duplicarNovedad.getEmpleado().getPersona().getNombreCompleto().equals(" ")) {
            System.out.println("Entro a Empleado");
            mensajeValidacion = mensajeValidacion + " * Empleado\n";
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

        if (duplicarNovedad.getEmpleado() == null) {
            System.out.println("Entro a Empleado");
            mensajeValidacion = mensajeValidacion + " * Empleado\n";
            pasa++;
        }

        if (duplicarNovedad.getTipo() == null) {
            System.out.println("Entro a Tipo");
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }

        for (int i = 0; i < listaEmpleados.size(); i++) {
            if (duplicarNovedad.getEmpleado().getSecuencia().compareTo(listaEmpleados.get(i).getSecuencia()) == 0) {

                if (duplicarNovedad.getFechainicial().compareTo(duplicarNovedad.getEmpleado().getFechacreacion()) < 0) {
                    context.update("formularioDialogos:inconsistencia");
                    context.execute("inconsistencia.show()");
                    pasa2++;
                }
            }
        }

        if (duplicarNovedad.getFechainicial().compareTo(duplicarNovedad.getFechafinal()) > 0) {
            context.update("formularioDialogos:fechas");
            context.execute("fechas.show()");
            pasa2++;
        }

        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedadTercero");
            context.execute("validacionNuevaNovedadTercero.show()");
        }


        if (pasa2 == 0) {
            listaNovedades.add(duplicarNovedad);
            listaNovedadesCrear.add(duplicarNovedad);

            context.update("form:datosNovedadesTercero");
            index = -1;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                nTEmpleadoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoCodigo");
                nTEmpleadoCodigo.setFilterStyle("display: none; visibility: hidden;");
                nTEmpleadoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoNombre");
                nTEmpleadoNombre.setFilterStyle("display: none; visibility: hidden;");
                nTConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoCodigo");
                nTConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
                nTConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoDescripcion");
                nTConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                nTFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasInicial");
                nTFechasInicial.setFilterStyle("display: none; visibility: hidden;");
                nTFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasFinal");
                nTFechasFinal.setFilterStyle("display: none; visibility: hidden;");
                nTValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTValor");
                nTValor.setFilterStyle("display: none; visibility: hidden;");
                nTSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTSaldo");
                nTSaldo.setFilterStyle("display: none; visibility: hidden;");
                nTPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTPeriodicidadCodigo");
                nTPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
                nTDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTDescripcionPeriodicidad");
                nTDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
                nTFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFormulas");
                nTFormulas.setFilterStyle("display: none; visibility: hidden;");
                nTHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTHorasDias");
                nTHorasDias.setFilterStyle("display: none; visibility: hidden;");
                nTMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTMinutosHoras");
                nTMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
                nTTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTTipo");
                nTTipo.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
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
            duplicarNovedad.setTercero(seleccionMostrar);
            duplicarNovedad = new Novedades();
            context.update("formularioDialogos:DuplicarRegistroNovedad");
            context.execute("DuplicarRegistroNovedad.hide()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaTercerosNovedad.isEmpty()) {
            listaTercerosNovedad.clear();
            listaTercerosNovedad = administrarNovedadesTerceros.Terceros();
        } else {
            listaTercerosNovedad = administrarNovedadesTerceros.Terceros();
        }
        if (!listaTercerosNovedad.isEmpty()) {
            seleccionMostrar = listaTercerosNovedad.get(0);
            listaTercerosNovedad = null;
            getListaTerceros();
        }
        listaNovedades = null;
        context.update("form:datosTerceros");
        context.update("form:datosNovedadesTercero");
        filtradosListaTercerosNovedad = null;
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
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaNovedades.get(index).setEmpleado(seleccionEmpleados);
                if (!listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(index))) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    }
                }
            } else {
                filtradosListaNovedades.get(index).setEmpleado(seleccionEmpleados);
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
            context.update("form:datosNovedadesTercero");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.setEmpleado(seleccionEmpleados);
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.setEmpleado(seleccionEmpleados);
            context.update("formularioDialogos:duplicarNovedad");

        }
        filtradoslistaEmpleados = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
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
                context.update("formularioDialogos:editarEmpleadosCodigos");
                context.execute("editarEmpleadosCodigos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarEmpleadosNombres");
                context.execute("editarEmpleadosNombres.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarConceptosCodigos");
                context.execute("editarConceptosCodigos.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarConceptosDescripciones");
                context.execute("editarConceptosDescripciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editFechaInicial");
                context.execute("editFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editFechasFinales");
                context.execute("editFechasFinales.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarValores");
                context.execute("editarValores.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarSaldos");
                context.execute("editarSaldos.show()");
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarPeriodicidadesCodigos");
                context.execute("editarPeriodicidadesCodigos.show()");
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarPeriodicidadesDescripciones");
                context.execute("editarPeriodicidadesDescripciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:editarFormulas");
                context.execute("editarFormulas.show()");
                cualCelda = -1;
            } else if (cualCelda == 13) {
                context.update("formularioDialogos:editarHorasDias");
                context.execute("editarHorasDias.show()");
                cualCelda = -1;
            } else if (cualCelda == 14) {
                context.update("formularioDialogos:editarMinutosHoras");
                context.execute("editarMinutosHoras.show()");
                cualCelda = -1;
            } else if (cualCelda == 15) {
                context.update("formularioDialogos:editarTipos");
                context.execute("editarTipos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }
    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:empleadosDialogo");
                context.execute("empleadosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:formulasDialogo");
                context.execute("formulasDialogo.show()");
            }
        }
    }

    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            nTEmpleadoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoCodigo");
            nTEmpleadoCodigo.setFilterStyle("width: 60px");
            nTEmpleadoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoNombre");
            nTEmpleadoNombre.setFilterStyle("width: 60px");
            nTConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoCodigo");
            nTConceptoCodigo.setFilterStyle("width: 60px");
            nTConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoDescripcion");
            nTConceptoDescripcion.setFilterStyle("width: 60px");
            nTFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasInicial");
            nTFechasInicial.setFilterStyle("width: 60px");
            nTFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasFinal");
            nTFechasFinal.setFilterStyle("width: 60px");
            nTValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTValor");
            nTValor.setFilterStyle("width: 60px");
            nTSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTSaldo");
            nTSaldo.setFilterStyle("width: 60px");
            nTPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTPeriodicidadCodigo");
            nTPeriodicidadCodigo.setFilterStyle("width: 60px");
            nTDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTDescripcionPeriodicidad");
            nTDescripcionPeriodicidad.setFilterStyle("width: 60px");
            nTFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFormulas");
            nTFormulas.setFilterStyle("width: 60px");
            nTHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTHorasDias");
            nTHorasDias.setFilterStyle("width: 60px");
            nTMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTMinutosHoras");
            nTMinutosHoras.setFilterStyle("width: 60px");
            nTTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTTipo");
            nTTipo.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            nTEmpleadoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoCodigo");
            nTEmpleadoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTEmpleadoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoNombre");
            nTEmpleadoNombre.setFilterStyle("display: none; visibility: hidden;");
            nTConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoCodigo");
            nTConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoDescripcion");
            nTConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nTFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasInicial");
            nTFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nTFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasFinal");
            nTFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nTValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTValor");
            nTValor.setFilterStyle("display: none; visibility: hidden;");
            nTSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTSaldo");
            nTSaldo.setFilterStyle("display: none; visibility: hidden;");
            nTPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTPeriodicidadCodigo");
            nTPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTDescripcionPeriodicidad");
            nTDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nTFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFormulas");
            nTFormulas.setFilterStyle("display: none; visibility: hidden;");
            nTHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTHorasDias");
            nTHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nTMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTMinutosHoras");
            nTMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nTTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTTipo");
            nTTipo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }
    }
    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesTercerosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "NovedadesTerceroPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesTercerosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NovedadesTercerosXLS", false, false, "UTF-8", null, null);
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
        nuevaNovedad.setEmpleado(new Empleados());
        nuevaNovedad.getEmpleado().getPersona().setNombreCompleto(" ");
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
            context.update("form:datosNovedadesTercero");
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
            context.update("form:datosNovedadesTercero");
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
            context.update("form:datosNovedadesTercero");
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
    
    public void cancelarCambioTercerosNovedad() {
        filtradosListaTercerosNovedad = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void actualizarTercerosNovedad() {
        RequestContext context = RequestContext.getCurrentInstance();
        Terceros t = seleccionTerceros;

        if (!listaTercerosNovedad.isEmpty()) {
            listaTercerosNovedad.clear();
            listaTercerosNovedad.add(t);
            seleccionMostrar = listaTercerosNovedad.get(0);
        } else {
            listaTercerosNovedad.add(t);
        }
        secuenciaTercero = seleccionTerceros.getSecuencia();
        listaNovedades = null;
        context.execute("tercerosDialogo.hide()");
        context.reset("formularioDialogos:LOVTerceros:globalFilter");
        context.update("formularioDialogos:LOVTerceros");
        context.update("form:datosTerceros");
        context.update("form:datosNovedadesTercero");
        filtradosListaTercerosNovedad = null;
        seleccionTerceros = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("EMPLEADO")) {
            if (tipoNuevo == 1) {
                CodigoEmpleado = nuevaNovedad.getEmpleado().getCodigoempleadoSTR();
            } else if (tipoNuevo == 2) {
                CodigoEmpleado = duplicarNovedad.getEmpleado().getCodigoempleadoSTR();
            }
        } else if (Campo.equals("CODIGO")) {
            if (tipoNuevo == 1) {
                CodigoPeriodicidad = nuevaNovedad.getPeriodicidad().getCodigoStr();
            } else if (tipoNuevo == 2) {
                CodigoPeriodicidad = duplicarNovedad.getPeriodicidad().getCodigoStr();
            }
        } else if (Campo.equals("CONCEPTO")) {
            if (tipoNuevo == 1) {
                CodigoConcepto = nuevaNovedad.getConcepto().getCodigoSTR();
            } else if (tipoNuevo == 2) {
                CodigoConcepto = duplicarNovedad.getConcepto().getCodigoSTR();
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
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTO")) {
            if (tipoNuevo == 1) {
                nuevaNovedad.getConcepto().setCodigoSTR(CodigoConcepto);
            } else if (tipoNuevo == 2) {
                duplicarNovedad.getConcepto().setCodigoSTR(CodigoConcepto);
            }

            for (int i = 0; i < listaConceptos.size(); i++) {
                if (listaConceptos.get(i).getCodigoSTR().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaNovedad.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoConceptoCodigo");
                    context.update("formularioDialogos:nuevoConceptoDescripcion");
                } else if (tipoNuevo == 2) {
                    duplicarNovedad.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarConceptoCodigo");
                    context.update("formularioDialogos:duplicarConceptoDescripcion");
                }
                listaConceptos.clear();
                getListaConceptos();
            } else {
                context.update("form:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoConceptoCodigo");
                    context.update("formularioDialogos:nuevoConceptoDescripcion");

                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarConceptoCodigo");
                    context.update("formularioDialogos:duplicarConceptoDescripcion");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoNuevo == 1) {
                nuevaNovedad.getPeriodicidad().setCodigoStr(CodigoPeriodicidad);
            } else if (tipoNuevo == 2) {
                duplicarNovedad.getPeriodicidad().setCodigoStr(CodigoPeriodicidad);
            }

            for (int i = 0; i < listaPeriodicidades.size(); i++) {
                if (listaPeriodicidades.get(i).getCodigoStr().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaNovedad.setPeriodicidad(listaPeriodicidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaPeriodicidadCodigo");
                    context.update("formularioDialogos:nuevaPeriodicidadDescripcion");
                } else if (tipoNuevo == 2) {
                    duplicarNovedad.setPeriodicidad(listaPeriodicidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarPeriodicidadCodigo");
                    context.update("formularioDialogos:duplicarPeriodicidadDescripcion");
                }
                listaPeriodicidades.clear();
                getListaPeriodicidades();
            } else {
                context.update("form:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaPeriodicidadCodigo");
                    context.update("formularioDialogos:nuevaPeriodicidadDescripcion");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarPeriodicidadCodigo");
                    context.update("formularioDialogos:duplicarPeriodicidadDescripcion");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("EMPLEADO")) {
            if (tipoNuevo == 1) {
                nuevaNovedad.getEmpleado().setCodigoempleadoSTR(CodigoEmpleado);
            } else if (tipoNuevo == 2) {
                duplicarNovedad.getEmpleado().setCodigoempleadoSTR(CodigoEmpleado);
            }

            for (int i = 0; i < listaEmpleados.size(); i++) {
                if (listaEmpleados.get(i).getCodigoempleadoSTR().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaNovedad.setEmpleado(listaEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoEmpleadoCodigo");
                    context.update("formularioDialogos:nuevoEmpleadoNombre");
                } else if (tipoNuevo == 2) {
                    duplicarNovedad.setEmpleado(listaEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEmpleadoCodigo");
                    context.update("formularioDialogos:duplicarEmpleadoNombre");
                }
                listaEmpleados.clear();
                getListaEmpleados();
            } else {
                context.update("form:empleadosDialogo");
                context.execute("empleadosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoEmpleadoCodigo");
                    context.update("formularioDialogos:nuevoEmpleadoNombre");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEmpleadoNombre");
                    context.update("formularioDialogos:duplicarEmpleadoCodigo");
                }
            }
        }
    }
    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            nTEmpleadoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoCodigo");
            nTEmpleadoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTEmpleadoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoNombre");
            nTEmpleadoNombre.setFilterStyle("display: none; visibility: hidden;");
            nTConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoCodigo");
            nTConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoDescripcion");
            nTConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nTFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasInicial");
            nTFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nTFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasFinal");
            nTFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nTValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTValor");
            nTValor.setFilterStyle("display: none; visibility: hidden;");
            nTSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTSaldo");
            nTSaldo.setFilterStyle("display: none; visibility: hidden;");
            nTPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTPeriodicidadCodigo");
            nTPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTDescripcionPeriodicidad");
            nTDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nTFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFormulas");
            nTFormulas.setFilterStyle("display: none; visibility: hidden;");
            nTHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTHorasDias");
            nTHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nTMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTMinutosHoras");
            nTMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nTTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTTipo");
            nTTipo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }

        listaNovedadesBorrar.clear();
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
        listaNovedades = null;
        guardado = true;
        permitirIndex = true;
        resultado = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesTercero");

    }

    public void salir() {
        if (bandera == 1) {
            nTEmpleadoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoCodigo");
            nTEmpleadoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTEmpleadoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTEmpleadoNombre");
            nTEmpleadoNombre.setFilterStyle("display: none; visibility: hidden;");
            nTConceptoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoCodigo");
            nTConceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTConceptoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTConceptoDescripcion");
            nTConceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            nTFechasInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasInicial");
            nTFechasInicial.setFilterStyle("display: none; visibility: hidden;");
            nTFechasFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFechasFinal");
            nTFechasFinal.setFilterStyle("display: none; visibility: hidden;");
            nTValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTValor");
            nTValor.setFilterStyle("display: none; visibility: hidden;");
            nTSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTSaldo");
            nTSaldo.setFilterStyle("display: none; visibility: hidden;");
            nTPeriodicidadCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTPeriodicidadCodigo");
            nTPeriodicidadCodigo.setFilterStyle("display: none; visibility: hidden;");
            nTDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTDescripcionPeriodicidad");
            nTDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            nTFormulas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTFormulas");
            nTFormulas.setFilterStyle("display: none; visibility: hidden;");
            nTHorasDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTHorasDias");
            nTHorasDias.setFilterStyle("display: none; visibility: hidden;");
            nTMinutosHoras = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTMinutosHoras");
            nTMinutosHoras.setFilterStyle("display: none; visibility: hidden;");
            nTTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesTercero:nTTipo");
            nTTipo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNovedadesTercero");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }
        listaNovedadesBorrar.clear();
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
        listaNovedades = null;
        resultado = 0;
        guardado = true;
        permitirIndex = true;
    }

    public void todasNovedades() {
        listaNovedades.clear();
        listaNovedades = administrarNovedadesTerceros.todasNovedadesTercero(seleccionMostrar.getSecuencia());
        RequestContext context = RequestContext.getCurrentInstance();
        todas = true;
        actuales = false;
        context.update("form:datosNovedadesTercero");
        context.update("form:TODAS");
        context.update("form:ACTUALES");
    }

    public void actualesNovedades() {
        listaNovedades.clear();
        listaNovedades = administrarNovedadesTerceros.novedadesTercero(seleccionMostrar.getSecuencia());
        RequestContext context = RequestContext.getCurrentInstance();
        todas = false;
        actuales = true;
        context.update("form:datosNovedadesTercero");
        context.update("form:TODAS");
        context.update("form:ACTUALES");
    }

    //GETTER & SETTER
    public List<Terceros> getListaTercerosNovedad() {
        if (listaTercerosNovedad == null) {
            listaTercerosNovedad = administrarNovedadesTerceros.Terceros();
            seleccionMostrar = listaTercerosNovedad.get(0);
            System.out.println(seleccionMostrar.getSecuencia());
        }
        return listaTercerosNovedad;
    }

    public void setListaTercerosNovedad(List<Terceros> listaTercerosNovedad) {
        this.listaTercerosNovedad = listaTercerosNovedad;
    }

    public List<Periodicidades> getListaPeriodicidades() {
        if (listaPeriodicidades == null) {
            listaPeriodicidades = administrarNovedadesTerceros.lovPeriodicidades();
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

    public List<Novedades> getListaNovedades() {
        if (listaNovedades == null) {
            listaNovedades = administrarNovedadesTerceros.novedadesTercero(seleccionMostrar.getSecuencia());
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

    public Terceros getSeleccionMostrar() {
        return seleccionMostrar;
    }

    public void setSeleccionMostrar(Terceros seleccionMostrar) {
        this.seleccionMostrar = seleccionMostrar;
    }

    public List<Terceros> getFiltradosListaTercerosNovedad() {
        return filtradosListaTercerosNovedad;
    }

    public void setFiltradosListaTercerosNovedad(List<Terceros> filtradosListaTercerosNovedad) {
        this.filtradosListaTercerosNovedad = filtradosListaTercerosNovedad;
    }

    public List<Terceros> getListaTerceros() {
        if (listaTerceros == null) {
            listaTerceros = administrarNovedadesTerceros.lovTerceros();
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

    public List<Formulas> getListaFormulas() {
        if (listaFormulas == null) {
            listaFormulas = administrarNovedadesTerceros.lovFormulas();
        }
        return listaFormulas;
    }

    public void setListaFormulas(List<Formulas> listaFormulas) {

        this.listaFormulas = listaFormulas;
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

    public List<Conceptos> getListaConceptos() {
        if (listaConceptos == null) {
            listaConceptos = administrarNovedadesTerceros.lovConceptos();
        }
        return listaConceptos;
    }

    public void setListaConceptos(List<Conceptos> listaConceptos) {
        this.listaConceptos = listaConceptos;
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

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public int getResultado() {
        if (!listaNovedadesBorrar.isEmpty()) {
            for (int i = 0; i < listaNovedadesBorrar.size(); i++) {
                resultado = administrarNovedadesTerceros.solucionesFormulas(listaNovedadesBorrar.get(i).getSecuencia());
            }
        }
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
//Terminal y Usuario{

    public String getAlias() {
        alias = administrarNovedadesTerceros.alias();
        System.out.println("Alias: " + alias);
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Usuarios getUsuarioBD() {
        getAlias();
        usuarioBD = administrarNovedadesTerceros.usuarioBD(alias);
        return usuarioBD;

    }

    public void setUsuarioBD(Usuarios usuarioBD) {
        this.usuarioBD = usuarioBD;
    }
//}
    public Novedades getNuevaNovedad() {
        return nuevaNovedad;
    }

    public void setNuevaNovedad(Novedades nuevaNovedad) {
        this.nuevaNovedad = nuevaNovedad;
    }

    public boolean isTodas() {
        return todas;
    }

    public boolean isActuales() {
        return actuales;
    }

    public Novedades getEditarNovedades() {
        return editarNovedades;
    }

    public void setEditarNovedades(Novedades editarNovedades) {
        this.editarNovedades = editarNovedades;
    }

    public Novedades getDuplicarNovedad() {
        return duplicarNovedad;
    }

    public void setDuplicarNovedad(Novedades duplicarNovedad) {
        this.duplicarNovedad = duplicarNovedad;
    }

    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesTerceros.lovEmpleados();
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
}

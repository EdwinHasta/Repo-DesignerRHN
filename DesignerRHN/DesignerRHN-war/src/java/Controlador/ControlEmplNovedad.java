/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Novedades;
import Entidades.Periodicidades;
import Entidades.Terceros;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplNovedadInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
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
public class ControlEmplNovedad implements Serializable {

    @EJB
    AdministrarEmplNovedadInterface administrarEmplNovedad;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Cargos
    private List<Novedades> listNovedadesEmpleado;
    private List<Novedades> filtrarListNovedadesEmpleado;
    private Novedades novedadSeleccionada;
    private Empleados empleado;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column novedadCodigoConcepto, novedadDescripcionConcepto, novedadFechaInicial, novedadFechaFinal, novedadValor, novedadSaldo, novedadCodigoPeriodicidad, novedadDescripcionPeriodicidad, novedadTercero, novedadObservacion, novedadFechaReporte;
    //Otros
    private boolean aceptar;
    private int index;
    private boolean guardado, guardarOk;
    //editar celda
    private Novedades editarNovedad;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Novedades actualNovedad;
    public String altoTabla;
    public String infoRegistro;

    public ControlEmplNovedad() {
        actualNovedad = new Novedades();
        backUpSecRegistro = null;
        listNovedadesEmpleado = null;
        aceptar = true;
        editarNovedad = new Novedades();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        secRegistro = null;
        altoTabla = "292";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEmplNovedad.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    //EMPLEADO DE LA VIGENCIA
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(BigInteger empl) {
        listNovedadesEmpleado = null;
        empleado = administrarEmplNovedad.actualEmpleado(empl);
        getListNovedadesEmpleado();
        //INICIALIZAR BOTONES NAVEGACION
        if (listNovedadesEmpleado != null && !listNovedadesEmpleado.isEmpty()) {
            if (listNovedadesEmpleado.size() == 1) {
                //INFORMACION REGISTRO
                novedadSeleccionada = listNovedadesEmpleado.get(0);
                //infoRegistro = "Cantidad de registros: 1";
                modificarInfoRegistro(1);
            } else if (listNovedadesEmpleado.size() > 1) {
                //INFORMACION REGISTRO
                novedadSeleccionada = listNovedadesEmpleado.get(0);
                //infoRegistro = "Cantidad de registros: " + listNovedadesEmpleado.size();
                modificarInfoRegistro(listNovedadesEmpleado.size());
            }
        } else {
            // infoRegistro = "Cantidad de registros: 0";
            modificarInfoRegistro(0);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");


    }

    public void refrescar() {
        cerrarFiltrado();
        listNovedadesEmpleado = null;
        novedadSeleccionada = null;
        guardado = true;
        //permitirIndex = true;
        getListNovedadesEmpleado();
        if (listNovedadesEmpleado != null && !listNovedadesEmpleado.isEmpty()) {
            modificarInfoRegistro(listNovedadesEmpleado.size());
        } else {
            modificarInfoRegistro(0);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesEmpleado");
        context.update("form:informacionRegistro");
    }

    public void sencillo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(indice, columna);
    }

    public void modificarNovedades(int indice) {
        if (tipoLista == 0) {
            listNovedadesEmpleado.set(indice, actualNovedad);
            if (guardado == true) {
                guardado = false;
            }
        }
        if (tipoLista == 1) {
            int posi = listNovedadesEmpleado.indexOf(filtrarListNovedadesEmpleado.get(indice));
            listNovedadesEmpleado.set(posi, actualNovedad);
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesEmpleado");
        index = -1;
        secRegistro = null;
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla Sets
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        index = indice;
        cualCelda = celda;
        secRegistro = listNovedadesEmpleado.get(index).getSecuencia();
        actualNovedad = listNovedadesEmpleado.get(index);
    }
    //GUARDAR

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                editarNovedad = listNovedadesEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarNovedad = filtrarListNovedadesEmpleado.get(index);
            }

            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigoConceptoD");
                context.execute("editarCodigoConceptoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarDescripcionConceptoD");
                context.execute("editarDescripcionConceptoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarFechaInicial");
                context.execute("editarFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarFechaFinal");
                context.execute("editarFechaFinal.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarValorD");
                context.execute("editarValorD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarSaldoD");
                context.execute("editarSaldoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarCodigoPeriodicidadD");
                context.execute("editarCodigoPeriodicidadD.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarNombrePeriodicidadD");
                context.execute("editarNombrePeriodicidadD.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarTerceroD");
                context.execute("editarTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarObservacionD");
                context.execute("editarObservacionD.show()");
                cualCelda = -1;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editarFechaIngreso");
                context.execute("editarFechaIngreso.show()");
                cualCelda = -1;
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
        index = -1;
        secRegistro = null;
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            altoTabla = "268";
            novedadCodigoConcepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadCodigoConcepto");
            novedadCodigoConcepto.setFilterStyle("width: 60px");
            novedadDescripcionConcepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadDescripcionConcepto");
            novedadDescripcionConcepto.setFilterStyle("width: 60px");
            novedadValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadValor");
            novedadValor.setFilterStyle("width: 60px");
            novedadFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadFechaInicial");
            novedadFechaInicial.setFilterStyle("width: 60px");
            novedadFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadFechaFinal");
            novedadFechaFinal.setFilterStyle("width: 60px");
            novedadSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadSaldo");
            novedadSaldo.setFilterStyle("width: 60px");
            novedadCodigoPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadCodigoPeriodicidad");
            novedadCodigoPeriodicidad.setFilterStyle("width: 60px");
            novedadDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadDescripcionPeriodicidad");
            novedadDescripcionPeriodicidad.setFilterStyle("width: 60px");
            novedadTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadTercero");
            novedadTercero.setFilterStyle("width: 60px");
            novedadObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadObservacion");
            novedadObservacion.setFilterStyle("width: 60px");
            novedadFechaReporte = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadFechaReporte");
            novedadFechaReporte.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            cerrarFiltrado();
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            cerrarFiltrado();
        }
        listNovedadesEmpleado = null;
        actualNovedad = null;
        index = -1;
        secRegistro = null;
        guardado = true;
        empleado = null;
    }

    public void cerrarFiltrado() {
        altoTabla = "292";
        novedadCodigoConcepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadCodigoConcepto");
        novedadCodigoConcepto.setFilterStyle("display: none; visibility: hidden;");
        novedadDescripcionConcepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadDescripcionConcepto");
        novedadDescripcionConcepto.setFilterStyle("display: none; visibility: hidden;");
        novedadValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadValor");
        novedadValor.setFilterStyle("display: none; visibility: hidden;");
        novedadFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadFechaInicial");
        novedadFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        novedadFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadFechaFinal");
        novedadFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        novedadSaldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadSaldo");
        novedadSaldo.setFilterStyle("display: none; visibility: hidden;");
        novedadCodigoPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadCodigoPeriodicidad");
        novedadCodigoPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
        novedadDescripcionPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadDescripcionPeriodicidad");
        novedadDescripcionPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
        novedadTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadTercero");
        novedadTercero.setFilterStyle("display: none; visibility: hidden;");
        novedadObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadObservacion");
        novedadObservacion.setFilterStyle("display: none; visibility: hidden;");
        novedadFechaReporte = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNovedadesEmpleado:novedadFechaReporte");
        novedadFechaReporte.setFilterStyle("display: none; visibility: hidden;");
        RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
        bandera = 0;
        filtrarListNovedadesEmpleado = null;
        tipoLista = 0;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NovedadesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NovedadesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        // infoRegistro = "Cantidad de Registros: " + filtrarListNovedadesEmpleado.size();
        modificarInfoRegistro(filtrarListNovedadesEmpleado.size());
        context.update("form:informacionRegistro");
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listNovedadesEmpleado != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "NOVEDADES");
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
//            } else {
//                context.execute("seleccionarRegistro.show()");
//            }
            } else {
                if (administrarRastros.verificarHistoricosTabla("NOVEDADES")) {
                    context.execute("confirmarRastroHistorico.show()");
                } else {
                    context.execute("errorRastroHistorico.show()");
                }

            }
            index = -1;
        }
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
        System.out.println("infoRegistro: " + infoRegistro);
    }

    public void verDetalle(Novedades novedad) {
        novedadSeleccionada = novedad;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "detallenovedad");
    }

    //GETTERS AND SETTERS
    public List<Novedades> getListNovedadesEmpleado() {
        try {
            if (listNovedadesEmpleado == null) {
                listNovedadesEmpleado = administrarEmplNovedad.listNovedadesEmpleado(empleado.getSecuencia());
            }
            if (!listNovedadesEmpleado.isEmpty()) {
                for (int i = 0; i < listNovedadesEmpleado.size(); i++) {
                    if (listNovedadesEmpleado.get(i).getConcepto() == null) {
                        listNovedadesEmpleado.get(i).setConcepto(new Conceptos());
                    }
                    if (listNovedadesEmpleado.get(i).getPeriodicidad() == null) {
                        listNovedadesEmpleado.get(i).setPeriodicidad(new Periodicidades());
                    }
                    if (listNovedadesEmpleado.get(i).getTercero() == null) {
                        listNovedadesEmpleado.get(i).setTercero(new Terceros());
                    }
                }
            }
            return listNovedadesEmpleado;
        } catch (Exception e) {
            System.out.println("Error...!! getListNovedadesEmpleado : " + e.toString());
            return null;
        }
    }

    public void setListNovedadesEmpleado(List<Novedades> listNovedadesEmpleado) {
        this.listNovedadesEmpleado = listNovedadesEmpleado;
    }

    /**
     * Get del empleado, en caso de existir lo retorna en caso contrario lo
     * obtiene y retorna
     *
     * @return empleado Empleado que esta usado en el momento
     */
    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public void setActualNovedad(Novedades actualNovedad) {
        this.actualNovedad = actualNovedad;
    }

    public List<Novedades> getFiltrarListNovedadesEmpleado() {
        return filtrarListNovedadesEmpleado;
    }

    public void setFiltrarListNovedadesEmpleado(List<Novedades> filtrarListNovedadesEmpleado) {
        this.filtrarListNovedadesEmpleado = filtrarListNovedadesEmpleado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Novedades getEditarNovedad() {
        return editarNovedad;
    }

    public void setEditarNovedad(Novedades editarNov) {
        this.editarNovedad = editarNov;
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

    public void setBackUpSecRegistro(BigInteger BackUpSecRegistro) {
        this.backUpSecRegistro = BackUpSecRegistro;
    }

    public Novedades getNovedadSeleccionada() {
        return novedadSeleccionada;
    }

    public void setNovedadSeleccionada(Novedades novedadSeleccionada) {
        this.novedadSeleccionada = novedadSeleccionada;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }
}

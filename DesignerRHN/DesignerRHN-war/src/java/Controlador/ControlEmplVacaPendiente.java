package Controlador;

import Entidades.Empleados;
import Entidades.VWVacaPendientesEmpleados;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVWVacaPendientesEmpleadosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlEmplVacaPendiente implements Serializable {

    @EJB
    AdministrarVWVacaPendientesEmpleadosInterface administrarVWVacaPendientesEmpleados;
    private List<VWVacaPendientesEmpleados> listVacaPendientes;
    private List<VWVacaPendientesEmpleados> filtrarListVacaPendientes;
    private VWVacaPendientesEmpleados vacaPendienteSeleccionada;
    private List<VWVacaPendientesEmpleados> listVacaDisfrutadas;
    private List<VWVacaPendientesEmpleados> filtrarListVacaDisfrutadas;
    private VWVacaPendientesEmpleados vacaDisfrutadaSeleccionada;
    private int tipoTabla, filtrarListaPendientes, filtrarListaDisfrutadas;
    private int banderaPendientes, banderaDisfrutadas;
    private int casillaPendiente, casillaDisfrutada;
    private int indexVPendientes, indexVDisfrutadas;
    //////
    private Empleados empleado;
    //
    private List<VWVacaPendientesEmpleados> listModificacionesTablaPendientes;
    private List<VWVacaPendientesEmpleados> listModificacionesTablaDisfrutadas;
    //////
    private List<VWVacaPendientesEmpleados> listCrearTablaPendientes;
    private List<VWVacaPendientesEmpleados> listCrearTablaDisfrutadas;
    //////
    private List<VWVacaPendientesEmpleados> listBorrarTablaPendientes;
    private List<VWVacaPendientesEmpleados> listBorrarTablaDisfrutadas;
    //
    private VWVacaPendientesEmpleados editarVacacion;
    private VWVacaPendientesEmpleados nuevaVacacion;
    private VWVacaPendientesEmpleados duplicarVacacion;
    //
    private String nombreTabla, nombreXML;
    //
    private BigInteger totalDiasPendientes;
    private BigInteger secuencia;
    //
    private BigDecimal diasProvisionados;
    //
    private Column vacacionesDP, vacacionesFechaInicial, vacacionesFechaFinal, vacacionesDPD, vacacionesFechaInicialD, vacacionesFechaFinalD;
    private Date fechaParametro;
    private Date fechaIniP, fechaFinP;
    private Date fechaIniD, fechaFinD;
    private String altoTabla1, altoTabla2;

    private boolean guardado;

    private Date fechaFinalContratacion;
    private int k;

    public ControlEmplVacaPendiente() {
        k = 0;
        guardado = true;
        listVacaDisfrutadas = null;
        listVacaPendientes = null;
        tipoTabla = 0;
        banderaPendientes = 0;
        banderaDisfrutadas = 0;
        filtrarListaPendientes = 0;
        filtrarListaDisfrutadas = 0;
        casillaPendiente = 0;
        casillaDisfrutada = 0;
        indexVPendientes = 0;
        indexVDisfrutadas = 0;
        editarVacacion = new VWVacaPendientesEmpleados();
        nuevaVacacion = new VWVacaPendientesEmpleados();
        duplicarVacacion = new VWVacaPendientesEmpleados();
        listModificacionesTablaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
        listModificacionesTablaDisfrutadas = new ArrayList<VWVacaPendientesEmpleados>();
        listCrearTablaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
        listCrearTablaDisfrutadas = new ArrayList<VWVacaPendientesEmpleados>();
        listBorrarTablaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
        listBorrarTablaDisfrutadas = new ArrayList<VWVacaPendientesEmpleados>();
        nombreTabla = ":formExportarVS:datosVSEmpleadoExportar";
        nombreXML = "VacacionesPendientesXML";
        totalDiasPendientes = BigInteger.valueOf(0);
        diasProvisionados = BigDecimal.valueOf(0);
        altoTabla1 = "115";
        altoTabla2 = "115";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVWVacaPendientesEmpleados.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        this.secuencia = sec;
        empleado = administrarVWVacaPendientesEmpleados.obtenerEmpleado(secuencia);
        if (empleado != null) {
            fechaFinalContratacion = administrarVWVacaPendientesEmpleados.obtenerFechaFinalContratacionEmpleado(empleado.getSecuencia());
        }
    }

    public void modificacionesTablas() {
        if (tipoTabla == 1) {
            if (filtrarListaPendientes == 0) {
                VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(indexVPendientes);
                listModificacionesTablaPendientes.add(vacaPendiente);
            } else {
                int posicion = listVacaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(posicion);
                listModificacionesTablaPendientes.add(vacaPendiente);
            }
            totalDiasPendientes = BigInteger.valueOf(0);
            getTotalDiasPendientes();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVacacionesPEmpleado:totalDiasP");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

        }
        if (tipoTabla == 2) {
            if (filtrarListaDisfrutadas == 0) {
                VWVacaPendientesEmpleados vacaPendiente = listVacaDisfrutadas.get(indexVDisfrutadas);
                listModificacionesTablaDisfrutadas.add(vacaPendiente);
            } else {
                int posicion = listVacaDisfrutadas.indexOf(filtrarListVacaDisfrutadas.get(indexVDisfrutadas));
                VWVacaPendientesEmpleados vacaPendiente = listVacaDisfrutadas.get(posicion);
                listModificacionesTablaDisfrutadas.add(vacaPendiente);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    public boolean validarFechasRegistroPendientes(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VWVacaPendientesEmpleados auxiliar = null;
            if (filtrarListaPendientes == 0) {
                auxiliar = listVacaPendientes.get(indexVPendientes);
            }
            if (filtrarListaPendientes == 1) {
                auxiliar = filtrarListVacaPendientes.get(indexVPendientes);
            }
            if (auxiliar.getInicialcausacion().after(fechaParametro) && auxiliar.getInicialcausacion().before(auxiliar.getFinalcausacion())) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaVacacion.getInicialcausacion().after(fechaParametro) && nuevaVacacion.getInicialcausacion().before(nuevaVacacion.getFinalcausacion())) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarVacacion.getInicialcausacion().after(fechaParametro) && duplicarVacacion.getInicialcausacion().before(duplicarVacacion.getFinalcausacion())) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroDisfrutadas() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        VWVacaPendientesEmpleados auxiliar = null;
        if (filtrarListaDisfrutadas == 0) {
            auxiliar = listVacaDisfrutadas.get(indexVDisfrutadas);
        }
        if (filtrarListaDisfrutadas == 1) {
            auxiliar = filtrarListVacaDisfrutadas.get(indexVDisfrutadas);
        }
        if (auxiliar.getInicialcausacion().after(fechaParametro) && auxiliar.getInicialcausacion().before(auxiliar.getFinalcausacion())) {
            retorno = true;
        } else {
            retorno = false;
        }
        return retorno;
    }

    public void modificarFechasDisfrutadas(int t, int i, int c) {
        VWVacaPendientesEmpleados auxiliar = null;
        if (filtrarListaDisfrutadas == 0) {
            auxiliar = listVacaDisfrutadas.get(i);
        }
        if (filtrarListaDisfrutadas == 1) {
            auxiliar = filtrarListVacaDisfrutadas.get(i);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (auxiliar.getInicialcausacion() != null && auxiliar.getFinalcausacion() != null) {
            boolean retorno = false;
            indexVDisfrutadas = i;
            retorno = validarFechasRegistroDisfrutadas();
            if (retorno == true) {
                if (validarFechasConFechaContratacion(0) == true) {
                    posicionTabla(t, i, c);
                    modificacionesTablas();
                } else {
                    if (filtrarListaDisfrutadas == 0) {
                        listVacaDisfrutadas.get(i).setInicialcausacion(fechaFinD);
                        listVacaDisfrutadas.get(i).setFinalcausacion(fechaIniD);
                    } else {
                        filtrarListVacaDisfrutadas.get(i).setInicialcausacion(fechaFinD);
                        filtrarListVacaDisfrutadas.get(i).setFinalcausacion(fechaIniD);
                    }
                    context.update("form:datosVacacionesDEmpleado");
                    context.execute("errorFechaContratacion.show()");
                }
            } else {
                if (filtrarListaDisfrutadas == 0) {
                    listVacaDisfrutadas.get(i).setInicialcausacion(fechaFinD);
                    listVacaDisfrutadas.get(i).setFinalcausacion(fechaIniD);
                } else {
                    filtrarListVacaDisfrutadas.get(i).setInicialcausacion(fechaFinD);
                    filtrarListVacaDisfrutadas.get(i).setFinalcausacion(fechaIniD);
                }

                context.update("form:datosVacacionesDEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (filtrarListaDisfrutadas == 0) {
                listVacaDisfrutadas.get(i).setInicialcausacion(fechaFinD);
                listVacaDisfrutadas.get(i).setFinalcausacion(fechaIniD);
            } else {
                filtrarListVacaDisfrutadas.get(i).setInicialcausacion(fechaFinD);
                filtrarListVacaDisfrutadas.get(i).setFinalcausacion(fechaIniD);
            }
            context.update("form:datosVacacionesDEmpleado");
            context.execute("errorRegNew.show()");
        }
    }

    public void modificarFechasPendientes(int t, int i, int c) {
        VWVacaPendientesEmpleados auxiliar = null;
        if (filtrarListaDisfrutadas == 0) {
            auxiliar = listVacaPendientes.get(i);
        } else {
            auxiliar = filtrarListVacaPendientes.get(i);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (auxiliar.getInicialcausacion() != null && auxiliar.getFinalcausacion() != null) {
            boolean retorno = false;
            indexVPendientes = i;
            retorno = validarFechasRegistroPendientes(0);
            if (retorno == true) {
                if (validarFechasConFechaContratacion(0) == true) {
                    posicionTabla(t, i, c);
                    modificacionesTablas();
                } else {
                    if (filtrarListaPendientes == 0) {
                        listVacaPendientes.get(i).setInicialcausacion(fechaFinP);
                        listVacaPendientes.get(i).setFinalcausacion(fechaIniP);
                    } else {
                        filtrarListVacaPendientes.get(i).setInicialcausacion(fechaFinP);
                        filtrarListVacaPendientes.get(i).setFinalcausacion(fechaIniP);
                    }
                    context.update("form:datosVacacionesPEmpleado");
                    context.execute("errorFechaContratacion.show()");
                }
            } else {
                if (filtrarListaPendientes == 0) {
                    listVacaPendientes.get(i).setInicialcausacion(fechaFinP);
                    listVacaPendientes.get(i).setFinalcausacion(fechaIniP);
                } else {
                    filtrarListVacaPendientes.get(i).setInicialcausacion(fechaFinP);
                    filtrarListVacaPendientes.get(i).setFinalcausacion(fechaIniP);
                }

                context.update("form:datosVacacionesPEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (filtrarListaPendientes == 0) {
                listVacaPendientes.get(i).setInicialcausacion(fechaFinP);
                listVacaPendientes.get(i).setFinalcausacion(fechaIniP);
            } else {
                filtrarListVacaPendientes.get(i).setInicialcausacion(fechaFinP);
                filtrarListVacaPendientes.get(i).setFinalcausacion(fechaIniP);
            }
            context.update("form:datosVacacionesPEmpleado");
            context.execute("errorRegNew.show()");
        }
    }

    public void limpiarNuevoRegistro() {
        nuevaVacacion = new VWVacaPendientesEmpleados();
    }

    public boolean validarFechasConFechaContratacion(int i) {
        boolean retorno = false;
        if (i == 0) {
            VWVacaPendientesEmpleados auxiliar = null;
            if (tipoTabla == 1) {
                if (filtrarListaPendientes == 0) {
                    auxiliar = listVacaPendientes.get(indexVPendientes);
                } else {
                    auxiliar = filtrarListVacaPendientes.get(indexVPendientes);
                }
            } else {
                if (filtrarListaDisfrutadas == 0) {
                    auxiliar = listVacaDisfrutadas.get(indexVDisfrutadas);
                } else {
                    auxiliar = filtrarListVacaDisfrutadas.get(indexVDisfrutadas);
                }
            }
            if (auxiliar.getInicialcausacion().after(fechaFinalContratacion) && auxiliar.getFinalcausacion().after(fechaFinalContratacion)) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaVacacion.getInicialcausacion().after(fechaFinalContratacion) && nuevaVacacion.getFinalcausacion().after(fechaFinalContratacion)) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVacacion.getInicialcausacion().after(fechaFinalContratacion) && duplicarVacacion.getFinalcausacion().after(fechaFinalContratacion)) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void validarNuevoRegistroPendientes() {
        if (nuevaVacacion.getInicialcausacion() != null && nuevaVacacion.getFinalcausacion() != null && nuevaVacacion.getEstado() != null && nuevaVacacion.getDiaspendientes() != null) {
            if (validarFechasRegistroPendientes(1) == true) {
                if (validarFechasConFechaContratacion(1) == true) {
                    if (filtrarListaPendientes == 1) {
                        FacesContext c = FacesContext.getCurrentInstance();
                        vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
                        vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
                        vacacionesFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
                        vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vacacionesFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
                        vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        altoTabla1 = "115";
                        RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
                        banderaPendientes = 0;
                        filtrarListVacaPendientes = null;
                        filtrarListaPendientes = 0;
                    }
                    int h = 0;
                    h = h + 1;
                    BigInteger k;
                    k = BigInteger.valueOf(h);
                    nuevaVacacion.setSecuencia(k);
                    nuevaVacacion.setEmpleado(empleado.getSecuencia());
                    if (listVacaPendientes == null) {
                        listVacaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
                    }
                    listVacaPendientes.add(nuevaVacacion);
                    listCrearTablaPendientes.add(nuevaVacacion);
                    nuevaVacacion = new VWVacaPendientesEmpleados();
                    indexVPendientes = -1;
                    RequestContext context = RequestContext.getCurrentInstance();
                    getTotalDiasPendientes();
                    context.update("form:totalDiasP");
                    context.update("form:datosVacacionesPEmpleado");
                    context.execute("NuevoRegistroVP.hide()");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorFechaContratacion.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }

    public void duplicarRegistroTabla() {
        if (indexVPendientes >= 0) {
            duplicarVacacion = new VWVacaPendientesEmpleados();
            if (filtrarListaPendientes == 1) {
                int pos = listVacaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                duplicarVacacion.setEmpleado(empleado.getSecuencia());
                duplicarVacacion.setEstado(listVacaPendientes.get(pos).getEstado());
                duplicarVacacion.setDiaspendientes(listVacaPendientes.get(pos).getDiaspendientes());
                duplicarVacacion.setInicialcausacion(listVacaPendientes.get(pos).getInicialcausacion());
                duplicarVacacion.setFinalcausacion(listVacaPendientes.get(pos).getFinalcausacion());
            } else {
                duplicarVacacion.setEmpleado(empleado.getSecuencia());
                duplicarVacacion.setEstado(listVacaPendientes.get(indexVPendientes).getEstado());
                duplicarVacacion.setDiaspendientes(listVacaPendientes.get(indexVPendientes).getDiaspendientes());
                duplicarVacacion.setInicialcausacion(listVacaPendientes.get(indexVPendientes).getInicialcausacion());
                duplicarVacacion.setFinalcausacion(listVacaPendientes.get(indexVPendientes).getFinalcausacion());
            }
            //Dialogo Duplicar VacaPendiente
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVP");
            context.execute("DuplicarRegistroVP.show()");
            indexVPendientes = -1;
        }

    }

    public void validarDuplicadoVacaPendientes() {
        if (duplicarVacacion.getInicialcausacion() != null && duplicarVacacion.getFinalcausacion() != null && duplicarVacacion.getEstado() != null && duplicarVacacion.getDiaspendientes() != null) {
            if (validarFechasRegistroPendientes(2) == true) {
                if (validarFechasConFechaContratacion(2) == true) {
                    k++;
                    BigInteger l = BigInteger.valueOf(k);
                    duplicarVacacion.setSecuencia(l);
                    if (filtrarListaPendientes == 1) {
                        FacesContext c = FacesContext.getCurrentInstance();
                        vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
                        vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
                        vacacionesFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
                        vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vacacionesFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
                        vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        altoTabla1 = "115";
                        RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
                        banderaPendientes = 0;
                        filtrarListVacaPendientes = null;
                        filtrarListaPendientes = 0;
                    }
                    listCrearTablaPendientes.add(duplicarVacacion);
                    listVacaPendientes.add(duplicarVacacion);
                    duplicarVacacion = new VWVacaPendientesEmpleados();
                    RequestContext context = RequestContext.getCurrentInstance();
                    getTotalDiasPendientes();
                    context.update("form:totalDiasP");
                    context.update("form:datosVacacionesPEmpleado");
                    context.execute("DuplicarRegistroVP.hide()");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorFechaContratacion.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }

    public void limpiarDuplicadoVacaPendiente() {
        duplicarVacacion = new VWVacaPendientesEmpleados();
    }

    public void eliminarRegistroTabla() {
        if (tipoTabla == 1) {
            if (filtrarListaPendientes == 0) {
                if (!listCrearTablaPendientes.isEmpty() && listCrearTablaPendientes.contains(listVacaPendientes.get(indexVPendientes))) {
                    int crearIndex = listCrearTablaPendientes.indexOf(listVacaPendientes.get(indexVPendientes));
                    listCrearTablaPendientes.remove(crearIndex);
                } else if (!listModificacionesTablaPendientes.isEmpty() && listModificacionesTablaPendientes.contains(listVacaPendientes.get(indexVPendientes))) {
                    int modIndex = listModificacionesTablaPendientes.indexOf(listVacaPendientes.get(indexVPendientes));
                    listModificacionesTablaPendientes.remove(modIndex);
                    listBorrarTablaPendientes.add(listVacaPendientes.get(indexVPendientes));
                } else {
                    VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(indexVPendientes);
                    listBorrarTablaPendientes.add(vacaPendiente);
                    listVacaPendientes.remove(indexVPendientes);
                }
            } else {
                if (!listCrearTablaPendientes.isEmpty() && listCrearTablaPendientes.contains(filtrarListVacaPendientes.get(indexVPendientes))) {
                    int crearIndex = listCrearTablaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                    listCrearTablaPendientes.remove(crearIndex);
                } else if (!listModificacionesTablaPendientes.isEmpty() && listModificacionesTablaPendientes.contains(filtrarListVacaPendientes.get(indexVPendientes))) {
                    int modIndex = listModificacionesTablaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                    listModificacionesTablaPendientes.remove(modIndex);
                    listBorrarTablaPendientes.add(filtrarListVacaPendientes.get(indexVPendientes));
                } else {
                    int posicion = listVacaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                    VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(posicion);
                    listBorrarTablaPendientes.add(vacaPendiente);
                    listVacaPendientes.remove(posicion);
                    filtrarListVacaPendientes.remove(indexVPendientes);
                }
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVacacionesPEmpleado");
            getTotalDiasPendientes();
            context.update("form:totalDiasP");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }

    }

    public void cancelarModificaciones() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (filtrarListaDisfrutadas == 1) {
            vacacionesDPD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 0;
            filtrarListVacaDisfrutadas = null;
            filtrarListaDisfrutadas = 0;
        }
        if (filtrarListaPendientes == 1) {
            vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            altoTabla1 = "115";
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
        listBorrarTablaDisfrutadas.clear();
        listBorrarTablaPendientes.clear();
        listCrearTablaDisfrutadas.clear();
        listCrearTablaPendientes.clear();
        listModificacionesTablaDisfrutadas.clear();
        listModificacionesTablaPendientes.clear();
        listVacaDisfrutadas = null;
        listVacaPendientes = null;
        casillaDisfrutada = -1;
        casillaPendiente = -1;
        indexVDisfrutadas = -1;
        indexVPendientes = -1;
        tipoTabla = -1;
        totalDiasPendientes = BigInteger.valueOf(0);
        RequestContext context = RequestContext.getCurrentInstance();
        guardado = true;
        context.update("form:ACEPTAR");
        getTotalDiasPendientes();
        context.update("form:totalDiasP");
        context.update("form:datosVacacionesPEmpleado");
        context.update("form:datosVacacionesDEmpleado");
        context.update("form:datosVacacionesPEmpleado:totalDiasP");

    }

    public void activarCtrlF11() {
        if (indexVPendientes >= 0) {
            filtradoVacacionesPendientes();
        }
        if (indexVDisfrutadas >= 0) {
            filtradoVacacionesDisfrutadas();
        }
    }

    public void filtradoVacacionesPendientes() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaPendientes == 0) {
            vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("width: 100px");
            vacacionesFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("width: 100px");
            vacacionesFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("width: 100px");
            altoTabla1 = "91";
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 1;
        } else if (banderaPendientes == 1) {
            vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            altoTabla1 = "115";
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
    }

    public void filtradoVacacionesDisfrutadas() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaDisfrutadas == 0) {
            vacacionesDPD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("width: 100px");
            vacacionesFechaInicialD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("width: 100px");
            vacacionesFechaFinalD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("width: 100px");
            altoTabla2 = "91";
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 1;
        } else if (banderaDisfrutadas == 1) {
            vacacionesDPD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 0;
            filtrarListVacaDisfrutadas = null;
            filtrarListaDisfrutadas = 0;
        }
    }

    public void posicionTabla(int tabla, int index, int casilla) {
        if (tabla == 1) {
            tipoTabla = 1;
            indexVPendientes = index;
            casillaPendiente = casilla;
            indexVDisfrutadas = -1;
            casillaDisfrutada = -1;
            if (filtrarListaPendientes == 0) {
                fechaIniP = listVacaPendientes.get(indexVPendientes).getInicialcausacion();
                fechaFinP = listVacaPendientes.get(indexVPendientes).getFinalcausacion();
            }
            if (filtrarListaPendientes == 1) {
                fechaIniP = filtrarListVacaPendientes.get(indexVPendientes).getInicialcausacion();
                fechaFinP = filtrarListVacaPendientes.get(indexVPendientes).getFinalcausacion();
            }
            if (banderaDisfrutadas == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                vacacionesDPD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
                vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaInicialD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
                vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaFinalD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
                vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
                altoTabla2 = "115";
                RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
                banderaDisfrutadas = 0;
                filtrarListVacaDisfrutadas = null;
                filtrarListaDisfrutadas = 0;
            }
        }
        if (tabla == 2) {
            tipoTabla = 2;
            indexVDisfrutadas = index;
            casillaDisfrutada = casilla;
            casillaPendiente = -1;
            indexVPendientes = -1;
            if (banderaPendientes == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
                vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
                vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
                vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                altoTabla1 = "115";
                RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
                banderaPendientes = 0;
                filtrarListVacaPendientes = null;
                filtrarListaPendientes = 0;
            }
        }
    }

    public void guardarGeneral() {
        FacesContext c = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        if (banderaPendientes == 1) {
            vacacionesDP = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            altoTabla1 = "115";
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
        if (banderaDisfrutadas == 1) {
            vacacionesDPD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) c.getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            context.update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 0;
            filtrarListVacaDisfrutadas = null;
            filtrarListaDisfrutadas = 0;
        }
        guardarCambiosVPendientes();
        guardarCambiosVDisfrutadas();
        guardado = true;
        context.update("form:ACEPTAR");
        FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.update("form:growl");
    }

    public void guardarCambiosVPendientes() {
        if (!listBorrarTablaPendientes.isEmpty()) {
            for (int i = 0; i < listBorrarTablaPendientes.size(); i++) {
                administrarVWVacaPendientesEmpleados.borrarVacaPendiente(listBorrarTablaPendientes.get(i));
            }
        }
        if (!listCrearTablaPendientes.isEmpty()) {
            for (int i = 0; i < listCrearTablaPendientes.size(); i++) {
                administrarVWVacaPendientesEmpleados.crearVacaPendiente(listCrearTablaPendientes.get(i));
            }
        }
        if (!listModificacionesTablaPendientes.isEmpty()) {
            for (int i = 0; i < listModificacionesTablaPendientes.size(); i++) {
                administrarVWVacaPendientesEmpleados.editarVacaPendiente(listModificacionesTablaPendientes.get(i));
            }
        }
    }

    public void guardarCambiosVDisfrutadas() {
        if (!listBorrarTablaDisfrutadas.isEmpty()) {
            for (int i = 0; i < listBorrarTablaDisfrutadas.size(); i++) {
                administrarVWVacaPendientesEmpleados.borrarVacaPendiente(listBorrarTablaDisfrutadas.get(i));
            }
        }
        if (!listCrearTablaDisfrutadas.isEmpty()) {
            for (int i = 0; i < listCrearTablaDisfrutadas.size(); i++) {
                administrarVWVacaPendientesEmpleados.crearVacaPendiente(listCrearTablaDisfrutadas.get(i));
            }
        }
        if (!listModificacionesTablaDisfrutadas.isEmpty()) {
            for (int i = 0; i < listModificacionesTablaDisfrutadas.size(); i++) {
                administrarVWVacaPendientesEmpleados.editarVacaPendiente(listModificacionesTablaDisfrutadas.get(i));
            }
        }
    }

    public void salir() {
        cancelarModificaciones();
        listBorrarTablaDisfrutadas.clear();
        listBorrarTablaPendientes.clear();
        listCrearTablaDisfrutadas.clear();
        listCrearTablaPendientes.clear();
        listModificacionesTablaDisfrutadas.clear();
        listModificacionesTablaPendientes.clear();
        listVacaDisfrutadas = null;
        listVacaPendientes = null;
        casillaDisfrutada = -1;
        casillaPendiente = -1;
        indexVDisfrutadas = -1;
        indexVPendientes = -1;
        tipoTabla = -1;
        totalDiasPendientes = BigInteger.valueOf(0);
        diasProvisionados = BigDecimal.valueOf(0);
    }

    public void editarCelda() {
        if (tipoTabla == 1) {
            editarVacacion = listVacaPendientes.get(indexVPendientes);
            RequestContext context = RequestContext.getCurrentInstance();
            if (casillaPendiente == 1) {
                //Dialogo Dias Pendientes
                context.update("formularioDialogos:editarDiasPendientesP");
                context.execute("editarDiasPendientesP.show()");
            }
            if (casillaPendiente == 2) {
                //Dialogo Fecha Inicial
                context.update("formularioDialogos:editarFechaInicialP");
                context.execute("editarFechaInicialP.show()");
            }
            if (casillaPendiente == 3) {
                //Dialogo Fecha Final
                context.update("formularioDialogos:editarFechaFinalP");
                context.execute("editarFechaFinalP.show()");
            }
        }
        if (tipoTabla == 2) {
            editarVacacion = listVacaDisfrutadas.get(indexVDisfrutadas);
            RequestContext context = RequestContext.getCurrentInstance();
            if (casillaDisfrutada == 1) {
                //Dialogo Dias Pendientes
                context.update("formularioDialogos:editarDiasPendientesD");
                context.execute("editarDiasPendientesD.show()");
            }
            if (casillaDisfrutada == 2) {
                //Dialogo Fecha Inicial
                context.update("formularioDialogos:editarFechaInicialD");
                context.execute("editarFechaInicialD.show()");
            }
            if (casillaDisfrutada == 3) {
                //Dialogo Fecha Final
                context.update("formularioDialogos:editarFechaFinalD");
                context.execute("editarFechaFinalD.show()");
            }
        }
    }

    public String exportXML() {
        if (indexVPendientes >= 0) {
            nombreTabla = ":formExportarVP:datosVPEmpleadoExportar";
            nombreXML = "VacacionesPendientesXML";
        }
        if (indexVDisfrutadas >= 0) {
            nombreTabla = ":formExportarVD:datosVDEmpleadoExportar";
            nombreXML = "VacacionesDisfrutadasXML";
        }
        return nombreTabla;
    }

    public void validarExportPDF() throws IOException {
        if (indexVPendientes >= 0) {
            exportPDFVP();
        }
        if (indexVDisfrutadas >= 0) {
            exportPDFVD();
        }
    }

    public void exportPDFVP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VacacionesPendientesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVPendientes = -1;
    }

    public void exportPDFVD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVD:datosVDEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VacacionesDisfrutadasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVDisfrutadas = -1;
    }

    public void verificarExportXLS() throws IOException {
        if (indexVDisfrutadas >= 0) {
            exportXLSVP();
        }
        if (indexVPendientes >= 0) {
            exportXLSVD();
        }
    }

    public void exportXLSVP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VacacionesPendientesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVPendientes = -1;
    }

    public void exportXLSVD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVD:datosVDEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VacacionesDisfrutadasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVDisfrutadas = -1;
    }

    public void eventoFiltrar() {
        if (indexVDisfrutadas >= 0) {
            if (filtrarListaDisfrutadas == 0) {
                filtrarListaDisfrutadas = 1;
            }
        }
        if (indexVPendientes >= 0) {
            if (filtrarListaPendientes == 0) {
                filtrarListaPendientes = 1;
            }
        }
    }

    public List<VWVacaPendientesEmpleados> getFiltrarListVacaPendientes() {
        return filtrarListVacaPendientes;
    }

    public void setFiltrarListVacaPendientes(List<VWVacaPendientesEmpleados> filtrarListVacaPendientes) {
        this.filtrarListVacaPendientes = filtrarListVacaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getFiltrarListVacaDisfrutadas() {
        return filtrarListVacaDisfrutadas;
    }

    public void setFiltrarListVacaDisfrutadas(List<VWVacaPendientesEmpleados> filtrarListVacaDisfrutadas) {
        this.filtrarListVacaDisfrutadas = filtrarListVacaDisfrutadas;
    }

    public VWVacaPendientesEmpleados getEditarVacacion() {
        return editarVacacion;
    }

    public void setEditarVacacion(VWVacaPendientesEmpleados editarVacaPendiente) {
        this.editarVacacion = editarVacaPendiente;
    }

    public List<VWVacaPendientesEmpleados> getListModificacionesTablaPendientes() {
        return listModificacionesTablaPendientes;
    }

    public void setListModificacionesTablaPendientes(List<VWVacaPendientesEmpleados> listModificacionesTablaPendientes) {
        this.listModificacionesTablaPendientes = listModificacionesTablaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListModificacionesTablaDisfrutadas() {
        return listModificacionesTablaDisfrutadas;
    }

    public void setListModificacionesTablaDisfrutadas(List<VWVacaPendientesEmpleados> listModificacionesTablaDisfrutadas) {
        this.listModificacionesTablaDisfrutadas = listModificacionesTablaDisfrutadas;
    }

    public List<VWVacaPendientesEmpleados> getListCrearTablaPendientes() {
        return listCrearTablaPendientes;
    }

    public void setListCrearTablaPendientes(List<VWVacaPendientesEmpleados> listCrearTablaPendientes) {
        this.listCrearTablaPendientes = listCrearTablaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListCrearTablaDisfrutadas() {
        return listCrearTablaDisfrutadas;
    }

    public void setListCrearTablaDisfrutadas(List<VWVacaPendientesEmpleados> listCrearTablaDisfrutadas) {
        this.listCrearTablaDisfrutadas = listCrearTablaDisfrutadas;
    }

    public List<VWVacaPendientesEmpleados> getListBorrarTablaPendientes() {
        return listBorrarTablaPendientes;
    }

    public void setListBorrarTablaPendientes(List<VWVacaPendientesEmpleados> listBorrarTablaPendientes) {
        this.listBorrarTablaPendientes = listBorrarTablaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListBorrarTablaDisfrutadas() {
        return listBorrarTablaDisfrutadas;
    }

    public void setListBorrarTablaDisfrutadas(List<VWVacaPendientesEmpleados> listBorrarTablaDisfrutadas) {
        this.listBorrarTablaDisfrutadas = listBorrarTablaDisfrutadas;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public BigInteger getTotalDiasPendientes() {
        if (totalDiasPendientes.compareTo(BigInteger.valueOf(0)) == 0) {
            totalDiasPendientes = BigInteger.valueOf(0);
            if (listVacaPendientes != null) {
                for (int i = 0; i < listVacaPendientes.size(); i++) {
                    totalDiasPendientes = totalDiasPendientes.add(listVacaPendientes.get(i).getDiaspendientes());
                }
            }
        }
        return totalDiasPendientes;
    }

    public void setTotalDiasPendientes(BigInteger totalDiasPendientes) {
        this.totalDiasPendientes = totalDiasPendientes;
    }

    public Empleados getEmpleado() {
        empleado = administrarVWVacaPendientesEmpleados.obtenerEmpleado(secuencia);
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public List<VWVacaPendientesEmpleados> getListVacaPendientes() {
        try {
            if (empleado.getSecuencia() != null) {
                if (listVacaPendientes == null) {
                    listVacaPendientes = administrarVWVacaPendientesEmpleados.vacaPendientesPendientes(empleado);
                }
            }
            return listVacaPendientes;
        } catch (Exception e) {
            System.out.println("Error getListVacaPendientes : " + e.toString());
            return null;
        }
    }

    public void setListVacaPendientes(List<VWVacaPendientesEmpleados> listVacaPendientes) {
        this.listVacaPendientes = listVacaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListVacaDisfrutadas() {
        try {
            if (empleado != null) {
                if (listVacaDisfrutadas == null) {
                    listVacaDisfrutadas = administrarVWVacaPendientesEmpleados.vacaPendientesDisfrutadas(empleado);
                }
            }
            return listVacaDisfrutadas;
        } catch (Exception e) {
            System.out.println("Error getListVacaDisfrutadas : " + e.toString());
            return null;
        }
    }

    public void setListVacaDisfrutadas(List<VWVacaPendientesEmpleados> listVacaDisfrutadas) {
        this.listVacaDisfrutadas = listVacaDisfrutadas;
    }

    public VWVacaPendientesEmpleados getNuevaVacacion() {
        return nuevaVacacion;
    }

    public void setNuevaVacacion(VWVacaPendientesEmpleados nuevaVacacion) {
        this.nuevaVacacion = nuevaVacacion;
    }

    public VWVacaPendientesEmpleados getDuplicarVacacion() {
        return duplicarVacacion;
    }

    public void setDuplicarVacacion(VWVacaPendientesEmpleados duplicarVacacion) {
        this.duplicarVacacion = duplicarVacacion;
    }

    public BigDecimal getDiasProvisionados() {
        try {
            if (empleado != null) {
                diasProvisionados = administrarVWVacaPendientesEmpleados.diasProvisionadosEmpleado(empleado);
            }
            return diasProvisionados;
        } catch (Exception e) {
            System.out.println("Error getDiasProvisionados : " + e.toString());
            return null;
        }
    }

    public void setDiasProvisionados(BigDecimal diasProvisionados) {
        this.diasProvisionados = diasProvisionados;
    }

    public VWVacaPendientesEmpleados getVacaPendienteSeleccionada() {
        return vacaPendienteSeleccionada;
    }

    public void setVacaPendienteSeleccionada(VWVacaPendientesEmpleados vacaPendienteSeleccionada) {
        this.vacaPendienteSeleccionada = vacaPendienteSeleccionada;
    }

    public VWVacaPendientesEmpleados getVacaDisfrutadaSeleccionada() {
        return vacaDisfrutadaSeleccionada;
    }

    public void setVacaDisfrutadaSeleccionada(VWVacaPendientesEmpleados vacaDisfrutadaSeleccionada) {
        this.vacaDisfrutadaSeleccionada = vacaDisfrutadaSeleccionada;
    }

    public String getAltoTabla1() {
        return altoTabla1;
    }

    public String getAltoTabla2() {
        return altoTabla2;
    }

    public boolean isGuardado() {
        return guardado;
    }
}

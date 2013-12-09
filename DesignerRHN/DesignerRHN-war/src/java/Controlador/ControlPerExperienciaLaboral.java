/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvExperienciasLaborales;
import Entidades.MotivosRetiros;
import Entidades.SectoresEconomicos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarPerExperienciaLaboralInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
public class ControlPerExperienciaLaboral implements Serializable {

    @EJB
    AdministrarPerExperienciaLaboralInterface administrarPerExperienciaLaboral;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //HvExperienciasLaborales
    private List<HvExperienciasLaborales> listExperienciaLaboralEmpl;
    private List<HvExperienciasLaborales> filtrarListExperienciaLaboralEmpl;
    //SectoresEconomicos
    private List<SectoresEconomicos> listSectoresEconomicos;
    private SectoresEconomicos sectorSeleccionada;
    private List<SectoresEconomicos> filtrarListSectoresEconomicos;
    //MotivosRetiros
    private List<MotivosRetiros> listMotivosRetiros;
    private MotivosRetiros motivoSeleccionado;
    private List<MotivosRetiros> filtrarListMotivosRetiros;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo VP Crtl + F11
    private int bandera;
    private HVHojasDeVida hojaVida;
    //Columnas Tabla VP
    private Column expEmpresa, expCargoDes, expJefe, expTelefono, expSectorEco, expMotivos, expFechaInicio, expFechaRetiro;
    //Otros
    private boolean aceptar;
    //modificar
    private List<HvExperienciasLaborales> listExperienciaLaboralModificar;
    private boolean guardado;
    //crear VP
    public HvExperienciasLaborales nuevaExperienciaLaboral;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<HvExperienciasLaborales> listExperienciaLaboralBorrar;
    //editar celda
    private HvExperienciasLaborales editarExperienciaLaboral;
    //duplicar
    //Autocompletar
    private boolean permitirIndex;
    //Variables Autompletar
    private String sector, motivo;
    private int index, indexAux;
    private int cualCelda, tipoLista;
    private HvExperienciasLaborales duplicarExperienciaLaboral;
    private List<HvExperienciasLaborales> listExperienciaLaboralCrear;
    private boolean cambioExperiencia;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private String logrosAlcanzados;
    private Empleados empleado;
    private boolean readOnlyLogros;
    private boolean cambiosLogros;
    private Date fechaDesde;
    private Date fechaIni, fechaFin;
    private String fechaDesdeText, fechaHastaText;
    ////
    private final SimpleDateFormat formatoFecha;

    public ControlPerExperienciaLaboral() {
        hojaVida = new HVHojasDeVida();
        fechaFin = new Date();
        fechaIni = new Date();
        fechaDesde = new Date();
        cambiosLogros = true;
        readOnlyLogros = false;
        empleado = new Empleados();
        logrosAlcanzados = "";
        backUpSecRegistro = null;
        tipoLista = 0;
        //Otros
        aceptar = true;
        listExperienciaLaboralBorrar = new ArrayList<HvExperienciasLaborales>();
        k = 0;
        listExperienciaLaboralModificar = new ArrayList<HvExperienciasLaborales>();
        editarExperienciaLaboral = new HvExperienciasLaborales();
        tipoLista = 0;
        guardado = true;
        bandera = 0;
        permitirIndex = true;
        index = 0;
        secRegistro = null;
        cualCelda = -1;
        listMotivosRetiros = null;
        listSectoresEconomicos = null;
        nuevaExperienciaLaboral = new HvExperienciasLaborales();
        nuevaExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
        nuevaExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
        listExperienciaLaboralCrear = new ArrayList<HvExperienciasLaborales>();
        cambioExperiencia = false;
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    }

    public void recibirEmpleado(BigInteger secuencia) {
        empleado = administrarPerExperienciaLaboral.empleadoActual(secuencia);
        hojaVida = administrarPerExperienciaLaboral.obtenerHojaVidaPersona(empleado.getPersona().getSecuencia());
        if (hojaVida != null) {
            listExperienciaLaboralEmpl = administrarPerExperienciaLaboral.listExperienciasLaboralesSecuenciaEmpleado(hojaVida.getSecuencia());
            if (!listExperienciaLaboralEmpl.isEmpty()) {
                logrosAlcanzados = listExperienciaLaboralEmpl.get(0).getAlcance();
            }
        }
    }

    public boolean validarFechasRegistro(int i) {
        boolean retorno = true;
        if (i == 0) {
            HvExperienciasLaborales temporal = new HvExperienciasLaborales();
            if (tipoLista == 0) {
                temporal = listExperienciaLaboralEmpl.get(index);
            }
            if (tipoLista == 1) {
                int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(index));
                temporal = listExperienciaLaboralEmpl.get(ind);
            }
            if (temporal.getFechadesde() != null && temporal.getFechahasta() != null) {
                if (temporal.getFechadesde().before(temporal.getFechahasta())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (temporal.getFechadesde() != null && temporal.getFechahasta() == null) {
                retorno = true;
            }
            if (temporal.getFechadesde() == null && temporal.getFechahasta() == null) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaExperienciaLaboral.getFechadesde() != null && nuevaExperienciaLaboral.getFechahasta() != null) {
                if (nuevaExperienciaLaboral.getFechadesde().before(nuevaExperienciaLaboral.getFechahasta())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaExperienciaLaboral.getFechadesde() != null && nuevaExperienciaLaboral.getFechahasta() == null) {
                retorno = true;
            }
            if (nuevaExperienciaLaboral.getFechadesde() == null && nuevaExperienciaLaboral.getFechahasta() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarExperienciaLaboral.getFechadesde() != null && duplicarExperienciaLaboral.getFechahasta() != null) {
                if (duplicarExperienciaLaboral.getFechadesde().before(duplicarExperienciaLaboral.getFechahasta())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarExperienciaLaboral.getFechadesde() != null && duplicarExperienciaLaboral.getFechahasta() == null) {
                retorno = true;
            }
            if (duplicarExperienciaLaboral.getFechadesde() == null && duplicarExperienciaLaboral.getFechahasta() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarCamposRegistro(int i) {
        boolean retorno = false;
        if (i == 0) {
            if (fechaDesdeText.isEmpty()) {
                retorno = false;
            } else {
                retorno = true;
            }
        } else if (i == 1) {
            if (fechaDesdeText.isEmpty()) {
                retorno = false;
            } else {
                retorno = true;
            }
        } else if (i == 2) {
            if (fechaDesdeText.isEmpty()) {
                retorno = false;
            } else {
                retorno = true;
            }
        }
        return retorno;
    }

    public void modificarExperiencia(int indice) {
        if (tipoLista == 0) {
            if (listExperienciaLaboralEmpl.get(indice).getFechadesde() != null) {
                fechaDesdeText = formatoFecha.format(listExperienciaLaboralEmpl.get(indice).getFechadesde());
            } else {
                fechaDesdeText = "";
            }
        }
        if (tipoLista == 1) {
            int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(indice));
            int aux = ind;
            if (listExperienciaLaboralEmpl.get(aux).getFechadesde() != null) {
                fechaDesdeText = formatoFecha.format(listExperienciaLaboralEmpl.get(aux).getFechadesde());
            } else {
                fechaDesdeText = "";
            }
        }
        boolean retorno = validarFechasRegistro(0);
        if (retorno == true) {
            if (validarCamposRegistro(0) == true) {
                if (tipoLista == 0) {
                    listExperienciaLaboralEmpl.get(indice).setAlcance(logrosAlcanzados);
                    if (!listExperienciaLaboralCrear.contains(listExperienciaLaboralEmpl.get(indice))) {
                        if (listExperienciaLaboralModificar.isEmpty()) {
                            listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(indice));
                        } else if (!listExperienciaLaboralModificar.contains(listExperienciaLaboralEmpl.get(indice))) {
                            listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    cambioExperiencia = true;
                    index = -1;
                    secRegistro = null;
                    fechaIni = null;
                    fechaFin = null;

                } else {
                    int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(indice));
                    listExperienciaLaboralEmpl.get(ind).setAlcance(logrosAlcanzados);
                    if (!listExperienciaLaboralCrear.contains(filtrarListExperienciaLaboralEmpl.get(indice))) {
                        if (listExperienciaLaboralModificar.isEmpty()) {
                            listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(indice));
                        } else if (!listExperienciaLaboralModificar.contains(filtrarListExperienciaLaboralEmpl.get(indice))) {
                            listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    cambioExperiencia = true;
                    index = -1;
                    secRegistro = null;
                    fechaIni = null;
                    fechaFin = null;

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosExperiencia");
            } else {
                if (tipoLista == 0) {
                    listExperienciaLaboralEmpl.get(indice).setFechadesde(fechaDesde);
                }
                if (tipoLista == 1) {
                    int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(indice));
                    listExperienciaLaboralEmpl.get(ind).setFechadesde(fechaDesde);
                }

                fechaDesdeText = "";
                index = -1;
                secRegistro = null;
                fechaIni = null;
                fechaFin = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorIngresoReg.show()");
                context.update("form:datosExperiencia");
            }
        } else {
            if (tipoLista == 0) {
                listExperienciaLaboralEmpl.get(index).setFechadesde(fechaIni);
                listExperienciaLaboralEmpl.get(index).setFechahasta(fechaFin);
            }
            if (tipoLista == 1) {
                int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(indice));
                listExperienciaLaboralEmpl.get(ind).setFechadesde(fechaIni);
                listExperienciaLaboralEmpl.get(ind).setFechahasta(fechaFin);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechasIngresoReg.show()");
            context.update("form:datosExperiencia");
            fechaIni = null;
            fechaFin = null;
        }
    }

    public void modificarExperiencia(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("SECTORES")) {
            if (tipoLista == 0) {
                listExperienciaLaboralEmpl.get(indice).getSectoreconomico().setDescripcion(sector);
            } else {
                filtrarListExperienciaLaboralEmpl.get(indice).getSectoreconomico().setDescripcion(sector);
            }
            if (listSectoresEconomicos != null) {
                for (int i = 0; i < listSectoresEconomicos.size(); i++) {
                    if (listSectoresEconomicos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listExperienciaLaboralEmpl.get(indice).setSectoreconomico(listSectoresEconomicos.get(indiceUnicoElemento));
                } else {
                    filtrarListExperienciaLaboralEmpl.get(indice).setSectoreconomico(listSectoresEconomicos.get(indiceUnicoElemento));
                }
                listSectoresEconomicos = null;
                getListSectoresEconomicos();
                cambioExperiencia = true;
            } else {
                permitirIndex = false;
                context.update("form:SectorDialogo");
                context.execute("SectorDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOS")) {
            if (tipoLista == 0) {
                listExperienciaLaboralEmpl.get(indice).getMotivoretiro().setNombre(motivo);
            } else {
                filtrarListExperienciaLaboralEmpl.get(indice).getMotivoretiro().setNombre(motivo);
            }
            if (listMotivosRetiros != null) {
                for (int i = 0; i < listMotivosRetiros.size(); i++) {
                    if (listMotivosRetiros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listExperienciaLaboralEmpl.get(indice).setMotivoretiro(listMotivosRetiros.get(indiceUnicoElemento));
                } else {
                    filtrarListExperienciaLaboralEmpl.get(indice).setMotivoretiro(listMotivosRetiros.get(indiceUnicoElemento));
                }
                listMotivosRetiros = null;
                getListMotivosRetiros();
                cambioExperiencia = true;
            } else {
                permitirIndex = false;
                context.update("form:MotivosDialogo");
                context.execute("MotivosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listExperienciaLaboralCrear.contains(listExperienciaLaboralEmpl.get(indice))) {

                    if (listExperienciaLaboralModificar.isEmpty()) {
                        listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(indice));
                    } else if (!listExperienciaLaboralModificar.contains(listExperienciaLaboralEmpl.get(indice))) {
                        listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioExperiencia = true;
                index = -1;
                secRegistro = null;
            } else {
                if (!listExperienciaLaboralCrear.contains(filtrarListExperienciaLaboralEmpl.get(indice))) {

                    if (listExperienciaLaboralModificar.isEmpty()) {
                        listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(indice));
                    } else if (!listExperienciaLaboralModificar.contains(filtrarListExperienciaLaboralEmpl.get(indice))) {
                        listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioExperiencia = true;
                index = -1;
                secRegistro = null;
            }
            cambioExperiencia = true;
        }
        context.update("form:datosExperiencia");
    }

    public void valoresBackupAutocompletarExperiencia(int tipoNuevo, String Campo) {

        if (Campo.equals("SECTORES")) {
            if (tipoNuevo == 1) {
                sector = nuevaExperienciaLaboral.getSectoreconomico().getDescripcion();
            } else if (tipoNuevo == 2) {
                sector = duplicarExperienciaLaboral.getSectoreconomico().getDescripcion();
            }
        } else if (Campo.equals("MOTIVOS")) {
            if (tipoNuevo == 1) {
                motivo = nuevaExperienciaLaboral.getMotivoretiro().getNombre();
            } else if (tipoNuevo == 2) {
                motivo = duplicarExperienciaLaboral.getMotivoretiro().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoExperiencia(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("SECTORES")) {
            if (tipoNuevo == 1) {
                nuevaExperienciaLaboral.getSectoreconomico().setDescripcion(sector);
            } else if (tipoNuevo == 2) {
                duplicarExperienciaLaboral.getSectoreconomico().setDescripcion(sector);
            }
            for (int i = 0; i < listSectoresEconomicos.size(); i++) {
                if (listSectoresEconomicos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaExperienciaLaboral.setSectoreconomico(listSectoresEconomicos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaSectorEP");
                } else if (tipoNuevo == 2) {
                    duplicarExperienciaLaboral.setSectoreconomico(listSectoresEconomicos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarSectorEP");
                }
                listSectoresEconomicos = null;
                getListSectoresEconomicos();
            } else {
                context.update("form:SectorDialogo");
                context.execute("SectorDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaSectorEP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarSectorEP");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOS")) {
            if (tipoNuevo == 1) {
                nuevaExperienciaLaboral.getMotivoretiro().setNombre(motivo);
            } else if (tipoNuevo == 2) {
                duplicarExperienciaLaboral.getMotivoretiro().setNombre(motivo);
            }
            for (int i = 0; i < listMotivosRetiros.size(); i++) {
                if (listMotivosRetiros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }

            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaExperienciaLaboral.setMotivoretiro(listMotivosRetiros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMotivoEP");
                } else if (tipoNuevo == 2) {
                    duplicarExperienciaLaboral.setMotivoretiro(listMotivosRetiros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivoEP");
                }
                listMotivosRetiros = null;
                getListMotivosRetiros();
            } else {
                context.update("form:MotivosDialogo");
                context.execute("MotivosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaMotivoEP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivoEP");
                }
            }
        }
    }

    public void posicionLogros(int cel) {
        if (permitirIndex == true) {
            if (index >= 0) {
                cualCelda = cel;
                secRegistro = listExperienciaLaboralEmpl.get(index).getSecuencia();
            }
        }
    }

    public void modificarLogros() {
        if (index >= 0) {
            modificarExperiencia(index);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:editarLogrosEP");
            cambiosLogros = false;
        }
    }

    public void adicionCambiarIndiceModificar(int indice, int celda) {
        cambiarIndiceFechas(indice, celda);
        modificarExperiencia(indice);
        indexAux = indice;
    }

    public void cambiarIndice(int indice, int celda) {
        if (cambiosLogros == true) {
            if (permitirIndex == true) {
                index = indice;
                cualCelda = celda;
                secRegistro = listExperienciaLaboralEmpl.get(index).getSecuencia();
                if (cualCelda == 6) {
                    sector = listExperienciaLaboralEmpl.get(index).getSectoreconomico().getDescripcion();
                }
                if (cualCelda == 7) {
                    motivo = listExperienciaLaboralEmpl.get(index).getMotivoretiro().getNombre();
                }
                /////////////////

                /////////////////
                if (tipoLista == 0) {
                    fechaFin = listExperienciaLaboralEmpl.get(indice).getFechahasta();
                    fechaIni = listExperienciaLaboralEmpl.get(indice).getFechadesde();
                    if (cualCelda == 1) {
                        if (listExperienciaLaboralEmpl.get(indice).getFechadesde() != null) {
                            fechaDesde = listExperienciaLaboralEmpl.get(indice).getFechadesde();
                        }
                    }
                }
                if (tipoLista == 1) {
                    int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(indice));
                    fechaFin = listExperienciaLaboralEmpl.get(ind).getFechahasta();
                    fechaIni = listExperienciaLaboralEmpl.get(ind).getFechadesde();
                    if (cualCelda == 1) {
                        if (listExperienciaLaboralEmpl.get(ind).getFechadesde() != null) {
                            fechaDesde = listExperienciaLaboralEmpl.get(ind).getFechadesde();
                        }
                    }
                }

                ///////////////
                logrosAlcanzados = listExperienciaLaboralEmpl.get(index).getAlcance();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:editarLogrosEP");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceFechas(int indice, int celda) {
        if (cambiosLogros == true) {
            if (permitirIndex == true) {
                index = indice;
                cualCelda = celda;
                secRegistro = listExperienciaLaboralEmpl.get(index).getSecuencia();
                if (cualCelda == 6) {
                    sector = listExperienciaLaboralEmpl.get(index).getSectoreconomico().getDescripcion();
                }
                if (cualCelda == 7) {
                    motivo = listExperienciaLaboralEmpl.get(index).getMotivoretiro().getNombre();
                }
                if (tipoLista == 0) {
                    if (fechaFin == null) {
                        fechaFin = listExperienciaLaboralEmpl.get(indice).getFechahasta();
                    }
                    if (fechaIni == null) {
                        fechaIni = listExperienciaLaboralEmpl.get(indice).getFechadesde();
                    }
                    if (cualCelda == 1) {
                        if (listExperienciaLaboralEmpl.get(indice).getFechadesde() != null) {
                            fechaDesde = listExperienciaLaboralEmpl.get(indice).getFechadesde();
                        }
                    }
                }
                if (tipoLista == 1) {
                    int ind = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(indice));
                    if (fechaFin == null) {
                        fechaFin = listExperienciaLaboralEmpl.get(ind).getFechahasta();
                    }
                    if (fechaIni == null) {
                        fechaIni = listExperienciaLaboralEmpl.get(ind).getFechadesde();
                    }
                    if (cualCelda == 1) {
                        if (listExperienciaLaboralEmpl.get(ind).getFechadesde() != null) {
                            fechaDesde = listExperienciaLaboralEmpl.get(ind).getFechadesde();
                        }
                    }
                }
                ///////////////
                logrosAlcanzados = listExperienciaLaboralEmpl.get(index).getAlcance();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:editarLogrosEP");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambios();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     */
    public void guardarCambios() {
        if (guardado == false) {
            if (!listExperienciaLaboralBorrar.isEmpty()) {
                administrarPerExperienciaLaboral.borrarExperienciaLaboral(listExperienciaLaboralBorrar);
                listExperienciaLaboralBorrar.clear();
            }
            if (!listExperienciaLaboralCrear.isEmpty()) {
                administrarPerExperienciaLaboral.crearExperienciaLaboral(listExperienciaLaboralCrear);
                listExperienciaLaboralCrear.clear();
            }
            if (!listExperienciaLaboralModificar.isEmpty()) {
                administrarPerExperienciaLaboral.editarExperienciaLaboral(listExperienciaLaboralModificar);
                listExperienciaLaboralModificar.clear();
            }
            listExperienciaLaboralEmpl = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosExperiencia");
            k = 0;
        }
        cambioExperiencia = false;
        index = -1;
        secRegistro = null;
        cambiosLogros = true;
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            expEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expEmpresa");
            expEmpresa.setFilterStyle("display: none; visibility: hidden;");
            expCargoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expCargoDes");
            expCargoDes.setFilterStyle("display: none; visibility: hidden;");
            expJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expJefe");
            expJefe.setFilterStyle("display: none; visibility: hidden;");
            expTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expTelefono");
            expTelefono.setFilterStyle("display: none; visibility: hidden;");
            expSectorEco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expSectorEco");
            expSectorEco.setFilterStyle("display: none; visibility: hidden;");
            expMotivos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expMotivos");
            expMotivos.setFilterStyle("display: none; visibility: hidden;");

            expFechaInicio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaInicio");
            expFechaInicio.setFilterStyle("display: none; visibility: hidden;");
            expFechaRetiro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaRetiro");
            expFechaRetiro.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosExperiencia");
            bandera = 0;
            filtrarListExperienciaLaboralEmpl = null;
            tipoLista = 0;
        }
        listMotivosRetiros = null;
        listSectoresEconomicos = null;
        listExperienciaLaboralBorrar.clear();
        listExperienciaLaboralCrear.clear();
        listExperienciaLaboralModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listExperienciaLaboralEmpl = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosExperiencia");
        logrosAlcanzados = " ";
        context.update("form:editarLogrosEP");
        cambioExperiencia = false;
        cambiosLogros = true;
        nuevaExperienciaLaboral = new HvExperienciasLaborales();
        nuevaExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
        nuevaExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
        fechaFin = null;
        fechaIni = null;
    }

    public void editarCelda() {
        if (cualCelda == 1 || cualCelda == 2) {
            index = indexAux;
        }
        if (index >= 0) {
            if (tipoLista == 0) {
                editarExperienciaLaboral = listExperienciaLaboralEmpl.get(index);
            }
            if (tipoLista == 1) {
                editarExperienciaLaboral = filtrarListExperienciaLaboralEmpl.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarEmpresaD");
                context.execute("editarEmpresaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaInicialD");
                context.execute("editarFechaInicialD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarFechaFinalD");
                context.execute("editarFechaFinalD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarCargoD");
                context.execute("editarCargoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarJefeD");
                context.execute("editarJefeD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarTelefonoD");
                context.execute("editarTelefonoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarSectorD");
                context.execute("editarSectorD.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarMotivoD");
                context.execute("editarMotivoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarLogroD");
                context.execute("editarLogroD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevaE() {
        if (nuevaExperienciaLaboral.getFechadesde() != null) {
            fechaDesdeText = formatoFecha.format(nuevaExperienciaLaboral.getFechadesde());
        } else {
            fechaDesdeText = "";
        }
        boolean respuesta = validarFechasRegistro(1);
        if (respuesta == true) {
            if (validarCamposRegistro(1)) {
                cambioExperiencia = true;
                //CERRAR FILTRADO
                if (bandera == 1) {
                    expEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expEmpresa");
                    expEmpresa.setFilterStyle("display: none; visibility: hidden;");
                    expCargoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expCargoDes");
                    expCargoDes.setFilterStyle("display: none; visibility: hidden;");
                    expJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expJefe");
                    expJefe.setFilterStyle("display: none; visibility: hidden;");
                    expTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expTelefono");
                    expTelefono.setFilterStyle("display: none; visibility: hidden;");
                    expSectorEco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expSectorEco");
                    expSectorEco.setFilterStyle("display: none; visibility: hidden;");
                    expMotivos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expMotivos");
                    expMotivos.setFilterStyle("display: none; visibility: hidden;");

                    expFechaInicio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaInicio");
                    expFechaInicio.setFilterStyle("display: none; visibility: hidden;");
                    expFechaRetiro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaRetiro");
                    expFechaRetiro.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosExperiencia");
                    bandera = 0;
                    filtrarListExperienciaLaboralEmpl = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                k++;
                l = BigInteger.valueOf(k);
                nuevaExperienciaLaboral.setSecuencia(l);
                nuevaExperienciaLaboral.setHojadevida(hojaVida);
                listExperienciaLaboralEmpl.add(nuevaExperienciaLaboral);
                listExperienciaLaboralCrear.add(nuevaExperienciaLaboral);
                //
                nuevaExperienciaLaboral = new HvExperienciasLaborales();
                nuevaExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
                nuevaExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
                limpiarNuevaE();
                //
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                index = -1;
                secRegistro = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosExperiencia");
                context.execute("NuevoRegistro.hide()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorIngresoReg.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechasIngresoReg.show()");
        }
    }

    /**
     */
    public void limpiarNuevaE() {
        nuevaExperienciaLaboral = new HvExperienciasLaborales();
        nuevaExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
        nuevaExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
        index = -1;
        secRegistro = null;

    }

    public void cancelarNuevaE() {
        nuevaExperienciaLaboral = new HvExperienciasLaborales();
        nuevaExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
        nuevaExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
        index = -1;
        secRegistro = null;
        cambioExperiencia = false;
    }

    public void verificarDuplicarExperiencia() {
        System.out.println("Valor Index : "+index);
        if (index >= 0) {
            if (!listExperienciaLaboralEmpl.isEmpty()) {
                duplicarVigenciaE();                
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("seleccionarRegistro.show()");
            }
        } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("seleccionarRegistro.show()");
            }
    }

    public void duplicarVigenciaE() {
        if (index >= 0) {
            duplicarExperienciaLaboral = new HvExperienciasLaborales();
            k++;
            BigDecimal var = BigDecimal.valueOf(k);
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarExperienciaLaboral.setSecuencia(l);
                duplicarExperienciaLaboral.setAlcance(listExperienciaLaboralEmpl.get(index).getAlcance());
                duplicarExperienciaLaboral.setCargo(listExperienciaLaboralEmpl.get(index).getCargo());
                duplicarExperienciaLaboral.setEmpresa(listExperienciaLaboralEmpl.get(index).getEmpresa());
                duplicarExperienciaLaboral.setFechadesde(listExperienciaLaboralEmpl.get(index).getFechadesde());
                duplicarExperienciaLaboral.setFechahasta(listExperienciaLaboralEmpl.get(index).getFechahasta());
                duplicarExperienciaLaboral.setHojadevida(listExperienciaLaboralEmpl.get(index).getHojadevida());
                duplicarExperienciaLaboral.setJefeinmediato(listExperienciaLaboralEmpl.get(index).getJefeinmediato());
                duplicarExperienciaLaboral.setMotivoretiro(listExperienciaLaboralEmpl.get(index).getMotivoretiro());
                duplicarExperienciaLaboral.setSectoreconomico(listExperienciaLaboralEmpl.get(index).getSectoreconomico());
                duplicarExperienciaLaboral.setTelefono(listExperienciaLaboralEmpl.get(index).getTelefono());
            }
            if (tipoLista == 1) {

                duplicarExperienciaLaboral.setSecuencia(l);
                duplicarExperienciaLaboral.setAlcance(filtrarListExperienciaLaboralEmpl.get(index).getAlcance());
                duplicarExperienciaLaboral.setCargo(filtrarListExperienciaLaboralEmpl.get(index).getCargo());
                duplicarExperienciaLaboral.setEmpresa(filtrarListExperienciaLaboralEmpl.get(index).getEmpresa());
                duplicarExperienciaLaboral.setFechadesde(filtrarListExperienciaLaboralEmpl.get(index).getFechadesde());
                duplicarExperienciaLaboral.setFechahasta(filtrarListExperienciaLaboralEmpl.get(index).getFechahasta());
                duplicarExperienciaLaboral.setHojadevida(filtrarListExperienciaLaboralEmpl.get(index).getHojadevida());
                duplicarExperienciaLaboral.setJefeinmediato(filtrarListExperienciaLaboralEmpl.get(index).getJefeinmediato());
                duplicarExperienciaLaboral.setMotivoretiro(filtrarListExperienciaLaboralEmpl.get(index).getMotivoretiro());
                duplicarExperienciaLaboral.setSectoreconomico(filtrarListExperienciaLaboralEmpl.get(index).getSectoreconomico());
                duplicarExperienciaLaboral.setTelefono(filtrarListExperienciaLaboralEmpl.get(index).getTelefono());
            }
            cambioExperiencia = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEP");
            context.execute("DuplicarRegistro.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicarE() {
        if (duplicarExperienciaLaboral.getFechadesde() != null) {
            fechaDesdeText = formatoFecha.format(duplicarExperienciaLaboral.getFechadesde());
        } else {
            fechaDesdeText = "";
        }
        boolean respuesta = validarFechasRegistro(2);
        if (respuesta == true) {
            if (validarCamposRegistro(2)) {
                cambioExperiencia = true;
                k++;
                l = BigInteger.valueOf(k);
                duplicarExperienciaLaboral.setSecuencia(l);
                duplicarExperienciaLaboral.setHojadevida(hojaVida);
                listExperienciaLaboralEmpl.add(duplicarExperienciaLaboral);
                listExperienciaLaboralCrear.add(duplicarExperienciaLaboral);
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    expEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expEmpresa");
                    expEmpresa.setFilterStyle("display: none; visibility: hidden;");
                    expCargoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expCargoDes");
                    expCargoDes.setFilterStyle("display: none; visibility: hidden;");
                    expJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expJefe");
                    expJefe.setFilterStyle("display: none; visibility: hidden;");
                    expTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expTelefono");
                    expTelefono.setFilterStyle("display: none; visibility: hidden;");
                    expSectorEco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expSectorEco");
                    expSectorEco.setFilterStyle("display: none; visibility: hidden;");
                    expMotivos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expMotivos");
                    expMotivos.setFilterStyle("display: none; visibility: hidden;");

                    expFechaInicio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaInicio");
                    expFechaInicio.setFilterStyle("display: none; visibility: hidden;");
                    expFechaRetiro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaRetiro");
                    expFechaRetiro.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosExperiencia");
                    bandera = 0;
                    filtrarListExperienciaLaboralEmpl = null;
                    tipoLista = 0;
                }
                duplicarExperienciaLaboral = new HvExperienciasLaborales();
                limpiarduplicarE();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosExperiencia");
                context.update("form:editarLogrosEP");
                context.execute("DuplicarRegistro.hide()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorIngresoReg.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechasIngresoReg.show()");
        }
    }

    public void limpiarduplicarE() {
        duplicarExperienciaLaboral = new HvExperienciasLaborales();
        duplicarExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
        duplicarExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
    }

    public void cancelarDuplicadoE() {
        cambioExperiencia = false;
        duplicarExperienciaLaboral = new HvExperienciasLaborales();
        duplicarExperienciaLaboral.setSectoreconomico(new SectoresEconomicos());
        duplicarExperienciaLaboral.setMotivoretiro(new MotivosRetiros());
        index = -1;
        secRegistro = null;
    }

    public void validarBorradoExperiencia() {
        if (logrosAlcanzados.isEmpty()) {
            if (index >= 0) {
                borrarE();
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorBorradoReg.show()");
        }
    }

    public void borrarE() {
        cambioExperiencia = true;
        if (tipoLista == 0) {
            if (!listExperienciaLaboralModificar.isEmpty() && listExperienciaLaboralModificar.contains(listExperienciaLaboralEmpl.get(index))) {
                int modIndex = listExperienciaLaboralModificar.indexOf(listExperienciaLaboralEmpl.get(index));
                listExperienciaLaboralModificar.remove(modIndex);
                listExperienciaLaboralBorrar.add(listExperienciaLaboralEmpl.get(index));
            } else if (!listExperienciaLaboralCrear.isEmpty() && listExperienciaLaboralCrear.contains(listExperienciaLaboralEmpl.get(index))) {
                int crearIndex = listExperienciaLaboralCrear.indexOf(listExperienciaLaboralEmpl.get(index));
                listExperienciaLaboralCrear.remove(crearIndex);
            } else {
                listExperienciaLaboralBorrar.add(listExperienciaLaboralEmpl.get(index));
            }
            listExperienciaLaboralEmpl.remove(index);
        }
        if (tipoLista == 1) {
            if (!listExperienciaLaboralModificar.isEmpty() && listExperienciaLaboralModificar.contains(filtrarListExperienciaLaboralEmpl.get(index))) {
                int modIndex = listExperienciaLaboralModificar.indexOf(filtrarListExperienciaLaboralEmpl.get(index));
                listExperienciaLaboralModificar.remove(modIndex);
                listExperienciaLaboralBorrar.add(filtrarListExperienciaLaboralEmpl.get(index));
            } else if (!listExperienciaLaboralCrear.isEmpty() && listExperienciaLaboralCrear.contains(filtrarListExperienciaLaboralEmpl.get(index))) {
                int crearIndex = listExperienciaLaboralCrear.indexOf(filtrarListExperienciaLaboralEmpl.get(index));
                listExperienciaLaboralCrear.remove(crearIndex);
            } else {
                listExperienciaLaboralBorrar.add(filtrarListExperienciaLaboralEmpl.get(index));
            }
            int VPIndex = listExperienciaLaboralEmpl.indexOf(filtrarListExperienciaLaboralEmpl.get(index));
            listExperienciaLaboralEmpl.remove(VPIndex);
            filtrarListExperienciaLaboralEmpl.remove(index);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosExperiencia");

        context.update("form:editarLogrosEP");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
        }
    }

    public void activarCtrlF11() {
        filtradoExperiencia();
    }

    /**
     */
    public void filtradoExperiencia() {
        if (bandera == 0) {
            expEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expEmpresa");
            expEmpresa.setFilterStyle("width: 90px");
            expCargoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expCargoDes");
            expCargoDes.setFilterStyle("width: 60px");
            expJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expJefe");
            expJefe.setFilterStyle("width: 80px");
            expTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expTelefono");
            expTelefono.setFilterStyle("width: 80px");
            expSectorEco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expSectorEco");
            expSectorEco.setFilterStyle("width: 90px");
            expMotivos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expMotivos");
            expMotivos.setFilterStyle("width: 90px");
            expFechaInicio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaInicio");
            expFechaInicio.setFilterStyle("width: 60px");
            expFechaRetiro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaRetiro");
            expFechaRetiro.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosExperiencia");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            expEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expEmpresa");
            expEmpresa.setFilterStyle("display: none; visibility: hidden;");
            expCargoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expCargoDes");
            expCargoDes.setFilterStyle("display: none; visibility: hidden;");
            expJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expJefe");
            expJefe.setFilterStyle("display: none; visibility: hidden;");
            expTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expTelefono");
            expTelefono.setFilterStyle("display: none; visibility: hidden;");
            expSectorEco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expSectorEco");
            expSectorEco.setFilterStyle("display: none; visibility: hidden;");
            expMotivos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expMotivos");
            expMotivos.setFilterStyle("display: none; visibility: hidden;");

            expFechaInicio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaInicio");
            expFechaInicio.setFilterStyle("display: none; visibility: hidden;");
            expFechaRetiro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaRetiro");
            expFechaRetiro.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosExperiencia");
            bandera = 0;
            filtrarListExperienciaLaboralEmpl = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     */
    public void salir() {
        if (bandera == 1) {
            expEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expEmpresa");
            expEmpresa.setFilterStyle("display: none; visibility: hidden;");
            expCargoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expCargoDes");
            expCargoDes.setFilterStyle("display: none; visibility: hidden;");
            expJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expJefe");
            expJefe.setFilterStyle("display: none; visibility: hidden;");
            expTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expTelefono");
            expTelefono.setFilterStyle("display: none; visibility: hidden;");
            expSectorEco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expSectorEco");
            expSectorEco.setFilterStyle("display: none; visibility: hidden;");
            expMotivos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expMotivos");
            expMotivos.setFilterStyle("display: none; visibility: hidden;");

            expFechaInicio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaInicio");
            expFechaInicio.setFilterStyle("display: none; visibility: hidden;");
            expFechaRetiro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosExperiencia:expFechaRetiro");
            expFechaRetiro.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosExperiencia");
            bandera = 0;
            filtrarListExperienciaLaboralEmpl = null;
            tipoLista = 0;
        }
        listExperienciaLaboralBorrar.clear();
        listExperienciaLaboralCrear.clear();
        listExperienciaLaboralModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listExperienciaLaboralEmpl = null;
        listMotivosRetiros = null;
        listSectoresEconomicos = null;
        guardado = true;
        cambioExperiencia = false;
        tipoActualizacion = -1;
        fechaDesde = null;
        fechaDesdeText = "";
        fechaFin = null;
        fechaHastaText = "";
        fechaIni = null;
        logrosAlcanzados = "";
        hojaVida = null;
        cambiosLogros = true;
        permitirIndex = true;
        empleado = null;

    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
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
        if (dlg == 1) {
            context.update("form:SectorDialogo");
            context.execute("SectorDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:MotivosDialogo");
            context.execute("MotivosDialogo.show()");
        }
    }

    public void actualizarSector() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listExperienciaLaboralEmpl.get(index).setSectoreconomico(sectorSeleccionada);
                if (!listExperienciaLaboralCrear.contains(listExperienciaLaboralEmpl.get(index))) {
                    if (listExperienciaLaboralModificar.isEmpty()) {
                        listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(index));
                    } else if (!listExperienciaLaboralModificar.contains(listExperienciaLaboralEmpl.get(index))) {
                        listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambioExperiencia = true;
                permitirIndex = true;

            } else {
                filtrarListExperienciaLaboralEmpl.get(index).setSectoreconomico(sectorSeleccionada);
                if (!listExperienciaLaboralCrear.contains(filtrarListExperienciaLaboralEmpl.get(index))) {
                    if (listExperienciaLaboralModificar.isEmpty()) {
                        listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(index));
                    } else if (!listExperienciaLaboralModificar.contains(filtrarListExperienciaLaboralEmpl.get(index))) {
                        listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambioExperiencia = true;
                permitirIndex = true;

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:datosExperiencia");
        } else if (tipoActualizacion == 1) {
            nuevaExperienciaLaboral.setSectoreconomico(sectorSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaSectorEP");
        } else if (tipoActualizacion == 2) {
            duplicarExperienciaLaboral.setSectoreconomico(sectorSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarSectorEP");
        }
        filtrarListSectoresEconomicos = null;
        sectorSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioSector() {
        filtrarListSectoresEconomicos = null;
        sectorSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarMotivo() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listExperienciaLaboralEmpl.get(index).setMotivoretiro(motivoSeleccionado);
                if (!listExperienciaLaboralCrear.contains(listExperienciaLaboralEmpl.get(index))) {
                    if (listExperienciaLaboralModificar.isEmpty()) {
                        listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(index));
                    } else if (!listExperienciaLaboralModificar.contains(listExperienciaLaboralEmpl.get(index))) {
                        listExperienciaLaboralModificar.add(listExperienciaLaboralEmpl.get(index));
                    }
                }
            } else {
                filtrarListExperienciaLaboralEmpl.get(index).setMotivoretiro(motivoSeleccionado);
                if (!listExperienciaLaboralCrear.contains(filtrarListExperienciaLaboralEmpl.get(index))) {
                    if (listExperienciaLaboralModificar.isEmpty()) {
                        listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(index));
                    } else if (!listExperienciaLaboralModificar.contains(filtrarListExperienciaLaboralEmpl.get(index))) {
                        listExperienciaLaboralModificar.add(filtrarListExperienciaLaboralEmpl.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambioExperiencia = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:datosExperiencia");
        } else if (tipoActualizacion == 1) {
            nuevaExperienciaLaboral.setMotivoretiro(motivoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaMotivoEP");
        } else if (tipoActualizacion == 2) {
            duplicarExperienciaLaboral.setMotivoretiro(motivoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivoEP");
        }
        filtrarListMotivosRetiros = null;
        motivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioMotivo() {
        filtrarListMotivosRetiros = null;
        motivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 6) {
                context.update("form:SectorDialogo");
                context.execute("SectorDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 7) {
                context.update("form:MotivosDialogo");
                context.execute("MotivosDialogo.show()");
                tipoActualizacion = 0;
            }
        }

    }

    /**
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
        exportPDF_E();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_E() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosExperienciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ExperienciasLaboralesPDF", false, false, "UTF-8", null, null);
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
        exportXLS_E();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_E() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosExperienciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ExperienciasLaboralesXLS", false, false, "UTF-8", null, null);
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
        if (listExperienciaLaboralEmpl != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "HVEXPERIENCIASLABORALES");
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
            if (administrarRastros.verificarHistoricosTabla("HVEXPERIENCIASLABORALES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GET - SET 
    public List<HvExperienciasLaborales> getListExperienciaLaboralEmpl() {
        try {
            if (listExperienciaLaboralEmpl == null) {
                listExperienciaLaboralEmpl = new ArrayList<HvExperienciasLaborales>();
                listExperienciaLaboralEmpl = administrarPerExperienciaLaboral.listExperienciasLaboralesSecuenciaEmpleado(hojaVida.getSecuencia());
                for (int i = 0; i < listExperienciaLaboralEmpl.size(); i++) {
                    if (listExperienciaLaboralEmpl.get(i).getSectoreconomico() == null) {
                        listExperienciaLaboralEmpl.get(i).setSectoreconomico(new SectoresEconomicos());
                    }
                    if (listExperienciaLaboralEmpl.get(i).getMotivoretiro() == null) {
                        listExperienciaLaboralEmpl.get(i).setMotivoretiro(new MotivosRetiros());
                    }
                }
            }
            return listExperienciaLaboralEmpl;
        } catch (Exception e) {
            System.out.println("Error en getListProyectos : " + e.toString());
            return null;
        }
    }

    public void setListExperienciaLaboralEmpl(List<HvExperienciasLaborales> setListExperienciaLaboralEmpl) {
        this.listExperienciaLaboralEmpl = setListExperienciaLaboralEmpl;
    }

    public List<HvExperienciasLaborales> getFiltrarListExperienciaLaboralEmpl() {
        return filtrarListExperienciaLaboralEmpl;
    }

    public void setFiltrarListExperienciaLaboralEmpl(List<HvExperienciasLaborales> setFiltrarListExperienciaLaboralEmpl) {
        this.filtrarListExperienciaLaboralEmpl = setFiltrarListExperienciaLaboralEmpl;
    }

    public List<SectoresEconomicos> getListSectoresEconomicos() {
        try {
            if (listSectoresEconomicos == null) {
                listSectoresEconomicos = new ArrayList<SectoresEconomicos>();
                listSectoresEconomicos = administrarPerExperienciaLaboral.listSectoresEconomicos();
            }
            return listSectoresEconomicos;
        } catch (Exception e) {
            System.out.println("Error getListEmpresas " + e.toString());
            return null;
        }
    }

    public void setListSectoresEconomicos(List<SectoresEconomicos> setListSectoresEconomicos) {
        this.listSectoresEconomicos = setListSectoresEconomicos;
    }

    public SectoresEconomicos getSectorSeleccionada() {
        return sectorSeleccionada;
    }

    public void setSectorSeleccionada(SectoresEconomicos setSectorSeleccionada) {
        this.sectorSeleccionada = setSectorSeleccionada;
    }

    public List<SectoresEconomicos> getFiltrarListSectoresEconomicos() {
        return filtrarListSectoresEconomicos;
    }

    public void setFiltrarListSectoresEconomicos(List<SectoresEconomicos> setFiltrarListSectoresEconomicos) {
        this.filtrarListSectoresEconomicos = setFiltrarListSectoresEconomicos;
    }

    public List<MotivosRetiros> getListMotivosRetiros() {
        try {
            if (listMotivosRetiros == null) {
                listMotivosRetiros = new ArrayList<MotivosRetiros>();
                listMotivosRetiros = administrarPerExperienciaLaboral.listMotivosRetiros();
            }
            return listMotivosRetiros;
        } catch (Exception e) {
            System.out.println("Error getListPryClientes " + e.toString());
            return null;
        }
    }

    public void setListMotivosRetiros(List<MotivosRetiros> setListMotivosRetiros) {
        this.listMotivosRetiros = setListMotivosRetiros;
    }

    public MotivosRetiros getMotivoSeleccionado() {
        return motivoSeleccionado;
    }

    public void setMotivoSeleccionado(MotivosRetiros setMotivoSeleccionado) {
        this.motivoSeleccionado = setMotivoSeleccionado;
    }

    public List<MotivosRetiros> getFiltrarListMotivosRetiros() {
        return filtrarListMotivosRetiros;
    }

    public void setFiltrarListMotivosRetiros(List<MotivosRetiros> setFiltrarListMotivosRetiros) {
        this.filtrarListMotivosRetiros = setFiltrarListMotivosRetiros;
    }

    public List<HvExperienciasLaborales> getListExperienciaLaboralModificar() {
        return listExperienciaLaboralModificar;
    }

    public void setListExperienciaLaboralModificar(List<HvExperienciasLaborales> setListExperienciaLaboralModificar) {
        this.listExperienciaLaboralModificar = setListExperienciaLaboralModificar;
    }

    public HvExperienciasLaborales getNuevaExperienciaLaboral() {
        return nuevaExperienciaLaboral;
    }

    public void setNuevaExperienciaLaboral(HvExperienciasLaborales setNuevaExperienciaLaboral) {
        this.nuevaExperienciaLaboral = setNuevaExperienciaLaboral;
    }

    public List<HvExperienciasLaborales> getListExperienciaLaboralBorrar() {
        return listExperienciaLaboralBorrar;
    }

    public void setListExperienciaLaboralBorrar(List<HvExperienciasLaborales> setListExperienciaLaboralBorrar) {
        this.listExperienciaLaboralBorrar = setListExperienciaLaboralBorrar;
    }

    public HvExperienciasLaborales getEditarExperienciaLaboral() {
        return editarExperienciaLaboral;
    }

    public void setEditarExperienciaLaboral(HvExperienciasLaborales setEditarExperienciaLaboral) {
        this.editarExperienciaLaboral = setEditarExperienciaLaboral;
    }

    public HvExperienciasLaborales getDuplicarExperienciaLaboral() {
        return duplicarExperienciaLaboral;
    }

    public void setDuplicarExperienciaLaboral(HvExperienciasLaborales setDuplicarExperienciaLaboral) {
        this.duplicarExperienciaLaboral = setDuplicarExperienciaLaboral;
    }

    public List<HvExperienciasLaborales> getListExperienciaLaboralCrear() {
        return listExperienciaLaboralCrear;
    }

    public void setListExperienciaLaboralCrear(List<HvExperienciasLaborales> setListExperienciaLaboralCrear) {
        this.listExperienciaLaboralCrear = setListExperienciaLaboralCrear;
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

    public String getLogrosAlcanzados() {
        return logrosAlcanzados;
    }

    public void setLogrosAlcanzados(String logrosAlcanzados) {
        this.logrosAlcanzados = logrosAlcanzados;
    }

    public boolean isReadOnlyLogros() {
        if (listExperienciaLaboralEmpl.isEmpty()) {
            readOnlyLogros = true;
        } else {
            readOnlyLogros = false;
        }
        return readOnlyLogros;
    }

    public void setReadOnlyLogros(boolean readOnlyLogros) {
        this.readOnlyLogros = readOnlyLogros;
    }

    public Empleados getEmpleado() {
        empleado = administrarPerExperienciaLaboral.empleadoActual(BigInteger.valueOf(10661474));
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public String getFechaDesdeText() {
        if (index >= 0) {
            if (listExperienciaLaboralEmpl.get(index).getFechadesde() != null) {
                fechaDesdeText = formatoFecha.format(listExperienciaLaboralEmpl.get(index).getFechadesde());
            } else {
                fechaDesdeText = "";
            }
        } else {
            fechaDesdeText = "";
        }
        return fechaDesdeText;
    }

    public void setFechaDesdeText(String fechaDesdeText) {
        this.fechaDesdeText = fechaDesdeText;
    }

    public String getFechaHastaText() {
        return fechaHastaText;
    }

    public void setFechaHastaText(String fechaHastaText) {
        this.fechaHastaText = fechaHastaText;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Motivosmvrs;
import Entidades.Mvrs;
import Entidades.OtrosCertificados;
import Entidades.TiposCertificados;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplMvrsInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class ControlEmplMvr implements Serializable {

    @EJB
    AdministrarEmplMvrsInterface administrarEmplMvrs;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Sueldos
    private List<Mvrs> listMvrsEmpleado;
    private List<Mvrs> filtrarListMvrsEmpleado;
    //Vigencias Afiliaciones
    private List<OtrosCertificados> listOtrosCertificadosEmpleado;
    private List<OtrosCertificados> filtrarListOtrosCertificados;
    //Motivo Cambio Sueldo
    private List<Motivosmvrs> listMotivosMvrs;
    private Motivosmvrs motivoMvrSeleccionado;
    private List<Motivosmvrs> filtrarListMotivosMvrs;
    //
    private List<TiposCertificados> listTiposCertificados;
    private TiposCertificados tipoCertificadoSeleccionado;
    private List<TiposCertificados> filtrarListTiposCertificados;
    //Empleado
    private Empleados empleado;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int banderaMvrs;
    //Activo/Desactivo VP Crtl + F11
    private int banderaOC;
    //Columnas Tabla VL
    private Column mvrValorAnual, mvrMotivo, mvrValor, mvrFechaFinal, mvrFechaInicial;
    //Columnas Tabla VP
    private Column ocDias, ocCertificado, ocEstado, ocValor, ocFechaFinal, ocFechaInicial;
    //Otros
    private boolean aceptar;
    private int indexMvrs;
    //modificar
    private List<Mvrs> listMvrsModificar;
    private List<OtrosCertificados> listOtrosCertificadosModificar;
    private boolean guardado, guardarOk;
    //crear VL
    public Mvrs nuevaMvrs;
    //crear VP
    public OtrosCertificados nuevaOtroCertificado;
    private List<Mvrs> listMvrsCrear;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<Mvrs> listMvrsBorrar;
    private List<OtrosCertificados> listOtrosCertificadosBorrar;
    //editar celda
    private Mvrs editarMvrs;
    private OtrosCertificados editarOtrosCertificados;
    private int cualCeldaMvrs, tipoListaMvrs;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Mvrs duplicarMvrs;
    //Autocompletar
    private boolean permitirIndexMvrs, permitirIndexOtrosCertificados;
    //Variables Autompletar
    private String motivoMvrs, tipoCertificado;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexOtrosCertificados;
    //Indice de celdas VigenciaProrrateo / VigenciaProrrateoProyecto
    private int cualCeldaOtrosCertificados, tipoListaOtrosCertificados;
    //Index Auxiliar Para Nuevos Registros
    private int indexAuxMvrs, indexAuxOC;
    //Duplicar Vigencia Prorrateo
    private OtrosCertificados duplicarOtrosCertificados;
    private List<OtrosCertificados> listOtrosCertificadosCrear;
    private String nombreXML;
    private String nombreTabla;
    private BigInteger secRegistroMvrs;
    private BigInteger backUpSecRegistroMvrs;
    private BigInteger secRegistroOtrosCertificados;
    private BigInteger backUpSecRegistroOtrosCertificados;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;

    public ControlEmplMvr() {

        nombreTablaRastro = "";
        backUp = null;
        listOtrosCertificadosEmpleado = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        secRegistroMvrs = null;
        backUpSecRegistroMvrs = null;
        secRegistroOtrosCertificados = null;
        backUpSecRegistroOtrosCertificados = null;
        listMvrsEmpleado = null;
        listMotivosMvrs = null;
        empleado = new Empleados();
        listTiposCertificados = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listMvrsBorrar = new ArrayList<Mvrs>();
        listOtrosCertificadosBorrar = new ArrayList<OtrosCertificados>();
        //crear aficiones
        listMvrsCrear = new ArrayList<Mvrs>();
        k = 0;
        //modificar aficiones
        listMvrsModificar = new ArrayList<Mvrs>();
        listOtrosCertificadosModificar = new ArrayList<OtrosCertificados>();
        //editar
        editarMvrs = new Mvrs();
        editarOtrosCertificados = new OtrosCertificados();
        cambioEditor = false;
        aceptarEditar = true;
        cualCeldaMvrs = -1;
        tipoListaMvrs = 0;
        tipoListaOtrosCertificados = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaMvrs = new Mvrs();
        nuevaMvrs.setMotivo(new Motivosmvrs());
        indexMvrs = -1;
        banderaMvrs = 0;
        banderaOC = 0;
        permitirIndexMvrs = true;
        permitirIndexOtrosCertificados = true;
        indexOtrosCertificados = -1;
        cualCeldaOtrosCertificados = -1;

        nuevaOtroCertificado = new OtrosCertificados();
        nuevaOtroCertificado.setTipocertificado(new TiposCertificados());
        listOtrosCertificadosCrear = new ArrayList<OtrosCertificados>();

        nombreTabla = ":formExportarMVR:datosMvrEmpleadoExportar";
        nombreXML = "M.V.R.XML";


        duplicarMvrs = new Mvrs();
        duplicarOtrosCertificados = new OtrosCertificados();
    }

    public void recibirEmpleado(BigInteger empl) {
        listMvrsEmpleado = null;
        listOtrosCertificadosEmpleado = null;
        empleado = administrarEmplMvrs.empleadoActual(empl);
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarMvrs(int indice) {
        if (tipoListaMvrs == 0) {
            if (!listMvrsCrear.contains(listMvrsEmpleado.get(indice))) {
                if (listMvrsModificar.isEmpty()) {
                    listMvrsModificar.add(listMvrsEmpleado.get(indice));
                } else if (!listMvrsModificar.contains(listMvrsEmpleado.get(indice))) {
                    listMvrsModificar.add(listMvrsEmpleado.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexMvrs = -1;
            secRegistroMvrs = null;
        } else {
            if (!listMvrsCrear.contains(filtrarListMvrsEmpleado.get(indice))) {
                if (listMvrsModificar.isEmpty()) {
                    listMvrsModificar.add(filtrarListMvrsEmpleado.get(indice));
                } else if (!listMvrsModificar.contains(filtrarListMvrsEmpleado.get(indice))) {
                    listMvrsModificar.add(filtrarListMvrsEmpleado.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexMvrs = -1;
            secRegistroMvrs = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOCEmpleado");

    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciasLocalizaciones de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarMvrs(int indice, String confirmarCambio, String valorConfirmar) {
        indexMvrs = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            if (tipoListaMvrs == 0) {
                listMvrsEmpleado.get(indice).getMotivo().setNombre(motivoMvrs);
            } else {
                filtrarListMvrsEmpleado.get(indice).getMotivo().setNombre(motivoMvrs);
            }
            for (int i = 0; i < listMotivosMvrs.size(); i++) {
                if (listMotivosMvrs.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaMvrs == 0) {
                    listMvrsEmpleado.get(indice).setMotivo(listMotivosMvrs.get(indiceUnicoElemento));
                } else {
                    filtrarListMvrsEmpleado.get(indice).setMotivo(listMotivosMvrs.get(indiceUnicoElemento));
                }
                listMotivosMvrs.clear();
                getListMotivosMvrs();
            } else {
                permitirIndexMvrs = false;
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaMvrs == 0) {
                if (!listMvrsCrear.contains(listMvrsEmpleado.get(indice))) {

                    if (listMvrsModificar.isEmpty()) {
                        listMvrsModificar.add(listMvrsEmpleado.get(indice));
                    } else if (!listMvrsModificar.contains(listMvrsEmpleado.get(indice))) {
                        listMvrsModificar.add(listMvrsEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexMvrs = -1;
                secRegistroMvrs = null;
            } else {
                if (!listMvrsCrear.contains(filtrarListMvrsEmpleado.get(indice))) {

                    if (listMvrsModificar.isEmpty()) {
                        listMvrsModificar.add(filtrarListMvrsEmpleado.get(indice));
                    } else if (!listMvrsModificar.contains(filtrarListMvrsEmpleado.get(indice))) {
                        listMvrsModificar.add(filtrarListMvrsEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexMvrs = -1;
                secRegistroMvrs = null;
            }
        }
        context.update("form:datosMvrEmpleado");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarOtrosCertificados(int indice) {
        if (tipoListaOtrosCertificados == 0) {
            if (!listOtrosCertificadosCrear.contains(listOtrosCertificadosEmpleado.get(indice))) {
                if (listOtrosCertificadosModificar.isEmpty()) {
                    listOtrosCertificadosModificar.add(listOtrosCertificadosEmpleado.get(indice));
                } else if (!listOtrosCertificadosModificar.contains(listOtrosCertificadosEmpleado.get(indice))) {
                    listOtrosCertificadosModificar.add(listOtrosCertificadosEmpleado.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexOtrosCertificados = -1;
            secRegistroOtrosCertificados = null;
        } else {
            if (!listOtrosCertificadosCrear.contains(filtrarListOtrosCertificados.get(indice))) {

                if (listOtrosCertificadosModificar.isEmpty()) {
                    listOtrosCertificadosModificar.add(filtrarListOtrosCertificados.get(indice));
                } else if (!listOtrosCertificadosModificar.contains(filtrarListOtrosCertificados.get(indice))) {
                    listOtrosCertificadosModificar.add(filtrarListOtrosCertificados.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexOtrosCertificados = -1;
            secRegistroOtrosCertificados = null;
        }

    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciaProrrateo
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarOtrosCertificados(int indice, String confirmarCambio, String valorConfirmar) {
        indexOtrosCertificados = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CERTIFICADO")) {
            if (tipoListaOtrosCertificados == 0) {
                listOtrosCertificadosEmpleado.get(indice).getTipocertificado().setDescripcion(tipoCertificado);
            } else {
                filtrarListOtrosCertificados.get(indice).getTipocertificado().setDescripcion(tipoCertificado);
            }
            for (int i = 0; i < listTiposCertificados.size(); i++) {
                if (listTiposCertificados.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaOtrosCertificados == 0) {
                    listOtrosCertificadosEmpleado.get(indice).setTipocertificado(listTiposCertificados.get(indiceUnicoElemento));
                } else {
                    filtrarListOtrosCertificados.get(indice).setTipocertificado(listTiposCertificados.get(indiceUnicoElemento));
                }
                listTiposCertificados.clear();
                getListTiposCertificados();
            } else {
                permitirIndexOtrosCertificados = false;
                context.update("form:CertificadosDialogo");
                context.execute("CertificadosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaOtrosCertificados == 0) {
                if (!listOtrosCertificadosCrear.contains(listOtrosCertificadosEmpleado.get(indice))) {

                    if (listOtrosCertificadosModificar.isEmpty()) {
                        listOtrosCertificadosModificar.add(listOtrosCertificadosEmpleado.get(indice));
                    } else if (!listOtrosCertificadosModificar.contains(listOtrosCertificadosEmpleado.get(indice))) {
                        listOtrosCertificadosModificar.add(listOtrosCertificadosEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexOtrosCertificados = -1;
                secRegistroOtrosCertificados = null;
            } else {
                if (!listOtrosCertificadosCrear.contains(filtrarListOtrosCertificados.get(indice))) {

                    if (listOtrosCertificadosModificar.isEmpty()) {
                        listOtrosCertificadosModificar.add(filtrarListOtrosCertificados.get(indice));
                    } else if (!listOtrosCertificadosModificar.contains(filtrarListOtrosCertificados.get(indice))) {
                        listOtrosCertificadosModificar.add(filtrarListOtrosCertificados.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexOtrosCertificados = -1;
                secRegistroOtrosCertificados = null;
            }
        }
        context.update("form:datosOCEmpleado");
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaLocalizacion)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletarMvr(int tipoNuevo, String Campo) {
        if (Campo.equals("MOTIVO")) {
            if (tipoNuevo == 1) {
                motivoMvrs = nuevaMvrs.getMotivo().getNombre();
            } else if (tipoNuevo == 2) {
                motivoMvrs = duplicarMvrs.getMotivo().getNombre();
            }
        }
    }

    /**
     * Metodo que genera el auto completar de un proceso nuevoRegistro o
     * duplicarRegistro de VigenciasLocalizaciones
     *
     * @param confirmarCambio Tipo de elemento a modificar: CENTROCOSTO -
     * MOTIVOLOCALIZACION - PROYECTO
     * @param valorConfirmar Valor ingresado para confirmar
     * @param tipoNuevo Tipo de proceso: Nuevo - Duplicar
     */
    public void autocompletarNuevoyDuplicadoMvr(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            if (tipoNuevo == 1) {
                nuevaMvrs.getMotivo().setNombre(motivoMvrs);
            } else if (tipoNuevo == 2) {
                duplicarMvrs.getMotivo().setNombre(motivoMvrs);
            }
            for (int i = 0; i < listMotivosMvrs.size(); i++) {
                if (listMotivosMvrs.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaMvrs.setMotivo(listMotivosMvrs.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMotivoMVRS");
                } else if (tipoNuevo == 2) {
                    duplicarMvrs.setMotivo(listMotivosMvrs.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivoMVRS");
                }
                listMotivosMvrs.clear();
                getListMotivosMvrs();
            } else {
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaMotivoMVRS");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivoMVRS");
                }
            }
        }
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaProrrateo)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletarOtrosCertificados(int tipoNuevo, String Campo) {

        if (Campo.equals("CERTIFICADOS")) {
            if (tipoNuevo == 1) {
                tipoCertificado = nuevaOtroCertificado.getTipocertificado().getDescripcion();
            } else if (tipoNuevo == 2) {
                tipoCertificado = duplicarOtrosCertificados.getTipocertificado().getDescripcion();
            }
        }
    }

    /**
     *
     * Metodo que genera el auto completar de un proceso nuevoRegistro o
     * duplicarRegistro de VigenciasProrrateos
     *
     * @param confirmarCambio Tipo de elemento a modificar: CENTROCOSTO -
     * PROYECTO
     * @param valorConfirmar Valor ingresado para confirmar
     * @param tipoNuevo Tipo de proceso: Nuevo - Duplicar
     */
    public void autocompletarNuevoyDuplicadoOtrosCertificados(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CERTIFICADO")) {
            if (tipoNuevo == 1) {
                nuevaOtroCertificado.getTipocertificado().setDescripcion(tipoCertificado);
            } else if (tipoNuevo == 2) {
                duplicarOtrosCertificados.getTipocertificado().setDescripcion(tipoCertificado);
            }
            for (int i = 0; i < listTiposCertificados.size(); i++) {
                if (listTiposCertificados.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaOtroCertificado.setTipocertificado(listTiposCertificados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCertificadoOC");
                } else if (tipoNuevo == 2) {
                    duplicarOtrosCertificados.setTipocertificado(listTiposCertificados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCertificadoOC");
                }
                listTiposCertificados.clear();
                getListTiposCertificados();
            } else {
                context.update("form:CertificadosDialogo");
                context.execute("CertificadosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCertificadoOC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCertificadoOC");
                }
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasLocalizaciones
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceMvr(int indice, int celda) {
        if (permitirIndexMvrs == true) {
            cualCeldaMvrs = celda;
            indexMvrs = indice;
            indexAuxMvrs = indice;
            indexAuxOC = -1;
            secRegistroMvrs = listMvrsEmpleado.get(indexMvrs).getSecuencia();
            if (cualCeldaMvrs == 3) {
                motivoMvrs = listMvrsEmpleado.get(indexMvrs).getMotivo().getNombre();
            }
        }
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasProrrateos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceOtrosCertificados(int indice, int celda) {
        if (permitirIndexOtrosCertificados == true) {
            indexOtrosCertificados = indice;
            cualCeldaOtrosCertificados = celda;
            indexAuxOC = indice;
            indexAuxMvrs = -1;
            secRegistroOtrosCertificados = listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getSecuencia();
            if (cualCeldaOtrosCertificados == 4) {
                tipoCertificado = listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getTipocertificado().getDescripcion();
            }
        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambiosMvrs();
        guardarCambiosOtrosCertificados();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosMvrs() {
        if (guardado == false) {
            if (!listMvrsBorrar.isEmpty()) {
                administrarEmplMvrs.borrarMvrs(listMvrsBorrar);
                listMvrsBorrar.clear();
            }
            if (!listMvrsCrear.isEmpty()) {
                administrarEmplMvrs.borrarMvrs(listMvrsCrear);
                listMvrsCrear.clear();
            }
            if (!listMvrsModificar.isEmpty()) {
                administrarEmplMvrs.modificarMvrs(listMvrsModificar);
                listMvrsModificar.clear();
            }
            listMvrsEmpleado = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMvrEmpleado");
            k = 0;

        }
        indexMvrs = -1;
        secRegistroMvrs = null;

    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosOtrosCertificados() {
        if (guardado == false) {
            if (!listOtrosCertificadosBorrar.isEmpty()) {
                administrarEmplMvrs.borrarOtrosCertificados(listOtrosCertificadosBorrar);
                listOtrosCertificadosBorrar.clear();
            }
            if (!listOtrosCertificadosCrear.isEmpty()) {
                administrarEmplMvrs.crearOtrosCertificados(listOtrosCertificadosCrear);
                listOtrosCertificadosCrear.clear();
            }
            if (!listOtrosCertificadosModificar.isEmpty()) {
                administrarEmplMvrs.modificarOtrosCertificados(listOtrosCertificadosModificar);
                listOtrosCertificadosModificar.clear();
            }
            listOtrosCertificadosEmpleado = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosOCEmpleado");
            k = 0;
        }
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (banderaMvrs == 1) {
            //CERRAR FILTRADO
            mvrValorAnual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSFechaVigencia");
            mvrValorAnual.setFilterStyle("display: none; visibility: hidden;");
            mvrMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSMotivoCambioSueldo");
            mvrMotivo.setFilterStyle("display: none; visibility: hidden;");
            mvrValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSTipoSueldo");
            mvrValor.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSVigenciaRetroactivo");
            mvrFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSValor");
            mvrFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMvrEmpleado");
            banderaMvrs = 0;
            filtrarListMvrsEmpleado = null;
            tipoListaMvrs = 0;
        }
        if (banderaOC == 1) {

            ocDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocDias");
            ocDias.setFilterStyle("display: none; visibility: hidden;");
            ocCertificado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocCertificado");
            ocCertificado.setFilterStyle("display: none; visibility: hidden;");
            ocEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocEstado");
            ocEstado.setFilterStyle("display: none; visibility: hidden;");
            ocValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocValor");
            ocValor.setFilterStyle("display: none; visibility: hidden;");
            ocFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaFinal");
            ocFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ocFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaInicial");
            ocFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
            RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
            banderaOC = 0;
            filtrarListOtrosCertificados = null;
            tipoListaOtrosCertificados = 0;
        }
        listMvrsBorrar.clear();
        listOtrosCertificadosBorrar.clear();
        listMvrsCrear.clear();
        listOtrosCertificadosCrear.clear();
        listMvrsModificar.clear();
        listOtrosCertificadosModificar.clear();
        indexMvrs = -1;
        secRegistroMvrs = null;
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
        k = 0;
        listMvrsEmpleado = null;
        listOtrosCertificadosEmpleado = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMvrEmpleado");
        context.update("form:datosOCEmpleado");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (indexAuxMvrs >= 0) {
            if (tipoListaMvrs == 0) {
                editarMvrs = listMvrsEmpleado.get(indexMvrs);
            }
            if (tipoListaMvrs == 1) {
                editarMvrs = filtrarListMvrsEmpleado.get(indexMvrs);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaMvrs == 0) {
                context.update("formularioDialogos:editarFechaInicialMVRD");
                context.execute("editarFechaInicialMVRD.show()");
                cualCeldaMvrs = -1;
            } else if (cualCeldaMvrs == 1) {
                context.update("formularioDialogos:editarFechaFinalMVRD");
                context.execute("editarFechaFinalMVRD.show()");
                cualCeldaMvrs = -1;
            } else if (cualCeldaMvrs == 2) {
                context.update("formularioDialogos:editarValorMVRD");
                context.execute("editarValorMVRD.show()");
                cualCeldaMvrs = -1;
            } else if (cualCeldaMvrs == 3) {
                context.update("formularioDialogos:editarMotivoMVRD");
                context.execute("editarMotivoMVRD.show()");
                cualCeldaMvrs = -1;
            } else if (cualCeldaMvrs == 4) {
                context.update("formularioDialogos:editarValorAMVRD");
                context.execute("editarValorAMVRD.show()");
                cualCeldaMvrs = -1;
            }
        }
        if (indexAuxOC >= 0) {
            if (tipoListaOtrosCertificados == 0) {
                editarOtrosCertificados = listOtrosCertificadosEmpleado.get(indexOtrosCertificados);
            }
            if (tipoListaOtrosCertificados == 1) {
                editarOtrosCertificados = filtrarListOtrosCertificados.get(indexOtrosCertificados);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaOtrosCertificados == 0) {
                context.update("formularioDialogos:editarFechaInicialOCD");
                context.execute("editarFechaInicialOCD.show()");
                cualCeldaOtrosCertificados = -1;
            } else if (cualCeldaOtrosCertificados == 1) {
                context.update("formularioDialogos:editarFechaFinalOCD");
                context.execute("editarFechaFinalOCD.show()");
                cualCeldaOtrosCertificados = -1;
            } else if (cualCeldaOtrosCertificados == 2) {
                context.update("formularioDialogos:editarValorOCD");
                context.execute("editarValorOCD.show()");
                cualCeldaOtrosCertificados = -1;
            } else if (cualCeldaOtrosCertificados == 3) {
                context.update("formularioDialogos:editarEstadoOCD");
                context.execute("editarEstadoOCD.show()");
                cualCeldaOtrosCertificados = -1;
            } else if (cualCeldaOtrosCertificados == 4) {
                context.update("formularioDialogos:editarCertificadoOCD");
                context.execute("editarCertificadoOCD.show()");
                cualCeldaOtrosCertificados = -1;
            } else if (cualCeldaOtrosCertificados == 5) {
                context.update("formularioDialogos:editarDiasOCD");
                context.execute("editarDiasOCD.show()");
                cualCeldaOtrosCertificados = -1;
            }
        }
        indexMvrs = -1;
        secRegistroMvrs = null;
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevaMvr() {
        if (banderaMvrs == 1) {
            //CERRAR FILTRADO
            mvrValorAnual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSFechaVigencia");
            mvrValorAnual.setFilterStyle("display: none; visibility: hidden;");
            mvrMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSMotivoCambioSueldo");
            mvrMotivo.setFilterStyle("display: none; visibility: hidden;");
            mvrValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSTipoSueldo");
            mvrValor.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSVigenciaRetroactivo");
            mvrFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSValor");
            mvrFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMvrEmpleado");
            banderaMvrs = 0;
            filtrarListMvrsEmpleado = null;
            tipoListaMvrs = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
        k++;
        BigInteger var = BigInteger.valueOf(k);
        nuevaMvrs.setSecuencia(var);
        nuevaMvrs.setEmpleado(empleado);
        listMvrsCrear.add(nuevaMvrs);

        listMvrsEmpleado.add(nuevaMvrs);
        nuevaMvrs = new Mvrs();
        nuevaMvrs.setMotivo(new Motivosmvrs());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMvrEmpleado");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        context.execute("NuevoRegistroVS.hide()");

        indexMvrs = -1;
        secRegistroMvrs = null;

    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaMvr() {
        nuevaMvrs = new Mvrs();
        nuevaMvrs.setMotivo(new Motivosmvrs());
        indexMvrs = -1;
        secRegistroMvrs = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaOtroC() {
        //CERRAR FILTRADO
        if (banderaOC == 1) {

            ocDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocDias");
            ocDias.setFilterStyle("display: none; visibility: hidden;");
            ocCertificado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocCertificado");
            ocCertificado.setFilterStyle("display: none; visibility: hidden;");
            ocEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocEstado");
            ocEstado.setFilterStyle("display: none; visibility: hidden;");
            ocValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocValor");
            ocValor.setFilterStyle("display: none; visibility: hidden;");
            ocFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaFinal");
            ocFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ocFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaInicial");
            ocFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
            banderaOC = 0;
            filtrarListOtrosCertificados = null;
            tipoListaOtrosCertificados = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS
        k++;
        l = BigInteger.valueOf(k);
        BigDecimal var = BigDecimal.valueOf(k);
        nuevaOtroCertificado.setSecuencia(l);
        nuevaOtroCertificado.setEmpleado(empleado);
        listOtrosCertificadosCrear.add(nuevaOtroCertificado);
        //
        nuevaOtroCertificado = new OtrosCertificados();
        nuevaOtroCertificado.setTipocertificado(new TiposCertificados());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOCEmpleado");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaOtroC() {
        nuevaOtroCertificado = new OtrosCertificados();
        nuevaOtroCertificado.setTipocertificado(new TiposCertificados());
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
    }

    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarRegistro() {
        if (indexMvrs >= 0) {
            duplicarMvr();
        }
        if (indexOtrosCertificados >= 0) {
            duplicarOtroC();
        }
    }

    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarMvr() {
        if (indexMvrs >= 0) {
            duplicarMvrs = new Mvrs();
            k++;
            BigInteger var = BigInteger.valueOf(k);


            if (tipoListaMvrs == 0) {
                duplicarMvrs.setSecuencia(var);
                duplicarMvrs.setEmpleado(listMvrsEmpleado.get(indexMvrs).getEmpleado());
                duplicarMvrs.setValor(listMvrsEmpleado.get(indexMvrs).getValor());
                duplicarMvrs.setFechafinal(listMvrsEmpleado.get(indexMvrs).getFechafinal());
                duplicarMvrs.setFechainicial(listMvrsEmpleado.get(indexMvrs).getFechainicial());
                duplicarMvrs.setValoranualoriginal(listMvrsEmpleado.get(indexMvrs).getValoranualoriginal());
                duplicarMvrs.setMotivo(listMvrsEmpleado.get(indexMvrs).getMotivo());

            }
            if (tipoListaMvrs == 1) {
                duplicarMvrs.setSecuencia(var);
                duplicarMvrs.setEmpleado(filtrarListMvrsEmpleado.get(indexMvrs).getEmpleado());
                duplicarMvrs.setValor(filtrarListMvrsEmpleado.get(indexMvrs).getValor());
                duplicarMvrs.setFechafinal(filtrarListMvrsEmpleado.get(indexMvrs).getFechafinal());
                duplicarMvrs.setFechainicial(filtrarListMvrsEmpleado.get(indexMvrs).getFechainicial());
                duplicarMvrs.setValoranualoriginal(filtrarListMvrsEmpleado.get(indexMvrs).getValoranualoriginal());
                duplicarMvrs.setMotivo(filtrarListMvrsEmpleado.get(indexMvrs).getMotivo());
            }

            if (duplicarMvrs.getMotivo() == null) {
                duplicarMvrs.setMotivo(new Motivosmvrs());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMVRS");
            context.execute("DuplicarRegistroMVRS.show()");
            indexMvrs = -1;
            secRegistroMvrs = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {

        listMvrsEmpleado.add(duplicarMvrs);
        listMvrsCrear.add(duplicarMvrs);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMvrEmpleado");
        indexMvrs = -1;
        secRegistroMvrs = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (banderaMvrs == 1) {
            //CERRAR FILTRADO
            mvrValorAnual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSFechaVigencia");
            mvrValorAnual.setFilterStyle("display: none; visibility: hidden;");
            mvrMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSMotivoCambioSueldo");
            mvrMotivo.setFilterStyle("display: none; visibility: hidden;");
            mvrValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSTipoSueldo");
            mvrValor.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSVigenciaRetroactivo");
            mvrFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSValor");
            mvrFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMvrEmpleado");
            banderaMvrs = 0;
            filtrarListMvrsEmpleado = null;
            tipoListaMvrs = 0;
        }
        context.execute("DuplicarRegistroVS.hide()");
        duplicarMvrs = new Mvrs();

    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia Localizacion
     */
    public void limpiarduplicarMvr() {
        duplicarMvrs = new Mvrs();
        duplicarMvrs.setMotivo(new Motivosmvrs());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarOtroC() {
        if (indexOtrosCertificados >= 0) {
            duplicarOtrosCertificados = new OtrosCertificados();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoListaOtrosCertificados == 0) {
                duplicarOtrosCertificados.setSecuencia(l);
                duplicarOtrosCertificados.setFechafinal(listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getFechainicial());
                duplicarOtrosCertificados.setFechainicial(listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getFechafinal());
                duplicarOtrosCertificados.setValor(listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getValor());
                duplicarOtrosCertificados.setEstado(listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getEstado());
                duplicarOtrosCertificados.setTipocertificado(listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getTipocertificado());
                duplicarOtrosCertificados.setDiascontratados(listOtrosCertificadosEmpleado.get(indexOtrosCertificados).getDiascontratados());

            }
            if (tipoListaOtrosCertificados == 1) {

                duplicarOtrosCertificados.setSecuencia(l);
                duplicarOtrosCertificados.setFechafinal(filtrarListOtrosCertificados.get(indexOtrosCertificados).getFechainicial());
                duplicarOtrosCertificados.setFechainicial(filtrarListOtrosCertificados.get(indexOtrosCertificados).getFechafinal());
                duplicarOtrosCertificados.setValor(filtrarListOtrosCertificados.get(indexOtrosCertificados).getValor());
                duplicarOtrosCertificados.setEstado(filtrarListOtrosCertificados.get(indexOtrosCertificados).getEstado());
                duplicarOtrosCertificados.setTipocertificado(filtrarListOtrosCertificados.get(indexOtrosCertificados).getTipocertificado());
                duplicarOtrosCertificados.setDiascontratados(filtrarListOtrosCertificados.get(indexOtrosCertificados).getDiascontratados());
            }
            if (duplicarOtrosCertificados.getTipocertificado().getSecuencia() == null) {
                duplicarOtrosCertificados.setTipocertificado(new TiposCertificados());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarOC");
            context.execute("DuplicarRegistroOC.show()");
            indexOtrosCertificados = -1;
            secRegistroOtrosCertificados = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarOtroC() {
        listOtrosCertificadosEmpleado.add(duplicarOtrosCertificados);
        listOtrosCertificadosCrear.add(duplicarOtrosCertificados);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOCEmpleado");
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (banderaOC == 1) {
            //CERRAR FILTRADO

            ocDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocDias");
            ocDias.setFilterStyle("display: none; visibility: hidden;");
            ocCertificado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocCertificado");
            ocCertificado.setFilterStyle("display: none; visibility: hidden;");
            ocEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocEstado");
            ocEstado.setFilterStyle("display: none; visibility: hidden;");
            ocValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocValor");
            ocValor.setFilterStyle("display: none; visibility: hidden;");
            ocFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaFinal");
            ocFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ocFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaInicial");
            ocFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
            banderaOC = 0;
            filtrarListOtrosCertificados = null;
            tipoListaOtrosCertificados = 0;
        }
        duplicarOtrosCertificados = new OtrosCertificados();
    }

    /**
     * Limpia los elementos del duplicar registro Vigencia Prorrateo
     */
    public void limpiarduplicarOtroC() {
        duplicarOtrosCertificados = new OtrosCertificados();
        duplicarOtrosCertificados.setTipocertificado(new TiposCertificados());

    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoRegistro() {
        if (indexAuxMvrs >= 0) {
            borrarMvr();
        }
        if (indexAuxOC >= 0) {
            borrarOtroC();
        }
    }

    //BORRAR VL
    /**
     * Metodo que borra una vigencia localizacion
     */
    public void borrarMvr() {
        if (indexMvrs >= 0) {
            if (tipoListaMvrs == 0) {
                if (!listMvrsModificar.isEmpty() && listMvrsModificar.contains(listMvrsEmpleado.get(indexMvrs))) {
                    int modIndex = listMvrsModificar.indexOf(listMvrsEmpleado.get(indexMvrs));
                    listMvrsModificar.remove(modIndex);
                    listMvrsBorrar.add(listMvrsEmpleado.get(indexMvrs));
                } else if (!listMvrsCrear.isEmpty() && listMvrsCrear.contains(listMvrsEmpleado.get(indexMvrs))) {
                    int crearIndex = listMvrsCrear.indexOf(listMvrsEmpleado.get(indexMvrs));
                    listMvrsCrear.remove(crearIndex);
                } else {
                    listMvrsBorrar.add(listMvrsEmpleado.get(indexMvrs));
                }
                listMvrsEmpleado.remove(indexMvrs);
            }
            if (tipoListaMvrs == 1) {
                if (!listMvrsModificar.isEmpty() && listMvrsModificar.contains(filtrarListMvrsEmpleado.get(indexMvrs))) {
                    int modIndex = listMvrsModificar.indexOf(filtrarListMvrsEmpleado.get(indexMvrs));
                    listMvrsModificar.remove(modIndex);
                    listMvrsBorrar.add(filtrarListMvrsEmpleado.get(indexMvrs));
                } else if (!listMvrsCrear.isEmpty() && listMvrsCrear.contains(filtrarListMvrsEmpleado.get(indexMvrs))) {
                    int crearIndex = listMvrsCrear.indexOf(filtrarListMvrsEmpleado.get(indexMvrs));
                    listMvrsCrear.remove(crearIndex);
                } else {
                    listMvrsBorrar.add(filtrarListMvrsEmpleado.get(indexMvrs));
                }
                int VLIndex = listMvrsEmpleado.indexOf(filtrarListMvrsEmpleado.get(indexMvrs));
                listMvrsEmpleado.remove(VLIndex);
                filtrarListMvrsEmpleado.remove(indexMvrs);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMvrEmpleado");

            indexMvrs = -1;
            secRegistroMvrs = null;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarOtroC() {
        if (indexOtrosCertificados >= 0) {
            if (tipoListaOtrosCertificados == 0) {
                if (!listOtrosCertificadosModificar.isEmpty() && listOtrosCertificadosModificar.contains(listOtrosCertificadosEmpleado.get(indexOtrosCertificados))) {
                    int modIndex = listOtrosCertificadosModificar.indexOf(listOtrosCertificadosEmpleado.get(indexOtrosCertificados));
                    listOtrosCertificadosModificar.remove(modIndex);
                    listOtrosCertificadosBorrar.add(listOtrosCertificadosEmpleado.get(indexOtrosCertificados));
                } else if (!listOtrosCertificadosCrear.isEmpty() && listOtrosCertificadosCrear.contains(listOtrosCertificadosEmpleado.get(indexOtrosCertificados))) {
                    int crearIndex = listOtrosCertificadosCrear.indexOf(listOtrosCertificadosEmpleado.get(indexOtrosCertificados));
                    listOtrosCertificadosCrear.remove(crearIndex);
                } else {
                    listOtrosCertificadosBorrar.add(listOtrosCertificadosEmpleado.get(indexOtrosCertificados));
                }
                listOtrosCertificadosEmpleado.remove(indexOtrosCertificados);
            }
            if (tipoListaOtrosCertificados == 1) {
                if (!listOtrosCertificadosModificar.isEmpty() && listOtrosCertificadosModificar.contains(filtrarListOtrosCertificados.get(indexOtrosCertificados))) {
                    int modIndex = listOtrosCertificadosModificar.indexOf(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                    listOtrosCertificadosModificar.remove(modIndex);
                    listOtrosCertificadosBorrar.add(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                } else if (!listOtrosCertificadosCrear.isEmpty() && listOtrosCertificadosCrear.contains(filtrarListOtrosCertificados.get(indexOtrosCertificados))) {
                    int crearIndex = listOtrosCertificadosCrear.indexOf(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                    listOtrosCertificadosCrear.remove(crearIndex);
                } else {
                    listOtrosCertificadosBorrar.add(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                }
                int VPIndex = listOtrosCertificadosEmpleado.indexOf(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                listOtrosCertificadosEmpleado.remove(VPIndex);
                filtrarListOtrosCertificados.remove(indexOtrosCertificados);
            }
            indexMvrs = indexAuxMvrs;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosOCEmpleado");
            indexOtrosCertificados = -1;
            secRegistroOtrosCertificados = null;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        // if (indexMvrs >= 0) {
        filtradoMvr();
        // }
        //  if (indexOtrosCertificados >= 0) {
        filtradoOtroC();
        //  }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoMvr() {
        if (indexMvrs >= 0) {
            if (banderaMvrs == 0) {
                mvrValorAnual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrValorAnual");
                mvrValorAnual.setFilterStyle("width: 60px");
                mvrMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrMotivo");
                mvrMotivo.setFilterStyle("width: 60px");
                mvrValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrValor");
                mvrValor.setFilterStyle("width: 60px");
                mvrFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrFechaFinal");
                mvrFechaFinal.setFilterStyle("width: 60px");
                mvrFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrFechaInicial");
                mvrFechaInicial.setFilterStyle("width: 60px");
                RequestContext.getCurrentInstance().update("form:datosMvrEmpleado");
                banderaMvrs = 1;
            } else if (banderaMvrs == 1) {
                mvrValorAnual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrValorAnual");
                mvrValorAnual.setFilterStyle("display: none; visibility: hidden;");
                mvrMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrMotivo");
                mvrMotivo.setFilterStyle("display: none; visibility: hidden;");
                mvrValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrValor");
                mvrValor.setFilterStyle("display: none; visibility: hidden;");
                mvrFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrFechaFinal");
                mvrFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                mvrFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:mvrFechaInicial");
                mvrFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMvrEmpleado");
                banderaMvrs = 0;
                filtrarListMvrsEmpleado = null;
                tipoListaMvrs = 0;
            }
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoOtroC() {
        if (indexOtrosCertificados >= 0) {
            if (banderaOC == 0) {
                //Columnas Tabla VPP
                ocDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocDias");
                ocDias.setFilterStyle("width: 60px");
                ocCertificado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocCertificado");
                ocCertificado.setFilterStyle("width: 60px");
                ocEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocEstado");
                ocEstado.setFilterStyle("width: 60px");
                ocValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocValor");
                ocValor.setFilterStyle("width: 60px");
                ocFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaFinal");
                ocFechaFinal.setFilterStyle("width: 60px");
                ocFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaInicial");
                ocFechaInicial.setFilterStyle("width: 60px");
                RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
                banderaOC = 1;
            } else if (banderaOC == 1) {

                ocDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocDias");
                ocDias.setFilterStyle("display: none; visibility: hidden;");
                ocCertificado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocCertificado");
                ocCertificado.setFilterStyle("display: none; visibility: hidden;");
                ocEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocEstado");
                ocEstado.setFilterStyle("display: none; visibility: hidden;");
                ocValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocValor");
                ocValor.setFilterStyle("display: none; visibility: hidden;");
                ocFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaFinal");
                ocFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                ocFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaInicial");
                ocFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
                banderaOC = 0;
                filtrarListOtrosCertificados = null;
                tipoListaOtrosCertificados = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaMvrs == 1) {
            mvrValorAnual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSFechaVigencia");
            mvrValorAnual.setFilterStyle("display: none; visibility: hidden;");
            mvrMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSMotivoCambioSueldo");
            mvrMotivo.setFilterStyle("display: none; visibility: hidden;");
            mvrValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSTipoSueldo");
            mvrValor.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSVigenciaRetroactivo");
            mvrFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            mvrFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMvrEmpleado:vSValor");
            mvrFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMvrEmpleado");
            banderaMvrs = 0;
            filtrarListMvrsEmpleado = null;
            tipoListaMvrs = 0;
        }
        if (banderaOC == 1) {

            ocDias = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocDias");
            ocDias.setFilterStyle("display: none; visibility: hidden;");
            ocCertificado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocCertificado");
            ocCertificado.setFilterStyle("display: none; visibility: hidden;");
            ocEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocEstado");
            ocEstado.setFilterStyle("display: none; visibility: hidden;");
            ocValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocValor");
            ocValor.setFilterStyle("display: none; visibility: hidden;");
            ocFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaFinal");
            ocFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ocFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOCEmpleado:ocFechaInicial");
            ocFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOCEmpleado");
            banderaOC = 0;
            filtrarListOtrosCertificados = null;
            tipoListaOtrosCertificados = 0;
        }

        listMvrsBorrar.clear();
        listMvrsCrear.clear();
        listMvrsModificar.clear();
        listOtrosCertificadosBorrar.clear();
        listOtrosCertificadosCrear.clear();;
        listOtrosCertificadosModificar.clear();
        indexMvrs = -1;
        secRegistroMvrs = null;
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
        k = 0;
        listMvrsEmpleado = null;
        listOtrosCertificadosEmpleado = null;
        guardado = true;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     * @param tt Tipo Tabla : VigenciaLocalizacion / VigenciaProrrateo /
     * VigenciaProrrateoProyecto
     */
    public void asignarIndex(Integer indice, int dlg, int LND, int tt) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tt == 0) {
            if (LND == 0) {
                indexMvrs = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
            }
        }
        if (tt == 1) {
            if (LND == 0) {
                indexOtrosCertificados = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:CertificadosDialogo");
                context.execute("CertificadosDialogo.show()");
            }
        }
    }

    //LOVS
    //Estructuras
    /**
     * Metodo que actualiza la estructura seleccionada (vigencia localizacion)
     */
    public void actualizarMotivo() {
        if (tipoActualizacion == 0) {
            if (tipoListaMvrs == 0) {
                listMvrsEmpleado.get(indexMvrs).setMotivo(motivoMvrSeleccionado);
                if (!listMvrsCrear.contains(listMvrsEmpleado.get(indexMvrs))) {
                    if (listMvrsModificar.isEmpty()) {
                        listMvrsModificar.add(listMvrsEmpleado.get(indexMvrs));
                    } else if (!listMvrsModificar.contains(listMvrsEmpleado.get(indexMvrs))) {
                        listMvrsModificar.add(listMvrsEmpleado.get(indexMvrs));
                    }
                }
            } else {
                filtrarListMvrsEmpleado.get(indexMvrs).setMotivo(motivoMvrSeleccionado);
                if (!listMvrsCrear.contains(filtrarListMvrsEmpleado.get(indexMvrs))) {
                    if (listMvrsModificar.isEmpty()) {
                        listMvrsModificar.add(filtrarListMvrsEmpleado.get(indexMvrs));
                    } else if (!listMvrsModificar.contains(filtrarListMvrsEmpleado.get(indexMvrs))) {
                        listMvrsModificar.add(filtrarListMvrsEmpleado.get(indexMvrs));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexMvrs = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:datosMvrEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaMvrs.setMotivo(motivoMvrSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaMotivoMVRS");
        } else if (tipoActualizacion == 2) {
            duplicarMvrs.setMotivo(motivoMvrSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivoMVRS");
        }
        filtrarListMotivosMvrs = null;
        motivoMvrSeleccionado = null;
        aceptar = true;
        indexMvrs = -1;
        secRegistroMvrs = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre estructura (vigencia localizacion)
     */
    public void cancelarCambioMotivo() {
        filtrarListMotivosMvrs = null;
        motivoMvrSeleccionado = null;
        aceptar = true;
        indexMvrs = -1;
        secRegistroMvrs = null;
        tipoActualizacion = -1;
        permitirIndexMvrs = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el proyecto seleccionado (vigencia localizacion)
     */
    public void actualizarTipo() {
        if (tipoActualizacion == 0) {
            if (tipoListaMvrs == 0) {
                listOtrosCertificadosEmpleado.get(indexOtrosCertificados).setTipocertificado(tipoCertificadoSeleccionado);
                if (!listOtrosCertificadosCrear.contains(listOtrosCertificadosEmpleado.get(indexOtrosCertificados))) {
                    if (listOtrosCertificadosModificar.isEmpty()) {
                        listOtrosCertificadosModificar.add(listOtrosCertificadosEmpleado.get(indexOtrosCertificados));
                    } else if (!listOtrosCertificadosModificar.contains(listOtrosCertificadosEmpleado.get(indexOtrosCertificados))) {
                        listOtrosCertificadosModificar.add(listOtrosCertificadosEmpleado.get(indexOtrosCertificados));
                    }
                }
            } else {
                filtrarListOtrosCertificados.get(indexOtrosCertificados).setTipocertificado(tipoCertificadoSeleccionado);
                if (!listOtrosCertificadosCrear.contains(filtrarListOtrosCertificados.get(indexOtrosCertificados))) {
                    if (listOtrosCertificadosModificar.isEmpty()) {
                        listOtrosCertificadosModificar.add(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                    } else if (!listOtrosCertificadosModificar.contains(filtrarListOtrosCertificados.get(indexOtrosCertificados))) {
                        listOtrosCertificadosModificar.add(filtrarListOtrosCertificados.get(indexOtrosCertificados));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexOtrosCertificados = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:datosOCEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaOtroCertificado.setTipocertificado(tipoCertificadoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaCertificadoOC");
        } else if (tipoActualizacion == 2) {
            duplicarOtrosCertificados.setTipocertificado(tipoCertificadoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCertificadoOC");
        }
        filtrarListTiposCertificados = null;
        tipoCertificadoSeleccionado = null;
        aceptar = true;
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioTipo() {
        filtrarListTiposCertificados = null;
        tipoCertificadoSeleccionado = null;
        aceptar = true;
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
        tipoActualizacion = -1;
        permitirIndexOtrosCertificados = true;
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de todas las tablas con respecto al
     * index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexAuxMvrs >= 0) {
            if (cualCeldaMvrs == 3) {
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = 0;
            }
            indexOtrosCertificados = -1;
        }
        if (indexAuxOC >= 0) {
            if (cualCeldaOtrosCertificados == 4) {
                context.update("form:CertificadosDialogo");
                context.execute("CertificadosDialogo.show()");
                tipoActualizacion = 0;
            }
            indexMvrs = -1;
        }

    }

    /**
     * Valida un proceso de nuevo registro dentro de la pagina con respecto a la
     * posicion en la pagina
     */
    public void validarNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listMvrsEmpleado.isEmpty() || listOtrosCertificadosEmpleado.isEmpty()) {
            System.out.println("Entro");
            context.execute("NuevoRegistroPagina.show()");
        } else {
            if (indexMvrs >= 0) {
                context.update("form:NuevoRegistroMVRS");
                context.execute("NuevoRegistroMVRS.show()");
            }
            if (indexOtrosCertificados >= 0) {
                context.update("form:NuevoRegistroOC");
                context.execute("NuevoRegistroOC.show()");
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
     * Selecciona la tabla a exportar XML con respecto al index activo
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        if (indexAuxMvrs >= 0) {
            nombreTabla = ":formExportarMVR:datosMvrEmpleadoExportar";
            nombreXML = "M.V.R.XML";
        }
        if (indexAuxOC >= 0) {
            nombreTabla = ":formExportarOC:datosOCEmpleadoExportar";
            nombreXML = "OtrosCertificadosXML";
        }
        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (indexAuxMvrs >= 0) {
            exportPDF_MVR();
        }
        if (indexAuxOC >= 0) {
            exportPDF_OC();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_MVR() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarMVR:datosMvrEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "M.V.R.PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexMvrs = -1;
        secRegistroMvrs = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_OC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarOC:datosOCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "OtrosCertificadosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (indexAuxMvrs >= 0) {
            exportXLS_MVR();
        }
        if (indexAuxOC >= 0) {
            exportXLS_OC();
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_MVR() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarMVR:datosMvrEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "M.V.R.XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexMvrs = -1;
        secRegistroMvrs = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_OC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarOC:datosOCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "OtrosCertificadosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexOtrosCertificados = -1;
        secRegistroOtrosCertificados = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (indexAuxMvrs >= 0) {
            if (tipoListaMvrs == 0) {
                tipoListaMvrs = 1;
            }
        }
        if (indexAuxOC >= 0) {
            if (tipoListaOtrosCertificados == 0) {
                tipoListaOtrosCertificados = 1;
            }
        }
    }

    //METODO RASTROS PARA LAS TABLAS EN EMPLVIGENCIASUELDOS
    public void verificarRastroTabla() {
        if (listOtrosCertificadosEmpleado == null || listMvrsEmpleado == null) {
            //Dialogo para seleccionar el rato de la tabla deseada
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        }

        if ((listOtrosCertificadosEmpleado != null) && (listMvrsEmpleado != null)) {
            if (indexMvrs >= 0) {
                verificarRastroMvr();
                indexMvrs = -1;
            }
            if (indexOtrosCertificados >= 0) {
                //Metodo Rastro Vigencias Afiliaciones
                verificarRastroOC();
                indexOtrosCertificados = -1;
            }
        }
    }
    //Verificar Rastro Vigencia Sueldos

    public void verificarRastroMvr() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listMvrsEmpleado != null) {
            if (secRegistroMvrs != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroMvrs, "MVRS");
                backUpSecRegistroMvrs = secRegistroMvrs;
                backUp = secRegistroMvrs;
                secRegistroMvrs = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Mvrs";
                    msnConfirmarRastro = "La tabla MVRS tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("MVRS")) {
                nombreTablaRastro = "Mvrs";
                msnConfirmarRastroHistorico = "La tabla MVRS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexMvrs = -1;
    }

    //Verificar Rastro Vigencia Sueldos
    public void verificarRastroOC() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listOtrosCertificadosEmpleado != null) {
            if (secRegistroOtrosCertificados != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroOtrosCertificados, "OTROSCERTIFICADOS");
                backUpSecRegistroOtrosCertificados = secRegistroOtrosCertificados;
                backUp = backUpSecRegistroOtrosCertificados;
                secRegistroOtrosCertificados = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "OtrosCertificados";
                    msnConfirmarRastro = "La tabla OTROSCERTIFICADOS tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("OTROSCERTIFICADOS")) {
                nombreTablaRastro = "OtrosCertificados";
                msnConfirmarRastroHistorico = "La tabla OTROSCERTIFICADOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexOtrosCertificados = -1;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public List<Mvrs> getListMvrsEmpleado() {
        try {
            if (listMvrsEmpleado == null) {
                listMvrsEmpleado = administrarEmplMvrs.listMvrsEmpleado(empleado.getSecuencia());
            }
            return listMvrsEmpleado;
        } catch (Exception e) {
            System.out.println("Error getListMvrsEmpleado : " + e.toString());
            return null;
        }
    }

    public void setListMvrsEmpleado(List<Mvrs> listMvrsEmpleado) {
        this.listMvrsEmpleado = listMvrsEmpleado;
    }

    public List<Mvrs> getFiltrarListMvrsEmpleado() {
        return filtrarListMvrsEmpleado;
    }

    public void setFiltrarListMvrsEmpleado(List<Mvrs> filtrarListMvrsEmpleado) {
        this.filtrarListMvrsEmpleado = filtrarListMvrsEmpleado;
    }

    public List<OtrosCertificados> getListOtrosCertificadosEmpleado() {
        try {
            if (listOtrosCertificadosEmpleado == null) {
                listOtrosCertificadosEmpleado = administrarEmplMvrs.listOtrosCertificadosEmpleado(empleado.getSecuencia());
            }
            return listOtrosCertificadosEmpleado;
        } catch (Exception e) {
            System.out.println("Error getListOtrosCertificadosEmpleado : " + e.toString());
            return null;
        }
    }

    public void setListOtrosCertificadosEmpleado(List<OtrosCertificados> listOtrosCertificadosEmpleado) {
        this.listOtrosCertificadosEmpleado = listOtrosCertificadosEmpleado;
    }

    public List<OtrosCertificados> getFiltrarListOtrosCertificados() {
        return filtrarListOtrosCertificados;
    }

    public void setFiltrarListOtrosCertificados(List<OtrosCertificados> filtrarListOtrosCertificados) {
        this.filtrarListOtrosCertificados = filtrarListOtrosCertificados;
    }

    public List<Motivosmvrs> getListMotivosMvrs() {
        if (listMotivosMvrs == null) {
            listMotivosMvrs = administrarEmplMvrs.listMotivos();
        }
        return listMotivosMvrs;
    }

    public void setListMotivosMvrs(List<Motivosmvrs> listMotivosMvrs) {
        this.listMotivosMvrs = listMotivosMvrs;
    }

    public Motivosmvrs getMotivoMvrSeleccionado() {
        return motivoMvrSeleccionado;
    }

    public void setMotivoMvrSeleccionado(Motivosmvrs motivoMvrSeleccionado) {
        this.motivoMvrSeleccionado = motivoMvrSeleccionado;
    }

    public List<Motivosmvrs> getFiltrarListMotivosMvrs() {
        return filtrarListMotivosMvrs;
    }

    public void setFiltrarListMotivosMvrs(List<Motivosmvrs> filtrarListMotivosMvrs) {
        this.filtrarListMotivosMvrs = filtrarListMotivosMvrs;
    }

    public List<TiposCertificados> getListTiposCertificados() {
        if (listTiposCertificados == null) {
            listTiposCertificados = administrarEmplMvrs.listTiposCertificados();
        }
        return listTiposCertificados;
    }

    public void setListTiposCertificados(List<TiposCertificados> listTiposCertificados) {
        this.listTiposCertificados = listTiposCertificados;
    }

    public TiposCertificados getTipoCertificadoSeleccionado() {
        return tipoCertificadoSeleccionado;
    }

    public void setTipoCertificadoSeleccionado(TiposCertificados tipoCertificadoSeleccionado) {
        this.tipoCertificadoSeleccionado = tipoCertificadoSeleccionado;
    }

    public List<TiposCertificados> getFiltrarListTiposCertificados() {
        return filtrarListTiposCertificados;
    }

    public void setFiltrarListTiposCertificados(List<TiposCertificados> filtrarListTiposCertificados) {
        this.filtrarListTiposCertificados = filtrarListTiposCertificados;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Mvrs> getListMvrsModificar() {
        return listMvrsModificar;
    }

    public void setListMvrsModificar(List<Mvrs> listMvrsModificar) {
        this.listMvrsModificar = listMvrsModificar;
    }

    public List<OtrosCertificados> getListOtrosCertificadosModificar() {
        return listOtrosCertificadosModificar;
    }

    public void setListOtrosCertificadosModificar(List<OtrosCertificados> listOtrosCertificadosModificar) {
        this.listOtrosCertificadosModificar = listOtrosCertificadosModificar;
    }

    public Mvrs getNuevaMvrs() {
        return nuevaMvrs;
    }

    public void setNuevaMvrs(Mvrs nuevaMvrs) {
        this.nuevaMvrs = nuevaMvrs;
    }

    public OtrosCertificados getNuevaOtroCertificado() {
        return nuevaOtroCertificado;
    }

    public void setNuevaOtroCertificado(OtrosCertificados nuevaOtroCertificado) {
        this.nuevaOtroCertificado = nuevaOtroCertificado;
    }

    public List<Mvrs> getListMvrsCrear() {
        return listMvrsCrear;
    }

    public void setListMvrsCrear(List<Mvrs> listMvrsCrear) {
        this.listMvrsCrear = listMvrsCrear;
    }

    public List<Mvrs> getListMvrsBorrar() {
        return listMvrsBorrar;
    }

    public void setListMvrsBorrar(List<Mvrs> listMvrsBorrar) {
        this.listMvrsBorrar = listMvrsBorrar;
    }

    public List<OtrosCertificados> getListOtrosCertificadosBorrar() {
        return listOtrosCertificadosBorrar;
    }

    public void setListOtrosCertificadosBorrar(List<OtrosCertificados> listOtrosCertificadosBorrar) {
        this.listOtrosCertificadosBorrar = listOtrosCertificadosBorrar;
    }

    public Mvrs getEditarMvrs() {
        return editarMvrs;
    }

    public void setEditarMvrs(Mvrs editarMvrs) {
        this.editarMvrs = editarMvrs;
    }

    public OtrosCertificados getEditarOtrosCertificados() {
        return editarOtrosCertificados;
    }

    public void setEditarOtrosCertificados(OtrosCertificados editarOtrosCertificados) {
        this.editarOtrosCertificados = editarOtrosCertificados;
    }

    public Mvrs getDuplicarMvrs() {
        return duplicarMvrs;
    }

    public void setDuplicarMvrs(Mvrs duplicarMvrs) {
        this.duplicarMvrs = duplicarMvrs;
    }

    public OtrosCertificados getDuplicarOtrosCertificados() {
        return duplicarOtrosCertificados;
    }

    public void setDuplicarOtrosCertificados(OtrosCertificados duplicarOtrosCertificados) {
        this.duplicarOtrosCertificados = duplicarOtrosCertificados;
    }

    public List<OtrosCertificados> getListOtrosCertificadosCrear() {
        return listOtrosCertificadosCrear;
    }

    public void setListOtrosCertificadosCrear(List<OtrosCertificados> listOtrosCertificadosCrear) {
        this.listOtrosCertificadosCrear = listOtrosCertificadosCrear;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getMsnConfirmarRastro() {
        return msnConfirmarRastro;
    }

    public void setMsnConfirmarRastro(String msnConfirmarRastro) {
        this.msnConfirmarRastro = msnConfirmarRastro;
    }

    public String getMsnConfirmarRastroHistorico() {
        return msnConfirmarRastroHistorico;
    }

    public void setMsnConfirmarRastroHistorico(String msnConfirmarRastroHistorico) {
        this.msnConfirmarRastroHistorico = msnConfirmarRastroHistorico;
    }

    public BigInteger getBackUp() {
        return backUp;
    }

    public void setBackUp(BigInteger backUp) {
        this.backUp = backUp;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }
}

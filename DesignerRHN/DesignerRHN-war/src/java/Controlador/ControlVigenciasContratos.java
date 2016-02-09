package Controlador;

import Entidades.Contratos;
import Entidades.Empleados;
import Entidades.TiposContratos;
import Entidades.VigenciasContratos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasContratosInterface;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlVigenciasContratos implements Serializable {

    @EJB
    AdministrarVigenciasContratosInterface administrarVigenciasContratos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Contratos
    private List<VigenciasContratos> vigenciasContratos;
    private List<VigenciasContratos> filtrarVC;
    private VigenciasContratos vigenciaSeleccionada;
    //Contratos
    private List<Contratos> listaContratos;
    private Contratos contratoSelecionado;
    private List<Contratos> filtradoContratos;
    //Tipos Contratos
    private List<TiposContratos> listaTiposContratos;
    private TiposContratos tipoContratoSelecionado;
    private List<TiposContratos> filtradoTiposContratos;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vcFechaInicial, vcFechaFinal, vcContrato, vcTipoContrato;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasContratos> listVCModificar;
    private boolean guardado;
    //private boolean guardarOk;
    //crear VC
    public VigenciasContratos nuevaVigencia;
    private List<VigenciasContratos> listVCCrear;
    private BigInteger nuevaVContratoSecuencia;
    private int paraNuevaVContrato;
    //borrar VC
    private List<VigenciasContratos> listVCBorrar;
    //editar celda
    private VigenciasContratos editarVC;
    private int cualCelda, tipoLista;
    //private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasContratos duplicarVC;
    //String Variables AutoCompletar
    private String tipoContrato, legislacionLaboral;
    //Boolean AutoCompletar
    private boolean permitirIndex;
    private BigInteger backUpSecRegistro;
    private BigInteger secRegistro;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;
    private String altoTabla;
    public String infoRegistro;
    //
    private String infoRegistroContrato, infoRegistroTipoContrato;

    public ControlVigenciasContratos() {
        secRegistro = null;
        backUpSecRegistro = null;
        vigenciasContratos = null;
        listaContratos = new ArrayList<Contratos>();
        listaTiposContratos = new ArrayList<TiposContratos>();
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVCBorrar = new ArrayList<VigenciasContratos>();
        //crear aficiones
        listVCCrear = new ArrayList<VigenciasContratos>();
        paraNuevaVContrato = 0;
        //modificar aficiones
        listVCModificar = new ArrayList<VigenciasContratos>();
        //editar
        editarVC = new VigenciasContratos();
        //cambioEditor = false;
        //aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar 
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasContratos();
        nuevaVigencia.setContrato(new Contratos());
        nuevaVigencia.setTipocontrato(new TiposContratos());
        permitirIndex = true;
        altoTabla = "270";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasContratos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
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
    public void recibirEmpleado(Empleados empl) {
        RequestContext context = RequestContext.getCurrentInstance();

        vigenciasContratos = null;
        empleado = empl;

        listaContratos = null;
        listaTiposContratos = null;

        vigenciasContratos = null;
        getVigenciasContratosEmpleado();
        //INICIALIZAR BOTONES NAVEGACION
        if (vigenciasContratos != null && !vigenciasContratos.isEmpty()) {
            System.out.println("Entra al primer IF");
            if (vigenciasContratos.size() == 1) {
                System.out.println("Segundo IF");
                //INFORMACION REGISTRO
                vigenciaSeleccionada = vigenciasContratos.get(0);
                //infoRegistro = "Registro 1 de 1";
                infoRegistro = "Cantidad de registros: 1";
            } else if (vigenciasContratos.size() > 1) {
                System.out.println("Else If");
                //INFORMACION REGISTRO
                vigenciaSeleccionada = vigenciasContratos.get(0);
                //infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
                infoRegistro = "Cantidad de registros: " + vigenciasContratos.size();
            }

        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        context.update("form:informacionRegistro");
    }

    public void modificarVC(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {// Si NO tiene Filtro
            if (!listVCCrear.contains(vigenciasContratos.get(indice))) {

                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciasContratos.get(indice));
                } else if (!listVCModificar.contains(vigenciasContratos.get(indice))) {
                    listVCModificar.add(vigenciasContratos.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        }
        if (tipoLista == 1) {// Si tiene Filtro
            if (!listVCCrear.contains(filtrarVC.get(indice))) {

                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(filtrarVC.get(indice));
                } else if (!listVCModificar.contains(filtrarVC.get(indice))) {
                    listVCModificar.add(filtrarVC.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            VigenciasContratos auxiliar = null;
            if (tipoLista == 0) {// Si NO tiene Filtro
                auxiliar = vigenciasContratos.get(index);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                auxiliar = filtrarVC.get(index);
            }
            if (auxiliar.getFechafinal() != null) {
                if (auxiliar.getFechainicial().after(fechaParametro) && auxiliar.getFechainicial().before(auxiliar.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (auxiliar.getFechafinal() == null) {
                if (auxiliar.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevaVigencia.getFechafinal() != null) {
                if (nuevaVigencia.getFechainicial().after(fechaParametro) && nuevaVigencia.getFechainicial().before(nuevaVigencia.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaVigencia.getFechafinal() == null) {
                if (nuevaVigencia.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVC.getFechafinal() != null) {
                if (duplicarVC.getFechainicial().after(fechaParametro) && duplicarVC.getFechainicial().before(duplicarVC.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarVC.getFechafinal() == null) {
                if (duplicarVC.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        VigenciasContratos auxiliar = null;
        if (tipoLista == 0) {// Si NO tiene Filtro
            auxiliar = vigenciasContratos.get(index);
        }
        if (tipoLista == 1) {// Si tiene Filtro
            auxiliar = filtrarVC.get(index);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistro(0);
            if (retorno) {
                cambiarIndice(i, c);
                modificarVC(i);
            } else {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciasContratos.get(i).setFechafinal(fechaFin);
                    vigenciasContratos.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    filtrarVC.get(i).setFechafinal(fechaFin);
                    filtrarVC.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVCEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciasContratos.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                filtrarVC.get(i).setFechainicial(fechaIni);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVCEmpleado");
            context.execute("errorRegNew.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciasContratos
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVC(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("LEGISLACIONLABORAL")) {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciasContratos.get(indice).getContrato().setDescripcion(legislacionLaboral);
            } else {// Si tiene Filtro
                filtrarVC.get(indice).getContrato().setDescripcion(legislacionLaboral);
            }
            for (int i = 0; i < listaContratos.size(); i++) {
                if (listaContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciasContratos.get(indice).setContrato(listaContratos.get(indiceUnicoElemento));
                } else {// Si tiene Filtro
                    filtrarVC.get(indice).setContrato(listaContratos.get(indiceUnicoElemento));
                }
                listaContratos.clear();
                getListaContratos();
            } else {
                permitirIndex = false;
                getInfoRegistroContrato();
                context.update("form:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOCONTRATO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciasContratos.get(indice).getTipocontrato().setNombre(tipoContrato);
                } else {// Si tiene Filtro
                    filtrarVC.get(indice).getTipocontrato().setNombre(tipoContrato);
                }
                for (int i = 0; i < listaTiposContratos.size(); i++) {
                    if (listaTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {// Si NO tiene Filtro
                        vigenciasContratos.get(indice).setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                    } else {// Si tiene Filtro
                        filtrarVC.get(indice).setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                    }
                    listaTiposContratos.clear();
                    getListaTiposContratos();
                } else {
                    permitirIndex = false;
                    getInfoRegistroTipoContrato();
                    context.update("form:TiposContratoDialogo");
                    context.execute("TiposContratoDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                listaTiposContratos.clear();
                getListaTiposContratos();
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciasContratos.get(indice).setTipocontrato(new TiposContratos());
                } else {// Si tiene Filtro
                    filtrarVC.get(indice).setTipocontrato(new TiposContratos());
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {// Si NO tiene Filtro
                if (!listVCCrear.contains(vigenciasContratos.get(indice))) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasContratos.get(indice));
                    } else if (!listVCModificar.contains(vigenciasContratos.get(indice))) {
                        listVCModificar.add(vigenciasContratos.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            } else {// Si tiene Filtro
                if (!listVCCrear.contains(filtrarVC.get(indice))) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filtrarVC.get(indice));
                    } else if (!listVCModificar.contains(filtrarVC.get(indice))) {
                        listVCModificar.add(filtrarVC.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVCEmpleado");
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("LEGISLACIONLABORAL")) {
            if (tipoNuevo == 1) {
                legislacionLaboral = nuevaVigencia.getContrato().getDescripcion();
            } else if (tipoNuevo == 2) {
                legislacionLaboral = duplicarVC.getContrato().getDescripcion();
            }
        } else if (Campo.equals("TIPOCONTRATO")) {
            if (tipoNuevo == 1) {
                tipoContrato = nuevaVigencia.getTipocontrato().getNombre();
            } else if (tipoNuevo == 2) {
                tipoContrato = duplicarVC.getTipocontrato().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("LEGISLACIONLABORAL")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getContrato().setDescripcion(tipoContrato);
            } else if (tipoNuevo == 2) {
                duplicarVC.getContrato().setDescripcion(tipoContrato);
            }
            for (int i = 0; i < listaContratos.size(); i++) {
                if (listaContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setContrato(listaContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCentroCosto");
                } else if (tipoNuevo == 2) {
                    duplicarVC.setContrato(listaContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCentroCosto");
                }
                listaContratos.clear();
                getListaContratos();
            } else {
                context.update("form:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaLegislacionLaboral");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarLegislacionLaboral");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOCONTRATO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.getTipocontrato().setNombre(tipoContrato);
                } else if (tipoNuevo == 2) {
                    duplicarVC.getTipocontrato().setNombre(tipoContrato);
                }
                for (int i = 0; i < listaTiposContratos.size(); i++) {
                    if (listaTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevoTipoContrato");
                    } else if (tipoNuevo == 2) {
                        duplicarVC.setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarTipoContrato");
                    }
                    listaTiposContratos.clear();
                    getListaTiposContratos();
                } else {
                    context.update("form:TiposContratoDialogo");
                    context.execute("TiposContratoDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevoTipoContrato");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarTipoContrato");
                    }
                }
            }
        } else {
            listaTiposContratos.clear();
            getListaTiposContratos();
            if (tipoNuevo == 1) {
                nuevaVigencia.setTipocontrato(new TiposContratos());
                context.update("formularioDialogos:nuevoTipoContrato");
            } else if (tipoNuevo == 2) {
                duplicarVC.setTipocontrato(new TiposContratos());
                context.update("formularioDialogos:duplicarTipoContrato");
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla Vigencias Contratos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {// Si NO tiene Filtro
                fechaIni = vigenciasContratos.get(index).getFechainicial();
                fechaFin = vigenciasContratos.get(index).getFechafinal();
                secRegistro = vigenciasContratos.get(index).getSecuencia();
                if (cualCelda == 2) {
                    legislacionLaboral = vigenciasContratos.get(index).getContrato().getDescripcion();
                } else if (cualCelda == 3) {
                    tipoContrato = vigenciasContratos.get(index).getTipocontrato().getNombre();
                }
            }
            if (tipoLista == 1) {// Si tiene Filtro
                fechaIni = filtrarVC.get(index).getFechainicial();
                fechaFin = filtrarVC.get(index).getFechafinal();
                secRegistro = filtrarVC.get(index).getSecuencia();
                if (cualCelda == 2) {
                    legislacionLaboral = filtrarVC.get(index).getContrato().getDescripcion();
                } else if (cualCelda == 3) {
                    tipoContrato = filtrarVC.get(index).getTipocontrato().getNombre();
                }
            }
        }
    }

    //GUARDAR
    /**
     * Metodo que guarda los cambios efectuados en la pagina Vigencias Contratos
     */
    public void guardarCambiosVC() {
        if (guardado == false) {
            if (!listVCBorrar.isEmpty()) {
                for (int i = 0; i < listVCBorrar.size(); i++) {
                    administrarVigenciasContratos.borrarVC(listVCBorrar.get(i));
                }
                listVCBorrar.clear();
            }
            if (!listVCCrear.isEmpty()) {
                for (int i = 0; i < listVCCrear.size(); i++) {
                    administrarVigenciasContratos.crearVC(listVCCrear.get(i));
                }
                listVCCrear.clear();
            }
            if (!listVCModificar.isEmpty()) {
                administrarVigenciasContratos.modificarVC(listVCModificar);
                listVCModificar.clear();
            }
            RequestContext context = RequestContext.getCurrentInstance();
            vigenciasContratos = null;
            getVigenciasContratosEmpleado();
            if (vigenciasContratos != null && !vigenciasContratos.isEmpty()) {
                vigenciaSeleccionada = vigenciasContratos.get(0);
                infoRegistro = "Cantidad de registros: " + vigenciasContratos.size();
            } else {
                infoRegistro = "Cantidad de registros: 0";
            }
            context.update("form:datosVCEmpleado");
            context.update("form:informacionRegistro");
            guardado = true;
            context.update("form:ACEPTAR");
            paraNuevaVContrato = 0;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
        secRegistro = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            vcFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filtrarVC = null;
            tipoLista = 0;
        }

        listVCBorrar.clear();
        listVCCrear.clear();
        listVCModificar.clear();
        index = -1;
        secRegistro = null;
        paraNuevaVContrato = 0;
        vigenciasContratos = null;
        getVigenciasContratosEmpleado();
        if (vigenciasContratos != null && !vigenciasContratos.isEmpty()) {
            vigenciaSeleccionada = vigenciasContratos.get(0);
            infoRegistro = "Cantidad de registros: " + vigenciasContratos.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }

        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVCEmpleado");
        context.update("form:informacionRegistro");

    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (tipoLista == 0) {// Si NO tiene Filtro
                editarVC = vigenciasContratos.get(index);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                editarVC = filtrarVC.get(index);
            }
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicial");
                context.execute("editarFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinal");
                context.execute("editarFechaFinal.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarContrato");
                context.execute("editarContrato.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarTipoContrato");
                context.execute("editarTipoContrato.show()");
                cualCelda = -1;
            }
            index = -1;
            secRegistro = null;
        }
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciasContratos
     */
    public void agregarNuevaVC() {
        if (nuevaVigencia.getFechainicial() != null && nuevaVigencia.getContrato().getSecuencia() != null) {
            if (validarFechasRegistro(1)) {

                if (bandera == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vcFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
                    vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vcFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
                    vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vcContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
                    vcContrato.setFilterStyle("display: none; visibility: hidden;");
                    vcTipoContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
                    vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = "270";
                    RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
                    bandera = 0;
                    filtrarVC = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
                paraNuevaVContrato++;
                nuevaVContratoSecuencia = BigInteger.valueOf(paraNuevaVContrato);
                nuevaVigencia.setSecuencia(nuevaVContratoSecuencia);
                nuevaVigencia.setEmpleado(empleado);
                listVCCrear.add(nuevaVigencia);

                vigenciasContratos.add(nuevaVigencia);

                nuevaVigencia = new VigenciasContratos();
                nuevaVigencia.setContrato(new Contratos());
                nuevaVigencia.setTipocontrato(new TiposContratos());
                RequestContext context = RequestContext.getCurrentInstance();
                infoRegistro = "Cantidad de registros: " + vigenciasContratos.size();
                context.update("form:informacionRegistro");
                context.update("form:datosVCEmpleado");
                context.execute("NuevoRegistroVC.hide()");
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVC() {
        nuevaVigencia = new VigenciasContratos();
        nuevaVigencia.setContrato(new Contratos());
        nuevaVigencia.setTipocontrato(new TiposContratos());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVigenciaC() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            duplicarVC = new VigenciasContratos();

            if (tipoLista == 0) {// Si NO tiene Filtro

                duplicarVC.setEmpleado(vigenciasContratos.get(index).getEmpleado());
                duplicarVC.setFechainicial(vigenciasContratos.get(index).getFechainicial());
                duplicarVC.setFechafinal(vigenciasContratos.get(index).getFechafinal());
                duplicarVC.setContrato(vigenciasContratos.get(index).getContrato());
                duplicarVC.setTipocontrato(vigenciasContratos.get(index).getTipocontrato());
            }
            if (tipoLista == 1) {// Si tiene Filtro
                duplicarVC.setEmpleado(filtrarVC.get(index).getEmpleado());
                duplicarVC.setFechainicial(filtrarVC.get(index).getFechafinal());
                duplicarVC.setFechafinal(filtrarVC.get(index).getFechafinal());
                duplicarVC.setContrato(filtrarVC.get(index).getContrato());
                duplicarVC.setTipocontrato(filtrarVC.get(index).getTipocontrato());
            }
            if (duplicarVC.getTipocontrato() == null) {
                duplicarVC.setTipocontrato(new TiposContratos());
            }
            context.update("formularioDialogos:duplicarVC");
            context.execute("DuplicarRegistroVC.show()");
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasContratos
     */
    public void confirmarDuplicar() {
        if (duplicarVC.getFechainicial() != null && duplicarVC.getContrato().getSecuencia() != null) {
            if (validarFechasRegistro(2)) {
                paraNuevaVContrato++;
                nuevaVContratoSecuencia = BigInteger.valueOf(paraNuevaVContrato);
                vigenciasContratos.add(duplicarVC);
                duplicarVC.setSecuencia(nuevaVContratoSecuencia);
                listVCCrear.add(duplicarVC);
                RequestContext context = RequestContext.getCurrentInstance();
                infoRegistro = "Cantidad de registros: " + vigenciasContratos.size();
                context.update("form:informacionRegistro");
                context.update("form:datosVCEmpleado");
                context.execute("DuplicarRegistroVC.hide()");
                index = -1;
                secRegistro = null;
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vcFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vcFechaInicial");
                    vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vcFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
                    vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vcContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
                    vcContrato.setFilterStyle("display: none; visibility: hidden;");
                    vcTipoContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
                    vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = "270";
                    RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
                    bandera = 0;
                    filtrarVC = null;
                    tipoLista = 0;
                }
                duplicarVC = new VigenciasContratos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarduplicarVC() {
        duplicarVC = new VigenciasContratos();
        duplicarVC.setContrato(new Contratos());
        duplicarVC.setTipocontrato(new TiposContratos());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVC() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (tipoLista == 0) {// Si NO tiene Filtro
                if (!listVCModificar.isEmpty() && listVCModificar.contains(vigenciasContratos.get(index))) {
                    int modIndex = listVCModificar.indexOf(vigenciasContratos.get(index));
                    listVCModificar.remove(modIndex);
                    listVCBorrar.add(vigenciasContratos.get(index));
                } else if (!listVCCrear.isEmpty() && listVCCrear.contains(vigenciasContratos.get(index))) {
                    int crearIndex = listVCCrear.indexOf(vigenciasContratos.get(index));
                    listVCCrear.remove(crearIndex);
                } else {
                    listVCBorrar.add(vigenciasContratos.get(index));
                }
                vigenciasContratos.remove(index);
                infoRegistro = "Cantidad de registros: " + vigenciasContratos.size();
            }
            if (tipoLista == 1) {// Si tiene Filtro
                if (!listVCModificar.isEmpty() && listVCModificar.contains(filtrarVC.get(index))) {
                    int modIndex = listVCModificar.indexOf(filtrarVC.get(index));
                    listVCModificar.remove(modIndex);
                    listVCBorrar.add(filtrarVC.get(index));
                } else if (!listVCCrear.isEmpty() && listVCCrear.contains(filtrarVC.get(index))) {
                    int crearIndex = listVCCrear.indexOf(filtrarVC.get(index));
                    listVCCrear.remove(crearIndex);
                } else {
                    listVCBorrar.add(filtrarVC.get(index));
                }
                int VCIndex = vigenciasContratos.indexOf(filtrarVC.get(index));
                vigenciasContratos.remove(VCIndex);
                filtrarVC.remove(index);
                infoRegistro = "Cantidad de registros: " + filtrarVC.size();
            }
            context.update("form:datosVCEmpleado");
            context.update("form:informacionRegistro");
            index = -1;
            secRegistro = null;

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            vcFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("width: 60px");
            vcFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("width: 60px");
            vcContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("width: 60px");
            vcTipoContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("width: 60px");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            vcFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filtrarVC = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            vcFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            context.update("form:datosVCEmpleado");
            bandera = 0;
            filtrarVC = null;
            tipoLista = 0;
        }

        listVCBorrar.clear();
        listVCCrear.clear();
        listVCModificar.clear();
        index = -1;
        secRegistro = null;
        paraNuevaVContrato = 0;
        vigenciasContratos = null;
        guardado = true;
        context.update("form:ACEPTAR");
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = CONTRATOS - TIPOSCONTRATOS)

    /**
     * Metodo que ejecuta los dialogos de contratos y tipos contratos
     *
     * @param indice Fila de la tabla
     * @param list Lista filtrada - Lista real
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
    public void asignarIndex(Integer indice, int list, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }

        if (list == 0) {
            getInfoRegistroContrato();
            context.update("form:ContratosDialogo");
            context.execute("ContratosDialogo.show()");
        } else if (list == 1) {
            getInfoRegistroTipoContrato();
            context.update("form:TiposContratoDialogo");
            context.execute("TiposContratoDialogo.show()");
        }
    }

    //LOVS
    //CONTRATO
    /**
     * Metodo que actualiza el contrato seleccionado
     */
    public void actualizarContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciasContratos.get(index).setContrato(contratoSelecionado);
                if (!listVCCrear.contains(vigenciasContratos.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    } else if (!listVCModificar.contains(vigenciasContratos.get(index))) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    }
                }
            } else {// Si tiene Filtro
                filtrarVC.get(index).setContrato(contratoSelecionado);
                if (!listVCCrear.contains(filtrarVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filtrarVC.get(index));
                    } else if (!listVCModificar.contains(filtrarVC.get(index))) {
                        listVCModificar.add(filtrarVC.get(index));
                    }
                }
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVCEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigencia.setContrato(contratoSelecionado);
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVC.setContrato(contratoSelecionado);
            context.update("formularioDialogos:duplicarVC");
        }
        filtradoContratos = null;
        contratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        /*
         * context.update("form:ContratosDialogo");
         * context.update("form:lovContratos"); context.update("form:aceptarC");
         */
        context.reset("form:lovContratos:globalFilter");
        context.execute("lovContratos.clearFilters()");
        context.execute("ContratosDialogo.hide()");
    }

    /**
     * Metodo que cancela los cambios sobre contrato
     */
    public void cancelarCambioContrato() {
        filtradoContratos = null;
        contratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovContratos:globalFilter");
        context.execute("lovContratos.clearFilters()");
        context.execute("ContratosDialogo.hide()");
    }

    //TIPO CONTRATO
    /**
     * Metodo que actualiza el tipo contrato seleccionado
     */
    public void actualizarTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciasContratos.get(index).setTipocontrato(tipoContratoSelecionado);
                if (!listVCCrear.contains(vigenciasContratos.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    } else if (!listVCModificar.contains(vigenciasContratos.get(index))) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    }
                }
            } else {// Si tiene Filtro
                filtrarVC.get(index).setTipocontrato(tipoContratoSelecionado);
                if (!listVCCrear.contains(filtrarVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filtrarVC.get(index));
                    } else if (!listVCModificar.contains(filtrarVC.get(index))) {
                        listVCModificar.add(filtrarVC.get(index));
                    }
                }
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVCEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigencia.setTipocontrato(tipoContratoSelecionado);
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVC.setTipocontrato(tipoContratoSelecionado);
            context.update("formularioDialogos:duplicarVC");
        }
        filtradoTiposContratos = null;
        tipoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.reset("form:lovTiposContratos:globalFilter");
        context.execute("lovTiposContratos.clearFilters()");
    }

    /**
     * Metodo que cancela la seleccion del tipo contrato seleccionado
     */
    public void cancelarCambioTipoContrato() {
        filtradoTiposContratos = null;
        tipoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTiposContratos:globalFilter");
        context.execute("lovTiposContratos.clearFilters()");
        context.execute("TiposContratoDialogo.hide()");
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de la tabla con respecto a la
     * columna tipos contratos o contratos
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (cualCelda == 2) {
                getInfoRegistroContrato();
                context.update("form:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 3) {
                getInfoRegistroTipoContrato();
                context.update("form:TiposContratoDialogo");
                context.execute("TiposContratoDialogo.show()");
                tipoActualizacion = 0;
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
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasContratosPDF", false, false, "UTF-8", null, null);
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasContratosXLS", false, false, "UTF-8", null, null);
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
        infoRegistro = "Cantidad de Registros: " + filtrarVC.size();
        context.update("form:informacionRegistro");
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si hay registro seleccionado
        if (index >= 0) {
            int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASCONTRATOS");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONTRATOS")) {
                //context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    //GETTERS AND SETTERS
    /**
     * Metodo que obtiene las VigenciasContratos de un empleado, en caso de ser
     * null por medio del administrar hace el llamado para almacenarlo
     *
     * @return listVC Lista Vigencias Contratos
     */
    public List<VigenciasContratos> getVigenciasContratosEmpleado() {

        try {
            if (vigenciasContratos == null) {
                vigenciasContratos = administrarVigenciasContratos.VigenciasContratosEmpleado(empleado.getSecuencia());
            }
            return vigenciasContratos;
        } catch (Exception e) {
            System.out.println("Error....................!!!!!!!!!!!! getVigenciasContratos ");
            return null;
        }
        /*
         * try { if (vigenciasContratos == null) { return vigenciasContratos =
         * administrarVigenciasContratos.VigenciasContratosEmpleado(empleado.getSecuencia());
         * } if (!vigenciasContratos.isEmpty()) { for (int i = 0; i <
         * vigenciasContratos.size(); i++) { if
         * (vigenciasContratos.get(i).getTipocontrato() == null) {
         * vigenciasContratos.get(i).setTipocontrato(new TiposContratos()); } }
         * } return vigenciasContratos;
         *
         * } catch (Exception e) { System.out.println("Error...!!
         * getVigenciasContratosEmpleado "); return null; }
         */

    }

    public void setVigenciasContratosEmpleado(List<VigenciasContratos> vigenciasContratos) {
        this.vigenciasContratos = vigenciasContratos;
    }

    /**
     * Metodo que obtiene el empleado usando en el momento, en caso de ser null
     * por medio del administrar obtiene el valor
     *
     * @return empl Empleado que esta siendo usando en el momento
     */
    public Empleados getEmpleado() {
        return empleado;
    }

    public List<VigenciasContratos> getFiltrarVC() {
        return filtrarVC;
    }

    public void setFiltrarVC(List<VigenciasContratos> filtrarVC) {
        this.filtrarVC = filtrarVC;
    }

    public VigenciasContratos getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasContratos nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    /**
     * Metodo que obtiene la lista de contratos, en caso de ser null por medio
     * del administrar los obtiene
     *
     * @return listTC Lista Tipos Contratos
     */
    public List<Contratos> getListaContratos() {

        if (listaContratos == null) {
            listaContratos = administrarVigenciasContratos.contratos();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaContratos == null || listaContratos.isEmpty()) {
                infoRegistroContrato = "Cantidad de registros: 0 ";
            } else {
                infoRegistroContrato = "Cantidad de registros: " + listaContratos.size();
            }
            context.update("form:infoRegistroContrato");
        }
        return listaContratos;

        /*
         * if (listaContratos == null) { listaContratos =
         * administrarVigenciasContratos.contratos(); RequestContext context =
         * RequestContext.getCurrentInstance(); if (listaContratos == null ||
         * listaContratos.isEmpty()) { infoRegistroContrato = "Cantidad de
         * registros: 0 "; } else { infoRegistroContrato = "Cantidad de
         * registros: " + listaContratos.size(); }
         * context.update("form:infoRegistroContrato"); } return listaContratos;
         */
    }

    public void setListaContratos(List<Contratos> listaContratos) {
        this.listaContratos = listaContratos;
    }

    public List<Contratos> getFiltradoContratos() {
        return filtradoContratos;
    }

    public void setFiltradoContratos(List<Contratos> filtradoContratos) {
        this.filtradoContratos = filtradoContratos;
    }

    /**
     * Metodo que obtiene los tipos contratos, en caso de ser null por medio del
     * administrar obtiene los valores
     *
     * @return listTC Lista Tipos Contratos
     */
    public List<TiposContratos> getListaTiposContratos() {
        if (listaTiposContratos == null) {
            listaTiposContratos = administrarVigenciasContratos.tiposContratos();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaTiposContratos == null || listaTiposContratos.isEmpty()) {
                infoRegistroTipoContrato = "Cantidad de registros: 0 ";
            } else {
                infoRegistroTipoContrato = "Cantidad de registros: " + listaTiposContratos.size();
            }
            context.update("form:infoRegistroTipoContrato");
        }
        return listaTiposContratos;
    }

    public void setListaTiposContratos(List<TiposContratos> listaTiposContratos) {
        this.listaTiposContratos = listaTiposContratos;
    }

    public List<TiposContratos> getFiltradoTiposContratos() {
        return filtradoTiposContratos;
    }

    public void setFiltradoTiposContratos(List<TiposContratos> filtradoTiposContratos) {
        this.filtradoTiposContratos = filtradoTiposContratos;
    }

    public VigenciasContratos getEditarVC() {
        return editarVC;
    }

    public void setEditarVC(VigenciasContratos editarVC) {
        this.editarVC = editarVC;
    }

    public VigenciasContratos getDuplicarVC() {
        return duplicarVC;
    }

    public void setDuplicarVC(VigenciasContratos duplicarVC) {
        this.duplicarVC = duplicarVC;
    }

    public Contratos getContratoSelecionado() {
        return contratoSelecionado;
    }

    public void setContratoSelecionado(Contratos contratoSelecionado) {
        this.contratoSelecionado = contratoSelecionado;
    }

    public TiposContratos getTipoContratoSelecionado() {
        return tipoContratoSelecionado;
    }

    public void setTipoContratoSelecionado(TiposContratos tipoContratoSelecionado) {
        this.tipoContratoSelecionado = tipoContratoSelecionado;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public VigenciasContratos getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasContratos vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getInfoRegistroContrato() {
        return infoRegistroContrato;
    }

    public void setInfoRegistroContrato(String infoRegistroContrato) {
        this.infoRegistroContrato = infoRegistroContrato;
    }

    public String getInfoRegistroTipoContrato() {
        return infoRegistroTipoContrato;
    }

    public void setInfoRegistroTipoContrato(String infoRegistroTipoContrato) {
        this.infoRegistroTipoContrato = infoRegistroTipoContrato;
    }
}

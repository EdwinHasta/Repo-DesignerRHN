package Controlador;

import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.MotivosContratos;
import Entidades.TiposContratos;
import Entidades.VigenciasTiposContratos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasTiposContratosInterface;
import java.io.IOException;
import java.io.Serializable;
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

@ManagedBean
@SessionScoped
public class ControlVigenciasTiposContratos implements Serializable {

    @EJB
    AdministrarVigenciasTiposContratosInterface administrarVigenciasTiposContratos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Cargos
    private List<VigenciasTiposContratos> vigenciasTiposContratoEmpleado;
    private List<VigenciasTiposContratos> filtrarVTC;
    private List<Ciudades> listaCiudades;
    private Ciudades ciudadSelecionada;
    private List<Ciudades> filtradoCiudades;
    private List<MotivosContratos> listaMotivosContratos;
    private MotivosContratos MotivoContratoSelecionado;
    private List<MotivosContratos> filtradoMotivoContrato;
    private List<TiposContratos> listaTiposContratos;
    private List<TiposContratos> filtradoTiposContrato;
    private TiposContratos TipoContratoSelecionado;
    private BigInteger secuenciaEmpleado;
    private Empleados empleado;
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vtcFecha, vtcContrato, vtcTipoContrato, vtcCiudad, vtcFechaSP, vtcInicioFlexibilizacion, vtcObservacion;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasTiposContratos> listVTCModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public VigenciasTiposContratos nuevaVigencia;
    private List<VigenciasTiposContratos> listVTCCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<VigenciasTiposContratos> listVTCBorrar;
    //editar celda
    private VigenciasTiposContratos editarVTC;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasTiposContratos duplicarVTC;
    //AUTOCOMPLETAR
    private String Motivo, TipoContrato, Ciudad;
    //RASTRO
    private BigInteger secRegistro;

    public ControlVigenciasTiposContratos() {
        permitirIndex = true;
        vigenciasTiposContratoEmpleado = null;
        listaCiudades = new ArrayList<Ciudades>();
        empleado = new Empleados();
        ciudadSelecionada = new Ciudades();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVTCBorrar = new ArrayList<VigenciasTiposContratos>();
        //crear aficiones
        listVTCCrear = new ArrayList<VigenciasTiposContratos>();
        k = 0;
        //modificar aficiones
        listVTCModificar = new ArrayList<VigenciasTiposContratos>();
        //editar
        editarVTC = new VigenciasTiposContratos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasTiposContratos();
        nuevaVigencia.setMotivocontrato(new MotivosContratos());
        nuevaVigencia.setTipocontrato(new TiposContratos());
        nuevaVigencia.setCiudad(new Ciudades());
        secRegistro = null;
    }
    //EMPLEADO DE LA VIGENCIA

    public void recibirEmpleado(BigInteger sec) {
        vigenciasTiposContratoEmpleado = null;
        secuenciaEmpleado = sec;
        //empleado = administrarVigenciasTiposContratos.buscarEmpleado(BigInteger.valueOf(10661039));
        empleado = administrarVigenciasTiposContratos.buscarEmpleado(secuenciaEmpleado);
    }

    public void modificarVTC(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listVTCCrear.contains(vigenciasTiposContratoEmpleado.get(indice))) {

                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(indice));
                    } else if (!listVTCModificar.contains(vigenciasTiposContratoEmpleado.get(indice))) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listVTCCrear.contains(filtrarVTC.get(indice))) {

                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(filtrarVTC.get(indice));
                    } else if (!listVTCModificar.contains(filtrarVTC.get(indice))) {
                        listVTCModificar.add(filtrarVTC.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosVTCEmpleado");
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOC")) {
            if (tipoLista == 0) {
                vigenciasTiposContratoEmpleado.get(indice).getMotivocontrato().setNombre(Motivo);
            } else {
                filtrarVTC.get(indice).getMotivocontrato().setNombre(Motivo);
            }
            for (int i = 0; i < listaMotivosContratos.size(); i++) {
                if (listaMotivosContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasTiposContratoEmpleado.get(indice).setMotivocontrato(listaMotivosContratos.get(indiceUnicoElemento));
                } else {
                    filtrarVTC.get(indice).setMotivocontrato(listaMotivosContratos.get(indiceUnicoElemento));
                }
                listaMotivosContratos.clear();
                getListaMotivosContratos();
            } else {
                permitirIndex = false;
                context.update("form:MotivosContratoDialogo");
                context.execute("MotivosContratoDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOC")) {
            if (tipoLista == 0) {
                vigenciasTiposContratoEmpleado.get(indice).getTipocontrato().setNombre(TipoContrato);
            } else {
                filtrarVTC.get(indice).getTipocontrato().setNombre(TipoContrato);
            }
            for (int i = 0; i < listaTiposContratos.size(); i++) {
                if (listaTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasTiposContratoEmpleado.get(indice).setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                } else {
                    filtrarVTC.get(indice).setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                }
                listaTiposContratos.clear();
                getListaTiposContratos();
            } else {
                permitirIndex = false;
                context.update("form:TiposContratoDialogo");
                context.execute("TiposContratoDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoLista == 0) {
                vigenciasTiposContratoEmpleado.get(indice).getCiudad().setNombre(Ciudad);
            } else {
                filtrarVTC.get(indice).getCiudad().setNombre(Ciudad);
            }
            for (int i = 0; i < listaCiudades.size(); i++) {
                if (listaCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasTiposContratoEmpleado.get(indice).setCiudad(listaCiudades.get(indiceUnicoElemento));
                } else {
                    filtrarVTC.get(indice).setCiudad(listaCiudades.get(indiceUnicoElemento));
                }
                listaCiudades.clear();
                getListaCiudades();
            } else {
                permitirIndex = false;
                context.update("form:ciudadesDialogo");
                context.execute("ciudadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVTCCrear.contains(vigenciasTiposContratoEmpleado.get(indice))) {

                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(indice));
                    } else if (!listVTCModificar.contains(vigenciasTiposContratoEmpleado.get(indice))) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVTCCrear.contains(filtrarVTC.get(indice))) {

                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(filtrarVTC.get(indice));
                    } else if (!listVTCModificar.contains(filtrarVTC.get(indice))) {
                        listVTCModificar.add(filtrarVTC.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVTCEmpleado");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("MOTIVOC")) {
            if (tipoNuevo == 1) {
                Motivo = nuevaVigencia.getMotivocontrato().getNombre();
            } else if (tipoNuevo == 2) {
                Motivo = duplicarVTC.getMotivocontrato().getNombre();
            }
        } else if (Campo.equals("TIPOC")) {
            if (tipoNuevo == 1) {
                TipoContrato = nuevaVigencia.getTipocontrato().getNombre();
            } else if (tipoNuevo == 2) {
                TipoContrato = duplicarVTC.getTipocontrato().getNombre();
            }
        } else if (Campo.equals("CIUDAD")) {
            if (tipoNuevo == 1) {
                Ciudad = nuevaVigencia.getCiudad().getNombre();
            } else if (tipoNuevo == 2) {
                Ciudad = duplicarVTC.getCiudad().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVOC")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getMotivocontrato().setNombre(Motivo);
            } else if (tipoNuevo == 2) {
                duplicarVTC.getMotivocontrato().setNombre(Motivo);
            }
            for (int i = 0; i < listaMotivosContratos.size(); i++) {
                if (listaMotivosContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setMotivocontrato(listaMotivosContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoMotivoContrato");
                } else if (tipoNuevo == 2) {
                    duplicarVTC.setMotivocontrato(listaMotivosContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivoContrato");
                }
                listaMotivosContratos.clear();
                getListaMotivosContratos();
            } else {
                context.update("form:MotivosContratoDialogo");
                context.execute("MotivosContratoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoMotivoContrato");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivoContrato");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOC")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getTipocontrato().setNombre(TipoContrato);
            } else if (tipoNuevo == 2) {
                duplicarVTC.getTipocontrato().setNombre(TipoContrato);
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
                    duplicarVTC.setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
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
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getCiudad().setNombre(Ciudad);
            } else if (tipoNuevo == 2) {
                duplicarVTC.getCiudad().setNombre(Ciudad);
            }
            for (int i = 0; i < listaCiudades.size(); i++) {
                if (listaCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setCiudad(listaCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCiudad");
                } else if (tipoNuevo == 2) {
                    duplicarVTC.setCiudad(listaCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCiudad");
                }
                listaCiudades.clear();
                getListaCiudades();
            } else {
                context.update("form:ciudadesDialogo");
                context.execute("ciudadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCiudad");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCiudad");
                }
            }
        }
    }

//Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = vigenciasTiposContratoEmpleado.get(index).getSecuencia();
            if (cualCelda == 1) {
                Motivo = vigenciasTiposContratoEmpleado.get(index).getMotivocontrato().getNombre();
            } else if (cualCelda == 2) {
                TipoContrato = vigenciasTiposContratoEmpleado.get(index).getTipocontrato().getNombre();
            } else if (cualCelda == 3) {
                Ciudad = null;
                if (vigenciasTiposContratoEmpleado.get(index).getCiudad() != null) {
                    Ciudad = vigenciasTiposContratoEmpleado.get(index).getCiudad().getNombre();
                }
            }
        }
    }
    //GUARDAR

    public void guardarCambiosVTC() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Tipos Contratos");
            if (!listVTCBorrar.isEmpty()) {
                for (int i = 0; i < listVTCBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listVTCBorrar.get(i).getCiudad().getSecuencia() == null) {
                        listVTCBorrar.get(i).setCiudad(null);
                        administrarVigenciasTiposContratos.borrarVTC(listVTCBorrar.get(i));
                    } else {
                        administrarVigenciasTiposContratos.borrarVTC(listVTCBorrar.get(i));
                    }

                }
                listVTCBorrar.clear();
            }
            if (!listVTCCrear.isEmpty()) {
                for (int i = 0; i < listVTCCrear.size(); i++) {
                    System.out.println("Creando...");
                    if (listVTCCrear.get(i).getCiudad().getSecuencia() == null) {
                        listVTCCrear.get(i).setCiudad(null);
                        administrarVigenciasTiposContratos.crearVTC(listVTCCrear.get(i));
                    } else {
                        administrarVigenciasTiposContratos.crearVTC(listVTCCrear.get(i));
                    }
                }
                listVTCCrear.clear();
            }
            if (!listVTCModificar.isEmpty()) {
                administrarVigenciasTiposContratos.modificarVTC(listVTCModificar);
                listVTCModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            vigenciasTiposContratoEmpleado = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVTCEmpleado");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        index = -1;
        secRegistro = null;
    }
    //CANCELAR MODIFICACIONES

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
            vtcFecha.setFilterStyle("display: none; visibility: hidden;");
            vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
            vtcContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
            vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
            vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
            vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
            vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
            vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
            vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
            vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
            vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
            bandera = 0;
            filtrarVTC = null;
            tipoLista = 0;
        }

        listVTCBorrar.clear();
        listVTCCrear.clear();
        listVTCModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        vigenciasTiposContratoEmpleado = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVTCEmpleado");
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVTC = vigenciasTiposContratoEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarVTC = filtrarVTC.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarMotivoContrato");
                context.execute("editarMotivoContrato.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipoContrato");
                context.execute("editarTipoContrato.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarCiudad");
                context.execute("editarCiudad.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarFechaSP");
                context.execute("editarFechaSP.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarFechaF");
                context.execute("editarFechaF.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarObservacion");
                context.execute("editarObservacion.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //CREAR VTC
    public void agregarNuevaVTC() {
        boolean pasa = false;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigencia.getFechavigencia() == null) {
            mensajeValidacion = " * Fecha \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevaVigencia.getMotivocontrato().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Motivo del Contrato \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevaVigencia.getTipocontrato().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Tipo de Contrato \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (pasa == true) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
                vtcFecha.setFilterStyle("display: none; visibility: hidden;");
                vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
                vtcContrato.setFilterStyle("display: none; visibility: hidden;");
                vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
                vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
                vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
                vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
                vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
                vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
                vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
                vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
                vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
                vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
                bandera = 0;
                filtrarVTC = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevaVigencia.setSecuencia(l);
            nuevaVigencia.setEmpleado(empleado);
            if (nuevaVigencia.getCiudad().getSecuencia() == null) {
                nuevaVigencia.setCiudad(null);
            }
            listVTCCrear.add(nuevaVigencia);

            vigenciasTiposContratoEmpleado.add(nuevaVigencia);
            nuevaVigencia = new VigenciasTiposContratos();
            nuevaVigencia.setMotivocontrato(new MotivosContratos());
            nuevaVigencia.setTipocontrato(new TiposContratos());
            nuevaVigencia.setCiudad(new Ciudades());
            context.update("form:datosVTCEmpleado");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroVTC.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaVC() {
        nuevaVigencia = new VigenciasTiposContratos();
        nuevaVigencia.setMotivocontrato(new MotivosContratos());
        nuevaVigencia.setTipocontrato(new TiposContratos());
        nuevaVigencia.setCiudad(new Ciudades());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    public void duplicarVigenciaC() {
        if (index >= 0) {
            duplicarVTC = new VigenciasTiposContratos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVTC.setSecuencia(l);
                duplicarVTC.setFechavigencia(vigenciasTiposContratoEmpleado.get(index).getFechavigencia());
                duplicarVTC.setMotivocontrato(vigenciasTiposContratoEmpleado.get(index).getMotivocontrato());
                duplicarVTC.setTipocontrato(vigenciasTiposContratoEmpleado.get(index).getTipocontrato());
                duplicarVTC.setCiudad(vigenciasTiposContratoEmpleado.get(index).getCiudad());
                duplicarVTC.setIniciosustitucion(vigenciasTiposContratoEmpleado.get(index).getIniciosustitucion());
                duplicarVTC.setInicioflexibiliza(vigenciasTiposContratoEmpleado.get(index).getInicioflexibiliza());
                duplicarVTC.setEmpleado(vigenciasTiposContratoEmpleado.get(index).getEmpleado());
                duplicarVTC.setObservaciones(vigenciasTiposContratoEmpleado.get(index).getObservaciones());
            }
            if (tipoLista == 1) {
                duplicarVTC.setSecuencia(l);
                duplicarVTC.setFechavigencia(filtrarVTC.get(index).getFechavigencia());
                duplicarVTC.setMotivocontrato(filtrarVTC.get(index).getMotivocontrato());
                duplicarVTC.setTipocontrato(filtrarVTC.get(index).getTipocontrato());
                duplicarVTC.setCiudad(filtrarVTC.get(index).getCiudad());
                duplicarVTC.setIniciosustitucion(filtrarVTC.get(index).getIniciosustitucion());
                duplicarVTC.setInicioflexibiliza(filtrarVTC.get(index).getInicioflexibiliza());
                duplicarVTC.setEmpleado(filtrarVTC.get(index).getEmpleado());
                duplicarVTC.setObservaciones(filtrarVTC.get(index).getObservaciones());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVTC");
            context.execute("DuplicarRegistroVTC.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {

        vigenciasTiposContratoEmpleado.add(duplicarVTC);
        listVTCCrear.add(duplicarVTC);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVTCEmpleado");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
            vtcFecha.setFilterStyle("display: none; visibility: hidden;");
            vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
            vtcContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
            vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
            vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
            vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
            vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
            vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
            vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
            vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
            vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
            bandera = 0;
            filtrarVTC = null;
            tipoLista = 0;
        }
        duplicarVTC = new VigenciasTiposContratos();
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicarVTC() {
        duplicarVTC = new VigenciasTiposContratos();
    }

    //BORRAR VC
    public void borrarVTC() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVTCModificar.isEmpty() && listVTCModificar.contains(vigenciasTiposContratoEmpleado.get(index))) {
                    int modIndex = listVTCModificar.indexOf(vigenciasTiposContratoEmpleado.get(index));
                    listVTCModificar.remove(modIndex);
                    listVTCBorrar.add(vigenciasTiposContratoEmpleado.get(index));
                } else if (!listVTCCrear.isEmpty() && listVTCCrear.contains(vigenciasTiposContratoEmpleado.get(index))) {
                    int crearIndex = listVTCCrear.indexOf(vigenciasTiposContratoEmpleado.get(index));
                    listVTCCrear.remove(crearIndex);
                } else {
                    listVTCBorrar.add(vigenciasTiposContratoEmpleado.get(index));
                }
                vigenciasTiposContratoEmpleado.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVTCModificar.isEmpty() && listVTCModificar.contains(filtrarVTC.get(index))) {
                    int modIndex = listVTCModificar.indexOf(filtrarVTC.get(index));
                    listVTCModificar.remove(modIndex);
                    listVTCBorrar.add(filtrarVTC.get(index));
                } else if (!listVTCCrear.isEmpty() && listVTCCrear.contains(filtrarVTC.get(index))) {
                    int crearIndex = listVTCCrear.indexOf(filtrarVTC.get(index));
                    listVTCCrear.remove(crearIndex);
                } else {
                    listVTCBorrar.add(filtrarVTC.get(index));
                }
                int VCIndex = vigenciasTiposContratoEmpleado.indexOf(filtrarVTC.get(index));
                vigenciasTiposContratoEmpleado.remove(VCIndex);
                filtrarVTC.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVTCEmpleado");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    public void activarCtrlF11() {
        if (bandera == 0) {
            System.out.println("Activar");
            vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
            vtcFecha.setFilterStyle("width: 60px");
            vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
            vtcContrato.setFilterStyle("");
            vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
            vtcTipoContrato.setFilterStyle("");
            vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
            vtcCiudad.setFilterStyle("width: 60px");
            vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
            vtcFechaSP.setFilterStyle("");
            vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
            vtcInicioFlexibilizacion.setFilterStyle("");
            vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
            vtcObservacion.setFilterStyle("");
            RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
            vtcFecha.setFilterStyle("display: none; visibility: hidden;");
            vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
            vtcContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
            vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
            vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
            vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
            vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
            vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
            vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
            vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
            vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
            bandera = 0;
            filtrarVTC = null;
            tipoLista = 0;
        }
    }

    //SALIR
    public void salir() {
        if (bandera == 1) {
            vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
            vtcFecha.setFilterStyle("display: none; visibility: hidden;");
            vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
            vtcContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
            vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
            vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
            vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
            vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
            vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
            vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
            vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
            vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
            bandera = 0;
            filtrarVTC = null;
            tipoLista = 0;
        }

        listVTCBorrar.clear();
        listVTCCrear.clear();
        listVTCModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        vigenciasTiposContratoEmpleado = null;
        guardado = true;
        permitirIndex = true;

    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)

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
            context.update("form:MotivosContratoDialogo");
            context.execute("MotivosContratoDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:TiposContratoDialogo");
            context.execute("TiposContratoDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:ciudadesDialogo");
            context.execute("ciudadesDialogo.show()");
        }

    }

    //LOVS
    //CIUDAD
    public void actualizarCiudad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasTiposContratoEmpleado.get(index).setCiudad(ciudadSelecionada);

                if (!listVTCCrear.contains(vigenciasTiposContratoEmpleado.get(index))) {
                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(index));
                    } else if (!listVTCModificar.contains(vigenciasTiposContratoEmpleado.get(index))) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(index));
                    }
                }
            } else {
                filtrarVTC.get(index).setCiudad(ciudadSelecionada);

                if (!listVTCCrear.contains(filtrarVTC.get(index))) {
                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(filtrarVTC.get(index));
                    } else if (!listVTCModificar.contains(filtrarVTC.get(index))) {
                        listVTCModificar.add(filtrarVTC.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosVTCEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setCiudad(ciudadSelecionada);
            context.update("formularioDialogos:nuevaVTC");
        } else if (tipoActualizacion == 2) {
            duplicarVTC.setCiudad(ciudadSelecionada);
            context.update("formularioDialogos:duplicarVTC");
        }
        filtradoCiudades = null;
        ciudadSelecionada = new Ciudades();
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("ciudadesDialogo.hide()");
        context.reset("form:lovCiudades:globalFilter");
        context.update("form:lovCiudades");
    }

    public void cancelarCambioCiudad() {
        filtradoCiudades = null;
        ciudadSelecionada = new Ciudades();
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
    //MOTIVO CONTRATO

    public void actualizarMotivoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasTiposContratoEmpleado.get(index).setMotivocontrato(MotivoContratoSelecionado);

                if (!listVTCCrear.contains(vigenciasTiposContratoEmpleado.get(index))) {
                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(index));
                    } else if (!listVTCModificar.contains(vigenciasTiposContratoEmpleado.get(index))) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(index));
                    }
                }
            } else {
                filtrarVTC.get(index).setMotivocontrato(MotivoContratoSelecionado);

                if (!listVTCCrear.contains(filtrarVTC.get(index))) {
                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(filtrarVTC.get(index));
                    } else if (!listVTCModificar.contains(filtrarVTC.get(index))) {
                        listVTCModificar.add(filtrarVTC.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosVTCEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setMotivocontrato(MotivoContratoSelecionado);
            context.update("formularioDialogos:nuevaVTC");
        } else if (tipoActualizacion == 2) {
            duplicarVTC.setMotivocontrato(MotivoContratoSelecionado);
            context.update("formularioDialogos:duplicarVTC");
        }
        filtradoMotivoContrato = null;
        MotivoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("MotivosContratoDialogo.hide()");
        context.reset("form:lovMotivosContrato:globalFilter");
        context.update("form:lovMotivosContrato");
    }

    public void cancelarCambioMotivoContrato() {
        filtradoMotivoContrato = null;
        MotivoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //TIPO CONTRATO
    public void actualizarTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasTiposContratoEmpleado.get(index).setTipocontrato(TipoContratoSelecionado);

                if (!listVTCCrear.contains(vigenciasTiposContratoEmpleado.get(index))) {
                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(index));
                    } else if (!listVTCModificar.contains(vigenciasTiposContratoEmpleado.get(index))) {
                        listVTCModificar.add(vigenciasTiposContratoEmpleado.get(index));
                    }
                }
            } else {
                filtrarVTC.get(index).setTipocontrato(TipoContratoSelecionado);

                if (!listVTCCrear.contains(filtrarVTC.get(index))) {
                    if (listVTCModificar.isEmpty()) {
                        listVTCModificar.add(filtrarVTC.get(index));
                    } else if (!listVTCModificar.contains(filtrarVTC.get(index))) {
                        listVTCModificar.add(filtrarVTC.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosVTCEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setTipocontrato(TipoContratoSelecionado);
            context.update("formularioDialogos:nuevaVTC");
        } else if (tipoActualizacion == 2) {
            duplicarVTC.setTipocontrato(TipoContratoSelecionado);
            context.update("formularioDialogos:duplicarVTC");
        }
        filtradoTiposContrato = null;
        TipoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("TiposContratoDialogo.hide()");
        context.reset("form:lovTiposContrato:globalFilter");
        context.update("form:lovTiposContrato");
    }

    public void cancelarTipoContrato() {
        filtradoTiposContrato = null;
        TipoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
    //LISTA DE VALORES DINAMICA

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                context.update("form:MotivosContratoDialogo");
                context.execute("MotivosContratoDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 2) {
                tipoActualizacion = 0;
                context.update("form:TiposContratoDialogo");
                context.execute("TiposContratoDialogo.show()");
            } else if (cualCelda == 3) {
                tipoActualizacion = 0;
                context.update("form:ciudadesDialogo");
                context.execute("ciudadesDialogo.show()");
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void limpiarNuevaVTC() {
        nuevaVigencia = new VigenciasTiposContratos();
        index = -1;
        secRegistro = null;
    }
    //EXPORTAR

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVTCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasTiposContratosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVTCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasTiposContratosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //EVENTO FILTRAR

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!vigenciasTiposContratoEmpleado.isEmpty()) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASTIPOSCONTRATOS");
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
        }else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASTIPOSCONTRATOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    //GETTERS AND SETTERS

    public List<VigenciasTiposContratos> getVigenciasTiposContratoEmpleado() {
        try {
            if (vigenciasTiposContratoEmpleado == null) {
                //vigenciasTiposContratoEmpleado = administrarVigenciasTiposContratos.vigenciasTiposContratosEmpleado(BigInteger.valueOf(10661039));
                // return vigenciasTiposContratoEmpleado;
                return vigenciasTiposContratoEmpleado = administrarVigenciasTiposContratos.vigenciasTiposContratosEmpleado(secuenciaEmpleado);
            } else {
                return vigenciasTiposContratoEmpleado;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getVigenciasTiposContratosEmpleado " + e);
            return null;
        }
    }

    public void setVigenciasTiposContratoEmpleado(List<VigenciasTiposContratos> vigenciasTiposContratoEmpleado) {
        this.vigenciasTiposContratoEmpleado = vigenciasTiposContratoEmpleado;
    }

    public Empleados getEmpleado() {
        //empleado = administrarVigenciasTiposContratos.buscarEmpleado(BigInteger.valueOf(10661039));
        return empleado;
    }

    public List<VigenciasTiposContratos> getFiltrarVTC() {
        return filtrarVTC;
    }

    public void setFiltrarVTC(List<VigenciasTiposContratos> filtrarVTC) {
        this.filtrarVTC = filtrarVTC;
    }

    public VigenciasTiposContratos getEditarVTC() {
        return editarVTC;
    }

    public void setEditarVTC(VigenciasTiposContratos editarVTC) {
        this.editarVTC = editarVTC;
    }

    public VigenciasTiposContratos getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasTiposContratos nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void verProceso() {
        /*for (int i = 0; i < listVTCModificar.size(); i++) {
         System.out.println("Las que se Modificaron:         " + " Fecha:   " + formatoFecha.format(listVTCModificar.get(i).getFechavigencia()) + "|   Estructura:  " + listVTCModificar.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + listVTCModificar.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + listVTCModificar.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + listVTCModificar.get(i).getIniciosustitucion());
         }*/
        System.out.println(".................................................");
        for (int i = 0; i < listVTCCrear.size(); i++) {
            System.out.println("Las que se van a Crear:         " + " Fecha:   " + listVTCCrear.get(i).getFechavigencia() + "|   Motivo:  " + listVTCCrear.get(i).getMotivocontrato().getNombre() + "|   Tipo:  " + listVTCCrear.get(i).getTipocontrato().getNombre() + "|   Ciudad:  " + listVTCCrear.get(i).getCiudad().getSecuencia() + "|   Empleado:  " + listVTCCrear.get(i).getEmpleado().getSecuencia());
        }/*
         System.out.println(".................................................");
         for (int i = 0; i < listVTCBorrar.size(); i++) {
         System.out.println("Las que se van a Borrar:         " + " Fecha:   " + formatoFecha.format(listVTCBorrar.get(i).getFechavigencia()) + "|   Estructura:  " + listVTCBorrar.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + listVTCBorrar.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + listVTCBorrar.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + listVTCBorrar.get(i).getEstructura().getCentrocosto().getNombre());
         }
         System.out.println(".................................................");
         for (int i = 0; i < vigenciasTiposContratoEmpleado.size(); i++) {
         System.out.println("lista total:         " + " Fecha:   " + formatoFecha.format(vigenciasTiposContratoEmpleado.get(i).getFechavigencia()) + "|   Estructura:  " + vigenciasTiposContratoEmpleado.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + vigenciasTiposContratoEmpleado.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + vigenciasTiposContratoEmpleado.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + vigenciasTiposContratoEmpleado.get(i).getEstructura().getCentrocosto().getNombre());
         }
         System.out.println("................................................. FIN.");*/
    }

    public VigenciasTiposContratos getDuplicarVTC() {
        return duplicarVTC;
    }

    public void setDuplicarVTC(VigenciasTiposContratos duplicarVTC) {
        this.duplicarVTC = duplicarVTC;
    }

    public List<Ciudades> getListaCiudades() {
        listaCiudades = administrarVigenciasTiposContratos.ciudades();
        return listaCiudades;
    }

    public Ciudades getCiudadSelecionada() {
        return ciudadSelecionada;
    }

    public void setCiudadSelecionada(Ciudades ciudadSelecionada) {
        this.ciudadSelecionada = ciudadSelecionada;
    }

    public List<Ciudades> getFiltradoCiudades() {
        return filtradoCiudades;
    }

    public void setFiltradoCiudades(List<Ciudades> filtradoCiudades) {
        this.filtradoCiudades = filtradoCiudades;
    }

    public List<MotivosContratos> getListaMotivosContratos() {
        listaMotivosContratos = administrarVigenciasTiposContratos.motivosContratos();
        return listaMotivosContratos;
    }

    public void setListaMotivosContratos(List<MotivosContratos> listaMotivosContratos) {
        this.listaMotivosContratos = listaMotivosContratos;
    }

    public MotivosContratos getMotivoContratoSelecionado() {
        return MotivoContratoSelecionado;
    }

    public void setMotivoContratoSelecionado(MotivosContratos MotivoContratoSelecionado) {
        this.MotivoContratoSelecionado = MotivoContratoSelecionado;
    }

    public List<MotivosContratos> getFiltradoMotivoContrato() {
        return filtradoMotivoContrato;
    }

    public void setFiltradoMotivoContrato(List<MotivosContratos> filtradoMotivoContrato) {
        this.filtradoMotivoContrato = filtradoMotivoContrato;
    }

    public List<TiposContratos> getFiltradoTiposContrato() {
        return filtradoTiposContrato;
    }

    public void setFiltradoTiposContrato(List<TiposContratos> filtradoTiposContrato) {
        this.filtradoTiposContrato = filtradoTiposContrato;
    }

    public List<TiposContratos> getListaTiposContratos() {
        listaTiposContratos = administrarVigenciasTiposContratos.tiposContratos();
        return listaTiposContratos;
    }

    public void setListaTiposContratos(List<TiposContratos> listaTiposContratos) {
        this.listaTiposContratos = listaTiposContratos;
    }

    public TiposContratos getTipoContratoSelecionado() {
        return TipoContratoSelecionado;
    }

    public void setTipoContratoSelecionado(TiposContratos TipoContratoSelecionado) {
        this.TipoContratoSelecionado = TipoContratoSelecionado;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void actualizar() {
        System.out.println("HOLAAAAAAA :)");
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVTCEmpleado");
    }
}

package Controlador;

import Entidades.Empleados;
import Entidades.UbicacionesGeograficas;
import Entidades.VigenciasUbicaciones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVigenciasUbicacionesInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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

@ManagedBean
@SessionScoped
public class ControlVigenciasUbicaciones implements Serializable {

    @EJB
    AdministrarVigenciasUbicacionesInterface administrarVigenciasUbicaciones;
    //Vigencias Cargos
    private List<VigenciasUbicaciones> vigenciasUbicaciones;
    private List<VigenciasUbicaciones> filtrarVU;
    private List<UbicacionesGeograficas> listaUbicaciones;
    private UbicacionesGeograficas vigenciaSeleccionada;
    private UbicacionesGeograficas UbicacionSelecionada;
    private List<UbicacionesGeograficas> filtradoUbicaciones;
    private BigInteger secuenciaEmpleado;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vuFecha, vuDescripcion, vuCiudad;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasUbicaciones> listVUModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public VigenciasUbicaciones nuevaVigencia;
    private List<VigenciasUbicaciones> listVUCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasUbicaciones> listVUBorrar;
    //editar celda
    private VigenciasUbicaciones editarVU;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasUbicaciones duplicarVU;
    //AUTOCOMPLETAR
    private boolean permitirIndex;
    private String ubicacion;
    //MENSAJE VALIDACION NUEVA VIGENCIA UBICACION
    private String mensajeValidacion;
    private String altoTabla;

    public ControlVigenciasUbicaciones() {
        vigenciasUbicaciones = null;
        listaUbicaciones = new ArrayList<UbicacionesGeograficas>();
        empleado = new Empleados();
        UbicacionSelecionada = new UbicacionesGeograficas();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVUBorrar = new ArrayList<VigenciasUbicaciones>();
        //crear aficiones
        listVUCrear = new ArrayList<VigenciasUbicaciones>();
        k = 0;
        //modificar aficiones
        listVUModificar = new ArrayList<VigenciasUbicaciones>();
        //editar
        editarVU = new VigenciasUbicaciones();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasUbicaciones();
        nuevaVigencia.setUbicacion(new UbicacionesGeograficas());
        //Autocompletar
        permitirIndex = true;
        altoTabla = "270";
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasUbicaciones.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    //EMPLEADO DE LA VIGENCIA
    public void recibirEmpleado(BigInteger sec) {
        vigenciasUbicaciones = null;
        secuenciaEmpleado = sec;
        empleado = administrarVigenciasUbicaciones.buscarEmpleado(secuenciaEmpleado);
    }

    public void modificarVU(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listVUCrear.contains(vigenciasUbicaciones.get(indice))) {

                    if (listVUModificar.isEmpty()) {
                        listVUModificar.add(vigenciasUbicaciones.get(indice));
                    } else if (!listVUModificar.contains(vigenciasUbicaciones.get(indice))) {
                        listVUModificar.add(vigenciasUbicaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
            } else {
                if (!listVUCrear.contains(filtrarVU.get(indice))) {

                    if (listVUModificar.isEmpty()) {
                        listVUModificar.add(filtrarVU.get(indice));
                    } else if (!listVUModificar.contains(filtrarVU.get(indice))) {
                        listVUModificar.add(filtrarVU.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
            }
            context.update("form:datosVUEmpleado");
        } else if (confirmarCambio.equalsIgnoreCase("UBICACION")) {
            if (tipoLista == 0) {
                vigenciasUbicaciones.get(indice).getUbicacion().setDescripcion(ubicacion);
            } else {
                filtrarVU.get(indice).getUbicacion().setDescripcion(ubicacion);
            }
            for (int i = 0; i < listaUbicaciones.size(); i++) {
                if (listaUbicaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasUbicaciones.get(indice).setUbicacion(listaUbicaciones.get(indiceUnicoElemento));
                } else {
                    filtrarVU.get(indice).setUbicacion(listaUbicaciones.get(indiceUnicoElemento));
                }
                listaUbicaciones.clear();
                getListaUbicaciones();
            } else {
                permitirIndex = false;
                context.update("form:UbicacionesGeograficasDialogo");
                context.execute("UbicacionesGeograficasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVUCrear.contains(vigenciasUbicaciones.get(indice))) {

                    if (listVUModificar.isEmpty()) {
                        listVUModificar.add(vigenciasUbicaciones.get(indice));
                    } else if (!listVUModificar.contains(vigenciasUbicaciones.get(indice))) {
                        listVUModificar.add(vigenciasUbicaciones.get(indice));
                    }
                }
            } else {
                if (!listVUCrear.contains(filtrarVU.get(indice))) {

                    if (listVUModificar.isEmpty()) {
                        listVUModificar.add(filtrarVU.get(indice));
                    } else if (!listVUModificar.contains(filtrarVU.get(indice))) {
                        listVUModificar.add(filtrarVU.get(indice));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            index = -1;
        }
        context.update("form:datosVUEmpleado");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (cualCelda == 1) {
                ubicacion = vigenciasUbicaciones.get(index).getUbicacion().getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    //AUTOCOMPLETAR NUEVO Y DUPLICADO
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("UBICACION")) {
            if (tipoNuevo == 1) {
                ubicacion = nuevaVigencia.getUbicacion().getDescripcion();
            } else if (tipoNuevo == 2) {
                ubicacion = duplicarVU.getUbicacion().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("UBICACION")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getUbicacion().setDescripcion(ubicacion);
            } else if (tipoNuevo == 2) {
                duplicarVU.getUbicacion().setDescripcion(ubicacion);
            }
            for (int i = 0; i < listaUbicaciones.size(); i++) {
                if (listaUbicaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setUbicacion(listaUbicaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDescripcion");
                    context.update("formularioDialogos:nuevaCiudad");
                } else if (tipoNuevo == 2) {
                    duplicarVU.setUbicacion(listaUbicaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDescripcion");
                    context.update("formularioDialogos:duplicarCiudad");
                }
                listaUbicaciones.clear();
                getListaUbicaciones();
            } else {
                context.update("form:UbicacionesGeograficasDialogo");
                context.execute("UbicacionesGeograficasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDescripcion");
                    context.update("formularioDialogos:nuevaCiudad");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDescripcion");
                    context.update("formularioDialogos:duplicarCiudad");
                }
            }
        }
    }
    //GUARDAR

    public void guardarCambiosVU() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Tipos Contratos");
            if (!listVUBorrar.isEmpty()) {
                for (int i = 0; i < listVUBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    administrarVigenciasUbicaciones.borrarVU(listVUBorrar.get(i));
                }
                listVUBorrar.clear();
            }
            if (!listVUCrear.isEmpty()) {
                for (int i = 0; i < listVUCrear.size(); i++) {
                    System.out.println("Creando...");
                    administrarVigenciasUbicaciones.crearVU(listVUCrear.get(i));
                }
                listVUCrear.clear();
            }
            if (!listVUModificar.isEmpty()) {
                administrarVigenciasUbicaciones.modificarVU(listVUModificar);
                listVUModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            vigenciasUbicaciones = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVUEmpleado");
            guardado = true;
            context.update("form:ACEPTAR");
            k = 0;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
    }
    //CANCELAR MODIFICACIONES

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            vuFecha = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuFecha");
            vuFecha.setFilterStyle("display: none; visibility: hidden;");
            vuDescripcion = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuDescripcion");
            vuDescripcion.setFilterStyle("display: none; visibility: hidden;");
            vuCiudad = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuCiudad");
            vuCiudad.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVUEmpleado");
            bandera = 0;
            filtrarVU = null;
            tipoLista = 0;
        }

        listVUBorrar.clear();
        listVUCrear.clear();
        listVUModificar.clear();
        index = -1;
        k = 0;
        vigenciasUbicaciones = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVUEmpleado");
        context.update("form:ACEPTAR");
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVU = vigenciasUbicaciones.get(index);
            }
            if (tipoLista == 1) {
                editarVU = filtrarVU.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarDescripcion");
                context.execute("editarDescripcion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarCiudad");
                context.execute("editarCiudad.show()");
                cualCelda = -1;
            }
        }
        index = -1;
    }

    //CREAR VU
    public void agregarNuevaVU() {
        boolean pasa = false;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigencia.getFechavigencia() == null) {
            mensajeValidacion = " * Fecha \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevaVigencia.getUbicacion().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Ubicación \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (pasa == true) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                vuFecha = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuFecha");
                vuFecha.setFilterStyle("display: none; visibility: hidden;");
                vuDescripcion = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuDescripcion");
                vuDescripcion.setFilterStyle("display: none; visibility: hidden;");
                vuCiudad = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuCiudad");
                vuCiudad.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "270";
                RequestContext.getCurrentInstance().update("form:datosVUEmpleado");
                bandera = 0;
                filtrarVU = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevaVigencia.setSecuencia(l);
            nuevaVigencia.setEmpleado(empleado);
            listVUCrear.add(nuevaVigencia);

            vigenciasUbicaciones.add(nuevaVigencia);
            nuevaVigencia = new VigenciasUbicaciones();
            nuevaVigencia.setUbicacion(new UbicacionesGeograficas());
            context.update("form:datosVUEmpleado");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.execute("NuevoRegistroVU.hide()");
            index = -1;
        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaVU() {
        nuevaVigencia = new VigenciasUbicaciones();
        nuevaVigencia.setUbicacion(new UbicacionesGeograficas());
        index = -1;
    }
    //DUPLICAR VC

    public void duplicarVigenciaU() {
        if (index >= 0) {
            duplicarVU = new VigenciasUbicaciones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVU.setSecuencia(l);
                duplicarVU.setEmpleado(vigenciasUbicaciones.get(index).getEmpleado());
                duplicarVU.setFechavigencia(vigenciasUbicaciones.get(index).getFechavigencia());
                duplicarVU.setUbicacion(vigenciasUbicaciones.get(index).getUbicacion());
            }
            if (tipoLista == 1) {
                duplicarVU.setSecuencia(l);
                duplicarVU.setEmpleado(filtrarVU.get(index).getEmpleado());
                duplicarVU.setFechavigencia(filtrarVU.get(index).getFechavigencia());
                duplicarVU.setUbicacion(filtrarVU.get(index).getUbicacion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVU");
            context.execute("DuplicarRegistroVU.show()");
            index = -1;
        }
    }

    public void confirmarDuplicar() {

        vigenciasUbicaciones.add(duplicarVU);
        listVUCrear.add(duplicarVU);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVUEmpleado");
        index = -1;
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            vuFecha = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuFecha");
            vuFecha.setFilterStyle("display: none; visibility: hidden;");
            vuDescripcion = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuDescripcion");
            vuDescripcion.setFilterStyle("display: none; visibility: hidden;");
            vuCiudad = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuCiudad");
            vuCiudad.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVUEmpleado");
            bandera = 0;
            filtrarVU = null;
            tipoLista = 0;
        }
        duplicarVU = new VigenciasUbicaciones();
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicarVU() {
        duplicarVU = new VigenciasUbicaciones();
    }

    //BORRAR VC
    public void borrarVU() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVUModificar.isEmpty() && listVUModificar.contains(vigenciasUbicaciones.get(index))) {
                    int modIndex = listVUModificar.indexOf(vigenciasUbicaciones.get(index));
                    listVUModificar.remove(modIndex);
                    listVUBorrar.add(vigenciasUbicaciones.get(index));
                } else if (!listVUCrear.isEmpty() && listVUCrear.contains(vigenciasUbicaciones.get(index))) {
                    int crearIndex = listVUCrear.indexOf(vigenciasUbicaciones.get(index));
                    listVUCrear.remove(crearIndex);
                } else {
                    listVUBorrar.add(vigenciasUbicaciones.get(index));
                }
                vigenciasUbicaciones.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVUModificar.isEmpty() && listVUModificar.contains(filtrarVU.get(index))) {
                    int modIndex = listVUModificar.indexOf(filtrarVU.get(index));
                    listVUModificar.remove(modIndex);
                    listVUBorrar.add(filtrarVU.get(index));
                } else if (!listVUCrear.isEmpty() && listVUCrear.contains(filtrarVU.get(index))) {
                    int crearIndex = listVUCrear.indexOf(filtrarVU.get(index));
                    listVUCrear.remove(crearIndex);
                } else {
                    listVUBorrar.add(filtrarVU.get(index));
                }
                int VCIndex = vigenciasUbicaciones.indexOf(filtrarVU.get(index));
                vigenciasUbicaciones.remove(VCIndex);
                filtrarVU.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVUEmpleado");
            index = -1;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            System.out.println("Activar");
            vuFecha = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuFecha");
            vuFecha.setFilterStyle("width: 60px");
            vuDescripcion = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuDescripcion");
            vuDescripcion.setFilterStyle("");
            vuCiudad = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuCiudad");
            vuCiudad.setFilterStyle("");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosVUEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            vuFecha = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuFecha");
            vuFecha.setFilterStyle("display: none; visibility: hidden;");
            vuDescripcion = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuDescripcion");
            vuDescripcion.setFilterStyle("display: none; visibility: hidden;");
            vuCiudad = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuCiudad");
            vuCiudad.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVUEmpleado");
            bandera = 0;
            filtrarVU = null;
            tipoLista = 0;
        }
    }

    //SALIR
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            vuFecha = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuFecha");
            vuFecha.setFilterStyle("display: none; visibility: hidden;");
            vuDescripcion = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuDescripcion");
            vuDescripcion.setFilterStyle("display: none; visibility: hidden;");
            vuCiudad = (Column) c.getViewRoot().findComponent("form:datosVUEmpleado:vuCiudad");
            vuCiudad.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            context.update("form:datosVUEmpleado");
            bandera = 0;
            filtrarVU = null;
            tipoLista = 0;
        }

        listVUBorrar.clear();
        listVUCrear.clear();
        listVUModificar.clear();
        index = -1;
        k = 0;
        vigenciasUbicaciones = null;
        guardado = true;
        context.update("form:ACEPTAR");
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)

    public void asignarIndex(Integer indice, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        context.update("form:UbicacionesGeograficasDialogo");
        context.execute("UbicacionesGeograficasDialogo.show()");
    }

    //LOVS
    //CIUDAD
    public void actualizarUbicacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasUbicaciones.get(index).setUbicacion(UbicacionSelecionada);
                if (!listVUCrear.contains(vigenciasUbicaciones.get(index))) {
                    if (listVUModificar.isEmpty()) {
                        listVUModificar.add(vigenciasUbicaciones.get(index));
                    } else if (!listVUModificar.contains(vigenciasUbicaciones.get(index))) {
                        listVUModificar.add(vigenciasUbicaciones.get(index));
                    }
                }
            } else {
                filtrarVU.get(index).setUbicacion(UbicacionSelecionada);
                if (!listVUCrear.contains(filtrarVU.get(index))) {
                    if (listVUModificar.isEmpty()) {
                        listVUModificar.add(filtrarVU.get(index));
                    } else if (!listVUModificar.contains(filtrarVU.get(index))) {
                        listVUModificar.add(filtrarVU.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVUEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setUbicacion(UbicacionSelecionada);
            context.update("formularioDialogos:nuevaVU");
        } else if (tipoActualizacion == 2) {
            duplicarVU.setUbicacion(UbicacionSelecionada);
            context.update("formularioDialogos:duplicarVU");
        }
        filtradoUbicaciones = null;
        UbicacionSelecionada = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("UbicacionesGeograficasDialogo.hide()");
        context.reset("form:lovUbicaciones:globalFilter");
        context.update("form:lovUbicaciones");
    }

    public void cancelarCambioUbicacion() {
        filtradoUbicaciones = null;
        UbicacionSelecionada = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                context.update("form:UbicacionesGeograficasDialogo");
                context.execute("UbicacionesGeograficasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    public void exportPDF() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosVUEmpleadoExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasUbicacionesGeograficasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    public void exportXLS() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosVUEmpleadoExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasUbicacionesGeograficasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }
    //EVENTO FILTRAR

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }
    //GETTERS AND SETTERS

    public List<VigenciasUbicaciones> getVigenciasUbicaciones() {
        try {
            if (vigenciasUbicaciones == null) {
                return vigenciasUbicaciones = administrarVigenciasUbicaciones.vigenciasUbicacionesEmpleado(BigInteger.valueOf(10661039));
                //return vigenciasUbicaciones = administrarVigenciasUbicaciones.vigenciasUbicacionesEmpleado(secuenciaEmpleado);
            } else {
                return vigenciasUbicaciones;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getVigenciasUbicacionsEmpleado ");
            return null;
        }
    }

    public void setVigenciasUbicaciones(List<VigenciasUbicaciones> vigenciasUbicaciones) {
        this.vigenciasUbicaciones = vigenciasUbicaciones;
    }

    public Empleados getEmpleado() {
        try {
            empleado = administrarVigenciasUbicaciones.buscarEmpleado(BigInteger.valueOf(10661039));
        } catch (Exception e) {
            System.out.println("Error getEmpleado (ControlVigenciasUbicaciones)");
        }
        return empleado;
    }

    public List<VigenciasUbicaciones> getFiltrarVU() {
        return filtrarVU;
    }

    public void setFiltrarVU(List<VigenciasUbicaciones> filtrarVU) {
        this.filtrarVU = filtrarVU;
    }

    public VigenciasUbicaciones getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasUbicaciones nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }


    /*
     public void verProceso() {
     for (int i = 0; i < listVUModificar.size(); i++) {
     System.out.println("Las que se Modificaron:         " + " Fecha:   " + formatoFecha.format(listVUModificar.get(i).getFechavigencia()) + "|   Estructura:  " + listVUModificar.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + listVUModificar.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + listVUModificar.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + listVUModificar.get(i).getIniciosustitucion());
     }
     System.out.println(".................................................");
     for (int i = 0; i < listVUCrear.size(); i++) {
     System.out.println("Las que se van a Crear:         " + " Fecha:   " + formatoFecha.format(listVUCrear.get(i).getFechavigencia()) + "|   Estructura:  " + listVUCrear.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + listVUCrear.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + listVUCrear.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + listVUCrear.get(i).getEstructura().getCentrocosto().getNombre());
     }
     System.out.println(".................................................");
     for (int i = 0; i < listVUBorrar.size(); i++) {
     System.out.println("Las que se van a Borrar:         " + " Fecha:   " + formatoFecha.format(listVUBorrar.get(i).getFechavigencia()) + "|   Estructura:  " + listVUBorrar.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + listVUBorrar.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + listVUBorrar.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + listVUBorrar.get(i).getEstructura().getCentrocosto().getNombre());
     }
     System.out.println(".................................................");
     for (int i = 0; i < vigenciasTiposContratoEmpleado.size(); i++) {
     System.out.println("lista total:         " + " Fecha:   " + formatoFecha.format(vigenciasTiposContratoEmpleado.get(i).getFechavigencia()) + "|   Estructura:  " + vigenciasTiposContratoEmpleado.get(i).getMotivocontrato().getNombre() + "|   Motivo:  " + vigenciasTiposContratoEmpleado.get(i).getTipocontrato().getNombre() + "|   Nombre Cargo:  " + vigenciasTiposContratoEmpleado.get(i).getCiudad().getNombre() + "|   Centro Costo:  " + vigenciasTiposContratoEmpleado.get(i).getEstructura().getCentrocosto().getNombre());
     }
     System.out.println("................................................. FIN.");
     }*/
    public List<UbicacionesGeograficas> getListaUbicaciones() {
        if (listaUbicaciones.isEmpty()) {
            listaUbicaciones = administrarVigenciasUbicaciones.ubicacionesGeograficas();
        }
        return listaUbicaciones;
    }

    public void setListaUbicaciones(List<UbicacionesGeograficas> listaUbicaciones) {
        this.listaUbicaciones = listaUbicaciones;
    }

    public UbicacionesGeograficas getUbicacionSelecionada() {
        return UbicacionSelecionada;
    }

    public void setUbicacionSelecionada(UbicacionesGeograficas UbicacionSelecionada) {
        this.UbicacionSelecionada = UbicacionSelecionada;
    }

    public List<UbicacionesGeograficas> getFiltradoUbicaciones() {
        return filtradoUbicaciones;
    }

    public void setFiltradoUbicaciones(List<UbicacionesGeograficas> filtradoUbicaciones) {
        this.filtradoUbicaciones = filtradoUbicaciones;
    }

    public VigenciasUbicaciones getEditarVU() {
        return editarVU;
    }

    public void setEditarVU(VigenciasUbicaciones editarVU) {
        this.editarVU = editarVU;
    }

    public VigenciasUbicaciones getDuplicarVU() {
        return duplicarVU;
    }

    public void setDuplicarVU(VigenciasUbicaciones duplicarVU) {
        this.duplicarVU = duplicarVU;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public UbicacionesGeograficas getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(UbicacionesGeograficas vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }
}

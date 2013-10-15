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
public class ControlVigenciasContratos implements Serializable {

    @EJB
    AdministrarVigenciasContratosInterface administrarVigenciasContratos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Contratos
    private List<VigenciasContratos> vigenciasContratos;
    private List<VigenciasContratos> filtrarVC;
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
    private boolean guardado, guardarOk;
    //crear VC
    public VigenciasContratos nuevaVigencia;
    private List<VigenciasContratos> listVCCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasContratos> listVCBorrar;
    //editar celda
    private VigenciasContratos editarVC;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasContratos duplicarVC;
    //String Variables AutoCompletar
    private String tipoContrato, legislacionLaboral;
    //Boolean AutoCompletar
    private boolean permitirIndex;
    private BigInteger backUpSecRegistro;
    private BigInteger secRegistro;

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
        k = 0;
        //modificar aficiones
        listVCModificar = new ArrayList<VigenciasContratos>();
        //editar
        editarVC = new VigenciasContratos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar 
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasContratos();
        nuevaVigencia.setContrato(new Contratos());
        nuevaVigencia.setTipocontrato(new TiposContratos());
        permitirIndex = true;
    }

    //EMPLEADO DE LA VIGENCIA
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(Empleados empl) {
        vigenciasContratos = null;
        empleado = empl;
    }

    public void modificarVC(int indice) {
        if (tipoLista == 0) {
            if (!listVCCrear.contains(vigenciasContratos.get(indice))) {

                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciasContratos.get(indice));
                } else if (!listVCModificar.contains(vigenciasContratos.get(indice))) {
                    listVCModificar.add(vigenciasContratos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        }
        if (tipoLista == 1) {
            if (!listVCCrear.contains(filtrarVC.get(indice))) {

                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(filtrarVC.get(indice));
                } else if (!listVCModificar.contains(filtrarVC.get(indice))) {
                    listVCModificar.add(filtrarVC.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
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
            if (tipoLista == 0) {
                vigenciasContratos.get(indice).getContrato().setDescripcion(tipoContrato);
            } else {
                filtrarVC.get(indice).getContrato().setDescripcion(tipoContrato);
            }
            for (int i = 0; i < listaContratos.size(); i++) {
                if (listaContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasContratos.get(indice).setContrato(listaContratos.get(indiceUnicoElemento));
                } else {
                    filtrarVC.get(indice).setContrato(listaContratos.get(indiceUnicoElemento));
                }
                listaContratos.clear();
                getListaContratos();
            } else {
                permitirIndex = false;
                context.update("form:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOCONTRATO")) {
            if (tipoLista == 0) {
                vigenciasContratos.get(indice).getTipocontrato().setNombre(legislacionLaboral);
            } else {
                filtrarVC.get(indice).getTipocontrato().setNombre(legislacionLaboral);
            }
            for (int i = 0; i < listaTiposContratos.size(); i++) {
                if (listaTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasContratos.get(indice).setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                } else {
                    filtrarVC.get(indice).setTipocontrato(listaTiposContratos.get(indiceUnicoElemento));
                }
                listaTiposContratos.clear();
                getListaTiposContratos();
            } else {
                permitirIndex = false;
                context.update("form:TiposContratoDialogo");
                context.execute("TiposContratoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVCCrear.contains(vigenciasContratos.get(indice))) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasContratos.get(indice));
                    } else if (!listVCModificar.contains(vigenciasContratos.get(indice))) {
                        listVCModificar.add(vigenciasContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVCCrear.contains(filtrarVC.get(indice))) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filtrarVC.get(indice));
                    } else if (!listVCModificar.contains(filtrarVC.get(indice))) {
                        listVCModificar.add(filtrarVC.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
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
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla Vigencias Contratos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = vigenciasContratos.get(index).getSecuencia();
            if (cualCelda == 2) {
                legislacionLaboral = vigenciasContratos.get(index).getContrato().getDescripcion();
            } else if (cualCelda == 3) {
                tipoContrato = vigenciasContratos.get(index).getTipocontrato().getNombre();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    //GUARDAR
    /**
     * Metodo que guarda los cambios efectuados en la pagina Vigencias Contratos
     */
    public void guardarCambiosVC() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Contratos");
            if (!listVCBorrar.isEmpty()) {
                for (int i = 0; i < listVCBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    administrarVigenciasContratos.borrarVC(listVCBorrar.get(i));
                }
                listVCBorrar.clear();
            }
            if (!listVCCrear.isEmpty()) {
                for (int i = 0; i < listVCCrear.size(); i++) {
                    System.out.println("Creando...");
                    administrarVigenciasContratos.crearVC(listVCCrear.get(i));
                }
                listVCCrear.clear();
            }
            if (!listVCModificar.isEmpty()) {
                administrarVigenciasContratos.modificarVC(listVCModificar);
                listVCModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            vigenciasContratos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVCEmpleado");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
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
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
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
        k = 0;
        vigenciasContratos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVCEmpleado");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVC = vigenciasContratos.get(index);
            }
            if (tipoLista == 1) {
                editarVC = filtrarVC.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
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
        }
        index = -1;
        secRegistro = null;
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciasContratos
     */
    public void agregarNuevaVC() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filtrarVC = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevaVigencia.setSecuencia(l);
        nuevaVigencia.setEmpleado(empleado);
        listVCCrear.add(nuevaVigencia);

        vigenciasContratos.add(nuevaVigencia);
        nuevaVigencia = new VigenciasContratos();
        nuevaVigencia.setContrato(new Contratos());
        nuevaVigencia.setTipocontrato(new TiposContratos());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVCEmpleado");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        index = -1;
        secRegistro = null;
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
        if (index >= 0) {
            duplicarVC = new VigenciasContratos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVC.setSecuencia(l);
                duplicarVC.setEmpleado(vigenciasContratos.get(index).getEmpleado());
                duplicarVC.setFechainicial(vigenciasContratos.get(index).getFechainicial());
                duplicarVC.setFechafinal(vigenciasContratos.get(index).getFechafinal());
                duplicarVC.setContrato(vigenciasContratos.get(index).getContrato());
                duplicarVC.setTipocontrato(vigenciasContratos.get(index).getTipocontrato());
            }
            if (tipoLista == 1) {
                duplicarVC.setSecuencia(l);
                duplicarVC.setEmpleado(filtrarVC.get(index).getEmpleado());
                duplicarVC.setFechainicial(filtrarVC.get(index).getFechafinal());
                duplicarVC.setFechafinal(filtrarVC.get(index).getFechafinal());
                duplicarVC.setContrato(filtrarVC.get(index).getContrato());
                duplicarVC.setTipocontrato(filtrarVC.get(index).getTipocontrato());
            }

            RequestContext context = RequestContext.getCurrentInstance();
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

        vigenciasContratos.add(duplicarVC);
        listVCCrear.add(duplicarVC);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVCEmpleado");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filtrarVC = null;
            tipoLista = 0;
        }
        duplicarVC = new VigenciasContratos();
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

        if (index >= 0) {
            if (tipoLista == 0) {
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
            }
            if (tipoLista == 1) {
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
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVCEmpleado");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (bandera == 0) {
            System.out.println("Activar");
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("width: 60px");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("width: 60px");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("width: 60px");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
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
        if (bandera == 1) {
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVCEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
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
        k = 0;
        vigenciasContratos = null;
        guardado = true;
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
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }

        if (list == 0) {
            context.update("form:ContratosDialogo");
            context.execute("ContratosDialogo.show()");
        } else if (list == 1) {
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
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasContratos.get(index).setContrato(contratoSelecionado);
                if (!listVCCrear.contains(vigenciasContratos.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    } else if (!listVCModificar.contains(vigenciasContratos.get(index))) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    }
                }
            } else {
                filtrarVC.get(index).setContrato(contratoSelecionado);
                if (!listVCCrear.contains(filtrarVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filtrarVC.get(index));
                    } else if (!listVCModificar.contains(filtrarVC.get(index))) {
                        listVCModificar.add(filtrarVC.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setContrato(contratoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setContrato(contratoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVC");
        }
        filtradoContratos = null;
        contratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
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
    }

    //TIPO CONTRATO
    /**
     * Metodo que actualiza el tipo contrato seleccionado
     */
    public void actualizarTipoContrato() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasContratos.get(index).setTipocontrato(tipoContratoSelecionado);
                if (!listVCCrear.contains(vigenciasContratos.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    } else if (!listVCModificar.contains(vigenciasContratos.get(index))) {
                        listVCModificar.add(vigenciasContratos.get(index));
                    }
                }
            } else {
                filtrarVC.get(index).setTipocontrato(tipoContratoSelecionado);
                if (!listVCCrear.contains(filtrarVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filtrarVC.get(index));
                    } else if (!listVCModificar.contains(filtrarVC.get(index))) {
                        listVCModificar.add(filtrarVC.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setTipocontrato(tipoContratoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setTipocontrato(tipoContratoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVC");
        }
        filtradoTiposContratos = null;
        tipoContratoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
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
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de la tabla con respecto a la
     * columna tipos contratos o contratos
     */
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 3) {
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
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciasContratos != null) {
            if (secRegistro != null) {
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
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONTRATOS")) {
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
                return vigenciasContratos = administrarVigenciasContratos.VigenciasContratosEmpleado(empleado.getSecuencia());
            } else {
                return vigenciasContratos;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getVigenciasContratosEmpleado ");
            return null;
        }
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
        if (listaContratos.isEmpty()) {
            listaContratos = administrarVigenciasContratos.contratos();
        }
        return listaContratos;
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
        if (listaTiposContratos.isEmpty()) {
            listaTiposContratos = administrarVigenciasContratos.tiposContratos();
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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import Administrar.AdministrarVigenciasReformasLaborales;
import Entidades.Empleados;
import Entidades.ReformasLaborales;
import Entidades.VigenciasReformasLaborales;
import InterfaceAdministrar.AdministrarVigenciasReformasLaboralesInterface;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlVigenciasReformasLaborales implements Serializable {

    @EJB
    AdministrarVigenciasReformasLaboralesInterface administrarVigenciasReformasLaborales;
    //Vigencias Cargos
    private List<VigenciasReformasLaborales> vigenciasReformasLaborales;
    private List<VigenciasReformasLaborales> filtrarVRL;
    private List<ReformasLaborales> listaReformasLaborales;
    private ReformasLaborales reformaLaboralSelecionada;
    private List<ReformasLaborales> filtradoReformasLaborales;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vrlFecha, vrlNombre;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasReformasLaborales> listVRLModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public VigenciasReformasLaborales nuevaVigencia;
    private List<VigenciasReformasLaborales> listVRLCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasReformasLaborales> listVRLBorrar;
    //editar celda
    private VigenciasReformasLaborales editarVRL;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasReformasLaborales duplicarVRL;
    private String reformaLaboral;
    private boolean permitirIndex;

    /**
     * Constructor de la clases Controlador
     */
    public ControlVigenciasReformasLaborales() {
        
        vigenciasReformasLaborales = null;
        listaReformasLaborales = new ArrayList<ReformasLaborales>();
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVRLBorrar = new ArrayList<VigenciasReformasLaborales>();
        //crear aficiones
        listVRLCrear = new ArrayList<VigenciasReformasLaborales>();
        k = 0;
        //modificar aficiones
        listVRLModificar = new ArrayList<VigenciasReformasLaborales>();
        //editar
        editarVRL = new VigenciasReformasLaborales();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasReformasLaborales();
        nuevaVigencia.setReformalaboral(new ReformasLaborales());
        
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
        vigenciasReformasLaborales = null;
        empleado = empl;
    }

    public void modificarVRL(int indice) {
        if (tipoLista == 0) {
            if (!listVRLCrear.contains(vigenciasReformasLaborales.get(indice))) {

                if (listVRLModificar.isEmpty()) {
                    listVRLModificar.add(vigenciasReformasLaborales.get(indice));
                } else if (!listVRLModificar.contains(vigenciasReformasLaborales.get(indice))) {
                    listVRLModificar.add(vigenciasReformasLaborales.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
        } else {
            if (!listVRLCrear.contains(filtrarVRL.get(indice))) {

                if (listVRLModificar.isEmpty()) {
                    listVRLModificar.add(filtrarVRL.get(indice));
                } else if (!listVRLModificar.contains(filtrarVRL.get(indice))) {
                    listVRLModificar.add(filtrarVRL.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciasReformasLaborales de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVRL(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMALABORAL")) {
            if (tipoLista == 0) {
                vigenciasReformasLaborales.get(indice).getReformalaboral().setNombre(reformaLaboral);
            } else {
                filtrarVRL.get(indice).getReformalaboral().setNombre(reformaLaboral);
            }
            for (int i = 0; i < listaReformasLaborales.size(); i++) {
                if (listaReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasReformasLaborales.get(indice).setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));
                } else {
                    filtrarVRL.get(indice).setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));
                }
                listaReformasLaborales.clear();
                getListaReformasLaborales();
            } else {
                permitirIndex = false;
                context.update("form:ReformasLaboralesDialogo");
                context.execute("ReformasLaboralesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVRLCrear.contains(vigenciasReformasLaborales.get(indice))) {

                    if (listVRLModificar.isEmpty()) {
                        listVRLModificar.add(vigenciasReformasLaborales.get(indice));
                    } else if (!listVRLModificar.contains(vigenciasReformasLaborales.get(indice))) {
                        listVRLModificar.add(vigenciasReformasLaborales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            } else {
                if (!listVRLCrear.contains(filtrarVRL.get(indice))) {

                    if (listVRLModificar.isEmpty()) {
                        listVRLModificar.add(filtrarVRL.get(indice));
                    } else if (!listVRLModificar.contains(filtrarVRL.get(indice))) {
                        listVRLModificar.add(filtrarVRL.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            }
        }
        context.update("form:datosVRLEmpleado");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("REFORMALABORAL")) {
            if (tipoNuevo == 1) {
                reformaLaboral = nuevaVigencia.getReformalaboral().getNombre();
            } else if (tipoNuevo == 2) {
                reformaLaboral = duplicarVRL.getReformalaboral().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMALABORAL")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getReformalaboral().setNombre(reformaLaboral);
            } else if (tipoNuevo == 2) {
                duplicarVRL.getReformalaboral().setNombre(reformaLaboral);
            }
            for (int i = 0; i < listaReformasLaborales.size(); i++) {
                if (listaReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaReformaLaboral");
                } else if (tipoNuevo == 2) {
                    duplicarVRL.setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarReformaLaboral");
                }
                listaReformasLaborales.clear();
                getListaReformasLaborales();
            } else {
                context.update("form:ReformasLaboralesDialogo");
                context.execute("ReformasLaboralesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaReformaLaboral");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarReformaLaboral");
                }
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasReformasLaborales
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (cualCelda == 1) {
                reformaLaboral = vigenciasReformasLaborales.get(index).getReformalaboral().getNombre();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }
    //GUARDAR

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasReformasLaborales
     */
    public void guardarCambiosVRL() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Reformas Laborales");
            if (!listVRLBorrar.isEmpty()) {
                for (int i = 0; i < listVRLBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    administrarVigenciasReformasLaborales.borrarVRL(listVRLBorrar.get(i));
                }
                listVRLBorrar.clear();
            }
            if (!listVRLCrear.isEmpty()) {
                for (int i = 0; i < listVRLCrear.size(); i++) {
                    System.out.println("Creando...");
                    administrarVigenciasReformasLaborales.crearVRL(listVRLCrear.get(i));
                }
                listVRLCrear.clear();
            }
            if (!listVRLModificar.isEmpty()) {
                administrarVigenciasReformasLaborales.modificarVRL(listVRLModificar);
                listVRLModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            vigenciasReformasLaborales = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVRLEmpleado");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        index = -1;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vrlFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }

        listVRLBorrar.clear();
        listVRLCrear.clear();
        listVRLModificar.clear();
        index = -1;
        k = 0;
        vigenciasReformasLaborales = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVRLEmpleado");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVRL = vigenciasReformasLaborales.get(index);
            }
            if (tipoLista == 1) {
                editarVRL = filtrarVRL.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarReformaLaboral");
                context.execute("editarReformaLaboral.show()");
                cualCelda = -1;
            }
        }
        index = -1;
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciaReformaLaboral
     */
    public void agregarNuevaVRL() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vrlFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevaVigencia.setSecuencia(l);
        nuevaVigencia.setEmpleado(empleado);
        listVRLCrear.add(nuevaVigencia);

        vigenciasReformasLaborales.add(nuevaVigencia);
        nuevaVigencia = new VigenciasReformasLaborales();
        nuevaVigencia.setReformalaboral(new ReformasLaborales());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVRLEmpleado");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        index = -1;
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVRL() {
        nuevaVigencia = new VigenciasReformasLaborales();
        nuevaVigencia.setReformalaboral(new ReformasLaborales());
        index = -1;
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVigenciaRL() {
        if (index >= 0) {
            duplicarVRL = new VigenciasReformasLaborales();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVRL.setSecuencia(l);
                duplicarVRL.setEmpleado(vigenciasReformasLaborales.get(index).getEmpleado());
                duplicarVRL.setFechavigencia(vigenciasReformasLaborales.get(index).getFechavigencia());
                duplicarVRL.setReformalaboral(vigenciasReformasLaborales.get(index).getReformalaboral());
            }
            if (tipoLista == 1) {
                duplicarVRL.setSecuencia(l);
                duplicarVRL.setEmpleado(filtrarVRL.get(index).getEmpleado());
                duplicarVRL.setFechavigencia(filtrarVRL.get(index).getFechavigencia());
                duplicarVRL.setReformalaboral(filtrarVRL.get(index).getReformalaboral());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVRL");
            context.execute("DuplicarRegistroVRL.show()");
            index = -1;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasReformasLaborales
     */
    public void confirmarDuplicar() {

        vigenciasReformasLaborales.add(duplicarVRL);
        listVRLCrear.add(duplicarVRL);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVRLEmpleado");
        index = -1;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            vrlFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }
        duplicarVRL = new VigenciasReformasLaborales();
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarduplicarVRL() {
        duplicarVRL = new VigenciasReformasLaborales();
        duplicarVRL.setReformalaboral(new ReformasLaborales());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVRL() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVRLModificar.isEmpty() && listVRLModificar.contains(vigenciasReformasLaborales.get(index))) {
                    int modIndex = listVRLModificar.indexOf(vigenciasReformasLaborales.get(index));
                    listVRLModificar.remove(modIndex);
                    listVRLBorrar.add(vigenciasReformasLaborales.get(index));
                } else if (!listVRLCrear.isEmpty() && listVRLCrear.contains(vigenciasReformasLaborales.get(index))) {
                    int crearIndex = listVRLCrear.indexOf(vigenciasReformasLaborales.get(index));
                    listVRLCrear.remove(crearIndex);
                } else {
                    listVRLBorrar.add(vigenciasReformasLaborales.get(index));
                }
                vigenciasReformasLaborales.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVRLModificar.isEmpty() && listVRLModificar.contains(filtrarVRL.get(index))) {
                    int modIndex = listVRLModificar.indexOf(filtrarVRL.get(index));
                    listVRLModificar.remove(modIndex);
                    listVRLBorrar.add(filtrarVRL.get(index));
                } else if (!listVRLCrear.isEmpty() && listVRLCrear.contains(filtrarVRL.get(index))) {
                    int crearIndex = listVRLCrear.indexOf(filtrarVRL.get(index));
                    listVRLCrear.remove(crearIndex);
                } else {
                    listVRLBorrar.add(filtrarVRL.get(index));
                }
                int VCIndex = vigenciasReformasLaborales.indexOf(filtrarVRL.get(index));
                vigenciasReformasLaborales.remove(VCIndex);
                filtrarVRL.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVRLEmpleado");
            index = -1;

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
            vrlFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("width: 60px");
            vrlNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("");
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            vrlFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            vrlFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }

        listVRLBorrar.clear();
        listVRLCrear.clear();
        listVRLModificar.clear();
        index = -1;
        k = 0;
        vigenciasReformasLaborales = null;
        guardado = true;
        
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)

    /**
     * Metodo que ejecuta el dialogo de reforma laboral
     *
     * @param indice Fila de la tabla
     * @param list Lista filtrada - Lista real
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
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
        context.update("form:ReformasLaboralesDialogo");
        context.execute("ReformasLaboralesDialogo.show()");
    }

    //LOVS
    //CIUDAD
    /**
     * Metodo que actualiza la reforma laboral seleccionada
     */
    public void actualizarReformaLaboral() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasReformasLaborales.get(index).setReformalaboral(reformaLaboralSelecionada);
                if (!listVRLCrear.contains(vigenciasReformasLaborales.get(index))) {
                    if (listVRLModificar.isEmpty()) {
                        listVRLModificar.add(vigenciasReformasLaborales.get(index));
                    } else if (!listVRLModificar.contains(vigenciasReformasLaborales.get(index))) {
                        listVRLModificar.add(vigenciasReformasLaborales.get(index));
                    }
                }
            } else {
                filtrarVRL.get(index).setReformalaboral(reformaLaboralSelecionada);
                if (!listVRLCrear.contains(filtrarVRL.get(index))) {
                    if (listVRLModificar.isEmpty()) {
                        listVRLModificar.add(filtrarVRL.get(index));
                    } else if (!listVRLModificar.contains(filtrarVRL.get(index))) {
                        listVRLModificar.add(filtrarVRL.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setReformalaboral(reformaLaboralSelecionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVRL");
        } else if (tipoActualizacion == 2) {
            duplicarVRL.setReformalaboral(reformaLaboralSelecionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVRL");
        }
        filtradoReformasLaborales = null;
        reformaLaboralSelecionada = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre reforma laboral
     */
    public void cancelarCambioReformaLaboral() {
        filtradoReformasLaborales = null;
        reformaLaboralSelecionada = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de la tabla con respecto a las
     * reformas laborales
     */
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                context.update("form:ReformasLaboralesDialogo");
                context.execute("ReformasLaboralesDialogo.show()");
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVRLEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasReformasLaboralesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVRLEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasReformasLaboralesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
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
    //GETTERS AND SETTERS

    /**
     * Metodo que obtiene las VigenciasReformasLaborales de un empleado, en caso
     * de ser null por medio del administrar hace el llamado para almacenarlo
     *
     * @return listVC Lista VigenciasReformasLaborales
     */
    public List<VigenciasReformasLaborales> getVigenciasReformasLaboralesEmpleado() {
        try {
            if (vigenciasReformasLaborales == null) {
                return vigenciasReformasLaborales = administrarVigenciasReformasLaborales.vigenciasReformasLaboralesEmpleado(empleado.getSecuencia());
            } else {
                return vigenciasReformasLaborales;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getVigenciasReformasLaboralesEmpleado ");
            return null;
        }
    }

    public void setVigenciasReformasLaborales(List<VigenciasReformasLaborales> vigenciasReformasLaborales) {
        this.vigenciasReformasLaborales = vigenciasReformasLaborales;
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

    public List<VigenciasReformasLaborales> getFiltrarVRL() {
        return filtrarVRL;
    }

    public void setFiltrarVRL(List<VigenciasReformasLaborales> filtrarVRL) {
        this.filtrarVRL = filtrarVRL;
    }

    public VigenciasReformasLaborales getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasReformasLaborales nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    /**
     * Metodo que obtiene la lista de reformas laborales, en caso de ser null
     * por medio del administrar los obtiene
     *
     * @return listTC Lista Reformas Laborales
     */
    public List<ReformasLaborales> getListaReformasLaborales() {
        if (listaReformasLaborales.isEmpty()) {
            listaReformasLaborales = administrarVigenciasReformasLaborales.reformasLaborales();
        }
        return listaReformasLaborales;
    }

    public void setListaReformasLaborales(List<ReformasLaborales> listaLaboraleses) {
        this.listaReformasLaborales = listaLaboraleses;
    }

    public List<ReformasLaborales> getFiltradoReformasLaborales() {
        return filtradoReformasLaborales;
    }

    public void setFiltradoReformasLaborales(List<ReformasLaborales> filtradoReformasLaborales) {
        this.filtradoReformasLaborales = filtradoReformasLaborales;
    }

    public VigenciasReformasLaborales getEditarVRL() {
        return editarVRL;
    }

    public void setEditarVRL(VigenciasReformasLaborales editarVRL) {
        this.editarVRL = editarVRL;
    }

    public VigenciasReformasLaborales getDuplicarVRL() {
        return duplicarVRL;
    }

    public void setDuplicarVRL(VigenciasReformasLaborales duplicarVRL) {
        this.duplicarVRL = duplicarVRL;
    }

    public ReformasLaborales getReformaLaboralSelecionada() {
        return reformaLaboralSelecionada;
    }

    public void setReformaLaboralSelecionada(ReformasLaborales reformaLaboralSelecionada) {
        this.reformaLaboralSelecionada = reformaLaboralSelecionada;
    }
}

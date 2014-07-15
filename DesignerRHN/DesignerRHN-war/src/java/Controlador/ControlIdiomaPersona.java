/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Idiomas;
import Entidades.IdiomasPersonas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarIdiomaPersonaInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlIdiomaPersona implements Serializable {

    @EJB
    AdministrarIdiomaPersonaInterface administrarIdiomaPersona;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Cargos
    private List<IdiomasPersonas> listIdiomasPersonas;
    private List<IdiomasPersonas> filtrarListIdiomasPersonas;
    private IdiomasPersonas idiomaTablaSeleccionado;

    private List<Idiomas> listIdiomas;
    private Idiomas idiomaSeleccionado;
    private List<Idiomas> filtrarListIdiomas;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column idIdioma, idConversacion, idLectura, idEscritura;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<IdiomasPersonas> listIdiomaPersonaModificar;
    private boolean guardado;
    //crear VC
    public IdiomasPersonas nuevaIdiomaPersona;
    private List<IdiomasPersonas> listIdiomaPersonaCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<IdiomasPersonas> listIdiomaPersonaBorrar;
    //editar celda
    private IdiomasPersonas editarIdiomaPersona;
    private int cualCelda, tipoLista;
    //duplicar
    private IdiomasPersonas duplicarIdiomaPersona;
    private String idioma;
    private boolean permitirIndex;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Empleados empleadoActual;
    //
    private String altoTabla;
    private String infoRegistro;
    private String infoRegistroIdioma;

    public ControlIdiomaPersona() {
        altoTabla = "280";
        listIdiomasPersonas = null;
        listIdiomas = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listIdiomaPersonaBorrar = new ArrayList<IdiomasPersonas>();
        //crear aficiones
        listIdiomaPersonaCrear = new ArrayList<IdiomasPersonas>();
        k = 0;
        //modificar aficiones
        listIdiomaPersonaModificar = new ArrayList<IdiomasPersonas>();
        //editar
        editarIdiomaPersona = new IdiomasPersonas();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaIdiomaPersona = new IdiomasPersonas();

        nuevaIdiomaPersona.setIdioma(new Idiomas());
        secRegistro = null;
        permitirIndex = true;
        backUpSecRegistro = null;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarIdiomaPersona.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger secuencia) {
        empleadoActual = new Empleados();
        empleadoActual = administrarIdiomaPersona.empleadoActual(secuencia);
        listIdiomasPersonas = null;
        getListIdiomasPersonas();
        if (listIdiomasPersonas != null) {
            infoRegistro = "Cantidad de registros : " + listIdiomasPersonas.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
    }

    public void modificarIdiomaPersona(int indice) {
        if (tipoLista == 0) {
            if (!listIdiomaPersonaCrear.contains(listIdiomasPersonas.get(indice))) {

                if (listIdiomaPersonaModificar.isEmpty()) {
                    listIdiomaPersonaModificar.add(listIdiomasPersonas.get(indice));
                } else if (!listIdiomaPersonaModificar.contains(listIdiomasPersonas.get(indice))) {
                    listIdiomaPersonaModificar.add(listIdiomasPersonas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        } else {
            if (!listIdiomaPersonaCrear.contains(filtrarListIdiomasPersonas.get(indice))) {

                if (listIdiomaPersonaModificar.isEmpty()) {
                    listIdiomaPersonaModificar.add(filtrarListIdiomasPersonas.get(indice));
                } else if (!listIdiomaPersonaModificar.contains(filtrarListIdiomasPersonas.get(indice))) {
                    listIdiomaPersonaModificar.add(filtrarListIdiomasPersonas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    
    public void modificarIdiomaPersona(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("IDIOMAS")) {
            if (tipoLista == 0) {
                listIdiomasPersonas.get(indice).getIdioma().setNombre(idioma);
            } else {
                filtrarListIdiomasPersonas.get(indice).getIdioma().setNombre(idioma);
            }
            for (int i = 0; i < listIdiomas.size(); i++) {
                if (listIdiomas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listIdiomasPersonas.get(indice).setIdioma(listIdiomas.get(indiceUnicoElemento));
                } else {
                    filtrarListIdiomasPersonas.get(indice).setIdioma(listIdiomas.get(indiceUnicoElemento));
                }
                listIdiomas.clear();
                getListIdiomas();
            } else {
                permitirIndex = false;
                eliminarRegistrosIdiomaLov();
                context.update("form:IdiomasDialogo");
                context.execute("IdiomasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listIdiomaPersonaCrear.contains(listIdiomasPersonas.get(indice))) {

                    if (listIdiomaPersonaModificar.isEmpty()) {
                        listIdiomaPersonaModificar.add(listIdiomasPersonas.get(indice));
                    } else if (!listIdiomaPersonaModificar.contains(listIdiomasPersonas.get(indice))) {
                        listIdiomaPersonaModificar.add(listIdiomasPersonas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listIdiomaPersonaCrear.contains(filtrarListIdiomasPersonas.get(indice))) {

                    if (listIdiomaPersonaModificar.isEmpty()) {
                        listIdiomaPersonaModificar.add(filtrarListIdiomasPersonas.get(indice));
                    } else if (!listIdiomaPersonaModificar.contains(filtrarListIdiomasPersonas.get(indice))) {
                        listIdiomaPersonaModificar.add(filtrarListIdiomasPersonas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosIdiomas");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("IDIOMAS")) {
            if (tipoNuevo == 1) {
                idioma = nuevaIdiomaPersona.getIdioma().getNombre();
            } else if (tipoNuevo == 2) {
                idioma = duplicarIdiomaPersona.getIdioma().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("IDIOMAS")) {
            if (tipoNuevo == 1) {
                nuevaIdiomaPersona.getIdioma().setNombre(idioma);
            } else if (tipoNuevo == 2) {
                duplicarIdiomaPersona.getIdioma().setNombre(idioma);
            }
            for (int i = 0; i < listIdiomas.size(); i++) {
                if (listIdiomas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaIdiomaPersona.setIdioma(listIdiomas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaIdioma");
                } else if (tipoNuevo == 2) {
                    duplicarIdiomaPersona.setIdioma(listIdiomas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarIdioma");
                }
                listIdiomas.clear();
                getListIdiomas();
            } else {
                eliminarRegistrosIdiomaLov();
                context.update("form:IdiomasDialogo");
                context.execute("IdiomasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaIdioma");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarIdioma");
                }
            }
        }
    }

   
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listIdiomasPersonas.get(index).getSecuencia();
                if (cualCelda == 0) {
                    idioma = listIdiomasPersonas.get(index).getIdioma().getNombre();
                }
            } else {
                secRegistro = filtrarListIdiomasPersonas.get(index).getSecuencia();
                if (cualCelda == 0) {
                    idioma = filtrarListIdiomasPersonas.get(index).getIdioma().getNombre();
                }
            }
        }
    }
   
    public void guardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!listIdiomaPersonaBorrar.isEmpty()) {
                    administrarIdiomaPersona.borrarIdiomasPersonas(listIdiomaPersonaBorrar);
                    listIdiomaPersonaBorrar.clear();
                }
                if (!listIdiomaPersonaCrear.isEmpty()) {
                    administrarIdiomaPersona.crearIdiomasPersonas(listIdiomaPersonaCrear);
                    listIdiomaPersonaCrear.clear();
                }
                if (!listIdiomaPersonaModificar.isEmpty()) {
                    administrarIdiomaPersona.editarIdiomasPersonas(listIdiomaPersonaModificar);
                    listIdiomaPersonaModificar.clear();
                }
                listIdiomasPersonas = null;
                getListIdiomasPersonas();
                if (listIdiomasPersonas != null) {
                    infoRegistro = "Cantidad de registros : " + listIdiomasPersonas.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                context.update("form:informacionRegistro");
                context.update("form:datosIdiomas");
                k = 0;
                index = -1;
                secRegistro = null;
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
                guardado = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambios : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }
   
    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "280";
            //CERRAR FILTRADO
            idIdioma = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idIdioma");
            idIdioma.setFilterStyle("display: none; visibility: hidden;");
            idConversacion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idConversacion");
            idConversacion.setFilterStyle("display: none; visibility: hidden;");
            idLectura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idLectura");
            idLectura.setFilterStyle("display: none; visibility: hidden;");
            idEscritura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idEscritura");
            idEscritura.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            bandera = 0;
            filtrarListIdiomasPersonas = null;
            tipoLista = 0;
        }

        listIdiomaPersonaBorrar.clear();
        listIdiomaPersonaCrear.clear();
        listIdiomaPersonaModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listIdiomasPersonas = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        getListIdiomasPersonas();
        if (listIdiomasPersonas != null) {
            infoRegistro = "Cantidad de registros : " + listIdiomasPersonas.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:informacionRegistro");
        context.update("form:datosIdiomas");
    }

  
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarIdiomaPersona = listIdiomasPersonas.get(index);
            }
            if (tipoLista == 1) {
                editarIdiomaPersona = filtrarListIdiomasPersonas.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarIdiomaD");
                context.execute("editarIdiomaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarConversacionD");
                context.execute("editarConversacionD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarLecturaD");
                context.execute("editarLecturaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarEscrituraD");
                context.execute("editarEscrituraD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

  
    public void agregarNuevaIdiomaPersona() {
        if (nuevaIdiomaPersona.getIdioma().getSecuencia() != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                altoTabla = "280";
                //CERRAR FILTRADO
                idIdioma = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idIdioma");
                idIdioma.setFilterStyle("display: none; visibility: hidden;");
                idConversacion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idConversacion");
                idConversacion.setFilterStyle("display: none; visibility: hidden;");
                idLectura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idLectura");
                idLectura.setFilterStyle("display: none; visibility: hidden;");
                idEscritura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idEscritura");
                idEscritura.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosIdiomas");
                bandera = 0;
                filtrarListIdiomasPersonas = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevaIdiomaPersona.setSecuencia(l);
            nuevaIdiomaPersona.setPersona(empleadoActual.getPersona());
            listIdiomaPersonaCrear.add(nuevaIdiomaPersona);
            if (listIdiomasPersonas == null) {
                listIdiomasPersonas = new ArrayList<IdiomasPersonas>();
            }
            listIdiomasPersonas.add(nuevaIdiomaPersona);
            nuevaIdiomaPersona = new IdiomasPersonas();
            nuevaIdiomaPersona.setIdioma(new Idiomas());
            RequestContext context = RequestContext.getCurrentInstance();
            getListIdiomasPersonas();
            if (listIdiomasPersonas != null) {
                infoRegistro = "Cantidad de registros : " + listIdiomasPersonas.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            context.update("form:informacionRegistro");
            context.update("form:datosIdiomas");
            context.execute("NuevoRegistroIdiomas.hide()");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            index = -1;
            secRegistro = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNull.show()");
        }
    }
   
    public void limpiarNuevaIdiomaPersona() {
        nuevaIdiomaPersona = new IdiomasPersonas();
        nuevaIdiomaPersona.setIdioma(new Idiomas());
        index = -1;
        secRegistro = null;
    }
   
    public void duplicarIdiomaPersonaM() {
        if (index >= 0) {
            duplicarIdiomaPersona = new IdiomasPersonas();
            if (tipoLista == 0) {
                duplicarIdiomaPersona.setEscritura(listIdiomasPersonas.get(index).getEscritura());
                duplicarIdiomaPersona.setHabla(listIdiomasPersonas.get(index).getHabla());
                duplicarIdiomaPersona.setIdioma(listIdiomasPersonas.get(index).getIdioma());
                duplicarIdiomaPersona.setLectura(listIdiomasPersonas.get(index).getLectura());
                duplicarIdiomaPersona.setPersona(listIdiomasPersonas.get(index).getPersona());
            }
            if (tipoLista == 1) {
                duplicarIdiomaPersona.setEscritura(filtrarListIdiomasPersonas.get(index).getEscritura());
                duplicarIdiomaPersona.setHabla(filtrarListIdiomasPersonas.get(index).getHabla());
                duplicarIdiomaPersona.setIdioma(filtrarListIdiomasPersonas.get(index).getIdioma());
                duplicarIdiomaPersona.setLectura(filtrarListIdiomasPersonas.get(index).getLectura());
                duplicarIdiomaPersona.setPersona(filtrarListIdiomasPersonas.get(index).getPersona());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarIdiomas");
            context.execute("DuplicarRegistroIdiomas.show()");
            index = -1;
            secRegistro = null;
        }
    }

   
    public void confirmarDuplicar() {
        if (duplicarIdiomaPersona.getIdioma().getSecuencia() != null) {
            if (listIdiomasPersonas == null) {
                listIdiomasPersonas = new ArrayList<IdiomasPersonas>();
            }
            k++;
            l = BigInteger.valueOf(k);
            duplicarIdiomaPersona.setSecuencia(l);
            duplicarIdiomaPersona.setPersona(empleadoActual.getPersona());
            listIdiomasPersonas.add(duplicarIdiomaPersona);
            listIdiomaPersonaCrear.add(duplicarIdiomaPersona);
            RequestContext context = RequestContext.getCurrentInstance();
            getListIdiomasPersonas();
            if (listIdiomasPersonas != null) {
                infoRegistro = "Cantidad de registros : " + listIdiomasPersonas.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            context.update("form:informacionRegistro");
            context.update("form:datosIdiomas");
            context.execute("DuplicarRegistroIdiomas.hide()");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                altoTabla = "280";
                //CERRAR FILTRADO
                idIdioma = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idIdioma");
                idIdioma.setFilterStyle("display: none; visibility: hidden;");
                idConversacion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idConversacion");
                idConversacion.setFilterStyle("display: none; visibility: hidden;");
                idLectura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idLectura");
                idLectura.setFilterStyle("display: none; visibility: hidden;");
                idEscritura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idEscritura");
                idEscritura.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosIdiomas");
                bandera = 0;
                filtrarListIdiomasPersonas = null;
                tipoLista = 0;
            }
            duplicarIdiomaPersona = new IdiomasPersonas();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNull.show()");
        }
    }
    
    public void limpiarDuplicar() {
        duplicarIdiomaPersona = new IdiomasPersonas();
        duplicarIdiomaPersona.setIdioma(new Idiomas());
    }

    
    public void borrarIdiomaPersona() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listIdiomaPersonaModificar.isEmpty() && listIdiomaPersonaModificar.contains(listIdiomasPersonas.get(index))) {
                    int modIndex = listIdiomaPersonaModificar.indexOf(listIdiomasPersonas.get(index));
                    listIdiomaPersonaModificar.remove(modIndex);
                    listIdiomaPersonaBorrar.add(listIdiomasPersonas.get(index));
                } else if (!listIdiomaPersonaCrear.isEmpty() && listIdiomaPersonaCrear.contains(listIdiomasPersonas.get(index))) {
                    int crearIndex = listIdiomaPersonaCrear.indexOf(listIdiomasPersonas.get(index));
                    listIdiomaPersonaCrear.remove(crearIndex);
                } else {
                    listIdiomaPersonaBorrar.add(listIdiomasPersonas.get(index));
                }
                listIdiomasPersonas.remove(index);
            }
            if (tipoLista == 1) {
                if (!listIdiomaPersonaModificar.isEmpty() && listIdiomaPersonaModificar.contains(filtrarListIdiomasPersonas.get(index))) {
                    int modIndex = listIdiomaPersonaModificar.indexOf(filtrarListIdiomasPersonas.get(index));
                    listIdiomaPersonaModificar.remove(modIndex);
                    listIdiomaPersonaBorrar.add(filtrarListIdiomasPersonas.get(index));
                } else if (!listIdiomaPersonaCrear.isEmpty() && listIdiomaPersonaCrear.contains(filtrarListIdiomasPersonas.get(index))) {
                    int crearIndex = listIdiomaPersonaCrear.indexOf(filtrarListIdiomasPersonas.get(index));
                    listIdiomaPersonaCrear.remove(crearIndex);
                } else {
                    listIdiomaPersonaBorrar.add(filtrarListIdiomasPersonas.get(index));
                }
                int VCIndex = listIdiomasPersonas.indexOf(filtrarListIdiomasPersonas.get(index));
                listIdiomasPersonas.remove(VCIndex);
                filtrarListIdiomasPersonas.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            getListIdiomasPersonas();
            if (listIdiomasPersonas != null) {
                infoRegistro = "Cantidad de registros : " + listIdiomasPersonas.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            context.update("form:informacionRegistro");
            context.update("form:datosIdiomas");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }
    }
   
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "258";
            idIdioma = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idIdioma");
            idIdioma.setFilterStyle("width: 100px");
            idConversacion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idConversacion");
            idConversacion.setFilterStyle("width: 100px");
            idLectura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idLectura");
            idLectura.setFilterStyle("width: 100px");
            idEscritura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idEscritura");
            idEscritura.setFilterStyle("width: 100px");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "280";
            idIdioma = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idIdioma");
            idIdioma.setFilterStyle("display: none; visibility: hidden;");
            idConversacion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idConversacion");
            idConversacion.setFilterStyle("display: none; visibility: hidden;");
            idLectura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idLectura");
            idLectura.setFilterStyle("display: none; visibility: hidden;");
            idEscritura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idEscritura");
            idEscritura.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            bandera = 0;
            filtrarListIdiomasPersonas = null;
            tipoLista = 0;
        }
    }

   
    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "280";
            idIdioma = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idIdioma");
            idIdioma.setFilterStyle("display: none; visibility: hidden;");
            idConversacion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idConversacion");
            idConversacion.setFilterStyle("display: none; visibility: hidden;");
            idLectura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idLectura");
            idLectura.setFilterStyle("display: none; visibility: hidden;");
            idEscritura = (Column) c.getViewRoot().findComponent("form:datosIdiomas:idEscritura");
            idEscritura.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            bandera = 0;
            filtrarListIdiomasPersonas = null;
            tipoLista = 0;
        }

        listIdiomaPersonaBorrar.clear();
        listIdiomaPersonaCrear.clear();
        listIdiomaPersonaModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listIdiomasPersonas = null;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }
    
    public void asignarIndex(Integer indice, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        eliminarRegistrosIdiomaLov();
        context.update("form:IdiomasDialogo");
        context.execute("IdiomasDialogo.show()");
    }

    
    public void actualizarIdioma() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listIdiomasPersonas.get(index).setIdioma(idiomaSeleccionado);
                if (!listIdiomaPersonaCrear.contains(listIdiomasPersonas.get(index))) {
                    if (listIdiomaPersonaModificar.isEmpty()) {
                        listIdiomaPersonaModificar.add(listIdiomasPersonas.get(index));
                    } else if (!listIdiomaPersonaModificar.contains(listIdiomasPersonas.get(index))) {
                        listIdiomaPersonaModificar.add(listIdiomasPersonas.get(index));
                    }
                }
            } else {
                filtrarListIdiomasPersonas.get(index).setIdioma(idiomaSeleccionado);
                if (!listIdiomaPersonaCrear.contains(filtrarListIdiomasPersonas.get(index))) {
                    if (listIdiomaPersonaModificar.isEmpty()) {
                        listIdiomaPersonaModificar.add(filtrarListIdiomasPersonas.get(index));
                    } else if (!listIdiomaPersonaModificar.contains(filtrarListIdiomasPersonas.get(index))) {
                        listIdiomaPersonaModificar.add(filtrarListIdiomasPersonas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosIdiomas");
        } else if (tipoActualizacion == 1) {
            nuevaIdiomaPersona.setIdioma(idiomaSeleccionado);
            context.update("formularioDialogos:nuevaIdioma");
        } else if (tipoActualizacion == 2) {
            duplicarIdiomaPersona.setIdioma(idiomaSeleccionado);
            context.update("formularioDialogos:duplicarIdioma");
        }
        filtrarListIdiomas = null;
        idiomaSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:IdiomasDialogo");
        context.update("form:lovIdiomas");
        context.update("form:aceptarI");
        context.reset("form:lovIdiomas:globalFilter");
        context.execute("IdiomasDialogo.hide()");
    }

   
    public void cancelarCambioIdioma() {
        filtrarListIdiomas = null;
        idiomaSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void eliminarRegistrosIdiomaLov() {
        if (listIdiomasPersonas != null) {
            getListIdiomas();
            for (int i = 0; i < listIdiomasPersonas.size(); i++) {
                for (int j = 0; j < listIdiomas.size(); j++) {
                    if (listIdiomas.get(j).getSecuencia().equals(listIdiomasPersonas.get(i).getIdioma().getSecuencia())) {
                        listIdiomas.remove(j);
                    }
                }
            }
        }
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                eliminarRegistrosIdiomaLov();
                context.update("form:IdiomasDialogo");
                context.execute("IdiomasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

   
    public void activarAceptar() {
        aceptar = false;
    }
   
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIdiomaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "IdiomasPersonasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

   
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIdiomaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "IdiomasPersonasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros : " + filtrarListIdiomasPersonas.size();
        context.update("form:informacionRegistro");
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listIdiomasPersonas != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "IDIOMASPERSONAS");
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
            if (administrarRastros.verificarHistoricosTabla("IDIOMASPERSONAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    
    public List<IdiomasPersonas> getListIdiomasPersonas() {
        try {
            if (listIdiomasPersonas == null) {
                if (empleadoActual.getSecuencia() != null) {
                    listIdiomasPersonas = administrarIdiomaPersona.listIdiomasPersonas(empleadoActual.getSecuencia());
                }
            }
            return listIdiomasPersonas;
        } catch (Exception e) {
            System.out.println("Error...!! getListIdiomasPersonas : " + e.toString());

            return null;
        }
    }

    public void setListIdiomasPersonas(List<IdiomasPersonas> setListIdiomasPersonas) {
        this.listIdiomasPersonas = setListIdiomasPersonas;
    }

    public List<IdiomasPersonas> getFiltrarListIdiomasPersonas() {
        return filtrarListIdiomasPersonas;
    }

    public void setFiltrarListIdiomasPersonas(List<IdiomasPersonas> setFiltrarListIdiomasPersonas) {
        this.filtrarListIdiomasPersonas = setFiltrarListIdiomasPersonas;
    }

    public IdiomasPersonas getNuevaIdiomaPersona() {
        return nuevaIdiomaPersona;
    }

    public void setNuevaIdiomaPersona(IdiomasPersonas setNuevaIdiomaPersona) {
        this.nuevaIdiomaPersona = setNuevaIdiomaPersona;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<Idiomas> getListIdiomas() {
        listIdiomas = administrarIdiomaPersona.listIdiomas();
        return listIdiomas;
    }

    public void setListIdiomas(List<Idiomas> setListIdiomas) {
        this.listIdiomas = setListIdiomas;
    }

    public List<Idiomas> getFiltrarListIdiomas() {
        return filtrarListIdiomas;
    }

    public void setFiltrarListIdiomas(List<Idiomas> setFiltrarListIdiomas) {
        this.filtrarListIdiomas = setFiltrarListIdiomas;
    }

    public IdiomasPersonas getEditarIdiomaPersona() {
        return editarIdiomaPersona;
    }

    public void setEditarIdiomaPersona(IdiomasPersonas setEditarIdiomaPersona) {
        this.editarIdiomaPersona = setEditarIdiomaPersona;
    }

    public IdiomasPersonas getDuplicarIdiomaPersona() {
        return duplicarIdiomaPersona;
    }

    public void setDuplicarIdiomaPersona(IdiomasPersonas setDuplicarIdiomaPersona) {
        this.duplicarIdiomaPersona = setDuplicarIdiomaPersona;
    }

    public Idiomas getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(Idiomas setIdiomaSeleccionado) {
        this.idiomaSeleccionado = setIdiomaSeleccionado;
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

    public Empleados getEmpleadoActual() {
        return empleadoActual;
    }

    public void setEmpleadoActual(Empleados empleadoActual) {
        this.empleadoActual = empleadoActual;
    }

    public IdiomasPersonas getIdiomaTablaSeleccionado() {
        getListIdiomasPersonas();
        if (listIdiomasPersonas != null) {
            int tam = listIdiomasPersonas.size();
            if (tam > 0) {
                idiomaTablaSeleccionado = listIdiomasPersonas.get(0);
            }
        }
        return idiomaTablaSeleccionado;
    }

    public void setIdiomaTablaSeleccionado(IdiomasPersonas idiomaTablaSeleccionado) {
        this.idiomaTablaSeleccionado = idiomaTablaSeleccionado;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
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

    public String getInfoRegistroIdioma() {
        getListIdiomas();
        if (listIdiomas != null) {
            infoRegistroIdioma = "Cantidad de registros : " + listIdiomas.size();
        } else {
            infoRegistroIdioma = "Cantidad de registros : 0";
        }
        return infoRegistroIdioma;
    }

    public void setInfoRegistroIdioma(String infoRegistroIdioma) {
        this.infoRegistroIdioma = infoRegistroIdioma;
    }

}

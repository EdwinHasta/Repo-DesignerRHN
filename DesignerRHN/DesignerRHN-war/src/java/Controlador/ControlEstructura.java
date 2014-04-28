package Controlador;

import Entidades.Empresas;
import Entidades.Estructuras;
import Entidades.Organigramas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEstructurasInterface;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class ControlEstructura implements Serializable {

    @EJB
    AdministrarEstructurasInterface administrarEstructuras;
    private List<Organigramas> listaOrganigramas;
    private List<Organigramas> filtradoListaOrganigramas;
    private TreeNode arbolEstructuras;
    private List<Estructuras> estructurasPadre;
    private List<Estructuras> estructurasHijas1;
    private List<Estructuras> estructurasHijas2;
    private List<Estructuras> estructurasHijas3;
    private List<Estructuras> estructurasHijas4;
    private List<Estructuras> estructurasHijas5;
    private List<Estructuras> estructurasHijas6;
    private List<Estructuras> estructurasHijas7;
    private List<Estructuras> estructurasHijas8;
    private List<Estructuras> estructurasHijas9;
    private List<Estructuras> estructurasHijas10;
    //LOVS
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtradoEmpresas;
    private Empresas seleccionEmpresa;
    //
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla datosOrganigramas
    private Column organigramaFecha, organigramaCodigo, organigramaEmpresa, organigramaNit, organigramaEstado;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar Organigrama
    private List<Organigramas> listOrganigramasModificar;
    private boolean guardado, guardarOk;
    //crear Organigrama
    public Organigramas nuevoOrganigrama;
    private List<Organigramas> listOrganigramasCrear;
    private BigInteger l;
    private int k;
    //borrar Organigrama
    private List<Organigramas> listOrganigramasBorrar;
    //editar celda
    private Organigramas editarOrganigrama;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Organigramas duplicarOrganigrama;
    //AUTOCOMPLETAR
    private String nombreEmpresa;
    private String mensajeValidacion;
    //Redireccionamiento a paginas
    private String paginaAnterior;
    //CODIGO EMPRESA PARA ESTRUCTURAS HIJAS 
    Short codigoEmpresa;

    public ControlEstructura() {
        arbolEstructuras = null;
        estructurasPadre = new ArrayList<Estructuras>();
        estructurasHijas1 = new ArrayList<Estructuras>();
        estructurasHijas2 = new ArrayList<Estructuras>();
        estructurasHijas3 = new ArrayList<Estructuras>();
        estructurasHijas4 = new ArrayList<Estructuras>();
        estructurasHijas5 = new ArrayList<Estructuras>();
        estructurasHijas6 = new ArrayList<Estructuras>();
        estructurasHijas7 = new ArrayList<Estructuras>();
        estructurasHijas8 = new ArrayList<Estructuras>();
        estructurasHijas9 = new ArrayList<Estructuras>();
        estructurasHijas10 = new ArrayList<Estructuras>();
        //LOVS
        listaEmpresas = new ArrayList<Empresas>();
        permitirIndex = true;
        listaOrganigramas = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listOrganigramasBorrar = new ArrayList<Organigramas>();
        //crear aficiones
        listOrganigramasCrear = new ArrayList<Organigramas>();
        k = 0;
        //modificar aficiones
        listOrganigramasModificar = new ArrayList<Organigramas>();
        //editar
        editarOrganigrama = new Organigramas();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoOrganigrama = new Organigramas();
        nuevoOrganigrama.setEmpresa(new Empresas());
        nuevoOrganigrama.setEstado("A");
        index = -1;
    }
    
    public void recibirPaginaEntrante(String pagina){
        paginaAnterior = pagina;        
        }
    
    public String redirigir(){
        return paginaAnterior;
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (cualCelda == 2) {
                nombreEmpresa = listaOrganigramas.get(index).getEmpresa().getNombre();
            }
            codigoEmpresa = listaOrganigramas.get(index).getEmpresa().getCodigo();
            arbolEstructuras = null;
            RequestContext.getCurrentInstance().update("form:arbolEstructuras");
        }
    }

    //AUTOCOMPLETAR
    public void modificarOrganigrama(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listOrganigramasCrear.contains(listaOrganigramas.get(indice))) {

                    if (listOrganigramasModificar.isEmpty()) {
                        listOrganigramasModificar.add(listaOrganigramas.get(indice));
                    } else if (!listOrganigramasModificar.contains(listaOrganigramas.get(indice))) {
                        listOrganigramasModificar.add(listaOrganigramas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            } else {
                if (!listOrganigramasCrear.contains(filtradoListaOrganigramas.get(indice))) {

                    if (listOrganigramasModificar.isEmpty()) {
                        listOrganigramasModificar.add(filtradoListaOrganigramas.get(indice));
                    } else if (!listOrganigramasModificar.contains(filtradoListaOrganigramas.get(indice))) {
                        listOrganigramasModificar.add(filtradoListaOrganigramas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            }
            context.update("form:datosOrganigramas");
        } else if (confirmarCambio.equalsIgnoreCase("EMPRESA")) {
            if (tipoLista == 0) {
                listaOrganigramas.get(indice).getEmpresa().setNombre(nombreEmpresa);
            } else {
                filtradoListaOrganigramas.get(indice).getEmpresa().setNombre(nombreEmpresa);
            }
            for (int i = 0; i < listaEmpresas.size(); i++) {
                if (listaEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaOrganigramas.get(indice).setEmpresa(listaEmpresas.get(indiceUnicoElemento));
                } else {
                    filtradoListaOrganigramas.get(indice).setEmpresa(listaEmpresas.get(indiceUnicoElemento));
                }
                listaEmpresas.clear();
                getListaEmpresas();
            } else {
                permitirIndex = false;
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listOrganigramasCrear.contains(listaOrganigramas.get(indice))) {

                    if (listOrganigramasModificar.isEmpty()) {
                        listOrganigramasModificar.add(listaOrganigramas.get(indice));
                    } else if (!listOrganigramasModificar.contains(listaOrganigramas.get(indice))) {
                        listOrganigramasModificar.add(listaOrganigramas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            } else {
                if (!listOrganigramasCrear.contains(filtradoListaOrganigramas.get(indice))) {

                    if (listOrganigramasModificar.isEmpty()) {
                        listOrganigramasModificar.add(filtradoListaOrganigramas.get(indice));
                    } else if (!listOrganigramasModificar.contains(filtradoListaOrganigramas.get(indice))) {
                        listOrganigramasModificar.add(filtradoListaOrganigramas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            }
        }
        context.update("form:datosOrganigramas");
    }

    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)
    public void asignarIndex(Integer indice, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
        } else if (LND == 2) {
            index = -1;
            tipoActualizacion = 2;
        }
        context.update("form:EmpresasDialogo");
        context.execute("EmpresasDialogo.show()");
    }

    //AUTOCOMPLETAR NUEVO Y DUPLICADO
    public void valoresBackupAutocompletar(int tipoNuevo) {

        if (tipoNuevo == 1) {
            nombreEmpresa = nuevoOrganigrama.getEmpresa().getNombre();
        } else if (tipoNuevo == 2) {
            nombreEmpresa = duplicarOrganigrama.getEmpresa().getNombre();
        }
    }

    public void autocompletarNuevoyDuplicado(String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoNuevo == 1) {
            nuevoOrganigrama.getEmpresa().setNombre(nombreEmpresa);
        } else if (tipoNuevo == 2) {
            duplicarOrganigrama.getEmpresa().setNombre(nombreEmpresa);
        }
        for (int i = 0; i < listaEmpresas.size(); i++) {
            if (listaEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }
        if (coincidencias == 1) {
            if (tipoNuevo == 1) {
                nuevoOrganigrama.setEmpresa(listaEmpresas.get(indiceUnicoElemento));
                context.update("formularioDialogos:nuevaEmpresa");
                context.update("formularioDialogos:nuevoNit");
            } else if (tipoNuevo == 2) {
                duplicarOrganigrama.setEmpresa(listaEmpresas.get(indiceUnicoElemento));
                context.update("formularioDialogos:duplicarEmpresa");
                context.update("formularioDialogos:duplicarNit");
            }
            listaEmpresas.clear();
            getListaEmpresas();
        } else {
            context.update("form:EmpresasDialogo");
            context.execute("EmpresasDialogo.show()");
            tipoActualizacion = tipoNuevo;
            if (tipoNuevo == 1) {
                context.update("formularioDialogos:nuevaEmpresa");
                context.update("formularioDialogos:nuevoNit");
            } else if (tipoNuevo == 2) {
                context.update("formularioDialogos:duplicarEmpresa");
                context.update("formularioDialogos:duplicarNit");
            }
        }
    }

    //CREAR ORGANIGRAMA
    public void agregarNuevoOrganigrama() {
        boolean pasa = false;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoOrganigrama.getEmpresa().getSecuencia() == null) {
            mensajeValidacion = " * Empresa \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (pasa == true) {
            if (bandera == 1) {
                organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaFecha");
                organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
                organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaCodigo");
                organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
                organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEmpresa");
                organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
                organigramaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaNit");
                organigramaNit.setFilterStyle("display: none; visibility: hidden;");
                organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEstado");
                organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosOrganigramas");
                bandera = 0;
                filtradoListaOrganigramas = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA ORGANIGRAMAS.
            k++;
            l = BigInteger.valueOf(k);
            nuevoOrganigrama.setSecuencia(l);
            listOrganigramasCrear.add(nuevoOrganigrama);

            listaOrganigramas.add(nuevoOrganigrama);
            nuevoOrganigrama = new Organigramas();
            nuevoOrganigrama.setEmpresa(new Empresas());
            nuevoOrganigrama.setEstado("A");
            context.update("form:datosOrganigramas");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroOrganigramas.hide()");
            index = -1;
        } else {
            context.update("form:validacioNuevoOrganigrama");
            context.execute("validacioNuevoOrganigrama.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevoOrganigrama() {
        nuevoOrganigrama = new Organigramas();
        nuevoOrganigrama.setEmpresa(new Empresas());
        nuevoOrganigrama.setEstado("A");
        index = -1;
    }

    //DUPLICAR VC
    public void duplicarOrg() {
        if (index >= 0) {
            duplicarOrganigrama = new Organigramas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarOrganigrama.setSecuencia(l);
                duplicarOrganigrama.setFecha(listaOrganigramas.get(index).getFecha());
                duplicarOrganigrama.setCodigo(listaOrganigramas.get(index).getCodigo());
                duplicarOrganigrama.setEmpresa(listaOrganigramas.get(index).getEmpresa());
                duplicarOrganigrama.setEstado(listaOrganigramas.get(index).getEstado());
            }
            if (tipoLista == 1) {
                duplicarOrganigrama.setSecuencia(l);
                duplicarOrganigrama.setFecha(filtradoListaOrganigramas.get(index).getFecha());
                duplicarOrganigrama.setCodigo(filtradoListaOrganigramas.get(index).getCodigo());
                duplicarOrganigrama.setEmpresa(filtradoListaOrganigramas.get(index).getEmpresa());
                duplicarOrganigrama.setEstado(filtradoListaOrganigramas.get(index).getEstado());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarOrganigrama");
            context.execute("DuplicarRegistroOrganigramas.show()");
            index = -1;
        }
    }

    public void confirmarDuplicar() {

        listaOrganigramas.add(duplicarOrganigrama);
        listOrganigramasCrear.add(duplicarOrganigrama);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOrganigramas");
        index = -1;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaFecha");
            organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaCodigo");
            organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
            organigramaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaNit");
            organigramaNit.setFilterStyle("display: none; visibility: hidden;");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEstado");
            organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOrganigramas");
            bandera = 0;
            filtradoListaOrganigramas = null;
            tipoLista = 0;
        }
        duplicarOrganigrama = new Organigramas();
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicarVTC() {
        duplicarOrganigrama = new Organigramas();
    }

    //LOV EMPRESAS
    //TIPO CONTRATO
    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaOrganigramas.get(index).setEmpresa(seleccionEmpresa);

                if (!listOrganigramasCrear.contains(listaOrganigramas.get(index))) {
                    if (listOrganigramasModificar.isEmpty()) {
                        listOrganigramasModificar.add(listaOrganigramas.get(index));
                    } else if (!listOrganigramasModificar.contains(listaOrganigramas.get(index))) {
                        listOrganigramasModificar.add(listaOrganigramas.get(index));
                    }
                }
            } else {
                filtradoListaOrganigramas.get(index).setEmpresa(seleccionEmpresa);

                if (!listOrganigramasCrear.contains(filtradoListaOrganigramas.get(index))) {
                    if (listOrganigramasModificar.isEmpty()) {
                        listOrganigramasModificar.add(filtradoListaOrganigramas.get(index));
                    } else if (!listOrganigramasModificar.contains(filtradoListaOrganigramas.get(index))) {
                        listOrganigramasModificar.add(filtradoListaOrganigramas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosOrganigramas");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoOrganigrama.setEmpresa(seleccionEmpresa);
            context.update("formularioDialogos:nuevoOrganigrama");
        } else if (tipoActualizacion == 2) {
            duplicarOrganigrama.setEmpresa(seleccionEmpresa);
            context.update("formularioDialogos:duplicarOrganigrama");
        }
        filtradoEmpresas = null;
        seleccionEmpresa = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("EmpresasDialogo.hide()");
        context.reset("form:lovEmpresas:globalFilter");
        context.update("form:lovEmpresas");
    }

    public void cancelarEmpresa() {
        filtradoEmpresas = null;
        seleccionEmpresa = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //GUARDAR
    public void guardarCambiosOrganigramas() {
        if (guardado == false) {
            if (!listOrganigramasBorrar.isEmpty()) {
                for (int i = 0; i < listOrganigramasBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    administrarEstructuras.borrarOrganigrama(listOrganigramasBorrar.get(i));
                }
                listOrganigramasBorrar.clear();
            }
            if (!listOrganigramasCrear.isEmpty()) {
                for (int i = 0; i < listOrganigramasCrear.size(); i++) {
                    System.out.println("Creando...");
                    administrarEstructuras.crearOrganigrama(listOrganigramasCrear.get(i));
                }
                listOrganigramasCrear.clear();
            }
            if (!listOrganigramasModificar.isEmpty()) {
                administrarEstructuras.modificarOrganigrama(listOrganigramasModificar);
                listOrganigramasModificar.clear();
            }
            listaOrganigramas = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosOrganigramas");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        index = -1;
    }
    //CANCELAR MODIFICACIONES

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaFecha");
            organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaCodigo");
            organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
            organigramaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaNit");
            organigramaNit.setFilterStyle("display: none; visibility: hidden;");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEstado");
            organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOrganigramas");
            bandera = 0;
            filtradoListaOrganigramas = null;
            tipoLista = 0;
        }

        listOrganigramasBorrar.clear();
        listOrganigramasCrear.clear();
        listOrganigramasModificar.clear();
        index = -1;
        k = 0;
        listaOrganigramas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOrganigramas");
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        if (bandera == 0) {
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaFecha");
            organigramaFecha.setFilterStyle("width: 40px;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaCodigo");
            organigramaCodigo.setFilterStyle("width: 25px;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("width: 180px;");
            organigramaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaNit");
            organigramaNit.setFilterStyle("width: 100px");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEstado");
            organigramaEstado.setFilterStyle("");
            RequestContext.getCurrentInstance().update("form:datosOrganigramas");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaFecha");
            organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaCodigo");
            organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
            organigramaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaNit");
            organigramaNit.setFilterStyle("display: none; visibility: hidden;");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEstado");
            organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOrganigramas");
            bandera = 0;
            filtradoListaOrganigramas = null;
            tipoLista = 0;
        }
    }

    //SALIR
    public void salir() {
        if (bandera == 1) {
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaFecha");
            organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaCodigo");
            organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
            organigramaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaNit");
            organigramaNit.setFilterStyle("display: none; visibility: hidden;");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigramas:organigramaEstado");
            organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOrganigramas");
            bandera = 0;
            filtradoListaOrganigramas = null;
            tipoLista = 0;
        }

        listOrganigramasBorrar.clear();
        listOrganigramasCrear.clear();
        listOrganigramasModificar.clear();
        index = -1;
        k = 0;
        listaOrganigramas = null;
        guardado = true;
        permitirIndex = true;

    }

//BORRAR VC
    public void borrarOrganigrama() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listOrganigramasModificar.isEmpty() && listOrganigramasModificar.contains(listaOrganigramas.get(index))) {
                    int modIndex = listOrganigramasModificar.indexOf(listaOrganigramas.get(index));
                    listOrganigramasModificar.remove(modIndex);
                    listOrganigramasBorrar.add(listaOrganigramas.get(index));
                } else if (!listOrganigramasCrear.isEmpty() && listOrganigramasCrear.contains(listaOrganigramas.get(index))) {
                    int crearIndex = listOrganigramasCrear.indexOf(listaOrganigramas.get(index));
                    listOrganigramasCrear.remove(crearIndex);
                } else {
                    listOrganigramasBorrar.add(listaOrganigramas.get(index));
                }
                listaOrganigramas.remove(index);
            }
            if (tipoLista == 1) {
                if (!listOrganigramasModificar.isEmpty() && listOrganigramasModificar.contains(filtradoListaOrganigramas.get(index))) {
                    int modIndex = listOrganigramasModificar.indexOf(filtradoListaOrganigramas.get(index));
                    listOrganigramasModificar.remove(modIndex);
                    listOrganigramasBorrar.add(filtradoListaOrganigramas.get(index));
                } else if (!listOrganigramasCrear.isEmpty() && listOrganigramasCrear.contains(filtradoListaOrganigramas.get(index))) {
                    int crearIndex = listOrganigramasCrear.indexOf(filtradoListaOrganigramas.get(index));
                    listOrganigramasCrear.remove(crearIndex);
                } else {
                    listOrganigramasBorrar.add(filtradoListaOrganigramas.get(index));
                }
                int VCIndex = listaOrganigramas.indexOf(filtradoListaOrganigramas.get(index));
                listaOrganigramas.remove(VCIndex);
                filtradoListaOrganigramas.remove(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosOrganigramas");
            index = -1;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarOrganigrama = listaOrganigramas.get(index);
            }
            if (tipoLista == 1) {
                editarOrganigrama = filtradoListaOrganigramas.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarCod");
                context.execute("editarCod.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarEmp");
                context.execute("editarEmp.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarN");
                context.execute("editarN.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarEs");
                context.execute("editarEs.show()");
                cualCelda = -1;
            }
        }
        index = -1;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosOrganigramasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "OrganigramasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosOrganigramasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "OrganigramasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }
    //EVENTO FILTRAR

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void seleccionarEstado(String estadoOrganigrama, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoOrganigrama.equals("ACTIVO")) {
                listaOrganigramas.get(indice).setEstado("A");
            } else {
                listaOrganigramas.get(indice).setEstado("I");
            }

            if (!listOrganigramasCrear.contains(listaOrganigramas.get(indice))) {
                if (listOrganigramasModificar.isEmpty()) {
                    listOrganigramasModificar.add(listaOrganigramas.get(indice));
                } else if (!listOrganigramasModificar.contains(listaOrganigramas.get(indice))) {
                    listOrganigramasModificar.add(listaOrganigramas.get(indice));
                }
            }
        } else {
            if (estadoOrganigrama.equals("ACTIVO")) {
                filtradoListaOrganigramas.get(indice).setEstado("A");
            } else {
                filtradoListaOrganigramas.get(indice).setEstado("I");
            }

            if (!listOrganigramasCrear.contains(filtradoListaOrganigramas.get(indice))) {
                if (listOrganigramasModificar.isEmpty()) {
                    listOrganigramasModificar.add(filtradoListaOrganigramas.get(indice));
                } else if (!listOrganigramasModificar.contains(filtradoListaOrganigramas.get(indice))) {
                    listOrganigramasModificar.add(filtradoListaOrganigramas.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
        }
        RequestContext.getCurrentInstance().update("form:datosOrganigramas");
    }

    public void seleccionarEstadoNuevoOrganigrama(String estadoOrganigrama, int tipoNuevo) {
        if (tipoNuevo == 1) {
            if (estadoOrganigrama.equals("ACTIVO")) {
                nuevoOrganigrama.setEstado("A");
            } else {
                nuevoOrganigrama.setEstado("I");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoEstadoOrganigrama");
        } else {
            if (estadoOrganigrama.equals("ACTIVO")) {
                duplicarOrganigrama.setEstado("A");
            } else {
                duplicarOrganigrama.setEstado("I");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarOrganigrama");
        }

    }

    //GETTER AND SETTER

   public TreeNode getArbolEstructuras() {
        if (arbolEstructuras == null) {
            arbolEstructuras = new DefaultTreeNode("arbolEstructuras", null);
            if (index == -1) {
                estructurasPadre = administrarEstructuras.estructuraPadre(listaOrganigramas.get(0).getCodigo());
            } else {
                estructurasPadre = administrarEstructuras.estructuraPadre(listaOrganigramas.get(index).getCodigo());
            }
            if (estructurasPadre != null) {
                for (int i = 0; i < estructurasPadre.size(); i++) {
                    TreeNode padre = new DefaultTreeNode(estructurasPadre.get(i), arbolEstructuras);
                    estructurasHijas1 = administrarEstructuras.estructurasHijas(estructurasPadre.get(i).getSecuencia(), codigoEmpresa);
                    if (estructurasHijas1 != null) {
                        for (int j = 0; j < estructurasHijas1.size(); j++) {
                            TreeNode hija1 = new DefaultTreeNode(estructurasHijas1.get(j), padre);
                            estructurasHijas2 = administrarEstructuras.estructurasHijas(estructurasHijas1.get(j).getSecuencia(), codigoEmpresa);
                            if (estructurasHijas2 != null) {
                                for (int k = 0; k < estructurasHijas2.size(); k++) {
                                    TreeNode hija2 = new DefaultTreeNode(estructurasHijas2.get(k), hija1);
                                    estructurasHijas3 = administrarEstructuras.estructurasHijas(estructurasHijas2.get(k).getSecuencia(), codigoEmpresa);
                                    if (estructurasHijas3 != null) {
                                        for (int l = 0; l < estructurasHijas3.size(); l++) {
                                            TreeNode hija3 = new DefaultTreeNode(estructurasHijas3.get(l), hija2);
                                            estructurasHijas4 = administrarEstructuras.estructurasHijas(estructurasHijas3.get(l).getSecuencia(), codigoEmpresa);
                                            if (estructurasHijas4 != null) {
                                                for (int m = 0; m < estructurasHijas4.size(); m++) {
                                                    TreeNode hija4 = new DefaultTreeNode(estructurasHijas4.get(m), hija3);
                                                    estructurasHijas5 = administrarEstructuras.estructurasHijas(estructurasHijas4.get(m).getSecuencia(), codigoEmpresa);
                                                    if (estructurasHijas5 != null) {
                                                        for (int f = 0; f < estructurasHijas5.size(); f++) {
                                                            TreeNode hija5 = new DefaultTreeNode(estructurasHijas5.get(f), hija4);
                                                            estructurasHijas6 = administrarEstructuras.estructurasHijas(estructurasHijas5.get(f).getSecuencia(), codigoEmpresa);
                                                            if (estructurasHijas6 != null) {
                                                                for (int e = 0; e < estructurasHijas6.size(); e++) {
                                                                    TreeNode hija6 = new DefaultTreeNode(estructurasHijas6.get(e), hija5);
                                                                    estructurasHijas7 = administrarEstructuras.estructurasHijas(estructurasHijas6.get(e).getSecuencia(), codigoEmpresa);
                                                                    if (estructurasHijas7 != null) {
                                                                        for (int p = 0; p < estructurasHijas7.size(); p++) {
                                                                            TreeNode hija7 = new DefaultTreeNode(estructurasHijas7.get(p), hija6);
                                                                            estructurasHijas8 = administrarEstructuras.estructurasHijas(estructurasHijas7.get(p).getSecuencia(), codigoEmpresa);
                                                                            if (estructurasHijas8 != null) {
                                                                                for (int a = 0; a < estructurasHijas8.size(); a++) {
                                                                                    TreeNode hija8 = new DefaultTreeNode(estructurasHijas8.get(m), hija7);
                                                                                    estructurasHijas9 = administrarEstructuras.estructurasHijas(estructurasHijas8.get(m).getSecuencia(), codigoEmpresa);
                                                                                    if (estructurasHijas9 != null) {
                                                                                        for (int t = 0; t < estructurasHijas9.size(); t++) {
                                                                                            TreeNode hija9 = new DefaultTreeNode(estructurasHijas9.get(m), hija8);
                                                                                            estructurasHijas10 = administrarEstructuras.estructurasHijas(estructurasHijas9.get(m).getSecuencia(), codigoEmpresa);
                                                                                            if (estructurasHijas10 != null) {
                                                                                                for (int r = 0; r < estructurasHijas10.size(); r++) {
                                                                                                    TreeNode hija10 = new DefaultTreeNode(estructurasHijas10.get(m), hija9);
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return arbolEstructuras;
    }

    public List<Estructuras> getEstructurasPadre() {
        return estructurasPadre;
    }

    public void setEstructurasPadre(List<Estructuras> estructurasPadre) {
        this.estructurasPadre = estructurasPadre;
    }

    public List<Organigramas> getListaOrganigramas() {
        if (listaOrganigramas == null) {
            listaOrganigramas = administrarEstructuras.obtenerOrganigramas();
        }
        return listaOrganigramas;
    }

    public void setListaOrganigramas(List<Organigramas> listaOrganigramas) {
        this.listaOrganigramas = listaOrganigramas;
    }

    public List<Organigramas> getFiltradoListaOrganigramas() {
        return filtradoListaOrganigramas;
    }

    public void setFiltradoListaOrganigramas(List<Organigramas> filtradoListaOrganigramas) {
        this.filtradoListaOrganigramas = filtradoListaOrganigramas;
    }

    public List<Empresas> getListaEmpresas() {
        listaEmpresas = administrarEstructuras.obtenerEmpresas();
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public List<Empresas> getFiltradoEmpresas() {
        return filtradoEmpresas;
    }

    public void setFiltradoEmpresas(List<Empresas> filtradoEmpresas) {
        this.filtradoEmpresas = filtradoEmpresas;
    }

    public Empresas getSelecionEmpresa() {
        return seleccionEmpresa;
    }

    public void setSelecionEmpresa(Empresas selecionEmpresa) {
        this.seleccionEmpresa = selecionEmpresa;
    }

    public Organigramas getNuevoOrganigrama() {
        return nuevoOrganigrama;
    }

    public void setNuevoOrganigrama(Organigramas nuevoOrganigrama) {
        this.nuevoOrganigrama = nuevoOrganigrama;
    }

    public Organigramas getEditarOrganigrama() {
        return editarOrganigrama;
    }

    public void setEditarOrganigrama(Organigramas editarOrganigrama) {
        this.editarOrganigrama = editarOrganigrama;
    }

    public boolean isAceptarEditar() {
        return aceptarEditar;
    }

    public void setAceptarEditar(boolean aceptarEditar) {
        this.aceptarEditar = aceptarEditar;
    }

    public Organigramas getDuplicarOrganigrama() {
        return duplicarOrganigrama;
    }

    public void setDuplicarOrganigrama(Organigramas duplicarOrganigrama) {
        this.duplicarOrganigrama = duplicarOrganigrama;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public boolean isAceptar() {
        return aceptar;
    }
}
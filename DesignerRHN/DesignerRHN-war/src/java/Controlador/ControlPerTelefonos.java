package Controlador;

import Entidades.Ciudades;
import Entidades.Personas;
import Entidades.Telefonos;
import Entidades.TiposTelefonos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTelefonosInterface;
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
public class ControlPerTelefonos implements Serializable {

    @EJB
    AdministrarTelefonosInterface administrarTelefonos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    
    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaPersona;
    private Personas persona;
    //LISTA TELEFONOS
    private List<Telefonos> listaTelefonos;
    private List<Telefonos> filtradosListaTelefonos;
    //L.O.V TIPOS TELEFONOS
    private List<TiposTelefonos> listaTiposTelefonos;
    private List<TiposTelefonos> filtradoslistaTiposTelefonos;
    private TiposTelefonos seleccionTipoTelefono;
    //L.O.V CIUDADES
    private List<Ciudades> listaCiudades;
    private List<Ciudades> filtradoslistaCiudades;
    private Ciudades seleccionCiudades;
    //Columnas Tabla Telefonos
    private Column tFecha, tTipoTelefono, tNumero, tCiudad;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //AUTOCOMPLETAR
    private String TipoTelefono, Ciudad;
    //Modificar Telefono
    private List<Telefonos> listaTelefonosModificar;
    private boolean guardado, guardarOk;
    //Crear Telefono
    public Telefonos nuevoTelefono;
    private List<Telefonos> listaTelefonosCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //Borrar Teléfono
    private List<Telefonos> listaTelefonosBorrar;
    //editar celda
    private Telefonos editarTelefono;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //Duplicar
    private Telefonos duplicarTelefono;
    //RASTRO
    private BigInteger secRegistro;

    public ControlPerTelefonos() {
        permitirIndex = true;
        //secuenciaPersona = BigInteger.valueOf(10668967);
        aceptar = true;
        tipoLista = 0;
        listaTelefonosBorrar = new ArrayList<Telefonos>();
        listaTelefonosCrear = new ArrayList<Telefonos>();
        listaTelefonosModificar = new ArrayList<Telefonos>();
        //INICIALIZAR LOVS
        listaTiposTelefonos = new ArrayList<TiposTelefonos>();
        listaCiudades = new ArrayList<Ciudades>();
        secRegistro = null;
        //editar
        editarTelefono = new Telefonos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //Crear VC
        nuevoTelefono = new Telefonos();
        nuevoTelefono.setTipotelefono(new TiposTelefonos());
        nuevoTelefono.setCiudad(new Ciudades());
        secRegistro = null;
        permitirIndex = true;
        k = 0;
    }

    public void recibirPersona(BigInteger secPersona) {
        secuenciaPersona = secPersona;
        persona = null;
        getPersona();
        listaTelefonos = null;
        getListaTelefonos();
        listaTiposTelefonos = null;
        getListaTiposTelefonos();
        aceptar = true;
    }

    public void refrescar() {
        listaTelefonos = null;
        aceptar = true;
        getListaTelefonos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTelefonosPersona");
    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            tFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tFecha");
            tFecha.setFilterStyle("display: none; visibility: hidden;");
            tTipoTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tTipoTelefono");
            tTipoTelefono.setFilterStyle("display: none; visibility: hidden;");
            tNumero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tNumero");
            tNumero.setFilterStyle("display: none; visibility: hidden;");
            tCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tCiudad");
            tCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTelefonosPersona");
            bandera = 0;
            filtradosListaTelefonos = null;
            tipoLista = 0;
        }

        listaTelefonosBorrar.clear();
        listaTelefonosCrear.clear();
        listaTelefonosModificar.clear();
        index = -1;
        secRegistro = null;
//        k = 0;
        listaTelefonos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTelefonosPersona");
    }

    public void actualizarTiposTelefonos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaTelefonos.get(index).setTipotelefono(seleccionTipoTelefono);
                if (!listaTelefonosCrear.contains(listaTelefonos.get(index))) {
                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(listaTelefonos.get(index));
                    } else if (!listaTelefonosModificar.contains(listaTelefonos.get(index))) {
                        listaTelefonosModificar.add(listaTelefonos.get(index));
                    }
                }
            } else {
                filtradosListaTelefonos.get(index).setTipotelefono(seleccionTipoTelefono);
                if (!listaTelefonosCrear.contains(filtradosListaTelefonos.get(index))) {
                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(index));
                    } else if (!listaTelefonosModificar.contains(filtradosListaTelefonos.get(index))) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosTelefonosPersona");
        } else if (tipoActualizacion == 1) {
            nuevoTelefono.setTipotelefono(seleccionTipoTelefono);
            context.update("formularioDialogos:nuevoTelefono");
        } else if (tipoActualizacion == 2) {
            duplicarTelefono.setTipotelefono(seleccionTipoTelefono);
            context.update("formularioDialogos:duplicarTelefono");
        }
        filtradoslistaTiposTelefonos = null;
        seleccionTipoTelefono = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("tiposTelefonosDialogo.hide()");
        context.reset("formularioDialogos:LOVTiposTelefonos:globalFilter");
        context.update("formularioDialogos:LOVTiposTelefonos");
    }

    public void cancelarCambioTiposTelefonos() {
        filtradoslistaTiposTelefonos = null;
        seleccionTipoTelefono = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }
//MOSTRAR DATOS CELDA

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTelefono = listaTelefonos.get(index);
            }
            if (tipoLista == 1) {
                editarTelefono = filtradosListaTelefonos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarTipoTelefono");
                context.execute("editarTipoTelefono.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarNumeroTelefono");
                context.execute("editarNumeroTelefono.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarCiudad");
                context.execute("editarCiudad.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                context.update("formularioDialogos:tiposTelefonosDialogo");
                context.execute("tiposTelefonosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:ciudadesDialogo");
                context.execute("ciudadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    //BORRAR CIUDADES
    public void borrarTelefonos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaTelefonosModificar.isEmpty() && listaTelefonosModificar.contains(listaTelefonos.get(index))) {
                    int modIndex = listaTelefonosModificar.indexOf(listaTelefonos.get(index));
                    listaTelefonosModificar.remove(modIndex);
                    listaTelefonosBorrar.add(listaTelefonos.get(index));
                } else if (!listaTelefonosCrear.isEmpty() && listaTelefonosCrear.contains(listaTelefonos.get(index))) {
                    int crearIndex = listaTelefonosCrear.indexOf(listaTelefonos.get(index));
                    listaTelefonosCrear.remove(crearIndex);
                } else {
                    listaTelefonosBorrar.add(listaTelefonos.get(index));
                }
                listaTelefonos.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaTelefonosModificar.isEmpty() && listaTelefonosModificar.contains(filtradosListaTelefonos.get(index))) {
                    int modIndex = listaTelefonosModificar.indexOf(filtradosListaTelefonos.get(index));
                    listaTelefonosModificar.remove(modIndex);
                    listaTelefonosBorrar.add(filtradosListaTelefonos.get(index));
                } else if (!listaTelefonosCrear.isEmpty() && listaTelefonosCrear.contains(filtradosListaTelefonos.get(index))) {
                    int crearIndex = listaTelefonosCrear.indexOf(filtradosListaTelefonos.get(index));
                    listaTelefonosCrear.remove(crearIndex);
                } else {
                    listaTelefonosBorrar.add(filtradosListaTelefonos.get(index));
                }
                int CIndex = listaTelefonos.indexOf(filtradosListaTelefonos.get(index));
                listaTelefonos.remove(CIndex);
                filtradosListaTelefonos.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTelefonosPersona");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //DUPLICAR TELEFONO
    public void duplicarT() {
        if (index >= 0) {
            duplicarTelefono = new Telefonos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTelefono.setSecuencia(l);
                duplicarTelefono.setFechavigencia(listaTelefonos.get(index).getFechavigencia());
                duplicarTelefono.setTipotelefono(listaTelefonos.get(index).getTipotelefono());
                duplicarTelefono.setNumerotelefono(listaTelefonos.get(index).getNumerotelefono());
                duplicarTelefono.setCiudad(listaTelefonos.get(index).getCiudad());
                duplicarTelefono.setPersona(listaTelefonos.get(index).getPersona());
            }
            if (tipoLista == 1) {
                duplicarTelefono.setSecuencia(l);
                duplicarTelefono.setFechavigencia(filtradosListaTelefonos.get(index).getFechavigencia());
                duplicarTelefono.setTipotelefono(filtradosListaTelefonos.get(index).getTipotelefono());
                duplicarTelefono.setNumerotelefono(filtradosListaTelefonos.get(index).getNumerotelefono());
                duplicarTelefono.setCiudad(filtradosListaTelefonos.get(index).getCiudad());
                duplicarTelefono.setPersona(filtradosListaTelefonos.get(index).getPersona());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTelefono");
            context.execute("DuplicarRegistroTelefono.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {

        RequestContext context = RequestContext.getCurrentInstance();
        int pasa= 0;
        
         for (int i = 0; i < listaTelefonos.size(); i++) {
            if ((listaTelefonos.get(i).getTipotelefono().getNombre().equals(duplicarTelefono.getTipotelefono().getNombre())) && (!(listaTelefonos.get(i).getFechavigencia().before(duplicarTelefono.getFechavigencia())) && !(duplicarTelefono.getFechavigencia().before(listaTelefonos.get(i).getFechavigencia())))){ 
                System.out.println("Entro al IF Tipo Telefono");
                context.update("formularioDialogos:existeTipoTelefono");
                context.execute("existeTipoTelefono.show()");
                pasa++;
            } if(pasa != 0){
                    context.update("formularioDialogos:validacionNuevoTelefono");
                    context.execute("validacionNuevoTelefono.show()");
                    
                }
        }
          
        
            if (pasa == 0){
        
        listaTelefonos.add(duplicarTelefono);
        listaTelefonosCrear.add(duplicarTelefono);
        
        context.update("form:datosTelefonosPersona");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            tFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tFecha");
            tFecha.setFilterStyle("display: none; visibility: hidden;");
            tTipoTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tTipoTelefono");
            tTipoTelefono.setFilterStyle("display: none; visibility: hidden;");
            tNumero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tNumero");
            tNumero.setFilterStyle("display: none; visibility: hidden;");
            tCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tCiudad");
            tCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTelefonosPersona");
            bandera = 0;
            filtradosListaTelefonos = null;
            tipoLista = 0;

        }
        duplicarTelefono = new Telefonos();
    }
       context.update("formularioDialogos:DuplicarRegistroTelefono");
        context.execute("DuplicarRegistroTelefono.hide()"); 
        }
    //LIMPIAR DUPLICAR

    public void limpiarduplicarTelefono() {
        duplicarTelefono = new Telefonos();
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTelefonosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TelefonosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTelefonosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TelefonosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevoTelefono() {
        nuevoTelefono = new Telefonos();
        nuevoTelefono.setTipotelefono(new TiposTelefonos());
        nuevoTelefono.setCiudad(new Ciudades());
        index = -1;
        secRegistro = null;
    }
    //FILTRADO

    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            tFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tFecha");
            tFecha.setFilterStyle("width: 60px");
            tTipoTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tTipoTelefono");
            tTipoTelefono.setFilterStyle("");
            tNumero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tNumero");
            tNumero.setFilterStyle("");
            tCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tCiudad");
            tCiudad.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosTelefonosPersona");
            bandera = 1;

        } else if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            tFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tFecha");
            tFecha.setFilterStyle("display: none; visibility: hidden;");
            tTipoTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tTipoTelefono");
            tTipoTelefono.setFilterStyle("display: none; visibility: hidden;");
            tNumero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tNumero");
            tNumero.setFilterStyle("display: none; visibility: hidden;");
            tCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tCiudad");
            tCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTelefonosPersona");
            bandera = 0;
            filtradosListaTelefonos = null;
            tipoLista = 0;
        }
    }
//CREAR TELEFONO

    public void agregarNuevoTelefono() {
        int pasa = 0;
        int pasaA = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        
        
        
        if (nuevoTelefono.getFechavigencia() == null) {
            System.out.println("Entro a Fecha");
            mensajeValidacion = " * Fecha \n";
            pasa++;
        }
        if (nuevoTelefono.getTipotelefono().getSecuencia() == null) {
            System.out.println("Entro a TipoTelefono");
            mensajeValidacion = mensajeValidacion + " * Tipo de Telefono\n";
            pasa++;
        }
        if (nuevoTelefono.getNumerotelefono() == 0) {
            System.out.println("Entro a Numero");
            mensajeValidacion = mensajeValidacion + " * Numero de Telefono\n";
            pasa++;
        }
         for (int i = 0; i < listaTelefonos.size(); i++) {
            if ((listaTelefonos.get(i).getTipotelefono().getNombre().equals(nuevoTelefono.getTipotelefono().getNombre())) && (!(listaTelefonos.get(i).getFechavigencia().before(nuevoTelefono.getFechavigencia())) && !(nuevoTelefono.getFechavigencia().before(listaTelefonos.get(i).getFechavigencia())))){ 
                System.out.println("Entro al IF Tipo Telefono");
                context.update("formularioDialogos:existeTipoTelefono");
                context.execute("existeTipoTelefono.show()");
                pasaA++;
            } if(pasa != 0){
                    context.update("formularioDialogos:validacionNuevoTelefono");
                    context.execute("validacionNuevoTelefono.show()");
                    
                }
        }
         
         
        
        if (pasa == 0 && pasaA == 0) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                //System.out.println("Desactivar");
                //System.out.println("TipoLista= " + tipoLista);
                tFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tFecha");
                tFecha.setFilterStyle("display: none; visibility: hidden;");
                tTipoTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tTipoTelefono");
                tTipoTelefono.setFilterStyle("display: none; visibility: hidden;");
                tNumero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tNumero");
                tNumero.setFilterStyle("display: none; visibility: hidden;");
                tCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tCiudad");
                tCiudad.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTelefonosPersona");
                bandera = 0;
                filtradosListaTelefonos = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA TELEFONOS.
            k++;
            l = BigInteger.valueOf(k);
            nuevoTelefono.setSecuencia(l);
            nuevoTelefono.setPersona(persona);
            if (nuevoTelefono.getCiudad().getSecuencia() == null) {
                nuevoTelefono.setCiudad(null);
            }
            listaTelefonosCrear.add(nuevoTelefono);

            listaTelefonos.add(nuevoTelefono);
            nuevoTelefono = new Telefonos();
            nuevoTelefono.setTipotelefono(new TiposTelefonos());
            nuevoTelefono.setCiudad(new Ciudades());
            context.update("form:datosTelefonosPersona");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroTelefono.hide()");
            index = -1;
            secRegistro = null;
        } else {
      //      context.update("formularioDialogos:validacionNuevoTelefono");
        //    context.execute("validacionNuevoTelefono.show()");
        }
    }

    //METODOS L.O.V CIUDADES
    public void actualizarCiudad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaTelefonos.get(index).setCiudad(seleccionCiudades);
                if (!listaTelefonosCrear.contains(listaTelefonos.get(index))) {
                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(listaTelefonos.get(index));
                    } else if (!listaTelefonosModificar.contains(listaTelefonos.get(index))) {
                        listaTelefonosModificar.add(listaTelefonos.get(index));
                    }
                }
            } else {
                filtradosListaTelefonos.get(index).setCiudad(seleccionCiudades);
                if (!listaTelefonosCrear.contains(filtradosListaTelefonos.get(index))) {
                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(index));
                    } else if (!listaTelefonosModificar.contains(filtradosListaTelefonos.get(index))) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosTelefonosPersona");
        } else if (tipoActualizacion == 1) {
            nuevoTelefono.setCiudad(seleccionCiudades);
            context.update("formularioDialogos:nuevoTelefono");
        } else if (tipoActualizacion == 2) {
            duplicarTelefono.setCiudad(seleccionCiudades);
            context.update("formularioDialogos:duplicarTelefono");
        }
        filtradoslistaCiudades = null;
        seleccionCiudades = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("ciudadesDialogo.hide()");
        context.reset("formularioDialogos:LOVCiudades:globalFilter");
        context.update("formularioDialogos:LOVCiudades");
    }

    public void cancelarCambioCiudad() {
        filtradoslistaCiudades = null;
        seleccionCiudades = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaTelefonos.get(index).getSecuencia();
                if (cualCelda == 1) {
                    TipoTelefono = listaTelefonos.get(index).getTipotelefono().getNombre();
                } else if (cualCelda == 3) {
                    Ciudad = listaTelefonos.get(index).getCiudad().getNombre();
                }
            } else {
                secRegistro = filtradosListaTelefonos.get(index).getSecuencia();
                if (cualCelda == 1) {
                    TipoTelefono = filtradosListaTelefonos.get(index).getTipotelefono().getNombre();
                } else if (cualCelda == 3) {
                    Ciudad = filtradosListaTelefonos.get(index).getCiudad().getNombre();
                }
            }
        }
    }

    //AUTOCOMPLETAR
    public void modificarTelefonos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaTelefonosCrear.contains(listaTelefonos.get(indice))) {

                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(listaTelefonos.get(indice));
                    } else if (!listaTelefonosModificar.contains(listaTelefonos.get(indice))) {
                        listaTelefonosModificar.add(listaTelefonos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaTelefonosCrear.contains(filtradosListaTelefonos.get(indice))) {

                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(indice));
                    } else if (!listaTelefonosModificar.contains(filtradosListaTelefonos.get(indice))) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosTelefonosPersona");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOTELEFONO")) {
            if (tipoLista == 0) {
                listaTelefonos.get(indice).getTipotelefono().setNombre(TipoTelefono);
            } else {
                filtradosListaTelefonos.get(indice).getTipotelefono().setNombre(TipoTelefono);
            }

            for (int i = 0; i < listaTiposTelefonos.size(); i++) {
                if (listaTiposTelefonos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaTelefonos.get(indice).setTipotelefono(listaTiposTelefonos.get(indiceUnicoElemento));
                } else {
                    filtradosListaTelefonos.get(indice).setTipotelefono(listaTiposTelefonos.get(indiceUnicoElemento));
                }
                listaTiposTelefonos.clear();
                getListaTiposTelefonos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposTelefonosDialogo");
                context.execute("tiposTelefonosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoLista == 0) {
                listaTelefonos.get(indice).getCiudad().setNombre(Ciudad);
            } else {
                filtradosListaTelefonos.get(indice).getCiudad().setNombre(Ciudad);
            }
            for (int i = 0; i < listaCiudades.size(); i++) {
                if (listaCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaTelefonos.get(indice).setCiudad(listaCiudades.get(indiceUnicoElemento));
                } else {
                    filtradosListaTelefonos.get(indice).setCiudad(listaCiudades.get(indiceUnicoElemento));
                }
                listaCiudades.clear();
                getListaCiudades();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:ciudadesDialogo");
                context.execute("ciudadesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaTelefonosCrear.contains(listaTelefonos.get(indice))) {
                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(listaTelefonos.get(indice));
                    } else if (!listaTelefonosModificar.contains(listaTelefonos.get(indice))) {
                        listaTelefonosModificar.add(listaTelefonos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaTelefonosCrear.contains(filtradosListaTelefonos.get(indice))) {

                    if (listaTelefonosModificar.isEmpty()) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(indice));
                    } else if (!listaTelefonosModificar.contains(filtradosListaTelefonos.get(indice))) {
                        listaTelefonosModificar.add(filtradosListaTelefonos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosTelefonosPersona");
    }

    //GUARDAR
    public void guardarCambiosTelefono() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Telefonos");
            if (!listaTelefonosBorrar.isEmpty()) {
                for (int i = 0; i < listaTelefonosBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listaTelefonosBorrar.get(i).getTipotelefono().getSecuencia() == null) {
                        listaTelefonosBorrar.get(i).setTipotelefono(null);
                        administrarTelefonos.borrarTelefono(listaTelefonosBorrar.get(i));
                    } else {
                        if (listaTelefonosBorrar.get(i).getCiudad().getSecuencia() == null) {
                            listaTelefonosBorrar.get(i).setCiudad(null);
                            administrarTelefonos.borrarTelefono(listaTelefonosBorrar.get(i));
                        } else {
                            administrarTelefonos.borrarTelefono(listaTelefonosBorrar.get(i));
                        }

                    }

                }
                System.out.println("Entra");
                listaTelefonosBorrar.clear();
            }

            if (!listaTelefonosCrear.isEmpty()) {
                for (int i = 0; i < listaTelefonosCrear.size(); i++) {
                    System.out.println("Creando...");
                    if (listaTelefonosCrear.get(i).getTipotelefono().getSecuencia() == null) {
                        listaTelefonosCrear.get(i).setTipotelefono(null);
                        administrarTelefonos.crearTelefono(listaTelefonosCrear.get(i));
                    } else {
                        if (listaTelefonosCrear.get(i).getCiudad().getSecuencia() == null) {
                            listaTelefonosCrear.get(i).setCiudad(null);
                            administrarTelefonos.crearTelefono(listaTelefonosCrear.get(i));
                        } else {
                            administrarTelefonos.crearTelefono(listaTelefonosCrear.get(i));

                        }

                    }
                }
                System.out.println("LimpiaLista");
                listaTelefonosCrear.clear();
            }
            if (!listaTelefonosModificar.isEmpty()) {
                administrarTelefonos.modificarTelefono(listaTelefonosModificar);
                listaTelefonosModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listaTelefonos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTelefonosPersona");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            //  k = 0;
        }
        System.out.println("Tamaño lista: " + listaTelefonosCrear.size());
        System.out.println("Valor k: " + k);
        index = -1;
        secRegistro = null;
    }

    public void salir() {

        if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            tFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tFecha");
            tFecha.setFilterStyle("display: none; visibility: hidden;");
            tTipoTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tTipoTelefono");
            tTipoTelefono.setFilterStyle("display: none; visibility: hidden;");
            tNumero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tNumero");
            tNumero.setFilterStyle("display: none; visibility: hidden;");
            tCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTelefonosPersona:tCiudad");
            tCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTelefonosPersona");
            bandera = 0;
            filtradosListaTelefonos = null;
            tipoLista = 0;
        }

        listaTelefonosBorrar.clear();
        listaTelefonosCrear.clear();
        listaTelefonosModificar.clear();
        index = -1;
        secRegistro = null;
        //  k = 0;
        listaTelefonos = null;
        guardado = true;
        permitirIndex = true;

    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("TIPOT")) {
            if (tipoNuevo == 1) {
                TipoTelefono = nuevoTelefono.getTipotelefono().getNombre();
            } else if (tipoNuevo == 2) {
                TipoTelefono = duplicarTelefono.getTipotelefono().getNombre();
            }
        } else if (Campo.equals("CIUDAD")) {
            if (tipoNuevo == 1) {
                Ciudad = nuevoTelefono.getCiudad().getNombre();
            } else if (tipoNuevo == 2) {
                Ciudad = duplicarTelefono.getCiudad().getNombre();
            }
        } 
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOT")) {
            if (tipoNuevo == 1) {
                nuevoTelefono.getTipotelefono().setNombre(TipoTelefono);
            } else if (tipoNuevo == 2) {
                duplicarTelefono.getTipotelefono().setNombre(TipoTelefono);
            }
            for (int i = 0; i < listaTiposTelefonos.size(); i++) {
                if (listaTiposTelefonos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTelefono.setTipotelefono(listaTiposTelefonos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTipoTelefono");
                } else if (tipoNuevo == 2) {
                    duplicarTelefono.setTipotelefono(listaTiposTelefonos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoTelefono");
                }
                listaTiposTelefonos.clear();
                getListaTiposTelefonos();
            } else {
                context.update("form:tiposTelefonosDialogo");
                context.execute("tiposTelefonosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTipoTelefono");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoTelefono");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoNuevo == 1) {
                nuevoTelefono.getCiudad().setNombre(Ciudad);
            } else if (tipoNuevo == 2) {
                duplicarTelefono.getCiudad().setNombre(Ciudad);
            }
            for (int i = 0; i < listaCiudades.size(); i++) {
                if (listaCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTelefono.setCiudad(listaCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCiudad");
                } else if (tipoNuevo == 2) {
                    duplicarTelefono.setCiudad(listaCiudades.get(indiceUnicoElemento));
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
            context.update("form:tiposTelefonosDialogo");
            context.execute("tiposTelefonosDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:ciudadesDialogo");
            context.execute("ciudadesDialogo.show()");
        }

    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaCiudades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TELEFONOS");
                System.out.println("resultado: " + resultado);
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
            if (administrarRastros.verificarHistoricosTabla("TELEFONOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GETTER AND SETTER
    public Personas getPersona() {
        if (persona == null) {
            persona = administrarTelefonos.encontrarPersona(secuenciaPersona);
        }
        return persona;
    }

    public List<Telefonos> getListaTelefonos() {
        if (listaTelefonos == null) {
            listaTelefonos = administrarTelefonos.telefonosPersona(secuenciaPersona);
        }
        return listaTelefonos;
    }

    public void setListaTelefonos(List<Telefonos> listaTelefonos) {
        this.listaTelefonos = listaTelefonos;
    }

    public List<Telefonos> getFiltradosListaTelefonos() {
        return filtradosListaTelefonos;
    }

    public void setFiltradosListaTelefonos(List<Telefonos> filtradosListaTelefonos) {
        this.filtradosListaTelefonos = filtradosListaTelefonos;
    }

    public List<TiposTelefonos> getListaTiposTelefonos() {
        if (listaTiposTelefonos == null) {
            listaTiposTelefonos = administrarTelefonos.lovTiposTelefonos();
        }
        return listaTiposTelefonos;
    }

    public void setListaTiposTelefonos(List<TiposTelefonos> listaTiposTelefonos) {
        this.listaTiposTelefonos = listaTiposTelefonos;
    }

    public List<TiposTelefonos> getFiltradoslistaTiposTelefonos() {
        return filtradoslistaTiposTelefonos;
    }

    public void setFiltradoslistaTiposTelefonos(List<TiposTelefonos> filtradoslistaTiposTelefonos) {
        this.filtradoslistaTiposTelefonos = filtradoslistaTiposTelefonos;
    }

    public TiposTelefonos getSeleccionTipoTelefono() {
        return seleccionTipoTelefono;
    }

    public void setSeleccionTipoTelefono(TiposTelefonos seleccionTipoTelefono) {
        this.seleccionTipoTelefono = seleccionTipoTelefono;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<Ciudades> getListaCiudades() {
        if (listaCiudades.isEmpty()) {
            listaCiudades = administrarTelefonos.lovCiudades();
        }

        return listaCiudades;
    }

    public void setListaCiudades(List<Ciudades> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public List<Ciudades> getFiltradoslistaCiudades() {
        return filtradoslistaCiudades;
    }

    public void setFiltradoslistaCiudades(List<Ciudades> filtradoslistaCiudades) {
        this.filtradoslistaCiudades = filtradoslistaCiudades;
    }

    public Ciudades getSeleccionCiudades() {
        return seleccionCiudades;
    }

    public void setSeleccionCiudades(Ciudades seleccionCiudades) {
        this.seleccionCiudades = seleccionCiudades;
    }

    public Telefonos getEditarTelefono() {
        return editarTelefono;
    }

    public void setEditarTelefono(Telefonos editarTelefono) {
        this.editarTelefono = editarTelefono;
    }

    public Telefonos getNuevoTelefono() {
        return nuevoTelefono;
    }

    public void setNuevoTelefono(Telefonos nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public Telefonos getDuplicarTelefono() {
        return duplicarTelefono;
    }

    public void setDuplicarTelefono(Telefonos duplicarTelefono) {
        this.duplicarTelefono = duplicarTelefono;
    }
}

package Controlador;

import Entidades.Personas;
import Entidades.Telefonos;
import Entidades.TiposTelefonos;
import InterfaceAdministrar.AdministrarTelefonosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPerTelefonos implements Serializable {

    @EJB
    AdministrarTelefonosInterface administrarTelefonos;
    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaPersona;
    private Personas persona;
    //LISTA TELEFONOS
    private List<Telefonos> listaTelefonos;
    private List<Telefonos> filtradosListaTelefonos;
    //TIPOS TELEFONOS
    private List<TiposTelefonos> listaTiposTelefonos;
    private List<TiposTelefonos> filtradoslistaTiposTelefonos;
    private TiposTelefonos seleccionTipoTelefono;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    //Modificar Telefono
    private List<Telefonos> listaTelefonosModificar;
    private boolean guardado, guardarOk;
    //Crear Telefono
    public Telefonos nuevoTelefono;
    private List<Telefonos> listaTelefonosCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Telefonos> listaTelefonosBorrar;
    //editar celda
    //private VigenciasCargos editarVC;
    private int cualCelda, tipoLista;
    //private boolean cambioEditor, aceptarEditar;
    //duplicar
    //private VigenciasCargos duplicarVC;
    

    public ControlPerTelefonos() {
        secuenciaPersona = BigInteger.valueOf(10668967);
    }

    public void recibirPersona(BigInteger secPersona) {
        secuenciaPersona = secPersona;
        persona = null;
        getPersona();
        listaTelefonos = null;
        getListaTelefonos();
        listaTiposTelefonos = null;
        getListaTiposTelefonos();
    }

    public void refrescar() {
        listaTelefonos = null;
        getListaTelefonos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTelefonosPersona");
    }

    public void asignarIndex(Integer indice, int dlg) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (dlg == 0) {
            tipoActualizacion = 0;
            context.update("formularioDialogos:tiposTelefonosDialogo");
            context.execute("tiposTelefonosDialogo.show()");
        } else if (dlg == 1) {
            /*tipoActualizacion = 0;
            context.update("form:cargosDialog");
            context.execute("cargosDialog.show()");*/
        } 
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
            //permitirIndex = true;
            context.update("form:datosVCEmpleado");
        } else if (tipoActualizacion == 1) {
            /*nuevaVigencia.setMotivocambiocargo(motivoSeleccionado);
            context.update("formularioDialogos:nuevaVC");*/
        } else if (tipoActualizacion == 2) {
            /*duplicarVC.setMotivocambiocargo(motivoSeleccionado);
            context.update("formularioDialogos:duplicarVC");*/
        }
        /*filterMotivos = null;
        motivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("motivosDialog.hide()");
        context.reset("form:motivosCambCargo:globalFilter");
        context.update("form:motivosCambCargo");*/
    }

    public void activarAceptar() {
        aceptar = false;
    }
    
    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
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
}

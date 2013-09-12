package Controlador;

import Entidades.Ciudades;
import Entidades.Personas;
import Entidades.Telefonos;
import Entidades.TiposTelefonos;
import InterfaceAdministrar.AdministrarTelefonosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
    //L.O.V TIPOS TELEFONOS
    private List<TiposTelefonos> listaTiposTelefonos;
    private List<TiposTelefonos> filtradoslistaTiposTelefonos;
    private TiposTelefonos seleccionTipoTelefono;
    //L.O.V CIUDADES
    private List<Ciudades> listaCiudades;
    private List<Ciudades> filtradoslistaCiudades;
    private Ciudades seleccionCiudades;
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
    //RASTRO
    private BigInteger secRegistro;

    public ControlPerTelefonos() {
        secuenciaPersona = BigInteger.valueOf(10668967);
        aceptar = true;
        tipoLista = 0;
        listaTelefonosBorrar = new ArrayList<Telefonos>();
        listaTelefonosCrear = new ArrayList<Telefonos>();
        listaTelefonosModificar = new ArrayList<Telefonos>();
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

    public void asignarIndex(Integer indice, int dlg) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (dlg == 0) {
            tipoActualizacion = 0;
            context.update("formularioDialogos:tiposTelefonosDialogo");
            context.execute("tiposTelefonosDialogo.show()");
        } else if (dlg == 1) {
            tipoActualizacion = 0;
            context.update("formularioDialogos:ciudadesDialogo");
            context.execute("ciudadesDialogo.show()");
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
            context.update("form:datosTelefonosPersona");
        } else if (tipoActualizacion == 1) {
            /*nuevaVigencia.setMotivocambiocargo(motivoSeleccionado);
             context.update("formularioDialogos:nuevaVC");*/
        } else if (tipoActualizacion == 2) {
            /*duplicarVC.setMotivocambiocargo(motivoSeleccionado);
             context.update("formularioDialogos:duplicarVC");*/
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
     //permitirIndex = true;
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
            //permitirIndex = true;
            context.update("form:datosTelefonosPersona");
        } else if (tipoActualizacion == 1) {
            /*nuevaVigencia.setMotivocambiocargo(motivoSeleccionado);
             context.update("formularioDialogos:nuevaVC");*/
        } else if (tipoActualizacion == 2) {
            /*duplicarVC.setMotivocambiocargo(motivoSeleccionado);
             context.update("formularioDialogos:duplicarVC");*/
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
     //permitirIndex = true;
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

    public List<Ciudades> getListaCiudades() {
        if (listaCiudades == null) {
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
    
    
}

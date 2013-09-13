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
        permitirIndex = true;
        secuenciaPersona = BigInteger.valueOf(10668967);
        aceptar = true;
        tipoLista = 0;
        listaTelefonosBorrar = new ArrayList<Telefonos>();
        listaTelefonosCrear = new ArrayList<Telefonos>();
        listaTelefonosModificar = new ArrayList<Telefonos>();
        //INICIALIZAR LOVS
        listaTiposTelefonos = new ArrayList<TiposTelefonos>();
        listaCiudades = new ArrayList<Ciudades>();
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
            permitirIndex = true;
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
        permitirIndex = true;
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

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listaTelefonos.get(index).getSecuencia();
            if (cualCelda == 1) {
                TipoTelefono = listaTelefonos.get(index).getTipotelefono().getNombre();
            } else if (cualCelda == 3) {
                Ciudad = listaTelefonos.get(index).getCiudad().getNombre();
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
        if (listaTiposTelefonos.isEmpty()) {
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
}

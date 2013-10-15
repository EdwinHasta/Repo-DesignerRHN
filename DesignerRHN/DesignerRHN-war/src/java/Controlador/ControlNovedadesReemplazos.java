/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Encargaturas;
import Entidades.Estructuras;
import Entidades.MotivosReemplazos;
import Entidades.TiposReemplazos;
import InterfaceAdministrar.AdministrarNovedadesReemplazosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlNovedadesReemplazos implements Serializable {

    @EJB
    AdministrarNovedadesReemplazosInterface administrarNovedadesReemplazos; // Encargaturas
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaEmpleado;
    private Empleados empleado;
    //LISTA VIGENCIAS FORMALES
    private List<Encargaturas> listaEncargaturas;
    private List<Encargaturas> filtradosListaEncargaturas;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //Consultas sobre detalles
    private Empleados empleadoParametro;
    private String clienteParametroProyecto;
    private String plataformaParametroProyecto;
    //editar celda
    private Encargaturas editarEncargaturas;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //L.O.V TIPOSREEMPLAZOS
    private List<TiposReemplazos> listaTiposReemplazos;
    private List<TiposReemplazos> filtradoslistaTiposReemplazos;
    private TiposReemplazos seleccionTiposReemplazos;
    //L.O.V MOTIVOSREEMPLAZOS
    private List<MotivosReemplazos> listaMotivosReemplazos;
    private List<MotivosReemplazos> filtradoslistaMotivosReemplazos;
    private MotivosReemplazos seleccionMotivosReemplazos;
    //L.O.V ESTRUCTURAS
    private List<Estructuras> listaEstructuras;
    private List<Estructuras> filtradoslistaEstructuras;
    private Estructuras seleccionEstructuras;
    //L.O.V EMPLEADOS
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradoslistaEmpleados;
    private Empleados seleccionEmpleados;
    private Empleados seleccionEmpleadosReemplazados;
    ///////////////////////////////////////////////FALTA EL LOV DE CARGOS
    //RASTROS
    private BigInteger secRegistro;
    //LISTA QUE NO ES LISTA - 1 SOLO ELEMENTO
    private List<Empleados> listaFalsaEmpleados;
    private List<Empleados> filtradosListaFalsaEmpleados;
    //Crear Encargaturas
    private List<Encargaturas> listaEncargaturasCrear;
    public Encargaturas nuevaEncargatura;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Vigencias Proyectos
    private List<Encargaturas> listaEncargaturasModificar;
    private boolean guardado, guardarOk;
    //Borrar Vigencias Proyectos
    private List<Encargaturas> listaEncargaturasBorrar;
    //Cual Tabla
    private int CualTabla;
    //Duplicar
    private Encargaturas duplicarEncargaturas;
    //AUTOCOMPLETAR
    private String Reemplazado, TipoReemplazo, MotivoReemplazo, Cargo, Estructura;

    public ControlNovedadesReemplazos() {
        permitirIndex = true;
        secuenciaEmpleado = BigInteger.valueOf(10661474);
        aceptar = true;
        empleadoParametro = new Empleados();
        listaEncargaturas = null;
        listaTiposReemplazos = new ArrayList<TiposReemplazos>();
        listaMotivosReemplazos = new ArrayList<MotivosReemplazos>();
        listaEstructuras = new ArrayList<Estructuras>();
        listaEmpleados = new ArrayList<Empleados>();
        listaEncargaturasBorrar = new ArrayList<Encargaturas>();
        listaEncargaturasCrear = new ArrayList<Encargaturas>();
        listaEncargaturasModificar = new ArrayList<Encargaturas>();
        listaFalsaEmpleados = null;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;

    }

    public void recibirEmpleado(BigInteger secEmpleado) {
        secuenciaEmpleado = secEmpleado;
        empleado = null;
        getEmpleado();
        listaEncargaturas = null;
        getListaEncargaturas();
        listaTiposReemplazos = null;
        getListaTiposReemplazos();
        listaMotivosReemplazos = null;
        getListaMotivosReemplazos();
        listaEstructuras = null;
        getListaEstructuras();
        listaEmpleados = null;
        getListaEmpleados();
        aceptar = true;
        listaFalsaEmpleados.clear();
        getEmpleadoParametro();
        listaFalsaEmpleados.add(empleadoParametro);

    }

    //AUTOCOMPLETAR
    public void modificarEncargaturas(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaEncargaturasCrear.contains(listaEncargaturas.get(indice))) {

                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(indice));
                    } else if (!listaEncargaturasModificar.contains(listaEncargaturas.get(indice))) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaEncargaturasCrear.contains(filtradosListaEncargaturas.get(indice))) {

                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(indice));
                    } else if (!listaEncargaturasModificar.contains(filtradosListaEncargaturas.get(indice))) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosEncargaturasEmpleado");
        } else if (confirmarCambio.equalsIgnoreCase("REEMPLAZADO")) {
            if (tipoLista == 0) {
                listaEncargaturas.get(indice).getReemplazado().getPersona().setNombreCompleto(Reemplazado);
            } else {
                filtradosListaEncargaturas.get(indice).getReemplazado().getPersona().setNombreCompleto(Reemplazado);
            }

            for (int i = 0; i < listaEncargaturas.size(); i++) {
                if (listaEncargaturas.get(i).getEmpleado().getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaEncargaturas.get(indice).setReemplazado(listaEmpleados.get(indiceUnicoElemento));
                } else {
                    filtradosListaEncargaturas.get(indice).setReemplazado(listaEmpleados.get(indiceUnicoElemento));
                }
                listaEmpleados.clear();
                getListaEmpleados();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:empleadosAbajoDialogo");
                context.execute("empleadosAbajoDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOREEMPLAZO")) {
            if (tipoLista == 0) {
                listaEncargaturas.get(indice).getTiporeemplazo().setNombre(TipoReemplazo);
            } else {
                filtradosListaEncargaturas.get(indice).getTiporeemplazo().setNombre(TipoReemplazo);
            }
            for (int i = 0; i < listaTiposReemplazos.size(); i++) {
                if (listaTiposReemplazos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaEncargaturas.get(indice).setTiporeemplazo(listaTiposReemplazos.get(indiceUnicoElemento));
                } else {
                    filtradosListaEncargaturas.get(indice).setTiporeemplazo(listaTiposReemplazos.get(indiceUnicoElemento));
                }
                listaTiposReemplazos.clear();
                getListaTiposReemplazos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposReemplazosDialogo");
                context.execute("tiposReemplazosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOREEMPLAZO")) {
            if (tipoLista == 0) {
                listaEncargaturas.get(indice).getMotivoreemplazo().setNombre(MotivoReemplazo);
            } else {
                filtradosListaEncargaturas.get(indice).getMotivoreemplazo().setNombre(MotivoReemplazo);
            }
            for (int i = 0; i < listaMotivosReemplazos.size(); i++) {
                if (listaMotivosReemplazos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaEncargaturas.get(indice).setMotivoreemplazo(listaMotivosReemplazos.get(indiceUnicoElemento));
                } else {
                    filtradosListaEncargaturas.get(indice).setMotivoreemplazo(listaMotivosReemplazos.get(indiceUnicoElemento));
                }
                listaMotivosReemplazos.clear();
                getListaMotivosReemplazos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:motivosReemplazosDialogo");
                context.execute("motivosReemplazosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("ESTRUCTURAS")) {
            if (tipoLista == 0) {
                listaEncargaturas.get(indice).getEstructura().setNombre(Estructura);
            } else {
                filtradosListaEncargaturas.get(indice).getEstructura().setNombre(Estructura);
            }
            for (int i = 0; i < listaEstructuras.size(); i++) {
                if (listaEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaEncargaturas.get(indice).setEstructura(listaEstructuras.get(indiceUnicoElemento));
                } else {
                    filtradosListaEncargaturas.get(indice).setEstructura(listaEstructuras.get(indiceUnicoElemento));
                }
                listaEstructuras.clear();
                getListaEstructuras();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:estructurasDialogo");
                context.execute("estructurasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaEncargaturasCrear.contains(listaEncargaturas.get(indice))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(indice));
                    } else if (!listaEncargaturasModificar.contains(listaEncargaturas.get(indice))) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaEncargaturasCrear.contains(filtradosListaEncargaturas.get(indice))) {

                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(indice));
                    } else if (!listaEncargaturasModificar.contains(filtradosListaEncargaturas.get(indice))) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosEncargaturasEmpleado");
    }

    //Ubicacion Celda Indice.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {

            index = indice;
            cualCelda = celda;
            //tablaImprimir = ":formExportar:datosVigenciasFormalesExportar";
            //nombreArchivo = "VigenciasFormalesXML";
            //RequestContext context = RequestContext.getCurrentInstance();
            //context.update("form:exportarXML");
            if (tipoLista == 0) {
                secRegistro = listaEncargaturas.get(index).getSecuencia();
                if (cualCelda == 0) {
                    Reemplazado = listaEncargaturas.get(index).getEmpleado().getPersona().getNombreCompleto();
                } else if (cualCelda == 1) {
                    TipoReemplazo = listaEncargaturas.get(index).getTiporeemplazo().getNombre();
                    /*else if (cualCelda == 6) { CARGO es la 6a Casilla
                     Cargo = listaVigenciasProyectos.get(index).getPryCargoproyecto().getNombre();
                     }*/
                } else if (cualCelda == 6) {
                    MotivoReemplazo = listaEncargaturas.get(index).getMotivoreemplazo().getNombre();
                } else if (cualCelda == 7) {
                    Estructura = listaEncargaturas.get(index).getEstructura().getNombre();
                }

            } else {
                secRegistro = filtradosListaEncargaturas.get(index).getSecuencia();
                if (cualCelda == 0) {
                    Reemplazado = filtradosListaEncargaturas.get(index).getEmpleado().getPersona().getNombreCompleto();
                } else if (cualCelda == 1) {
                    TipoReemplazo = filtradosListaEncargaturas.get(index).getTiporeemplazo().getNombre();
                    /*else if (cualCelda == 6) {
                     Cargo = filtradosListaEncargaturas.get(index).getPryCargoproyecto().getNombre();
                     }*/
                } else if (cualCelda == 6) {
                    MotivoReemplazo = filtradosListaEncargaturas.get(index).getMotivoreemplazo().getNombre();
                } else if (cualCelda == 7) {
                    Estructura = filtradosListaEncargaturas.get(index).getEstructura().getNombre();
                }
            }
        }
        System.out.println("Index: " + index + " Celda: " + celda);
    }

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
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:tiposReemplazosDialogo");
            context.execute("tiposReemplazosDialogo.show()");
        } else if (dlg == 2) {
            context.update("formularioDialogos:motivosReemplazosDialogo");
            context.execute("motivosReemplazosDialogo.show()");
        } else if (dlg == 3) {
            context.update("formularioDialogos:cargosDialogo");
            context.execute("cargosDialogo.show()");
        } else if (dlg == 4) {
            context.update("formularioDialogos:estructurasDialogo");
            context.execute("estructurasDialogo.show()");
        } else if (dlg == 5) {
            context.update("formularioDialogos:empleadosAbajoDialogo");
            context.execute("empleadosAbajoDialogo.show()");
        }

    }

    public void actualizarEmpleadosFalso() {


        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaFalsaEmpleados.isEmpty()) {
            listaFalsaEmpleados.clear();
            listaFalsaEmpleados.add(seleccionEmpleados);
        } else {
            listaFalsaEmpleados.add(seleccionEmpleados);

        }
        secuenciaEmpleado = seleccionEmpleados.getSecuencia();
        for (int i = 0; i < listaFalsaEmpleados.size(); i++) {
            System.out.println("En la lista está:" + listaFalsaEmpleados.get(i).getPersona().getNombre());
        }
        listaEncargaturas = null;
        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:datosEncargaturasEmpleado");
        filtradosListaFalsaEmpleados = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarEmpleados() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Index: " + index + " Tipo Actualización: " + tipoActualizacion);
        if (tipoActualizacion == 0) {
            System.out.println("Tipo Lista: " + tipoLista);
            if (tipoLista == 0) {
                listaEncargaturas.get(index).setReemplazado(seleccionEmpleadosReemplazados);
                if (!listaEncargaturasCrear.contains(listaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(listaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    }
                }
            } else {
                filtradosListaEncargaturas.get(index).setReemplazado(seleccionEmpleadosReemplazados);
                if (!listaEncargaturasCrear.contains(filtradosListaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(filtradosListaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }

            for (int i = 0; i < listaEncargaturas.size(); i++) {
                System.out.println("En la lista encargaturas está:" + listaEncargaturas.get(i).getReemplazado().getPersona().getNombreCompleto());
                System.out.println("Seleccionado: " + seleccionEmpleadosReemplazados.getPersona().getNombreCompleto());
            }
            permitirIndex = true;
            context.update("form:datosEncargaturasEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaEncargatura.setEmpleado(seleccionEmpleadosReemplazados);
            context.update("formularioDialogos:nuevaEncargatura");
        } else if (tipoActualizacion == 2) {
            duplicarEncargaturas.setEmpleado(seleccionEmpleadosReemplazados);
            context.update("formularioDialogos:duplicarEncargatura");
        }
        filtradoslistaEmpleados = null;
        seleccionEmpleadosReemplazados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("empleadosAbajoDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleadosAbajo:globalFilter");
        context.update("formularioDialogos:LOVEmpleadosAbajo");
    }

    public void actualizarTiposReemplazos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaEncargaturas.get(index).setTiporeemplazo(seleccionTiposReemplazos);
                if (!listaEncargaturasCrear.contains(listaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(listaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    }
                }
            } else {
                filtradosListaEncargaturas.get(index).setTiporeemplazo(seleccionTiposReemplazos);
                if (!listaEncargaturasCrear.contains(filtradosListaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(filtradosListaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosEncargaturasEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaEncargatura.setTiporeemplazo(seleccionTiposReemplazos);
            context.update("formularioDialogos:nuevaEncargatura");
        } else if (tipoActualizacion == 2) {
            duplicarEncargaturas.setTiporeemplazo(seleccionTiposReemplazos);
            context.update("formularioDialogos:duplicarEncargatura");

        }
        filtradoslistaTiposReemplazos = null;
        seleccionTiposReemplazos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("tiposReemplazosDialogo.hide()");
        context.reset("formularioDialogos:LOVTiposReemplazos:globalFilter");
        context.update("formularioDialogos:LOVTiposReemplazos");
    }

    public void actualizarMotivosReemplazos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaEncargaturas.get(index).setTiporeemplazo(seleccionTiposReemplazos);
                if (!listaEncargaturasCrear.contains(listaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(listaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    }
                }
            } else {
                filtradosListaEncargaturas.get(index).setTiporeemplazo(seleccionTiposReemplazos);
                if (!listaEncargaturasCrear.contains(filtradosListaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(filtradosListaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosEncargaturasEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaEncargatura.setTiporeemplazo(seleccionTiposReemplazos);
            context.update("formularioDialogos:nuevaEncargatura");
        } else if (tipoActualizacion == 2) {
            duplicarEncargaturas.setTiporeemplazo(seleccionTiposReemplazos);
            context.update("formularioDialogos:duplicarEncargatura");

        }
        filtradoslistaTiposReemplazos = null;
        seleccionTiposReemplazos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("motivosReemplazosDialogo.hide()");
        context.reset("formularioDialogos:LOVMotivosReemplazos:globalFilter");
        context.update("formularioDialogos:LOVMotivosReemplazos");
    }

    public void actualizarEstructuras() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaEncargaturas.get(index).setEstructura(seleccionEstructuras);
                if (!listaEncargaturasCrear.contains(listaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(listaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(listaEncargaturas.get(index));
                    }
                }
            } else {
                filtradosListaEncargaturas.get(index).setEstructura(seleccionEstructuras);
                if (!listaEncargaturasCrear.contains(filtradosListaEncargaturas.get(index))) {
                    if (listaEncargaturasModificar.isEmpty()) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    } else if (!listaEncargaturasModificar.contains(filtradosListaEncargaturas.get(index))) {
                        listaEncargaturasModificar.add(filtradosListaEncargaturas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosEncargaturasEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaEncargatura.setEstructura(seleccionEstructuras);
            context.update("formularioDialogos:nuevaEncargatura");
        } else if (tipoActualizacion == 2) {
            duplicarEncargaturas.setEstructura(seleccionEstructuras);
            context.update("formularioDialogos:duplicarEncargatura");

        }
        filtradoslistaEstructuras = null;
        seleccionEstructuras = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("estructurasDialogo.hide()");
        context.reset("formularioDialogos:LOVEstructuras:globalFilter");
        context.update("formularioDialogos:LOVEstructuras");
    }

    public void cancelarCambioEstructuras() {
        filtradoslistaEstructuras = null;
        seleccionEstructuras = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioEmpleados() {
        filtradoslistaEmpleados = null;
        seleccionEmpleados = null;
        seleccionEmpleadosReemplazados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioMotivosReemplazos() {
        filtradoslistaMotivosReemplazos = null;
        seleccionMotivosReemplazos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioTiposReemplazos() {
        filtradoslistaTiposReemplazos = null;
        seleccionTiposReemplazos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
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

    //GUARDAR
    public void guardarCambiosEncargaturas() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Proyectos");
            if (!listaEncargaturasBorrar.isEmpty()) {
                for (int i = 0; i < listaEncargaturasBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listaEncargaturasBorrar.get(i).getEmpleado().getSecuencia() == null) {
                        listaEncargaturasBorrar.get(i).setEmpleado(null);
                        administrarNovedadesReemplazos.borrarEncargaturas(listaEncargaturasBorrar.get(i));
                    } else {
                        if (listaEncargaturasBorrar.get(i).getTiporeemplazo().getSecuencia() == null) {
                            listaEncargaturasBorrar.get(i).setTiporeemplazo(null);
                            administrarNovedadesReemplazos.borrarEncargaturas(listaEncargaturasBorrar.get(i));
                        } else {
                            administrarNovedadesReemplazos.borrarEncargaturas(listaEncargaturasBorrar.get(i));
                        }

                    }

                }
                System.out.println("Entra");
                listaEncargaturasBorrar.clear();
            }

            if (!listaEncargaturasCrear.isEmpty()) {
                for (int i = 0; i < listaEncargaturasCrear.size(); i++) {
                    System.out.println("Creando...");
                    if (listaEncargaturasCrear.get(i).getEmpleado().getSecuencia() == null) {
                        listaEncargaturasCrear.get(i).setEmpleado(null);
                        administrarNovedadesReemplazos.crearEncargaturas(listaEncargaturasCrear.get(i));
                    } else {
                        if (listaEncargaturasCrear.get(i).getTiporeemplazo().getSecuencia() == null) {
                            listaEncargaturasCrear.get(i).setTiporeemplazo(null);
                            administrarNovedadesReemplazos.crearEncargaturas(listaEncargaturasCrear.get(i));
                        } else {
                            administrarNovedadesReemplazos.crearEncargaturas(listaEncargaturasCrear.get(i));

                        }

                    }
                }
                System.out.println("LimpiaLista");
                listaEncargaturasCrear.clear();
            }
            if (!listaEncargaturasModificar.isEmpty()) {
                administrarNovedadesReemplazos.modificarEncargatura(listaEncargaturasModificar);
                listaEncargaturasModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listaEncargaturas = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEncargaturasEmpleado");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            //  k = 0;
        }
        System.out.println("Tamaño lista: " + listaEncargaturasCrear.size());
        System.out.println("Valor k: " + k);
        index = -1;
        secRegistro = null;
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEncargaturas = listaEncargaturas.get(index);
            }
            if (tipoLista == 1) {
                editarEncargaturas = filtradosListaEncargaturas.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarReemplazados");
                context.execute("editarReemplazados.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarTiposReemplazos");
                context.execute("editarTiposReemplazos.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarFechasPagos");
                context.execute("editarFechasPagos.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarFechasIniciales");
                context.execute("editarFechasIniciales.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarFechasFinales");
                context.execute("editarFechasFinales.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarMotivosReemplazos");
                context.execute("editarMotivosReemplazos.show()");
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarEstructuras");
                context.execute("editarEstructuras.show()");
            } // Falta Cargos que es cualCelda == 5
            //else if (cualCelda == 5) {
            //  context.update("formularioDialogos:editarCantidadPersonas");
            //  context.execute("editarCantidadPersonas.show()");
            //  cualCelda = -1;

        }
        index = -1;
        secRegistro = null;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:empleadosAbajoDialogo");
                context.execute("empleadosAbajoDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:tiposReemplazosDialogo");
                context.execute("tiposReemplazosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:motivosReemplazosDialogo");
                context.execute("motivosReemplazosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:estructurasDialogo");
                context.execute("estructurasDialogo.show()");
            }//Falta Cargos cualCelda == 5 
        }
    }

    //Getter & Setter
    public Empleados getEmpleado() {
        if (empleado == null) {
            empleado = administrarNovedadesReemplazos.encontrarEmpleado(secuenciaEmpleado);
        }

        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public List<Encargaturas> getListaEncargaturas() {
        if (listaEncargaturas == null) {
            listaEncargaturas = administrarNovedadesReemplazos.encargaturasEmpleado(secuenciaEmpleado);
        }
        return listaEncargaturas;
    }

    public void setListaEncargaturas(List<Encargaturas> listaEncargaturas) {
        this.listaEncargaturas = listaEncargaturas;
    }

    public List<Encargaturas> getFiltradosListaEncargaturas() {
        return filtradosListaEncargaturas;
    }

    public void setFiltradosListaEncargaturas(List<Encargaturas> filtradosListaEncargaturas) {
        this.filtradosListaEncargaturas = filtradosListaEncargaturas;
    }

    public Empleados getEmpleadoParametro() {
        empleadoParametro = administrarNovedadesReemplazos.encontrarEmpleado(secuenciaEmpleado);
        return empleadoParametro;
    }

    public void setEmpleadoParametro(Empleados empleadoParametro) {
        this.empleadoParametro = empleadoParametro;
    }

    public List<TiposReemplazos> getListaTiposReemplazos() {
        if (listaTiposReemplazos == null) {
            listaTiposReemplazos = administrarNovedadesReemplazos.lovTiposReemplazos();
        }
        return listaTiposReemplazos;
    }

    public void setListaTiposReemplazos(List<TiposReemplazos> listaTiposReemplazos) {
        this.listaTiposReemplazos = listaTiposReemplazos;
    }

    public List<MotivosReemplazos> getListaMotivosReemplazos() {
        if (listaMotivosReemplazos == null) {
            listaMotivosReemplazos = administrarNovedadesReemplazos.lovMotivosReemplazos();
        }
        return listaMotivosReemplazos;
    }

    public void setListaMotivosReemplazos(List<MotivosReemplazos> listaMotivosReemplazos) {
        this.listaMotivosReemplazos = listaMotivosReemplazos;
    }

    public List<Estructuras> getListaEstructuras() {
        if (listaEstructuras == null) {
            listaEstructuras = administrarNovedadesReemplazos.lovEstructuras();
        }
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructuras> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesReemplazos.lovEmpleados();
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Empleados> getListaFalsaEmpleados() {
        if (listaFalsaEmpleados == null) {
            listaFalsaEmpleados = new ArrayList<Empleados>();
            recibirEmpleado(secuenciaEmpleado);
        }
        return listaFalsaEmpleados;
    }

    public void setListaFalsaEmpleados(List<Empleados> listaFalsaEmpleados) {
        this.listaFalsaEmpleados = listaFalsaEmpleados;
    }

    public List<TiposReemplazos> getFiltradoslistaTiposReemplazos() {
        return filtradoslistaTiposReemplazos;
    }

    public void setFiltradoslistaTiposReemplazos(List<TiposReemplazos> filtradoslistaTiposReemplazos) {
        this.filtradoslistaTiposReemplazos = filtradoslistaTiposReemplazos;
    }

    public List<MotivosReemplazos> getFiltradoslistaMotivosReemplazos() {
        return filtradoslistaMotivosReemplazos;
    }

    public void setFiltradoslistaMotivosReemplazos(List<MotivosReemplazos> filtradoslistaMotivosReemplazos) {
        this.filtradoslistaMotivosReemplazos = filtradoslistaMotivosReemplazos;
    }

    public List<Estructuras> getFiltradoslistaEstructuras() {
        return filtradoslistaEstructuras;
    }

    public void setFiltradoslistaEstructuras(List<Estructuras> filtradoslistaEstructuras) {
        this.filtradoslistaEstructuras = filtradoslistaEstructuras;
    }

    public List<Empleados> getFiltradoslistaEmpleados() {
        return filtradoslistaEmpleados;
    }

    public void setFiltradoslistaEmpleados(List<Empleados> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleados = filtradoslistaEmpleados;
    }

    public List<Empleados> getFiltradosListaFalsaEmpleados() {
        return filtradosListaFalsaEmpleados;
    }

    public void setFiltradosListaFalsaEmpleados(List<Empleados> filtradoListaFalsaEmpleados) {
        this.filtradosListaFalsaEmpleados = filtradoListaFalsaEmpleados;
    }

    public TiposReemplazos getSeleccionTiposReemplazos() {
        return seleccionTiposReemplazos;
    }

    public void setSeleccionTiposReemplazos(TiposReemplazos seleccionTiposReemplazos) {
        this.seleccionTiposReemplazos = seleccionTiposReemplazos;
    }

    public MotivosReemplazos getSeleccionMotivosReemplazos() {
        return seleccionMotivosReemplazos;
    }

    public void setSeleccionMotivosReemplazos(MotivosReemplazos seleccionMotivosReemplazos) {
        this.seleccionMotivosReemplazos = seleccionMotivosReemplazos;
    }

    public Estructuras getSeleccionEstructuras() {
        return seleccionEstructuras;
    }

    public void setSeleccionEstructuras(Estructuras seleccionEstructuras) {
        this.seleccionEstructuras = seleccionEstructuras;
    }

    public Empleados getSeleccionEmpleados() {
        return seleccionEmpleados;
    }

    public void setSeleccionEmpleados(Empleados seleccionEmpleados) {
        this.seleccionEmpleados = seleccionEmpleados;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public Empleados getSeleccionEmpleadosReemplazados() {
        return seleccionEmpleadosReemplazados;
    }

    public void setSeleccionEmpleadosReemplazados(Empleados seleccionEmpleadosReemplazados) {
        this.seleccionEmpleadosReemplazados = seleccionEmpleadosReemplazados;
    }

    public Encargaturas getEditarEncargaturas() {
        return editarEncargaturas;
    }

    public void setEditarEncargaturas(Encargaturas editarEncargaturas) {
        this.editarEncargaturas = editarEncargaturas;
    }
}

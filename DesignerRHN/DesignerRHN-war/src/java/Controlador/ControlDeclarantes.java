package Controlador;

import Entidades.Declarantes;
import Entidades.Personas;
import Entidades.RetencionesMinimas;
import Entidades.TarifaDeseo;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarDeclarantesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
 * @author Victor Algarin
 */
@ManagedBean
@SessionScoped
public class ControlDeclarantes implements Serializable {

    @EJB
    AdministrarDeclarantesInterface administrarDeclarantes;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    //Lista Declarantes
    private List<Declarantes> listaDeclarantes;
    private List<Declarantes> filtradoListaDeclarantes;
    private Declarantes declaranteSeleccionado;
    private Personas persona;
    //Columnas Tabla VC
    private Column declarantesFechaInicial, declarantesFechaFinal, declarantesBooleano, declarantesPromedio, declarantesTarifa;
    //OTROS
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    private boolean aceptar;
    private int index;
    //AUTOCOMPLETAR
    private BigInteger Minima;
    private String altoScrollDeclarantes;
    //modificar
    private List<Declarantes> listaDeclarantesModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public Declarantes nuevoDeclarante;
    private List<Declarantes> listaDeclarantesCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Declarantes> listaDeclarantesBorrar;
    //editar celda
    private Declarantes editarDeclarantes;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Declarantes duplicarDeclarante;
    //RASTROS
    private BigInteger secRegistro;
    private BigInteger secRegistroD;
    private boolean cambiosPagina;
    //L.O.V Terceros
    private List<TarifaDeseo> lovlistaRetenciones;
    private List<TarifaDeseo> lovfiltradoslistaRetenciones;
    private TarifaDeseo retencionesSeleccionado;

    /**
     * Constructor de ControlDeclarante
     */
    public ControlDeclarantes() {
        altoScrollDeclarantes = "245";
        permitirIndex = true;
        listaDeclarantes = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listaDeclarantesBorrar = new ArrayList<Declarantes>();
        //crear aficiones
        listaDeclarantesCrear = new ArrayList<Declarantes>();
        k = 0;
        //modificar aficiones
        listaDeclarantesModificar = new ArrayList<Declarantes>();
        //editar
        editarDeclarantes = new Declarantes();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoDeclarante = new Declarantes();
        duplicarDeclarante = new Declarantes();
        secRegistro = null;
        cambiosPagina = true;
    }

    public void actualizarRetenciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        List<RetencionesMinimas> listaRetenciones = administrarDeclarantes.retencionesMinimasLista();
        RetencionesMinimas seleccionado = new RetencionesMinimas();
        System.out.println("retencionesSeleccionado.getSecuenciaRetencion() : " + retencionesSeleccionado.getSecuenciaRetencion());
        System.out.println("listaRetenciones : " + listaRetenciones.size());
        for (int j = 0; j < listaRetenciones.size(); j++) {
            System.out.println("listaRetenciones : " + listaRetenciones.get(j).getSecuencia());
        }
        for (int i = 0; i < listaRetenciones.size(); i++) {
            BigInteger secuencia = new BigInteger(listaRetenciones.get(i).getSecuencia().toString());
            System.out.println("secuencia : " + secuencia);
            if (secuencia.equals(retencionesSeleccionado.getSecuenciaRetencion())) {
                seleccionado = listaRetenciones.get(i);
                break;
            }
        }
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDeclarantes.get(index).setRetencionminima(seleccionado);
                if (!listaDeclarantesCrear.contains(listaDeclarantes.get(index))) {
                    if (listaDeclarantesModificar.isEmpty()) {
                        listaDeclarantesModificar.add(listaDeclarantes.get(index));
                    } else if (!listaDeclarantesModificar.contains(listaDeclarantes.get(index))) {
                        listaDeclarantesModificar.add(listaDeclarantes.get(index));
                    }
                }
            } else {
                filtradoListaDeclarantes.get(index).setRetencionminima(seleccionado);
                if (!listaDeclarantesCrear.contains(filtradoListaDeclarantes.get(index))) {
                    if (listaDeclarantesModificar.isEmpty()) {
                        listaDeclarantesModificar.add(filtradoListaDeclarantes.get(index));
                    } else if (!listaDeclarantesModificar.contains(filtradoListaDeclarantes.get(index))) {
                        listaDeclarantesModificar.add(filtradoListaDeclarantes.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:datosDeclarantes");
        } else if (tipoActualizacion == 1) {
            nuevoDeclarante.setRetencionminima(seleccionado);
            context.update("formularioDialogos:nuevoDeclarante");
        } else if (tipoActualizacion == 2) {
            duplicarDeclarante.setRetencionminima(seleccionado);
            context.update("formularioDialogos:duplicarDeclarante");
        }
        filtradoListaDeclarantes = null;
        retencionesSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("minimasDialogo.hide()");
        context.reset("formularioDialogos:LOVMinimas:globalFilter");
        context.update("formularioDialogos:LOVMinimas");
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 3) {
                context.update("formularioDialogos:minimasDialogo");
                context.execute("minimasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void recibirPersona(Personas per) {
        persona = per;
        listaDeclarantes = null;
    }

    //AUTOCOMPLETAR
    public void modificarDeclarantes(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        System.out.println("Entro a Modificar Declarantes");

        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaDeclarantesCrear.contains(listaDeclarantes.get(index))) {

                    if (listaDeclarantesModificar.isEmpty()) {
                        listaDeclarantesModificar.add(listaDeclarantes.get(index));
                    } else if (!listaDeclarantesModificar.contains(listaDeclarantes.get(index))) {
                        listaDeclarantesModificar.add(listaDeclarantes.get(index));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                    cambiosPagina = false;
                    context.update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaDeclarantesCrear.contains(filtradoListaDeclarantes.get(index))) {

                    if (listaDeclarantesCrear.isEmpty()) {
                        listaDeclarantesCrear.add(filtradoListaDeclarantes.get(index));
                    } else if (!listaDeclarantesCrear.contains(filtradoListaDeclarantes.get(index))) {
                        listaDeclarantesCrear.add(filtradoListaDeclarantes.get(index));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                    cambiosPagina = false;
                    context.update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosDeclarantes");
        } else if (confirmarCambio.equalsIgnoreCase("MINIMA")) {
            if (tipoLista == 0) {
                listaDeclarantes.get(indice).getRetencionminima().setRetencion(Minima);
            } else {
                filtradoListaDeclarantes.get(indice).getRetencionminima().setRetencion(Minima);
            }

            for (int i = 0; i < lovlistaRetenciones.size(); i++) {
                if ((lovlistaRetenciones.get(i).getRetencion().toString()).startsWith(valorConfirmar.toString().toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                List<RetencionesMinimas> listaRetenciones = administrarDeclarantes.retencionesMinimasLista();
                TarifaDeseo ratifa = lovlistaRetenciones.get(indiceUnicoElemento);
                RetencionesMinimas seleccionado = new RetencionesMinimas();
                for (int i = 0; i < listaRetenciones.size(); i++) {
                    if (listaRetenciones.get(i).getSecuencia().equals(ratifa.getSecuenciaRetencion())) {
                        seleccionado = listaRetenciones.get(i);
                        break;
                    }
                }
                if (tipoLista == 0) {
                    listaDeclarantes.get(indice).setRetencionminima(seleccionado);
                } else {
                    filtradoListaDeclarantes.get(indice).setRetencionminima(seleccionado);
                }
                lovlistaRetenciones.clear();
                getLovlistaRetenciones();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:minimasDialogo");
                context.execute("minimasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla Declarantes
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    //UBICACION CELDA
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            declaranteSeleccionado = listaDeclarantes.get(index);
            System.out.println("Declarante Seleccionado Fecha Final: " + declaranteSeleccionado.getFechafinal());

            if (tipoLista == 0) {
                secRegistro = listaDeclarantes.get(index).getSecuencia();
                if (cualCelda == 3) {
                    Minima = listaDeclarantes.get(index).getRetencionminima().getRetencion();
                }
            } else {
                secRegistro = filtradoListaDeclarantes.get(index).getSecuencia();
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.reset("formularioDialogos:LOVMinimas:globalFilter");
            context.update("formularioDialogos:LOVMinimas");
        }
    }
    //FALTA GUARDAR

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
            if (nuevoDeclarante.getFechafinal() == null) {
                System.out.println("La fecha final es nula");
                context.update("formularioDialogos:fechaNula");
                context.execute("fechaNula.show()");
            } else {
                lovlistaRetenciones = administrarDeclarantes.retencionesMinimas(nuevoDeclarante.getFechafinal());
                context.update("formularioDialogos:minimasDialogo");
                context.execute("minimasDialogo.show()");

            }
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            if (duplicarDeclarante.getFechafinal() == null) {
                System.out.println("La fecha final es nula");
                context.update("formularioDialogos:fechaNula");
                context.execute("fechaNula.show()");
            } else {
                lovlistaRetenciones = administrarDeclarantes.retencionesMinimas(duplicarDeclarante.getFechafinal());
                context.update("formularioDialogos:minimasDialogo");
                context.execute("minimasDialogo.show()");

            }
        }
        if (dlg == 0) {
            lovlistaRetenciones = null;
            getLovlistaRetenciones();
            context.update("formularioDialogos:minimasDialogo");
            context.execute("minimasDialogo.show()");
        }
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        //CANCELAR MODIFICACIONES
        if (bandera == 1) {
            declarantesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaInicial");
            declarantesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            declarantesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaFinal");
            declarantesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            declarantesBooleano = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesBooleano");
            declarantesBooleano.setFilterStyle("display: none; visibility: hidden;");
            declarantesPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesPromedio");
            declarantesPromedio.setFilterStyle("display: none; visibility: hidden;");
            declarantesTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesTarifa");
            declarantesTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeclarantes");
            altoScrollDeclarantes = "245";
            bandera = 0;
            filtradoListaDeclarantes = null;
            tipoLista = 0;
        }

        listaDeclarantesBorrar.clear();
        listaDeclarantesCrear.clear();
        listaDeclarantesModificar.clear();
        cambiosPagina = true;
        index = -1;
        secRegistro = null;
        k = 0;
        listaDeclarantes = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDeclarantes");
        context.update("form:ACEPTAR");
    }

    public void cancelarCambioDeclarantes() {
        lovfiltradoslistaRetenciones = null;
        retencionesSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarDeclarantes = listaDeclarantes.get(index);
            }
            if (tipoLista == 1) {
                editarDeclarantes = filtradoListaDeclarantes.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicial");
                context.execute("editarFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinal");
                context.execute("editarFechaFinal.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarDeseado");
                context.execute("editarDeseado.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarTarifaDeseo");
                context.execute("editarTarifaDeseo.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //CREAR DECLARANTE
    /**
     * Metodo que se encarga de agregar un nuevo Declarante
     */
    //CREAR DECLARANTE
    public void agregarNuevoDeclarante() {
        int pasa = 0;
        mensajeValidacion = new String();

        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoDeclarante.getFechainicial() == null) {
            mensajeValidacion = mensajeValidacion + " * Fecha Inicial\n";
            pasa++;
        }
        if (nuevoDeclarante.getFechafinal() == null) {
            mensajeValidacion = mensajeValidacion + " * Fecha Final\n";
            pasa++;
        }

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevoDeclarante");
            context.execute("validacionNuevoDeclarante.show()");
        }

        if (pasa == 0) {
            if (bandera == 1) {
                declarantesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaInicial");
                declarantesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                declarantesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaFinal");
                declarantesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                declarantesBooleano = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesBooleano");
                declarantesBooleano.setFilterStyle("display: none; visibility: hidden;");
                declarantesPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesPromedio");
                declarantesPromedio.setFilterStyle("display: none; visibility: hidden;");
                declarantesTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesTarifa");
                declarantesTarifa.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDeclarantes");
                altoScrollDeclarantes = "245";
                bandera = 0;
                filtradoListaDeclarantes = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevoDeclarante.setSecuencia(l);
            nuevoDeclarante.setPersona(persona);

            cambiosPagina = false;
            context.update("form:ACEPTAR");
            listaDeclarantesCrear.add(nuevoDeclarante);
            listaDeclarantes.add(nuevoDeclarante);
            nuevoDeclarante = new Declarantes();
            context.update("form:datosDeclarantes");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroDeclarantes.hide()");
            index = -1;
            secRegistro = null;
        }
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("MINIMA")) {
            if (tipoNuevo == 1) {
                Minima = nuevoDeclarante.getRetencionminima().getRetencion();
            } else if (tipoNuevo == 2) {
                Minima = duplicarDeclarante.getRetencionminima().getRetencion();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MINIMA")) {
            if (tipoNuevo == 1) {
                nuevoDeclarante.getRetencionminima().setRetencion(Minima);
            } else if (tipoNuevo == 2) {
                duplicarDeclarante.getRetencionminima().setRetencion(Minima);
            }
            for (int i = 0; i < lovlistaRetenciones.size(); i++) {
                if ((lovlistaRetenciones.get(i).getRetencion().toString()).startsWith(valorConfirmar.toString().toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                List<RetencionesMinimas> listaRetenciones = administrarDeclarantes.retencionesMinimasLista();
                TarifaDeseo ratifa = lovlistaRetenciones.get(indiceUnicoElemento);
                RetencionesMinimas seleccionado = new RetencionesMinimas();
                for (int i = 0; i < listaRetenciones.size(); i++) {
                    if (listaRetenciones.get(i).getSecuencia().equals(ratifa.getSecuenciaRetencion())) {
                        seleccionado = listaRetenciones.get(i);
                        break;
                    }
                }
                if (tipoNuevo == 1) {
                    nuevoDeclarante.setRetencionminima(seleccionado);
                    context.update("formularioDialogos:nuevoTarifaDeseo");
                } else if (tipoNuevo == 2) {
                    duplicarDeclarante.setRetencionminima(seleccionado);
                    context.update("formularioDialogos:duplicarTarifaDeseo");
                }
                lovlistaRetenciones.clear();
                getLovlistaRetenciones();
            } else {
                context.update("form:minimasDialogo");
                context.execute("minimasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTarifaDeseo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTarifaDeseo");
                }
            }
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas del nuevo Declarante
     */
    public void limpiarNuevaDeclarantes() {
        nuevoDeclarante = new Declarantes();
        index = -1;
        secRegistro = null;
    }

    //DUPLICAR VC
    /**
     * Metodo que duplica un Declarante especifico dado por la posicion de la
     * fila
     */
    public void duplicarDeclarantes() {
        if (index >= 0) {
            duplicarDeclarante = new Declarantes();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarDeclarante.setSecuencia(l);
                duplicarDeclarante.setPersona(persona);
                duplicarDeclarante.setFechainicial(listaDeclarantes.get(index).getFechainicial());
                duplicarDeclarante.setFechafinal(listaDeclarantes.get(index).getFechafinal());
                duplicarDeclarante.setRetenciondeseada(listaDeclarantes.get(index).getRetenciondeseada());
                duplicarDeclarante.setRetencionminima(listaDeclarantes.get(index).getRetencionminima());
                duplicarDeclarante.setDeclarante(listaDeclarantes.get(index).getDeclarante());

            }
            if (tipoLista == 1) {

                duplicarDeclarante.setSecuencia(l);
                duplicarDeclarante.setPersona(persona);
                duplicarDeclarante.setFechainicial(filtradoListaDeclarantes.get(index).getFechainicial());
                duplicarDeclarante.setFechafinal(filtradoListaDeclarantes.get(index).getFechafinal());
                duplicarDeclarante.setRetenciondeseada(filtradoListaDeclarantes.get(index).getRetenciondeseada());
                duplicarDeclarante.setRetencionminima(filtradoListaDeclarantes.get(index).getRetencionminima());
                duplicarDeclarante.setDeclarante(filtradoListaDeclarantes.get(index).getDeclarante());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarDeclarante");
            context.execute("DuplicarRegistroDeclarantes.show()");
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * Declarantes
     */
    public void confirmarDuplicar() {

        listaDeclarantes.add(duplicarDeclarante);
        listaDeclarantesCrear.add(duplicarDeclarante);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDeclarantes");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            declarantesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaInicial");
            declarantesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            declarantesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaFinal");
            declarantesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            declarantesBooleano = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesBooleano");
            declarantesBooleano.setFilterStyle("display: none; visibility: hidden;");
            declarantesPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesPromedio");
            declarantesPromedio.setFilterStyle("display: none; visibility: hidden;");
            declarantesTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesTarifa");
            declarantesTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeclarantes");
            altoScrollDeclarantes = "245";
            bandera = 0;
            filtradoListaDeclarantes = null;
            tipoLista = 0;
        }

        duplicarDeclarante = new Declarantes();
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Declarante
     */
    public void limpiarDuplicarDeclarantes() {
        duplicarDeclarante = new Declarantes();
    }

    //BORRAR VC
    /**
     * Metodo que borra los Declarantes seleccionados
     */
    public void borrarDeclarantes() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaDeclarantesModificar.isEmpty() && listaDeclarantesModificar.contains(listaDeclarantes.get(index))) {
                    int modIndex = listaDeclarantesModificar.indexOf(listaDeclarantes.get(index));
                    listaDeclarantesModificar.remove(modIndex);
                    listaDeclarantesBorrar.add(listaDeclarantes.get(index));
                } else if (!listaDeclarantesCrear.isEmpty() && listaDeclarantesCrear.contains(listaDeclarantes.get(index))) {
                    int crearIndex = listaDeclarantesCrear.indexOf(listaDeclarantes.get(index));
                    listaDeclarantesCrear.remove(crearIndex);
                } else {
                    listaDeclarantesBorrar.add(listaDeclarantes.get(index));
                }
                listaDeclarantes.remove(index);
            }
            if (tipoLista == 1) {
                if (!listaDeclarantesModificar.isEmpty() && listaDeclarantesModificar.contains(filtradoListaDeclarantes.get(index))) {
                    int modIndex = listaDeclarantesModificar.indexOf(filtradoListaDeclarantes.get(index));
                    listaDeclarantesModificar.remove(modIndex);
                    listaDeclarantesBorrar.add(filtradoListaDeclarantes.get(index));
                } else if (!listaDeclarantesCrear.isEmpty() && listaDeclarantesCrear.contains(filtradoListaDeclarantes.get(index))) {
                    int crearIndex = listaDeclarantesCrear.indexOf(filtradoListaDeclarantes.get(index));
                    listaDeclarantesCrear.remove(crearIndex);
                } else {
                    listaDeclarantesBorrar.add(filtradoListaDeclarantes.get(index));
                }
                int VCIndex = listaDeclarantes.indexOf(filtradoListaDeclarantes.get(index));
                listaDeclarantes.remove(VCIndex);
                filtradoListaDeclarantes.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDeclarantes");
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
            altoScrollDeclarantes = "223";
            declarantesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaInicial");
            declarantesFechaInicial.setFilterStyle("width: 60px");
            declarantesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaFinal");
            declarantesFechaFinal.setFilterStyle("width: 60px");
            declarantesBooleano = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesBooleano");
            declarantesBooleano.setFilterStyle("width: 60px");
            declarantesPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesPromedio");
            declarantesPromedio.setFilterStyle("width: 60px");
            declarantesTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesTarifa");
            declarantesTarifa.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosDeclarantes");
            bandera = 1;

        } else if (bandera == 1) {
            altoScrollDeclarantes = "245";
            declarantesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaInicial");
            declarantesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            declarantesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaFinal");
            declarantesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            declarantesBooleano = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesBooleano");
            declarantesBooleano.setFilterStyle("display: none; visibility: hidden;");
            declarantesPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesPromedio");
            declarantesPromedio.setFilterStyle("display: none; visibility: hidden;");
            declarantesTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesTarifa");
            declarantesTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeclarantes");

            bandera = 0;
            filtradoListaDeclarantes = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            declarantesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaInicial");
            declarantesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            declarantesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesFechaFinal");
            declarantesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            declarantesBooleano = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesBooleano");
            declarantesBooleano.setFilterStyle("display: none; visibility: hidden;");
            declarantesPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesPromedio");
            declarantesPromedio.setFilterStyle("display: none; visibility: hidden;");
            declarantesTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDeclarantes:declarantesTarifa");
            declarantesTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDeclarantes");
            altoScrollDeclarantes = "245";

            bandera = 0;
            filtradoListaDeclarantes = null;
            tipoLista = 0;
        }

        listaDeclarantesBorrar.clear();
        listaDeclarantesCrear.clear();
        listaDeclarantesModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaDeclarantes = null;
        guardado = true;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosDeclarantesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "DeclarantesPDF", false, false, "UTF-8", null, null);
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosDeclarantesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "DeclarantesXLS", false, false, "UTF-8", null, null);
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
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {

        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaDeclarantes.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "DECLARANTES");
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
            if (administrarRastros.verificarHistoricosTabla("DECLARANTES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GUARDAR
    public void guardarCambiosDeclarantes() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Declarantes");

            if (!listaDeclarantesBorrar.isEmpty()) {
                for (int i = 0; i < listaDeclarantesBorrar.size(); i++) {
                    System.out.println("Borrando..." + listaDeclarantesBorrar.size());
                    if (listaDeclarantesBorrar.get(i).getRetenciondeseada() == null) {
                        listaDeclarantesBorrar.get(i).setRetenciondeseada(null);
                    }

                    if (listaDeclarantesBorrar.get(i).getRetencionminima().getSecuencia() == null) {
                        listaDeclarantesBorrar.get(i).setRetencionminima(null);
                    }

                    administrarDeclarantes.borrarDeclarantes(listaDeclarantesBorrar.get(i));
                }
                System.out.println("Entra");
                listaDeclarantesBorrar.clear();
            }

            if (!listaDeclarantesCrear.isEmpty()) {
                for (int i = 0; i < listaDeclarantesCrear.size(); i++) {
                    System.out.println("Creando...");
                    if (listaDeclarantesCrear.get(i).getRetenciondeseada() == null) {
                        listaDeclarantesCrear.get(i).setRetenciondeseada(null);
                    }

                    if (listaDeclarantesCrear.get(i).getRetencionminima().getSecuencia() == null) {
                        listaDeclarantesCrear.get(i).setRetencionminima(null);
                    }
                    administrarDeclarantes.crearDeclarantes(listaDeclarantesCrear.get(i));
                }
                System.out.println("LimpiaLista");
                listaDeclarantesCrear.clear();
            }
            if (!listaDeclarantesModificar.isEmpty()) {
                administrarDeclarantes.modificarDeclarantes(listaDeclarantesModificar);
                listaDeclarantesModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaDeclarantes = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = true;
            context.update("form:ACEPTAR");
            context.update("form:datosDeclarantes");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            //  k = 0;
        }
        index = -1;
        secRegistro = null;
    }

    //GETTERS AND SETTERS
    /**
     * Metodo que obtiene la lista de Declarantes de un Empleado, en caso de que
     * sea null hace el llamado al metodo de obtener Declarantes del empleado,
     * en caso contrario no genera operaciones
     *
     * @return listS Lista de Declarantes de una Persona
     */
    public List<Declarantes> getListaDeclarantes() {
        if (listaDeclarantes == null) {
            listaDeclarantes = administrarDeclarantes.declarantesPersona(persona.getSecuencia());
            declaranteSeleccionado = listaDeclarantes.get(0);
        }
        return listaDeclarantes;
    }

    public void setListaDeclarantes(List<Declarantes> listaDeclarantes) {
        this.listaDeclarantes = listaDeclarantes;
    }

    /**
     * Get del empleado, en caso de existir lo retorna en caso contrario lo
     * obtiene y retorna
     *
     * @return empleado Empleado que esta usado en el momento
     */
    public Personas getPersona() {
        return persona;
    }

    public List<Declarantes> getFiltradoListaDeclarantes() {
        return filtradoListaDeclarantes;
    }

    public void setFiltrarDeclarantes(List<Declarantes> filtradoListaDeclarantes) {
        this.filtradoListaDeclarantes = filtradoListaDeclarantes;
    }

    public Declarantes getNuevoDeclarante() {
        return nuevoDeclarante;
    }

    public void setNuevoDeclarante(Declarantes nuevoDeclarante) {
        this.nuevoDeclarante = nuevoDeclarante;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Declarantes getEditarDeclarantes() {
        return editarDeclarantes;
    }

    public void setEditarDeclarantes(Declarantes editarDeclarante) {
        this.editarDeclarantes = editarDeclarante;
    }

    public Declarantes getDuplicarDeclarante() {
        return duplicarDeclarante;
    }

    public void setDuplicarDeclarante(Declarantes duplicarDeclarante) {
        this.duplicarDeclarante = duplicarDeclarante;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public List<TarifaDeseo> getLovlistaRetenciones() {
        if (lovlistaRetenciones == null) {
            lovlistaRetenciones = administrarDeclarantes.retencionesMinimas(declaranteSeleccionado.getFechafinal());
        }
        return lovlistaRetenciones;
    }

    public void setLovlistaRetenciones(List<TarifaDeseo> lovlistaRetenciones) {
        this.lovlistaRetenciones = lovlistaRetenciones;
    }

    public List<TarifaDeseo> getLovfiltradoslistaRetenciones() {
        return lovfiltradoslistaRetenciones;
    }

    public void setLovfiltradoslistaRetenciones(List<TarifaDeseo> lovfiltradoslistaRetenciones) {
        this.lovfiltradoslistaRetenciones = lovfiltradoslistaRetenciones;
    }

    public TarifaDeseo getRetencionesSeleccionado() {
        return retencionesSeleccionado;
    }

    public void setRetencionesSeleccionado(TarifaDeseo retencionesSeleccionado) {
        this.retencionesSeleccionado = retencionesSeleccionado;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public String getAltoScrollDeclarantes() {
        return altoScrollDeclarantes;
    }

    public void setAltoScrollDeclarantes(String altoScrollDeclarantes) {
        this.altoScrollDeclarantes = altoScrollDeclarantes;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

}
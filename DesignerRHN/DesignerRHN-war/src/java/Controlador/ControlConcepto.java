package Controlador;

import Entidades.Conceptos;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.Unidades;
import Entidades.VigenciasTiposContratos;
import InterfaceAdministrar.AdministrarConceptosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.component.column.Column;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlConcepto implements Serializable {

    @EJB
    AdministrarConceptosInterface administrarConceptos;
    private List<Conceptos> listaConceptosEmpresa;
    private List<Conceptos> filtrardoConceptosEmpresa;
    private List<Empresas> listaEmpresas;
    private Empresas empresaActual;
    private Map<String, String> conjuntoC;
    private List<Unidades> listaUnidades;
    private Unidades unidadSelecionada;
    private List<Unidades> filtradoUnidades;
    private List<Terceros> listaTerceros;
    private Terceros terceroSelecionado;
    private List<Terceros> filtradoTerceros;
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vtcFecha, vtcContrato, vtcTipoContrato, vtcCiudad, vtcFechaSP, vtcInicioFlexibilizacion, vtcObservacion;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<Conceptos> listaConceptosEmpresaModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public Conceptos nuevoConcepto;
    private List<Conceptos> listaConceptosEmpresaCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Conceptos> listaConceptosBorrar;
    //editar celda
    private Conceptos editarConcepto;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Conceptos duplicarConcepto;
    //AUTOCOMPLETAR
    private String codigoUnidad, nombreUnidad, Tercero;
    //RASTRO
    private BigInteger secRegistro;

    public ControlConcepto() {
        listaConceptosEmpresa = null;
        conjuntoC = new LinkedHashMap<String, String>();
        listaUnidades = new ArrayList<Unidades>();
        listaTerceros = new ArrayList<Terceros>();
        empresaActual = new Empresas();
        //Otros
        aceptar = true;
        //borrar aficiones
        listaConceptosBorrar = new ArrayList<Conceptos>();
        //crear aficiones
        listaConceptosEmpresaCrear = new ArrayList<Conceptos>();
        k = 0;
        //modificar aficiones
        listaConceptosEmpresaModificar = new ArrayList<Conceptos>();
        //editar
        editarConcepto = new Conceptos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoConcepto = new Conceptos();
        nuevoConcepto.setTercero(new Terceros());        
        permitirIndex = true;
    }

    //SELECCIONAR NATURALEZA
    public void seleccionarItem(String itemSeleccionado, int indice, int celda) {
        if (tipoLista == 0) {
            if (celda == 2) {
                if (itemSeleccionado.equals("NETO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("N");
                } else if (itemSeleccionado.equals("GASTO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("G");
                } else if (itemSeleccionado.equals("DESCUENTO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("D");
                } else if (itemSeleccionado.equals("PAGO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("P");
                } else if (itemSeleccionado.equals("PASIVO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("L");
                }
            } else if (celda == 11) {
                if (itemSeleccionado.equals("ACTIVO")) {
                    listaConceptosEmpresa.get(indice).setActivo("S");
                } else if (itemSeleccionado.equals("INACTIVO")) {
                    listaConceptosEmpresa.get(indice).setActivo("N");
                }
            } else if (celda == 12) {
                if (itemSeleccionado.equals("SI")) {
                    listaConceptosEmpresa.get(indice).setEnviotesoreria("S");
                } else if (itemSeleccionado.equals("NO")) {
                    listaConceptosEmpresa.get(indice).setEnviotesoreria("N");
                }
            }
            if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {
                if (listaConceptosEmpresaModificar.isEmpty()) {
                    listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))) {
                    listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                }
            }
        } else {
            if (celda == 2) {
                if (itemSeleccionado.equals("NETO")) {
                    filtrardoConceptosEmpresa.get(indice).setNaturaleza("N");
                } else if (itemSeleccionado.equals("GASTO")) {
                    filtrardoConceptosEmpresa.get(indice).setNaturaleza("G");
                } else if (itemSeleccionado.equals("DESCUENTO")) {
                    filtrardoConceptosEmpresa.get(indice).setNaturaleza("D");
                } else if (itemSeleccionado.equals("PAGO")) {
                    filtrardoConceptosEmpresa.get(indice).setNaturaleza("P");
                } else if (itemSeleccionado.equals("PASIVO")) {
                    filtrardoConceptosEmpresa.get(indice).setNaturaleza("L");
                }
            } else if (celda == 11) {
                if (itemSeleccionado.equals("ACTIVO")) {
                    filtrardoConceptosEmpresa.get(indice).setActivo("S");
                } else if (itemSeleccionado.equals("INACTIVO")) {
                    filtrardoConceptosEmpresa.get(indice).setActivo("N");
                }
            } else if (celda == 12) {
                if (itemSeleccionado.equals("SI")) {
                    filtrardoConceptosEmpresa.get(indice).setEnviotesoreria("S");
                } else if (itemSeleccionado.equals("NO")) {
                    filtrardoConceptosEmpresa.get(indice).setEnviotesoreria("N");
                }
            }

            if (!listaConceptosEmpresaCrear.contains(filtrardoConceptosEmpresa.get(indice))) {
                if (listaConceptosEmpresaModificar.isEmpty()) {
                    listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(indice));
                } else if (!listaConceptosEmpresaModificar.contains(filtrardoConceptosEmpresa.get(indice))) {
                    listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
        }
        RequestContext.getCurrentInstance().update("form:datosConceptos");
    }

    public void llamarDialogosLista(Integer indice, int dlg) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = 0;
        if (dlg == 0) {
            context.update("form:unidadesDialogo");
            context.execute("unidadesDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:TercerosDialogo");
            context.execute("TercerosDialogo.show()");
        }
    }

    public void modificarConcepto(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {

                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaConceptosEmpresaCrear.contains(filtrardoConceptosEmpresa.get(indice))) {

                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(indice));
                    } else if (!listaConceptosEmpresaModificar.contains(filtrardoConceptosEmpresa.get(indice))) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosConceptos");
        } else if (confirmarCambio.equalsIgnoreCase("UNIDADESCODIGO")) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(indice).getUnidad().setCodigo(codigoUnidad);
            } else {
                filtrardoConceptosEmpresa.get(indice).getUnidad().setCodigo(codigoUnidad);
            }
            for (int i = 0; i < listaUnidades.size(); i++) {
                if (listaUnidades.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                } else {
                    filtrardoConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                }
                listaUnidades.clear();
                getListaUnidades();
            } else {
                permitirIndex = false;
                context.update("form:unidadesDialogo");
                context.execute("unidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("UNIDADESNOMBRE")) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(indice).getUnidad().setNombre(nombreUnidad);
            } else {
                filtrardoConceptosEmpresa.get(indice).getUnidad().setNombre(nombreUnidad);
            }
            for (int i = 0; i < listaUnidades.size(); i++) {
                if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                } else {
                    filtrardoConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                }
                listaUnidades.clear();
                getListaUnidades();
            } else {
                permitirIndex = false;
                context.update("form:unidadesDialogo");
                context.execute("unidadesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(indice).getTercero().setNombre(Tercero);
            } else {
                filtrardoConceptosEmpresa.get(indice).getTercero().setNombre(Tercero);
            }
            for (int i = 0; i < listaTerceros.size(); i++) {
                if (listaTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaConceptosEmpresa.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                } else {
                    filtrardoConceptosEmpresa.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                }
                listaTerceros.clear();
                getListaTerceros();
            } else {
                permitirIndex = false;
                context.update("form:TercerosDialogo");
                context.execute("TercerosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {

                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                    }
                }
            } else {
                if (!listaConceptosEmpresaCrear.contains(filtrardoConceptosEmpresa.get(indice))) {

                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(indice));
                    } else if (!listaConceptosEmpresaModificar.contains(filtrardoConceptosEmpresa.get(indice))) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(indice));
                    }

                }
            }
            if (guardado == true) {
                guardado = false;
            }
            index = -1;
            secRegistro = null;
        }
        context.update("form:datosConceptos");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listaConceptosEmpresa.get(index).getSecuencia();
            if (cualCelda == 3) {
                codigoUnidad = listaConceptosEmpresa.get(index).getUnidad().getCodigo();
            } else if (cualCelda == 4) {
                nombreUnidad = listaConceptosEmpresa.get(index).getUnidad().getNombre();
            } else if (cualCelda == 10) {
                Tercero = listaConceptosEmpresa.get(index).getTercero().getNombre();
            }
        }
    }

    public void actualizarUnidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(index).setUnidad(unidadSelecionada);
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    }
                }
            } else {
                filtrardoConceptosEmpresa.get(index).setUnidad(unidadSelecionada);
                if (!listaConceptosEmpresaCrear.contains(filtrardoConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(filtrardoConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosConceptos");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoConcepto.setUnidad(unidadSelecionada);
            context.update("formularioDialogos:nuevoCorteProceso");
        } else if (tipoActualizacion == 2) {
            duplicarConcepto.setUnidad(unidadSelecionada);
            context.update("formularioDialogos:duplicadoCorteProceso");
        }
        filtradoUnidades = null;
        unidadSelecionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("unidadesDialogo.hide()");
        context.reset("formularioDialogos:lovUnidades:globalFilter");
        context.update("formularioDialogos:lovUnidades");
    }

    public void cancelarUnidades() {
        filtradoUnidades = null;
        unidadSelecionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(index).setTercero(terceroSelecionado);
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    }
                }
            } else {
                filtrardoConceptosEmpresa.get(index).setTercero(terceroSelecionado);
                if (!listaConceptosEmpresaCrear.contains(filtrardoConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(filtrardoConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(filtrardoConceptosEmpresa.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosConceptos");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoConcepto.setTercero(terceroSelecionado);
            context.update("formularioDialogos:nuevoCorteProceso");
        } else if (tipoActualizacion == 2) {
            duplicarConcepto.setTercero(terceroSelecionado);
            context.update("formularioDialogos:duplicadoCorteProceso");
        }
        filtradoTerceros = null;
        terceroSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("TercerosDialogo.hide()");
        context.reset("formularioDialogos:lovTerceros:globalFilter");
        context.update("formularioDialogos:lovTerceros");
    }

    public void cancelarTercero() {
        filtradoTerceros = null;
        terceroSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
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
    //GETTER AND SETTER

    public List<Conceptos> getListaConceptosEmpresa() {
        if (listaConceptosEmpresa == null) {
            //listaConceptosEmpresa = administrarConceptos.conceptosEmpresa(BigInteger.valueOf(10595521));
            listaConceptosEmpresa = administrarConceptos.conceptosEmpresa(empresaActual.getSecuencia());
            int i = 0;
            while (i <= 44) {
                i++;
                conjuntoC.put("" + i + "", "" + i + "");
            }
        }
        return listaConceptosEmpresa;
    }

    public void setListaConceptosEmpresa(List<Conceptos> listaConceptosEmpresa) {
        this.listaConceptosEmpresa = listaConceptosEmpresa;
    }

    public List<Conceptos> getFiltrardoConceptosEmpresa() {
        return filtrardoConceptosEmpresa;
    }

    public void setFiltrardoConceptosEmpresa(List<Conceptos> filtrardoConceptosEmpresa) {
        this.filtrardoConceptosEmpresa = filtrardoConceptosEmpresa;
    }

    public List<Empresas> getListaEmpresas() {
        if (listaEmpresas == null) {
            listaEmpresas = administrarConceptos.listadoEmpresas();
            if (!listaEmpresas.isEmpty()) {
                empresaActual = listaEmpresas.get(1);
            }
        }
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public Empresas getEmpresaActual() {
        getListaEmpresas();
        return empresaActual;
    }

    public void setEmpresaActual(Empresas empresaActual) {
        this.empresaActual = empresaActual;
    }

    public Map<String, String> getConjuntoC() {
        return conjuntoC;
    }

    public void setConjuntoC(Map<String, String> conjuntoC) {
        this.conjuntoC = conjuntoC;
    }

    public List<Unidades> getListaUnidades() {
        if(listaUnidades.isEmpty()){
            listaUnidades = administrarConceptos.lovUnidades();
        }
        return listaUnidades;
    }

    public void setListaUnidades(List<Unidades> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public Unidades getUnidadSelecionada() {
        return unidadSelecionada;
    }

    public void setUnidadSelecionada(Unidades unidadSelecionada) {
        this.unidadSelecionada = unidadSelecionada;
    }

    public List<Unidades> getFiltradoUnidades() {
        return filtradoUnidades;
    }

    public void setFiltradoUnidades(List<Unidades> filtradoUnidades) {
        this.filtradoUnidades = filtradoUnidades;
    }

    public List<Terceros> getListaTerceros() {
        if (listaTerceros.isEmpty()) {
            listaTerceros = administrarConceptos.lovTerceros(empresaActual.getSecuencia());
        }
        return listaTerceros;
    }

    public void setListaTerceros(List<Terceros> listaTerceros) {
        this.listaTerceros = listaTerceros;
    }

    public List<Terceros> getFiltradoTerceros() {
        return filtradoTerceros;
    }

    public void setFiltradoTerceros(List<Terceros> filtradoTerceros) {
        this.filtradoTerceros = filtradoTerceros;
    }

    public Terceros getTerceroSelecionado() {
        return terceroSelecionado;
    }

    public void setTerceroSelecionado(Terceros terceroSelecionado) {
        this.terceroSelecionado = terceroSelecionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Conceptos getEditarConcepto() {
        return editarConcepto;
    }

    public void setEditarConcepto(Conceptos editarConcepto) {
        this.editarConcepto = editarConcepto;
    }

    public Conceptos getDuplicarConcepto() {
        return duplicarConcepto;
    }

    public void setDuplicarConcepto(Conceptos duplicarConcepto) {
        this.duplicarConcepto = duplicarConcepto;
    }
}

package Controlador;

import Entidades.Cargos;
import Entidades.Competenciascargos;
import Entidades.DetallesCargos;
import Entidades.Empresas;
import Entidades.Enfoques;
import Entidades.EvalCompetencias;
import Entidades.GruposSalariales;
import Entidades.GruposViaticos;
import Entidades.ProcesosProductivos;
import Entidades.SueldosMercados;
import Entidades.TiposDetalles;
import Entidades.TiposEmpresas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCargosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlCargo implements Serializable {

    @EJB
    AdministrarCargosInterface administrarCargos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<Cargos> listaCargos;
    private List<Cargos> filtrarListaCargos;
    //
    private List<SueldosMercados> listaSueldosMercados;
    private List<SueldosMercados> filtrarListaSueldosMercados;
    //
    private List<Competenciascargos> listaCompetenciasCargos;
    private List<Competenciascargos> filtrarListaCompetenciasCargos;
    //
    private List<TiposDetalles> listaTiposDetalles;
    private List<TiposDetalles> filtrarListaTiposDetalles;
    //Activo/Desactivo Crtl + F11
    private int bandera, banderaSueldoMercado, banderaCompetencia, banderaTipoDetalle;
    //Columnas Tabla 
    private Column cargoCodigo, cargoNombre, cargoGrupoSalarial, cargoSalario, cargoSueldoMinimo, cargoSueldoMaximo, cargoGrupoViatico, cargoCargoRotativo, cargoJefe, cargoProcesoProductivo, cargoCodigoAlternativo;
    private Column sueldoMercadoTipoEmpresa, sueldoMercadoSueldoMinimo, sueldoMercadoSueldoMaximo;
    private Column competenciaCargoDescripcion;
    private Column tipoDetalleCodigo, tipoDetalleDescripcion, tipoDetalleEnfoque;
    //Otros
    private boolean aceptar;
    private int index, indexSueldoMercado, indexAux, indexCompetenciaCargo, indexTipoDetalle, indexAuxTipoDetalle, indexDetalleCargo;
    //modificar
    private List<Cargos> listCargosModificar;
    private List<SueldosMercados> listSueldosMercadosModificar;
    private List<Competenciascargos> listCompetenciasCargosModificar;
    private List<TiposDetalles> listTiposDetallesModificar;
    private boolean guardado, guardadoSueldoMercado, guardadoCompetencia, guardadoTipoDetalle, guardadoDetalleCargo, borrarDetalleCargo;
    //crear 
    private Cargos nuevoCargo;
    private SueldosMercados nuevoSueldoMercado;
    private Competenciascargos nuevoCompetenciaCargo;
    private TiposDetalles nuevoTipoDetalle;
    private List<Cargos> listCargosCrear;
    private List<SueldosMercados> listSueldosMercadosCrear;
    private List<Competenciascargos> listCompetenciasCargosCrear;
    private List<TiposDetalles> listTiposDetallesCrear;
    private BigInteger l;
    private int k;
    //borrar 
    private List<Cargos> listCargosBorrar;
    private List<SueldosMercados> listSueldosMercadosBorrar;
    private List<Competenciascargos> listCompetenciasCargosBorrar;
    private List<TiposDetalles> listTiposDetallesBorrar;
    //editar celda
    private Cargos editarCargo;
    private SueldosMercados editarSueldoMercado;
    private Competenciascargos editarCompetenciaCargo;
    private TiposDetalles editarTipoDetalle;
    private int cualCelda, tipoLista, cualCeldaSueldoMercado, tipoListaSueldoMercado, cualCeldaCompetencia, tipoListaCompetencia, cualCeldaTipoDetalle, tipoListaTipoDetalle;
    //duplicar
    private Cargos duplicarCargo;
    private SueldosMercados duplicarSueldoMercado;
    private Competenciascargos duplicarCompetenciaCargo;
    private TiposDetalles duplicarTipoDetalle;
    private BigInteger secRegistro, secRegistroSueldoMercado, secRegistroCompetencia, secRegistroTipoDetalle;
    private BigInteger backUpSecRegistro, backUpSecRegistroSueldoMercado, backUpSecRegistroCompetencia, backUpSecRegistroTipoDetalle;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private String nombreXML, nombreTabla;
    private String grupoSalarial, grupoViatico, procesoProductivo, tipoEmpresa, evalCompetencia, enfoque;

    ///////////LOV///////////
    private List<GruposSalariales> lovGruposSalariales;
    private List<GruposSalariales> filtrarLovGruposSalariales;
    private GruposSalariales grupoSalarialSeleccionado;

    private List<GruposViaticos> lovGruposViaticos;
    private List<GruposViaticos> filtrarLovGruposViaticos;
    private GruposViaticos grupoViaticoSeleccionado;

    private List<ProcesosProductivos> lovProcesosProductivos;
    private List<ProcesosProductivos> filtrarLovProcesosProductivos;
    private ProcesosProductivos procesoProductivoSeleccionado;

    private List<TiposEmpresas> lovTiposEmpresas;
    private List<TiposEmpresas> filtrarLovTiposEmpresas;
    private TiposEmpresas tipoEmpresaSeleccionado;

    private List<EvalCompetencias> lovEvalCompetencias;
    private List<EvalCompetencias> filtrarLovEvalCompetencias;
    private EvalCompetencias evalCompetenciaSeleccionado;

    private List<Enfoques> lovEnfoques;
    private List<Enfoques> filtrarLovEnfoques;
    private Enfoques enfoqueSeleccionado;

    private boolean permitirIndexSueldoMercado, permitirCompetencia, permitirIndexTipoDetalle, permitirIndexCargo;
    private int tipoActualizacion;
    private Short auxCodigoCargo;
    private String auxNombreCargo;
    private BigInteger auxCodigoTipoDetalle;
    private String auxDescriptionTipoDetalle;
    //
    private boolean cambiosPagina;
    private String altoTablaCargo, altoTablaSueldoMercado, altoTablaCompetencia, altoTablaTipoDetalle;
    //
    private String paginaAnterior;
    //
    private boolean activoSueldoMercado, activoCompetencia, activoTipoDetalle;
    //
    private Empresas empresaActual;
    private Empresas empresaSeleccionada;
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtrarListaEmpresas;
    //
    private String legendDetalleCargo;
    //
    private DetallesCargos detalleCargo;
    //
    private boolean activoDetalleCargo;

    public ControlCargo() {
        activoDetalleCargo = true;
        indexAux = -1;
        indexAuxTipoDetalle = -1;
        detalleCargo = null;
        legendDetalleCargo = "";
        empresaSeleccionada = new Empresas();
        empresaActual = new Empresas();
        listaEmpresas = null;
        activoSueldoMercado = true;
        activoCompetencia = true;
        activoTipoDetalle = true;
        paginaAnterior = "";
        //altos tablas
        altoTablaCargo = "110";
        altoTablaSueldoMercado = "58";
        altoTablaCompetencia = "58";
        altoTablaTipoDetalle = "58";
        //Permitir index
        permitirIndexSueldoMercado = true;
        permitirIndexTipoDetalle = true;
        permitirCompetencia = true;
        permitirIndexCargo = true;
        //lovs
        lovGruposSalariales = null;
        grupoSalarialSeleccionado = new GruposSalariales();
        lovGruposViaticos = null;
        grupoViaticoSeleccionado = new GruposViaticos();
        lovProcesosProductivos = null;
        procesoProductivoSeleccionado = new ProcesosProductivos();
        lovTiposEmpresas = null;
        tipoEmpresaSeleccionado = new TiposEmpresas();
        lovEnfoques = null;
        enfoqueSeleccionado = new Enfoques();
        lovEvalCompetencias = null;
        evalCompetenciaSeleccionado = new EvalCompetencias();
        //index tablas
        indexSueldoMercado = -1;
        indexCompetenciaCargo = -1;
        indexTipoDetalle = -1;
        indexDetalleCargo = -1;
        index = 0;
        //listas de tablas
        listaCargos = null;
        listaSueldosMercados = null;
        listaCompetenciasCargos = null;
        listaTiposDetalles = null;
        //Otros
        aceptar = true;
        cambiosPagina = true;
        tipoActualizacion = -1;
        k = 0;
        //borrar 
        listCargosBorrar = new ArrayList<Cargos>();
        listSueldosMercadosBorrar = new ArrayList<SueldosMercados>();
        listCompetenciasCargosBorrar = new ArrayList<Competenciascargos>();
        listTiposDetallesBorrar = new ArrayList<TiposDetalles>();
        //crear 
        listCargosCrear = new ArrayList<Cargos>();
        listSueldosMercadosCrear = new ArrayList<SueldosMercados>();
        listCompetenciasCargosCrear = new ArrayList<Competenciascargos>();
        listTiposDetallesCrear = new ArrayList<TiposDetalles>();
        //modificar 
        listSueldosMercadosModificar = new ArrayList<SueldosMercados>();
        listCargosModificar = new ArrayList<Cargos>();
        listCompetenciasCargosModificar = new ArrayList<Competenciascargos>();
        listTiposDetallesModificar = new ArrayList<TiposDetalles>();
        //editar
        editarCargo = new Cargos();
        editarSueldoMercado = new SueldosMercados();
        editarCompetenciaCargo = new Competenciascargos();
        editarTipoDetalle = new TiposDetalles();
        //Cual Celda
        cualCelda = -1;
        cualCeldaSueldoMercado = -1;
        cualCeldaTipoDetalle = -1;
        cualCeldaCompetencia = -1;
        //Tipo Lista
        tipoListaSueldoMercado = 0;
        tipoListaTipoDetalle = 0;
        tipoLista = 0;
        tipoListaCompetencia = 0;
        //guardar
        guardado = true;
        guardadoSueldoMercado = true;
        guardadoTipoDetalle = true;
        guardadoCompetencia = true;
        guardadoDetalleCargo = true;
        borrarDetalleCargo = false;
        //Crear 
        nuevoCargo = new Cargos();
        nuevoCargo.setGruposalarial(new GruposSalariales());
        nuevoCargo.setGrupoviatico(new GruposViaticos());
        nuevoCargo.setProcesoproductivo(new ProcesosProductivos());
        nuevoSueldoMercado = new SueldosMercados();
        nuevoSueldoMercado.setTipoempresa(new TiposEmpresas());
        nuevoCompetenciaCargo = new Competenciascargos();
        nuevoCompetenciaCargo.setEvalcompetencia(new EvalCompetencias());
        nuevoTipoDetalle = new TiposDetalles();
        nuevoTipoDetalle.setEnfoque(new Enfoques());
        //Duplicar
        duplicarCargo = new Cargos();
        duplicarSueldoMercado = new SueldosMercados();
        duplicarCompetenciaCargo = new Competenciascargos();
        duplicarTipoDetalle = new TiposDetalles();
        //Sec Registro
        secRegistro = null;
        backUpSecRegistro = null;
        secRegistroSueldoMercado = null;
        secRegistroTipoDetalle = null;
        backUpSecRegistroSueldoMercado = null;
        backUpSecRegistroTipoDetalle = null;
        secRegistroSueldoMercado = null;
        backUpSecRegistroCompetencia = null;
        //Banderas
        bandera = 0;
        banderaSueldoMercado = 0;
        banderaTipoDetalle = 0;
        banderaCompetencia = 0;
    }

    public String valorPaginaAnterior() {
        return paginaAnterior;
    }

    public void inicializarPagina(String paginaLlamado) {
        paginaAnterior = paginaLlamado;
        listaEmpresas = administrarCargos.listaEmpresas();
        int tam = listaEmpresas.size();
        if (tam > 0) {
            empresaActual = listaEmpresas.get(0);
            empresaSeleccionada = empresaActual;
            index = 0;
            listaCargos = null;
            getListaCargos();
            int tam2 = listaCargos.size();
            if (tam2 > 0) {
                listaSueldosMercados = null;
                getListaSueldosMercados();
                listaCompetenciasCargos = null;
                getListaCompetenciasCargos();
            }
            listaTiposDetalles = null;
            getListaTiposDetalles();
        }
    }

    public boolean validarCamposNulosCargos(int i) {
        boolean retorno = true;
        if (i == 0) {
            Cargos aux = new Cargos();
            if (tipoLista == 0) {
                aux = listaCargos.get(index);
            }
            if (tipoLista == 1) {
                aux = filtrarListaCargos.get(index);
            }
            if (aux.getCodigo() == null || aux.getNombre().isEmpty()) {
                retorno = false;
            }
            if (aux.getCodigo() != null) {
                if (aux.getCodigo() <= 0) {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevoCargo.getCodigo() == null || nuevoCargo.getNombre().isEmpty()) {
                retorno = false;
            }
            if (nuevoCargo.getCodigo() != null) {
                if (nuevoCargo.getCodigo() <= 0) {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarCargo.getCodigo() == null || duplicarCargo.getNombre().isEmpty()) {
                retorno = false;
            }
            if (duplicarCargo.getCodigo() != null) {
                if (duplicarCargo.getCodigo() <= 0) {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public boolean validarCamposNulosSueldosMercados(int i) {
        boolean retorno = true;
        if (i == 1) {
            if (nuevoSueldoMercado.getTipoempresa().getSecuencia() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarSueldoMercado.getTipoempresa().getSecuencia() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarCamposNulosTiposDetalles(int i) {
        boolean retorno = true;
        if (i == 0) {
            TiposDetalles aux = new TiposDetalles();
            if (tipoListaTipoDetalle == 0) {
                aux = listaTiposDetalles.get(indexTipoDetalle);
            }
            if (tipoListaTipoDetalle == 1) {
                aux = filtrarListaTiposDetalles.get(indexTipoDetalle);
            }
            if (aux.getCodigo() == null || aux.getDescripcion().isEmpty() || aux.getEnfoque().getSecuencia() == null) {
                retorno = false;
            }
            if (aux.getCodigo() != null) {
                if (aux.getCodigo().intValue() <= 0) {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevoTipoDetalle.getCodigo() == null || nuevoTipoDetalle.getDescripcion().isEmpty() || nuevoTipoDetalle.getEnfoque().getSecuencia() == null) {
                retorno = false;
            }
            if (nuevoTipoDetalle.getCodigo() != null) {
                if (nuevoTipoDetalle.getCodigo().intValue() <= 0) {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarTipoDetalle.getCodigo() == null || duplicarTipoDetalle.getDescripcion().isEmpty() || duplicarTipoDetalle.getEnfoque().getSecuencia() == null) {
                retorno = false;
            }
            if (duplicarTipoDetalle.getCodigo() != null) {
                if (duplicarTipoDetalle.getCodigo().intValue() <= 0) {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void procesoModificacionCargo(int i) {
        index = i;
        boolean respuesta = validarCamposNulosCargos(0);
        if (respuesta == true) {
            modificarCargo(i);
        } else {
            if (tipoLista == 0) {
                listaCargos.get(index).setCodigo(auxCodigoCargo);
                listaCargos.get(index).setNombre(auxNombreCargo);
            }
            if (tipoLista == 1) {
                filtrarListaCargos.get(index).setCodigo(auxCodigoCargo);
                filtrarListaCargos.get(index).setNombre(auxNombreCargo);
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCargo");
            context.execute("errorDatosNullCargo.show()");
        }
    }

    public void modificarCargo(int indice) {
        int tamDes = 0;
        if (tipoLista == 0) {
            tamDes = listaCargos.get(indice).getNombre().length();
        }
        if (tipoLista == 1) {
            tamDes = filtrarListaCargos.get(indice).getNombre().length();
        }
        if (tamDes >= 1 && tamDes <= 50) {
            if (tipoLista == 0) {
                String textM = listaCargos.get(indice).getNombre().toUpperCase();
                listaCargos.get(indice).setNombre(textM);
                if (listaCargos.get(indice).getCodigoalternativo() != null) {
                    String var = listaCargos.get(indice).getCodigoalternativo().toUpperCase();
                    listaCargos.get(indice).setCodigoalternativo(var);
                }
                if (!listCargosCrear.contains(listaCargos.get(indice))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(listaCargos.get(indice));
                    } else if (!listCargosModificar.contains(listaCargos.get(indice))) {
                        listCargosModificar.add(listaCargos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
            }
            if (tipoLista == 1) {
                String textM = filtrarListaCargos.get(indice).getNombre().toUpperCase();
                filtrarListaCargos.get(indice).setNombre(textM);
                if (filtrarListaCargos.get(indice).getCodigoalternativo() != null) {
                    String var = filtrarListaCargos.get(indice).getCodigoalternativo().toUpperCase();
                    filtrarListaCargos.get(indice).setCodigoalternativo(var);
                }
                if (!listCargosCrear.contains(filtrarListaCargos.get(indice))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(filtrarListaCargos.get(indice));
                    } else if (!listCargosModificar.contains(filtrarListaCargos.get(indice))) {
                        listCargosModificar.add(filtrarListaCargos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
            index = -1;
            secRegistro = null;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCargo");
        } else {
            if (tipoLista == 0) {
                listaCargos.get(index).setNombre(auxNombreCargo);
            }
            if (tipoLista == 1) {
                filtrarListaCargos.get(index).setNombre(auxNombreCargo);
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCargo");
            context.execute("errorDescripcionCargo.show()");
        }
    }

    public void modificarCargo(int indice, String confirmarCambio, String valorConfirmar) {
        indexSueldoMercado = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPOSALARIAL")) {
            if (tipoLista == 0) {
                listaCargos.get(indice).getGruposalarial().setDescripcion(grupoSalarial);
            } else {
                filtrarListaCargos.get(indice).getGruposalarial().setDescripcion(grupoSalarial);
            }
            for (int i = 0; i < lovGruposSalariales.size(); i++) {
                if (lovGruposSalariales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaCargos.get(indice).setGruposalarial(lovGruposSalariales.get(indiceUnicoElemento));
                } else {
                    filtrarListaCargos.get(indice).setGruposalarial(lovGruposSalariales.get(indiceUnicoElemento));
                }
                lovGruposSalariales = null;
                getLovGruposSalariales();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexCargo = false;
                context.update("form:GrupoSalarialDialogo");
                context.execute("GrupoSalarialDialogo.show()");
                tipoActualizacion = 0;
            }
        }

        if (confirmarCambio.equalsIgnoreCase("GRUPOVIATICO")) {
            if (tipoLista == 0) {
                listaCargos.get(indice).getGrupoviatico().setDescripcion(grupoViatico);
            } else {
                filtrarListaCargos.get(indice).getGrupoviatico().setDescripcion(grupoViatico);
            }
            for (int i = 0; i < lovGruposViaticos.size(); i++) {
                if (lovGruposViaticos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaCargos.get(indice).setGrupoviatico(lovGruposViaticos.get(indiceUnicoElemento));
                } else {
                    filtrarListaCargos.get(indice).setGrupoviatico(lovGruposViaticos.get(indiceUnicoElemento));
                }
                lovGruposViaticos = null;
                getLovGruposViaticos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexCargo = false;
                context.update("form:GrupoViaticoDialogo");
                context.execute("GrupoViaticoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("PROCESOPRODUCTIVO")) {
            if (tipoLista == 0) {
                listaCargos.get(indice).getProcesoproductivo().setDescripcion(procesoProductivo);
            } else {
                filtrarListaCargos.get(indice).getProcesoproductivo().setDescripcion(procesoProductivo);
            }
            for (int i = 0; i < lovProcesosProductivos.size(); i++) {
                if (lovProcesosProductivos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaCargos.get(indice).setProcesoproductivo(lovProcesosProductivos.get(indiceUnicoElemento));
                } else {
                    filtrarListaCargos.get(indice).setProcesoproductivo(lovProcesosProductivos.get(indiceUnicoElemento));
                }
                lovProcesosProductivos = null;
                getLovProcesosProductivos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexCargo = false;
                context.update("form:ProcesoProductivoDialogo");
                context.execute("ProcesoProductivoDialogo.show()");
                tipoActualizacion = 0;
            }
        }

        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listCargosCrear.contains(listaCargos.get(indice))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(listaCargos.get(indice));
                    } else if (!listCargosModificar.contains(listaCargos.get(indice))) {
                        listCargosModificar.add(listaCargos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
            }
            if (tipoLista == 1) {
                if (!listCargosCrear.contains(filtrarListaCargos.get(indice))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(filtrarListaCargos.get(indice));
                    } else if (!listCargosModificar.contains(filtrarListaCargos.get(indice))) {
                        listCargosModificar.add(filtrarListaCargos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosCargo");
    }

    public void modificarSueldoMercado(int indice) {
        if (tipoListaSueldoMercado == 0) {
            if (!listSueldosMercadosCrear.contains(listaSueldosMercados.get(indice))) {
                if (listSueldosMercadosModificar.isEmpty()) {
                    listSueldosMercadosModificar.add(listaSueldosMercados.get(indice));
                } else if (!listSueldosMercadosModificar.contains(listaSueldosMercados.get(indice))) {
                    listSueldosMercadosModificar.add(listaSueldosMercados.get(indice));
                }
                if (guardadoSueldoMercado == true) {
                    guardadoSueldoMercado = false;
                }
            }
        }
        if (tipoListaSueldoMercado == 1) {
            if (!listSueldosMercadosCrear.contains(filtrarListaSueldosMercados.get(indice))) {
                if (listSueldosMercadosModificar.isEmpty()) {
                    listSueldosMercadosModificar.add(filtrarListaSueldosMercados.get(indice));
                } else if (!listSueldosMercadosModificar.contains(filtrarListaSueldosMercados.get(indice))) {
                    listSueldosMercadosModificar.add(filtrarListaSueldosMercados.get(indice));
                }
                if (guardadoSueldoMercado == true) {
                    guardadoSueldoMercado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosSueldoMercado");
    }

    public void modificarSueldoMercado(int indice, String confirmarCambio, String valorConfirmar) {
        indexSueldoMercado = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOEMPRESA")) {
            if (tipoListaSueldoMercado == 0) {
                listaSueldosMercados.get(indice).getTipoempresa().setDescripcion(tipoEmpresa);
            } else {
                filtrarListaSueldosMercados.get(indice).getTipoempresa().setDescripcion(tipoEmpresa);
            }
            for (int i = 0; i < lovTiposEmpresas.size(); i++) {
                if (lovTiposEmpresas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaSueldoMercado == 0) {
                    listaSueldosMercados.get(indice).setTipoempresa(lovTiposEmpresas.get(indiceUnicoElemento));
                } else {
                    filtrarListaSueldosMercados.get(indice).setTipoempresa(lovTiposEmpresas.get(indiceUnicoElemento));
                }
                lovTiposEmpresas = null;
                getLovTiposEmpresas();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexSueldoMercado = false;
                context.update("form:TipoEmpresaDialogo");
                context.execute("TipoEmpresaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaSueldoMercado == 0) {
                if (!listSueldosMercadosCrear.contains(listaSueldosMercados.get(indice))) {
                    if (listSueldosMercadosModificar.isEmpty()) {
                        listSueldosMercadosModificar.add(listaSueldosMercados.get(indice));
                    } else if (!listSueldosMercadosModificar.contains(listaSueldosMercados.get(indice))) {
                        listSueldosMercadosModificar.add(listaSueldosMercados.get(indice));
                    }
                    if (guardadoSueldoMercado == true) {
                        guardadoSueldoMercado = false;
                    }
                }
            }
            if (tipoListaSueldoMercado == 1) {
                if (!listSueldosMercadosCrear.contains(filtrarListaSueldosMercados.get(indice))) {
                    if (listSueldosMercadosModificar.isEmpty()) {
                        listSueldosMercadosModificar.add(filtrarListaSueldosMercados.get(indice));
                    } else if (!listSueldosMercadosModificar.contains(filtrarListaSueldosMercados.get(indice))) {
                        listSueldosMercadosModificar.add(filtrarListaSueldosMercados.get(indice));
                    }
                    if (guardadoSueldoMercado == true) {
                        guardadoSueldoMercado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosSueldoMercado");
    }

    public void modificarCompetenciaCargo(int indice) {
        if (tipoListaCompetencia == 0) {
            if (!listCompetenciasCargosCrear.contains(listaCompetenciasCargos.get(indice))) {
                if (listCompetenciasCargosModificar.isEmpty()) {
                    listCompetenciasCargosModificar.add(listaCompetenciasCargos.get(indice));
                } else if (!listCompetenciasCargosModificar.contains(listaCompetenciasCargos.get(indice))) {
                    listCompetenciasCargosModificar.add(listaCompetenciasCargos.get(indice));
                }
                if (guardadoCompetencia == true) {
                    guardadoCompetencia = false;
                }
            }
        }
        if (tipoListaCompetencia == 1) {
            if (!listCompetenciasCargosCrear.contains(filtrarListaCompetenciasCargos.get(indice))) {
                if (listCompetenciasCargosModificar.isEmpty()) {
                    listCompetenciasCargosModificar.add(filtrarListaCompetenciasCargos.get(indice));
                } else if (!listCompetenciasCargosModificar.contains(filtrarListaCompetenciasCargos.get(indice))) {
                    listCompetenciasCargosModificar.add(filtrarListaCompetenciasCargos.get(indice));
                }
                if (guardadoCompetencia == true) {
                    guardadoCompetencia = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosCompetenciaCargo");
    }

    public void modificarCompetenciaCargo(int indice, String confirmarCambio, String valorConfirmar) {
        indexCompetenciaCargo = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EVALCOMPETENCIA")) {
            if (tipoListaCompetencia == 0) {
                listaCompetenciasCargos.get(indice).getEvalcompetencia().setDescripcion(evalCompetencia);
            } else {
                filtrarListaCompetenciasCargos.get(indice).getEvalcompetencia().setDescripcion(evalCompetencia);
            }
            for (int i = 0; i < lovEvalCompetencias.size(); i++) {
                if (lovEvalCompetencias.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaCompetencia == 0) {
                    listaCompetenciasCargos.get(indice).setEvalcompetencia(lovEvalCompetencias.get(indiceUnicoElemento));
                } else {
                    filtrarListaCompetenciasCargos.get(indice).setEvalcompetencia(lovEvalCompetencias.get(indiceUnicoElemento));
                }
                lovEvalCompetencias = null;
                getLovEvalCompetencias();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirCompetencia = false;
                context.update("form:EvalCompetenciaDialogo");
                context.execute("EvalCompetenciaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaCompetencia == 0) {
                if (!listCompetenciasCargosCrear.contains(listaCompetenciasCargos.get(indice))) {
                    if (listCompetenciasCargosModificar.isEmpty()) {
                        listCompetenciasCargosModificar.add(listaCompetenciasCargos.get(indice));
                    } else if (!listCompetenciasCargosModificar.contains(listaCompetenciasCargos.get(indice))) {
                        listCompetenciasCargosModificar.add(listaCompetenciasCargos.get(indice));
                    }
                    if (guardadoCompetencia == true) {
                        guardadoCompetencia = false;
                    }
                }
            }
            if (tipoListaCompetencia == 1) {
                if (!listCompetenciasCargosCrear.contains(filtrarListaCompetenciasCargos.get(indice))) {
                    if (listCompetenciasCargosModificar.isEmpty()) {
                        listCompetenciasCargosModificar.add(filtrarListaCompetenciasCargos.get(indice));
                    } else if (!listCompetenciasCargosModificar.contains(filtrarListaCompetenciasCargos.get(indice))) {
                        listCompetenciasCargosModificar.add(filtrarListaCompetenciasCargos.get(indice));
                    }
                    if (guardadoCompetencia == true) {
                        guardadoCompetencia = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosCompetenciaCargo");
    }

    public void procesoModificacionTipoDetalle(int i) {
        indexTipoDetalle = i;
        boolean respuesta = validarCamposNulosTiposDetalles(0);
        if (respuesta == true) {
            modificarTipoDetalle(i);
        } else {
            if (tipoListaTipoDetalle == 0) {
                listaTiposDetalles.get(indexTipoDetalle).setCodigo(auxCodigoTipoDetalle);
                listaTiposDetalles.get(indexTipoDetalle).setDescripcion(auxDescriptionTipoDetalle);
            }
            if (tipoListaTipoDetalle == 1) {
                filtrarListaTiposDetalles.get(indexTipoDetalle).setCodigo(auxCodigoTipoDetalle);
                filtrarListaTiposDetalles.get(indexTipoDetalle).setDescripcion(auxDescriptionTipoDetalle);
            }
            indexTipoDetalle = -1;
            secRegistroTipoDetalle = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoDetalle");
            context.execute("errorDatosNullTipoDetalle.show()");
        }
    }

    public void modificarTipoDetalle(int indice) {
        String descrip = "";
        if (tipoListaTipoDetalle == 0) {
            descrip = listaTiposDetalles.get(indice).getDescripcion();
        }
        if (tipoListaTipoDetalle == 1) {
            descrip = filtrarListaTiposDetalles.get(indice).getDescripcion();
        }
        if (descrip.length() >= 1 && descrip.length() <= 40) {
            if (tipoListaTipoDetalle == 0) {
                String aux = listaTiposDetalles.get(indice).getDescripcion().toUpperCase();
                listaTiposDetalles.get(indice).setDescripcion(aux);
                if (!listTiposDetallesCrear.contains(listaTiposDetalles.get(indice))) {
                    if (listTiposDetallesModificar.isEmpty()) {
                        listTiposDetallesModificar.add(listaTiposDetalles.get(indice));
                    } else if (!listTiposDetallesModificar.contains(listaTiposDetalles.get(indice))) {
                        listTiposDetallesModificar.add(listaTiposDetalles.get(indice));
                    }
                    if (guardadoTipoDetalle == true) {
                        guardadoTipoDetalle = false;
                    }
                }
            }
            if (tipoListaTipoDetalle == 1) {
                String aux = filtrarListaTiposDetalles.get(indice).getDescripcion().toUpperCase();
                filtrarListaTiposDetalles.get(indice).setDescripcion(aux);
                if (!listTiposDetallesCrear.contains(filtrarListaTiposDetalles.get(indice))) {
                    if (listTiposDetallesModificar.isEmpty()) {
                        listTiposDetallesModificar.add(filtrarListaTiposDetalles.get(indice));
                    } else if (!listTiposDetallesModificar.contains(filtrarListaTiposDetalles.get(indice))) {
                        listTiposDetallesModificar.add(filtrarListaTiposDetalles.get(indice));
                    }
                    if (guardadoTipoDetalle == true) {
                        guardadoTipoDetalle = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
            indexTipoDetalle = -1;
            secRegistroTipoDetalle = null;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosTipoDetalle");
        } else {
            if (tipoListaTipoDetalle == 0) {
                listaTiposDetalles.get(indice).setDescripcion(auxDescriptionTipoDetalle);
            }
            if (tipoListaTipoDetalle == 1) {
                filtrarListaTiposDetalles.get(indice).setDescripcion(auxDescriptionTipoDetalle);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoDetalle");
            context.execute("errorDescripcionTipoDetalle.show()");

        }
    }

    public void modificarTipoDetalle(int indice, String confirmarCambio, String valorConfirmar) {
        indexTipoDetalle = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ENFOQUE")) {
            if (tipoListaTipoDetalle == 0) {
                listaTiposDetalles.get(indice).getEnfoque().setDescripcion(grupoSalarial);
            } else {
                filtrarListaTiposDetalles.get(indice).getEnfoque().setDescripcion(grupoSalarial);
            }
            for (int i = 0; i < lovEnfoques.size(); i++) {
                if (lovEnfoques.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaTipoDetalle == 0) {
                    listaTiposDetalles.get(indice).setEnfoque(lovEnfoques.get(indiceUnicoElemento));
                } else {
                    filtrarListaTiposDetalles.get(indice).setEnfoque(lovEnfoques.get(indiceUnicoElemento));
                }
                lovEnfoques = null;
                getLovEnfoques();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexTipoDetalle = false;
                context.update("form:EnfoqueDialogo");
                context.execute("EnfoqueDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaTipoDetalle == 0) {
                if (!listTiposDetallesCrear.contains(listaTiposDetalles.get(indice))) {
                    if (listTiposDetallesModificar.isEmpty()) {
                        listTiposDetallesModificar.add(listaTiposDetalles.get(indice));
                    } else if (!listTiposDetallesModificar.contains(listaTiposDetalles.get(indice))) {
                        listTiposDetallesModificar.add(listaTiposDetalles.get(indice));
                    }
                    if (guardadoTipoDetalle == true) {
                        guardadoTipoDetalle = false;
                    }
                }
            }
            if (tipoListaTipoDetalle == 1) {
                if (!listTiposDetallesCrear.contains(filtrarListaTiposDetalles.get(indice))) {
                    if (listTiposDetallesModificar.isEmpty()) {
                        listTiposDetallesModificar.add(filtrarListaTiposDetalles.get(indice));
                    } else if (!listTiposDetallesModificar.contains(filtrarListaTiposDetalles.get(indice))) {
                        listTiposDetallesModificar.add(filtrarListaTiposDetalles.get(indice));
                    }
                    if (guardadoTipoDetalle == true) {
                        guardadoTipoDetalle = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosTipoDetalle");
    }

    public void posicionCargo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(indice, columna);
    }

    public void cambiarIndice(int indice, int celda) {
        if (guardadoSueldoMercado == true && guardadoCompetencia == true && guardadoTipoDetalle == true && guardadoDetalleCargo == true) {
            if (permitirIndexCargo == true) {
                RequestContext context = RequestContext.getCurrentInstance();
                cualCelda = celda;
                indexAux = indice;
                index = indice;
                indexSueldoMercado = -1;
                indexCompetenciaCargo = -1;
                indexTipoDetalle = -1;
                indexDetalleCargo = -1;
                if (tipoLista == 0) {
                    auxCodigoCargo = listaCargos.get(index).getCodigo();
                    secRegistro = listaCargos.get(index).getSecuencia();
                    auxNombreCargo = listaCargos.get(index).getNombre();
                    grupoSalarial = listaCargos.get(index).getGruposalarial().getDescripcion();
                    grupoViatico = listaCargos.get(index).getGrupoviatico().getDescripcion();
                    procesoProductivo = listaCargos.get(index).getProcesoproductivo().getDescripcion();
                }
                if (tipoLista == 1) {
                    auxCodigoCargo = filtrarListaCargos.get(index).getCodigo();
                    secRegistro = filtrarListaCargos.get(index).getSecuencia();
                    auxNombreCargo = filtrarListaCargos.get(index).getNombre();
                    grupoSalarial = filtrarListaCargos.get(index).getGruposalarial().getDescripcion();
                    grupoViatico = filtrarListaCargos.get(index).getGrupoviatico().getDescripcion();
                    procesoProductivo = filtrarListaCargos.get(index).getProcesoproductivo().getDescripcion();
                }

                listaSueldosMercados = null;
                getListaSueldosMercados();
                context.update("form:datosSueldoMercado");
                listaCompetenciasCargos = null;
                getListaCompetenciasCargos();
                context.update("form:datosCompetenciaCargo");
                if (banderaSueldoMercado == 1) {
                    altoTablaSueldoMercado = "58";
                    sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
                    sueldoMercadoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                    sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
                    sueldoMercadoTipoEmpresa.setFilterStyle("display: none; visibility: hidden;");
                    sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
                    sueldoMercadoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
                    banderaSueldoMercado = 0;
                    filtrarListaSueldosMercados = null;
                    tipoListaSueldoMercado = 0;
                }
                if (banderaCompetencia == 1) {
                    altoTablaCompetencia = "58";
                    competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
                    competenciaCargoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
                    banderaCompetencia = 0;
                    filtrarListaCompetenciasCargos = null;
                    tipoListaCompetencia = 0;
                }
                activoDetalleCargo = true;
                legendDetalleCargo = "";
                detalleCargo = new DetallesCargos();
                context.update("form:legendDetalleCargo");
                context.update("form:detalleCargo");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceSueldoMercado(int indice, int celda) {
        if (guardadoDetalleCargo == true) {
            if (permitirIndexSueldoMercado == true) {
                indexSueldoMercado = indice;
                index = indice;
                index = -1;
                indexCompetenciaCargo = -1;
                indexTipoDetalle = -1;
                indexDetalleCargo = -1;
                cualCeldaSueldoMercado = celda;
                if (tipoListaSueldoMercado == 0) {
                    secRegistroSueldoMercado = listaSueldosMercados.get(indexSueldoMercado).getSecuencia();
                    tipoEmpresa = listaSueldosMercados.get(indexSueldoMercado).getTipoempresa().getDescripcion();
                }
                if (tipoListaSueldoMercado == 1) {
                    secRegistroSueldoMercado = filtrarListaSueldosMercados.get(indexSueldoMercado).getSecuencia();
                    tipoEmpresa = filtrarListaSueldosMercados.get(indexSueldoMercado).getTipoempresa().getDescripcion();
                }
                activoDetalleCargo = true;
                legendDetalleCargo = "";
                detalleCargo = new DetallesCargos();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:legendDetalleCargo");
                context.update("form:detalleCargo");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceCompetenciaCargo(int indice, int celda) {
        if (guardadoDetalleCargo == true) {
            if (permitirCompetencia == true) {
                indexCompetenciaCargo = indice;
                index = -1;
                indexTipoDetalle = -1;
                indexSueldoMercado = -1;
                cualCeldaCompetencia = celda;
                indexDetalleCargo = -1;
                if (tipoListaCompetencia == 0) {
                    secRegistroCompetencia = listaCompetenciasCargos.get(indexCompetenciaCargo).getSecuencia();
                    evalCompetencia = listaCompetenciasCargos.get(indexCompetenciaCargo).getEvalcompetencia().getDescripcion();
                }
                if (tipoListaCompetencia == 1) {
                    secRegistroCompetencia = filtrarListaCompetenciasCargos.get(indexCompetenciaCargo).getSecuencia();
                    evalCompetencia = filtrarListaCompetenciasCargos.get(indexCompetenciaCargo).getEvalcompetencia().getDescripcion();
                }
                activoDetalleCargo = true;
                legendDetalleCargo = "";
                detalleCargo = new DetallesCargos();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:legendDetalleCargo");
                context.update("form:detalleCargo");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceTipoDetalle(int indice, int celda) {
        if (guardadoDetalleCargo == true) {
            if (permitirIndexTipoDetalle == true) {
                indexTipoDetalle = indice;
                indexAuxTipoDetalle = indice;
                index = -1;
                indexSueldoMercado = -1;
                indexCompetenciaCargo = -1;
                cualCeldaTipoDetalle = celda;
                indexDetalleCargo = -1;
                if (tipoListaTipoDetalle == 0) {
                    secRegistroTipoDetalle = listaTiposDetalles.get(indexTipoDetalle).getSecuencia();
                    enfoque = listaTiposDetalles.get(indexTipoDetalle).getEnfoque().getDescripcion();
                    auxCodigoTipoDetalle = listaTiposDetalles.get(indexTipoDetalle).getCodigo();
                    auxDescriptionTipoDetalle = listaTiposDetalles.get(indexTipoDetalle).getDescripcion();
                    legendDetalleCargo = "[" + listaTiposDetalles.get(indexTipoDetalle).getDescripcion() + "]";
                }
                if (tipoListaTipoDetalle == 1) {
                    secRegistroTipoDetalle = filtrarListaTiposDetalles.get(indexTipoDetalle).getSecuencia();
                    enfoque = filtrarListaTiposDetalles.get(indexTipoDetalle).getEnfoque().getDescripcion();
                    auxCodigoTipoDetalle = filtrarListaTiposDetalles.get(indexTipoDetalle).getCodigo();
                    auxDescriptionTipoDetalle = filtrarListaTiposDetalles.get(indexTipoDetalle).getDescripcion();
                    legendDetalleCargo = "[" + filtrarListaTiposDetalles.get(indexTipoDetalle).getDescripcion() + "]";
                }
                detalleCargo = null;
                getDetalleCargo();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:legendDetalleCargo");
                context.update("form:detalleCargo");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");

        }
    }
    //GUARDAR

    public void guardarYSalir() {
        guardarGeneral();
        salir();
    }

    public void cancelarYSalir() {
        cancelarModificacionGeneral();
        salir();
    }

    public void guardarGeneral() {
        if (guardado == false || guardadoSueldoMercado == false || guardadoCompetencia == false || guardadoTipoDetalle == false || guardadoDetalleCargo == false) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == false) {
                guardarCambiosCargos();
            }
            if (guardadoSueldoMercado == false) {
                guardarCambiosSueldosMercados();
            }
            if (guardadoCompetencia == false) {
                guardarCambiosCompetenciasCargos();
            }
            if (guardadoTipoDetalle == false) {
                guardarCambiosTiposDetalles();
            }
            if (guardadoDetalleCargo == false) {
                System.out.println("Entro a guardar el dato : " + detalleCargo.getDescripcion());
                if (borrarDetalleCargo == false) {
                    System.out.println("Editar");
                    administrarCargos.editarDetalleCargo(detalleCargo);
                } else {
                    System.out.println("Borrar");
                    administrarCargos.borrarDetalleCargo(detalleCargo);
                }
                guardadoDetalleCargo = true;
                activoDetalleCargo = true;
                detalleCargo = new DetallesCargos();
                legendDetalleCargo = "";
                context.update("form:legendDetalleCargo");
                context.update("form:detalleCargo");
            }
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            cambiosPagina = true;
            context.update("form:ACEPTAR");
        }
    }

    public void guardarCambiosCargos() {

        if (!listCargosBorrar.isEmpty()) {
            for (int i = 0; i < listCargosBorrar.size(); i++) {
                administrarCargos.borrarCargos(listCargosBorrar);
            }
            listCargosBorrar.clear();
        }
        if (!listCargosCrear.isEmpty()) {
            for (int i = 0; i < listCargosCrear.size(); i++) {
                administrarCargos.crearCargos(listCargosCrear);
            }
            listCargosCrear.clear();
        }
        if (!listCargosModificar.isEmpty()) {
            administrarCargos.editarCargos(listCargosModificar);
            listCargosModificar.clear();
        }
        listaCargos = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCargo");
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        index = -1;
        secRegistro = null;

    }

    public void guardarCambiosSueldosMercados() {
        if (!listSueldosMercadosBorrar.isEmpty()) {
            administrarCargos.borrarSueldosMercados(listSueldosMercadosBorrar);
            listSueldosMercadosBorrar.clear();
        }
        if (!listSueldosMercadosCrear.isEmpty()) {
            administrarCargos.crearSueldosMercados(listSueldosMercadosCrear);
            listSueldosMercadosCrear.clear();
        }
        if (!listSueldosMercadosModificar.isEmpty()) {
            administrarCargos.editarSueldosMercados(listSueldosMercadosModificar);
            listSueldosMercadosModificar.clear();
        }
        listaSueldosMercados = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSueldoMercado");
        guardadoSueldoMercado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
    }

    public void guardarCambiosCompetenciasCargos() {
        if (!listCompetenciasCargosBorrar.isEmpty()) {
            administrarCargos.borrarCompetenciasCargos(listCompetenciasCargosBorrar);
            listCompetenciasCargosBorrar.clear();
        }
        if (!listCompetenciasCargosCrear.isEmpty()) {
            administrarCargos.crearCompetenciasCargos(listCompetenciasCargosCrear);
            listCompetenciasCargosCrear.clear();
        }
        if (!listCompetenciasCargosModificar.isEmpty()) {
            administrarCargos.editarCompetenciasCargos(listCompetenciasCargosModificar);
            listCompetenciasCargosModificar.clear();
        }
        listaCompetenciasCargos = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCompetenciaCargo");
        guardadoCompetencia = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
    }

    public void guardarCambiosTiposDetalles() {
        if (!listTiposDetallesBorrar.isEmpty()) {
            administrarCargos.borrarTiposDetalles(listTiposDetallesBorrar);
            listTiposDetallesBorrar.clear();
        }
        if (!listTiposDetallesCrear.isEmpty()) {
            administrarCargos.crearTiposDetalles(listTiposDetallesCrear);
            listTiposDetallesCrear.clear();
        }
        if (!listTiposDetallesModificar.isEmpty()) {
            administrarCargos.editarTiposDetalles(listTiposDetallesModificar);
            listTiposDetallesModificar.clear();
        }
        listaTiposDetalles = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoDetalle");
        guardadoTipoDetalle = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacionGeneral() {
        cambiosPagina = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        if (guardado == false) {
            cancelarModificacionCargos();
            context.update("form:datosCargo");
        }
        if (guardadoSueldoMercado == false) {
            cancelarModificacionSueldosMercados();
            context.update("form:datosSueldoMercado");
        }
        if (guardadoCompetencia == false) {
            cancelarModificacionCompetenciasCargos();
            context.update("form:datosCompetenciaCargo");
        }
        if (guardadoTipoDetalle == false) {
            cancelarModificacionTiposDetalles();
            context.update("form:datosTipoDetalle");
        }

        activoDetalleCargo = true;
        detalleCargo = new DetallesCargos();
        legendDetalleCargo = "";
        context.update("form:legendDetalleCargo");
        context.update("form:detalleCargo");
        guardadoDetalleCargo = true;

    }

    public void cancelarModificacionCargos() {
        if (bandera == 1) {
            altoTablaCargo = "110";
            cargoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigo");
            cargoCodigo.setFilterStyle("display: none; visibility: hidden;");
            cargoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoNombre");
            cargoNombre.setFilterStyle("display: none; visibility: hidden;");
            cargoGrupoSalarial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoSalarial");
            cargoGrupoSalarial.setFilterStyle("display: none; visibility: hidden;");
            cargoSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSalario");
            cargoSalario.setFilterStyle("display: none; visibility: hidden;");
            cargoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMinimo");
            cargoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
            cargoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMaximo");
            cargoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
            cargoGrupoViatico = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoViatico");
            cargoGrupoViatico.setFilterStyle("display: none; visibility: hidden;");
            cargoCargoRotativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCargoRotativo");
            cargoCargoRotativo.setFilterStyle("display: none; visibility: hidden;");
            cargoJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoJefe");
            cargoJefe.setFilterStyle("display: none; visibility: hidden;");
            cargoProcesoProductivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoProcesoProductivo");
            cargoProcesoProductivo.setFilterStyle("display: none; visibility: hidden;");
            cargoCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigoAlternativo");
            cargoCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCargo");
            bandera = 0;
            filtrarListaCargos = null;
            tipoLista = 0;
        }
        listCargosBorrar.clear();
        listCargosCrear.clear();
        listCargosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaCargos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCargo");
    }

    public void cancelarModificacionSueldosMercados() {
        if (banderaSueldoMercado == 1) {
            altoTablaSueldoMercado = "58";
            sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
            sueldoMercadoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
            sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
            sueldoMercadoTipoEmpresa.setFilterStyle("display: none; visibility: hidden;");
            sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
            sueldoMercadoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
            banderaSueldoMercado = 0;
            filtrarListaSueldosMercados = null;
            tipoListaSueldoMercado = 0;
        }
        listSueldosMercadosBorrar.clear();
        listSueldosMercadosCrear.clear();
        listSueldosMercadosModificar.clear();
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
        k = 0;
        listaSueldosMercados = null;
        guardadoSueldoMercado = true;
        permitirIndexSueldoMercado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSueldoMercado");
    }

    public void cancelarModificacionCompetenciasCargos() {
        if (banderaCompetencia == 1) {
            altoTablaCompetencia = "58";
            competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
            competenciaCargoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
            banderaCompetencia = 0;
            filtrarListaCompetenciasCargos = null;
            tipoListaCompetencia = 0;
        }
        listCompetenciasCargosBorrar.clear();
        listCompetenciasCargosCrear.clear();
        listCompetenciasCargosModificar.clear();
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
        k = 0;
        listaCompetenciasCargos = null;
        guardadoCompetencia = true;
        permitirCompetencia = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCompetenciaCargo");
    }

    public void cancelarModificacionTiposDetalles() {
        if (banderaTipoDetalle == 1) {
            altoTablaTipoDetalle = "58";
            tipoDetalleDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleDescripcion");
            tipoDetalleDescripcion.setFilterStyle("display: none; visibility: hidden;");
            tipoDetalleCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleCodigo");
            tipoDetalleCodigo.setFilterStyle("display: none; visibility: hidden;");
            tipoDetalleEnfoque = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleEnfoque");
            tipoDetalleEnfoque.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoDetalle");
            banderaTipoDetalle = 0;
            filtrarListaTiposDetalles = null;
            tipoListaTipoDetalle = 0;
        }
        listTiposDetallesBorrar.clear();
        listTiposDetallesCrear.clear();
        listTiposDetallesModificar.clear();
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
        k = 0;
        listaTiposDetalles = null;
        guardadoTipoDetalle = true;
        permitirIndexTipoDetalle = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoDetalle");
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarCargo = listaCargos.get(index);
            }
            if (tipoLista == 1) {
                editarCargo = filtrarListaCargos.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigoCargoD");
                context.execute("editarCodigoCargoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombreCargoD");
                context.execute("editarNombreCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarGrupoSalarialCargoD");
                context.execute("editarGrupoSalarialCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarSalarioCargoD");
                context.execute("editarSalarioCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarSueldoMinimoCargoD");
                context.execute("editarSueldoMinimoCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarSueldoMaximoCargoD");
                context.execute("editarSueldoMaximoCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarGrupoViaticoCargoD");
                context.execute("editarGrupoViaticoCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarProcesoProductivoCargoD");
                context.execute("editarProcesoProductivoCargoD.show()");
                cualCelda = -1;

            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editarCodigoAlternativoCargoD");
                context.execute("editarCodigoAlternativoCargoD.show()");
                cualCelda = -1;
            }
            index = -1;
            secRegistro = null;
        }
        if (indexSueldoMercado >= 0) {
            if (tipoListaSueldoMercado == 0) {
                editarSueldoMercado = listaSueldosMercados.get(indexSueldoMercado);
            }
            if (tipoListaSueldoMercado == 1) {
                editarSueldoMercado = listaSueldosMercados.get(indexSueldoMercado);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaSueldoMercado == 0) {
                context.update("formularioDialogos:editarTipoEmpresaSueldoMercadoD");
                context.execute("editarTipoEmpresaSueldoMercadoD.show()");
                cualCeldaSueldoMercado = -1;
            } else if (cualCeldaSueldoMercado == 1) {
                context.update("formularioDialogos:editarSueldoMinimoSueldoMercadoD");
                context.execute("editarSueldoMinimoSueldoMercadoD.show()");
                cualCeldaSueldoMercado = -1;
            } else if (cualCeldaSueldoMercado == 2) {
                context.update("formularioDialogos:editarSueldoMaximoSueldoMercadoD");
                context.execute("editarSueldoMaximoSueldoMercadoD.show()");
                cualCeldaSueldoMercado = -1;
            }
            indexSueldoMercado = -1;
            secRegistroSueldoMercado = null;
        }
        if (indexCompetenciaCargo >= 0) {
            if (tipoListaCompetencia == 0) {
                editarCompetenciaCargo = listaCompetenciasCargos.get(indexCompetenciaCargo);
            }
            if (tipoListaCompetencia == 1) {
                editarCompetenciaCargo = listaCompetenciasCargos.get(indexCompetenciaCargo);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaCompetencia == 0) {
                context.update("formularioDialogos:editarDescripcionCompetenciaCargoD");
                context.execute("editarDescripcionCompetenciaCargoD.show()");
                cualCeldaCompetencia = -1;
            }
            indexCompetenciaCargo = -1;
            secRegistroCompetencia = null;
        }
        if (indexTipoDetalle >= 0) {
            if (tipoListaTipoDetalle == 0) {
                editarTipoDetalle = listaTiposDetalles.get(indexTipoDetalle);
            }
            if (tipoListaTipoDetalle == 1) {
                editarTipoDetalle = listaTiposDetalles.get(indexTipoDetalle);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaTipoDetalle == 0) {
                context.update("formularioDialogos:editarCodigoTipoDetalleD");
                context.execute("editarCodigoTipoDetalleD.show()");
                cualCeldaTipoDetalle = -1;
            } else if (cualCeldaTipoDetalle == 1) {
                context.update("formularioDialogos:editarDescripcionTipoDetalleD");
                context.execute("editarDescripcionTipoDetalleD.show()");
                cualCeldaTipoDetalle = -1;
            } else if (cualCeldaTipoDetalle == 2) {
                context.update("formularioDialogos:editarEnfoqueTipoDetalleD");
                context.execute("editarEnfoqueTipoDetalleD.show()");
                cualCeldaTipoDetalle = -1;
            }
            indexTipoDetalle = -1;
            secRegistroTipoDetalle = null;
        }
    }

    public void dialogoNuevoRegistro() {
        if (guardado == false || guardadoSueldoMercado == false || guardadoCompetencia == false || guardadoTipoDetalle == false) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            int tam1 = listaCargos.size();
            int tam2 = listaSueldosMercados.size();
            int tam3 = listaCompetenciasCargos.size();
            int tam4 = listaTiposDetalles.size();
            if (tam1 == 0) {
                activoSueldoMercado = true;
                activoCompetencia = true;
                activoTipoDetalle = false;
                context.update("formularioDialogos:verificarNuevoRegistro");
                context.execute("verificarNuevoRegistro.show()");
            } else {
                if (tam2 == 0 || tam3 == 0 || tam4 == 0) {
                    activoSueldoMercado = false;
                    activoCompetencia = false;
                    activoTipoDetalle = false;
                    context.update("formularioDialogos:verificarNuevoRegistro");
                    context.execute("verificarNuevoRegistro.show()");
                } else {
                    activoSueldoMercado = false;
                    activoCompetencia = false;
                    activoTipoDetalle = false;
                    if (index >= 0) {
                        codigoNuevoCargo();
                        context.update("formularioDialogos:NuevoRegistroCargo");
                        context.execute("NuevoRegistroCargo.show()");
                    }
                    if (indexSueldoMercado >= 0) {
                        context.update("formularioDialogos:NuevoRegistroSueldoMercado");
                        context.execute("NuevoRegistroSueldoMercado.show()");
                    }
                    if (indexCompetenciaCargo >= 0) {
                        context.update("formularioDialogos:NuevoRegistroCompetenciaCargo");
                        context.execute("NuevoRegistroCompetenciaCargo.show()");
                    }
                    if (indexTipoDetalle >= 0) {
                        codigoNuevoTipoDetalle();
                        context.update("formularioDialogos:NuevoRegistroTipoDetalle");
                        context.execute("NuevoRegistroTipoDetalle.show()");
                    }
                }
            }
        }
    }

    public void dispararDialogoNuevoCargo() {
        RequestContext context = RequestContext.getCurrentInstance();
        codigoNuevoCargo();
        context.update("formularioDialogos:NuevoRegistroCargo");
        context.execute("NuevoRegistroCargo.show()");
    }

    public void dispararDialogoNuevoTipoDetalle() {
        RequestContext context = RequestContext.getCurrentInstance();
        codigoNuevoTipoDetalle();
        context.update("formularioDialogos:NuevoRegistroTipoDetalle");
        context.execute("NuevoRegistroTipoDetalle.show()");
    }

    public void codigoNuevoCargo() {
        String code = "";
        int tam = listaCargos.size();
        if (tam > 0) {
            int newCode = listaCargos.get(tam - 1).getCodigo().intValue() + 1;
            code = String.valueOf(newCode);
        } else {
            code = "1";
        }
        nuevoCargo.setCodigo(new Short(code));
    }

    public void codigoNuevoTipoDetalle() {
        String code = "";
        int tam = listaTiposDetalles.size();
        if (tam > 0) {
            int newCode = listaTiposDetalles.get(tam - 1).getCodigo().intValue() + 1;
            code = String.valueOf(newCode);
        } else {
            code = "1";
        }
        nuevoTipoDetalle.setCodigo(new BigInteger(code));
    }

//CREAR 
    public void agregarNuevoCargo() {
        boolean respueta = validarCamposNulosCargos(1);
        if (respueta == true) {
            int tamDes = 0;
            tamDes = nuevoCargo.getNombre().length();
            if (tamDes >= 1 && tamDes <= 50) {
                if (bandera == 1) {
                    altoTablaCargo = "110";
                    cargoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigo");
                    cargoCodigo.setFilterStyle("display: none; visibility: hidden;");
                    cargoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoNombre");
                    cargoNombre.setFilterStyle("display: none; visibility: hidden;");
                    cargoGrupoSalarial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoSalarial");
                    cargoGrupoSalarial.setFilterStyle("display: none; visibility: hidden;");
                    cargoSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSalario");
                    cargoSalario.setFilterStyle("display: none; visibility: hidden;");
                    cargoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMinimo");
                    cargoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                    cargoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMaximo");
                    cargoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                    cargoGrupoViatico = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoViatico");
                    cargoGrupoViatico.setFilterStyle("display: none; visibility: hidden;");
                    cargoCargoRotativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCargoRotativo");
                    cargoCargoRotativo.setFilterStyle("display: none; visibility: hidden;");
                    cargoJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoJefe");
                    cargoJefe.setFilterStyle("display: none; visibility: hidden;");
                    cargoProcesoProductivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoProcesoProductivo");
                    cargoProcesoProductivo.setFilterStyle("display: none; visibility: hidden;");
                    cargoCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigoAlternativo");
                    cargoCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosCargo");
                    bandera = 0;
                    filtrarListaCargos = null;
                    tipoLista = 0;
                }

                k++;
                l = BigInteger.valueOf(k);
                nuevoCargo.setEmpresa(empresaActual);
                String text = nuevoCargo.getNombre().toUpperCase();
                nuevoCargo.setNombre(text);
                if (nuevoCargo.getCodigoalternativo() != null) {
                    String text2 = nuevoCargo.getCodigoalternativo().toUpperCase();
                    nuevoCargo.setCodigoalternativo(text2);
                }
                nuevoCargo.setSecuencia(l);
                listCargosCrear.add(nuevoCargo);
                listaCargos.add(nuevoCargo);
                nuevoCargo = new Cargos();
                nuevoCargo.setGruposalarial(new GruposSalariales());
                nuevoCargo.setGrupoviatico(new GruposViaticos());
                nuevoCargo.setProcesoproductivo(new ProcesosProductivos());
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosCargo");
                context.execute("NuevoRegistroCargo.hide()");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                index = -1;
                secRegistro = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorDescripcionCargo.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullCargo.show()");
        }
    }

    public void agregarNuevoSueldoMercado() {
        boolean respueta = validarCamposNulosSueldosMercados(1);
        if (respueta == true) {
            if (banderaSueldoMercado == 1) {
                altoTablaSueldoMercado = "58";
                sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
                sueldoMercadoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
                sueldoMercadoTipoEmpresa.setFilterStyle("display: none; visibility: hidden;");
                sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
                sueldoMercadoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
                banderaSueldoMercado = 0;
                filtrarListaSueldosMercados = null;
                tipoListaSueldoMercado = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevoSueldoMercado.setSecuencia(l);
            if (tipoLista == 0) {
                nuevoSueldoMercado.setCargo(listaCargos.get(indexAux));
            }
            if (tipoLista == 1) {
                nuevoSueldoMercado.setCargo(filtrarListaCargos.get(indexAux));
            }
            if (listaSueldosMercados.size() == 0) {
                listaSueldosMercados = new ArrayList<SueldosMercados>();
            }
            listSueldosMercadosCrear.add(nuevoSueldoMercado);
            listaSueldosMercados.add(nuevoSueldoMercado);
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            index = indexAux;
            context.update("form:datosSueldoMercado");
            context.execute("NuevoRegistroSueldoMercado.hide()");
            nuevoSueldoMercado = new SueldosMercados();
            nuevoSueldoMercado.setTipoempresa(new TiposEmpresas());
            if (guardadoSueldoMercado == true) {
                guardadoSueldoMercado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            indexSueldoMercado = -1;
            secRegistroSueldoMercado = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullSueldoMercado.show()");
        }
    }

    public void agregarNuevoCompetenciaCargo() {
        if (nuevoCompetenciaCargo.getEvalcompetencia().getSecuencia() != null) {
            if (banderaCompetencia == 1) {
                altoTablaCompetencia = "58";
                competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
                competenciaCargoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
                banderaCompetencia = 0;
                filtrarListaCompetenciasCargos = null;
                tipoListaCompetencia = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevoCompetenciaCargo.setSecuencia(l);
            if (tipoLista == 0) {
                nuevoCompetenciaCargo.setCargo(listaCargos.get(indexAux));
            }
            if (tipoLista == 1) {
                nuevoCompetenciaCargo.setCargo(filtrarListaCargos.get(indexAux));
            }
            if (listaCompetenciasCargos.size() == 0) {
                listaCompetenciasCargos = new ArrayList<Competenciascargos>();
            }
            listCompetenciasCargosCrear.add(nuevoCompetenciaCargo);
            listaCompetenciasCargos.add(nuevoCompetenciaCargo);
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            index = indexAux;
            context.update("form:datosCompetenciaCargo");
            context.execute("NuevoRegistroCompetenciaCargo.hide()");
            nuevoCompetenciaCargo = new Competenciascargos();
            nuevoCompetenciaCargo.setEvalcompetencia(new EvalCompetencias());
            if (guardadoCompetencia == true) {
                guardadoCompetencia = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            indexCompetenciaCargo = -1;
            secRegistroCompetencia = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullCompetencia.show()");
        }
    }

    public void agregarNuevoTipoDetalle() {
        boolean respueta = validarCamposNulosTiposDetalles(1);
        if (respueta == true) {
            if (nuevoTipoDetalle.getDescripcion().length() >= 1 && nuevoTipoDetalle.getDescripcion().length() <= 40) {
                if (banderaTipoDetalle == 1) {
                    altoTablaTipoDetalle = "58";
                    tipoDetalleDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleDescripcion");
                    tipoDetalleDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    tipoDetalleCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleCodigo");
                    tipoDetalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                    tipoDetalleEnfoque = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleEnfoque");
                    tipoDetalleEnfoque.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosTipoDetalle");
                    banderaTipoDetalle = 0;
                    filtrarListaTiposDetalles = null;
                    tipoListaTipoDetalle = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevoTipoDetalle.setSecuencia(l);
                if (listaTiposDetalles.size() == 0) {
                    listaTiposDetalles = new ArrayList<TiposDetalles>();
                }
                String var = nuevoTipoDetalle.getDescripcion().toUpperCase();
                nuevoTipoDetalle.setDescripcion(var);
                listTiposDetallesCrear.add(nuevoTipoDetalle);
                listaTiposDetalles.add(nuevoTipoDetalle);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosTipoDetalle");
                context.execute("NuevoRegistroTipoDetalle.hide()");
                nuevoTipoDetalle = new TiposDetalles();
                nuevoTipoDetalle.setEnfoque(new Enfoques());
                if (guardadoTipoDetalle == true) {
                    guardadoTipoDetalle = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                indexTipoDetalle = -1;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorDescripcionTipoDetalle.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullTipoDetalle.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     */
    public void limpiarNuevaCargo() {
        nuevoCargo = new Cargos();
        nuevoCargo.setGruposalarial(new GruposSalariales());
        nuevoCargo.setGrupoviatico(new GruposViaticos());
        nuevoCargo.setProcesoproductivo(new ProcesosProductivos());
        index = -1;
        secRegistro = null;
    }

    public void limpiarNuevaSueldoMercado() {
        nuevoSueldoMercado = new SueldosMercados();
        nuevoSueldoMercado.setTipoempresa(new TiposEmpresas());
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
    }

    public void limpiarNuevaCompetenciaCargo() {
        nuevoCompetenciaCargo = new Competenciascargos();
        nuevoCompetenciaCargo.setEvalcompetencia(new EvalCompetencias());
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
    }

    public void limpiarNuevaTipoDetalle() {
        nuevoTipoDetalle = new TiposDetalles();
        nuevoTipoDetalle.setEnfoque(new Enfoques());
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
    }

    //DUPLICAR VC
    /**
     */
    public void verificarRegistroDuplicar() {
        if (index >= 0) {
            duplicarCargoM();
        }
        if (indexSueldoMercado >= 0) {
            duplicarSueldoMercadoM();
        }
        if (indexCompetenciaCargo >= 0) {
            duplicarCompetenciaCargoM();
        }
        if (indexTipoDetalle >= 0) {
            duplicarTipoDetalleM();
        }
    }

    public void duplicarCargoM() {
        duplicarCargo = new Cargos();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoLista == 0) {
            duplicarCargo.setSecuencia(l);
            duplicarCargo.setCodigo(listaCargos.get(index).getCodigo());
            duplicarCargo.setNombre(listaCargos.get(index).getNombre());
            duplicarCargo.setGruposalarial(listaCargos.get(index).getGruposalarial());
            duplicarCargo.setSueldominimo(listaCargos.get(index).getSueldomaximo());
            duplicarCargo.setSueldomaximo(listaCargos.get(index).getSueldomaximo());
            duplicarCargo.setGrupoviatico(listaCargos.get(index).getGrupoviatico());
            duplicarCargo.setTurnorotativo(listaCargos.get(index).getTurnorotativo());
            duplicarCargo.setJefe(listaCargos.get(index).getJefe());
            duplicarCargo.setProcesoproductivo(listaCargos.get(index).getProcesoproductivo());
            duplicarCargo.setCodigoalternativo(listaCargos.get(index).getCodigoalternativo());
        }
        if (tipoLista == 1) {
            duplicarCargo.setSecuencia(l);
            duplicarCargo.setCodigo(filtrarListaCargos.get(index).getCodigo());
            duplicarCargo.setNombre(filtrarListaCargos.get(index).getNombre());
            duplicarCargo.setGruposalarial(filtrarListaCargos.get(index).getGruposalarial());
            duplicarCargo.setSueldominimo(filtrarListaCargos.get(index).getSueldomaximo());
            duplicarCargo.setSueldomaximo(filtrarListaCargos.get(index).getSueldomaximo());
            duplicarCargo.setGrupoviatico(filtrarListaCargos.get(index).getGrupoviatico());
            duplicarCargo.setTurnorotativo(filtrarListaCargos.get(index).getTurnorotativo());
            duplicarCargo.setJefe(filtrarListaCargos.get(index).getJefe());
            duplicarCargo.setProcesoproductivo(filtrarListaCargos.get(index).getProcesoproductivo());
            duplicarCargo.setCodigoalternativo(filtrarListaCargos.get(index).getCodigoalternativo());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroCargo");
        context.execute("DuplicarRegistroCargo.show()");
        index = -1;
        secRegistro = null;

    }

    public void duplicarSueldoMercadoM() {
        duplicarSueldoMercado = new SueldosMercados();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoListaSueldoMercado == 0) {
            duplicarSueldoMercado.setSecuencia(l);
            duplicarSueldoMercado.setTipoempresa(listaSueldosMercados.get(indexSueldoMercado).getTipoempresa());
            duplicarSueldoMercado.setSueldomax(listaSueldosMercados.get(indexSueldoMercado).getSueldomax());
            duplicarSueldoMercado.setSueldomin(listaSueldosMercados.get(indexSueldoMercado).getSueldomin());
        }
        if (tipoListaSueldoMercado == 1) {
            duplicarSueldoMercado.setSecuencia(l);
            duplicarSueldoMercado.setTipoempresa(filtrarListaSueldosMercados.get(indexSueldoMercado).getTipoempresa());
            duplicarSueldoMercado.setSueldomax(filtrarListaSueldosMercados.get(indexSueldoMercado).getSueldomax());
            duplicarSueldoMercado.setSueldomin(filtrarListaSueldosMercados.get(indexSueldoMercado).getSueldomin());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroSueldoMercado");
        context.execute("DuplicarRegistroSueldoMercado.show()");
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;

    }

    public void duplicarCompetenciaCargoM() {
        duplicarCompetenciaCargo = new Competenciascargos();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoListaCompetencia == 0) {
            duplicarCompetenciaCargo.setSecuencia(l);
            duplicarCompetenciaCargo.setEvalcompetencia(listaCompetenciasCargos.get(indexCompetenciaCargo).getEvalcompetencia());
        }
        if (tipoListaCompetencia == 1) {
            duplicarCompetenciaCargo.setSecuencia(l);
            duplicarCompetenciaCargo.setEvalcompetencia(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo).getEvalcompetencia());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroCompetenciaCargo");
        context.execute("DuplicarRegistroCompetenciaCargo.show()");
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;

    }

    public void duplicarTipoDetalleM() {
        duplicarTipoDetalle = new TiposDetalles();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoListaTipoDetalle == 0) {
            duplicarTipoDetalle.setSecuencia(l);
            duplicarTipoDetalle.setCodigo(listaTiposDetalles.get(indexTipoDetalle).getCodigo());
            duplicarTipoDetalle.setDescripcion(listaTiposDetalles.get(indexTipoDetalle).getDescripcion());
            duplicarTipoDetalle.setEnfoque(listaTiposDetalles.get(indexTipoDetalle).getEnfoque());
        }
        if (tipoListaTipoDetalle == 1) {
            duplicarTipoDetalle.setSecuencia(l);
            duplicarTipoDetalle.setCodigo(filtrarListaTiposDetalles.get(indexTipoDetalle).getCodigo());
            duplicarTipoDetalle.setDescripcion(filtrarListaTiposDetalles.get(indexTipoDetalle).getDescripcion());
            duplicarTipoDetalle.setEnfoque(filtrarListaTiposDetalles.get(indexTipoDetalle).getEnfoque());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroTipoDetalle");
        context.execute("DuplicarRegistroTipoDetalle.show()");
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;

    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla Sets
     */
    public void confirmarDuplicarCargo() {
        boolean respueta = validarCamposNulosCargos(2);
        if (respueta == true) {
            int tamDes = 0;
            tamDes = nuevoCargo.getNombre().length();
            if (tamDes >= 1 && tamDes <= 50) {
                if (bandera == 1) {
                    altoTablaCargo = "110";
                    cargoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigo");
                    cargoCodigo.setFilterStyle("display: none; visibility: hidden;");
                    cargoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoNombre");
                    cargoNombre.setFilterStyle("display: none; visibility: hidden;");
                    cargoGrupoSalarial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoSalarial");
                    cargoGrupoSalarial.setFilterStyle("display: none; visibility: hidden;");
                    cargoSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSalario");
                    cargoSalario.setFilterStyle("display: none; visibility: hidden;");
                    cargoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMinimo");
                    cargoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                    cargoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMaximo");
                    cargoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                    cargoGrupoViatico = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoViatico");
                    cargoGrupoViatico.setFilterStyle("display: none; visibility: hidden;");
                    cargoCargoRotativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCargoRotativo");
                    cargoCargoRotativo.setFilterStyle("display: none; visibility: hidden;");
                    cargoJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoJefe");
                    cargoJefe.setFilterStyle("display: none; visibility: hidden;");
                    cargoProcesoProductivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoProcesoProductivo");
                    cargoProcesoProductivo.setFilterStyle("display: none; visibility: hidden;");
                    cargoCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigoAlternativo");
                    cargoCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosCargo");
                    bandera = 0;
                    filtrarListaCargos = null;
                    tipoLista = 0;
                }
                duplicarCargo.setEmpresa(empresaActual);
                String text = duplicarCargo.getNombre().toUpperCase();
                duplicarCargo.setNombre(text);
                if (duplicarCargo.getCodigoalternativo() != null) {
                    String text2 = duplicarCargo.getCodigoalternativo().toUpperCase();
                    duplicarCargo.setCodigoalternativo(text2);
                }
                listaCargos.add(duplicarCargo);
                listCargosCrear.add(duplicarCargo);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosCargo");
                context.execute("DuplicarRegistroCargo.hide()");
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                duplicarCargo = new Cargos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorDescripcionCargo.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullCargo.show()");
        }
    }

    public void confirmarDuplicarSueldoMercado() {
        boolean respueta = validarCamposNulosSueldosMercados(2);
        if (respueta == true) {
            if (banderaSueldoMercado == 1) {
                altoTablaSueldoMercado = "58";
                sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
                sueldoMercadoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
                sueldoMercadoTipoEmpresa.setFilterStyle("display: none; visibility: hidden;");
                sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
                sueldoMercadoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
                banderaSueldoMercado = 0;
                filtrarListaSueldosMercados = null;
                tipoListaSueldoMercado = 0;
            }

            if (tipoLista == 0) {
                duplicarSueldoMercado.setCargo(listaCargos.get(indexAux));
            }
            if (tipoLista == 1) {
                duplicarSueldoMercado.setCargo(filtrarListaCargos.get(indexAux));
            }
            listaSueldosMercados.add(duplicarSueldoMercado);
            listSueldosMercadosCrear.add(duplicarSueldoMercado);
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosSueldoMercado");
            context.execute("DuplicarRegistroSueldoMercado.hide()");
            indexSueldoMercado = -1;
            secRegistroSueldoMercado = null;
            if (guardadoSueldoMercado == true) {
                guardadoSueldoMercado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }

            duplicarSueldoMercado = new SueldosMercados();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullSueldoMercado.show()");
        }
    }

    public void confirmarDuplicarCompetenciaCargo() {
        if (duplicarCompetenciaCargo.getEvalcompetencia().getSecuencia() != null) {
            if (banderaCompetencia == 1) {
                altoTablaCompetencia = "58";
                competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
                competenciaCargoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
                banderaCompetencia = 0;
                filtrarListaCompetenciasCargos = null;
                tipoListaCompetencia = 0;
            }
            if (tipoLista == 0) {
                duplicarCompetenciaCargo.setCargo(listaCargos.get(indexAux));
            }
            if (tipoLista == 1) {
                duplicarCompetenciaCargo.setCargo(filtrarListaCargos.get(indexAux));
            }
            listaCompetenciasCargos.add(duplicarCompetenciaCargo);
            listCompetenciasCargosCrear.add(duplicarCompetenciaCargo);
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCompetenciaCargo");
            context.execute("DuplicarRegistroCompetenciaCargo.hide()");
            indexCompetenciaCargo = -1;
            secRegistroCompetencia = null;
            if (guardadoCompetencia == true) {
                guardadoCompetencia = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }

            duplicarCompetenciaCargo = new Competenciascargos();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullCompetencia.show()");
        }
    }

    public void confirmarDuplicarTipoDetalle() {
        boolean respueta = validarCamposNulosTiposDetalles(2);
        if (respueta == true) {
            if (duplicarTipoDetalle.getDescripcion().length() >= 1 && duplicarTipoDetalle.getDescripcion().length() <= 40) {
                if (banderaTipoDetalle == 1) {
                    altoTablaTipoDetalle = "58";
                    tipoDetalleDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleDescripcion");
                    tipoDetalleDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    tipoDetalleCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleCodigo");
                    tipoDetalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                    tipoDetalleEnfoque = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleEnfoque");
                    tipoDetalleEnfoque.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosTipoDetalle");
                    banderaTipoDetalle = 0;
                    filtrarListaTiposDetalles = null;
                    tipoListaTipoDetalle = 0;
                }
                String var = duplicarTipoDetalle.getDescripcion().toUpperCase();
                duplicarTipoDetalle.setDescripcion(var);
                listaTiposDetalles.add(duplicarTipoDetalle);
                listTiposDetallesCrear.add(duplicarTipoDetalle);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosTipoDetalle");
                context.execute("DuplicarRegistroTipoDetalle.hide()");
                indexTipoDetalle = -1;
                secRegistroTipoDetalle = null;
                if (guardadoTipoDetalle == true) {
                    guardadoTipoDetalle = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                duplicarTipoDetalle = new TiposDetalles();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorDescripcionTipoDetalle.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullTipoDetalle.show()");
        }
    }

    //LIMPIAR DUPLICAR
    /**
     */
    public void limpiarDuplicarCargo() {
        duplicarCargo = new Cargos();
        duplicarCargo.setGruposalarial(new GruposSalariales());
        duplicarCargo.setGrupoviatico(new GruposViaticos());
        duplicarCargo.setProcesoproductivo(new ProcesosProductivos());
    }

    public void limpiarDuplicarSueldoMercado() {
        duplicarSueldoMercado = new SueldosMercados();
        duplicarSueldoMercado.setTipoempresa(new TiposEmpresas());
    }

    public void limpiarDuplicarCompetenciaCargo() {
        duplicarCompetenciaCargo = new Competenciascargos();
        duplicarCompetenciaCargo.setEvalcompetencia(new EvalCompetencias());
    }

    public void limpiarDuplicarTipoDetalle() {
        duplicarTipoDetalle = new TiposDetalles();
        duplicarTipoDetalle.setEnfoque(new Enfoques());
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //BORRAR VC
    /**
     */
    public boolean validarExistenciaCargoDetalleCargo() {
        boolean retorno = true;
        int regAsociados = 0;
        if (tipoLista == 0) {
            regAsociados = administrarCargos.validarExistenciaCargoDetalleCargos(listaCargos.get(index).getSecuencia());
        }
        if (tipoLista == 1) {
            regAsociados = administrarCargos.validarExistenciaCargoDetalleCargos(filtrarListaCargos.get(index).getSecuencia());
        }
        if (regAsociados == 0) {
            retorno = true;
        }
        if (regAsociados > 0) {
            retorno = false;
        }
        return retorno;
    }

    public void verificarRegistroBorrar() {
        if (index >= 0) {
            int tam = listaSueldosMercados.size();
            int tam2 = listaCompetenciasCargos.size();
            if (tam == 0 && tam2 == 0) {
                if (validarExistenciaCargoDetalleCargo() == true) {
                    borrarCargo();
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorBorradoCargo.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorBorrarRegistro.show()");
            }
        }
        if (indexSueldoMercado >= 0) {
            borrarSueldoMercado();
        }
        if (indexCompetenciaCargo >= 0) {
            borrarCompetenciaCargo();
        }
        if (indexTipoDetalle >= 0) {
            borrarTipoDetalle();
        }
        if (indexDetalleCargo >= 0) {
            borrarDetalleCargoM();
        }
    }

    public void borrarDetalleCargoM() {
        borrarDetalleCargo = true;
        guardadoDetalleCargo = false;
        BigInteger auxS = detalleCargo.getSecuencia();
        Cargos auxC = detalleCargo.getCargo();
        TiposDetalles auxTD = detalleCargo.getTipodetalle();
        detalleCargo = new DetallesCargos();
        detalleCargo.setCargo(auxC);
        detalleCargo.setSecuencia(auxS);
        detalleCargo.setTipodetalle(auxTD);
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:detalleCargo");
    }

    public void borrarCargo() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listCargosModificar.isEmpty() && listCargosModificar.contains(listaCargos.get(index))) {
                    int modIndex = listCargosModificar.indexOf(listaCargos.get(index));
                    listCargosModificar.remove(modIndex);
                    listCargosBorrar.add(listaCargos.get(index));
                } else if (!listCargosCrear.isEmpty() && listCargosCrear.contains(listaCargos.get(index))) {
                    int crearIndex = listCargosCrear.indexOf(listaCargos.get(index));
                    listCargosCrear.remove(crearIndex);
                } else {
                    listCargosBorrar.add(listaCargos.get(index));
                }
                listaCargos.remove(index);
            }
            if (tipoLista == 1) {
                if (!listCargosModificar.isEmpty() && listCargosModificar.contains(filtrarListaCargos.get(index))) {
                    int modIndex = listCargosModificar.indexOf(filtrarListaCargos.get(index));
                    listCargosModificar.remove(modIndex);
                    listCargosBorrar.add(filtrarListaCargos.get(index));
                } else if (!listCargosCrear.isEmpty() && listCargosCrear.contains(filtrarListaCargos.get(index))) {
                    int crearIndex = listCargosCrear.indexOf(filtrarListaCargos.get(index));
                    listCargosCrear.remove(crearIndex);
                } else {
                    listCargosBorrar.add(filtrarListaCargos.get(index));
                }
                int VCIndex = listaCargos.indexOf(filtrarListaCargos.get(index));
                listaCargos.remove(VCIndex);
                filtrarListaCargos.remove(index);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCargo");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarSueldoMercado() {
        if (indexSueldoMercado >= 0) {
            if (tipoListaSueldoMercado == 0) {
                if (!listSueldosMercadosModificar.isEmpty() && listSueldosMercadosModificar.contains(listaSueldosMercados.get(indexSueldoMercado))) {
                    int modIndex = listSueldosMercadosModificar.indexOf(listaSueldosMercados.get(indexSueldoMercado));
                    listSueldosMercadosModificar.remove(modIndex);
                    listSueldosMercadosBorrar.add(listaSueldosMercados.get(indexSueldoMercado));
                } else if (!listSueldosMercadosCrear.isEmpty() && listSueldosMercadosCrear.contains(listaSueldosMercados.get(indexSueldoMercado))) {
                    int crearIndex = listSueldosMercadosCrear.indexOf(listaSueldosMercados.get(indexSueldoMercado));
                    listSueldosMercadosCrear.remove(crearIndex);
                } else {
                    listSueldosMercadosBorrar.add(listaSueldosMercados.get(indexSueldoMercado));
                }
                listaSueldosMercados.remove(indexSueldoMercado);
            }
            if (tipoListaSueldoMercado == 1) {
                if (!listSueldosMercadosModificar.isEmpty() && listSueldosMercadosModificar.contains(filtrarListaSueldosMercados.get(indexSueldoMercado))) {
                    int modIndex = listSueldosMercadosModificar.indexOf(filtrarListaSueldosMercados.get(indexSueldoMercado));
                    listSueldosMercadosModificar.remove(modIndex);
                    listSueldosMercadosBorrar.add(filtrarListaSueldosMercados.get(indexSueldoMercado));
                } else if (!listSueldosMercadosCrear.isEmpty() && listSueldosMercadosCrear.contains(filtrarListaSueldosMercados.get(indexSueldoMercado))) {
                    int crearIndex = listSueldosMercadosCrear.indexOf(filtrarListaSueldosMercados.get(indexSueldoMercado));
                    listSueldosMercadosCrear.remove(crearIndex);
                } else {
                    listSueldosMercadosBorrar.add(filtrarListaSueldosMercados.get(indexSueldoMercado));
                }
                int VCIndex = listaSueldosMercados.indexOf(filtrarListaSueldosMercados.get(indexSueldoMercado));
                listaSueldosMercados.remove(VCIndex);
                filtrarListaSueldosMercados.remove(indexSueldoMercado);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosSueldoMercado");
            indexSueldoMercado = -1;
            secRegistroSueldoMercado = null;

            if (guardadoSueldoMercado == true) {
                guardadoSueldoMercado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarCompetenciaCargo() {
        if (indexCompetenciaCargo >= 0) {
            if (tipoListaCompetencia == 0) {
                if (!listCompetenciasCargosModificar.isEmpty() && listCompetenciasCargosModificar.contains(listaCompetenciasCargos.get(indexCompetenciaCargo))) {
                    int modIndex = listCompetenciasCargosModificar.indexOf(listaCompetenciasCargos.get(indexCompetenciaCargo));
                    listCompetenciasCargosModificar.remove(modIndex);
                    listCompetenciasCargosBorrar.add(listaCompetenciasCargos.get(indexCompetenciaCargo));
                } else if (!listCompetenciasCargosCrear.isEmpty() && listCompetenciasCargosCrear.contains(listaCompetenciasCargos.get(indexCompetenciaCargo))) {
                    int crearIndex = listCompetenciasCargosCrear.indexOf(listaCompetenciasCargos.get(indexCompetenciaCargo));
                    listCompetenciasCargosCrear.remove(crearIndex);
                } else {
                    listCompetenciasCargosBorrar.add(listaCompetenciasCargos.get(indexCompetenciaCargo));
                }
                listaCompetenciasCargos.remove(indexCompetenciaCargo);
            }
            if (tipoListaCompetencia == 1) {
                if (!listCompetenciasCargosModificar.isEmpty() && listCompetenciasCargosModificar.contains(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo))) {
                    int modIndex = listCompetenciasCargosModificar.indexOf(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                    listCompetenciasCargosModificar.remove(modIndex);
                    listCompetenciasCargosBorrar.add(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                } else if (!listCompetenciasCargosCrear.isEmpty() && listCompetenciasCargosCrear.contains(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo))) {
                    int crearIndex = listCompetenciasCargosCrear.indexOf(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                    listCompetenciasCargosCrear.remove(crearIndex);
                } else {
                    listCompetenciasCargosBorrar.add(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                }
                int VCIndex = listaCompetenciasCargos.indexOf(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                listaCompetenciasCargos.remove(VCIndex);
                filtrarListaCompetenciasCargos.remove(indexCompetenciaCargo);
            }
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCompetenciaCargo");
            indexCompetenciaCargo = -1;
            secRegistroCompetencia = null;
            if (guardadoCompetencia == true) {
                guardadoCompetencia = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarTipoDetalle() {
        if (indexTipoDetalle >= 0) {
            if (tipoListaTipoDetalle == 0) {
                if (!listTiposDetallesModificar.isEmpty() && listTiposDetallesModificar.contains(listaTiposDetalles.get(indexTipoDetalle))) {
                    int modIndex = listTiposDetallesModificar.indexOf(listaTiposDetalles.get(indexTipoDetalle));
                    listTiposDetallesModificar.remove(modIndex);
                    listTiposDetallesBorrar.add(listaTiposDetalles.get(indexTipoDetalle));
                } else if (!listTiposDetallesCrear.isEmpty() && listTiposDetallesCrear.contains(listaTiposDetalles.get(indexTipoDetalle))) {
                    int crearIndex = listTiposDetallesCrear.indexOf(listaTiposDetalles.get(indexTipoDetalle));
                    listTiposDetallesCrear.remove(crearIndex);
                } else {
                    listTiposDetallesBorrar.add(listaTiposDetalles.get(indexTipoDetalle));
                }
                listaTiposDetalles.remove(indexTipoDetalle);
            }
            if (tipoListaTipoDetalle == 1) {
                if (!listTiposDetallesModificar.isEmpty() && listTiposDetallesModificar.contains(filtrarListaTiposDetalles.get(indexTipoDetalle))) {
                    int modIndex = listTiposDetallesModificar.indexOf(filtrarListaTiposDetalles.get(indexTipoDetalle));
                    listTiposDetallesModificar.remove(modIndex);
                    listTiposDetallesBorrar.add(filtrarListaTiposDetalles.get(indexTipoDetalle));
                } else if (!listTiposDetallesCrear.isEmpty() && listTiposDetallesCrear.contains(filtrarListaTiposDetalles.get(indexTipoDetalle))) {
                    int crearIndex = listTiposDetallesCrear.indexOf(filtrarListaTiposDetalles.get(indexTipoDetalle));
                    listTiposDetallesCrear.remove(crearIndex);
                } else {
                    listTiposDetallesBorrar.add(filtrarListaTiposDetalles.get(indexTipoDetalle));
                }
                int VCIndex = listaTiposDetalles.indexOf(filtrarListaTiposDetalles.get(indexTipoDetalle));
                listaTiposDetalles.remove(VCIndex);
                filtrarListaTiposDetalles.remove(indexTipoDetalle);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosTipoDetalle");
            indexTipoDetalle = -1;
            secRegistroTipoDetalle = null;

            if (guardadoTipoDetalle == true) {
                guardadoTipoDetalle = false;
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
        if (index >= 0) {
            if (bandera == 0) {
                altoTablaCargo = "88";
                cargoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigo");
                cargoCodigo.setFilterStyle("width: 25px");
                cargoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoNombre");
                cargoNombre.setFilterStyle("width: 95px");
                cargoGrupoSalarial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoSalarial");
                cargoGrupoSalarial.setFilterStyle("width: 85px");
                cargoSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSalario");
                cargoSalario.setFilterStyle("width: 60px");
                cargoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMinimo");
                cargoSueldoMinimo.setFilterStyle("width: 65px");
                cargoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMaximo");
                cargoSueldoMaximo.setFilterStyle("width: 65px");
                cargoGrupoViatico = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoViatico");
                cargoGrupoViatico.setFilterStyle("width: 85px");
                cargoCargoRotativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCargoRotativo");
                cargoCargoRotativo.setFilterStyle("width: 13px");
                cargoJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoJefe");
                cargoJefe.setFilterStyle("width: 13px");
                cargoProcesoProductivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoProcesoProductivo");
                cargoProcesoProductivo.setFilterStyle("width: 85px");
                cargoCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigoAlternativo");
                cargoCodigoAlternativo.setFilterStyle("width: 55px");
                RequestContext.getCurrentInstance().update("form:datosCargo");
                bandera = 1;
            } else if (bandera == 1) {
                altoTablaCargo = "110";
                cargoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigo");
                cargoCodigo.setFilterStyle("display: none; visibility: hidden;");
                cargoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoNombre");
                cargoNombre.setFilterStyle("display: none; visibility: hidden;");
                cargoGrupoSalarial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoSalarial");
                cargoGrupoSalarial.setFilterStyle("display: none; visibility: hidden;");
                cargoSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSalario");
                cargoSalario.setFilterStyle("display: none; visibility: hidden;");
                cargoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMinimo");
                cargoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                cargoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMaximo");
                cargoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                cargoGrupoViatico = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoViatico");
                cargoGrupoViatico.setFilterStyle("display: none; visibility: hidden;");
                cargoCargoRotativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCargoRotativo");
                cargoCargoRotativo.setFilterStyle("display: none; visibility: hidden;");
                cargoJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoJefe");
                cargoJefe.setFilterStyle("display: none; visibility: hidden;");
                cargoProcesoProductivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoProcesoProductivo");
                cargoProcesoProductivo.setFilterStyle("display: none; visibility: hidden;");
                cargoCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigoAlternativo");
                cargoCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCargo");
                bandera = 0;
                filtrarListaCargos = null;
                tipoLista = 0;
            }
        }
        if (indexSueldoMercado >= 0) {
            if (banderaSueldoMercado == 0) {
                altoTablaSueldoMercado = "36";
                sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
                sueldoMercadoTipoEmpresa.setFilterStyle("width: 105px");
                sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
                sueldoMercadoSueldoMinimo.setFilterStyle("width: 70px");
                sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
                sueldoMercadoSueldoMaximo.setFilterStyle("width: 70px");
                RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
                banderaSueldoMercado = 1;
            } else if (banderaSueldoMercado == 1) {
                altoTablaSueldoMercado = "58";
                sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
                sueldoMercadoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
                sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
                sueldoMercadoTipoEmpresa.setFilterStyle("display: none; visibility: hidden;");
                sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
                sueldoMercadoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
                banderaSueldoMercado = 0;
                filtrarListaSueldosMercados = null;
                tipoListaSueldoMercado = 0;
            }
        }
        if (indexCompetenciaCargo >= 0) {
            if (banderaCompetencia == 0) {
                altoTablaCompetencia = "36";
                competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
                competenciaCargoDescripcion.setFilterStyle("width: 115px");
                RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
                banderaCompetencia = 1;
            } else if (banderaCompetencia == 1) {
                altoTablaCompetencia = "58";
                competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
                competenciaCargoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
                banderaCompetencia = 0;
                filtrarListaCompetenciasCargos = null;
                tipoListaCompetencia = 0;
            }
        }
        if (indexTipoDetalle >= 0) {
            if (banderaTipoDetalle == 0) {
                altoTablaTipoDetalle = "36";

                tipoDetalleDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleDescripcion");
                tipoDetalleDescripcion.setFilterStyle("width: 65px");
                tipoDetalleCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleCodigo");
                tipoDetalleCodigo.setFilterStyle("width: 80px");
                tipoDetalleEnfoque = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleEnfoque");
                tipoDetalleEnfoque.setFilterStyle("width: 80px");
                RequestContext.getCurrentInstance().update("form:datosTipoDetalle");
                banderaTipoDetalle = 1;
            } else if (banderaTipoDetalle == 1) {
                altoTablaTipoDetalle = "58";
                tipoDetalleDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleDescripcion");
                tipoDetalleDescripcion.setFilterStyle("display: none; visibility: hidden;");
                tipoDetalleCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleCodigo");
                tipoDetalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                tipoDetalleEnfoque = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleEnfoque");
                tipoDetalleEnfoque.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoDetalle");
                banderaTipoDetalle = 0;
                filtrarListaTiposDetalles = null;
                tipoListaTipoDetalle = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            altoTablaCargo = "110";
            cargoCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigo");
            cargoCodigo.setFilterStyle("display: none; visibility: hidden;");
            cargoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoNombre");
            cargoNombre.setFilterStyle("display: none; visibility: hidden;");
            cargoGrupoSalarial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoSalarial");
            cargoGrupoSalarial.setFilterStyle("display: none; visibility: hidden;");
            cargoSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSalario");
            cargoSalario.setFilterStyle("display: none; visibility: hidden;");
            cargoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMinimo");
            cargoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
            cargoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoSueldoMaximo");
            cargoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
            cargoGrupoViatico = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoGrupoViatico");
            cargoGrupoViatico.setFilterStyle("display: none; visibility: hidden;");
            cargoCargoRotativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCargoRotativo");
            cargoCargoRotativo.setFilterStyle("display: none; visibility: hidden;");
            cargoJefe = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoJefe");
            cargoJefe.setFilterStyle("display: none; visibility: hidden;");
            cargoProcesoProductivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoProcesoProductivo");
            cargoProcesoProductivo.setFilterStyle("display: none; visibility: hidden;");
            cargoCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCargo:cargoCodigoAlternativo");
            cargoCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCargo");
            bandera = 0;
            filtrarListaCargos = null;
            tipoLista = 0;
        }
        if (banderaSueldoMercado == 1) {
            altoTablaSueldoMercado = "58";
            sueldoMercadoSueldoMinimo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMinimo");
            sueldoMercadoSueldoMinimo.setFilterStyle("display: none; visibility: hidden;");
            sueldoMercadoTipoEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoTipoEmpresa");
            sueldoMercadoTipoEmpresa.setFilterStyle("display: none; visibility: hidden;");
            sueldoMercadoSueldoMaximo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSueldoMercado:sueldoMercadoSueldoMaximo");
            sueldoMercadoSueldoMaximo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSueldoMercado");
            banderaSueldoMercado = 0;
            filtrarListaSueldosMercados = null;
            tipoListaSueldoMercado = 0;
        }
        if (banderaCompetencia == 1) {
            altoTablaCompetencia = "58";
            competenciaCargoDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCompetenciaCargo:competenciaCargoDescripcion");
            competenciaCargoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCompetenciaCargo");
            banderaCompetencia = 0;
            filtrarListaCompetenciasCargos = null;
            tipoListaCompetencia = 0;
        }
        if (banderaTipoDetalle == 1) {
            altoTablaTipoDetalle = "58";
            tipoDetalleDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleDescripcion");
            tipoDetalleDescripcion.setFilterStyle("display: none; visibility: hidden;");
            tipoDetalleCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleCodigo");
            tipoDetalleCodigo.setFilterStyle("display: none; visibility: hidden;");
            tipoDetalleEnfoque = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoDetalle:tipoDetalleEnfoque");
            tipoDetalleEnfoque.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoDetalle");
            banderaTipoDetalle = 0;
            filtrarListaTiposDetalles = null;
            tipoListaTipoDetalle = 0;
        }
        listCargosBorrar.clear();
        listCargosCrear.clear();
        listCargosModificar.clear();
        listSueldosMercadosBorrar.clear();
        listSueldosMercadosCrear.clear();
        listSueldosMercadosModificar.clear();
        listCompetenciasCargosBorrar.clear();
        listCompetenciasCargosCrear.clear();
        listCompetenciasCargosModificar.clear();
        listTiposDetallesBorrar.clear();
        listTiposDetallesCrear.clear();
        listTiposDetallesModificar.clear();
        index = -1;
        indexAux = -1;
        indexSueldoMercado = -1;
        indexCompetenciaCargo = -1;
        indexTipoDetalle = -1;
        secRegistro = null;
        secRegistroSueldoMercado = null;
        secRegistroTipoDetalle = null;
        secRegistroCompetencia = null;
        k = 0;
        listaCargos = null;
        listaSueldosMercados = null;
        listaTiposDetalles = null;
        listaCompetenciasCargos = null;
        guardado = true;
        guardadoSueldoMercado = true;
        guardadoTipoDetalle = true;
        guardadoCompetencia = true;
        cambiosPagina = true;
        lovGruposViaticos = null;
        lovGruposSalariales = null;
        lovProcesosProductivos = null;
        lovTiposEmpresas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 2) {
                context.update("form:GrupoSalarialDialogo");
                context.execute("GrupoSalarialDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 6) {
                context.update("form:GrupoViaticoDialogo");
                context.execute("GrupoViaticoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 9) {
                context.update("form:ProcesoProductivoDialogo");
                context.execute("ProcesoProductivoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexSueldoMercado >= 0) {
            if (cualCeldaSueldoMercado == 0) {
                context.update("form:TipoEmpresaDialogo");
                context.execute("TipoEmpresaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexCompetenciaCargo >= 0) {
            if (cualCeldaCompetencia == 0) {
                context.update("form:EvalCompetenciaDialogo");
                context.execute("EvalCompetenciaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexTipoDetalle >= 0) {
            if (cualCeldaTipoDetalle == 2) {
                context.update("form:EnfoqueDialogo");
                context.execute("EnfoqueDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND, int tabla) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tabla == 0) {
            if (LND == 0) {
                index = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:GrupoSalarialDialogo");
                context.execute("GrupoSalarialDialogo.show()");
            }
            if (dlg == 1) {
                context.update("form:GrupoViaticoDialogo");
                context.execute("GrupoViaticoDialogo.show()");
            }
            if (dlg == 2) {
                context.update("form:ProcesoProductivoDialogo");
                context.execute("ProcesoProductivoDialogo.show()");
            }
        }
        if (tabla == 1) {
            if (LND == 0) {
                indexSueldoMercado = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:TipoEmpresaDialogo");
                context.execute("TipoEmpresaDialogo.show()");
            }
        }
        if (tabla == 2) {
            if (LND == 0) {
                indexCompetenciaCargo = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:EvalCompetenciaDialogo");
                context.execute("EvalCompetenciaDialogo.show()");
            }
        }
        if (tabla == 3) {
            if (LND == 0) {
                indexTipoDetalle = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:EnfoqueDialogo");
                context.execute("EnfoqueDialogo.show()");
            }
        }
    }

    public void valoresBackupAutocompletarGeneral(int tipoNuevo, String Campo) {
        if (Campo.equals("GRUPOSALARIAL")) {
            if (tipoNuevo == 1) {
                grupoSalarial = nuevoCargo.getGruposalarial().getDescripcion();
            } else if (tipoNuevo == 2) {
                grupoSalarial = duplicarCargo.getGruposalarial().getDescripcion();
            }
        }
        if (Campo.equals("GRUPOVIATICO")) {
            if (tipoNuevo == 1) {
                grupoViatico = nuevoCargo.getGrupoviatico().getDescripcion();
            } else if (tipoNuevo == 2) {
                grupoViatico = duplicarCargo.getGrupoviatico().getDescripcion();
            }
        }
        if (Campo.equals("PROCESOPRODUCTIVO")) {
            if (tipoNuevo == 1) {
                procesoProductivo = nuevoCargo.getProcesoproductivo().getDescripcion();
            } else if (tipoNuevo == 2) {
                procesoProductivo = duplicarCargo.getProcesoproductivo().getDescripcion();
            }
        }
        if (Campo.equals("TIPOEMPRESA")) {
            if (tipoNuevo == 1) {
                tipoEmpresa = nuevoSueldoMercado.getTipoempresa().getDescripcion();
            } else if (tipoNuevo == 2) {
                tipoEmpresa = duplicarSueldoMercado.getTipoempresa().getDescripcion();
            }
        }
        if (Campo.equals("EVALCOMPETENCIA")) {
            if (tipoNuevo == 1) {
                evalCompetencia = nuevoCompetenciaCargo.getEvalcompetencia().getDescripcion();
            } else if (tipoNuevo == 2) {
                evalCompetencia = duplicarCompetenciaCargo.getEvalcompetencia().getDescripcion();
            }
        }
        if (Campo.equals("ENFOQUE")) {
            if (tipoNuevo == 1) {
                enfoque = nuevoTipoDetalle.getEnfoque().getDescripcion();
            } else if (tipoNuevo == 2) {
                enfoque = duplicarTipoDetalle.getEnfoque().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoCargo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPOSALARIAL")) {
            if (tipoNuevo == 1) {
                nuevoCargo.getGruposalarial().setDescripcion(grupoSalarial);
            } else if (tipoNuevo == 2) {
                duplicarCargo.getGruposalarial().setDescripcion(grupoSalarial);
            }
            for (int i = 0; i < lovGruposSalariales.size(); i++) {
                if (lovGruposSalariales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCargo.setGruposalarial(lovGruposSalariales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCargoGrupoSalarial");
                    context.update("formularioDialogos:nuevoCargoSalario");
                } else if (tipoNuevo == 2) {
                    duplicarCargo.setGruposalarial(lovGruposSalariales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCargoGrupoSalarial");
                    context.update("formularioDialogos:duplicarCargoSalario");
                }
                lovGruposSalariales = null;
                getLovGruposSalariales();
            } else {
                context.update("form:GrupoSalarialDialogo");
                context.execute("GrupoSalarialDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCargoGrupoSalarial");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCargoGrupoSalarial");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("GRUPOVIATICO")) {
            if (tipoNuevo == 1) {
                nuevoCargo.getGrupoviatico().setDescripcion(grupoViatico);
            } else if (tipoNuevo == 2) {
                duplicarCargo.getGrupoviatico().setDescripcion(grupoViatico);
            }
            for (int i = 0; i < lovGruposViaticos.size(); i++) {
                if (lovGruposViaticos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCargo.setGrupoviatico(lovGruposViaticos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCargoGrupoViatico");
                } else if (tipoNuevo == 2) {
                    duplicarCargo.setGrupoviatico(lovGruposViaticos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCargoGrupoViatico");
                }
                lovGruposViaticos = null;
                getLovGruposViaticos();
            } else {
                context.update("form:GrupoViaticoDialogo");
                context.execute("GrupoViaticoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCargoGrupoViatico");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCargoGrupoViatico");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("PROCESOPRODUCTIVO")) {
            if (tipoNuevo == 1) {
                nuevoCargo.getProcesoproductivo().setDescripcion(procesoProductivo);
            } else if (tipoNuevo == 2) {
                duplicarCargo.getProcesoproductivo().setDescripcion(procesoProductivo);
            }
            for (int i = 0; i < lovProcesosProductivos.size(); i++) {
                if (lovProcesosProductivos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCargo.setProcesoproductivo(lovProcesosProductivos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCargoProcesoProductivo");
                } else if (tipoNuevo == 2) {
                    duplicarCargo.setProcesoproductivo(lovProcesosProductivos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCargoProcesoProductivo");
                }
                lovProcesosProductivos = null;
                getLovProcesosProductivos();
            } else {
                context.update("form:ProcesoProductivoDialogo");
                context.execute("ProcesoProductivoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCargoProcesoProductivo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCargoProcesoProductivo");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoSueldoMercado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOEMPRESA")) {
            if (tipoNuevo == 1) {
                nuevoSueldoMercado.getTipoempresa().setDescripcion(tipoEmpresa);
            } else if (tipoNuevo == 2) {
                duplicarSueldoMercado.getTipoempresa().setDescripcion(tipoEmpresa);
            }
            for (int i = 0; i < lovTiposEmpresas.size(); i++) {
                if (lovTiposEmpresas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoSueldoMercado.setTipoempresa(lovTiposEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoSueldoMercadoTipoEmpresa");
                } else if (tipoNuevo == 2) {
                    duplicarSueldoMercado.setTipoempresa(lovTiposEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarSueldoMercadoTipoEmpresa");
                }
                lovTiposEmpresas = null;
                getLovTiposEmpresas();
            } else {
                context.update("form:TipoEmpresaDialogo");
                context.execute("TipoEmpresaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoSueldoMercadoTipoEmpresa");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarSueldoMercadoTipoEmpresa");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoCompetenciaCargo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EVALCOMPETENCIA")) {
            if (tipoNuevo == 1) {
                nuevoCompetenciaCargo.getEvalcompetencia().setDescripcion(evalCompetencia);
            } else if (tipoNuevo == 2) {
                duplicarCompetenciaCargo.getEvalcompetencia().setDescripcion(evalCompetencia);
            }
            for (int i = 0; i < lovEvalCompetencias.size(); i++) {
                if (lovEvalCompetencias.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCompetenciaCargo.setEvalcompetencia(lovEvalCompetencias.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCompetenciaCargoEval");
                } else if (tipoNuevo == 2) {
                    duplicarCompetenciaCargo.setEvalcompetencia(lovEvalCompetencias.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCompetenciaCargoEval");
                }
                lovEvalCompetencias = null;
                getLovEvalCompetencias();
            } else {
                context.update("form:EvalCompetenciaDialogo");
                context.execute("EvalCompetenciaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCompetenciaCargoEval");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCompetenciaCargoEval");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoTipoDetalle(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ENFOQUE")) {
            if (tipoNuevo == 1) {
                nuevoTipoDetalle.getEnfoque().setDescripcion(enfoque);
            } else if (tipoNuevo == 2) {
                duplicarTipoDetalle.getEnfoque().setDescripcion(enfoque);
            }
            for (int i = 0; i < lovEnfoques.size(); i++) {
                if (lovEnfoques.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTipoDetalle.setEnfoque(lovEnfoques.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTipoDetalleEnfoque");
                } else if (tipoNuevo == 2) {
                    duplicarTipoDetalle.setEnfoque(lovEnfoques.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoDetalleEnfoque");
                }
                lovEnfoques = null;
                getLovEnfoques();
            } else {
                context.update("form:EnfoqueDialogo");
                context.execute("EnfoqueDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTipoDetalleEnfoque");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoDetalleEnfoque");
                }
            }
        }
    }

    public void actualizarGrupoSalarial() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaCargos.get(index).setGruposalarial(grupoSalarialSeleccionado);
                if (!listCargosCrear.contains(listaCargos.get(index))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(listaCargos.get(index));
                    } else if (!listCargosModificar.contains(listaCargos.get(index))) {
                        listCargosModificar.add(listaCargos.get(index));
                    }
                }
            } else {
                filtrarListaCargos.get(index).setGruposalarial(grupoSalarialSeleccionado);
                if (!listCargosCrear.contains(filtrarListaCargos.get(index))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(filtrarListaCargos.get(index));
                    } else if (!listCargosModificar.contains(filtrarListaCargos.get(index))) {
                        listCargosModificar.add(filtrarListaCargos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexCargo = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCargo");
        } else if (tipoActualizacion == 1) {
            nuevoCargo.setGruposalarial(grupoSalarialSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoCargoGrupoSalarial");
            context.update("formularioDialogos:nuevoCargoSalario");
        } else if (tipoActualizacion == 2) {
            duplicarCargo.setGruposalarial(grupoSalarialSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCargoGrupoSalarial");
            context.update("formularioDialogos:duplicarCargoSalario");
        }
        filtrarLovGruposSalariales = null;
        grupoSalarialSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:GrupoSalarialDialogo");
        context.update("form:lovGrupoSalarial");
        context.update("form:aceptarGS");
        context.execute("GrupoSalarialDialogo.hide()");
    }

    public void cancelarCambioGrupoSalarial() {
        filtrarLovGruposSalariales = null;
        grupoSalarialSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexCargo = true;
    }

    public void actualizarGrupoViatico() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaCargos.get(index).setGrupoviatico(grupoViaticoSeleccionado);
                if (!listCargosCrear.contains(listaCargos.get(index))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(listaCargos.get(index));
                    } else if (!listCargosModificar.contains(listaCargos.get(index))) {
                        listCargosModificar.add(listaCargos.get(index));
                    }
                }
            } else {
                filtrarListaCargos.get(index).setGrupoviatico(grupoViaticoSeleccionado);
                if (!listCargosCrear.contains(filtrarListaCargos.get(index))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(filtrarListaCargos.get(index));
                    } else if (!listCargosModificar.contains(filtrarListaCargos.get(index))) {
                        listCargosModificar.add(filtrarListaCargos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexCargo = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCargo");
        } else if (tipoActualizacion == 1) {
            nuevoCargo.setGrupoviatico(grupoViaticoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoCargoGrupoViatico");
        } else if (tipoActualizacion == 2) {
            duplicarCargo.setGrupoviatico(grupoViaticoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCargoGrupoViatico");
        }
        filtrarLovGruposViaticos = null;
        grupoViaticoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:GrupoViaticoDialogo");
        context.update("form:lovGrupoViatico");
        context.update("form:aceptarGV");
        context.execute("GrupoViaticoDialogo.hide()");
    }

    public void cancelarCambioGrupoViatico() {
        filtrarLovGruposViaticos = null;
        grupoViaticoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexCargo = true;
    }

    public void actualizarProcesoProductivo() {
        System.out.println("procesoProductivoSeleccionado : " + procesoProductivoSeleccionado.getDescripcion());
        System.out.println("tipoActualizacion : " + tipoActualizacion);
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaCargos.get(index).setProcesoproductivo(procesoProductivoSeleccionado);
                if (!listCargosCrear.contains(listaCargos.get(index))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(listaCargos.get(index));
                    } else if (!listCargosModificar.contains(listaCargos.get(index))) {
                        listCargosModificar.add(listaCargos.get(index));
                    }
                }
            } else {
                filtrarListaCargos.get(index).setProcesoproductivo(procesoProductivoSeleccionado);
                if (!listCargosCrear.contains(filtrarListaCargos.get(index))) {
                    if (listCargosModificar.isEmpty()) {
                        listCargosModificar.add(filtrarListaCargos.get(index));
                    } else if (!listCargosModificar.contains(filtrarListaCargos.get(index))) {
                        listCargosModificar.add(filtrarListaCargos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexCargo = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCargo");
        } else if (tipoActualizacion == 1) {
            nuevoCargo.setProcesoproductivo(procesoProductivoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoCargoProcesoProductivo");
        } else if (tipoActualizacion == 2) {
            duplicarCargo.setProcesoproductivo(procesoProductivoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCargoProcesoProductivo");
        }
        filtrarLovProcesosProductivos = null;
        procesoProductivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ProcesoProductivoDialogo");
        context.update("form:lovProcesoProductivo");
        context.update("form:aceptarPP");
        context.execute("ProcesoProductivoDialogo.hide()");
    }

    public void cancelarCambioProcesoProductivo() {
        filtrarLovProcesosProductivos = null;
        procesoProductivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexCargo = true;
    }

    public void actualizarTipoEmpresa() {
        if (tipoActualizacion == 0) {
            if (tipoListaSueldoMercado == 0) {
                listaSueldosMercados.get(indexSueldoMercado).setTipoempresa(tipoEmpresaSeleccionado);
                if (!listSueldosMercadosCrear.contains(listaSueldosMercados.get(indexSueldoMercado))) {
                    if (listSueldosMercadosModificar.isEmpty()) {
                        listSueldosMercadosModificar.add(listaSueldosMercados.get(indexSueldoMercado));
                    } else if (!listSueldosMercadosModificar.contains(listaSueldosMercados.get(indexSueldoMercado))) {
                        listSueldosMercadosModificar.add(listaSueldosMercados.get(indexSueldoMercado));
                    }
                }
            } else {
                filtrarListaSueldosMercados.get(indexSueldoMercado).setTipoempresa(tipoEmpresaSeleccionado);
                if (!listSueldosMercadosCrear.contains(filtrarListaSueldosMercados.get(indexSueldoMercado))) {
                    if (listSueldosMercadosModificar.isEmpty()) {
                        listSueldosMercadosModificar.add(filtrarListaSueldosMercados.get(indexSueldoMercado));
                    } else if (!listSueldosMercadosModificar.contains(filtrarListaSueldosMercados.get(indexSueldoMercado))) {
                        listSueldosMercadosModificar.add(filtrarListaSueldosMercados.get(indexSueldoMercado));
                    }
                }
            }
            if (guardadoSueldoMercado == true) {
                guardadoSueldoMercado = false;
            }
            permitirIndexSueldoMercado = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosSueldoMercado");
        } else if (tipoActualizacion == 1) {
            nuevoSueldoMercado.setTipoempresa(tipoEmpresaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoSueldoMercadoTipoEmpresa");
        } else if (tipoActualizacion == 2) {
            duplicarSueldoMercado.setTipoempresa(tipoEmpresaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarSueldoMercadoTipoEmpresa");
        }
        filtrarLovTiposEmpresas = null;
        tipoEmpresaSeleccionado = null;
        aceptar = true;
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:TipoEmpresaDialogo");
        context.update("form:lovTipoEmpresa");
        context.update("form:aceptarTT");
        context.execute("TipoEmpresaDialogo.hide()");
    }

    public void cancelarCambioTipoEmpresa() {
        filtrarLovTiposEmpresas = null;
        tipoEmpresaSeleccionado = null;
        aceptar = true;
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
        tipoActualizacion = -1;
        permitirCompetencia = true;
    }

    public void actualizarEvalCompetencia() {
        if (tipoActualizacion == 0) {
            if (tipoListaCompetencia == 0) {
                listaCompetenciasCargos.get(indexCompetenciaCargo).setEvalcompetencia(evalCompetenciaSeleccionado);
                if (!listCompetenciasCargosCrear.contains(listaCompetenciasCargos.get(indexCompetenciaCargo))) {
                    if (listCompetenciasCargosModificar.isEmpty()) {
                        listCompetenciasCargosModificar.add(listaCompetenciasCargos.get(indexCompetenciaCargo));
                    } else if (!listCompetenciasCargosModificar.contains(listaCompetenciasCargos.get(indexCompetenciaCargo))) {
                        listCompetenciasCargosModificar.add(listaCompetenciasCargos.get(indexCompetenciaCargo));
                    }
                }
            } else {
                filtrarListaCompetenciasCargos.get(indexCompetenciaCargo).setEvalcompetencia(evalCompetenciaSeleccionado);
                if (!listCompetenciasCargosCrear.contains(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo))) {
                    if (listCompetenciasCargosModificar.isEmpty()) {
                        listCompetenciasCargosModificar.add(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                    } else if (!listCompetenciasCargosModificar.contains(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo))) {
                        listCompetenciasCargosModificar.add(filtrarListaCompetenciasCargos.get(indexCompetenciaCargo));
                    }
                }
            }
            if (guardadoCompetencia == true) {
                guardadoCompetencia = false;
            }
            permitirCompetencia = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCompetenciaCargo");
        } else if (tipoActualizacion == 1) {
            nuevoCompetenciaCargo.setEvalcompetencia(evalCompetenciaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoCompetenciaCargoEval");
        } else if (tipoActualizacion == 2) {
            duplicarCompetenciaCargo.setEvalcompetencia(evalCompetenciaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCompetenciaCargoEval");
        }
        filtrarLovProcesosProductivos = null;
        procesoProductivoSeleccionado = null;
        aceptar = true;
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EvalCompetenciaDialogo");
        context.update("form:lovEvalCompetencia");
        context.update("form:aceptarEC");
        context.execute("EvalCompetenciaDialogo.hide()");
    }

    public void cancelarCambioEvalCompetencia() {
        filtrarLovProcesosProductivos = null;
        procesoProductivoSeleccionado = null;
        aceptar = true;
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
        tipoActualizacion = -1;
        permitirCompetencia = true;
    }

    public void actualizarEnfoque() {
        if (tipoActualizacion == 0) {
            if (tipoListaTipoDetalle == 0) {
                listaTiposDetalles.get(indexTipoDetalle).setEnfoque(enfoqueSeleccionado);
                if (!listTiposDetallesCrear.contains(listaTiposDetalles.get(indexTipoDetalle))) {
                    if (listTiposDetallesModificar.isEmpty()) {
                        listTiposDetallesModificar.add(listaTiposDetalles.get(indexTipoDetalle));
                    } else if (!listTiposDetallesModificar.contains(listaTiposDetalles.get(indexTipoDetalle))) {
                        listTiposDetallesModificar.add(listaTiposDetalles.get(indexTipoDetalle));
                    }
                }
            } else {
                filtrarListaTiposDetalles.get(indexTipoDetalle).setEnfoque(enfoqueSeleccionado);
                if (!listTiposDetallesCrear.contains(filtrarListaTiposDetalles.get(indexTipoDetalle))) {
                    if (listTiposDetallesModificar.isEmpty()) {
                        listTiposDetallesModificar.add(filtrarListaTiposDetalles.get(indexTipoDetalle));
                    } else if (!listTiposDetallesModificar.contains(filtrarListaTiposDetalles.get(indexTipoDetalle))) {
                        listTiposDetallesModificar.add(filtrarListaTiposDetalles.get(indexTipoDetalle));
                    }
                }
            }
            if (guardadoTipoDetalle == true) {
                guardadoTipoDetalle = false;
            }
            permitirIndexTipoDetalle = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosTipoDetalle");
        } else if (tipoActualizacion == 1) {
            nuevoTipoDetalle.setEnfoque(enfoqueSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoTipoDetalleEnfoque");
        } else if (tipoActualizacion == 2) {
            duplicarTipoDetalle.setEnfoque(enfoqueSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTipoDetalleEnfoque");
        }
        lovTiposEmpresas = null;
        tipoEmpresaSeleccionado = null;
        aceptar = true;
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EnfoqueDialogo");
        context.update("form:lovEnfoque");
        context.update("form:aceptarE");
        context.execute("EnfoqueDialogo.hide()");
    }

    public void cancelarCambioEnfoque() {
        lovTiposEmpresas = null;
        tipoEmpresaSeleccionado = null;
        aceptar = true;
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
        tipoActualizacion = -1;
        permitirIndexTipoDetalle = true;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    public String exportXML() {
        if (index >= 0) {
            nombreTabla = ":formExportarC:datosCargoExportar";
            nombreXML = "Cargos_XML";
        }
        if (indexSueldoMercado >= 0) {
            nombreTabla = ":formExportarSM:datosSueldoMercadoExportar";
            nombreXML = "SueldosMercados_XML";
        }
        if (indexCompetenciaCargo >= 0) {
            nombreTabla = ":formExportarCC:datosCompetenciaCargoExportar";
            nombreXML = "CompetenciasCargos_XML";
        }
        if (indexTipoDetalle >= 0) {
            nombreTabla = ":formExportarTD:datosTipoDetalleExportar";
            nombreXML = "PropiedadesCargo_XML";
        }
        return nombreTabla;
    }

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (index >= 0) {
            exportPDF_C();
        }
        if (indexSueldoMercado >= 0) {
            exportPDF_SM();
        }
        if (indexCompetenciaCargo >= 0) {
            exportPDF_CC();
        }
        if (indexTipoDetalle >= 0) {
            exportPDF_TD();
        }
    }

    public void exportPDF_C() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarC:datosCargoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Cargos_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportPDF_SM() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarSM:datosSueldoMercadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "SueldosMercados_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
    }

    public void exportPDF_CC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarCC:datosCompetenciaCargoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CompetenciasCargos_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
    }

    public void exportPDF_TD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTD:datosTipoDetalleExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "PropiedadesCargo_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLS_C();
        }
        if (indexSueldoMercado >= 0) {
            exportXLS_SM();
        }
        if (indexCompetenciaCargo >= 0) {
            exportXLS_CC();
        }
        if (indexTipoDetalle >= 0) {
            exportXLS_TD();
        }
    }

    public void exportXLS_C() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarC:datosCargoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Cargos_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS_SM() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarSM:datosSueldoMercadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "SueldosMercados_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
    }

    public void exportXLS_CC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarCC:datosCompetenciaCargoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CompetenciasCargos_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
    }

    public void exportXLS_TD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTD:datosTipoDetalleExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "PropiedadesCargo_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
        if (indexSueldoMercado >= 0) {
            if (tipoListaSueldoMercado == 0) {
                tipoListaSueldoMercado = 1;
            }
        }
        if (indexCompetenciaCargo >= 0) {
            if (tipoListaCompetencia == 0) {
                tipoListaCompetencia = 1;
            }
        }
        if (indexTipoDetalle >= 0) {
            if (tipoListaTipoDetalle == 0) {
                tipoListaTipoDetalle = 1;
            }
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        int tam = listaCargos.size();
        int tam1 = listaSueldosMercados.size();
        int tam2 = listaCompetenciasCargos.size();
        int tam3 = listaTiposDetalles.size();
        if (tam == 0 || tam1 == 0 || tam2 == 0 || tam3 == 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        } else {
            if (index >= 0) {
                verificarRastroCargo();
                index = -1;
            }
            if (indexSueldoMercado >= 0) {
                verificarRastroSueldoMercado();
                indexSueldoMercado = -1;
            }
            if (indexCompetenciaCargo >= 0) {
                verificarRastroCompetencia();
                indexCompetenciaCargo = -1;
            }
            if (indexTipoDetalle >= 0) {
                verificarRastroTipoDetalle();
                indexTipoDetalle = -1;
            }
        }
    }

    public void verificarRastroCargo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaCargos != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CARGOS");
                backUpSecRegistro = secRegistro;
                backUp = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Cargos";
                    msnConfirmarRastro = "La tabla CARGOS tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("CARGOS")) {
                nombreTablaRastro = "Cargos";
                msnConfirmarRastroHistorico = "La tabla CARGOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroSueldoMercado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaSueldosMercados != null) {
            if (secRegistroSueldoMercado != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroSueldoMercado, "SUELDOSMERCADOS");
                backUpSecRegistroSueldoMercado = secRegistroSueldoMercado;
                backUp = secRegistroSueldoMercado;
                secRegistroSueldoMercado = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "SueldosMercados";
                    msnConfirmarRastro = "La tabla SUELDOSMERCADOS tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("SUELDOSMERCADOS")) {
                nombreTablaRastro = "SueldosMercados";
                msnConfirmarRastroHistorico = "La tabla SUELDOSMERCADOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexSueldoMercado = -1;
    }

    public void verificarRastroCompetencia() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaCompetenciasCargos != null) {
            if (secRegistroCompetencia != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroCompetencia, "COMPETENCIASCARGOS");
                backUpSecRegistroCompetencia = secRegistroCompetencia;
                backUp = backUpSecRegistroCompetencia;
                secRegistroCompetencia = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Competenciascargos";
                    msnConfirmarRastro = "La tabla COMPETENCIASCARGOS tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("COMPETENCIASCARGOS")) {
                nombreTablaRastro = "Competenciascargos";
                msnConfirmarRastroHistorico = "La tabla COMPETENCIASCARGOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexCompetenciaCargo = -1;
    }

    public void verificarRastroTipoDetalle() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaTiposDetalles != null) {
            if (secRegistroTipoDetalle != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroTipoDetalle, "TIPOSDETALLES");
                backUpSecRegistroTipoDetalle = secRegistroTipoDetalle;
                backUp = secRegistroTipoDetalle;
                secRegistroTipoDetalle = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "TiposDetalles";
                    msnConfirmarRastro = "La tabla TIPOSDETALLES tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSDETALLES")) {
                nombreTablaRastro = "TiposDetalles";
                msnConfirmarRastroHistorico = "La tabla TIPOSDETALLES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexTipoDetalle = -1;
    }

    public void lovEmpresas() {
        if (cambiosPagina == true) {
            index = -1;
            secRegistro = null;
            indexCompetenciaCargo = -1;
            secRegistroCompetencia = null;
            indexSueldoMercado = -1;
            secRegistroSueldoMercado = null;
            indexTipoDetalle = -1;
            secRegistroTipoDetalle = null;
            cualCelda = -1;
            RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void actualizarEmpresa() {
        indexAux = -1;
        indexAuxTipoDetalle = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:nombreEmpresa");
        context.update("form:nitEmpresa");
        filtrarListaEmpresas = null;
        aceptar = true;
        context.execute("EmpresasDialogo.hide()");
        context.reset("form:lovEmpresas:globalFilter");
        context.update("form:lovEmpresas");
        context.update("form:aceptarEM");
        empresaActual = empresaSeleccionada;
        listaCargos = null;
        listaCompetenciasCargos = null;
        listaSueldosMercados = null;
        getListaCargos();
        int tam2 = listaCargos.size();
        if (tam2 > 0) {
            index = 0;
            listaSueldosMercados = null;
            getListaSueldosMercados();
            listaCompetenciasCargos = null;
            getListaCompetenciasCargos();
        }
        activoDetalleCargo = true;
        listaTiposDetalles = null;
        getListaTiposDetalles();
        detalleCargo = new DetallesCargos();
        legendDetalleCargo = "";
        context.update("form:legendDetalleCargo");
        context.update("form:detalleCargo");
        context.update("form:datosSueldoMercado");
        context.update("form:datosCargo");
        context.update("form:datosCompetenciaCargo");
        context.update("form:datosTipoDetalle");
    }

    public void cancelarCambioEmpresa() {
        index = -1;
        secRegistro = null;
        indexCompetenciaCargo = -1;
        secRegistroCompetencia = null;
        indexSueldoMercado = -1;
        secRegistroSueldoMercado = null;
        indexTipoDetalle = -1;
        secRegistroTipoDetalle = null;
        cualCelda = -1;
        filtrarListaEmpresas = null;
        empresaSeleccionada = new Empresas();
    }

    public void validarExistenciaInformacionDetalleCargo() {
        if (guardado == true && guardadoTipoDetalle == true) {
            if (detalleCargo.getSecuencia() == null) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("ingresoNuevoDetalleCargo.show()");
            } else {
                index = -1;
                indexCompetenciaCargo = -1;
                indexSueldoMercado = -1;
                indexTipoDetalle = -1;
                indexDetalleCargo = 1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void crearDetalleCargo() {
        try {
            k++;
            l = BigInteger.valueOf(k);
            detalleCargo = new DetallesCargos();
            detalleCargo.setSecuencia(l);
            detalleCargo.setDescripcion(" ");
            if (tipoLista == 0) {
                detalleCargo.setCargo(listaCargos.get(indexAux));
            }
            if (tipoLista == 1) {
                detalleCargo.setCargo(filtrarListaCargos.get(indexAux));
            }
            if (tipoListaTipoDetalle == 0) {
                detalleCargo.setTipodetalle(listaTiposDetalles.get(indexAuxTipoDetalle));
            }
            if (tipoListaTipoDetalle == 1) {
                detalleCargo.setTipodetalle(filtrarListaTiposDetalles.get(indexAuxTipoDetalle));
            }
            administrarCargos.crearDetalleCargo(detalleCargo);
            FacesMessage msg = new FacesMessage("Información", "El registro fue creado con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            detalleCargo = null;
            getDetalleCargo();
            
        } catch (Exception e) {
            System.out.println("Error crearDetalleCargo : " + e.toString());
        }
    }

    public void modificacionesDetalleCargo() {
        if (guardadoDetalleCargo == true) {
            guardadoDetalleCargo = false;
        }
        if (borrarDetalleCargo == true) {
            borrarDetalleCargo = false;
        }
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        System.out.println("Entro a modificar datos : " + detalleCargo.getDescripcion());
    }

    //GETTERS AND SETTERS
    public List<Cargos> getListaCargos() {
        try {
            if (listaCargos == null) {
                listaCargos = new ArrayList<Cargos>();
                listaCargos = administrarCargos.listaCargosParaEmpresa(empresaActual.getSecuencia());
                if (listaCargos != null) {
                    for (int i = 0; i < listaCargos.size(); i++) {
                        if (listaCargos.get(i).getGruposalarial() == null) {
                            listaCargos.get(i).setGruposalarial(new GruposSalariales());
                        }
                        if (listaCargos.get(i).getGrupoviatico() == null) {
                            listaCargos.get(i).setGrupoviatico(new GruposViaticos());
                        }
                        if (listaCargos.get(i).getProcesoproductivo() == null) {
                            listaCargos.get(i).setProcesoproductivo(new ProcesosProductivos());
                        }
                    }
                }
            }

            return listaCargos;

        } catch (Exception e) {
            System.out.println("Error...!! getListaCargos " + e.toString());
            return null;
        }
    }

    public void setListaCargos(List<Cargos> setListaTiposSueldos) {
        this.listaCargos = setListaTiposSueldos;
    }

    public List<Cargos> getFiltrarListaCargos() {
        return filtrarListaCargos;
    }

    public void setFiltrarListaCargos(List<Cargos> setFiltrarListaTiposSueldos) {
        this.filtrarListaCargos = setFiltrarListaTiposSueldos;
    }

    public Cargos getNuevoCargo() {
        return nuevoCargo;
    }

    public void setNuevoCargo(Cargos setNuevoTipoSueldo) {
        this.nuevoCargo = setNuevoTipoSueldo;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Cargos getEditarCargo() {
        return editarCargo;
    }

    public void setEditarCargo(Cargos setEditarTipoSueldo) {
        this.editarCargo = setEditarTipoSueldo;
    }

    public Cargos getDuplicarCargo() {
        return duplicarCargo;
    }

    public void setDuplicarCargo(Cargos setDuplicarTipoSueldo) {
        this.duplicarCargo = setDuplicarTipoSueldo;
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

    public void setBackUpSecRegistro(BigInteger BackUpSecRegistro) {
        this.backUpSecRegistro = BackUpSecRegistro;
    }

    public List<SueldosMercados> getListaSueldosMercados() {
        if (listaSueldosMercados == null) {
            listaSueldosMercados = new ArrayList<SueldosMercados>();
            if (index == -1) {
                index = indexAux;
            }
            if (tipoLista == 0) {
                if (listaCargos.size() > 0) {
                    listaSueldosMercados = administrarCargos.listaSueldosMercadosParaCargo(listaCargos.get(index).getSecuencia());
                }
            }
            if (tipoLista == 1) {
                if (filtrarListaCargos.size() > 0) {
                    listaSueldosMercados = administrarCargos.listaSueldosMercadosParaCargo(filtrarListaCargos.get(index).getSecuencia());
                }
            }
            if (listaSueldosMercados != null) {
                for (int i = 0; i < listaSueldosMercados.size(); i++) {
                    if (listaSueldosMercados.get(i).getTipoempresa() == null) {
                        listaSueldosMercados.get(i).setTipoempresa(new TiposEmpresas());
                    }
                }
            }
        }
        return listaSueldosMercados;
    }

    public void setListaSueldosMercados(List<SueldosMercados> setListaTSFormulasConceptos) {
        this.listaSueldosMercados = setListaTSFormulasConceptos;
    }

    public List<SueldosMercados> getFiltrarListaSueldosMercados() {
        return filtrarListaSueldosMercados;
    }

    public void setFiltrarListaSueldosMercados(List<SueldosMercados> setFiltrarListaTSFormulasConceptos) {
        this.filtrarListaSueldosMercados = setFiltrarListaTSFormulasConceptos;
    }

    public List<Cargos> getListCargosModificar() {
        return listCargosModificar;
    }

    public void setListCargosModificar(List<Cargos> setListTiposSueldosModificar) {
        this.listCargosModificar = setListTiposSueldosModificar;
    }

    public List<Cargos> getListCargosCrear() {
        return listCargosCrear;
    }

    public void setListCargosCrear(List<Cargos> setListTiposSueldosCrear) {
        this.listCargosCrear = setListTiposSueldosCrear;
    }

    public List<Cargos> getListCargosBorrar() {
        return listCargosBorrar;
    }

    public void setListCargosBorrar(List<Cargos> setListTiposSueldosBorrar) {
        this.listCargosBorrar = setListTiposSueldosBorrar;
    }

    public List<SueldosMercados> getListSueldosMercadosModificar() {
        return listSueldosMercadosModificar;
    }

    public void setListSueldosMercadosModificar(List<SueldosMercados> setListTSFormulasConceptosModificar) {
        this.listSueldosMercadosModificar = setListTSFormulasConceptosModificar;
    }

    public SueldosMercados getNuevoSueldoMercado() {
        return nuevoSueldoMercado;
    }

    public void setNuevoSueldoMercado(SueldosMercados setNuevoTSFormulaConcepto) {
        this.nuevoSueldoMercado = setNuevoTSFormulaConcepto;
    }

    public List<SueldosMercados> getListSueldosMercadosCrear() {
        return listSueldosMercadosCrear;
    }

    public void setListSueldosMercadosCrear(List<SueldosMercados> setListTSFormulasConceptosCrear) {
        this.listSueldosMercadosCrear = setListTSFormulasConceptosCrear;
    }

    public List<SueldosMercados> getListSueldosMercadosBorrar() {
        return listSueldosMercadosBorrar;
    }

    public void setListSueldosMercadosBorrar(List<SueldosMercados> setListTSFormulasConceptosBorrar) {
        this.listSueldosMercadosBorrar = setListTSFormulasConceptosBorrar;
    }

    public SueldosMercados getEditarSueldoMercado() {
        return editarSueldoMercado;
    }

    public void setEditarSueldoMercado(SueldosMercados setEditarTSFormulaConcepto) {
        this.editarSueldoMercado = setEditarTSFormulaConcepto;
    }

    public SueldosMercados getDuplicarSueldoMercado() {
        return duplicarSueldoMercado;
    }

    public void setDuplicarSueldoMercado(SueldosMercados setDuplicarTSFormulaConcepto) {
        this.duplicarSueldoMercado = setDuplicarTSFormulaConcepto;
    }

    public BigInteger getSecRegistroSueldoMercado() {
        return secRegistroSueldoMercado;
    }

    public void setSecRegistroSueldoMercado(BigInteger setSecRegistroTSFormulas) {
        this.secRegistroSueldoMercado = setSecRegistroTSFormulas;
    }

    public BigInteger getBackUpSecRegistroSueldoMercado() {
        return backUpSecRegistroSueldoMercado;
    }

    public void setBackUpSecRegistroSueldoMercado(BigInteger setBackUpSecRegistroTSFormulas) {
        this.backUpSecRegistroSueldoMercado = setBackUpSecRegistroTSFormulas;
    }

    public String getMsnConfirmarRastro() {
        return msnConfirmarRastro;
    }

    public void setMsnConfirmarRastro(String msnConfirmarRastro) {
        this.msnConfirmarRastro = msnConfirmarRastro;
    }

    public String getMsnConfirmarRastroHistorico() {
        return msnConfirmarRastroHistorico;
    }

    public void setMsnConfirmarRastroHistorico(String msnConfirmarRastroHistorico) {
        this.msnConfirmarRastroHistorico = msnConfirmarRastroHistorico;
    }

    public BigInteger getBackUp() {
        return backUp;
    }

    public void setBackUp(BigInteger backUp) {
        this.backUp = backUp;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String setNombreTabla) {
        this.nombreTabla = setNombreTabla;
    }

    public List<GruposSalariales> getLovGruposSalariales() {
        if (lovGruposSalariales == null) {
            lovGruposSalariales = administrarCargos.lovGruposSalariales();
        }
        return lovGruposSalariales;
    }

    public void setLovGruposSalariales(List<GruposSalariales> setLovFormulas) {
        this.lovGruposSalariales = setLovFormulas;
    }

    public List<GruposSalariales> getFiltrarLovGruposSalariales() {
        return filtrarLovGruposSalariales;
    }

    public void setFiltrarLovGruposSalariales(List<GruposSalariales> setFiltrarLovFormulas) {
        this.filtrarLovGruposSalariales = setFiltrarLovFormulas;
    }

    public GruposSalariales getGrupoSalarialSeleccionado() {
        return grupoSalarialSeleccionado;
    }

    public void setGrupoSalarialSeleccionado(GruposSalariales setFormulaSeleccionado) {
        this.grupoSalarialSeleccionado = setFormulaSeleccionado;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

    public String getAltoTablaCargo() {
        return altoTablaCargo;
    }

    public void setAltoTablaCargo(String setAltoTablaTiposSueldos) {
        this.altoTablaCargo = setAltoTablaTiposSueldos;
    }

    public String getAltoTablaSueldoMercado() {
        return altoTablaSueldoMercado;
    }

    public void setAltoTablaSueldoMercado(String setAltoTablaTSFormulas) {
        this.altoTablaSueldoMercado = setAltoTablaTSFormulas;
    }

    public List<GruposViaticos> getLovGruposViaticos() {
        if (lovGruposViaticos == null) {
            lovGruposViaticos = administrarCargos.lovGruposViaticos();
        }
        return lovGruposViaticos;
    }

    public void setLovGruposViaticos(List<GruposViaticos> lovConceptos) {
        this.lovGruposViaticos = lovConceptos;
    }

    public List<GruposViaticos> getFiltrarLovGruposViaticos() {
        return filtrarLovGruposViaticos;
    }

    public void setFiltrarLovGruposViaticos(List<GruposViaticos> filtrarLovConceptos) {
        this.filtrarLovGruposViaticos = filtrarLovConceptos;
    }

    public GruposViaticos getGrupoViaticoSeleccionado() {
        return grupoViaticoSeleccionado;
    }

    public void setGrupoViaticoSeleccionado(GruposViaticos conceptoSeleccionado) {
        this.grupoViaticoSeleccionado = conceptoSeleccionado;
    }

    public List<Competenciascargos> getListaCompetenciasCargos() {
        if (listaCompetenciasCargos == null) {
            listaCompetenciasCargos = new ArrayList<Competenciascargos>();
            if (index == -1) {
                index = indexAux;
            }
            if (tipoLista == 0) {
                if (listaCargos.size() > 0) {
                    listaCompetenciasCargos = administrarCargos.listaCompetenciasCargosParaCargo(listaCargos.get(index).getSecuencia());
                }
            }
            if (tipoLista == 1) {
                if (filtrarListaCargos.size() > 0) {
                    listaCompetenciasCargos = administrarCargos.listaCompetenciasCargosParaCargo(filtrarListaCargos.get(index).getSecuencia());
                }
            }
            if (listaCompetenciasCargos != null) {
                for (int i = 0; i < listaCompetenciasCargos.size(); i++) {
                    if (listaCompetenciasCargos.get(i).getEvalcompetencia() == null) {
                        listaCompetenciasCargos.get(i).setEvalcompetencia(new EvalCompetencias());
                    }
                }
            }
        }
        return listaCompetenciasCargos;
    }

    public void setListaCompetenciasCargos(List<Competenciascargos> listaTSGruposTiposEntidades) {
        this.listaCompetenciasCargos = listaTSGruposTiposEntidades;
    }

    public List<Competenciascargos> getFiltrarListaCompetenciasCargos() {
        return filtrarListaCompetenciasCargos;
    }

    public void setFiltrarListaCompetenciasCargos(List<Competenciascargos> filtrarListaTSGruposTiposEntidades) {
        this.filtrarListaCompetenciasCargos = filtrarListaTSGruposTiposEntidades;
    }

    public List<Competenciascargos> getListCompetenciasCargosModificar() {
        return listCompetenciasCargosModificar;
    }

    public void setListCompetenciasCargosModificar(List<Competenciascargos> listTSGruposTiposEntidadesModificar) {
        this.listCompetenciasCargosModificar = listTSGruposTiposEntidadesModificar;
    }

    public Competenciascargos getNuevoCompetenciaCargo() {
        return nuevoCompetenciaCargo;
    }

    public void setNuevoCompetenciaCargo(Competenciascargos nuevoTSGrupoTipoEntidad) {
        this.nuevoCompetenciaCargo = nuevoTSGrupoTipoEntidad;
    }

    public List<Competenciascargos> getListCompetenciasCargosCrear() {
        return listCompetenciasCargosCrear;
    }

    public void setListCompetenciasCargosCrear(List<Competenciascargos> listTSGruposTiposEntidadessCrear) {
        this.listCompetenciasCargosCrear = listTSGruposTiposEntidadessCrear;
    }

    public List<Competenciascargos> getListCompetenciasCargosBorrar() {
        return listCompetenciasCargosBorrar;
    }

    public void setListCompetenciasCargosBorrar(List<Competenciascargos> listTSGruposTiposEntidadesBorrar) {
        this.listCompetenciasCargosBorrar = listTSGruposTiposEntidadesBorrar;
    }

    public Competenciascargos getEditarCompetenciaCargo() {
        return editarCompetenciaCargo;
    }

    public void setEditarCompetenciaCargo(Competenciascargos editarTSGrupoTipoEntidad) {
        this.editarCompetenciaCargo = editarTSGrupoTipoEntidad;
    }

    public Competenciascargos getDuplicarCompetenciaCargo() {
        return duplicarCompetenciaCargo;
    }

    public void setDuplicarCompetenciaCargo(Competenciascargos duplicarTSGrupoTipoEntidad) {
        this.duplicarCompetenciaCargo = duplicarTSGrupoTipoEntidad;
    }

    public BigInteger getSecRegistroCompetencia() {
        return secRegistroCompetencia;
    }

    public void setSecRegistroCompetencia(BigInteger secRegistroTSGrupos) {
        this.secRegistroCompetencia = secRegistroTSGrupos;
    }

    public BigInteger getBackUpSecRegistroCompetencia() {
        return backUpSecRegistroCompetencia;
    }

    public void setBackUpSecRegistroCompetencia(BigInteger backUpSecRegistroTSGrupos) {
        this.backUpSecRegistroCompetencia = backUpSecRegistroTSGrupos;
    }

    public List<ProcesosProductivos> getLovProcesosProductivos() {
        if (lovProcesosProductivos == null) {
            lovProcesosProductivos = administrarCargos.lovProcesosProductivos();
        }
        return lovProcesosProductivos;
    }

    public void setLovProcesosProductivos(List<ProcesosProductivos> lovGruposTiposEntidades) {
        this.lovProcesosProductivos = lovGruposTiposEntidades;
    }

    public List<ProcesosProductivos> getFiltrarLovProcesosProductivos() {
        return filtrarLovProcesosProductivos;
    }

    public void setFiltrarLovProcesosProductivos(List<ProcesosProductivos> filtrarLovGruposTiposEntidades) {
        this.filtrarLovProcesosProductivos = filtrarLovGruposTiposEntidades;
    }

    public ProcesosProductivos getProcesoProductivoSeleccionado() {
        return procesoProductivoSeleccionado;
    }

    public void setProcesoProductivoSeleccionado(ProcesosProductivos setProcesoProductivoSeleccionado) {
        this.procesoProductivoSeleccionado = setProcesoProductivoSeleccionado;
    }

    public String getAltoTablaCompetencia() {
        return altoTablaCompetencia;
    }

    public void setAltoTablaCompetencia(String altoTablaTSGrupos) {
        this.altoTablaCompetencia = altoTablaTSGrupos;
    }

    public List<TiposDetalles> getListaTiposDetalles() {
        if (listaTiposDetalles == null) {
            listaTiposDetalles = new ArrayList<TiposDetalles>();
            listaTiposDetalles = administrarCargos.listaTiposDetalles();
            if (listaTiposDetalles != null) {
                for (int i = 0; i < listaTiposDetalles.size(); i++) {
                    if (listaTiposDetalles.get(i).getEnfoque() == null) {
                        listaTiposDetalles.get(i).setEnfoque(new Enfoques());
                    }
                }
            }
        }
        return listaTiposDetalles;
    }

    public void setListaTiposDetalles(List<TiposDetalles> listaTEFormulasConceptos) {
        this.listaTiposDetalles = listaTEFormulasConceptos;
    }

    public List<TiposDetalles> getFiltrarListaTiposDetalles() {
        return filtrarListaTiposDetalles;
    }

    public void setFiltrarListaTiposDetalles(List<TiposDetalles> filtrarListaTEFormulasConceptos) {
        this.filtrarListaTiposDetalles = filtrarListaTEFormulasConceptos;
    }

    public List<TiposDetalles> getListTiposDetallesModificar() {
        return listTiposDetallesModificar;
    }

    public void setListTiposDetallesModificar(List<TiposDetalles> listTEFormulasConceptosModificar) {
        this.listTiposDetallesModificar = listTEFormulasConceptosModificar;
    }

    public TiposDetalles getNuevoTipoDetalle() {
        return nuevoTipoDetalle;
    }

    public void setNuevoTipoDetalle(TiposDetalles nuevoTEFormulaConcepto) {
        this.nuevoTipoDetalle = nuevoTEFormulaConcepto;
    }

    public List<TiposDetalles> getListTiposDetallesCrear() {
        return listTiposDetallesCrear;
    }

    public void setListTiposDetallesCrear(List<TiposDetalles> listTEFormulasConceptosCrear) {
        this.listTiposDetallesCrear = listTEFormulasConceptosCrear;
    }

    public List<TiposDetalles> getListTiposDetallesBorrar() {
        return listTiposDetallesBorrar;
    }

    public void setListTiposDetallesBorrar(List<TiposDetalles> listTEFormulasConceptosBorrar) {
        this.listTiposDetallesBorrar = listTEFormulasConceptosBorrar;
    }

    public TiposDetalles getEditarTipoDetalle() {
        return editarTipoDetalle;
    }

    public void setEditarTipoDetalle(TiposDetalles editarTEFormulaConcepto) {
        this.editarTipoDetalle = editarTEFormulaConcepto;
    }

    public TiposDetalles getDuplicarTipoDetalle() {
        return duplicarTipoDetalle;
    }

    public void setDuplicarTipoDetalle(TiposDetalles duplicarTEFormulaConcepto) {
        this.duplicarTipoDetalle = duplicarTEFormulaConcepto;
    }

    public BigInteger getSecRegistroTipoDetalle() {
        return secRegistroTipoDetalle;
    }

    public void setSecRegistroTipoDetalle(BigInteger secRegistroTEFormulas) {
        this.secRegistroTipoDetalle = secRegistroTEFormulas;
    }

    public BigInteger getBackUpSecRegistroTipoDetalle() {
        return backUpSecRegistroTipoDetalle;
    }

    public void setBackUpSecRegistroTipoDetalle(BigInteger backUpSecRegistroTEFormulas) {
        this.backUpSecRegistroTipoDetalle = backUpSecRegistroTEFormulas;
    }

    public List<TiposEmpresas> getLovTiposEmpresas() {
        if (lovTiposEmpresas == null) {
            lovTiposEmpresas = administrarCargos.lovTiposEmpresas();
        }
        return lovTiposEmpresas;
    }

    public void setLoviposEmpresas(List<TiposEmpresas> lovTiposEntidades) {
        this.lovTiposEmpresas = lovTiposEntidades;
    }

    public List<TiposEmpresas> getFiltrarLovTiposEmpresas() {
        return filtrarLovTiposEmpresas;
    }

    public void setFiltrarLovTiposEmpresas(List<TiposEmpresas> filtrarLovTiposEntidades) {
        this.filtrarLovTiposEmpresas = filtrarLovTiposEntidades;
    }

    public TiposEmpresas getTipoEmpresaSeleccionado() {
        return tipoEmpresaSeleccionado;
    }

    public void setTipoEmpresaSeleccionado(TiposEmpresas tipoEntidadSeleccionado) {
        this.tipoEmpresaSeleccionado = tipoEntidadSeleccionado;
    }

    public String getAltoTablaTipoDetalle() {
        return altoTablaTipoDetalle;
    }

    public void setAltoTablaTipoDetalle(String altoTablaTEFormulas) {
        this.altoTablaTipoDetalle = altoTablaTEFormulas;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public boolean isActivoSueldoMercado() {
        return activoSueldoMercado;
    }

    public void setActivoSueldoMercado(boolean activoFormulaConcepto) {
        this.activoSueldoMercado = activoFormulaConcepto;
    }

    public boolean isActivoCompetencia() {
        return activoCompetencia;
    }

    public void setActivoCompetencia(boolean activoGrupoDistribucion) {
        this.activoCompetencia = activoGrupoDistribucion;
    }

    public boolean isActivoTipoDetalle() {
        return activoTipoDetalle;
    }

    public void setActivoTipoDetalle(boolean activoTipoEntidad) {
        this.activoTipoDetalle = activoTipoEntidad;
    }

    public List<EvalCompetencias> getLovEvalCompetencias() {
        if (lovEvalCompetencias == null) {
            lovEvalCompetencias = administrarCargos.lovEvalCompetencias();
        }
        return lovEvalCompetencias;
    }

    public void setLovEvalCompetencias(List<EvalCompetencias> lovEvalCompetencias) {
        this.lovEvalCompetencias = lovEvalCompetencias;
    }

    public List<EvalCompetencias> getFiltrarLovEvalCompetencias() {
        return filtrarLovEvalCompetencias;
    }

    public void setFiltrarLovEvalCompetencias(List<EvalCompetencias> filtrarLovEvalCompetencias) {
        this.filtrarLovEvalCompetencias = filtrarLovEvalCompetencias;
    }

    public EvalCompetencias getEvalCompetenciaSeleccionado() {
        return evalCompetenciaSeleccionado;
    }

    public void setEvalCompetenciaSeleccionado(EvalCompetencias evalCompetenciaSeleccionado) {
        this.evalCompetenciaSeleccionado = evalCompetenciaSeleccionado;
    }

    public List<Enfoques> getLovEnfoques() {
        if (lovEnfoques == null) {
            lovEnfoques = administrarCargos.lovEnfoques();
        }
        return lovEnfoques;
    }

    public void setLovEnfoques(List<Enfoques> lovEnfoques) {
        this.lovEnfoques = lovEnfoques;
    }

    public List<Enfoques> getFiltrarLovEnfoques() {
        return filtrarLovEnfoques;
    }

    public void setFiltrarLovEnfoques(List<Enfoques> filtrarLovEnfoques) {
        this.filtrarLovEnfoques = filtrarLovEnfoques;
    }

    public Enfoques getEnfoqueSeleccionado() {
        return enfoqueSeleccionado;
    }

    public void setEnfoqueSeleccionado(Enfoques enfoqueSeleccionado) {
        this.enfoqueSeleccionado = enfoqueSeleccionado;
    }

    public Empresas getEmpresaActual() {
        return empresaActual;
    }

    public void setEmpresaActual(Empresas empresaActual) {
        this.empresaActual = empresaActual;
    }

    public List<Empresas> getListaEmpresas() {
        if (listaEmpresas == null) {
            listaEmpresas = new ArrayList<Empresas>();
            listaEmpresas = administrarCargos.listaEmpresas();
        }
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public List<Empresas> getFiltrarListaEmpresas() {
        return filtrarListaEmpresas;
    }

    public void setFiltrarListaEmpresas(List<Empresas> filtrarListaEmpresas) {
        this.filtrarListaEmpresas = filtrarListaEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public String getLegendDetalleCargo() {
        return legendDetalleCargo;
    }

    public void setLegendDetalleCargo(String legendDetalleCargo) {
        this.legendDetalleCargo = legendDetalleCargo;
    }

    public DetallesCargos getDetalleCargo() {
        if (detalleCargo == null) {
            detalleCargo = new DetallesCargos();
            if (indexAux >= 0 && indexAuxTipoDetalle >= 0) {
                activoDetalleCargo = false;
                Cargos actualC = null;
                if (tipoLista == 0) {
                    actualC = listaCargos.get(indexAux);
                }
                if (tipoLista == 1) {
                    actualC = filtrarListaCargos.get(indexAux);
                }
                TiposDetalles actualTD = null;
                if (tipoListaTipoDetalle == 0) {
                    actualTD = listaTiposDetalles.get(indexAuxTipoDetalle);
                }
                if (tipoListaTipoDetalle == 1) {
                    actualTD = filtrarListaTiposDetalles.get(indexAuxTipoDetalle);
                }
                detalleCargo = administrarCargos.detalleDelCargo(actualTD.getSecuencia(), actualC.getSecuencia());
            }
            if (detalleCargo == null) {
                detalleCargo = new DetallesCargos();
            }
        }
        return detalleCargo;
    }

    public void setDetalleCargo(DetallesCargos detalleCargo) {
        this.detalleCargo = detalleCargo;
    }

    public boolean isActivoDetalleCargo() {
        return activoDetalleCargo;
    }

    public void setActivoDetalleCargo(boolean activoDetalleCargo) {
        this.activoDetalleCargo = activoDetalleCargo;
    }

}

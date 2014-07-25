/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Administrar.AdministrarCausasAusentismos;
import Entidades.Causasausentismos;
import Entidades.Clasesausentismos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCausasAusentismosInterface;
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
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlCausasAusentismos implements Serializable {

    @EJB
    AdministrarCausasAusentismosInterface administrarCausasAusentismos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<Causasausentismos> listaCausasAusentismos;
    private List<Causasausentismos> filtrarCausasAusentismos;
    private List<Causasausentismos> listaCausasAusentismosCrear;
    private String mensajeValidacion;
    private List<Causasausentismos> listaCausasAusentismosModificar;
    private List<Causasausentismos> listaCausasAusentismosBorrar;
    //LISTA DE VALORES DE CLASES AUSENTISMOS FALTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private List<Clasesausentismos> lovClasesAusentismos;
    private List<Clasesausentismos> lovFiltradoClasesAusentismos;
    private Clasesausentismos clasesAusentismosSeleccionado;
    //NUEVO, DUPLICADO EDITAR Y SLECCIONADA
    private Causasausentismos nuevaCausasAusentismos;
    private Causasausentismos duplicarCausasAusentismos;
    private Causasausentismos editarCausasAusentismos;
    private Causasausentismos causasAusentismoSeleccionado;
    private BigInteger secRegistro;
    public String altoTabla;
    public String infoRegistroClasesausentismos;
    //AutoCompletar
    private boolean permitirIndex;
    private String claseAusentismo;
    //Tabla a Imprimir
    private String tablaImprimir, nombreArchivo;
    private Column Codigo, Descripcion, Clase ,OrigenIncapacidad, PorcentajeLiquidacion, RestaDiasIncapacidad, FormaLiquidacion,
            Remunerada, DescuentaTiempoContinuo, DescuentaTiempoSoloPS, GarantizaBaseSalario;
    public String infoRegistro;
    ///////////////////////////////////////////////
    //////////PRUEBAS UNITARIAS COMPONENTES////////
    ///////////////////////////////////////////////
    public String buscarNombre;
    public boolean buscador;
    public String paginaAnterior;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //RASTRO

    public ControlCausasAusentismos() {

        permitirIndex = true;
        aceptar = true;
        tipoLista = 0;
        listaCausasAusentismos = null;
        listaCausasAusentismosCrear = new ArrayList<Causasausentismos>();
        listaCausasAusentismosModificar = new ArrayList<Causasausentismos>();
        listaCausasAusentismosBorrar = new ArrayList<Causasausentismos>();
        lovClasesAusentismos = new ArrayList<Clasesausentismos>();
        
        cualCelda = -1;
        tipoLista = 0;
        nuevaCausasAusentismos = new Causasausentismos();
        nuevaCausasAusentismos.setClase(new Clasesausentismos());
        secRegistro = null;
        k = 0;
        altoTabla = "270";
        guardado = true;
        buscador = false;
        tablaImprimir = ":formExportar:datosCausasAusentismosExportar";
        nombreArchivo = "CausasAusentismosXML";

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarCausasAusentismos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            getListaCausasAusentismos();
            if (listaCausasAusentismos != null) {
                infoRegistro = "Cantidad de registros : " + listaCausasAusentismos.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
        public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
    }

    public String redirigir() {
        return paginaAnterior;
    }
    
    public void activarAceptar() {
        aceptar = false;
    }
    //ACTIVAR F11
    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            System.out.println("Activar");
            Codigo = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:codigo");
            Codigo.setFilterStyle("");
            Descripcion = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:descripcion");
            Descripcion.setFilterStyle("");
            Clase = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:clase");
            Clase.setFilterStyle("");
            OrigenIncapacidad = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:origenIncapacidad");
            OrigenIncapacidad.setFilterStyle("");
            PorcentajeLiquidacion = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:porcentajeLiquidacion");
            PorcentajeLiquidacion.setFilterStyle("");
            RestaDiasIncapacidad = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:restaDiasIncapacidad");
            RestaDiasIncapacidad.setFilterStyle("");
            FormaLiquidacion = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:formaLiquidacion");
            FormaLiquidacion.setFilterStyle("");
            Remunerada = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:remunerada");
            Remunerada.setFilterStyle("");
            DescuentaTiempoContinuo = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:descuentaTiempoContinuo");
            DescuentaTiempoContinuo.setFilterStyle("");
            DescuentaTiempoSoloPS = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:descuentaTiempoSoloPS");
            DescuentaTiempoSoloPS.setFilterStyle("");
            GarantizaBaseSalario = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:garantizaBaseSalario");
            GarantizaBaseSalario.setFilterStyle("");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosCausasAusentismos");
            bandera = 1;
            tipoLista = 1;
            System.out.println("TipoLista= " + tipoLista);
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            Codigo = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:codigo");
            Codigo.setFilterStyle("display: none; visibility: hidden;");
            Descripcion = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:descripcion");
            Descripcion.setFilterStyle("display: none; visibility: hidden;");
            Clase = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:clase");
            Clase.setFilterStyle("display: none; visibility: hidden;");
            OrigenIncapacidad = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:origenIncapacidad");
            OrigenIncapacidad.setFilterStyle("display: none; visibility: hidden;");
            PorcentajeLiquidacion = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:porcentajeLiquidacion");
            PorcentajeLiquidacion.setFilterStyle("display: none; visibility: hidden;");
            RestaDiasIncapacidad = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:restaDiasIncapacidad");
            RestaDiasIncapacidad.setFilterStyle("display: none; visibility: hidden;");
            FormaLiquidacion = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:formaLiquidacion");
            FormaLiquidacion.setFilterStyle("display: none; visibility: hidden;");
            Remunerada = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:remunerada");
            Remunerada.setFilterStyle("display: none; visibility: hidden;");
            DescuentaTiempoContinuo = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:descuentaTiempoContinuo");
            DescuentaTiempoContinuo.setFilterStyle("display: none; visibility: hidden;");
            DescuentaTiempoSoloPS = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:descuentaTiempoSoloPS");
            DescuentaTiempoSoloPS.setFilterStyle("display: none; visibility: hidden;");
            GarantizaBaseSalario = (Column) c.getViewRoot().findComponent("form:datosCausasAusentismos:garantizaBaseSalario");
            GarantizaBaseSalario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCausasAusentismos");
            altoTabla = "270";
            bandera = 0;
            filtrarCausasAusentismos = null;
            tipoLista = 0;
            System.out.println("TipoLista= " + tipoLista);

        }
    }
    
        //EVENTO FILTRAR
    public void eventofiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros: " + filtrarCausasAusentismos.size();
        context.update("form:informacionRegistro");
    }
    //UBICACION CELDA
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            tablaImprimir = ":formExportar:datosCausasAusentismosExportar";
            nombreArchivo = "CausasAusentismosXML";
            if (tipoLista == 0) {
                secRegistro = listaCausasAusentismos.get(index).getSecuencia();
                if (cualCelda == 2) {
                    claseAusentismo = listaCausasAusentismos.get(index).getClase().getDescripcion();
                }
            } else {
                secRegistro = filtrarCausasAusentismos.get(index).getSecuencia();
                if (cualCelda == 2) {
                    claseAusentismo = filtrarCausasAusentismos.get(index).getClase().getDescripcion();
                }
            }
        }
    }
    
     //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarCausasAusentismos = listaCausasAusentismos.get(index);
            }
            if (tipoLista == 1) {
                editarCausasAusentismos = filtrarCausasAusentismos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigo");
                context.execute("editarCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarDescripcion");
                context.execute("editarDescripcion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarClase");
                context.execute("editarClase.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarPorcentajeLiquidacion");
                context.execute("editarPorcentajeLiquidacion.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarRestaDiasIncapacidad");
                context.execute("editarRestaDiasIncapacidad.show()");
                cualCelda = -1;
            }   
        }
        index = -1;
        secRegistro = null;
    }   
    //Menu Origen Incapacidad
    public void seleccionarOrigenIncapacidad(String estadoOrigenIncapacidad, int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();

        if (tipoLista == 0) {
            if (estadoOrigenIncapacidad.equals("AT")) {
                listaCausasAusentismos.get(indice).setOrigenincapacidad("AT");
            } else if (estadoOrigenIncapacidad.equals("EG")) {
                listaCausasAusentismos.get(indice).setOrigenincapacidad("EG");
            } else if (estadoOrigenIncapacidad.equals("EP")) {
                listaCausasAusentismos.get(indice).setOrigenincapacidad("EP");
            } else if (estadoOrigenIncapacidad.equals("MA")) {
                listaCausasAusentismos.get(indice).setOrigenincapacidad("MA");
            } else if (estadoOrigenIncapacidad.equals("OT")) {
                listaCausasAusentismos.get(indice).setOrigenincapacidad("OT");
            } else if (estadoOrigenIncapacidad.equals(" ")) {
                listaCausasAusentismos.get(indice).setOrigenincapacidad(" ");
            }
            if (!listaCausasAusentismosCrear.contains(listaCausasAusentismos.get(indice))) {
                if (listaCausasAusentismosModificar.isEmpty()) {
                    listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                } else if (!listaCausasAusentismosModificar.contains(listaCausasAusentismos.get(indice))) {
                    listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                }
            }
        } else {
            if (estadoOrigenIncapacidad.equals("AT")) {
                filtrarCausasAusentismos.get(indice).setOrigenincapacidad("AT");
            } else if (estadoOrigenIncapacidad.equals("EG")) {
                filtrarCausasAusentismos.get(indice).setOrigenincapacidad("EG");
            } else if (estadoOrigenIncapacidad.equals("EP")) {
                filtrarCausasAusentismos.get(indice).setOrigenincapacidad("EP");
            } else if (estadoOrigenIncapacidad.equals("MA")) {
                filtrarCausasAusentismos.get(indice).setOrigenincapacidad("MA");
            } else if (estadoOrigenIncapacidad.equals("OTR")) {
                filtrarCausasAusentismos.get(indice).setOrigenincapacidad("OT");
            } else if (estadoOrigenIncapacidad.equals(" ")) {
                filtrarCausasAusentismos.get(indice).setOrigenincapacidad(" ");
            }
            if (!listaCausasAusentismosCrear.contains(filtrarCausasAusentismos.get(indice))) {
                if (listaCausasAusentismosModificar.isEmpty()) {
                    listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                } else if (!listaCausasAusentismosModificar.contains(filtrarCausasAusentismos.get(indice))) {
                    listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        RequestContext.getCurrentInstance().update("form:datosCausasAusentismos");
    }
    //Menu Forma Liquidacion
    public void seleccionarFormaLiquidacion(String estadoFormaLiquidacion, int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();

        if (tipoLista == 0) {
            if (estadoFormaLiquidacion.equals("BASICO")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("BASICO");
            } else if (estadoFormaLiquidacion.equals("IBC MES ANTERIOR")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("IBC MES ANTERIOR");
            } else if (estadoFormaLiquidacion.equals("IBC MES ENERO")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("IBC MES ENERO");
            } else if (estadoFormaLiquidacion.equals("IBC MES INCAPACIDAD")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("IBC MES INCAPACIDAD");
            } else if (estadoFormaLiquidacion.equals("PROMEDIO ACUMULADOS 12 MESES")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("PROMEDIO ACUMULADOS 12 MESES");
            } else if (estadoFormaLiquidacion.equals("PROMEDIO IBC 12 MESES")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("PROMEDIO IBC 12 MESES");
            } else if (estadoFormaLiquidacion.equals("PROMEDIO IBC 6 MESES")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion("PROMEDIO IBC 6 MESES");
            } else if (estadoFormaLiquidacion.equals(" ")) {
                listaCausasAusentismos.get(indice).setFormaliquidacion(" ");
            }
            if (!listaCausasAusentismosCrear.contains(listaCausasAusentismos.get(indice))) {
                if (listaCausasAusentismosModificar.isEmpty()) {
                    listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                } else if (!listaCausasAusentismosModificar.contains(listaCausasAusentismos.get(indice))) {
                    listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                }
            }
        } else {
            if (estadoFormaLiquidacion.equals("BASICO")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("BASICO");
            } else if (estadoFormaLiquidacion.equals("IBC MES ANTERIOR")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("IBC MES ANTERIOR");
            } else if (estadoFormaLiquidacion.equals("IBC MES ENERO")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("IBC MES ENERO");
            } else if (estadoFormaLiquidacion.equals("IBC MES INCAPACIDAD")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("IBC MES INCAPACIDAD");
            } else if (estadoFormaLiquidacion.equals("PROMEDIO ACUMULADOS 12 MESES")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("PROMEDIO ACUMULADOS 12 MESES");
            } else if (estadoFormaLiquidacion.equals("PROMEDIO IBC 12 MESES")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("PROMEDIO IBC 12 MESES");
            } else if (estadoFormaLiquidacion.equals("PROMEDIO IBC 6 MESES")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion("PROMEDIO IBC 6 MESES");
            } else if (estadoFormaLiquidacion.equals(" ")) {
                filtrarCausasAusentismos.get(indice).setFormaliquidacion(" ");
            }
            if (!listaCausasAusentismosCrear.contains(filtrarCausasAusentismos.get(indice))) {
                if (listaCausasAusentismosModificar.isEmpty()) {
                    listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                } else if (!listaCausasAusentismosModificar.contains(filtrarCausasAusentismos.get(indice))) {
                    listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        RequestContext.getCurrentInstance().update("form:datosCausasAusentismos");
    }
    
    //AUTOCOMPLETAR
    public void modificarCausasAusentismos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaCausasAusentismosCrear.contains(listaCausasAusentismos.get(indice))) {

                    if (listaCausasAusentismosModificar.isEmpty()) {
                        listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                    } else if (!listaCausasAusentismosModificar.contains(listaCausasAusentismos.get(indice))) {
                        listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaCausasAusentismosCrear.contains(filtrarCausasAusentismos.get(indice))) {

                    if (listaCausasAusentismosModificar.isEmpty()) {
                        listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                    } else if (!listaCausasAusentismosModificar.contains(filtrarCausasAusentismos.get(indice))) {
                        listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosCausasAusentismos");
        } else if (confirmarCambio.equalsIgnoreCase("CLASESAUSENTISMOS")) {
            if (tipoLista == 0) {
                listaCausasAusentismos.get(indice).getClase().setDescripcion(claseAusentismo);
            } else {
                filtrarCausasAusentismos.get(indice).getClase().setDescripcion(claseAusentismo);
            }
            for (int i = 0; i < lovClasesAusentismos.size(); i++) {
                if (lovClasesAusentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaCausasAusentismos.get(indice).setClase(lovClasesAusentismos.get(indiceUnicoElemento));
                } else {
                    filtrarCausasAusentismos.get(indice).setClase(lovClasesAusentismos.get(indiceUnicoElemento));
                }
                lovClasesAusentismos.clear();
                getLovClasesAusentismos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:clasesAusentismosDialogo");
                context.execute("clasesAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaCausasAusentismosCrear.contains(listaCausasAusentismos.get(indice))) {
                    if (listaCausasAusentismosModificar.isEmpty()) {
                        listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                    } else if (!listaCausasAusentismosModificar.contains(listaCausasAusentismos.get(indice))) {
                        listaCausasAusentismosModificar.add(listaCausasAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaCausasAusentismosCrear.contains(filtrarCausasAusentismos.get(indice))) {

                    if (listaCausasAusentismosModificar.isEmpty()) {
                        listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
                    } else if (!listaCausasAusentismosModificar.contains(filtrarCausasAusentismos.get(indice))) {
                        listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(indice));
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
        context.update("form:datosCausasAusentismos");
    }
    
    //MOSTRAR L.O.V CLASES DE AUSENTISMO   
    public void actualizarClases() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaCausasAusentismos.get(index).setClase(clasesAusentismosSeleccionado);
                if (!listaCausasAusentismosCrear.contains(listaCausasAusentismos.get(index))) {
                    if (listaCausasAusentismosModificar.isEmpty()) {
                        listaCausasAusentismosModificar.add(listaCausasAusentismos.get(index));
                    } else if (!listaCausasAusentismosModificar.contains(listaCausasAusentismos.get(index))) {
                        listaCausasAusentismosModificar.add(listaCausasAusentismos.get(index));
                    }
                }
            } else {
                filtrarCausasAusentismos.get(index).setClase(clasesAusentismosSeleccionado);
                if (!listaCausasAusentismosCrear.contains(filtrarCausasAusentismos.get(index))) {
                    if (listaCausasAusentismosModificar.isEmpty()) {
                        listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(index));
                    } else if (!listaCausasAusentismosModificar.contains(filtrarCausasAusentismos.get(index))) {
                        listaCausasAusentismosModificar.add(filtrarCausasAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");

            }
            permitirIndex = true;
            context.update("form:datosCausasAusentismos");
        } else if (tipoActualizacion == 1) {
            nuevaCausasAusentismos.setClase(clasesAusentismosSeleccionado);
            context.update("formularioDialogos:nuevaJornadaLaboral");
        } else if (tipoActualizacion == 2) {
            duplicarCausasAusentismos.setClase(clasesAusentismosSeleccionado);
            context.update("formularioDialogos:duplicarJornadaLaboral");
        }
        lovFiltradoClasesAusentismos = null;
        clasesAusentismosSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("jornadasDialogo.hide()");
        context.reset("formularioDialogos:LOVClasesAusentismos:globalFilter");
        context.update("formularioDialogos:LOVClasesAusentismos");
    }
    
    public void cancelarCambioClase() {
        lovFiltradoClasesAusentismos = null;
        clasesAusentismosSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }
    
    
    //Getter & Setters
    public List<Causasausentismos> getListaCausasAusentismos() {
        if (listaCausasAusentismos == null) {
            listaCausasAusentismos = administrarCausasAusentismos.consultarCausasAusentismos();
        }
               
        return listaCausasAusentismos;
    }

    public void setListaCausasAusentismos(List<Causasausentismos> listaCausasAusentismos) {
        this.listaCausasAusentismos = listaCausasAusentismos;
    }

    public List<Causasausentismos> getFiltrarCausasAusentismos() {
        return filtrarCausasAusentismos;
    }

    public void setFiltrarCausasAusentismos(List<Causasausentismos> filtrarCausasAusentismos) {
        this.filtrarCausasAusentismos = filtrarCausasAusentismos;
    }

    public List<Causasausentismos> getModificarCausasAusentismos() {
        return listaCausasAusentismosModificar;
    }

    public void setModificarCausasAusentismos(List<Causasausentismos> modificarCausasAusentismos) {
        this.listaCausasAusentismosModificar = modificarCausasAusentismos;
    }

    public List<Causasausentismos> getBorrarCausasAusentismos() {
        return listaCausasAusentismosBorrar;
    }

    public void setBorrarCausasAusentismos(List<Causasausentismos> borrarCausasAusentismos) {
        this.listaCausasAusentismosBorrar = borrarCausasAusentismos;
    }

    public List<Clasesausentismos> getLovClasesAusentismos() {
        lovClasesAusentismos = administrarCausasAusentismos.consultarClasesAusentismos();
        RequestContext context = RequestContext.getCurrentInstance();

        if (lovClasesAusentismos == null || lovClasesAusentismos.isEmpty()) {
            infoRegistroClasesausentismos = "Cantidad de registros: 0 ";
        } else {
            infoRegistroClasesausentismos = "Cantidad de registros: " + lovClasesAusentismos.size();
        }
        context.update("formularioDialogos:infoRegistroClasesAusentismos");
        return lovClasesAusentismos;
    }

    public void setLovClasesAusentismos(List<Clasesausentismos> lovClasesAusentismos) {
        this.lovClasesAusentismos = lovClasesAusentismos;
    }

    public List<Clasesausentismos> getLovFiltradoClasesAusentismos() {
        return lovFiltradoClasesAusentismos;
    }

    public void setLovFiltradoClasesAusentismos(List<Clasesausentismos> lovFiltradoClasesAusentismos) {
        this.lovFiltradoClasesAusentismos = lovFiltradoClasesAusentismos;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getInfoRegistroCausasausentismos() {
        return infoRegistroClasesausentismos;
    }

    public void setInfoRegistroCausasausentismos(String infoRegistroCausasausentismos) {
        this.infoRegistroClasesausentismos = infoRegistroCausasausentismos;
    }

    public String getTablaImprimir() {
        return tablaImprimir;
    }

    public void setTablaImprimir(String tablaImprimir) {
        this.tablaImprimir = tablaImprimir;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public Causasausentismos getEditarCausasAusentismos() {
        return editarCausasAusentismos;
    }

    public void setEditarCausasAusentismos(Causasausentismos editarCausasAusentismos) {
        this.editarCausasAusentismos = editarCausasAusentismos;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }        

    public Causasausentismos getCausasAusentismoSeleccionado() {
        return causasAusentismoSeleccionado;
    }

    public void setCausasAusentismoSeleccionado(Causasausentismos causasAusentismoSeleccionado) {
        this.causasAusentismoSeleccionado = causasAusentismoSeleccionado;
    }

    public Clasesausentismos getClasesAusentismosSeleccionado() {
        return clasesAusentismosSeleccionado;
    }

    public void setClasesAusentismosSeleccionado(Clasesausentismos clasesAusentismosSeleccionado) {
        this.clasesAusentismosSeleccionado = clasesAusentismosSeleccionado;
    }
    
    
    
    
}

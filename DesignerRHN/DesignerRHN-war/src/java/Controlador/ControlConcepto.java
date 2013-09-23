package Controlador;

import Entidades.Conceptos;
import Entidades.VigenciasTiposContratos;
import InterfaceAdministrar.AdministrarConceptosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
    /*private List<Ciudades> listaCiudades;
     private Ciudades ciudadSelecionada;
     private List<Ciudades> filtradoCiudades;
     private List<MotivosContratos> listaMotivosContratos;
     private MotivosContratos MotivoContratoSelecionado;
     private List<MotivosContratos> filtradoMotivoContrato;
     private List<TiposContratos> listaTiposContratos;
     private List<TiposContratos> filtradoTiposContrato;
     private TiposContratos TipoContratoSelecionado;
     private BigInteger secuenciaEmpleado;
     private Empleados empleado;*/
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
    private List<VigenciasTiposContratos> listVTCModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public VigenciasTiposContratos nuevaVigencia;
    private List<VigenciasTiposContratos> listVTCCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<VigenciasTiposContratos> listVTCBorrar;
    //editar celda
    private VigenciasTiposContratos editarVTC;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasTiposContratos duplicarVTC;
    //AUTOCOMPLETAR
    private String Motivo, TipoContrato, Ciudad;
    //RASTRO
    private BigInteger secRegistro;

    public ControlConcepto() {
        listaConceptosEmpresa = new ArrayList<Conceptos>();
    }

    //SELECCIONAR NATURALEZA
    public void seleccionarNaturaleza(String naturalezaConcepto, int indice, int celda) {
        if (tipoLista == 0) {
            if (naturalezaConcepto.equals("NETO")) {
                listaConceptosEmpresa.get(indice).setNaturaleza("N");
            } else if (naturalezaConcepto.equals("GASTO")) {
                listaConceptosEmpresa.get(indice).setNaturaleza("G");
            } else if (naturalezaConcepto.equals("DESCUENTO")) {
                listaConceptosEmpresa.get(indice).setNaturaleza("D");
            } else if (naturalezaConcepto.equals("PAGO")) {
                listaConceptosEmpresa.get(indice).setNaturaleza("P");
            } else if (naturalezaConcepto.equals("PASIVO")) {
                listaConceptosEmpresa.get(indice).setNaturaleza("L");
            }

            /*     if (!listOrganigramasCrear.contains(listaOrganigramas.get(indice))) {
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
             }*/
            if (guardado == true) {
                guardado = false;
            }
            RequestContext.getCurrentInstance().update("form:datosConceptos");
        }
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    //GETTER AND SETTER
    public List<Conceptos> getListaConceptosEmpresa() {
        if (listaConceptosEmpresa.isEmpty()) {
            listaConceptosEmpresa = administrarConceptos.conceptosEmpresa(BigInteger.valueOf(10595521));
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
}

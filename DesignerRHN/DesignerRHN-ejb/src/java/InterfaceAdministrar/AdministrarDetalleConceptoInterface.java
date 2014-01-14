/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.CentrosCostos;
import Entidades.Conceptos;
import Entidades.Cuentas;
import Entidades.Formulas;
import Entidades.FormulasConceptos;
import Entidades.GruposConceptos;
import Entidades.ReformasLaborales;
import Entidades.TiposCentrosCostos;
import Entidades.TiposContratos;
import Entidades.TiposTrabajadores;
import Entidades.VigenciasConceptosRL;
import Entidades.VigenciasConceptosTC;
import Entidades.VigenciasConceptosTT;
import Entidades.VigenciasCuentas;
import Entidades.VigenciasGruposConceptos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'DetalleConcepto'.
 * @author Andres Pineda.
 */
public interface AdministrarDetalleConceptoInterface {
    
    public List<VigenciasCuentas> listaVigenciasCuentasConcepto(BigInteger secuencia);
    /**
     * Método encargado de crear VigenciasCuentas.
     * @param listaVigenciasCuentas Lista de las VigenciasCuentas que se van a crear.
     */
    public void crearVigenciasCuentas(List<VigenciasCuentas> listaVigenciasCuentas);
    /**
     * Método encargado de editar VigenciasCuentas.
     * @param listaVigenciasCuentas Lista de las VigenciasCuentas que se van a modificar.
     */
    public void modificarVigenciasCuentas(List<VigenciasCuentas> listaVigenciasCuentas);
    /**
     * Método encargado de borrar VigenciasCuentas.
     * @param listaVigenciasCuentas Lista de las VigenciasCuentas que se van a eliminar.
     */
    public void borrarVigenciasCuentas(List<VigenciasCuentas> listaVigenciasCuentas);
    
    public List<VigenciasGruposConceptos> listaVigenciasGruposConceptosConcepto(BigInteger secuencia);
    /**
     * Método encargado de crear VigenciasGruposConceptos.
     * @param listaVigenGruposConceptos Lista de las VigenciasGruposConceptos que se van a crear.
     */
    public void crearVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVigenGruposConceptos);
    /**
     * Método encargado de editar VigenGruposConceptos.
     * @param listaVigenGruposConceptos Lista de las VigenGruposConceptos que se van a modificar.
     */
    public void modificarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVigenGruposConceptos);
    /**
     * Método encargado de borrar VigenciasGruposConceptos.
     * @param listaVigenGruposConceptos Lista de las VigenciasGruposConceptos que se van a eliminar.
     */
    public void borrarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVigenGruposConceptos);
    
    public List<VigenciasConceptosTT> listaVigenciasConceptosTTConcepto(BigInteger secuencia);
    /**
     * Método encargado de crear VigenciasConceptosTT.
     * @param listaVigenciasConceptosTT Lista de las VigenciasConceptosTT que se van a crear.
     */
    public void crearVigenciasConceptosTT(List<VigenciasConceptosTT> listaVigenciasConceptosTT);
    /**
     * Método encargado de editar VigenciasConceptosTT.
     * @param listaVigenciasConceptosTT Lista de las VigenciasConceptosTT que se van a modificar.
     */
    public void modificarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVigenciasConceptosTT);
    /**
     * Método encargado de borrar VigenciasConceptosTT.
     * @param listaVigenciasConceptosTT Lista de las VigenciasConceptosTT que se van a eliminar.
     */
    public void borrarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVigenciasConceptosTT);
    
    public List<VigenciasConceptosTC> listaVigenciasConceptosTCConcepto(BigInteger secuencia);
    /**
     * Método encargado de crear VigenciasConceptosTC.
     * @param listaVigenciasConceptosTC Lista de las VigenciasConceptosTC que se van a crear.
     */
    public void crearVigenciasConceptosTC(List<VigenciasConceptosTC> listaVigenciasConceptosTC);
    /**
     * Método encargado de editar VigenciasConceptosTC.
     * @param listaVigenciasConceptosTC Lista de las VigenciasConceptosTC que se van a modificar.
     */
    public void modificarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVigenciasConceptosTC);
    /**
     * Método encargado de borrar VigenciasConceptosTC.
     * @param listaVigenciasConceptosTC Lista de las VigenciasConceptosTC que se van a eliminar.
     */
    public void borrarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVigenciasConceptosTC);
    
    public List<VigenciasConceptosRL> listaVigenciasConceptosRLCConcepto(BigInteger secuencia);
    /**
     * Método encargado de crear VigenciasConceptosRL.
     * @param listaVigenciasConceptosRL Lista de las VigenciasConceptosRL que se van a crear.
     */
    public void crearVigenciasConceptosRL(List<VigenciasConceptosRL> listaVigenciasConceptosRL);
    /**
     * Método encargado de editar VigenciasConceptosRL.
     * @param listaVigenciasConceptosRL Lista de las VigenciasConceptosRL que se van a modificar.
     */
    public void modificarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVigenciasConceptosRL);
    /**
     * Método encargado de borrar VigenciasConceptosRL.
     * @param listaVigenciasConceptosRL Lista de las VigenciasConceptosRL que se van a eliminar.
     */
    public void borrarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVigenciasConceptosRL);
    
    public List<FormulasConceptos> listaFormulasConceptosConcepto(BigInteger secuencia);
    /**
     * Método encargado de crear FormulasConceptos.
     * @param listaFormulasConceptos Lista de las FormulasConceptos que se van a crear.
     */
    public void crearFormulasConceptos(List<FormulasConceptos> listaFormulasConceptos);
    /**
     * Método encargado de editar FormulasConceptos.
     * @param listaFormulasConceptos Lista de las FormulasConceptos que se van a modificar.
     */
    public void modificarFormulasConceptos(List<FormulasConceptos> listaFormulasConceptos);
    /**
     * Método encargado de borrar FormulasConceptos.
     * @param listaFormulasConceptos Lista de las FormulasConceptos que se van a eliminar.
     */
    public void borrarFormulasConceptos(List<FormulasConceptos> listaFormulasConceptos);
    
    public Conceptos conceptoActual(BigInteger secuencia);
    
    public List<TiposCentrosCostos> listaTiposCentrosCostos();
    
    public List<Cuentas> listaCuentas();
    
    public List<CentrosCostos> listaCentrosCostos();
    
    public List<GruposConceptos> listaGruposConceptos();
    
    public List<TiposTrabajadores> listaTiposTrabajadores();
    
    public List<TiposContratos> listaTiposContratos();
    
    public List<ReformasLaborales> listaReformasLaborales();
    
    public List<Formulas> listaFormulas();
    
    public List<FormulasConceptos> listaFormulasConceptos();
    
    public Long comportamientoAutomaticoConcepto(BigInteger secuencia);
    
    public Long comportamientoSemiAutomaticoConcepto(BigInteger secuencia);
    
    public boolean eliminarConcepto(BigInteger secuencia);
    
    public boolean verificarSolucionesNodosParaConcepto(BigInteger secuencia);
}

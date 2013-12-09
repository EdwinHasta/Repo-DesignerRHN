/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author user
 */
public interface AdministrarDetalleConceptoInterface {

    public List<VigenciasCuentas> listVigenciasCuentasConcepto(BigInteger secuencia);

    public void crearVigenciasCuentas(List<VigenciasCuentas> listaVC);

    public void editarVigenciasCuentas(List<VigenciasCuentas> listaVC);

    public void borrarVigenciasCuentas(List<VigenciasCuentas> listaVC);

    public List<VigenciasGruposConceptos> listVigenciasGruposConceptosConcepto(BigInteger secuencia);

    public void crearVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC);

    public void editarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC);

    public void borrarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC);

    public List<VigenciasConceptosTT> listVigenciasConceptosTTConcepto(BigInteger secuencia);

    public void crearVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC);

    public void editarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC);

    public void borrarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC);

    public List<VigenciasConceptosTC> listVigenciasConceptosTCConcepto(BigInteger secuencia);

    public void crearVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC);

    public void editarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC);

    public void borrarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC);

    public List<VigenciasConceptosRL> listVigenciasConceptosRLCConcepto(BigInteger secuencia);

    public void crearVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC);

    public void editarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC);

    public void borrarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC);

    public List<FormulasConceptos> listFormulasConceptosConcepto(BigInteger secuencia);

    public void crearFormulasConceptos(List<FormulasConceptos> listaFC);

    public void editarFormulasConceptos(List<FormulasConceptos> listaFC);

    public void borrarFormulasConceptos(List<FormulasConceptos> listaFC);

    public Conceptos conceptoActual(BigInteger secuencia);

    public List<TiposCentrosCostos> listTiposCentrosCostos();

    public List<Cuentas> listCuentas();

    public List<CentrosCostos> listCentrosCostos();

    public List<GruposConceptos> listGruposConceptos();

    public List<TiposTrabajadores> listTiposTrabajadores();

    public List<TiposContratos> listTiposContratos();

    public List<ReformasLaborales> listReformasLaborales();

    public List<Formulas> listFormulas();

    public List<FormulasConceptos> listFormulasConceptos();

}

package Administrar;

import Entidades.CentrosCostos;
import Entidades.VigenciasCuentas;
import Entidades.VigenciasGruposConceptos;
import Entidades.VigenciasConceptosTT;
import Entidades.VigenciasConceptosTC;
import Entidades.VigenciasConceptosRL;
import Entidades.FormulasConceptos;
import Entidades.Conceptos;
import Entidades.Cuentas;
import Entidades.Formulas;
import Entidades.GruposConceptos;
import Entidades.ReformasLaborales;
import Entidades.TiposCentrosCostos;
import Entidades.TiposContratos;
import Entidades.TiposTrabajadores;
import InterfaceAdministrar.AdministrarDetalleConceptoInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaCuentasInterface;
import InterfacePersistencia.PersistenciaFormulasConceptosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaGruposConceptosInterface;
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaTiposCentrosCostosInterface;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaVigenciasConceptosRLInterface;
import InterfacePersistencia.PersistenciaVigenciasConceptosTCInterface;
import InterfacePersistencia.PersistenciaVigenciasConceptosTTInterface;
import InterfacePersistencia.PersistenciaVigenciasCuentasInterface;
import InterfacePersistencia.PersistenciaVigenciasGruposConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class AdministrarDetalleConcepto implements AdministrarDetalleConceptoInterface {

    @EJB
    PersistenciaVigenciasCuentasInterface persistenciaVigenciasCuentas;
    @EJB
    PersistenciaVigenciasGruposConceptosInterface persistenciaVigenciasGruposConceptos;
    @EJB
    PersistenciaVigenciasConceptosTTInterface persistenciaVigenciasConceptosTT;
    @EJB
    PersistenciaVigenciasConceptosTCInterface persistenciaVigenciasConceptosTC;
    @EJB
    PersistenciaVigenciasConceptosRLInterface persistenciaVigenciasConceptosRL;
    @EJB
    PersistenciaFormulasConceptosInterface persistenciaFormulasConceptos;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    //////Listas de valores//////
    @EJB
    PersistenciaTiposCentrosCostosInterface persistenciaTiposCentrosCostos;
    @EJB
    PersistenciaCuentasInterface persistenciaCuentas;
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    //////Listas de valores//////
    @EJB
    PersistenciaGruposConceptosInterface persistenciaGruposConceptos;
    //////Listas de valores//////
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    //////Listas de valores//////
    @EJB
    PersistenciaTiposContratosInterface persistenciaTiposContratos;
    //////Listas de valores//////
    @EJB
    PersistenciaReformasLaboralesInterface persistenciaReformasLaborales;
    //////Listas de valores//////
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    @EJB
    PersistenciaFormulasConceptosInterface PersistenciaFormulasConceptos;

    ///////////VigenciasCuentas/////////////////VigenciasCuentas////////////////////////VigenciasCuentas/////////////
    @Override
    public List<VigenciasCuentas> listVigenciasCuentasConcepto(BigInteger secuencia) {
        try {
            List<VigenciasCuentas> lista = persistenciaVigenciasCuentas.buscarVigenciasCuentasPorConcepto(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listVigenciasCuentasConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasCuentas(List<VigenciasCuentas> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasCuentas.crear(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasCuentas Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasCuentas(List<VigenciasCuentas> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasCuentas.editar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasCuentas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasCuentas(List<VigenciasCuentas> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasCuentas.borrar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasCuentas Admi : " + e.toString());
        }
    }

    @Override
    public List<TiposCentrosCostos> listTiposCentrosCostos() {
        try {
            List<TiposCentrosCostos> tipos = persistenciaTiposCentrosCostos.buscarTiposCentrosCostos();
            return tipos;
        } catch (Exception e) {
            System.out.println("Error listTiposCentrosCostos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Cuentas> listCuentas() {
        try {
            List<Cuentas> cuentas = persistenciaCuentas.buscarCuentas();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error listCuentas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<CentrosCostos> listCentrosCostos() {
        try {
            List<CentrosCostos> centros = persistenciaCentrosCostos.buscarCentrosCostos();
            return centros;
        } catch (Exception e) {
            System.out.println("Error listCentrosCostos Admi : " + e.toString());
            return null;
        }
    }
    ///////////VigenciasCuentas/////////////////VigenciasCuentas////////////////////////VigenciasCuentas/////////////

    ///////////VigenciasGruposConceptos////////////VigenciasGruposConceptos/////////////////VigenciasGruposConceptos///////
    @Override
    public List<VigenciasGruposConceptos> listVigenciasGruposConceptosConcepto(BigInteger secuencia) {
        try {
            List<VigenciasGruposConceptos> lista = persistenciaVigenciasGruposConceptos.listVigenciasGruposConceptosPorConcepto(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listVigenciasGruposConceptosConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC) {
        try {
            for (int i = 0; i < listaVGC.size(); i++) {
                persistenciaVigenciasGruposConceptos.crear(listaVGC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC) {
        try {
            for (int i = 0; i < listaVGC.size(); i++) {
                persistenciaVigenciasGruposConceptos.editar(listaVGC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasGruposConceptos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC) {
        try {
            for (int i = 0; i < listaVGC.size(); i++) {
                persistenciaVigenciasGruposConceptos.borrar(listaVGC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasGruposConceptos Admi : " + e.toString());
        }
    }

    @Override
    public List<GruposConceptos> listGruposConceptos() {
        try {
            List<GruposConceptos> grupos = persistenciaGruposConceptos.buscarGruposConceptos();
            return grupos;
        } catch (Exception e) {
            System.out.println("Error listGruposConceptos Admi : " + e.toString());
            return null;
        }
    }
    ///////////VigenciasGruposConceptos////////////VigenciasGruposConceptos/////////////////VigenciasGruposConceptos///////

    ///////////VigenciasConceptosTT////////////VigenciasConceptosTT/////////////////VigenciasConceptosTT///////
    @Override
    public List<VigenciasConceptosTT> listVigenciasConceptosTTConcepto(BigInteger secuencia) {
        try {
            List<VigenciasConceptosTT> lista = persistenciaVigenciasConceptosTT.listVigenciasConceptosTTPorConcepto(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listVigenciasConceptosTTConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosTT.crear(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTT Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosTT.editar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasConceptosTT Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosTT.borrar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasConceptosTT Admi : " + e.toString());
        }
    }

    @Override
    public List<TiposTrabajadores> listTiposTrabajadores() {
        try {
            List<TiposTrabajadores> tipos = persistenciaTiposTrabajadores.buscarTiposTrabajadores();
            return tipos;
        } catch (Exception e) {
            System.out.println("Error listTiposTrabajadores Admi : " + e.toString());
            return null;
        }
    }
    ///////////VigenciasConceptosTT////////////VigenciasConceptosTT/////////////////VigenciasConceptosTT///////

    ///////////VigenciasConceptosTC////////////VigenciasConceptosTC/////////////////VigenciasConceptosTC///////
    @Override
    public List<VigenciasConceptosTC> listVigenciasConceptosTCConcepto(BigInteger secuencia) {
        try {
            List<VigenciasConceptosTC> lista = persistenciaVigenciasConceptosTC.listVigenciasConceptosTCPorConcepto(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listVigenciasConceptosTCConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosTC.crear(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosTC.editar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasConceptosTC Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosTC.borrar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasConceptosTC Admi : " + e.toString());
        }
    }

    @Override
    public List<TiposContratos> listTiposContratos() {
        try {
            List<TiposContratos> tipos = persistenciaTiposContratos.tiposContratos();
            return tipos;
        } catch (Exception e) {
            System.out.println("Error listTiposContratos Admi : " + e.toString());
            return null;
        }
    }
    ///////////VigenciasConceptosTC////////////VigenciasConceptosTC/////////////////VigenciasConceptosTC///////

    ///////////VigenciasConceptosRL////////////VigenciasConceptosRL/////////////////VigenciasConceptosRL///////
    @Override
    public List<VigenciasConceptosRL> listVigenciasConceptosRLCConcepto(BigInteger secuencia) {
        try {
            List<VigenciasConceptosRL> lista = persistenciaVigenciasConceptosRL.listVigenciasConceptosRLPorConcepto(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listVigenciasConceptosRLCConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosRL.crear(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosRL.editar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasConceptosTC Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC) {
        try {
            for (int i = 0; i < listaVC.size(); i++) {
                persistenciaVigenciasConceptosRL.borrar(listaVC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasConceptosTC Admi : " + e.toString());
        }
    }

    @Override
    public List<ReformasLaborales> listReformasLaborales() {
        try {
            List<ReformasLaborales> reformas = persistenciaReformasLaborales.buscarReformasLaborales();
            return reformas;
        } catch (Exception e) {
            System.out.println("Error listReformasLaborales Admi : " + e.toString());
            return null;
        }
    }
    ///////////VigenciasConceptosRL////////////VigenciasConceptosRL/////////////////VigenciasConceptosRL///////

    ///////////FormulasConceptos////////////FormulasConceptos/////////////////FormulasConceptos///////
    @Override
    public List<FormulasConceptos> listFormulasConceptosConcepto(BigInteger secuencia) {
        try {
            List<FormulasConceptos> lista = persistenciaFormulasConceptos.formulasConcepto(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulasConceptosConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearFormulasConceptos(List<FormulasConceptos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                persistenciaFormulasConceptos.crear(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulasConceptos Admi : " + e.toString());
        }
    }

    @Override
    public void editarFormulasConceptos(List<FormulasConceptos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                persistenciaFormulasConceptos.editar(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarFormulasConceptos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarFormulasConceptos(List<FormulasConceptos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                persistenciaFormulasConceptos.borrar(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarFormulasConceptos Admi : " + e.toString());
        }
    }

    @Override
    public List<Formulas> listFormulas() {
        try {
            List<Formulas> formulas = persistenciaFormulas.buscarFormulas();
            return formulas;
        } catch (Exception e) {
            System.out.println("Error listFormulas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<FormulasConceptos> listFormulasConceptos() {
        try {
            List<FormulasConceptos> formulas = persistenciaFormulasConceptos.buscarFormulasConceptos();
            return formulas;
        } catch (Exception e) {
            System.out.println("Error listFormulasConceptos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public Long comportamientoAutomaticoConcepto(BigInteger secuencia){
        try{
            Long retorno = PersistenciaFormulasConceptos.comportamientoConceptoAutomaticoSecuenciaConcepto(secuencia);
            return retorno;
        }catch(Exception e){
            System.out.println("Error comportamientoAutomaticoConcepto Admi : "+e.toString());
            return null;
        }
    }
    

    @Override
    public Long comportamientoSemiAutomaticoConcepto(BigInteger secuencia){
        try{
            Long retorno = PersistenciaFormulasConceptos.comportamientoConceptoSemiAutomaticoSecuenciaConcepto(secuencia);
            return retorno;
        }catch(Exception e){
            System.out.println("Error comportamientoSemiAutomaticoConcepto Admi : "+e.toString());
            return null;
        }
    }
    ///////////FormulasConceptos////////////FormulasConceptos/////////////////FormulasConceptos///////

    ///////////Conceptos////////////Conceptos/////////////////Conceptos///////
    @Override
    public Conceptos conceptoActual(BigInteger secuencia) {
        try {
            Conceptos actual = persistenciaConceptos.conceptosPorSecuencia(secuencia);
            return actual;
        } catch (Exception e) {
            System.out.println("Error conceptoActual Admi : " + e.toString());
            return null;
        }
    }
    ///////////Conceptos////////////Conceptos/////////////////Conceptos///////
}

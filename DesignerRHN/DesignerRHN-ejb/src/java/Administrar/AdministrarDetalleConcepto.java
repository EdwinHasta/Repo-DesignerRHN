/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
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
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
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
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'DetalleConcepto'.
 * @author Andres Pineda.
 */
@Stateful
public class AdministrarDetalleConcepto implements AdministrarDetalleConceptoInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasCuentas'.
     */
    @EJB
    PersistenciaVigenciasCuentasInterface persistenciaVigenciasCuentas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasGruposConceptos'.
     */
    @EJB
    PersistenciaVigenciasGruposConceptosInterface persistenciaVigenciasGruposConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasConceptosTT'.
     */
    @EJB
    PersistenciaVigenciasConceptosTTInterface persistenciaVigenciasConceptosTT;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasConceptosTC'.
     */
    @EJB
    PersistenciaVigenciasConceptosTCInterface persistenciaVigenciasConceptosTC;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasConceptosRL'.
     */
    @EJB
    PersistenciaVigenciasConceptosRLInterface persistenciaVigenciasConceptosRL;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaFormulasConceptos'.
     */
    @EJB
    PersistenciaFormulasConceptosInterface persistenciaFormulasConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaConceptos'.
     */
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaTiposCentrosCostos'.
     */
    @EJB
    PersistenciaTiposCentrosCostosInterface persistenciaTiposCentrosCostos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCuentas'.
     */
    @EJB
    PersistenciaCuentasInterface persistenciaCuentas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCentrosCostos'.
     */
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaGruposConceptos'.
     */
    @EJB
    PersistenciaGruposConceptosInterface persistenciaGruposConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaTiposTrabajadores'.
     */
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaTiposContratos'.
     */
    @EJB
    PersistenciaTiposContratosInterface persistenciaTiposContratos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaReformasLaborales'.
     */
    @EJB
    PersistenciaReformasLaboralesInterface persistenciaReformasLaborales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaFormulas'.
     */
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'PersistenciaFormulasConceptos'.
     */
    @EJB
    PersistenciaFormulasConceptosInterface PersistenciaFormulasConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaSolucionesNodos'.
     */
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<VigenciasCuentas> listaVigenciasCuentasConcepto(BigInteger secuencia) {
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
    public void modificarVigenciasCuentas(List<VigenciasCuentas> listaVC) {
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
    public List<TiposCentrosCostos> listaTiposCentrosCostos() {
        try {
            List<TiposCentrosCostos> tipos = persistenciaTiposCentrosCostos.buscarTiposCentrosCostos();
            return tipos;
        } catch (Exception e) {
            System.out.println("Error listTiposCentrosCostos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Cuentas> listaCuentas() {
        try {
            List<Cuentas> cuentas = persistenciaCuentas.buscarCuentas();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error listCuentas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<CentrosCostos> listaCentrosCostos() {
        try {
            List<CentrosCostos> centros = persistenciaCentrosCostos.buscarCentrosCostos();
            return centros;
        } catch (Exception e) {
            System.out.println("Error listCentrosCostos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<VigenciasGruposConceptos> listaVigenciasGruposConceptosConcepto(BigInteger secuencia) {
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
    public void modificarVigenciasGruposConceptos(List<VigenciasGruposConceptos> listaVGC) {
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
    public List<GruposConceptos> listaGruposConceptos() {
        try {
            List<GruposConceptos> grupos = persistenciaGruposConceptos.buscarGruposConceptos();
            return grupos;
        } catch (Exception e) {
            System.out.println("Error listGruposConceptos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<VigenciasConceptosTT> listaVigenciasConceptosTTConcepto(BigInteger secuencia) {
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
    public void modificarVigenciasConceptosTT(List<VigenciasConceptosTT> listaVC) {
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
    public List<TiposTrabajadores> listaTiposTrabajadores() {
        try {
            List<TiposTrabajadores> tipos = persistenciaTiposTrabajadores.buscarTiposTrabajadores();
            return tipos;
        } catch (Exception e) {
            System.out.println("Error listTiposTrabajadores Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<VigenciasConceptosTC> listaVigenciasConceptosTCConcepto(BigInteger secuencia) {
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
    public void modificarVigenciasConceptosTC(List<VigenciasConceptosTC> listaVC) {
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
    public List<TiposContratos> listaTiposContratos() {
        try {
            List<TiposContratos> tipos = persistenciaTiposContratos.tiposContratos();
            return tipos;
        } catch (Exception e) {
            System.out.println("Error listTiposContratos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<VigenciasConceptosRL> listaVigenciasConceptosRLCConcepto(BigInteger secuencia) {
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
    public void modificarVigenciasConceptosRL(List<VigenciasConceptosRL> listaVC) {
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
    public List<ReformasLaborales> listaReformasLaborales() {
        try {
            List<ReformasLaborales> reformas = persistenciaReformasLaborales.buscarReformasLaborales();
            return reformas;
        } catch (Exception e) {
            System.out.println("Error listReformasLaborales Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<FormulasConceptos> listaFormulasConceptosConcepto(BigInteger secuencia) {
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
    public void modificarFormulasConceptos(List<FormulasConceptos> listaFC) {
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
    public List<Formulas> listaFormulas() {
        try {
            List<Formulas> formulas = persistenciaFormulas.buscarFormulas();
            return formulas;
        } catch (Exception e) {
            System.out.println("Error listFormulas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<FormulasConceptos> listaFormulasConceptos() {
        try {
            List<FormulasConceptos> formulas = persistenciaFormulasConceptos.buscarFormulasConceptos();
            return formulas;
        } catch (Exception e) {
            System.out.println("Error listFormulasConceptos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Long comportamientoAutomaticoConcepto(BigInteger secuencia) {
        try {
            Long retorno = PersistenciaFormulasConceptos.comportamientoConceptoAutomaticoSecuenciaConcepto(secuencia);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error comportamientoAutomaticoConcepto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Long comportamientoSemiAutomaticoConcepto(BigInteger secuencia) {
        try {
            Long retorno = PersistenciaFormulasConceptos.comportamientoConceptoSemiAutomaticoSecuenciaConcepto(secuencia);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error comportamientoSemiAutomaticoConcepto Admi : " + e.toString());
            return null;
        }
    }
    
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

    @Override
    public boolean eliminarConcepto(BigInteger secuencia) {
        try {
            return persistenciaConceptos.eliminarConcepto(secuencia);
        } catch (Exception e) {
            System.out.println("Error eliminarConcepto Admi : " + e.toString());
            return false;
        }
    }
    
    @Override
    public boolean verificarSolucionesNodosParaConcepto(BigInteger secuencia) {
        try {
            boolean retorno = persistenciaSolucionesNodos.solucionesNodosParaConcepto(secuencia);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error verificarSolucionesNodosParaConcepto Admi : " + e.toString());
            return false;
        }
    }
}

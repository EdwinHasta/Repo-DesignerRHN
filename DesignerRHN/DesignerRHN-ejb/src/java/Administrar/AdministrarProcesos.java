/**
 * Documentación a cargo de Andres Pineda
 */
package Administrar;

import Entidades.Formulas;
import Entidades.FormulasProcesos;
import Entidades.Operandos;
import Entidades.OperandosLogs;
import Entidades.Procesos;
import Entidades.Tipospagos;
import InterfaceAdministrar.AdministrarProcesosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaFormulasProcesosInterface;
import InterfacePersistencia.PersistenciaOperandosInterface;
import InterfacePersistencia.PersistenciaOperandosLogsInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaTiposPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Proceso'.
 *
 * @author AndresPineda 
 */
@Stateful
public class AdministrarProcesos implements AdministrarProcesosInterface {
    //------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaProcesos'.
     */
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaOperandosLogs'.
     */
    @EJB
    PersistenciaOperandosLogsInterface persistenciaOperandosLogs;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaFormulasProcesos'.
     */
    @EJB
    PersistenciaFormulasProcesosInterface persistenciaFormulasProcesos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaTiposPagos'.
     */
    @EJB
    PersistenciaTiposPagosInterface persistenciaTiposPagos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaFormulas'.
     */
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaOperandos'.
     */
    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------    
    ///// ---- PROCESOS ---- /////
    @Override
    public List<Procesos> listaProcesos() {
        try {
            List<Procesos> lista = persistenciaProcesos.buscarProcesos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaProcesos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearProcesos(List<Procesos> listaP) {
        try {
            for (int i = 0; i < listaP.size(); i++) {
                String textM = listaP.get(i).getDescripcion().toUpperCase();
                listaP.get(i).setDescripcion(textM);
                String textC = listaP.get(i).getComentarios().toUpperCase();
                listaP.get(i).setComentarios(textC);
                persistenciaProcesos.crear(listaP.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearProcesos Admi : " + e.toString()
            );
        }
    }

    @Override
    public void editarProcesos(List<Procesos> listaP) {
        try {
            for (int i = 0; i < listaP.size(); i++) {
                String textM = listaP.get(i).getDescripcion().toUpperCase();
                listaP.get(i).setDescripcion(textM);
                String textC = listaP.get(i).getComentarios().toUpperCase();
                listaP.get(i).setComentarios(textC);
                persistenciaProcesos.editar(listaP.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarProcesos Admi : " + e.toString()
            );
        }
    }

    @Override
    public void borrarProcesos(List<Procesos> listaP) {
        try {
            for (int i = 0; i < listaP.size(); i++) {
                persistenciaProcesos.borrar(listaP.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarProcesos Admi : " + e.toString()
            );
        }
    }

    @Override
    public List<Tipospagos> lovTiposPagos() {
        try {
            List<Tipospagos> lista = persistenciaTiposPagos.buscarTiposPagos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposPagos Admi : " + e.toString());
            return null;
        }
    }
    ///// ---- PROCESOS ---- /////

    ///// ---- FORMULASPROCESOS ---- /////
    @Override
    public List<FormulasProcesos> listaFormulasProcesosParaProcesoSecuencia(BigInteger secuencia) {
        try {
            List<FormulasProcesos> lista = persistenciaFormulasProcesos.formulasProcesosParaProcesoSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaFormulasProcesosParaProcesoSecuencia Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearFormulasProcesos(List<FormulasProcesos> listaFP) {
        try {
            for (int i = 0; i < listaFP.size(); i++) {
                persistenciaFormulasProcesos.crear(listaFP.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulasProcesos Admi : " + e.toString()
            );
        }
    }

    @Override
    public void editarFormulasProcesos(List<FormulasProcesos> listaFP) {
        try {
            for (int i = 0; i < listaFP.size(); i++) {
                persistenciaFormulasProcesos.editar(listaFP.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarFormulasProcesos Admi : " + e.toString()
            );
        }
    }

    @Override
    public void borrarFormulasProcesos(List<FormulasProcesos> listaFP) {
        try {
            for (int i = 0; i < listaFP.size(); i++) {
                persistenciaFormulasProcesos.borrar(listaFP.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarFormulasProcesos Admi : " + e.toString()
            );
        }
    }

    @Override
    public List<Formulas> lovFormulas() {
        try {
            List<Formulas> lista = persistenciaFormulas.buscarFormulas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovFormulas Admi : " + e.toString());
            return null;
        }
    }
    ///// ---- FORMULASPROCESOS ---- /////

    ///// ---- OPERANDOSLOGS ---- /////
    @Override
    public List<OperandosLogs> listaOperandosLogsParaProcesoSecuencia(BigInteger secuencia) {
        try {
            List<OperandosLogs> lista = persistenciaOperandosLogs.buscarOperandosLogsParaProcesoSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaOperandosLogsParaProcesoSecuencia Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearOperandosLogs(List<OperandosLogs> listaOL) {
        try {
            for (int i = 0; i < listaOL.size(); i++) {
                listaOL.get(i).setDescripcion(String.valueOf(listaOL.get(i).getOperando().getCodigo()));
                persistenciaOperandosLogs.crear(listaOL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearOperandosLogs Admi : " + e.toString()
            );
        }
    }

    @Override
    public void editarOperandosLogs(List<OperandosLogs> listaOL) {
        try {
            for (int i = 0; i < listaOL.size(); i++) {
                persistenciaOperandosLogs.editar(listaOL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarOperandosLogs Admi : " + e.toString()
            );
        }
    }

    @Override
    public void borrarOperandosLogs(List<OperandosLogs> listaOL) {
        try {
            for (int i = 0; i < listaOL.size(); i++) {
                persistenciaOperandosLogs.borrar(listaOL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarOperandosLogs Admi : " + e.toString()
            );
        }
    }

    @Override
    public List<Operandos> lovOperandos() {
        try {
            List<Operandos> lista = persistenciaOperandos.buscarOperandos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovOperandos Admi : " + e.toString());
            return null;
        }
    }
    ///// ---- OPERANDOSLOGS ---- /////

}

package Administrar;

import Entidades.Formulas;
import Entidades.Historiasformulas;
import Entidades.Nodos;
import Entidades.Operadores;
import Entidades.Operandos;
import InterfaceAdministrar.AdministrarHistoriaFormulaInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import InterfacePersistencia.PersistenciaNodosInterface;
import InterfacePersistencia.PersistenciaOperadoresInterface;
import InterfacePersistencia.PersistenciaOperandosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarHistoriaFormula implements AdministrarHistoriaFormulaInterface {

    @EJB
    PersistenciaHistoriasformulasInterface persistenciaHistoriasFormulas;
    @EJB
    PersistenciaNodosInterface persistenciaNodos;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;
    @EJB
    PersistenciaOperadoresInterface persistenciaOperadores;

    @Override
    public List<Historiasformulas> listHistoriasFormulasParaFormula(BigInteger secuencia) {
        try {
            List<Historiasformulas> lista = persistenciaHistoriasFormulas.historiasFormulasParaFormulaSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listHistoriasFormulasParaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearHistoriasFormulas(List<Historiasformulas> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaHistoriasFormulas.crear(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearHistoriasFormulas Admi : " + e.toString());
        }
    }

    @Override
    public void editarHistoriasFormulas(List<Historiasformulas> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaHistoriasFormulas.editar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarHistoriasFormulas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarHistoriasFormulas(List<Historiasformulas> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaHistoriasFormulas.borrar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarHistoriasFormulas Admi : " + e.toString());
        }
    }

    @Override
    public List<Nodos> listNodosDeHistoriaFormula(BigInteger secuencia) {
        try {
            List<Nodos> lista = persistenciaNodos.buscarNodosPorSecuenciaHistoriaFormula(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listNodosDeHistoriaFormula Admi : " + e.toString());
            return null;
        }
    }

    public void crearNodos(List<Nodos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getOperador().getSecuencia() == null) {
                    lista.get(i).setOperador(null);
                }
                if (lista.get(i).getOperando().getSecuencia() == null) {
                    lista.get(i).setOperando(null);
                }
                persistenciaNodos.crear(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearNodos Admi : " + e.toString());
        }
    }

    public void borrarNodos(List<Nodos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getOperador().getSecuencia() == null) {
                    lista.get(i).setOperador(null);
                }
                if (lista.get(i).getOperando().getSecuencia() == null) {
                    lista.get(i).setOperando(null);
                }
                persistenciaNodos.borrar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarNodos Admi : " + e.toString());
        }
    }

    public void editarNodos(List<Nodos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getOperador().getSecuencia() == null) {
                    lista.get(i).setOperador(null);
                }
                if (lista.get(i).getOperando().getSecuencia() == null) {
                    lista.get(i).setOperando(null);
                }
                persistenciaNodos.editar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarNodos Admi : " + e.toString());
        }
    }

    @Override
    public Formulas actualFormula(BigInteger secuencia) {
        try {
            Formulas form = persistenciaFormulas.buscarFormula(secuencia);
            return form;
        } catch (Exception e) {
            System.out.println("Error actualFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Operadores> listOperadores() {
        try {
            List<Operadores> lista = persistenciaOperadores.buscarOperadores();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listOperadores  Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Operandos> listOperandos() {
        try {
            List<Operandos> lista = persistenciaOperandos.buscarOperandos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listOperandos  Admi : " + e.toString());
            return null;
        }
    }

}

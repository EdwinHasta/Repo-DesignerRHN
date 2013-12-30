package Administrar;

import Entidades.EstructurasFormulas;
import Entidades.Formulas;
import Entidades.Historiasformulas;
import Entidades.Nodos;
import Entidades.Operadores;
import Entidades.Operandos;
import InterfaceAdministrar.AdministrarHistoriaFormulaInterface;
import InterfacePersistencia.PersistenciaEstructurasFormulasInterface;
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
    @EJB
    PersistenciaEstructurasFormulasInterface persistenciaEstructurasFormulas;

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
                String aux = lista.get(i).getObservaciones().toUpperCase();
                lista.get(i).setObservaciones(aux);
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
                String aux = lista.get(i).getObservaciones().toUpperCase();
                lista.get(i).setObservaciones(aux);
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
                System.out.println("Nivel : " + lista.get(i).getPosicion());
            }
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getOperador().getSecuencia() == null) {
                    lista.get(i).setOperador(null);
                }
                if (lista.get(i).getOperando().getSecuencia() == null) {
                    lista.get(i).setOperando(null);
                }
                System.out.println("lista.get(i) Crear : " + lista.get(i).getSecuencia());
                persistenciaNodos.crear(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearNodos Admi : " + e.toString());
        }
    }

    public void borrarNodos(List<Nodos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                System.out.println("Nivel : " + lista.get(i).getPosicion());
            }
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

    @Override
    public List<EstructurasFormulas> listEstructurasFormulasParaHistoriaFormula(BigInteger secuencia) {
        try {
            List<EstructurasFormulas> lista = persistenciaEstructurasFormulas.estructurasFormulasParaHistoriaFormula(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listEstructurasFormulasParaHistoriaFormula Admi : " + e.toString());
            return null;
        }
    }

}

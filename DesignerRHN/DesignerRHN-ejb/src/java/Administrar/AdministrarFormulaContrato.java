package Administrar;

import Entidades.Contratos;
import Entidades.Formulas;
import Entidades.Formulascontratos;
import Entidades.Periodicidades;
import Entidades.Terceros;
import InterfaceAdministrar.AdministrarFormulaContratoInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaFormulasContratosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarFormulaContrato implements AdministrarFormulaContratoInterface{

    @EJB
    PersistenciaFormulasContratosInterface persistenciaFormulasContratos;
    @EJB
    PersistenciaContratosInterface persistenciaContratos;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;

    @Override
    public List<Formulascontratos> listFormulasContratosParaFormula(BigInteger secuencia) {
        try {
            List<Formulascontratos> lista = persistenciaFormulasContratos.formulasContratosParaFormulaSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulasContratosParaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearFormulasContratos(List<Formulascontratos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                if (listaFC.get(i).getTercero().getSecuencia() == null) {
                    listaFC.get(i).setTercero(null);
                }
                persistenciaFormulasContratos.crear(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulasContratos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarFormulasContratos(List<Formulascontratos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                if (listaFC.get(i).getTercero().getSecuencia() == null) {
                    listaFC.get(i).setTercero(null);
                }
                persistenciaFormulasContratos.borrar(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarFormulasContratos Admi : " + e.toString());
        }
    }

    @Override
    public void editarFormulasContratos(List<Formulascontratos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                if (listaFC.get(i).getTercero().getSecuencia() == null) {
                    listaFC.get(i).setTercero(null);
                }
                persistenciaFormulasContratos.editar(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarFormulasContratos Admi : " + e.toString());
        }
    }

    @Override
    public List<Contratos> listContratos() {
        try {
            List<Contratos> lista = persistenciaContratos.buscarContratos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listContratos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Periodicidades> listPeriodicidades() {
        try {
            List<Periodicidades> lista = persistenciaPeriodicidades.buscarPeriodicidades();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listPeriodicidades Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Terceros> listTerceros() {
        try {
            List<Terceros> lista = persistenciaTerceros.buscarTerceros();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listTerceros Admi : " + e.toString());
            return null;
        }
    }

    public Formulas actualFormula(BigInteger secuencia) {
        try {
            Formulas form = persistenciaFormulas.buscarFormula(secuencia);
            return form;
        } catch (Exception e) {
            System.out.println("Error actualFormula Admi : " + e.toString());
            return null;
        }
    }

}

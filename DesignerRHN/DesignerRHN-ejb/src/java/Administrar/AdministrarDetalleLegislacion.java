package Administrar;

import Entidades.Contratos;
import Entidades.Formulas;
import Entidades.Formulascontratos;
import Entidades.Periodicidades;
import Entidades.Terceros;
import InterfaceAdministrar.AdministrarDetalleLegislacionInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaFormulasContratosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarDetalleLegislacion implements AdministrarDetalleLegislacionInterface {

    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    @EJB
    PersistenciaFormulasContratosInterface persistenciaFormulasContratos;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    @EJB
    PersistenciaContratosInterface persistenciaContratos;

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
    public List<Formulas> listFormulas() {
        try {
            List<Formulas> lista = persistenciaFormulas.buscarFormulas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulas Admi : " + e.toString());
            return null;
        }
    }

    @Override 
    public List<Formulascontratos> listFormulasContratosParaFormula(BigInteger secuencia) {
        try {
            List<Formulascontratos> lista = persistenciaFormulasContratos.formulasContratosParaContratoSecuencia(secuencia);
            int tam = lista.size();
            if (tam>=1) {
                for (int i = 0; i < tam; i++) {
                    String aux = persistenciaConceptos.conceptoParaFormulaContrato(lista.get(i).getFormula().getSecuencia(), lista.get(i).getFechafinal());
                    lista.get(i).setStrConcepto(aux);
                }
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulasContratosParaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override 
    public void crearFormulaContrato(List<Formulascontratos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                if (listaFC.get(i).getTercero().getSecuencia() == null) {
                    listaFC.get(i).setTercero(null);
                }
                persistenciaFormulasContratos.crear(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulaContrato Admi : " + e.toString());
        }
    }

    @Override 
    public void borrarrFormulaContrato(List<Formulascontratos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                if (listaFC.get(i).getTercero().getSecuencia() == null) {
                    listaFC.get(i).setTercero(null);
                }
                persistenciaFormulasContratos.borrar(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarrFormulaContrato Admi : " + e.toString());
        }
    }

    @Override
    public void editarFormulaContrato(List<Formulascontratos> listaFC) {
        try {
            for (int i = 0; i < listaFC.size(); i++) {
                if (listaFC.get(i).getTercero().getSecuencia() == null) {
                    listaFC.get(i).setTercero(null);
                }
                persistenciaFormulasContratos.editar(listaFC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarFormulaContrato Admi : " + e.toString());
        }
    }

    @Override
    public Contratos contratoActual(BigInteger secuencia) {
        try {
            Contratos act = persistenciaContratos.buscarContratoSecuencia(secuencia);
            return act;
        } catch (Exception e) {
            System.out.println("Error contratoActual Admi : "+e.toString());
            return null;
        }
    }
    
    public String obtenerConcepto(BigInteger secuencia, Date fecha){
        try{
            String aux = persistenciaConceptos.conceptoParaFormulaContrato(secuencia, fecha);
            return aux;
            }catch(Exception e){
            System.out.println("Error obtenerConcepto Admi : "+e.toString());
            return "";
        }
    }

}

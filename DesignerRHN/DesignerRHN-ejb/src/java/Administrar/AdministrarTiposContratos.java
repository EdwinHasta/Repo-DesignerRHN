/**
 * Documentación a cargo de Andres Pineda
 */
package Administrar;

import Entidades.DiasLaborables;
import Entidades.TiposContratos;
import Entidades.TiposDias;
import InterfaceAdministrar.AdministrarTiposContratosInterface;
import InterfacePersistencia.PersistenciaDiasLaborablesInterface;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'TipoContrato'.
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarTiposContratos implements AdministrarTiposContratosInterface{

    //--------------------------------------------------------------------------    
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaTiposContratos'.
     */
    @EJB
    PersistenciaTiposContratosInterface persistenciaTiposContratos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDiasLaborables'.
     */
    @EJB
    PersistenciaDiasLaborablesInterface persistenciaDiasLaborables;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaTiposDias'.
     */
    @EJB
    PersistenciaTiposDiasInterface persistenciaTiposDias;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------    
    @Override
    public List<TiposContratos> listaTiposContratos() {
        try {
            List<TiposContratos> lista = persistenciaTiposContratos.tiposContratos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaTiposContratos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearTiposContratos(List<TiposContratos> listaTC) {
        try {
            for (int i = 0; i < listaTC.size(); i++) {
                persistenciaTiposContratos.crear(listaTC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearTiposContratos Admi : " + e.toString());
        }
    }

    @Override
    public void editarTiposContratos(List<TiposContratos> listaTC) {
        try {
            for (int i = 0; i < listaTC.size(); i++) {
                persistenciaTiposContratos.editar(listaTC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarTiposContratos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarTiposContratos(List<TiposContratos> listaTC) {
        try {
            for (int i = 0; i < listaTC.size(); i++) {
                persistenciaTiposContratos.borrar(listaTC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarTiposContratos Admi : " + e.toString());
        }
    }

    @Override
    public List<DiasLaborables> listaDiasLaborablesParaTipoContrato(BigInteger secTipoContrato) {
        try {
            List<DiasLaborables> lista = persistenciaDiasLaborables.diasLaborablesParaSecuenciaTipoContrato(secTipoContrato);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaDiasLaborablesParaTipoContrato Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearDiasLaborables(List<DiasLaborables> listaDL) {
        try {
            for (int i = 0; i < listaDL.size(); i++) {
                persistenciaDiasLaborables.crear(listaDL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearDiasLaborables Admi : " + e.toString());
        }
    }

    @Override
    public void editarDiasLaborables(List<DiasLaborables> listaDL) {
        try {
            for (int i = 0; i < listaDL.size(); i++) {
                persistenciaDiasLaborables.editar(listaDL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarDiasLaborables Admi : " + e.toString());
        }
    }

    @Override
    public void borrarDiasLaborables(List<DiasLaborables> listaDL) {
        try {
            for (int i = 0; i < listaDL.size(); i++) {
                persistenciaDiasLaborables.borrar(listaDL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarDiasLaborables Admi : " + e.toString());
        }
    }

    @Override
    public List<TiposDias> lovTiposDias() {
        try {
            List<TiposDias> lista = persistenciaTiposDias.buscarTiposDias();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposDias Admi : " + e.toString());
            return null;
        }
    }
}

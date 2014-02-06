/**
 * Documentación a cargo de Andres Pineda
 */
package Administrar;

import Entidades.DetallesReformasLaborales;
import Entidades.ReformasLaborales;
import InterfaceAdministrar.AdministrarReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaDetallesReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'SolucionFormula'.
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarReformasLaborales implements AdministrarReformasLaboralesInterface{

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaReformasLaborales'.
     */
    @EJB
    PersistenciaReformasLaboralesInterface persistenciaReformasLaborales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDetallesReformasLaborales'.
     */
    @EJB
    PersistenciaDetallesReformasLaboralesInterface persistenciaDetallesReformasLaborales;

    @Override
    public List<ReformasLaborales> listaReformasLaborales() {
        try {
            List<ReformasLaborales> lista = persistenciaReformasLaborales.buscarReformasLaborales();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaReformasLaborales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearReformaLaboral(List<ReformasLaborales> listaRL) {
        try {
            for (int i = 0; i < listaRL.size(); i++) {
                persistenciaReformasLaborales.crear(listaRL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearReformaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public void editarReformaLaboral(List<ReformasLaborales> listaRL) {
        try {
            for (int i = 0; i < listaRL.size(); i++) {
                persistenciaReformasLaborales.editar(listaRL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarReformaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public void borrarReformaLaboral(List<ReformasLaborales> listaRL) {
        try {
            for (int i = 0; i < listaRL.size(); i++) {
                persistenciaReformasLaborales.borrar(listaRL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarReformaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public List<DetallesReformasLaborales> listaDetalleReformasLaborales(BigInteger secuencia) {
        try {
            List<DetallesReformasLaborales> lista = persistenciaDetallesReformasLaborales.buscarDetalleReformasParaReformaSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaDetalleReformasLaborales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearDetalleReformaLaboral(List<DetallesReformasLaborales> listaDRL) {
        try {
            for (int i = 0; i < listaDRL.size(); i++) {
                persistenciaDetallesReformasLaborales.crear(listaDRL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearDetalleReformaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public void editarDetalleReformaLaboral(List<DetallesReformasLaborales> listaDRL) {
        try {
            for (int i = 0; i < listaDRL.size(); i++) {
                persistenciaDetallesReformasLaborales.editar(listaDRL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarDetalleReformaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public void borrarDetalleReformaLaboral(List<DetallesReformasLaborales> listaDRL) {
        try {
            for (int i = 0; i < listaDRL.size(); i++) {
                persistenciaDetallesReformasLaborales.borrar(listaDRL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarDetalleReformaLaboral Admi : " + e.toString());
        }
    }

}

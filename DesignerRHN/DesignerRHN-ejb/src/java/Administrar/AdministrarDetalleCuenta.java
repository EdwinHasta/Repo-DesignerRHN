/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Cuentas;
import Entidades.VigenciasCuentas;
import InterfaceAdministrar.AdministrarDetalleCuentaInterface;
import InterfacePersistencia.PersistenciaCuentasInterface;
import InterfacePersistencia.PersistenciaVigenciasCuentasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'DetalleCuenta'.
 * @author Betelgeuse.
 */
@Stateful
public class AdministrarDetalleCuenta implements AdministrarDetalleCuentaInterface {
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
     * Atributo que representa la comunicación con la persistencia 'persistenciaCuentas'.
     */
    @EJB
    PersistenciaCuentasInterface persistenciaCuentas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<VigenciasCuentas> consultarListaVigenciasCuentasCredito(BigInteger secuenciaC) {
        try {
            List<VigenciasCuentas> listCCredito = persistenciaVigenciasCuentas.buscarVigenciasCuentasPorCredito(secuenciaC);
            return listCCredito;
        } catch (Exception e) {
            System.out.println("Error listVigenciasCuentasCredito Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<VigenciasCuentas> consultarListaVigenciasCuentasDebito(BigInteger secuenciaC) {
        try {
            List<VigenciasCuentas> listCDebito = persistenciaVigenciasCuentas.buscarVigenciasCuentasPorDebito(secuenciaC);
            return listCDebito;
        } catch (Exception e) {
            System.out.println("Error listVigenciasCuentasDebito Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Cuentas mostrarCuenta(BigInteger secuencia) {
        try {
            Cuentas cuentaActual = persistenciaCuentas.buscarCuentasSecuencia(secuencia);
            return cuentaActual;
        } catch (Exception e) {
            System.out.println("Error cuentaActual Admi: " + e.toString());
            return null;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author user
 */
@Stateful
public class AdministrarDetalleCuenta implements AdministrarDetalleCuentaInterface {

    @EJB
    PersistenciaVigenciasCuentasInterface persistenciaVigenciasCuentas;
    @EJB
    PersistenciaCuentasInterface persistenciaCuentas;

    @Override
    public List<VigenciasCuentas> listVigenciasCuentasCredito(BigInteger secuenciaC) {
        try {
            List<VigenciasCuentas> listCCredito = persistenciaVigenciasCuentas.buscarVigenciasCuentasPorCredito(secuenciaC);
            return listCCredito;
        } catch (Exception e) {
            System.out.println("Error listVigenciasCuentasCredito Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<VigenciasCuentas> listVigenciasCuentasDebito(BigInteger secuenciaC) {
        try {
            List<VigenciasCuentas> listCDebito = persistenciaVigenciasCuentas.buscarVigenciasCuentasPorDebito(secuenciaC);
            return listCDebito;
        } catch (Exception e) {
            System.out.println("Error listVigenciasCuentasDebito Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Cuentas cuentaActual(BigInteger secuencia) {
        try {
            Cuentas cuentaActual = persistenciaCuentas.buscarCuentasSecuencia(secuencia);
            return cuentaActual;
        } catch (Exception e) {
            System.out.println("Error cuentaActual Admi: " + e.toString());
            return null;
        }
    }
}

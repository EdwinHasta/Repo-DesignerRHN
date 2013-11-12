/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Cuentas;
import Entidades.VigenciasCuentas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarDetalleCuentaInterface {

    public List<VigenciasCuentas> listVigenciasCuentasCredito(BigInteger secuenciaC);

    public List<VigenciasCuentas> listVigenciasCuentasDebito(BigInteger secuenciaC);

    public Cuentas cuentaActual(BigInteger secuencia);
}

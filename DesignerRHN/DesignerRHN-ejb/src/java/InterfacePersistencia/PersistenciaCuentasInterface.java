/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Cuentas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaCuentasInterface {

    public void crear(Cuentas cuentas);

    public void editar(Cuentas cuentas);

    public void borrar(Cuentas cuentas);

    public Cuentas buscarCuenta(Object id);

    public List<Cuentas> buscarCuentas();

    public Cuentas buscarCuentasSecuencia(BigInteger secuencia);
    
    public List<Cuentas> buscarCuentasSecuenciaEmpresa(BigInteger secuencia);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasCuentas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasCuentasInterface {

    public void crear(VigenciasCuentas vigenciasCuentas);

    public void editar(VigenciasCuentas vigenciasCuentas);

    public void borrar(VigenciasCuentas vigenciasCuentas);

    public List<VigenciasCuentas> buscarVigenciasCuentasPorConcepto(BigInteger secuencia);

    public VigenciasCuentas buscarVigenciaCuenta(Object id);

    public List<VigenciasCuentas> buscarVigenciasCuentas();

    public VigenciasCuentas buscarVigenciaCuentaSecuencia(BigInteger secuencia);

    public List<VigenciasCuentas> buscarVigenciasCuentasPorCredito(BigInteger secuencia);

    public List<VigenciasCuentas> buscarVigenciasCuentasPorDebito(BigInteger secuencia);
}

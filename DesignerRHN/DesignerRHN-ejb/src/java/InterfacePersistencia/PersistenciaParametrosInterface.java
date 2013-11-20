/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Parametros;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaParametrosInterface {

    public List<Parametros> parametrosComprobantes(String usuarioBD);
    public List<Parametros> empleadosParametros();
    public void borrar(Parametros parametro);
    public void borrarParametros(BigInteger secParametrosEstructuras);
}

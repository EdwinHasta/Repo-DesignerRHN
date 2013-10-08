/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.PryClientes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Viktor
 */
public interface PersistenciaPryClientesInterface {
    public List<PryClientes> pryclientes();
    public PryClientes consultaCliente(BigInteger secuencia);
}

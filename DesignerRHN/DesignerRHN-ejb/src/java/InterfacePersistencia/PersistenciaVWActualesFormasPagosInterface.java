/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesFormasPagos;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVWActualesFormasPagosInterface {

    public VWActualesFormasPagos buscarFormaPago(BigInteger secuencia);
}

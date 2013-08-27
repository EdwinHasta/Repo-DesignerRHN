/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesReformasLaborales;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVWActualesReformasLaboralesInterface {

    public VWActualesReformasLaborales buscarReformaLaboral(BigInteger secuencia);
}

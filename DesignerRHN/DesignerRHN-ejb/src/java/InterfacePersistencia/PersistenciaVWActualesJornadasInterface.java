/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesJornadas;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVWActualesJornadasInterface {
    
        public VWActualesJornadas buscarJornada(BigInteger secuencia);

}

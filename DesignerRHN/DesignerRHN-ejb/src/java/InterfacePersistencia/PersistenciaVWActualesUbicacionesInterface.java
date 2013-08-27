/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesUbicaciones;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVWActualesUbicacionesInterface {
        public VWActualesUbicaciones buscarUbicacion(BigInteger secuencia);

}

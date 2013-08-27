/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesLocalizaciones;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVWActualesLocalizacionesInterface {

    public VWActualesLocalizaciones buscarLocalizacion(BigInteger secuencia);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.HvEntrevistas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaHvEntrevistasInterface {
    public List<HvEntrevistas> entrevistasPersona(BigInteger secuenciaHV);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Familiares;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaFamiliaresInterface {
    public List<Familiares> familiaresPersona(BigInteger secuenciaPersona);
}

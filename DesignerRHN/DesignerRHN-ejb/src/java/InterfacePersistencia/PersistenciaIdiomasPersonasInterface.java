/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.IdiomasPersonas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaIdiomasPersonasInterface {
    public List<IdiomasPersonas> idiomasPersona(BigInteger secuenciaPersona);
}

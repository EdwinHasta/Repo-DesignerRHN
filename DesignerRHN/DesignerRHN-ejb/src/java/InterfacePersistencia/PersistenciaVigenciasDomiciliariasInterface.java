/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasDomiciliarias;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasDomiciliariasInterface {
    public List<VigenciasDomiciliarias> visitasDomiciliariasPersona(BigInteger secuenciaPersona) ;
}

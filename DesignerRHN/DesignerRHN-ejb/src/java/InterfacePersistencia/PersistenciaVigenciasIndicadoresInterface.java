/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasIndicadores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasIndicadoresInterface {
    public List<VigenciasIndicadores> indicadoresPersona(BigInteger secuenciaEmpl);
}

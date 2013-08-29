/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Encargaturas;
import Entidades.VigenciasFormales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasFormalesInterface {
    public List<VigenciasFormales> educacionPersona(BigInteger secuenciaPersona);
}

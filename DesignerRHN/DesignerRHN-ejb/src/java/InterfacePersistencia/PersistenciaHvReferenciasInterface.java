/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.HvReferencias;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaHvReferenciasInterface {
    public List<HvReferencias> referenciasPersonalesPersona(BigInteger secuenciaHV);
    public List<HvReferencias> referenciasFamiliaresPersona(BigInteger secuenciaHV);
}

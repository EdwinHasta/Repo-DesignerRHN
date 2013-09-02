/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasDeportes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasDeportesInterface {
    public List<VigenciasDeportes> deportePersona(BigInteger secuenciaPersona);
}

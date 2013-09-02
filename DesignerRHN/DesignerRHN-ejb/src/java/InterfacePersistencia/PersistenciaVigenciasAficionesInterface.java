/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasAficiones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasAficionesInterface {
 public List<VigenciasAficiones> aficionesPersona(BigInteger secuenciaPersona);   
}

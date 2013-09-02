/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Demandas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaDemandasInterface {
    public List<Demandas> demandasPersona(BigInteger secuenciaEmpl);
}

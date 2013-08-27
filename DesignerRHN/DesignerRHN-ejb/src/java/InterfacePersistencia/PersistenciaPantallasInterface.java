/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Pantallas;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaPantallasInterface {
        public Pantallas buscarPantalla(BigInteger secuenciaTab);

}

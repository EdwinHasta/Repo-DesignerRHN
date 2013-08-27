/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.RastrosValores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaRastrosValoresInterface {

    public List<RastrosValores> rastroValores(BigInteger secRastro);
}

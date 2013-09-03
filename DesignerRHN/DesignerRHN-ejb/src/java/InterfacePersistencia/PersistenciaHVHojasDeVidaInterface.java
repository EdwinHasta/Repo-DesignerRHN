/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.HVHojasDeVida;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaHVHojasDeVidaInterface {

    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secuenciaPersona);
    public void editar(HVHojasDeVida hVHojasDeVida);
}
